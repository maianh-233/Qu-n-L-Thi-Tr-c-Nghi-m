package QuanLyTracNghiem.DAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DTO.UserChooseModel;

public class UserChooseDAO {
    //Lấy đáp án của người dùng
    public UserChooseModel selectAnswer(int exam_question_id, String user_id) {
        var query = "SELECT * FROM user_choices WHERE exam_question_id=? AND user_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam_question_id);
            ps.setString(2, user_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new UserChooseModel(
                        resultSet.getInt("answer_id"),
                        resultSet.getInt("exam_question_id"),
                        resultSet.getString("user_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Thêm câu trả lời của người dùng
    public boolean insert(UserChooseModel userChoice) {
        var query = "INSERT INTO user_choices (answer_id, exam_question_id, user_id) VALUES (?, ?, ?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, userChoice.getAnswer_id());
            ps.setInt(2, userChoice.getExam_question_id());
            ps.setString(3, userChoice.getUser_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Chỉnh sửa câu trả lời
    public boolean update(UserChooseModel userChoice) {
        var query = "UPDATE user_choices SET answer_id=? WHERE exam_question_id=? AND user_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, userChoice.getAnswer_id());
            ps.setInt(2, userChoice.getExam_question_id());
            ps.setString(3, userChoice.getUser_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Xóa sự lựa chọn
    public boolean delete(int exam_question_id, String user_id) {
        var query = "DELETE FROM user_choices WHERE exam_question_id=? AND user_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, exam_question_id);
            ps.setString(2, user_id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
