package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.AnswerBUS;
import QuanLyTracNghiem.BUS.QuestionBUS;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.QuestionModel;
import QuanLyTracNghiem.DTO.ResultModel;
import QuanLyTracNghiem.DTO.UserModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Component_Result extends JPanel {
    private QuestionBUS questionBUS = new QuestionBUS();
    private AnswerBUS answerBUS = new AnswerBUS();

    public Component_Result(UserModel user, ResultModel result) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Kết Quả"));

        // Panel hiển thị kết quả
        JPanel panelTop = new JPanel(new GridLayout(7, 1, 5, 5));
        panelTop.setBorder(BorderFactory.createTitledBorder("Thông tin kết quả"));

        JLabel lblUserId = new JLabel("Mã tham gia: " + user.getUser_id());
        JLabel lblUserName = new JLabel("Tên thí sinh: " + user.getFullname());
        JLabel lblDiem = new JLabel("Điểm: " + result.getDiem());
        JLabel lblSoCauDung = new JLabel("Số câu đúng: " + result.getSoCauDung());
        JLabel lblSoCauSai = new JLabel("Số câu sai: " + result.getSoCauSai());
        JLabel lblTgianNop = new JLabel("Thời gian nộp bài: " + result.getTgian_nop());
        JLabel lblStatus = new JLabel("Trạng thái: " + ((result.getStatus() == 1) ? "Đạt" : "Không Đạt"));

        Font font = new Font("Arial", Font.BOLD, 16);
        lblUserId.setFont(font);
        lblUserName.setFont(font);
        lblDiem.setFont(font);
        lblSoCauDung.setFont(font);
        lblSoCauSai.setFont(font);
        lblTgianNop.setFont(font);
        lblStatus.setFont(font);

        panelTop.add(lblUserId);
        panelTop.add(lblUserName);
        panelTop.add(lblDiem);
        panelTop.add(lblSoCauDung);
        panelTop.add(lblSoCauSai);
        panelTop.add(lblTgianNop);
        panelTop.add(lblStatus);

        // Lấy danh sách câu hỏi và câu trả lời
        ArrayList<QuestionModel> list_ques = questionBUS.getListQuestionInExam(result.getExam_id());
        ArrayList<ArrayList<AnswerModel>> list_ans = new ArrayList<>();
        for (QuestionModel questionModel : list_ques) {
            list_ans.add(answerBUS.selectAnswerByQues_id(questionModel.getQuestion_id()));
        }
        ArrayList<ArrayList<AnswerModel>> user_Answer = answerBUS.getUserAnswers(result.getExam_id(), user.getUser_id(), list_ques);
        String html = generateHTML(list_ques, list_ans, user_Answer);

        // Panel hiển thị chi tiết câu hỏi
        JEditorPane htmlPane = new JEditorPane("text/html", html);
        htmlPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(htmlPane);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Chi tiết bài làm"));

        // Thêm vào giao diện chính
        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private String generateHTML(ArrayList<QuestionModel> questions, ArrayList<ArrayList<AnswerModel>> answers, ArrayList<ArrayList<AnswerModel>> selectedAnswer) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family: Arial, sans-serif; padding:10px;'>");

        for (int i = 0; i < questions.size(); i++) {
            html.append("<div style='border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;'>");
            html.append("<h3 style='color:blue;'>Câu ").append(i + 1).append(": ").append(questions.get(i).getQuestion_content()).append("</h3>");

            html.append("<div style='margin-left: 20px;'><b>Các đáp án:</b>");
            for (AnswerModel ans : answers.get(i)) {
                boolean isSelected = selectedAnswer.get(i).contains(ans);
                boolean isCorrect = ans.getIsRight() == 1;
                String color = isSelected ? (isCorrect ? "green" : "red") : "black";
                html.append("<p style='color:").append(color).append("'>").append(ans.getAnswer_content()).append("</p>");
            }
            html.append("</div>");

            html.append("<div style='margin-left: 20px; color: blue;'><b>Đáp án đã chọn:</b> ");
            if (selectedAnswer.get(i).isEmpty()) {
                html.append("Không có đáp án nào được chọn.");
            } else {
                for (AnswerModel ans : selectedAnswer.get(i)) {
                    html.append(ans.getAnswer_content()).append(" ");
                }
            }
            html.append("</div>");

            html.append("<div style='margin-left: 20px; color: green;'><b>Đáp án đúng:</b> ");
            for (AnswerModel ans : answers.get(i)) {
                if (ans.getIsRight() == 1) {
                    html.append(ans.getAnswer_content()).append(" ");
                }
            }
            html.append("</div>");

            html.append("</div>");
        }
        html.append("</body></html>");
        return html.toString();
    }
}