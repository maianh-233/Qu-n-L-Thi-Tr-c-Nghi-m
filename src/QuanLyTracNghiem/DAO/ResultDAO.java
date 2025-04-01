package QuanLyTracNghiem.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.ResultModel;
import QuanLyTracNghiem.DTO.UserModel;

public class ResultDAO {
    public ArrayList<ResultModel> selectListKetQuaCuaUserThamGiaTest(ArrayList<ExamModel> list_exam) {
        var list = new ArrayList<ResultModel>();

        if (list_exam.isEmpty()) {
            return list; // Trả về danh sách rỗng nếu không có kỳ thi nào
        }

        // Xây dựng truy vấn với số lượng tham số động
        var query = new StringBuilder("SELECT * FROM result WHERE exam_id IN (");

        // Thêm dấu '?' tương ứng với số lượng exam_id
        query.append("?,".repeat(list_exam.size()));
        query.setLength(query.length() - 1); // Xóa dấu ',' cuối cùng
        query.append(")");

        try (var ps = MyConnect.conn.prepareStatement(query.toString())) {
            // Gán giá trị cho các tham số exam_id
            for (int i = 0; i < list_exam.size(); i++) {
                ps.setInt(i + 1, list_exam.get(i).getExam_id());
            }

            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new ResultModel(
                        resultSet.getInt("diem"),
                        resultSet.getInt("raw_score"),
                        resultSet.getInt("status"),
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("socaudung"),
                        resultSet.getInt("socausai"),
                        resultSet.getTimestamp("tgian_nop") != null
                                ? resultSet.getTimestamp("tgian_nop").toLocalDateTime()
                                : null,  // Xử lý nếu giá trị NULL trong database
                        resultSet.getString("user_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public ArrayList<UserModel> selectListInfoCuaUserThamGiaTest(ArrayList<ExamModel> list_exam) {
        var list = new ArrayList<UserModel>();

        if (list_exam.isEmpty()) {
            return list; // Trả về danh sách rỗng nếu không có kỳ thi nào
        }

        // Xây dựng câu truy vấn động với tham số
        var query = new StringBuilder("SELECT * FROM user u JOIN result r ON u.user_id = r.user_id WHERE r.exam_id IN (");

        // Thêm dấu '?' tương ứng với số lượng exam_id
        query.append("?,".repeat(list_exam.size()));
        query.setLength(query.length() - 1); // Xóa dấu ',' cuối cùng
        query.append(")");

        try (var ps = MyConnect.conn.prepareStatement(query.toString())) {
            // Gán giá trị cho tham số
            for (int i = 0; i < list_exam.size(); i++) {
                ps.setInt(i + 1, list_exam.get(i).getExam_id());
            }

            var rs = ps.executeQuery();
            while (rs.next()) {
                UserModel user=new UserModel();
                user.setUser_id(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setIsAdmin(rs.getInt("isAdmin"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    //Lây kết quả của một thí sinh tham gia bài thi
    public  ResultModel selectResultUser(int exam_id, String user_id) {
        var query = "SELECT * FROM result WHERE exam_id=? AND user_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam_id);
            ps.setString(2, user_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new ResultModel(
                        resultSet.getInt("diem"),
                        resultSet.getInt("raw_score"),
                        resultSet.getInt("status"),
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("socaudung"),
                        resultSet.getInt("socausai"),
                        resultSet.getTimestamp("tgian_nop") != null
                                ? resultSet.getTimestamp("tgian_nop").toLocalDateTime()
                                : null,  // Xử lý nếu giá trị NULL trong database
                        resultSet.getString("user_id")
                );            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Thêm kết quả của thí sinh
    public boolean insert(ResultModel result) {
        var query = "INSERT INTO result (diem, raw_score, status, exam_id, socaudung, socausai, tgian_nop, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setFloat(1, result.getDiem()); // Điểm
            ps.setFloat(2, result.getRaw_score()); // Điểm thô
            ps.setInt(3, result.getStatus()); // Trạng thái
            ps.setInt(4, result.getExam_id()); // Mã kỳ thi
            ps.setInt(5, result.getSoCauDung()); // Số câu đúng
            ps.setInt(6, result.getSoCauSai()); // Số câu sai

            // Kiểm tra nếu tgian_nop là null thì đặt giá trị NULL
            if (result.getTgian_nop() != null) {
                ps.setTimestamp(7, Timestamp.valueOf(result.getTgian_nop()));
            } else {
                ps.setNull(7, Types.TIMESTAMP);
            }

            ps.setString(8, result.getUser_id()); // Mã người dùng

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //sửa chữa kết quả bài thi =>Không cần thiest lắm vì kết quả do hệ thống tính điểm
    public boolean update(ResultModel result) {
        var query = "UPDATE result SET diem=?, raw_score=?, status=?, socaudung=?, socausai=?, tgian_nop=? WHERE exam_id=? AND user_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, result.getDiem()); // Điểm
            ps.setInt(2, result.getRaw_score()); // Điểm thô
            ps.setInt(3, result.getStatus()); // Trạng thái
            ps.setInt(4, result.getSoCauDung()); // Số câu đúng
            ps.setInt(5, result.getSoCauSai()); // Số câu sai

            // Xử lý nếu tgian_nop là null
            if (result.getTgian_nop() != null) {
                ps.setTimestamp(6, Timestamp.valueOf(result.getTgian_nop()));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }

            ps.setInt(7, result.getExam_id()); // Mã kỳ thi
            ps.setString(8, result.getUser_id()); // Mã người dùng

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Inset một list result
    public void insertListResultToDB(ArrayList<ResultModel> list) {
        String sql =  "INSERT INTO result (exam_id, user_id) VALUES (?, ?)";

        try (PreparedStatement stmt = MyConnect.conn.prepareStatement(sql)) {
            MyConnect.conn.setAutoCommit(false); // Bật chế độ batch insert

            for (ResultModel result: list) {
                stmt.setInt(1, result.getExam_id());
                stmt.setString(2, result.getUser_id());
                stmt.addBatch(); // Thêm vào batch
            }

            int[] result = stmt.executeBatch(); // Chạy batch insert
            MyConnect.conn.commit(); // Xác nhận dữ liệu

            System.out.println("Đã chèn " + result.length + " bản ghi vào Result.");

        } catch (SQLException e) {
            try {
                MyConnect.conn.rollback(); // Rollback nếu có lỗi
                System.err.println("Lỗi xảy ra, rollback dữ liệu.");
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                MyConnect.conn.setAutoCommit(true); // Bật lại auto commit
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }

    //Tìm user_id khi không biết exam_íd
    public ResultModel selectResultsForUser(ArrayList<ExamModel> examIds, String userId) {
        if (examIds == null || examIds.isEmpty()) {
            return null; // Trả về null nếu không có exam_id nào
        }

        // Tạo chuỗi dấu ? để truyền vào câu lệnh SQL
        String placeholders = examIds.stream().map(id -> "?").collect(Collectors.joining(", "));
        String query = "SELECT * FROM result WHERE exam_id IN (" + placeholders + ") AND user_id=? LIMIT 1";

        try (var ps = MyConnect.conn.prepareStatement(query)) {
            // Gán giá trị cho các tham số exam_id
            for (int i = 0; i < examIds.size(); i++) {
                ps.setInt(i + 1, examIds.get(i).getExam_id());
            }

            // Gán giá trị cho user_id
            ps.setString(examIds.size() + 1, userId);

            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new ResultModel(
                        resultSet.getInt("diem"),
                        resultSet.getInt("raw_score"),
                        resultSet.getInt("status"),
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("socaudung"),
                        resultSet.getInt("socausai"),
                        resultSet.getTimestamp("tgian_nop") != null
                                ? resultSet.getTimestamp("tgian_nop").toLocalDateTime()
                                : null,  // Xử lý nếu giá trị NULL trong database
                        resultSet.getString("user_id")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null; // Trả về null nếu không tìm thấy kết quả nào
    }




}
