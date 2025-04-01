package QuanLyTracNghiem.BUS;

import QuanLyTracNghiem.DAO.AnswerDAO;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.QuestionModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerBUS {
    private AnswerDAO answerDAO=new AnswerDAO();

    public ArrayList<AnswerModel> getListAnswerInExam (ArrayList<ExamModel> list_exam){
        return answerDAO.getListAnswerInExam(list_exam);
    }

    public  ArrayList<AnswerModel> selectAnswerByQues_id(int question_id){
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

    public void insertListAnswerToDB(ArrayList<AnswerModel> list){
        answerDAO.insertListAnswerToDB(list);
    }

    public ArrayList<ArrayList<AnswerModel>> getUserAnswers(int exam_id, String user_id, ArrayList<QuestionModel> questions){
        return answerDAO.getUserAnswers(exam_id, user_id, questions);
    }

}
