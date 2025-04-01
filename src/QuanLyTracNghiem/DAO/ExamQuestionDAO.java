package QuanLyTracNghiem.DAO;

import QuanLyTracNghiem.DTO.ExamQuestionModel;
import QuanLyTracNghiem.DTO.TopicTestModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamQuestionDAO {
    //Lấy danh sách các câu hỏi trong mã đề
    public  ArrayList<ExamQuestionModel> selectByExamQuesID(int exam_id) {
        var list = new ArrayList<ExamQuestionModel>();
        var query = "SELECT * FROM exam_question WHERE exam_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam_id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new ExamQuestionModel(
                        resultSet.getInt("exam_id"),
                        resultSet.getInt("exam_question_id"),
                        resultSet.getInt("question_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //Thêm câu hỏi vô mã đề thi
    public  boolean insert(ExamQuestionModel examQuestion) {
        var query = "INSERT INTO exam_question (exam_id, question_id) VALUES (?, ?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, examQuestion.getExam_id());
            ps.setInt(2, examQuestion.getQuestion_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Insert một arraylist examquestion
    public void insertListExam_QuestionToDB(ArrayList<ExamQuestionModel> list) {
        String sql = "INSERT INTO exam_question (exam_id, question_id) VALUES (?, ?)"; //

        try (PreparedStatement stmt = MyConnect.conn.prepareStatement(sql)) {
            MyConnect.conn.setAutoCommit(false); // Bật chế độ batch insert

            for (ExamQuestionModel ques : list) {
                stmt.setInt(1, ques.getExam_id()); // exam_id
                stmt.setInt(2, ques.getQuestion_id()); // question_id
                stmt.addBatch(); // Thêm vào batch
            }

            int[] result = stmt.executeBatch(); // Chạy batch insert
            MyConnect.conn.commit(); // Xác nhận dữ liệu

            System.out.println("Đã chèn " + result.length + " bản ghi vào Exam_Question.");

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


    public Integer finMaxExamQuestionId() {
        String sql = "SELECT MAX(exam_question_id) AS max_exam_question_id FROM exam_question";

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            if (rs.next()) {
                int maxTestId = rs.getInt("max_exam_question_id");
                return rs.wasNull() ? 0 : maxTestId; // Kiểm tra nếu giá trị NULL trong DB
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy id lớn nhất: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // Trả về null nếu không có dữ liệu hoặc có lỗi
    }

}
