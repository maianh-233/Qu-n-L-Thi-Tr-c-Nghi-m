package QuanLyTracNghiem.GUI;

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

    public UIMainRoleAdmin() {
        addControls();
        addEvents();
    }

    public void showUI(){
        setTitle("Giao diện giáo viên");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
        String[] buttonLabels = {"Quản lý", "Báo cáo", "Thông tin cá nhân", "Trợ giúp","Tạo Câu Hỏi"};
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
        ThongTinUI thongtin_ui_panel=new ThongTinUI();
        TrogiupUI trogiupUI=new TrogiupUI();
        UI_Quanli ui_quanli=new UI_Quanli(this);
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

        navigationButtons[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn file Excel");
                int result = fileChooser.showOpenDialog(UIMainRoleAdmin.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    // Lấy thông tin file
                    java.io.File file = fileChooser.getSelectedFile();
                    String fileName = file.getName();
                    long fileSize = file.length(); // Kích thước file (bytes)

                    // Hiển thị thông báo
                    JOptionPane.showMessageDialog(
                            UIMainRoleAdmin.this,
                            "Bạn đã chọn file: " + fileName + " có kích thước: " + fileSize + " bytes",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // Gọi phương thức mở dialog chứa UI_DetailList_Ques_inTest=>Nớ thêm phương thức xử lí file rồi mới gọi showDetailListDialog nha
//                    showDetailListDialog();
                }

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

    private void showDetailListDialog() {
        JDialog detailDialog = new JDialog(this, "Danh Sách Câu Hỏi", true);
        detailDialog.setSize(1500, 900);
        detailDialog.setLocationRelativeTo(this);

        // Tạo panel chứa danh sách câu hỏi
        UI_DetailList_Ques_inTest detailPanel = new UI_DetailList_Ques_inTest();
        detailDialog.add(detailPanel);

        detailDialog.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIMainRoleAdmin ui = new UIMainRoleAdmin();
          ui.showUI();
        });
    }

}
