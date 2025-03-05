package QuanLyTracNghiem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIMainRoleThiSinh extends JFrame {
    private JButton btn_innfor;
    private JButton btn_dotest;
    private JPanel centerPanel; // Panel chứa CardLayout
    private CardLayout cardLayout; // CardLayout để chuyển đổi giữa các panel

    private JButton btn_dangxuat;
    private ThongTinUI thongTinUI;

    public void showUI(){
        // Thiết lập JFrame
        this.setTitle("Giao diện của thí sinh");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    public UIMainRoleThiSinh() {
        // Thêm các thành phần giao diện
        addControls();
        addEvents();

    }

    private void addControls() {
        //Header
        // Tạo JLabel làm header
        JLabel headerLabel = new JLabel("Chào mừng thí sinh đến với ứng dụng của chúng tôi", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Chữ to hơn
        headerLabel.setForeground(Color.BLUE); // Chữ màu xanh dương
        headerLabel.setPreferredSize(new Dimension(1920, 80)); // Chiều cao cố định

    // Tạo một panel chứa header để căn chỉnh
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Thêm header vào centerPanel
        this.add(headerPanel, BorderLayout.NORTH);

        // Tạo leftPanel (20% chiều rộng)
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 1080)); // 20% chiều rộng
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Sắp xếp dọc
        leftPanel.setBackground(Color.decode("#F5F5F5")); // Xám nhạt


        // Tạo button1 và button2 với kích thước cố định
        btn_innfor = new JButton("Thông tin");
        btn_dotest = new JButton("Làm bài thi");
        btn_dangxuat=new JButton("Đăng xuất");

        // Thiết lập kích thước cụ thể
        Dimension buttonSize = new Dimension(384, 70);
        btn_innfor.setMaximumSize(buttonSize);
        btn_dotest.setMaximumSize(buttonSize);
        btn_dangxuat.setMaximumSize(buttonSize);
        // Chỉnh cỡ chữ và màu chữ
        Font buttonFont = new Font("Arial", Font.BOLD, 20); // Cỡ chữ 18, đậm
        btn_innfor.setFont(buttonFont);
        btn_dotest.setFont(buttonFont);
        btn_dangxuat.setFont(buttonFont);

        btn_innfor.setBackground(Color.PINK); // Chữ màu xanh dương
        btn_dotest.setBackground(Color.PINK);
        btn_dangxuat.setBackground(Color.PINK);



        // Căn giữa button
        btn_innfor.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_dotest.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_dangxuat.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm khoảng cách
        leftPanel.add(Box.createVerticalStrut(20)); // Khoảng cách trên
        leftPanel.add(btn_innfor);
        leftPanel.add(Box.createVerticalStrut(20)); // Khoảng cách giữa
        leftPanel.add(btn_dotest);
        leftPanel.add(Box.createVerticalStrut(20)); // Khoảng cách dưới
        leftPanel.add(btn_dangxuat);
        // Tạo CardLayout cho centerPanel
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        thongTinUI=new ThongTinUI();



        // Thêm các panel vào centerPanel
        centerPanel.add(thongTinUI, "1");


        // Sự kiện chuyển đổi panel
        btn_innfor.addActionListener(e -> cardLayout.show(centerPanel, "Panel1"));
        btn_dotest.addActionListener(e -> cardLayout.show(centerPanel, "Panel2"));

        // Thêm các panel vào JFrame
        this.add(leftPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public void addEvents(){
        btn_innfor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel,"1");
            }
        });
        btn_dangxuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        btn_dotest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UI_FormLoginDoTest loginDialog = new UI_FormLoginDoTest(UIMainRoleThiSinh.this);
                loginDialog.setVisible(true);
            }
        });

    }

}
