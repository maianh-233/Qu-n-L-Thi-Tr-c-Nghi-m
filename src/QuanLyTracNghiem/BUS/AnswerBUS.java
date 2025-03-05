package QuanLyTracNghiem.BUS;

import QuanLyTracNghiem.DAO.AnswerDAO;
import QuanLyTracNghiem.DTO.AnswerModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerBUS {
    private AnswerDAO answerDAO=new AnswerDAO();

    public  List<AnswerModel> selectAnswerByQues_id(int question_id){
        return answerDAO.selectAnswerByQues_id(question_id);
    }

    public  boolean insert(AnswerModel answer){
        return answerDAO.insert(answer);
    }

    public boolean update(AnswerModel answer){
        return answerDAO.update(answer);
    }
    public int getMaxAnswerId(){
        return answerDAO.getMaxAnswerId();
    }

}
