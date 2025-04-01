package QuanLyTracNghiem.DAO;

import QuanLyTracNghiem.BUS.QuestionBUS;
import QuanLyTracNghiem.DTO.QuestionModel;
import QuanLyTracNghiem.DTO.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    public Integer finMaxQuestion_Id() {
        String sql = "SELECT MAX(question_id) AS max_question_id FROM question";

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            if (rs.next()) {
                int maxTestId = rs.getInt("max_question_id"); // Xóa khoảng trắng dư thừa
                return rs.wasNull() ? 0 : maxTestId; // Kiểm tra nếu giá trị NULL trong DB
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy question_id lớn nhất: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu không có dữ liệu hoặc có lỗi
    }

    //Thêm câu hỏir
    public boolean addQuestion(String question_content, int question_level, String question_picture, int question_status, int topic_id, int question_id) {
        String sql = "INSERT INTO question (question_content, question_level, question_picture, question_status, topic_id, question_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql)) {
            pre.setString(1, question_content);
            pre.setInt(2, question_level);
            pre.setString(3, question_picture);
            pre.setInt(4, question_status);
            pre.setInt(5, topic_id);
            pre.setInt(6, question_id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi thêm question vào CSDL: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //Sửa câu hỏi
    public boolean editQuestion (String question_content, Integer question_level, String question_picture, Integer question_status, int question_id){
        StringBuilder sql = new StringBuilder("UPDATE question SET ");
        List<String> fields = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        if (question_content != null && !question_content.trim().isEmpty()) {
            fields.add("question_content = ?");
            values.add(question_content);
        }
        if (question_level != null ) {
            fields.add("question_level = ?");
            values.add(question_level);
        }
        if ( question_picture != null && ! question_picture.trim().isEmpty()) {
            fields.add(" question_picture = ?");
            values.add( question_picture);
        }
        if ( question_status != null) {
            fields.add(" question_status = ?");
            values.add( question_status);
        }

        if (fields.isEmpty()) {
            System.out.println("Không có dữ liệu để cập nhật.");
            return false;
        }

        sql.append(String.join(", ", fields)).append(" WHERE question_id = ?;");
        values.add(question_id);

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.size(); i++) {
                pre.setObject(i + 1, values.get(i));
            }
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xảy ra khi sửa user trong CSDL: " + e.getMessage());
            e.printStackTrace();
        }
       return false;
    }
    //Lấy câu hỏi theo theo điều kiện (lấy ngẫu nhiên, dùng tạo bài )

    //Tìm câu hỏi theo điều kiện (dùng khi tìm kiếm )
    public ArrayList<QuestionModel> getQuestionForExam(int solg_cauhoi, int topic_id, int level) {
        ArrayList<QuestionModel> questionList = new ArrayList<>();
        String query = "SELECT question_id FROM question WHERE topic_id = ? AND question_level = ? ORDER BY RAND() LIMIT ?";

        try (PreparedStatement stmt =  MyConnect.conn.prepareStatement(query)) {
            stmt.setInt(1, topic_id);
            stmt.setInt(2, level);
            stmt.setInt(3, solg_cauhoi);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                QuestionModel ques=new QuestionModel();
                ques.setQuestion_id(rs.getInt("question_id"));
                questionList.add(ques);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    //Tìm câu hỏi
    public QuestionModel getQuestion (Integer question_id){
        QuestionModel question= null;
        String sql="SELECT*FROM question WHERE question_id = ?;";
        try (PreparedStatement pst = MyConnect.conn.prepareStatement(sql)){
            pst.setInt(1, question_id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    question = new QuestionModel();
                    //String question_content, Integer question_id, Integer question_level, String question_picture, Integer question_status, Integer topic_id
                    question.setQuestion_content(rs.getString("question_content"));
                    question.setQuestion_id(rs.getInt("question_id"));
                    question.setQuestion_level(rs.getInt("question_level"));
                    question.setQuestion_picture(rs.getString("question_picture"));
                    question.setQuestion_status(rs.getInt("question_status"));
                    question.setTopic_id(rs.getInt("topic_id"));
                }
            }
        }catch(SQLException e){
            System.out.println("Lỗi xảy ra khi lấy thông tin user trong csdl: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return question;
    }

    //Tìm câu hỏi có trong mã đề
    public ArrayList<QuestionModel> getListQuestionInExam(int exam_id) {
        ArrayList<QuestionModel> questionList = new ArrayList<>();
        String sql = "SELECT q.question_id, q.question_content, q.question_level, q.question_picture, q.question_status, q.topic_id " +
                "FROM question q " +
                "JOIN exam_question eq ON q.question_id = eq.question_id " +
                "WHERE eq.exam_id = ?";

        try (PreparedStatement ps = MyConnect.conn.prepareStatement(sql)) {
            ps.setInt(1, exam_id);
            ResultSet rs = ps.executeQuery();
            //String question_content, Integer question_id, Integer question_level, String question_picture, Integer question_status, Integer topic_id
            while (rs.next()) {
                QuestionModel question = new QuestionModel(
                        rs.getString("question_content"),
                        rs.getInt("question_id"),
                        rs.getInt("question_level"),
                        rs.getString ("question_picture"),
                        rs.getInt("question_status"),
                        rs.getInt("topic_id"));
                questionList.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    //Insert một danh sách question

    public void insertListQuestionToDB(ArrayList<QuestionModel> list) {
        String sql = "INSERT INTO question (question_content, question_level, question_picture, question_status, topic_id, question_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = MyConnect.conn.prepareStatement(sql)) {
            MyConnect.conn.setAutoCommit(false); // Bật chế độ batch insert

            for (QuestionModel ques : list) {
                stmt.setString(1, ques.getQuestion_content()); // question_content
                stmt.setInt(2, ques.getQuestion_level()); // question_level
                stmt.setString(3, ques.getQuestion_picture()); // question_picture
                stmt.setInt(4, ques.getQuestion_status()); // question_status
                stmt.setInt(5, ques.getTopic_id()); // topic_id
                stmt.setInt(6, ques.getQuestion_id()); // question_id
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

}
