package QuanLyTracNghiem.BUS;

import QuanLyTracNghiem.DAO.MyConnect;
import QuanLyTracNghiem.DAO.TestDAO;
import QuanLyTracNghiem.DAO.TopicDAO;
import QuanLyTracNghiem.DTO.TestModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestBUS {
    private TestDAO testdao=new TestDAO();

    public boolean addTest(String create_by, int luotlambai, LocalDateTime ngaybd_thi, int socaude,
                           int socauhoi,int socaukho, int socauthuong, int solgmade, int test_id,
                           String test_name,int test_status ,int tgianlambai) {
        return testdao.addTest(create_by,luotlambai, ngaybd_thi,socaude, socauhoi, socaukho,  socauthuong, solgmade,  test_id, test_name, test_status ,tgianlambai);

    }

    //Tìm kiếm Test


    //Sửa bài test (luotlanbai, ngaybd_thi,test_name, tgianlambai)
    public boolean editTest( LocalDateTime ngaybd_thi, String test_name, Integer tgianlambai, int test_id) {
        return testdao.editTest(  ngaybd_thi,  test_name, tgianlambai,  test_id);
    }

    //Tìm test_id lớn nhất
    public Integer finMaxTestId() {
        return testdao.finMaxTestId();
    }

    // Lấy danh sách bài kiểm tra đã xảy ra
    public ArrayList<TestModel> getPastTestsByCreator(String create_by){
        return testdao.getPastTestsByCreator(create_by);
    }


    // Lấy danh sách bài kiểm tra chưa xảy ra
    public ArrayList<TestModel> getUpcomingTestsByCreator(String create_by){
        return testdao.getUpcomingTestsByCreator(create_by);
    }

    public TestModel findTestById(int test_id){
        return testdao.findTestById(test_id);
    }

}
