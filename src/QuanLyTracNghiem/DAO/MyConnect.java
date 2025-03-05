package QuanLyTracNghiem.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnect {
    public static Connection conn = null;
    private static String serverName = "localhost"; // Đã sửa chính tả
    private static String dbName = "quanlytracnghiem";
    private static String userName = "root";
    private static String password = "";

    public MyConnect() {
        String strConnect = "jdbc:mysql://" + serverName + ":3306/" + dbName; // Kiểm tra lại cổng
        Properties pro = new Properties();
        pro.put("user", userName);
        pro.put("password", password);

        try {
            conn = DriverManager.getConnection(strConnect, pro);
            System.out.println("Kết nối đến CSDL thành công");
        } catch (SQLException ex) {
            System.out.println("Kết nối đến CSDL không thành  công");
            // JOptionPane.showMessageDialog(null, "Không kết nối được tới CSDL!" +
            // ex.getMessage(), "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            // System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MyConnect(); // Tạo một đối tượng để kiểm tra kết nối
    }
}