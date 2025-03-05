package QuanLyTracNghiem.GUI;

import javax.swing.*;
import java.awt.*;

public class Componet_Ques extends JPanel {
    public Componet_Ques() {
        setPreferredSize(new Dimension(600, 800));
        setLayout(new BorderLayout());

        // Panel câu hỏi
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createTitledBorder("Câu hỏi"));

        // TextArea với ScrollPane
        JTextArea questionTextArea = new JTextArea(5, 30);
        questionTextArea.setText("Câu hỏi: Thủ đô của Nhật Bản là gì?");
        JScrollPane scrollPane = new JScrollPane(questionTextArea);
        questionPanel.add(scrollPane, BorderLayout.CENTER);

        // Label chứa ảnh (màu đen tạm thời)
        JLabel questionImageLabel = new JLabel();
        questionImageLabel.setPreferredSize(new Dimension(150, 150));
        questionImageLabel.setOpaque(true);
        questionImageLabel.setBackground(Color.BLACK);
        questionPanel.add(questionImageLabel, BorderLayout.EAST);

        // Panel chứa 2 button
        JPanel questionButtonPanel = new JPanel();
        JButton btnChooseImage = new JButton("Chọn ảnh");
        JButton btnEditQuestion = new JButton("Chỉnh sửa câu hỏi");
        questionButtonPanel.add(btnChooseImage);
        questionButtonPanel.add(btnEditQuestion);
        questionPanel.add(questionButtonPanel, BorderLayout.SOUTH);

        add(questionPanel, BorderLayout.NORTH);

        // Panel câu trả lời
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new GridLayout(4, 1));
        answerPanel.setBorder(BorderFactory.createTitledBorder("Câu trả lời"));

        String[] answers = {"Tokyo", "Seoul", "Beijing", "Bangkok"};
        for (int i = 0; i < 4; i++) {
            JPanel answerSubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JCheckBox checkBox = new JCheckBox();
            JLabel answerText = new JLabel(answers[i]);
            JLabel answerImageLabel = new JLabel();
            answerImageLabel.setPreferredSize(new Dimension(150, 150));
            answerImageLabel.setOpaque(true);
            answerImageLabel.setBackground(Color.BLACK);
            JButton btnChooseAnswerImage = new JButton("Chọn ảnh");
            JButton btnEditAnswer = new JButton("Sửa câu trả lời");

            answerSubPanel.add(checkBox);
            answerSubPanel.add(answerText);
            answerSubPanel.add(answerImageLabel);
            answerSubPanel.add(btnChooseAnswerImage);
            answerSubPanel.add(btnEditAnswer);

            answerPanel.add(answerSubPanel);
        }

        add(answerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Component Question");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.add(new Componet_Ques());
        frame.setVisible(true);
    }
}


