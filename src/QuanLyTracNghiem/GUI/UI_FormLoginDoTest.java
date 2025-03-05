package QuanLyTracNghiem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_FormLoginDoTest extends JDialog {
    private JTextField jTextField1;
    private JButton jButton1;
    private JFrame parentFrame; // Thêm biến để lưu frame chính

    public UI_FormLoginDoTest(JFrame parent) {
        super(parent, "Đăng Nhập Bài Thi", true); // JDialog modal
        this.parentFrame = parent; // Lưu frame chính
        initComponents();
        setLocationRelativeTo(parent); // Căn giữa frame cha
    }

    private void initComponents() {
        JLabel jLabel1 = new JLabel("Mã Bài Thi:");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jTextField1 = new JTextField(20);


        jButton1 = new JButton("Bắt Đầu Làm Bài");
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jButton1.setBackground(new Color(51, 153, 255));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFocusPainted(false);
        jButton1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        add(jLabel1, gbc);
        gbc.gridx = 1;
        add(jTextField1, gbc);



        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jButton1, gbc);

        setPreferredSize(new Dimension(400, 250));
        pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        String maBaiThi = jTextField1.getText().trim();


        if (maBaiThi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this, "Bắt đầu làm bài!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            GiaoDienLamBaiThi giaoDienLamBaiThi=new GiaoDienLamBaiThi(parentFrame);
            giaoDienLamBaiThi.showWindow();
            dispose(); // Đóng dialog hiện tại
            parentFrame.dispose();

        }
    }
}
