package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.AnswerBUS;
import QuanLyTracNghiem.BUS.ExportExam;
import QuanLyTracNghiem.BUS.QuestionBUS;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.QuestionModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

class ExportTestDialog extends JDialog {
    private JComboBox<String> comboBox;
    private JButton exportButton;
    private QuestionBUS questionBUS = new QuestionBUS();
    private AnswerBUS answerBUS = new AnswerBUS();

    public ExportTestDialog(JDialog parent, ArrayList<ExamModel> list_exam) {
        super(parent, "Xuất Bài Test", true);

        if (list_exam.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Không có đề thi nào để xuất!");
            dispose();
            return;
        }

        String[] maDeThi = list_exam.stream().map(ExamModel::getExam_code).toArray(String[]::new);
        comboBox = new JComboBox<>(maDeThi);
        exportButton = new JButton("Xuất ra DOC");

        exportButton.addActionListener(e -> exportFile(list_exam));

        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.add(new JLabel("Chọn mã đề: "));
        panel.add(comboBox);

        add(panel, BorderLayout.CENTER);
        add(exportButton, BorderLayout.SOUTH);

        setSize(350, 150);
        setLocationRelativeTo(parent);
    }

    private void exportFile(ArrayList<ExamModel> list_exam) {
        String selectedTest = (String) comboBox.getSelectedItem();
        int selectedIndex = comboBox.getSelectedIndex();

        if (selectedTest == null || selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một mã đề thi!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xuất mã đề: " + selectedTest + "?",
                "Xác nhận xuất file", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String fileName = Optional.ofNullable(JOptionPane.showInputDialog(this, "Nhập tên file (không cần .docx):"))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .orElse(null);

        if (fileName == null) {
            JOptionPane.showMessageDialog(this, "Tên file không hợp lệ!");
            return;
        }
        fileName += ".docx";

        ArrayList<QuestionModel> listQues = questionBUS.getListQuestionInExam(list_exam.get(selectedIndex).getExam_id());
        if (listQues.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có câu hỏi nào trong mã đề thi này.");
            return;
        }

        ArrayList<ArrayList<AnswerModel>> listAns = new ArrayList<>();
        for (QuestionModel ques : listQues) {
            listAns.add(answerBUS.selectAnswerByQues_id(ques.getQuestion_id()));
        }

        try {
            ExportExam.exportToDoc(fileName, listQues, listAns);
            JOptionPane.showMessageDialog(this, "Xuất file thành công: " + fileName);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage());
        }
    }
}
