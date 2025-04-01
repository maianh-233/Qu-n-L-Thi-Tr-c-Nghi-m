package QuanLyTracNghiem.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.ExamQuestionModel;

public class ExamDAO {


    //Lấy tất cả các mã đề của bài test
    public ArrayList<ExamModel> selectByTestID(int test_id) {
        var query = "SELECT * FROM exam WHERE test_id=?";
        ArrayList<ExamModel> exams = new ArrayList<>();

        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, test_id);
            var resultSet = ps.executeQuery();

            while (resultSet.next()) { // Duyệt từng dòng dữ liệu
                exams.add(new ExamModel(
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("test_id"),
                        resultSet.getString("exam_code"),
                        resultSet.getInt("passing_score"),
                        resultSet.getInt("total_score")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exams; // Trả về danh sách (có thể rỗng)
    }
    public ExamModel selectByExamID(int exam_id) {
        var query = "SELECT * FROM exam WHERE exam_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new ExamModel(
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("test_id"),
                        resultSet.getString("exam_code"),
                        resultSet.getInt("passing_score"),
                        resultSet.getInt("total_score"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    //Thêm mã đề vào bài thi
    public boolean insert(ExamModel exam) {
        var query = "INSERT INTO exam (test_id, exam_code, passing_score, total_score,exam_id) VALUES (?, ?, ?, ?, ?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam.getTest_id());
            ps.setString(2, exam.getExam_code());
            ps.setInt(3, exam.getPassing_score());
            ps.setInt(4, exam.getTotal_score());
            ps.setInt(5,exam.getExam_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Inset một list exam
    public void insertListExamToDB(ArrayList<ExamModel> list) {
        String sql =  "INSERT INTO exam (test_id, exam_code, passing_score, total_score,exam_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = MyConnect.conn.prepareStatement(sql)) {
            MyConnect.conn.setAutoCommit(false); // Bật chế độ batch insert

            for (ExamModel exam: list) {
                stmt.setInt(1, exam.getTest_id());
                stmt.setString(2, exam.getExam_code());
                stmt.setInt(3, exam.getPassing_score());
                stmt.setInt(4, exam.getTotal_score());
                stmt.setInt(5,exam.getExam_id());
                stmt.addBatch(); // Thêm vào batch
            }

            int[] result = stmt.executeBatch(); // Chạy batch insert
            MyConnect.conn.commit(); // Xác nhận dữ liệu

            System.out.println("Đã chèn " + result.length + " bản ghi vào Exam.");

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

    //Lấy max exam_id
    public int findMaxExamId() {
        int maxExamId = 0; // Giá trị mặc định nếu bảng trống
        String query = "SELECT MAX(exam_id) FROM exam";
        try (PreparedStatement pstmt = MyConnect.conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                maxExamId = rs.getInt(1); // Lấy giá trị lớn nhất của exam_id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxExamId;
    }

}
