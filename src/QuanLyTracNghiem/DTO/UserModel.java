package QuanLyTracNghiem.DTO;

public class UserModel {
    private String email;
    private String password;
    private String phone;

    private String user_id;
    private String fullname;
    private Integer isAdmin;

    // Constructor
    public UserModel( String email, String password, String phone,  String user_id, String user_name, Integer isAdmin) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.user_id = user_id;
        this.fullname = user_name;
        this.isAdmin = isAdmin;
    }

    public UserModel(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
