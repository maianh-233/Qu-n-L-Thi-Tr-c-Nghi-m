package QuanLyTracNghiem.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DTO.TopicTestModel;

public class TopicTestDAO {
    //Lấy các chủ đề có trong một bài test
    public TopicTestModel selectTopicInTest(int test_id) {
        var query = "SELECT * FROM topic_tests WHERE test_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, test_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new TopicTestModel(
                        resultSet.getInt("test_id"),
                        resultSet.getInt("topic_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Thêm một topic vô bài test
    public boolean insert(TopicTestModel topicTest) {
        var query = "INSERT INTO topic_tests (test_id, topic_id) VALUES (?, ?)";
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
        var query = "DELETE FROM topic_tests WHERE test_id=? AND topic_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, test_id);
            ps.setInt(2, topic_id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
