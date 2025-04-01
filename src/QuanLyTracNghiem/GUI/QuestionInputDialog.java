package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.AnswerBUS;
import QuanLyTracNghiem.BUS.ExcelImporter;
import QuanLyTracNghiem.BUS.QuestionBUS;
import QuanLyTracNghiem.BUS.TopicBUS;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.QuestionModel;
import QuanLyTracNghiem.DTO.TopicModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;



public class QuestionInputDialog extends JDialog {
    private TopicBUS topicBUS = new TopicBUS();
    private QuestionBUS questionBUS=new QuestionBUS();
    private AnswerBUS answerBUS=new AnswerBUS();
    private ArrayList<TopicModel> list_topic;
    private ArrayList<QuestionModel> list_question;
    private ArrayList<AnswerModel> list_answer;
    private ButtonGroup group;
    private JFrame frame;

    public QuestionInputDialog(JFrame parent) {
        super(parent, "Nhập câu hỏi", true);
        this.frame=parent;
        this.list_topic = topicBUS.selectAll();
        setLayout(new GridLayout(3, 1, 10, 10));

        // Panel chọn môn học
        JPanel panel2 = new JPanel();
        JLabel label2 = new JLabel("Chọn môn học:");
        group = new ButtonGroup(); // ButtonGroup để đảm bảo chỉ chọn một môn học
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(list_topic.size(), 1));

        for (TopicModel subject : list_topic) {
            JRadioButton radioButton = new JRadioButton(subject.getTopic_name());
            radioButton.setActionCommand(subject.getTopic_name()); // Đặt giá trị để lấy khi cần
            group.add(radioButton);
            radioPanel.add(radioButton);
        }

        JScrollPane scrollPane = new JScrollPane(radioPanel);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        panel2.setLayout(new BorderLayout());
        panel2.add(label2, BorderLayout.NORTH);
        panel2.add(scrollPane, BorderLayout.CENTER);

        // Nút Nhập câu hỏi
        JPanel panel3 = new JPanel();
        JButton submitButton = new JButton("Nhập câu hỏi");
        panel3.add(submitButton);

        // Xử lý sự kiện khi nhấn nút nhập câu hỏi
        // Xử lý sự kiện khi nhấn nút nhập câu hỏi
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra xem người dùng đã chọn môn học chưa
                if (group.getSelection() == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một môn học trước!");
                    return;
                }

                // Lấy môn học đã chọn
                String selectedTopicName = group.getSelection().getActionCommand();
                int selectedTopicId = -1; // ID môn học

                // Tìm ID của môn học trong danh sách
                for (TopicModel topic : list_topic) {
                    if (topic.getTopic_name().equals(selectedTopicName)) {
                        selectedTopicId = topic.getTopic_id();
                        System.out.println("Topic được chọn"+topic.getTopic_id());
                        break;
                    }
                }

                if (selectedTopicId == -1) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy môn học đã chọn!");
                    return;
                }

                // Mở hộp thoại chọn file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn file Excel");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xls", "xlsx"));

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile == null || !selectedFile.exists()) {
                        JOptionPane.showMessageDialog(null, "File không hợp lệ!");
                        return;
                    }

                    // Hiển thị thông báo đã chọn file
                    JOptionPane.showMessageDialog(null, "Bạn đã chọn file: " + selectedFile.getAbsolutePath() + "\nMôn học: " + selectedTopicName);

                    // Gọi ExcelImporter để nhập câu hỏi
                    ExcelImporter excelImporter = new ExcelImporter();
                    excelImporter.importExcel(selectedFile.getAbsolutePath(), selectedTopicId);
                    ArrayList<QuestionModel>list_ques=excelImporter.getList_question();
                    ArrayList<AnswerModel>list_answer=excelImporter.getList_answer();
                    UI_List_Import_Question_Dialog uiListImportQuestionDialog=new UI_List_Import_Question_Dialog(frame,list_ques,list_answer,1);
                    uiListImportQuestionDialog.setVisible(true);
                    dispose();
                }
            }
        });


        // Thêm các panel vào dialog
        add(panel2);
        add(panel3);

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }





}
