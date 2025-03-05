package QuanLyTracNghiem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GiaoDienLamBaiThi extends JFrame {
    // Khai báo các thành phần giao diện
    private JPanel wrapperPanel, leftPanel, rightPanel, topPanel;
    private JPanel studentPanel, timerPanel, subjectPanel;
    private JPanel questionPanel, controlPanel, answerPanel;
    private JPanel questionListPanel;
    private JTextArea questionArea;
    private JLabel questionImage;
    private JCheckBox choiceA, choiceB, choiceC, choiceD;
    private JTextArea answerTextA, answerTextB, answerTextC, answerTextD;
    private JLabel answerImageA, answerImageB, answerImageC, answerImageD;
    private ButtonGroup choiceGroup;
    private Color backgroundColor = Color.WHITE;
    private Color accentColor = Color.BLUE;

    private JButton previousButton, nextButton, submitButton;
    private JLabel timerLabel;

    // Khai báo các biến dữ liệu
    private Timer timer;
    private int timeRemaining = 60 * 60;
    private List<String> questions = new ArrayList<>();
    private List<String[]> answers = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private List<Character> selectedAnswers = new ArrayList<>();

    private static final Color buttonColor = new Color(100, 149, 237);

    private JFrame mainframe;

    public GiaoDienLamBaiThi(JFrame mainframe) {
        this.mainframe=mainframe;
        addControls();
        addEvents();
        loadMockData();
        initializeTimer();
        updateQuestionList();
        updateQuestion();
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
        studentPanel = createHighlightedPanel("Tài khoản: 11190658", "Tên thí sinh: Quinn");
        timerPanel = createHighlightedPanel("Thời gian còn lại:", "60:00");
        subjectPanel = createHighlightedPanel("Môn Thi: Toán, Lí, Anh, Địa", "Số câu: 40");

        timerLabel = (JLabel) timerPanel.getComponent(1);
        
        topPanel.add(studentPanel);
        topPanel.add(timerPanel);
        topPanel.add(subjectPanel);
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
        questionImage.setBackground(Color.BLACK);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.7; gbc.fill = GridBagConstraints.BOTH;
        questionPanel.add(scrollQuestion, gbc);

        gbc.gridx = 1; gbc.weightx = 0.3;
        questionPanel.add(questionImage, gbc);

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

        answerTextA = new JTextArea(6, 15);
        answerTextB = new JTextArea(6, 15);
        answerTextC = new JTextArea(6, 15);
        answerTextD = new JTextArea(6, 15);

        answerImageA = new JLabel();
        answerImageB = new JLabel();
        answerImageC = new JLabel();
        answerImageD = new JLabel();

        JLabel[] answerImages = {answerImageA, answerImageB, answerImageC, answerImageD};
        for (JLabel label : answerImages) {
            label.setPreferredSize(new Dimension(150, 150));
            label.setOpaque(true);
            label.setBackground(Color.BLACK);
        }

        setupAnswerChoices();
    }

    private void setupAnswerChoices() {
        JCheckBox[] choices = {choiceA, choiceB, choiceC, choiceD};
        JLabel[] answerImages = {answerImageA, answerImageB, answerImageC, answerImageD};
        choiceGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            JPanel answerItem = new JPanel(new BorderLayout());
            answerItem.setBackground(backgroundColor);

            styleAnswerChoice(choices[i]);


            answerItem.add(choices[i], BorderLayout.WEST);
            answerItem.add(answerImages[i], BorderLayout.EAST);

            choiceGroup.add(choices[i]);
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
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                updateQuestion();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                updateQuestion();
            }
        });

        ActionListener answerListener = e -> {
            JRadioButton selectedButton = (JRadioButton) e.getSource();
            char answer = selectedButton.getText().charAt(0);
            selectedAnswers.set(currentQuestionIndex, answer);
            updateQuestionList();
        };

        choiceA.addActionListener(answerListener);
        choiceB.addActionListener(answerListener);
        choiceC.addActionListener(answerListener);
        choiceD.addActionListener(answerListener);
    }

    private void loadMockData() {
        questions.add("2 + 2 bằng bao nhiêu?");
        answers.add(new String[]{"A. 3", "B. 4", "C. 5", "D. 6"});
        selectedAnswers.add(null);

        questions.add("Thủ đô của Việt Nam là gì?");
        answers.add(new String[]{"A. Hà Nội", "B. TP.HCM", "C. Đà Nẵng", "D. Hải Phòng"});
        selectedAnswers.add(null);

        
        questions.add("Ngôn ngữ lập trình nào được sử dụng để phát triển ứng dụng Android?");
        answers.add(new String[]{"A. Java", "B. Swift", "C. Python", "D. Ruby"});
        selectedAnswers.add(null);

        questions.add("Đâu là số nguyên tố?");
        answers.add(new String[]{"A. 4", "B. 6", "C. 7", "D. 8"});
        selectedAnswers.add(null);

        questions.add("HTML là viết tắt của?");
        answers.add(new String[]{"A. High Text Markup Language", "B. Hyper Text Markup Language", 
                                "C. Hyper Text Making Language", "D. High Text Making Language"});
        selectedAnswers.add(null);

        questions.add("Ai là người sáng lập Facebook?");
        answers.add(new String[]{"A. Bill Gates", "B. Steve Jobs", "C. Mark Zuckerberg", "D. Jeff Bezos"});
        selectedAnswers.add(null);

        questions.add("1 GB bằng bao nhiêu MB?");
        answers.add(new String[]{"A. 1000 MB", "B. 1024 MB", "C. 100 MB", "D. 512 MB"});
        selectedAnswers.add(null);

        questions.add("Đâu không phải là một hệ điều hành?");
        answers.add(new String[]{"A. Windows", "B. Linux", "C. Oracle", "D. MacOS"});
        selectedAnswers.add(null);

        questions.add("Ngôn ngữ lập trình Java do công ty nào phát triển?");
        answers.add(new String[]{"A. Microsoft", "B. Apple", "C. Sun Microsystems", "D. IBM"});
        selectedAnswers.add(null);

        questions.add("RAM là viết tắt của?");
        answers.add(new String[]{"A. Read Access Memory", "B. Random Access Memory", 
                                "C. Read Available Memory", "D. Random Available Memory"});
        selectedAnswers.add(null);
    }

    private void updateQuestion() {
        questionArea.setText(questions.get(currentQuestionIndex));
        String[] answerChoices = answers.get(currentQuestionIndex);
        choiceA.setText(answerChoices[0]);
        choiceB.setText(answerChoices[1]);
        choiceC.setText(answerChoices[2]);
        choiceD.setText(answerChoices[3]);
        updateQuestionList();
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
            questionItemPanel.setMaximumSize(new Dimension(480, Short.MAX_VALUE)); // Đặt chiều rộng tối đa
            
            StringBuilder questionText = new StringBuilder();
            questionText.append("<html><div style='width: 600px;'>"); // Tăng width
            questionText.append(String.format("<table width='100%%'><tr><td width='70'><b style='color: #2196F3;'>Câu %d:</b></td>", (i + 1)));
            questionText.append("<td>").append(questions.get(i)).append("</td></tr></table>");
            
    // CHỈNH SỬA MÀU
            questionText.append("<div style='margin-left: 20px; margin-top: 5px;'>");
            String[] answerChoices = answers.get(i);
            questionText.append("<table width='100%'>");
            for (String choice : answerChoices) {
                questionText.append("<tr><td>").append(choice).append("</td></tr>");
            }
            questionText.append("</table></div>");
            
            if (selectedAnswers.get(i) != null) {
                char selected = selectedAnswers.get(i);
                String selectedAnswer = "";
                for (String answer : answers.get(i)) {
                    if (answer.charAt(0) == selected) {
                        selectedAnswer = answer;
                        break;
                    }
                }
                questionText.append("<div style='color: #4CAF50; font-weight: bold; margin-top: 5px; margin-left: 20px;'>")
                          .append("Đã chọn: ").append(selectedAnswer)
                          .append("</div>");
            } else {
                questionText.append("<div style='color: #FF5722; font-weight: bold; margin-top: 5px; margin-left: 20px;'>")
                          .append("Chưa chọn đáp án")
                          .append("</div>");
            }
            questionText.append("</div></html>");
            
            JButton btn = new JButton(questionText.toString());
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    questionItemPanel.setBackground(new Color(245, 245, 245));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    questionItemPanel.setBackground(Color.WHITE);
                }
            });
            
            int index = i;
            btn.addActionListener(e -> {
                currentQuestionIndex = index;
                updateQuestion();
            });
            
            questionItemPanel.add(btn);
            

            questionListPanel.add(questionItemPanel);
            // Thêm khoảng cách giữa các câu hỏi
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
        panel.add(bottomLabel);
        
        return panel;
    }

    public void showWindow() {
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(400, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GiaoDienLamBaiThi examGUI = new GiaoDienLamBaiThi(mainFrame);
        examGUI.setVisible(true);
    }
}
