package QuanLyTracNghiem.BUS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DAO.TopicTestDAO;
import QuanLyTracNghiem.DTO.TopicModel;
import QuanLyTracNghiem.DTO.TopicTestModel;

public class TopicTestBUS {
    private TopicTestDAO topictestdao=new TopicTestDAO();

    public List<TopicModel> selectTopicsInTest(int test_id){
        return topictestdao.selectTopicsInTest(test_id);
    }

    public boolean insert(TopicTestModel topicTest){
        return topictestdao.insert(topicTest);
    }

    public boolean delete(int test_id, int topic_id){
        return topictestdao.delete(test_id, topic_id);
    }

    public void insertListTopic_TestToDB(ArrayList<TopicTestModel> list){
        topictestdao.insertListTopic_TestToDB(list);
    }

}
