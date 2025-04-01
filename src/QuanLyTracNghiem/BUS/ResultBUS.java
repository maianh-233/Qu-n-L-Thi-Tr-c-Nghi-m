package QuanLyTracNghiem.BUS;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DAO.ResultDAO;
import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.ResultModel;
import QuanLyTracNghiem.DTO.UserModel;

public class ResultBUS {
    private ResultDAO resultdao =new ResultDAO();

    public ArrayList<UserModel> selectListInfoCuaUserThamGiaTest(ArrayList<ExamModel> list_exam){
        return resultdao.selectListInfoCuaUserThamGiaTest(list_exam);
    }

    public ArrayList<ResultModel> selectListKetQuaCuaUserThamGiaTest(ArrayList<ExamModel> list_exam){
        return resultdao.selectListKetQuaCuaUserThamGiaTest(list_exam);
    }

    public  ResultModel selectResultUser(int exam_id, String user_id){
        return resultdao.selectResultUser(exam_id,user_id);
    }

    public  boolean insert(ResultModel result){
        return resultdao.insert(result);
    }

    public  boolean update(ResultModel result){
        return resultdao.update(result);
    }

    public void insertListResultToDB(ArrayList<ResultModel> list){
        resultdao.insertListResultToDB(list);
    }

    public ResultModel selectResultsForUser(ArrayList<ExamModel> examIds, String userId){
        return resultdao.selectResultsForUser( examIds, userId);
    }
}
