package QuanLyTracNghiem.BUS;

import QuanLyTracNghiem.DAO.UserDAO;
import QuanLyTracNghiem.DTO.UserModel;

import java.util.ArrayList;

public class UserBUS {
private UserDAO userdao=new UserDAO();
    public UserModel getUser (String user_id, String password){
        return userdao.getUser(user_id.trim(), password.trim());
    }

    public boolean addUser (UserModel user){
        return userdao.addUser(user.getEmail(),user.getIsAdmin(),user.getPassword(),user.getPhone(),user.getFullname(),user.getUser_id());
    }

    public String generateUserId(String user_name){
        return userdao.generateUserId(user_name);
    }

    public boolean editUser(String email,String fullname, String password, String phone, String user_id){
        return userdao.editUser(email, fullname,password, phone, user_id);
    }

    public UserModel getInfo (String user_id){
        return userdao.getInfo(user_id);
    }

    public ArrayList<UserModel> getListUserDoExam(int exam_id){
        return userdao.getListUserDoExam(exam_id);
    }

    public ArrayList<UserModel> getExistingUsersFromDB (ArrayList<UserModel> list_user){
        return userdao.getExistingUsersFromDB(list_user);
    }
}
