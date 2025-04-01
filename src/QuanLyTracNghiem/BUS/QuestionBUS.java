package QuanLyTracNghiem.BUS;

import QuanLyTracNghiem.DAO.QuestionDAO;
import QuanLyTracNghiem.DTO.QuestionModel;
import QuanLyTracNghiem.DTO.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionBUS {
    private QuestionDAO questiondao=new QuestionDAO();
    public Integer finMaxQuestion_Id(){
        return questiondao.finMaxQuestion_Id();
    }
    public boolean addQuestion(String question_content, int question_level, String question_picture, int question_status, int topic_id, int question_id){
        return questiondao.addQuestion( question_content, question_level, question_picture, question_status,  topic_id, question_id);
    }

    public boolean editQuestion (String question_content, Integer question_level, String question_picture, Integer question_status, int question_id){
        return questiondao.editQuestion ( question_content,  question_level,question_picture,  question_status, question_id);
    }

    public ArrayList<QuestionModel> getQuestionForExam(int solg_cauhoi, int topic_id, int level){
        return questiondao.getQuestionForExam(solg_cauhoi,topic_id,level);
    }

    public QuestionModel getQuestion (Integer question_id){
        return questiondao.getQuestion(question_id);
    }

    public ArrayList<QuestionModel> getListQuestionInExam(int exam_id){
        return questiondao.getListQuestionInExam(exam_id);
    }

    public void insertListQuestionToDB(ArrayList<QuestionModel> list){
        questiondao.insertListQuestionToDB(list);
    }
}
