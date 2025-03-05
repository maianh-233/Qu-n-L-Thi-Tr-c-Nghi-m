package QuanLyTracNghiem.GUI;

import MyCustom.TransparentPanel;
import QuanLyTracNghiem.BUS.InputValidator;
import QuanLyTracNghiem.BUS.UserBUS;
import QuanLyTracNghiem.DTO.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DangKyGUI extends JFrame {
    private JPanel pnDangKy;
    private JTextField txtFullName, txtEmail, txtPhone;
    private JPasswordField txtPassword, txtCheckPassword;
    private JCheckBox ckbStudent, ckbTeacher;
    private JButton btnDangKy, btnDangNhap;

    private UserBUS userBUS = new UserBUS();

    public DangKyGUI() {
        addControls();
        addEvents();
    }

    public void showWindow() {
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void addControls() {
        this.setTitle("Quản lý trắc nghiệm");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 620);
        this.setResizable(true);

        // Tiêu đề
        JLabel titleLabel = new JLabel("ĐĂNG KÝ");
        titleLabel.setForeground(Color.black);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        JPanel pnTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnTitle.add(titleLabel);
        pnDangKy = new JPanel(new BorderLayout());
        pnDangKy.add(pnTitle, BorderLayout.NORTH);

        //=================PANEL INPUT===========
        int x = 15;
        txtPassword = new JPasswordField(x);
        txtCheckPassword = new JPasswordField(x);
        txtFullName = new JTextField(x);
        txtEmail = new JTextField(x);
        txtPhone = new JTextField(x);

        //===========================================
        JPanel pnThongTinDangKy = new TransparentPanel();
        pnThongTinDangKy.setLayout(null);
        Font font = new Font("Tahoma", Font.PLAIN, 20);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(font);
        txtPassword.setFont(font);
        lblPassword.setBounds(30, 30, 150, 40);
        txtPassword.setBounds(160, 30, 300, 40);

        JLabel lblCheckPassword = new JLabel("Nhập lại mk:");
        lblCheckPassword.setFont(font);
        txtCheckPassword.setFont(font);
        lblCheckPassword.setBounds(30, 80, 150, 40);
        txtCheckPassword.setBounds(160, 80, 300, 40);

        JLabel lblFullName = new JLabel("Họ và tên:");
        lblFullName.setFont(font);
        txtFullName.setFont(font);
        lblFullName.setBounds(30, 130, 150, 40);
        txtFullName.setBounds(160, 130, 300, 40);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(font);
        txtEmail.setFont(font);
        lblEmail.setBounds(30, 180, 150, 40);
        txtEmail.setBounds(160, 180, 300, 40);

        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setFont(font);
        txtPhone.setFont(font);
        lblPhone.setBounds(30, 230, 150, 40);
        txtPhone.setBounds(160, 230, 300, 40);

        JLabel lblChon = new JLabel("Chọn:");
        ckbStudent = new JCheckBox("Học sinh");
        ckbTeacher = new JCheckBox("Giáo viên");
        lblChon.setFont(font);
        ckbStudent.setFont(font);
        ckbTeacher.setFont(font);
        lblChon.setBounds(30, 330, 120, 40);
        ckbStudent.setBounds(130, 330, 150, 40);
        ckbTeacher.setBounds(280, 330, 150, 40);

        //=================BUTTON===============
        btnDangKy = new JButton("Đăng ký");
        btnDangNhap = new JButton("Đăng nhập ngay");

        Font fontButton = new Font("Tahoma", Font.BOLD, 16);
        btnDangNhap.setFont(fontButton);
        btnDangKy.setFont(fontButton);

        btnDangKy.setBounds(130, 410, 200, 40);
        JLabel lblCoTK = new JLabel("Đã có tài khoản?");
        lblCoTK.setFont(font);
        lblCoTK.setBounds(30, 480, 150, 40);
        btnDangNhap.setBounds(250, 480, 200, 40);


        pnThongTinDangKy.add(lblPassword);
        pnThongTinDangKy.add(txtPassword);
        pnThongTinDangKy.add(lblCheckPassword);
        pnThongTinDangKy.add(txtCheckPassword);
        pnThongTinDangKy.add(lblFullName);
        pnThongTinDangKy.add(txtFullName);
        pnThongTinDangKy.add(lblEmail);
        pnThongTinDangKy.add(txtEmail);
        pnThongTinDangKy.add(lblPhone);
        pnThongTinDangKy.add(txtPhone);
        pnThongTinDangKy.add(lblChon);
        pnThongTinDangKy.add(ckbStudent);
        pnThongTinDangKy.add(ckbTeacher);
        pnThongTinDangKy.add(btnDangKy);
        pnThongTinDangKy.add(lblCoTK);
        pnThongTinDangKy.add(btnDangNhap);

        pnDangKy.add(pnThongTinDangKy);

        this.add(pnDangKy);
    }

    private void addEvents() {
        ckbStudent.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ckbStudent.isSelected()) {
                    ckbTeacher.setSelected(false);
                }
            }
        });

        ckbTeacher.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ckbTeacher.isSelected()) {
                    ckbStudent.setSelected(false);
                }
            }
        });

        btnDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DangNhapGUI dangNhapGUI = new DangNhapGUI();
                dangNhapGUI.showWindow();
                dispose();
            }
        });


        btnDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy mật khẩu từ JPasswordField
                char[] passwordChars = txtPassword.getPassword();
                String password = new String(passwordChars).trim();

                char[] checkPasswordChars = txtCheckPassword.getPassword();
                String checkPassword = new String(checkPasswordChars).trim();

                InputValidator inputValidator = new InputValidator();

                // Lấy các thông tin khác
                String fullname = txtFullName.getText().trim();
                String email = txtEmail.getText().trim();
                String phone = txtPhone.getText().trim();

                // Kiểm tra rỗng
                if (password.isEmpty() || checkPassword.isEmpty() || fullname.isEmpty() ||
                        email.isEmpty() || phone.isEmpty() || (!ckbTeacher.isSelected() && !ckbStudent.isSelected())) {
                    JOptionPane.showMessageDialog(null,
                            "Vui lòng nhập đầy đủ các thông tin cần thiết!",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Kiểm tra định dạng mật khẩu
                if (!inputValidator.isValidPassword(password)) {
                    JOptionPane.showMessageDialog(null,
                            "Mật khẩu phải chứa ít nhất:\n- 1 Chữ cái\n- 1 Số\n- 1 Ký tự đặc biệt",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Kiểm tra mật khẩu trùng khớp
                if (!password.equals(checkPassword)) {
                    JOptionPane.showMessageDialog(null,
                            "Mật khẩu và nhập lại mật khẩu phải giống nhau!",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Kiểm tra số điện thoại
                if (!inputValidator.isValidVietnamesePhoneNumber(phone)) {
                    JOptionPane.showMessageDialog(null,
                            "Vui lòng nhập đúng số điện thoại Việt Nam!",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Kiểm tra email
                if (!inputValidator.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null,
                            "Vui lòng nhập đúng định dạng email!",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Xác định role (0: Học sinh, 1: Giáo viên)
                int role = ckbStudent.isSelected() ? 0 : 1;

                // Tạo user_id
                String user_id = userBUS.generateUserId(fullname);

                // Tạo đối tượng UserModel
                UserModel newUser = new UserModel(email, password, phone, user_id, fullname, role);

                // Thêm user vào CSDL
                if (userBUS.addUser(newUser)) {
                    JOptionPane.showMessageDialog(null,
                            "Đăng ký thành công! Mã tài khoản cấp cho bạn là " + user_id + ".",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Đã xảy ra lỗi!",
                            "Cảnh báo",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }
}
