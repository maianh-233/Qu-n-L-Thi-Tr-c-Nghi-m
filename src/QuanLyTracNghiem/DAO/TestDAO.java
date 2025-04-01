package QuanLyTracNghiem.DAO;
import QuanLyTracNghiem.DTO.TestModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {
    //Thêm bài test vào csdl=>không cần tham số test_id vì cột tăng tự động
    public boolean addTest(String create_by, int luotlambai, LocalDateTime ngaybd_thi, int socaude,
                           int socauhoi,int socaukho, int socauthuong, int solgmade, int test_id,
                           String test_name,int test_status ,int tgianlambai) {
        LocalDateTime create_at = LocalDateTime.now();
        String sql = "INSERT INTO test (create_at, create_by, luotlambai, ngaybd_thi, socaude, socauhoi, socaukho, socauthuong, solgmade,test_id, test_name,test_status,tgianlambai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql)) {
            pre.setObject(1, create_at);
            pre.setObject(2, create_by);
            pre.setObject(3, luotlambai);
            pre.setObject(4, ngaybd_thi);
            pre.setObject(5, socaude);

            pre.setObject(6, socauhoi);
            pre.setObject(7,  socaukho);
            pre.setObject(8, socauthuong);
            pre.setObject(9, solgmade);
            pre.setObject(10, test_id);

            pre.setObject(11, test_name);
            pre.setObject(12, test_status);
            pre.setObject(13, tgianlambai);

            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi thêm test vào cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    //Sửa bài test (luotlanbai, ngaybd_thi,test_name, tgianlambai)
    public boolean editTest( LocalDateTime ngaybd_thi, String test_name, Integer tgianlambai, int test_id) {
        StringBuilder sql = new StringBuilder("UPDATE test SET ");
        List<String> fields = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        if (ngaybd_thi != null) {
            fields.add("ngaybd_thi = ?");
            values.add(Timestamp.valueOf(ngaybd_thi));
        }
        if (test_name != null && !test_name.trim().isEmpty()) {
            fields.add("test_name = ?");
            values.add(test_name);
        }
        if (tgianlambai != null) {
            fields.add("tgianlambai = ?");
            values.add(tgianlambai);
        }

        // Nếu không có trường nào để cập nhật, return false ngay
        if (fields.isEmpty()) {
            System.out.println("Không có dữ liệu nào để cập nhật.");
            return false;
        }

        // Ghép câu lệnh SQL
        sql.append(String.join(", ", fields)).append(" WHERE test_id = ?");
        values.add(test_id);

        // Thực thi câu lệnh SQL
        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.size(); i++) {
                pre.setObject(i + 1, values.get(i));
            }
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi sửa test trong CSDL: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //Tìm test_id lớn nhất
    public Integer finMaxTestId() {
        String sql = "SELECT MAX(test_id) AS max_test_id FROM test";

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            if (rs.next()) {
                int maxTestId = rs.getInt("max_test_id");
                return rs.wasNull() ? 0: maxTestId; // Kiểm tra nếu giá trị NULL trong DB
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy tạo test_id lớn nhất: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;// Trả về null nếu không có dữ liệu hoặc có lỗi
    }


    // Lấy danh sách bài kiểm tra đã xảy ra
    public ArrayList<TestModel> getPastTestsByCreator(String create_by) {
        ArrayList<TestModel> test_list = new ArrayList<>();
        String sql = "SELECT * FROM test WHERE create_by = ? AND ngaybd_thi < NOW() ORDER BY ngaybd_thi DESC";

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql)) {
            pre.setString(1, create_by);

            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    TestModel test = new TestModel();
                    test.setTest_id(rs.getInt("test_id"));
                    test.setTest_name(rs.getString("test_name"));
                    test.setCreate_by(rs.getString("create_by"));
                    test.setLuotlambai(rs.getInt("luotlambai"));
                    test.setSocaude(rs.getInt("socaude"));
                    test.setSocauhoi(rs.getInt("socauhoi"));
                    test.setSocaukho(rs.getInt("socaukho"));
                    test.setSocauthuong(rs.getInt("socauthuong"));
                    test.setSolgmade(rs.getInt("solgmade"));
                    test.setTest_status(rs.getInt("test_status"));
                    test.setTgianlambai(rs.getInt("tgianlambai"));
                    test.setNgbd_thi(rs.getTimestamp("ngaybd_thi") != null ? rs.getTimestamp("ngaybd_thi").toLocalDateTime() : null);
                    test.setCreate_at(rs.getTimestamp("create_at") != null ? rs.getTimestamp("create_at").toLocalDateTime() : null);
                    test_list.add(test);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi lấy danh sách bài kiểm tra đã xảy ra: " + e.getMessage());
            e.printStackTrace();
        }

        return test_list;
    }

    // Lấy danh sách bài kiểm tra chưa xảy ra
    public ArrayList<TestModel> getUpcomingTestsByCreator(String create_by) {
        ArrayList<TestModel> test_list = new ArrayList<>();
        String sql = "SELECT * FROM test WHERE create_by = ? AND ngaybd_thi >= NOW() ORDER BY ngaybd_thi ASC";

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql)) {
            pre.setString(1, create_by);

            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    TestModel test = new TestModel();
                    test.setTest_id(rs.getInt("test_id"));
                    test.setTest_name(rs.getString("test_name"));
                    test.setCreate_by(rs.getString("create_by"));
                    test.setLuotlambai(rs.getInt("luotlambai"));
                    test.setSocaude(rs.getInt("socaude"));
                    test.setSocauhoi(rs.getInt("socauhoi"));
                    test.setSocaukho(rs.getInt("socaukho"));
                    test.setSocauthuong(rs.getInt("socauthuong"));
                    test.setSolgmade(rs.getInt("solgmade"));
                    test.setTest_status(rs.getInt("test_status"));
                    test.setTgianlambai(rs.getInt("tgianlambai"));
                    test.setNgbd_thi(rs.getTimestamp("ngaybd_thi") != null ? rs.getTimestamp("ngaybd_thi").toLocalDateTime() : null);
                    test.setCreate_at(rs.getTimestamp("create_at") != null ? rs.getTimestamp("create_at").toLocalDateTime() : null);
                    test_list.add(test);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi lấy danh sách bài kiểm tra chưa xảy ra: " + e.getMessage());
            e.printStackTrace();
        }

        return test_list;
    }
    public TestModel findTestById(int test_id) {
        String sql = "SELECT * FROM test WHERE test_id = ?";
        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql)) {
            pre.setInt(1, test_id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    TestModel test = new TestModel();
                    test.setTest_id(rs.getInt("test_id"));
                    test.setTest_name(rs.getString("test_name"));
                    test.setCreate_by(rs.getString("create_by"));
                    test.setLuotlambai(rs.getInt("luotlambai"));
                    test.setSocaude(rs.getInt("socaude"));
                    test.setSocauhoi(rs.getInt("socauhoi"));
                    test.setSocaukho(rs.getInt("socaukho"));
                    test.setSocauthuong(rs.getInt("socauthuong"));
                    test.setSolgmade(rs.getInt("solgmade"));
                    test.setTest_status(rs.getInt("test_status"));
                    test.setTgianlambai(rs.getInt("tgianlambai"));
                    test.setNgbd_thi(rs.getTimestamp("ngaybd_thi") != null ? rs.getTimestamp("ngaybd_thi").toLocalDateTime() : null);
                    test.setCreate_at(rs.getTimestamp("create_at") != null ? rs.getTimestamp("create_at").toLocalDateTime() : null);
                    return test;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi tìm bài kiểm tra theo test_id: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy bài kiểm tra
    }



}
