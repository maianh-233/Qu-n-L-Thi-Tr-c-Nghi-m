package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.AnswerBUS;
import QuanLyTracNghiem.BUS.ResultBUS;
import QuanLyTracNghiem.BUS.UserChooseBUS;
import QuanLyTracNghiem.DTO.*;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;
public class GiaoDienLamBaiThi extends JFrame {
    // Khai báo các thành phần giao diện
    private JPanel wrapperPanel, leftPanel, rightPanel, topPanel;
    private JPanel studentPanel, timerPanel;
    private JPanel questionPanel, controlPanel, answerPanel;
    private JPanel questionListPanel;
    private JTextArea questionArea;
    private JLabel questionImage;
    private JCheckBox choiceA, choiceB, choiceC, choiceD;

    private JLabel answerImageA, answerImageB, answerImageC, answerImageD;
    private Color backgroundColor = Color.WHITE;
    private Color accentColor = Color.BLUE;

    private JButton previousButton, nextButton, submitButton;
    private JLabel timerLabel;

    // Khai báo các biến dữ liệu
    private Timer timer;
    private int timeRemaining;
    //Danh sách các câu hỏi dùng để thi
    private ArrayList<QuestionModel> questions;
    private UserChooseBUS userChooseBUS=new UserChooseBUS();
    private int currentQuestionIndex = 0;
    //Danh sách đáp án cho người dùng chọn

    private ArrayList<ExamQuestionModel> list_exam_question;
    private ResultModel result;
    private UserModel user;
    private static final Color buttonColor = new Color(100, 149, 237);
    private JFrame mainframe;
    private Integer tgianlambai;
    private ArrayList<ArrayList<AnswerModel>> answers;
    private ArrayList<ArrayList<Integer>> selected_answers;
    private ArrayList<UserChooseModel> list_user_choose;
    private ExamModel made;
    private ResultBUS resultBUS=new ResultBUS();

    public GiaoDienLamBaiThi(JFrame mainframe, ResultModel result,UserModel user,Integer tgianlambai,
                             ArrayList<QuestionModel> questions,ArrayList<ArrayList<AnswerModel>> answers , ArrayList<ExamQuestionModel> list_exam_question, ExamModel made ) {
        this.mainframe=mainframe;
        this.result=result;
        this.made=made;
        this.user=user;
        this.questions=questions;
        this.list_exam_question=list_exam_question;
        this.tgianlambai=tgianlambai;
        this.answers=answers;
        int numQuestions = questions.size(); // Số lượng câu hỏi
        int numAnswersPerQuestion = 4; // Giả sử mỗi câu hỏi có 4 đáp án (bạn có thể thay đổi số này)
        this.timeRemaining=tgianlambai*60;
        //Khởi tạo sự lựa chọn ban đầu là 0
        this.selected_answers = new ArrayList<>();
        for (int i = 0; i < numQuestions; i++) {
            ArrayList<Integer> ans = new ArrayList<>(Collections.nCopies(numAnswersPerQuestion, 0));
            this.selected_answers.add(ans);
        }
        addControls();
        addEvents();
        initializeTimer();

    }

    private void addControls() {
        // Thiết lập frame chính
        this.setTitle("Thi Trắc Nghiệm");
        this.setSize(1600, 900);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Định dạng font chung
        setupUIFont();

        // Tạo các panel chính
        createMainPanels();
        
        // Tạo panel thông tin sinh viên và thời gian
        createTopPanel();
        
        // Tạo panel câu hỏi và đáp án
        createQuestionPanel();
        
        // Tạo panel điều khiển
        createControlPanel();
        
        // Tạo panel danh sách câu hỏi
        createQuestionListPanel();

        // Gắn các thành phần vào frame
        assembleComponents();

        updateQuestionList();
        updateQuestion(currentQuestionIndex);
    }

