package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.AnswerBUS;
import QuanLyTracNghiem.BUS.QuestionBUS;
import QuanLyTracNghiem.DAO.AnswerDAO;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.QuestionModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Componet_Ques extends JPanel {
    private QuestionModel question;
    private ArrayList<AnswerModel>  answer;
    private AnswerBUS answerBUS=new AnswerBUS();
    private QuestionBUS questionBUS=new QuestionBUS();
    private Integer haveButton;
    public Componet_Ques(QuestionModel question,ArrayList <AnswerModel> answer, Integer haveButton) {
        this.question=question;
        this.answer=answer;
        this.haveButton=haveButton;
        setPreferredSize(new Dimension(900, 1000));
        setLayout(new BorderLayout());
        addControls();

    }
    public void addControls() {
        // Panel câu hỏi
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createTitledBorder("Câu hỏi"));

        // TextArea với ScrollPane
        JTextArea questionTextArea = new JTextArea(5, 30); // 🔹 Số hàng: 5, Số cột: 30
        questionTextArea.setText(question.getQuestion_content());
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);
        questionPanel.add(questionScrollPane, BorderLayout.CENTER);

        JLabel questionImageLabel = new JLabel();
        questionImageLabel.setPreferredSize(new Dimension(150, 150));
        questionImageLabel.setOpaque(true);
        questionPanel.add(questionImageLabel, BorderLayout.EAST);
        loadImageToLabel(questionImageLabel, question.getQuestion_picture());

        JPanel questionButtonPanel = new JPanel();
        JButton btnChooseImage = new JButton("Chọn ảnh");
        btnChooseImage.setVisible(haveButton==1);
        btnChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn ảnh");
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath();
                    question.setQuestion_picture(imagePath);
                    //String question_content, Integer question_level, String question_picture, Integer question_status, int question_id
                    if(questionBUS.editQuestion(question.getQuestion_content(), question.getQuestion_level(),question.getQuestion_picture(),question.getQuestion_status(),question.getQuestion_id())){
                        JOptionPane.showMessageDialog(null, "Thay đổi img câu hỏi thành công!","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(null, "Thay đổi img câu hỏi không thành công!","Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    // Cập nhật ảnh vào JLabel
                    loadImageToLabel(questionImageLabel, imagePath);

                }
            }
        });
        JButton btnEditQuestion = new JButton("Chỉnh sửa câu hỏi");
        btnEditQuestion.setVisible(haveButton==1);
        btnEditQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String q_content=questionTextArea.getText();
                if (q_content.isEmpty()||q_content==null){
                    JOptionPane.showMessageDialog(null, "Hãy nhập nội dung câu hỏi!","Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }else {
                    question.setQuestion_content(q_content);
                    if(questionBUS.editQuestion(question.getQuestion_content(), question.getQuestion_level(),question.getQuestion_picture(),question.getQuestion_status(),question.getQuestion_id())){
                        JOptionPane.showMessageDialog(null, "Thay đổi img câu hỏi thành công!","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(null, "Thay đổi img câu hỏi không thành công!","Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
        });
        questionButtonPanel.add(btnChooseImage);
        questionButtonPanel.add(btnEditQuestion);
        questionPanel.add(questionButtonPanel, BorderLayout.SOUTH);

        add(questionPanel, BorderLayout.NORTH);



        // Panel câu trả lời
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new GridLayout(4, 1));
        answerPanel.setBorder(BorderFactory.createTitledBorder("Câu trả lời"));

        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerPanel.setBorder(BorderFactory.createTitledBorder("Câu trả lời"));

// 🔹 Đặt kích thước cố định cho JTextArea
        int textAreaWidth = 500;
        int textAreaHeight = 150;

        for (int i = 0; i < answer.size(); i++) {
            JPanel answerSubPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            // Tạo JTextArea và JScrollPane
            JTextArea answerTextArea = new JTextArea();
            answerTextArea.setText(answer.get(i).getAnswer_content());
            answerTextArea.setLineWrap(true);
            answerTextArea.setWrapStyleWord(true);
            answerTextArea.setEditable(true);
            answerTextArea.setPreferredSize(new Dimension(500, 150));
            answerTextArea.setMinimumSize(new Dimension(500, 150));
            answerTextArea.setMaximumSize(new Dimension(500, 150));


            JScrollPane answerScrollPane = new JScrollPane(answerTextArea);
            answerScrollPane.setPreferredSize(new Dimension(500, 150));

            // Tạo JLabel để hiển thị ảnh
            JLabel answerImageLabel = new JLabel();
            answerScrollPane.setPreferredSize(new Dimension(500, 150));
            answerScrollPane.setMinimumSize(new Dimension(500, 150));
            answerScrollPane.setMaximumSize(new Dimension(500, 150));
            answerImageLabel.setOpaque(true);
            loadImageToLabel(answerImageLabel, answer.get(i).getAnswer_picture());

            // Nút chọn ảnh và chỉnh sửa
            JButton btnChooseAnswerImage = new JButton("Chọn ảnh");
            JButton btnEditAnswer = new JButton("Sửa câu trả lời");
            btnChooseAnswerImage.setVisible(haveButton==1);
            btnEditAnswer.setVisible(haveButton==1);
            // Gán thuộc tính cho nút chỉnh sửa
            final int answerIndex = i;
            btnEditAnswer.putClientProperty("index", answerIndex);
            btnEditAnswer.putClientProperty("textArea", answerTextArea);

            // Gán thuộc tính cho nút chọn ảnh
            btnChooseAnswerImage.putClientProperty("index", answerIndex);
            btnChooseAnswerImage.putClientProperty("imageLabel", answerImageLabel);

            // Sự kiện chỉnh sửa nội dung câu trả lời
            btnEditAnswer.addActionListener(e -> {
                int index = (int) btnEditAnswer.getClientProperty("index");
                JTextArea textArea = (JTextArea) btnEditAnswer.getClientProperty("textArea");

                if (textArea != null) {
                    String updatedAnswer = textArea.getText();
                    answer.get(index).setAnswer_content(updatedAnswer);
                    if (answerBUS.update(answer.get(index))){
                        JOptionPane.showMessageDialog(null,"Thay đổi nội dung câu trả lời thành công!","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(null,"Thay đổi nội dung câu trả lời không thành công!","Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (index >= 0 && index < answer.size()) {
                        answer.get(index).setAnswer_content(updatedAnswer);
                    }
                }
            });

            // Sự kiện chọn ảnh
            btnChooseAnswerImage.addActionListener(e -> {
                int index = (int) btnChooseAnswerImage.getClientProperty("index");
                JLabel imageLabel = (JLabel) btnChooseAnswerImage.getClientProperty("imageLabel");

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn ảnh");
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath();

                    answer.get(index).setAnswer_picture(imagePath);
                    if (answerBUS.update(answer.get(index))){
                        JOptionPane.showMessageDialog(null,"Thay đổi img câu trả lời thành công!","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(null,"Thay đổi img câu trả lời không thành công!","Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    // Cập nhật ảnh vào JLabel
                    loadImageToLabel(imageLabel, imagePath);

                    if (index >= 0 && index < answer.size()) {
                        answer.get(index).setAnswer_picture(imagePath);
                    }
                }
            });

            // Thêm JTextArea vào panel
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            answerSubPanel.add(answerScrollPane, gbc);

            // Thêm JLabel vào panel
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            answerSubPanel.add(answerImageLabel, gbc);

            // Thêm hai nút vào panel
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            answerSubPanel.add(btnChooseAnswerImage, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            answerSubPanel.add(btnEditAnswer, gbc);

            // Thêm panel con vào panel chính
            answerPanel.add(answerSubPanel);
        }




        add(answerPanel, BorderLayout.CENTER);
    }


    public void loadImageToLabel(JLabel label, String imagePath) {
        int labelWidth = 150;
        int labelHeight = 150;

        label.setPreferredSize(new Dimension(labelWidth, labelHeight)); // Đặt kích thước tối thiểu
        label.setOpaque(true);
        label.setBackground(Color.WHITE); // Nền trắng mặc định
        label.setIcon(null); // Xóa icon nếu có

        if (imagePath == null || imagePath.isEmpty()) {
            return; // Không có ảnh, giữ JLabel trắng với kích thước mặc định
        }

        try {
            BufferedImage originalImage = null;
            if (imagePath.startsWith("http") || imagePath.startsWith("https")) {
                // Load ảnh từ URL
                URL url = new URL(imagePath);
                originalImage = ImageIO.read(url);
            } else {
                // Load ảnh từ file cục bộ
                File file = new File(imagePath);
                if (file.exists()) {
                    originalImage = ImageIO.read(file);
                } else {
                    System.out.println("Không tìm thấy file: " + imagePath);
                    return; // Không tìm thấy ảnh, giữ JLabel trắng
                }
            }

            if (originalImage != null) {
                // Scale ảnh theo kích thước JLabel
                Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc ảnh: " + imagePath);
        }
    }

}
