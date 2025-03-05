package Main;

import QuanLyTracNghiem.DAO.MyConnect;
import QuanLyTracNghiem.GUI.DangNhapGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Đặt Look and Feel trước khi khởi tạo giao diện
        changLNF("Nimbus");

        new MyConnect();
        DangNhapGUI login = new DangNhapGUI();
        login.showWindow();
    }

    public static void changLNF(String nameLNF) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (nameLNF.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    // Cập nhật lại giao diện sau khi đổi Look and Feel
                    SwingUtilities.updateComponentTreeUI(null);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}