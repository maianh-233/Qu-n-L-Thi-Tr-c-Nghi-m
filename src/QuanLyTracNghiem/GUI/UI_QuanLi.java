package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.DTO.UserModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class UI_Quanli extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;


    private JButton btn_CreateTest;
    private JFrame mainframe;

    private UserModel login_user;
    public UI_Quanli(JFrame mainframe, UserModel login_user) {
        this.login_user=login_user;
        this.mainframe = mainframe;
        addControls();
    }

    public void addControls() {
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1500, 900));

        // Tạo header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(1500, 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.decode("#B0F2FA"));


        btn_CreateTest = createHeaderButton("Tạo bài thi mới");

        headerPanel.add(btn_CreateTest);

        add(headerPanel, BorderLayout.NORTH);

        // Tạo main panel với CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Thêm trang Danh sách các bài thi
        UI_ListTest uiListTest = new UI_ListTest(mainframe,login_user);
        mainPanel.add(uiListTest, "1");

        add(mainPanel, BorderLayout.CENTER); // Cần thêm mainPanel vào UI_Quanli


        btn_CreateTest.addActionListener(e -> new ExamCreationDialog(mainframe,login_user)); // Hiển thị dialog tạo bài thi
    }

    private JButton createHeaderButton(String btn_name) {
        JButton btn = new JButton(btn_name);
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        return btn;
    }

    // Hàm để test UI_Quanli độc lập

}
