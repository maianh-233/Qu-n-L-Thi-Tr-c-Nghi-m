package QuanLyTracNghiem.DAO;

import QuanLyTracNghiem.DTO.AnswerModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    //Lấy các câu trả lời của một câu hỏi
    public  List<AnswerModel> selectAnswerByQues_id(int question_id) {
        var list = new ArrayList<AnswerModel>();
        var query = "SELECT * FROM answers WHERE question_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, question_id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new AnswerModel(
                        resultSet.getString("answer_content"),
                        resultSet.getInt("answer_id"),
                        resultSet.getString("answer_picture"),
                        resultSet.getInt("status"),
                        resultSet.getInt("isRight"),
                        resultSet.getInt("question_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    //Thêm câu trả lời cho một câu hỏi
    public  boolean insert(AnswerModel answer) {
        var query = "INSERT INTO answers (answer_content, answer_picture, status, isRight, question_id,answer_id) VALUES (?, ?, ?, ?, ?,?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setString(1, answer.getAnswer_content());
            ps.setString(2, answer.getAnswer_picture());
            ps.setInt(3, answer.getStatus());
            ps.setInt(4, answer.getIsRight());
            ps.setInt(5, answer.getQuestion_id());
            ps.setInt(6, answer.getAnswer_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Sửa chữa câu trả lời cho câu hỏi
    public boolean update(AnswerModel answer) {
        var query = "UPDATE answers SET answer_content=?, answer_picture=?, status=?, isRight=?, question_id=? WHERE answer_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setString(1, answer.getAnswer_content());
            ps.setString(2, answer.getAnswer_picture());
            ps.setInt(3, answer.getStatus());
            ps.setInt(4, answer.getIsRight());
            ps.setInt(5, answer.getQuestion_id());
            ps.setInt(6, answer.getAnswer_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Phương thức lấy exam_id lớn nhất trong
    // Lấy exam_id lớn nhất
    public int getMaxAnswerId() {
        var query = "SELECT MAX(answer_id) AS max_id FROM answer";
        try (var ps = MyConnect.conn.prepareStatement(query);
             var rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // Trả về -1 nếu không tìm thấy giá trị nào
    }



}
