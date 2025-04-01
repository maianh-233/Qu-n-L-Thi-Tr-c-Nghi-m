package QuanLyTracNghiem.GUI;

import MyCustom.TransparentPanel;
import QuanLyTracNghiem.BUS.UserBUS;
import QuanLyTracNghiem.DTO.UserModel;
import QuanLyTracNghiem.GUI.DangKyGUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.ArrayList;
public class DangNhapGUI extends JFrame{
    private JPanel pnDangNhap;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JCheckBox ckbCheck;
    private JButton btnDangNhap, btnThoat, btnDangKy;

    private UserBUS userBUS=new UserBUS();


    public DangNhapGUI(){
        addControls();
        addEvents();
    }

    public void showWindow(){
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void addControls(){
        this.setTitle("Quản lý trắc nghiệm");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 380);
        this.setResizable(true);

        // Tiêu đề
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP");
        titleLabel.setForeground(Color.black);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        JPanel pnTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnTitle.add(titleLabel);
        pnDangNhap = new JPanel(new BorderLayout());
        pnDangNhap.add(pnTitle, BorderLayout.NORTH);

        //=================PANEL INPUT===========
        int x = 15;
        txtUser = new JTextField(x);
        txtPassword = new JPasswordField(x);

        //===========================================
        JPanel pnThongTinDangNhap = new TransparentPanel();
        pnThongTinDangNhap.setLayout(null);
        Font font = new Font("Tahoma", Font.PLAIN,20);

        JLabel lblUser = new JLabel("Tài khoản:");
        lblUser.setFont(font);
        txtUser.setFont(font);
        lblUser.setBounds(30,30,150,40);
        txtUser.setBounds(160,30,300,40);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(font);
        txtPassword.setFont(font);
        lblPassword.setBounds(30,80,150,40);
        txtPassword.setBounds(160,80,300,40);

        ckbCheck = new JCheckBox("Hiện mật khẩu");
        ckbCheck.setBounds(160,130,200,30);

        JLabel lblKhongCoTK = new JLabel("Không có tài khoản?");
        lblKhongCoTK.setFont(font);
        lblKhongCoTK.setBounds(30,230,300,40);

        //=================BUTTON===============
        btnDangNhap = new JButton("Đăng nhập");
        btnDangKy = new JButton("Đăng ký ngay");
        btnThoat = new JButton("Thoát");

        Font fontButton = new Font("Tahoma",Font.BOLD,16);
        btnDangNhap.setFont(fontButton);
        btnDangKy.setFont(fontButton);
        btnThoat.setFont(fontButton);

        btnDangNhap.setBounds(30, 180, 200,40);
        btnThoat.setBounds(250,180,200,40);
        btnDangKy.setBounds(250,230,200,40);

        pnThongTinDangNhap.add(lblUser);
        pnThongTinDangNhap.add(txtUser);
        pnThongTinDangNhap.add(lblPassword);
        pnThongTinDangNhap.add(txtPassword);
        pnThongTinDangNhap.add(ckbCheck);
        pnThongTinDangNhap.add(btnDangNhap);
        pnThongTinDangNhap.add(btnThoat);
        pnThongTinDangNhap.add(lblKhongCoTK);
        pnThongTinDangNhap.add(btnDangKy);

        pnDangNhap.add(pnThongTinDangNhap);

        this.add(pnDangNhap);
    }

    private void addEvents(){
        ckbCheck.addActionListener(e -> {
            if (ckbCheck.isSelected()) {
                txtPassword.setEchoChar((char) 0); // Hiển thị văn bản
            } else {
                txtPassword.setEchoChar('*'); // Ẩn văn bản
            }
        });
        btnDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DangKyGUI dangKyGUI = new DangKyGUI();
                dangKyGUI.showWindow();
                dispose();
            }
        });
        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taikhoan=txtUser.getText().trim();
                char[] passwordChars = txtPassword.getPassword();
                String password = new String(passwordChars).trim();

                // Kiểm tra rỗng
                if (password.isEmpty() || taikhoan.isEmpty() ) {
                    JOptionPane.showMessageDialog(null,
                            "Vui lòng nhập đầy đủ các thông tin cần thiết!",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                UserModel user=userBUS.getUser(taikhoan,password);
                if(user==null){
                    JOptionPane.showMessageDialog(null,
                            "Đăng nhập không thành công !" ,
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else {
                    JOptionPane.showMessageDialog(null,
                            "Đăng nhập thành công !" ,
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                            if (user.getIsAdmin()==1){
                                dispose();
                                UIMainRoleAdmin ui=new UIMainRoleAdmin(user);
                                ui.showUI();
                            }else {
                                dispose();
                                UIMainRoleThiSinh ui=new UIMainRoleThiSinh(user);
                                ui.showUI();
                            }



                }

            }
        });
    }



}
