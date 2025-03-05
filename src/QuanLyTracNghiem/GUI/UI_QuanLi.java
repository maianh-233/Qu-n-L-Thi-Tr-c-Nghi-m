package QuanLyTracNghiem.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class UI_Quanli extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JButton btn_Qli_ListTest;
    private JButton btn_CreateTest;
    private JFrame mainframe;

    public UI_Quanli(JFrame mainframe) {
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

        btn_Qli_ListTest = createHeaderButton("Danh sách bài thi");
        btn_CreateTest = createHeaderButton("Tạo bài thi mới");

        headerPanel.add(btn_Qli_ListTest);
        headerPanel.add(btn_CreateTest);

        add(headerPanel, BorderLayout.NORTH);

        // Tạo main panel với CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Thêm trang Danh sách các bài thi
        UI_ListTest uiListTest = new UI_ListTest(mainframe);
        mainPanel.add(uiListTest, "1");

        add(mainPanel, BorderLayout.CENTER); // Cần thêm mainPanel vào UI_Quanli

        btn_Qli_ListTest.addActionListener(e -> cardLayout.show(mainPanel, "1"));
        btn_CreateTest.addActionListener(e -> new ExamCreationDialog(mainframe)); // Hiển thị dialog tạo bài thi
    }

    private JButton createHeaderButton(String btn_name) {
        JButton btn = new JButton(btn_name);
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        return btn;
    }

    // Hàm để test UI_Quanli độc lập
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý bài thi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1600, 1000);
            frame.setLocationRelativeTo(null);
            frame.add(new UI_Quanli(frame));
            frame.setVisible(true);
        });
    }
}
