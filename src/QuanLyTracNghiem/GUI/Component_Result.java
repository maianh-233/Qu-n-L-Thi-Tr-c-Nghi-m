package QuanLyTracNghiem.GUI;

import javax.swing.*;
import java.awt.*;

public class Component_Result extends JPanel {
    public Component_Result() {
        setPreferredSize(new Dimension(400, 500));
        setLayout(new GridLayout(7, 1, 10, 10)); // 7 dòng, 1 cột, khoảng cách 10px
        setBorder(BorderFactory.createTitledBorder("Kết Quả"));

        // Thông tin giả lập
        String userId = "U12345";
        String userName = "Nguyễn Văn A";
        int diem = 85;
        int socaudung = 17;
        int socausai = 3;
        String tgianNop = "2025-03-03 10:30:00";
        String status = diem >= 50 ? "Đạt" : "Không đạt";

        // Tạo JLabel
        JLabel lblUserId = new JLabel("Mã tham gia: " + userId);
        JLabel lblUserName = new JLabel("Tên thí sinh: " + userName);
        JLabel lblDiem = new JLabel("Điểm: " + diem);
        JLabel lblSoCauDung = new JLabel("Số câu đúng: " + socaudung);
        JLabel lblSoCauSai = new JLabel("Số câu sai: " + socausai);
        JLabel lblTgianNop = new JLabel("Thời gian nộp bài: " + tgianNop);
        JLabel lblStatus = new JLabel("Trạng thái: " + status);

        // Định dạng chữ
        Font font = new Font("Arial", Font.BOLD, 16);
        lblUserId.setFont(font);
        lblUserName.setFont(font);
        lblDiem.setFont(font);
        lblSoCauDung.setFont(font);
        lblSoCauSai.setFont(font);
        lblTgianNop.setFont(font);
        lblStatus.setFont(font);

        // Thêm vào panel
        add(lblUserId);
        add(lblUserName);
        add(lblDiem);
        add(lblSoCauDung);
        add(lblSoCauSai);
        add(lblTgianNop);
        add(lblStatus);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Kết Quả Thí Sinh");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 850);
        frame.setLayout(new FlowLayout());
        frame.add(new Component_Result());
        frame.setVisible(true);
    }
}

