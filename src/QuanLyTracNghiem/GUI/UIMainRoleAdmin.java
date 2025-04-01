package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.DTO.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIMainRoleAdmin extends JFrame {
    private JPanel panelLeft, panelTop, panelMain, contentPanel;
    private JButton[] navigationButtons;
    private JLabel logoutLabel, titleLabel;

    // Định nghĩa các màu sắc
    private final Color backgroundColor = new Color(245, 245, 250); // Màu nền nhẹ nhàng
    private final Color panelColor = new Color(255, 255, 255); // Màu trắng cho panel
    private final Color accentColor = new Color(70, 130, 180); // Màu xanh dương đậm
    private final Color buttonHoverColor = new Color(100, 149, 237); // Màu xanh dương nhạt
    private final Color logoutColor = new Color(220, 53, 69); // Màu đỏ cho nút đăng xuất
    private final Color logoutHoverColor = new Color(200, 35, 51); // Màu đỏ đậm khi hover
    private JPanel panel_temp;
    private CardLayout main_cardlayout;
    private UserModel login_user;

    public UIMainRoleAdmin(UserModel login_user) {
        this.login_user=login_user;
        addControls();
        addEvents();
    }

    public void showUI(){
        setTitle("Giao diện giáo viên");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Mở cửa sổ ở chế độ toàn màn hình
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private void addControls() {
        setLayout(new BorderLayout());
        // Khởi tạo các panel
        createLeftPanel();
        createTopPanel();
        createMainPanel();
        // Thêm các panel vào Frame
        add(panelLeft, BorderLayout.WEST);
        add(panelTop, BorderLayout.NORTH);
        add(panelMain, BorderLayout.CENTER);
    }

    private void createLeftPanel() {
        panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.setPreferredSize(new Dimension(400, getHeight()));
        panelLeft.setBackground(backgroundColor);

        // Thêm các nút điều hướng
        String[] buttonLabels = {"Quản lý", "Thông tin cá nhân", "Trợ giúp","Tạo Câu Hỏi"};
        navigationButtons = new JButton[buttonLabels.length];
        
        for (int i = 0; i < buttonLabels.length; i++) {
            navigationButtons[i] = createNavigationButton(buttonLabels[i]);
            panelLeft.add(Box.createVerticalStrut(10));
            panelLeft.add(navigationButtons[i]);

        }

        panelLeft.add(Box.createVerticalGlue());

        // Thêm nút đăng xuất
        createLogoutButton();
        JPanel logoutPanel = new JPanel();
        logoutPanel.setMaximumSize(new Dimension(380, 60));
        logoutPanel.setBackground(backgroundColor);
        logoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.add(logoutLabel);

        panelLeft.add(Box.createVerticalGlue());
        panelLeft.add(logoutPanel);
        panelLeft.add(Box.createVerticalStrut(50));
    }

    private void createTopPanel() {
        panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(getWidth(), 80));
        panelTop.setBackground(backgroundColor);
        
        titleLabel = new JLabel("Chào Mừng Giáo Viên Đến Với Hệ Thống Chúng Tôi !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(accentColor);
        panelTop.add(titleLabel);
    }

    private void createMainPanel() {
        panelMain = new JPanel();
        main_cardlayout=new CardLayout();
        panelMain.setLayout(main_cardlayout);
        ThongTinUI thongtin_ui_panel=new ThongTinUI(login_user);
        TrogiupUI trogiupUI=new TrogiupUI();
        UI_Quanli ui_quanli=new UI_Quanli(this, login_user);
        panelMain.add(ui_quanli,"1");
        panelMain.add(thongtin_ui_panel,"2");
        panelMain.add(trogiupUI,"3");
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(380, 50));
        button.setMaximumSize(new Dimension(380, 50));
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }

    private void createLogoutButton() {
        ImageIcon logoutIcon = new ImageIcon("src/QuanLyTracNghiem/resources/logout.png");
        logoutLabel = new JLabel("Đăng xuất", logoutIcon, JLabel.CENTER);
        logoutLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logoutLabel.setForeground(Color.WHITE);
        logoutLabel.setOpaque(true);
        logoutLabel.setBackground(logoutColor);
        logoutLabel.setPreferredSize(new Dimension(380, 50));
        logoutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoutLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoutLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }



    private void addEvents() {
        // Thêm sự kiện cho các nút điều hướng
        for (int i = 0; i < navigationButtons.length; i++) {
            final int index = i; // Lưu lại index để sử dụng trong sự kiện
            navigationButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    navigationButtons[index].setBackground(buttonHoverColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    navigationButtons[index].setBackground(accentColor);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    switch (index) {
                        case 0:
                            main_cardlayout.show(panelMain, "1");
                            break;
                        case 2: // "Thông tin cá nhân"
                            main_cardlayout.show(panelMain, "2");
                            break;
                        case 3: // "Trợ giúp"
                            main_cardlayout.show(panelMain, "3");
                            break;

                        default:
                            System.out.println("Chức năng chưa được thiết lập.");
                    }
                }
            });
        }

        navigationButtons[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                QuestionInputDialog questionInputDialog=new QuestionInputDialog(UIMainRoleAdmin.this);
                questionInputDialog.setVisible(true);
            }
        });



        // Thêm sự kiện cho nút đăng xuất
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutLabel.setBackground(logoutHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutLabel.setBackground(logoutColor);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyDangXuat();
            }
        });
    }

    private void xuLyDangXuat() {
        int choice = JOptionPane.showConfirmDialog(
            UIMainRoleAdmin.this,
            "Bạn có chắc chắn muốn đăng xuất?",
            "Xác nhận đăng xuất",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }



}
