package QuanLyTracNghiem.GUI;
import QuanLyTracNghiem.DTO.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class UIResult extends JDialog {
    private JPanel resultPanel, scrollPanel;
    private JEditorPane editorPane;

    public UIResult(JFrame parent, ArrayList<QuestionModel> questions, ArrayList<ArrayList<AnswerModel>> answers, ArrayList<ArrayList<Integer>> selectedAnswer, UserModel user, ResultModel result, ExamModel exam) {
        super(parent, "Kết Quả", true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0); // Đóng toàn bộ chương trình
            }
        });

        setSize(1000, 1000);
        setLayout(new BorderLayout());

        // Panel hiển thị kết quả
        resultPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Kết quả"));

        // Giả sử có một thí sinh, lấy thông tin thí sinh
        String candidateName = user.getFullname();
        String userId = user.getUser_id();
        int totalQuestions = questions.size();
        String status = (result.getDiem()>=exam.getPassing_score()) ? "Đạt" : "Không Đạt";

        resultPanel.add(new JLabel("Tên thí sinh: " + candidateName));
        resultPanel.add(new JLabel("User ID: " + userId));
        resultPanel.add(new JLabel("Tổng số câu: " + totalQuestions));
        resultPanel.add(new JLabel("Số câu đúng: " + result.getSoCauDung()));
        resultPanel.add(new JLabel("Số câu sai: " + result.getSoCauSai()));
        resultPanel.add(new JLabel("Điểm số: " + result.getDiem()));
        resultPanel.add(new JLabel("Trạng thái: " + status));

        // Panel chứa danh sách câu hỏi
        scrollPanel = new JPanel(new BorderLayout());
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText(generateHTML(questions, answers, selectedAnswer));
        editorPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết câu hỏi"));

        // Thêm các panel vào Dialog
        add(resultPanel, BorderLayout.NORTH);
        add(scrollPanel, BorderLayout.CENTER);
    }



    private String generateHTML(ArrayList<QuestionModel> questions, ArrayList<ArrayList<AnswerModel>> answers, ArrayList<ArrayList<Integer>> selectedAnswer) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family: Arial, sans-serif;'>");

        for (int i = 0; i < questions.size(); i++) {
            html.append("<div style='border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;'>");
            html.append("<h3>" + questions.get(i).getQuestion_content() + "</h3>");

            html.append("<div style='margin-left: 20px;'><b>Các đáp án:</b>");
            for (int j = 0; j < answers.get(i).size(); j++) {
                AnswerModel ans = answers.get(i).get(j);
                boolean isSelected = selectedAnswer.get(i).get(j) == 1;
                boolean isCorrect = ans.getIsRight()==1? true:false;
                String color = isSelected ? (isCorrect ? "green" : "red") : "black";
                html.append("<p style='color:" + color + "'>" + ans.getAnswer_content() + "</p>");
            }
            html.append("</div>");

            html.append("<div style='margin-left: 20px; color: blue;'><b>Đáp án đã chọn:</b> ");
            for (int j = 0; j < answers.get(i).size(); j++) {
                if (selectedAnswer.get(i).get(j) == 1) {
                    html.append(answers.get(i).get(j).getAnswer_content() + " ");
                }
            }
            html.append("</div>");

            html.append("<div style='margin-left: 20px; color: green;'><b>Đáp án chính xác:</b> ");
            for (AnswerModel ans : answers.get(i)) {
                if (ans.getIsRight()==1) {
                    html.append(ans.getAnswer_content() + " ");
                }
            }
            html.append("</div>");

            html.append("</div>");
        }

        html.append("</body></html>");
        return html.toString();
    }

}
