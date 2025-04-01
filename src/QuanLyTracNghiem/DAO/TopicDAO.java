package QuanLyTracNghiem.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DTO.TopicModel;

public class TopicDAO {
    //Lây tất cả các topic
    public ArrayList<TopicModel> selectAll() {
        var list = new ArrayList<TopicModel>();
        var query = "SELECT * FROM topic";
        try (var statement = MyConnect.conn.createStatement()) {
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(new TopicModel(
                        resultSet.getInt("topic_id"),
                        resultSet.getString("topic_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //Lâý thông tin của một topic bất kì
    public TopicModel selectByID(int topic_id) {
        var query = "SELECT * FROM topic WHERE topic_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setInt(1, topic_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new TopicModel(
                        resultSet.getInt("topic_id"),
                        resultSet.getString("topic_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Thêm một topic vô hệ thông -> topic_id tự tăng
    public  boolean insert(TopicModel topic) {
        var query = "INSERT INTO topic (topic_name, topic_id) VALUES (?,?)";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setString(1, topic.getTopic_name());
            ps.setInt(2, topic.getTopic_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Chỉnh sửa thông tin của một topic
    public  boolean update(TopicModel topic) {
        var query = "UPDATE topics SET topic_name=? WHERE topic_id=?";
        try (var ps = MyConnect.conn.prepareStatement(query)) {
            ps.setString(1, topic.getTopic_name());
            ps.setInt(2, topic.getTopic_id());
    
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Tìm topic_id lớn nhất
    public Integer finMaxTopicId() {
        String sql = "SELECT MAX(topic_id) AS max_topic_id FROM topic";

        try (PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            if (rs.next()) {
                int maxTestId = rs.getInt("max_topic_id");
                return rs.wasNull() ? 0 : maxTestId; // Kiểm tra nếu giá trị NULL trong DB
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy tạo topic_id lớn nhất: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // Trả về null nếu không có dữ liệu hoặc có lỗi
    }


}
