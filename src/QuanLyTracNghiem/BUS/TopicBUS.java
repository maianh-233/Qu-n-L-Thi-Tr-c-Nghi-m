package QuanLyTracNghiem.BUS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DAO.TopicDAO;
import QuanLyTracNghiem.DTO.TopicModel;

public class TopicBUS {
    private TopicDAO topicdao=new TopicDAO();

    public List<TopicModel> selectAll(){
        return topicdao.selectAll();
    }

    public  boolean insert(TopicModel topic){
        return topicdao.insert(topic);
    }

    public  boolean update(TopicModel topic){
        return topicdao.update(topic);
    }

    public Integer finMaxTopicId(){
        return topicdao.finMaxTopicId();
    }

}
