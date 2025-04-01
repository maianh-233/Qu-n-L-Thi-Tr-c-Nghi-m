package QuanLyTracNghiem.DAO;

import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.QuestionModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AnswerDAO {
    //Lấy tât cả câu trả lời trong exam

    public ArrayList<AnswerModel> getListAnswerInExam (ArrayList<ExamModel> list_exam){
        ArrayList<AnswerModel>list=new ArrayList<>();
        String sql = "SELECT a.* FROM answer a " +
                "JOIN exam_question eq ON a.question_id = eq.question_id " +
                "WHERE eq.exam_id IN (" +
                String.join(",", Collections.nCopies(list_exam.size(), "?")) + ")";
        try(var ps = MyConnect.conn.prepareStatement(sql)) {
            for (int i = 0; i < list_exam.size(); i++) {
                ps.setInt(i + 1, list_exam.get(i).getExam_id());
            }
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

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    //Lấy các câu trả lời của một câu hỏi
    public  ArrayList<AnswerModel> selectAnswerByQues_id(int question_id) {
        var list = new ArrayList<AnswerModel>();
        var query = "SELECT * FROM answer WHERE question_id=?";
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
        var query = "INSERT INTO answer (answer_content, answer_picture, status, isRight, question_id,answer_id) VALUES (?, ?, ?, ?, ?,?)";
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
        var query = "UPDATE answer SET answer_content=?, answer_picture=?, status=?, isRight=?, question_id=? WHERE answer_id=?";
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
        return 0; // Trả về -1 nếu không tìm thấy giá trị nào
    }

//Insert mộ list Answer
public void insertListAnswerToDB(ArrayList<AnswerModel> list) {
    String sql = "INSERT INTO answer (answer_content, answer_picture, status, isRight, question_id, answer_id) VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = MyConnect.conn.prepareStatement(sql)) {
        MyConnect.conn.setAutoCommit(false); // Bật chế độ batch insert

        for (AnswerModel ans : list) {
            stmt.setString(1, ans.getAnswer_content()); // answer_content
            stmt.setString(2, ans.getAnswer_picture()); // answer_picture
            stmt.setInt(3, ans.getStatus()); // status
            stmt.setInt(4, ans.getIsRight()); // isRight
            stmt.setInt(5, ans.getQuestion_id()); // question_id
            stmt.setInt(6, ans.getAnswer_id()); // answer_id
            stmt.addBatch(); // Thêm vào batch
        }

        int[] result = stmt.executeBatch(); // Chạy batch insert
        MyConnect.conn.commit(); // Xác nhận dữ liệu

        System.out.println("Đã chèn " + result.length + " bản ghi vào hệ thống.");

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


    public ArrayList<ArrayList<AnswerModel>> getUserAnswers(int exam_id, String user_id, ArrayList<QuestionModel> questions) {
        ArrayList<ArrayList<AnswerModel>> userAnswersList = new ArrayList<>();

        String query = "SELECT a.answer_id, a.answer_content, a.answer_picture, a.status, a.isRight, a.question_id " +
                "FROM userchoose uc " +
                "JOIN exam_question eq ON uc.exam_question_id = eq.exam_question_id " +
                "JOIN answer a ON uc.answer_id = a.answer_id " +
                "WHERE eq.exam_id = ? AND uc.user_id = ?";

        try (PreparedStatement stmt = MyConnect.conn.prepareStatement(query)) {
            stmt.setInt(1, exam_id);
            stmt.setString(2, user_id);

            try (ResultSet rs = stmt.executeQuery()) {
                Map<Integer, ArrayList<AnswerModel>> answerMap = new HashMap<>();

                while (rs.next()) {
                    //(String answer_content, Integer answer_id, String answer_picture, Integer status, Integer isRight, Integer question_id)
                    AnswerModel answer = new AnswerModel(
                            rs.getString("answer_content"),
                            rs.getInt("answer_id"),
                            rs.getString("answer_picture"),
                            rs.getInt("status"),
                            rs.getInt("isRight"),
                            rs.getInt("question_id")
                    );

                    answerMap.computeIfAbsent(answer.getQuestion_id(), k -> new ArrayList<>()).add(answer);
                }

                for (QuestionModel q : questions) {
                    userAnswersList.add(answerMap.getOrDefault(q.getQuestion_id(), new ArrayList<>()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userAnswersList;
    }


}