    private void setupUIFont() {
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 18));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("RadioButton.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 18));
        UIManager.put("TitledBorder.font", new Font("Arial", Font.BOLD, 22));
    }

    private void createMainPanels() {
        wrapperPanel = new JPanel(new BorderLayout());
        
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(1090, 900));
        leftPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            "Thông Tin & Câu Hỏi"
        ));
        leftPanel.setBackground(backgroundColor);

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(500, 900));
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            "Danh Sách Câu Hỏi"
        ));
        rightPanel.setBackground(backgroundColor);
    }

    //Panel top
    private void createTopPanel() {
        topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setPreferredSize(new Dimension(1090, 120));

        //Thông tin về bài thi và thí sinh
        studentPanel = createHighlightedPanel("Tài khoản:\t"+user.getUser_id(), "Tên thí sinh:\t"+user.getFullname());
        timerPanel = createHighlightedPanel("Thời gian còn lại:", tgianlambai+":00");

        timerLabel = (JLabel) timerPanel.getComponent(1);
        
        topPanel.add(studentPanel);
        topPanel.add(timerPanel);

    }


    private void createQuestionPanel() {
        questionPanel = new JPanel(new GridBagLayout());
        questionPanel.setBorder(BorderFactory.createTitledBorder("Câu Hỏi"));
        questionPanel.setPreferredSize(new Dimension(1090, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        questionArea = new JTextArea(6, 30);
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setPreferredSize(new Dimension(300, 150));
        JScrollPane scrollQuestion = new JScrollPane(questionArea);

        questionImage = new JLabel();
        questionImage.setPreferredSize(new Dimension(150, 150));
        questionImage.setOpaque(true);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.7; gbc.fill = GridBagConstraints.BOTH;
        questionPanel.add(scrollQuestion, gbc);

        gbc.gridx = 1; gbc.weightx = 0.3;
        questionPanel.add(questionImage, gbc);
        //List Câu trả lời
        createAnswerPanel();
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weightx = 1;
        questionPanel.add(answerPanel, gbc);
    }

    private void createAnswerPanel() {
        answerPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        answerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        answerPanel.setBackground(backgroundColor);

        choiceA = new JCheckBox();
        choiceB = new JCheckBox();
        choiceC = new JCheckBox();
        choiceD = new JCheckBox();

        answerImageA = new JLabel();
        answerImageB = new JLabel();
        answerImageC = new JLabel();
        answerImageD = new JLabel();

        JLabel[] answerImages = {answerImageA, answerImageB, answerImageC, answerImageD};
        int i=0;
        for (JLabel label : answerImages) {
            label.setPreferredSize(new Dimension(150, 150));
            label.setOpaque(true);
            i++;
        }

        setupAnswerChoices();
    }

    private void setupAnswerChoices() {
        JCheckBox[] choices = {choiceA, choiceB, choiceC, choiceD};
        JLabel[] answerImages = {answerImageA, answerImageB, answerImageC, answerImageD};


        for (int i = 0; i < 4; i++) {
            JPanel answerItem = new JPanel(new BorderLayout());
            answerItem.setBackground(backgroundColor);
            styleAnswerChoice(choices[i]);
            answerItem.add(choices[i], BorderLayout.WEST);
            answerItem.add(answerImages[i], BorderLayout.EAST);
            answerPanel.add(answerItem);
        }
    }

    private void styleAnswerChoice(JCheckBox choice) {
        choice.setBackground(Color.WHITE);
        choice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        choice.setCursor(new Cursor(Cursor.HAND_CURSOR));

        choice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                choice.setBackground(new Color(230, 230, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                choice.setBackground(Color.WHITE);
            }
        });
    }

    private void createControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setPreferredSize(new Dimension(1090, 80));

        previousButton = new JButton("Câu trước");
        nextButton = new JButton("Câu sau");
        submitButton = new JButton("NỘP BÀI");

        styleControlButtons();
    }

    private void styleControlButtons() {
        previousButton.setPreferredSize(new Dimension(150, 50));
        nextButton.setPreferredSize(new Dimension(150, 50));
        submitButton.setPreferredSize(new Dimension(300, 50));

        previousButton.setBackground(buttonColor);
        nextButton.setBackground(buttonColor);
        submitButton.setBackground(new Color(220, 53, 69));

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        navPanel.add(previousButton);
        navPanel.add(nextButton);

        controlPanel.add(navPanel);
        controlPanel.add(Box.createHorizontalStrut(50));
        controlPanel.add(submitButton);
    }

    private void createQuestionListPanel() {
        questionListPanel = new JPanel();
        questionListPanel.setLayout(new BoxLayout(questionListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(questionListPanel);
        scrollPane.setPreferredSize(new Dimension(500, 800));
        rightPanel.add(scrollPane);
    }

    private void assembleComponents() {
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(questionPanel, BorderLayout.CENTER);
        leftPanel.add(controlPanel, BorderLayout.SOUTH);

        wrapperPanel.add(leftPanel, BorderLayout.WEST);
        wrapperPanel.add(rightPanel, BorderLayout.EAST);

        this.add(wrapperPanel);
    }

    private void addEvents() {
        previousButton.addActionListener(e -> {
            updateListAnswerSelected(currentQuestionIndex);
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                updateQuestion(currentQuestionIndex);
            }
        });

        nextButton.addActionListener(e -> {
            updateListAnswerSelected(currentQuestionIndex);
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                updateQuestion(currentQuestionIndex);
            }
        });

        submitButton.addActionListener(e -> {
            ArrayList<ArrayList<Integer>> true_answers=DapAnDung(answers);
            TinhDiem(result, selected_answers, true_answers,  questions);
        });
    }

    private void updateQuestion(int i) {
        // Cập nhật nội dung câu hỏi và đáp án
        questionArea.setText(questions.get(i).getQuestion_content());
        choiceA.setText(answers.get(i).get(0).getAnswer_content());
        choiceB.setText(answers.get(i).get(1).getAnswer_content());
        choiceC.setText(answers.get(i).get(2).getAnswer_content());
        choiceD.setText(answers.get(i).get(3).getAnswer_content());

        // Đánh dấu lại các đáp án đã chọn
        setCheckBox(i);
        // Cập nhật hình ảnh
        loadImageToLabel(questionImage, questions.get(i).getQuestion_picture());
        loadImageToLabel(answerImageA, answers.get(i).get(0).getAnswer_picture());
        loadImageToLabel(answerImageB, answers.get(i).get(1).getAnswer_picture());
        loadImageToLabel(answerImageC, answers.get(i).get(2).getAnswer_picture());
        loadImageToLabel(answerImageD, answers.get(i).get(3).getAnswer_picture());

        updateQuestionList();
    }
    private void setCheckBox(int i) {
        ArrayList<Integer> list_selected_question = selected_answers.get(i);

        choiceA.setSelected(list_selected_question.get(0) == 1);
        choiceB.setSelected(list_selected_question.get(1) == 1);
        choiceC.setSelected(list_selected_question.get(2) == 1);
        choiceD.setSelected(list_selected_question.get(3) == 1);
    }

    private void updateQuestionList() {
        questionListPanel.removeAll();
        questionListPanel.setLayout(new BoxLayout(questionListPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < questions.size(); i++) {
            JPanel questionItemPanel = new JPanel(new BorderLayout());
            questionItemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            questionItemPanel.setBackground(Color.WHITE);
            questionItemPanel.setMaximumSize(new Dimension(480, Short.MAX_VALUE));

            StringBuilder questionText = new StringBuilder();
            questionText.append("<html><div style='width: 600px;'>");
            questionText.append(String.format("<table width='100%%'><tr><td width='70'><b style='color: #2196F3;'>Câu %d:</b></td>", (i + 1)));
            questionText.append("<td>").append(questions.get(i).getQuestion_content()).append("</td></tr></table>");

            questionText.append("<div style='margin-left: 20px; margin-top: 5px;'>");
            questionText.append("<table width='100%'>");

            ArrayList<Integer> selected_answer = selected_answers.get(i);
            int b = 0;
            for (AnswerModel choice : answers.get(i)) {
                if (selected_answer.get(b) == 1) {
                    questionText.append("<tr><td style='color:red;font-weight: bold;'>").append(choice.getAnswer_content()).append("</td></tr>");
                } else {
                    questionText.append("<tr><td>").append(choice.getAnswer_content()).append("</td></tr>");
                }
                b++;
            }
            questionText.append("</table></div>");
            questionText.append("</div></html>");

            JLabel lblQuestion = new JLabel(questionText.toString());
            lblQuestion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblQuestion.setOpaque(true);
            lblQuestion.setBackground(Color.WHITE);

            // Sự kiện khi rê chuột vào thì đổi màu nền
            int finalI = i;
            lblQuestion.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    questionItemPanel.setBackground(new Color(245, 245, 245));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    questionItemPanel.setBackground(Color.WHITE);
                }
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    currentQuestionIndex= finalI;
                    updateQuestion(currentQuestionIndex);  // Hàm này cập nhật nội dung câu hỏi
                }
            });

            questionItemPanel.add(lblQuestion, BorderLayout.CENTER);
            questionListPanel.add(questionItemPanel);

            if (i < questions.size() - 1) {
                questionListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        questionListPanel.revalidate();
        questionListPanel.repaint();
    }


    private void initializeTimer() {
        timer = new Timer(1000, e -> {
            timeRemaining--;
            int minutes = timeRemaining / 60;
            int seconds = timeRemaining % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
            
            if (timeRemaining == 600) { // 10 phút
                TimeWarningDialog.showWarning(this, 10);
            } else if (timeRemaining == 300) { // 5 phút
                TimeWarningDialog.showWarning(this, 5);
            } else if (timeRemaining == 60) { // 1 phút
                TimeWarningDialog.showWarning(this, 1);
            } else if (timeRemaining <= 0) {
                timer.stop();
                ArrayList<ArrayList<Integer>> true_answers=DapAnDung(answers);
                TinhDiem(result, selected_answers, true_answers,  questions);
                JOptionPane.showMessageDialog(this, "Hết thời gian làm bài!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);

            }
        });
        timer.start();
    }

    private static JPanel createHighlightedPanel(String label1, String label2) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        panel.setBackground(Color.WHITE);

        JLabel topLabel = new JLabel(label1, SwingConstants.CENTER);
        JLabel bottomLabel = new JLabel(label2, SwingConstants.CENTER);

        topLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bottomLabel.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(topLabel);
        panel.add(bottomLabel); // Sửa thành bottomLabel

        return panel;
    }

    public void showWindow() {
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    public void loadImageToLabel(JLabel label, String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            System.out.println("Đường dẫn ảnh trống hoặc null.");
            return;
        }

        BufferedImage originalImage = null;
        try {
            if (imagePath.startsWith("http") || imagePath.startsWith("https")) {
                // Load ảnh từ URL
                URL url = new URL(imagePath);
                originalImage = ImageIO.read(url);
            } else if (imagePath.startsWith("/")) {
                // Load ảnh từ resource trong project
                originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            } else {
                // Load ảnh từ file cục bộ
                File file = new File(imagePath);
                if (file.exists()) {
                    originalImage = ImageIO.read(file);
                } else {
                    System.out.println("Không tìm thấy file: " + imagePath);
                }
            }

            if (originalImage != null) {
                // Đảm bảo JLabel có kích thước hợp lý trước khi scale ảnh
                int labelWidth = Math.max(label.getWidth(), 150); // Nếu width = 0 thì dùng 150
                int labelHeight = Math.max(label.getHeight(), 150); // Nếu height = 0 thì dùng 150
                Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Không tải được ảnh: " + imagePath);
            }
        } catch (MalformedURLException e) {
            System.out.println("URL không hợp lệ: " + imagePath);
        } catch (IOException e) {
            System.out.println("Lỗi đọc ảnh: " + imagePath);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Ảnh không tồn tại trong resource: " + imagePath);
        }
    }
    private void updateListAnswerSelected(int i) {
        selected_answers.get(i).set(0, choiceA.isSelected() ? 1 : 0);
        selected_answers.get(i).set(1, choiceB.isSelected() ? 1 : 0);
        selected_answers.get(i).set(2, choiceC.isSelected() ? 1 : 0);
        selected_answers.get(i).set(3, choiceD.isSelected() ? 1 : 0);
    }
    private ArrayList<ArrayList<Integer>> DapAnDung(ArrayList<ArrayList<AnswerModel>> answers) {
        ArrayList<ArrayList<Integer>> true_answer = new ArrayList<>();
        int numQuestions = answers.size(); // Sử dụng kích thước của answers
        for (int i = 0; i < numQuestions; i++) {
            ArrayList<Integer> correctAnswers = new ArrayList<>(); // Tạo danh sách cho câu hỏi i
            for (int j = 0; j < answers.get(i).size(); j++) { // Duyệt tất cả đáp án của câu hỏi i
                correctAnswers.add(answers.get(i).get(j).getIsRight()); // Lấy giá trị isRight (0 hoặc 1)
            }
            true_answer.add(correctAnswers); // Thêm danh sách vào kết quả
        }
        return true_answer;
    }

    private void TinhDiem(ResultModel result, ArrayList<ArrayList<Integer>> selected_answers,
                          ArrayList<ArrayList<Integer>> true_answers, ArrayList<QuestionModel> questions) {
        int diem = 0;
        int socaudung = 0;
        int socausai = 0;
        int total_question = questions.size();

        for (int i = 0; i < total_question; i++) {
            if (SoSanhDapAnUserVaDapAnDung(selected_answers.get(i), true_answers.get(i))==true) {
                diem += questions.get(i).getQuestion_level();
                socaudung++;
            } else {
                socausai++;
            }
        }

        result.setDiem(diem);
        result.setSoCauDung(socaudung);
        result.setSoCauSai(socausai);
        result.setRaw_score(0);
        result.setStatus(1);
        result.setTgian_nop(LocalDateTime.now()); // Lưu thời gian hiện tại
        resultBUS.update(result);
        sendDaTaToDatabase();
        endTest();
    }

    public static boolean SoSanhDapAnUserVaDapAnDung(ArrayList<Integer> selected_answer, ArrayList<Integer> true_answer) {
        // Kiểm tra từng phần tử
        for (int i = 0; i < selected_answer.size(); i++) {
            if (!selected_answer.get(i).equals(true_answer.get(i))) {
                return false;
            }
        }

        return true;
    }

    private void endTest() {
        // 1. Vô hiệu hóa tất cả các thành phần trên JFrame
        for (Component component : getContentPane().getComponents()) {
            component.setEnabled(false); // Tắt các nút, ô nhập liệu, v.v.
        }
        // 2. Hiển thị ViewResultDialog (hộp thoại kết quả)
        UIResult uiResult=new UIResult(this,questions,answers,selected_answers,user,result,made);
        uiResult.setVisible(true);
    }
    private void sendDaTaToDatabase() {
        this.list_user_choose = new ArrayList<>();
        int totalQuestion = questions.size();
        for (int i = 0; i < totalQuestion; i++) {
            for (int j = 0; j < selected_answers.get(i).size(); j++) {
                if (selected_answers.get(i).get(j) != null && selected_answers.get(i).get(j).equals(1)) {
                    //(Integer answer_id, Integer exam_question_id, String user_id)
                    UserChooseModel userChooseModel = new UserChooseModel(
                            answers.get(i).get(j).getAnswer_id(),
                            list_exam_question.get(i).getExam_question_id(),
                            user.getUser_id()
                    );
                    list_user_choose.add(userChooseModel);
                }
            }
        }
        if(userChooseBUS.insertBatch(list_user_choose)){
            System.out.println("Đã lưu đáp án của thí sinh lên database thành công");
        }
    }






}
