package QuanLyTracNghiem.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DTO.QuestionModel;
import QuanLyTracNghiem.DTO.TopicModel;
import QuanLyTracNghiem.DTO.TopicTestModel;

public class TopicTestDAO {
    //Lấy các chủ đề có trong một bài test
    public List<TopicModel> selectTopicsInTest(int test_id) {
        List<TopicModel> topics = new ArrayList<>();
        var query = "SELECT t.topic_id, t.topic_name " +
                "FROM topic t " +
                "JOIN topic_test tt ON t.topic_id = tt.topic_id " +
                "WHERE tt.test_id = ?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, test_id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                topics.add(new TopicModel(
                        resultSet.getInt("topic_id"),
                        resultSet.getString("topic_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topics;
    }


    //Thêm một topic vô bài test
    public boolean insert(TopicTestModel topicTest) {
        var query = "INSERT INTO topic_test (test_id, topic_id) VALUES (?, ?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, topicTest.getTest_id());
            ps.setInt(2, topicTest.getTopic_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Xóa topic
    public boolean delete(int test_id, int topic_id) {
        var query = "DELETE FROM topic_test WHERE test_id=? AND topic_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, test_id);
            ps.setInt(2, topic_id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Insert một list topictest
    public void insertListTopic_TestToDB(ArrayList<TopicTestModel> list) {
        String sql = "INSERT INTO topic_test (test_id, topic_id) VALUES (?, ?)";

        try (PreparedStatement stmt = MyConnect.conn.prepareStatement(sql)) {
            MyConnect.conn.setAutoCommit(false); // Bật chế độ batch insert

            for (TopicTestModel ques : list) {
                stmt.setInt(1, ques.getTest_id()); // topic_id
                stmt.setInt(2, ques.getTopic_id()); // question_id
                stmt.addBatch(); // Thêm vào batch
            }

            int[] result = stmt.executeBatch(); // Chạy batch insert
            MyConnect.conn.commit(); // Xác nhận dữ liệu

            System.out.println("Đã chèn " + result.length + " bản ghi vào TopicTest.");

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
