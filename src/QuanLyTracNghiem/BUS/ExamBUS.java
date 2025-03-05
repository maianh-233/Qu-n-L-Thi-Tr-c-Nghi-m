package QuanLyTracNghiem.BUS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QuanLyTracNghiem.DAO.ExamDAO;
import QuanLyTracNghiem.DTO.ExamModel;

public class ExamBUS {
    private ExamDAO examdao=new ExamDAO();

    public ExamModel selectByExamID(int exam_id){
        return examdao.selectByExamID(exam_id);
    }

    public ArrayList<ExamModel> selectByTestID(int test_id){
        return examdao.selectByTestID(test_id);
    }
    public boolean insert(ExamModel exam){
        return examdao.insert(exam);
    }

}
