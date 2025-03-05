package QuanLyTracNghiem.BUS;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DAO.ResultDAO;
import QuanLyTracNghiem.DTO.ResultModel;

public class ResultBUS {
    private ResultDAO resultdao =new ResultDAO();

    public List<ResultModel> selectListKetQuaCuaUserThamGiaTest(Integer exam_id){
        return resultdao.selectListKetQuaCuaUserThamGiaTest(exam_id);
    }

    public  ResultModel selectResultUser(int exam_id, String user_id){
        return resultdao.selectResultUser(exam_id,user_id);
    }

    public  boolean insert(ResultModel result){
        return resultdao.insert(result);
    }

    public  boolean update(ResultModel result){
        return resultdao.insert(result);
    }

}
