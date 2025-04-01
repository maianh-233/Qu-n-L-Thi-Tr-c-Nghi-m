package QuanLyTracNghiem.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnect {
    public static Connection conn = null;
    private static final String SERVER_NAME = "localhost";
    private static final String DB_NAME = "db_azota";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final int PORT = 3306;

    public MyConnect() {
        String strConnect = "jdbc:mysql://" + SERVER_NAME + ":" + PORT + "/" + DB_NAME
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh";


        Properties pro = new Properties();
        pro.put("user", USER_NAME);
        pro.put("password", PASSWORD);

        try {
            // Đảm bảo driver đã được tải (có thể bỏ nếu dùng phiên bản mới)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Kết nối đến MySQL
            conn = DriverManager.getConnection(strConnect, pro);
            System.out.println("Kết nối đến CSDL thành công");
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver MySQL!");
            e.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Kết nối đến CSDL không thành công");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyConnect(); // Kiểm tra kết nối
    }
}
