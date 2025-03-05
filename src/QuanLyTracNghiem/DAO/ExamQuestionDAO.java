package QuanLyTracNghiem.DAO;

import QuanLyTracNghiem.DTO.ExamQuestionModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamQuestionDAO {
    //Lấy danh sách các câu hỏi trong mã đề
    public  List<ExamQuestionModel> selectByExamQuesID(int exam_id) {
        var list = new ArrayList<ExamQuestionModel>();
        var query = "SELECT * FROM exam_questions WHERE exam_id=?";
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
        var query = "INSERT INTO exam_questions (exam_id, question_id) VALUES (?, ?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, examQuestion.getExam_id());
            ps.setInt(2, examQuestion.getQuestion_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
