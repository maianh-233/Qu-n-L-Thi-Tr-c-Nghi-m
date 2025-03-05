package QuanLyTracNghiem.BUS;

import QuanLyTracNghiem.DAO.ExamQuestionDAO;
import QuanLyTracNghiem.DTO.ExamQuestionModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamQuestionBUS {
    private ExamQuestionDAO examQuestiondao=new ExamQuestionDAO();

    public  List<ExamQuestionModel> selectByExamQuesID(int exam_id){
        return examQuestiondao.selectByExamQuesID(exam_id);
    }

    public  boolean insert(ExamQuestionModel examQuestion){
        return examQuestiondao.insert(examQuestion);
    }
}
