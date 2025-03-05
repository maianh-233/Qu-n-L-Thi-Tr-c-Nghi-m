package QuanLyTracNghiem.DAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DTO.ResultModel;

public class ResultDAO {
    public List<ResultModel> selectListKetQuaCuaUserThamGiaTest(Integer exam_id) {
        var list = new ArrayList<ResultModel>();
        var query = "SELECT * FROM results WHERE user_id=? AND exam_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(2,exam_id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new ResultModel(
                        resultSet.getFloat("diem"),
                        resultSet.getFloat("raw_score"),
                        resultSet.getInt("status"),
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("soCauDung"),
                        resultSet.getInt("soCauSai"),
                        resultSet.getTimestamp("thoiGianNop") != null
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

    //Lây kết quả của một thí sinh tham gia bài thi
    public  ResultModel selectResultUser(int exam_id, String user_id) {
        var query = "SELECT * FROM results WHERE exam_id=? AND user_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam_id);
            ps.setString(2, user_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new ResultModel(
                        resultSet.getFloat("diem"),
                        resultSet.getFloat("raw_score"),
                        resultSet.getInt("status"),
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("soCauDung"),
                        resultSet.getInt("soCauSai"),
                        resultSet.getTimestamp("thoiGianNop") != null
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
        var query = "INSERT INTO results (diem, raw_score, status, exam_id, socaudung, socausai, tgian_nop, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
        var query = "UPDATE results SET diem=?, raw_score=?, status=?, socaudung=?, socausai=?, tgian_nop=? WHERE exam_id=? AND user_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setFloat(1, result.getDiem()); // Điểm
            ps.setFloat(2, result.getRaw_score()); // Điểm thô
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


}
