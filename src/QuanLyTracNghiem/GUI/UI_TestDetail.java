package QuanLyTracNghiem.GUI;


import QuanLyTracNghiem.BUS.ExamBUS;
import QuanLyTracNghiem.DTO.ExamModel;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

 class UI_TestDetail extends JDialog {
    private CardLayout cardLayout;
    private ExamBUS examBUS=new ExamBUS();
    private ArrayList <ExamModel> list_exam;

    private JPanel mainPanel;
    private String test_id;
    private Integer have_PanelButton;

    public UI_TestDetail (JFrame parent, String test_id, int have_PanelButton) {

        super(parent, "Quản lý thông tin bài test", true);
        setSize(1500 ,1000);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        this.test_id=test_id;
        this.have_PanelButton=have_PanelButton;



        // Màu chủ đạo
        Color pastelBlue = new Color(173, 216, 230); // Xanh pastel
        Color white = Color.black;
        Font font = new Font("Arial", Font.PLAIN, 18);

        // Header - Thanh điều hướng
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        headerPanel.setBackground(pastelBlue);

        JButton btnInfo = createHeaderButton("Thông tin", font, white, pastelBlue);
        JButton btnQuestion = createHeaderButton("Câu hỏi", font, white, pastelBlue);
        JButton btnStudent = createHeaderButton("Sinh viên", font, white, pastelBlue);

        headerPanel.add(btnInfo);
        headerPanel.add(btnQuestion);
        headerPanel.add(btnStudent);
        add(headerPanel, BorderLayout.NORTH);

        // Main - CardLayout để chuyển trang
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(white);

        list_exam=examBUS.selectByTestID(Integer.parseInt(test_id));
        DetailList_User_doTest list_user_doTest=new DetailList_User_doTest(list_exam,have_PanelButton);
        UI_DetailList_Ques_inTest ui_detailList_ques_inTest=new UI_DetailList_Ques_inTest(list_exam,have_PanelButton);
        UI_DetailInformationTest ui_detailInformationTest=new UI_DetailInformationTest(test_id, have_PanelButton);
        JPanel studentPanel = createPanel("Thông tin", font, white);

        mainPanel.add(ui_detailInformationTest, "Thông tin");
        mainPanel.add(list_user_doTest, "Sinh viên");
        mainPanel.add(ui_detailList_ques_inTest, "Câu hỏi");


        add(mainPanel, BorderLayout.CENTER);

        // Xử lý sự kiện chuyển trang
        btnInfo.addActionListener(e -> cardLayout.show(mainPanel, "Thông tin"));
        btnQuestion.addActionListener(e -> cardLayout.show(mainPanel, "Câu hỏi"));
        btnStudent.addActionListener(e -> cardLayout.show(mainPanel, "Sinh viên"));

        setVisible(true);
    }

    private JButton createHeaderButton(String text, Font font, Color fg, Color bg) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setForeground(fg);
        button.setBackground(bg);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createPanel(String text, Font font, Color bgColor) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(Color.BLACK);
        panel.add(label);

        return panel;
    }


}

