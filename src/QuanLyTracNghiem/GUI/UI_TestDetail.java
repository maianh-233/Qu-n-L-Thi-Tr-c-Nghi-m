package QuanLyTracNghiem.GUI;


import java.awt.*;
import javax.swing.*;

 class UI_TestDetail extends JDialog {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public UI_TestDetail (JFrame parent) {

        super(parent, "Quản lý thông tin bài test", true);
        setSize(1500, 1000);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());



        // Màu chủ đạo
        Color pastelBlue = new Color(173, 216, 230); // Xanh pastel
        Color white = Color.WHITE;
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

        DetailList_User_doTest list_user_doTest=new DetailList_User_doTest();
        UI_DetailList_Ques_inTest ui_detailList_ques_inTest=new UI_DetailList_Ques_inTest();
        UI_DetailInformationTest ui_detailInformationTest=new UI_DetailInformationTest();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);

            JButton openDialogButton = new JButton("Mở Dialog");
            openDialogButton.addActionListener(e -> new UI_TestDetail(frame).setVisible(true));

            frame.setLayout(new FlowLayout());
            frame.add(openDialogButton);
            frame.setVisible(true);
        });
    }
}

