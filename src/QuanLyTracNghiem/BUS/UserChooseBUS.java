package QuanLyTracNghiem.BUS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DAO.UserChooseDAO;
import QuanLyTracNghiem.DTO.UserChooseModel;

public class UserChooseBUS {
    private UserChooseDAO userchoosedao=new UserChooseDAO();

    public  UserChooseModel selectAnswer(int exam_question_id, String user_id){
        return userchoosedao.selectAnswer(exam_question_id, user_id);
    }

    public boolean insert(UserChooseModel userChoice){
        return userchoosedao.insert(userChoice);
    }

    public boolean update(UserChooseModel userChoice){
        return userchoosedao.update(userChoice);
    }

    public boolean delete(int exam_question_id, String user_id){
        return userchoosedao.delete(exam_question_id,user_id);
    }
    public boolean insertBatch(ArrayList<UserChooseModel> userChoices){
        return userchoosedao.insertBatch(userChoices);
    }

}
