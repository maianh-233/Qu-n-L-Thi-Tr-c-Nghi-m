package QuanLyTracNghiem.DAO;

import QuanLyTracNghiem.DTO.UserModel;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Lấy thông tin user khi đăng nhập
    public UserModel getUser(String user_id, String password) {
        UserModel user = null;
        String sql = "SELECT user_id, fullname, phone, email, isAdmin, password FROM user WHERE user_id = ?";

        try (PreparedStatement pst = MyConnect.conn.prepareStatement(sql)) {
            pst.setString(1, user_id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    if (hashedPassword.equals(hashPassword(password))) {
                        user = new UserModel();
                        user.setUser_id(rs.getString("user_id"));
                        user.setFullname(rs.getString("fullname"));
                        user.setPhone(rs.getString("phone"));
                        user.setEmail(rs.getString("email"));
                        user.setIsAdmin(rs.getInt("isAdmin"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi lấy user trong CSDL: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    // Hàm thêm user vào CSDL
    public boolean addUser(String email, int isAdmin, String password, String phone, String fullname,String user_id) {
        String sql = "INSERT INTO user (email, isAdmin, password, phone, user_id, fullname) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql)) {
            pre.setString(1, email);
            pre.setInt(2, isAdmin);
            pre.setString(3, hashPassword(password));
            pre.setString(4, phone);
            pre.setString(5, user_id);
            pre.setString(6, fullname);

            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi thêm user vào CSDL: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Hàm tự động tạo user_id
    public String generateUserId(String fullname) {
        String baseId = fullname.toLowerCase().replace(" ", "");
        String sql = "SELECT user_id FROM user WHERE user_id LIKE ? ORDER BY user_id DESC LIMIT 1";
        try (PreparedStatement pst = MyConnect.conn.prepareStatement(sql)) {
            pst.setString(1, baseId + "%");
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String lastId = rs.getString("user_id");
                    int number = 1;
                    if (lastId.matches(baseId + "\\d+")) {
                        number = Integer.parseInt(lastId.replace(baseId, "")) + 1;
                    }
                    return baseId + number;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return baseId + "1";
    }

    // Hàm băm mật khẩu (SHA-256)
        private String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Mật khẩu không được null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi khi băm mật khẩu", e);
        }
    }

    // Hàm sửa thông tin user
    public boolean editUser(String email, String password, String phone, String user_id) {
        StringBuilder sql = new StringBuilder("UPDATE user SET ");
        List<String> fields = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        if (email != null && !email.trim().isEmpty()) {
            fields.add("email = ?");
            values.add(email);
        }
        if (phone != null && !phone.trim().isEmpty()) {
            fields.add("phone = ?");
            values.add(phone);
        }
        if (password != null && !password.trim().isEmpty()) {
            fields.add("password = ?");
            values.add(hashPassword(password));
        }

        if (fields.isEmpty()) {
            System.out.println("Không có dữ liệu để cập nhật.");
            return false;
        }

        sql.append(String.join(", ", fields)).append(" WHERE user_id = ?;");
        values.add(user_id);

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.size(); i++) {
                pre.setObject(i + 1, values.get(i));
            }
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi sửa user trong CSDL: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Hàm lấy thông tin user theo user_id
    public UserModel getInfo(String user_id) {
        UserModel user = null;
        String sql = "SELECT user_id, fullname, phone, email, isAdmin FROM user WHERE user_id = ?";
        try (PreparedStatement pst = MyConnect.conn.prepareStatement(sql)) {
            pst.setString(1, user_id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = new UserModel();
                    user.setUser_id(rs.getString("user_id"));
                    user.setFullname(rs.getString("fullname"));
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setIsAdmin(rs.getInt("isAdmin"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi lấy thông tin user trong CSDL: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    //Lấy danh sách người đã làm bài thi
    public ArrayList<UserModel> getListUserDoExam(int exam_id) {
        ArrayList<UserModel> userList = new ArrayList<>();
        String query = "SELECT u.user_id, u.fullname, u.email, u.phone, u.isAdmin " +
                "FROM user u " +
                "JOIN result r ON u.user_id = r.user_id " +
                "WHERE r.exam_id = ?";
        try (PreparedStatement pstmt =  MyConnect.conn.prepareStatement(query)) {
            pstmt.setInt(1, exam_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setUser_id(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setIsAdmin(rs.getInt("isAdmin"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

}
