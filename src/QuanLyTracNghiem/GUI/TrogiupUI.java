package QuanLyTracNghiem.GUI;

import javax.swing.*;
import java.awt.*;

public class TrogiupUI extends JPanel {

    private JButton btnThoat;
    private JLabel jLbTTthisinh;
    private JPanel jPanel1, jPanel4;
    private JSeparator jSeparator1;
    private TextArea textArea1;

    public TrogiupUI() {
        setPreferredSize(new Dimension(1500, 1000));
        initComponents();
    }

    private void initComponents() {
        jSeparator1 = new JSeparator();
        jPanel1 = new JPanel();
        jLbTTthisinh = new JLabel();
        textArea1 = new TextArea();
        jPanel4 = new JPanel();
        btnThoat = new JButton();

        setLayout(new BorderLayout());

        jPanel1.setBackground(new Color(70, 130, 180));
        jLbTTthisinh.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jLbTTthisinh.setForeground(Color.WHITE);
        jLbTTthisinh.setText("Trợ giúp");
        jPanel1.add(jLbTTthisinh);
        add(jPanel1, BorderLayout.NORTH);

        textArea1.setColumns(10);
        textArea1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textArea1.setRows(2);
        textArea1.setEditable(false);
        textArea1.setText("\n\n\tChào mừng bạn đến với phần Trợ giúp của ứng dụng Quản lý Thi Trắc nghiệm!\n\n\tPhần trợ giúp này sẽ cung cấp cho bạn hướng dẫn chi tiết về cách sử dụng ứng dụng một cách hiệu quả.\n\t1. Tổng quan\n\n\tỨng dụng Quản lý Thi Trắc nghiệm được thiết kế để hỗ trợ quản lý và tổ chức các kỳ thi trắc nghiệm một cách dễ dàng và hiệu quả. Ứng dụng này cung cấp các chức năng chính sau:\n\n\tQuản lý tài khoản: Tạo và quản lý tài khoản cho quản trị viên và thí sinh.\n\tQuản lý đề thi: Tạo, chỉnh sửa và xóa các đề thi.\n\tQuản lý câu hỏi: Tạo, chỉnh sửa và xóa các câu hỏi trắc nghiệm.\n\tTổ chức thi: Tổ chức các kỳ thi và cho phép thí sinh làm bài.\n\tXem kết quả: Xem kết quả thi của thí sinh và thống kê kết quả.\n\t2. Hướng dẫn sử dụng\n\n\t2.1. Đăng nhập\n\n\tKhi khởi động ứng dụng, bạn sẽ thấy màn hình đăng nhập.\n\tNhập tên đăng nhập và mật khẩu của bạn.\n\tChọn vai trò (Quản trị viên hoặc Thí sinh).\n\tNhấn nút \"Đăng nhập\".\n\t2.2. Quản lý tài khoản (Quản trị viên)\n\n\tSau khi đăng nhập với vai trò Quản trị viên, bạn có thể quản lý tài khoản người dùng.\n\tTạo tài khoản: Nhấn nút \"Tạo tài khoản\", nhập thông tin người dùng và nhấn \"Lưu\".\n\tChỉnh sửa tài khoản: Chọn tài khoản cần chỉnh sửa, thay đổi thông tin và nhấn \"Lưu\".\n\tXóa tài khoản: Chọn tài khoản cần xóa và nhấn \"Xóa\".\n\t2.3. Quản lý đề thi (Quản trị viên)\n\n\tTạo đề thi: Nhấn nút \"Tạo đề thi\", nhập thông tin đề thi và thêm câu hỏi.\n\tChỉnh sửa đề thi: Chọn đề thi cần chỉnh sửa, thay đổi thông tin và câu hỏi.\n\tXóa đề thi: Chọn đề thi cần xóa và nhấn \"Xóa\".\n\t2.4. Quản lý câu hỏi (Quản trị viên)\n\n\tTạo câu hỏi: Nhấn nút \"Tạo câu hỏi\", nhập nội dung câu hỏi và các lựa chọn.\n\tChỉnh sửa câu hỏi: Chọn câu hỏi cần chỉnh sửa, thay đổi nội dung và lựa chọn.\n\tXóa câu hỏi: Chọn câu hỏi cần xóa và nhấn \"Xóa\".\n\t2.5. Làm bài thi (Thí sinh)\n\n\tChọn đề thi: Chọn đề thi từ danh sách các đề thi có sẵn.\n\tLàm bài: Chọn câu trả lời cho từng câu hỏi và nhấn \"Nộp bài\" khi hoàn thành.\n\tXem kết quả: Xem điểm số và kết quả chi tiết sau khi nộp bài.\n\t2.6. Xem kết quả (Quản trị viên)\n\n\tXem kết quả theo thí sinh: Chọn thí sinh và xem kết quả các bài thi đã làm.\n\tXem thống kê kết quả: Xem thống kê điểm số và phân tích kết quả thi.\n\t3. Khắc phục sự cố\n\n\tNếu bạn gặp sự cố khi đăng nhập, hãy kiểm tra lại tên đăng nhập và mật khẩu.\n\tNếu bạn gặp sự cố khi làm bài thi, hãy kiểm tra kết nối internet.\n\tNếu có bất kỳ vấn đề nào khác, xin vui lòng liên hệ với quản trị viên hệ thống.\n\t4. Liên hệ hỗ trợ\n\n\tEmail: [gmail]\n\tĐiện thoại: sdt\n\n");
        add(textArea1, BorderLayout.CENTER);

        jPanel4.setBackground(Color.WHITE);
        btnThoat.setBackground(new Color(209, 70, 12));
        btnThoat.setFont(new Font("Segoe UI", Font.BOLD, 24));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setText("THOÁT");
        jPanel4.add(btnThoat);
        add(jPanel4, BorderLayout.SOUTH);
    }
}