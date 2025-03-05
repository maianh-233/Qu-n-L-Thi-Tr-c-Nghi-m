package QuanLyTracNghiem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ExamCreationDialog extends JDialog {
    public ExamCreationDialog(JFrame parent) {
        super(parent, "Form tạo bài thi", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(8, 1));

        // Header
        JLabel lbl0 = new JLabel("Form tạo bài thi", SwingConstants.CENTER);
        lbl0.setFont(new Font("Arial", Font.BOLD, 16));
        add(lbl0);

        // Mã vào phòng thi
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl1 = new JLabel("Mã vào phòng thi: ");
        JLabel lbl_testid = new JLabel("1111");
        panel1.add(lbl1);
        panel1.add(lbl_testid);
        add(panel1);

        // Số lượng mã đề
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl2 = new JLabel("Số lượng mã đề: ");
        JComboBox<Integer> comboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        panel2.add(lbl2);
        panel2.add(comboBox);
        add(panel2);

        // Thời gian làm bài
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl4 = new JLabel("Thời gian làm bài: ");
        JComboBox<Integer> timeComboBox = new JComboBox<>(new Integer[]{30, 60, 90});
        panel4.add(lbl4);
        panel4.add(timeComboBox);
        add(panel4);

        // Số lượng chủ đề
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl3 = new JLabel("Số lượng chủ đề: ");
        panel3.add(lbl3);
        add(panel3);

        // Checkbox nhóm môn học
        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox cbMath = new JCheckBox("Toán");
        JCheckBox cbIT = new JCheckBox("Tin");
        JCheckBox cbBio = new JCheckBox("Sinh");
        JCheckBox cbPhys = new JCheckBox("Lí");
        JCheckBox cbChem = new JCheckBox("Hóa");
        panel5.add(cbMath);
        panel5.add(cbIT);
        panel5.add(cbBio);
        panel5.add(cbPhys);
        panel5.add(cbChem);
        add(panel5);

        // Nhập danh sách thí sinh
        JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblStudentList = new JLabel("Nhập danh sách thí sinh: ");
        JButton btnEnterStudentList = new JButton("Nhập danh sách");
        panel6.add(lblStudentList);
        panel6.add(btnEnterStudentList);
        add(panel6);

        // Footer button
        JButton btnCreateExam = new JButton("Tạo bài thi");
        add(btnCreateExam);
        btnCreateExam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UI_TestDetail(parent);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamCreationDialog(null));
    }
}
