package QuanLyTracNghiem.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DTO.ExamModel;

public class ExamDAO {


    //Lấy tất cả các mã đề của bài test
    public ArrayList<ExamModel> selectByTestID(int test_id) {
        var query = "SELECT * FROM exams WHERE test_id=?";
        ArrayList<ExamModel> exams = new ArrayList<>();

        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, test_id);
            var resultSet = ps.executeQuery();

            while (resultSet.next()) { // Duyệt từng dòng dữ liệu
                exams.add(new ExamModel(
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("test_id"),
                        resultSet.getString("exam_codde"),
                        resultSet.getFloat("passing_code"),
                        resultSet.getFloat("total_score")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exams; // Trả về danh sách (có thể rỗng)
    }
    public ExamModel selectByExamID(int exam_id) {
        var query = "SELECT * FROM exams WHERE exam_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new ExamModel(
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("test_id"),
                        resultSet.getString("exam_codde"),
                        resultSet.getFloat("passing_code"),
                        resultSet.getFloat("total_score"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    //Thêm mã đề vào bài thi
    public boolean insert(ExamModel exam) {
        var query = "INSERT INTO exams (test_id, exam_code, passing_score, total_score,exam_id) VALUES (?, ?, ?, ?, ?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam.getTest_id());
            ps.setString(2, exam.getExam_code());
            ps.setFloat(3, exam.getPassing_score());
            ps.setFloat(4, exam.getTotal_score());
            ps.setInt(5,exam.getExam_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
