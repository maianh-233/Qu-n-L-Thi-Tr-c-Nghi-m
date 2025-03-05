package QuanLyTracNghiem.GUI;

import javax.swing.*;
import java.awt.*;

public class TimeWarningDialog {
    public static void showWarning(JFrame parent, int minutesLeft) {
        // Tạo thông báo với định dạng HTML để có thể căn chỉnh và tạo kiểu
        String message = String.format("<html><div style='text-align: center; padding: 10px;'>" +
                "<b>Chỉ còn %d phút!</b><br>Hãy kiểm tra lại bài làm của bạn.</div></html>", minutesLeft);
        
        // Tạo và cấu hình JOptionPane
        JOptionPane optionPane = new JOptionPane(
                message,
                JOptionPane.WARNING_MESSAGE,
                JOptionPane.DEFAULT_OPTION
        );
        
        // Tạo và cấu hình dialog
        JDialog dialog = optionPane.createDialog(parent, "Cảnh báo thời gian");
        dialog.setAlwaysOnTop(true);
        
        // Đặt kích thước tối thiểu
        dialog.setMinimumSize(new Dimension(300, 150));
        
        // Hiển thị dialog ở giữa màn hình
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

    }
}

