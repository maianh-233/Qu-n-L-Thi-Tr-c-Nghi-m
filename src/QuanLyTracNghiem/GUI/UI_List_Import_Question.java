package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.AnswerBUS;
import QuanLyTracNghiem.BUS.QuestionBUS;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.QuestionModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class UI_List_Import_Question_Dialog extends JDialog {
    private JPanel centerPanel, rightPanel, buttonPanel;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete, btnLoad;
    private JScrollPane scrollPane_rightPanel;

    private final Color backgroundColor = new Color(245, 245, 250);
    private final Color panelColor = new Color(255, 255, 255);
    private final Color accentColor = new Color(70, 130, 180);
    private final Color buttonHoverColor = new Color(100, 149, 237);

    private QuestionBUS questionBUS = new QuestionBUS();
    private AnswerBUS answerBUS = new AnswerBUS();
    private Integer havePanelButton;
    private ArrayList<QuestionModel> list_ques;
    private ArrayList<AnswerModel> list_answer;

    public UI_List_Import_Question_Dialog(JFrame parent, ArrayList<QuestionModel> list_ques, ArrayList<AnswerModel> list_answer, Integer havePanelButton) {
        super(parent, "Danh sách câu hỏi", true);
        this.list_answer = list_answer;
        this.list_ques = list_ques;
        this.havePanelButton = havePanelButton;

        setSize(1200, 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        addControls();
        addEvents();
        loadDataToTable();
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        if (list_ques == null || list_ques.isEmpty() || list_answer == null || list_answer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi load dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (QuestionModel question : list_ques) {
            ArrayList<AnswerModel> answers = answerBUS.selectAnswerByQues_id(question.getQuestion_id());
            Object[] rowData = {
                    question.getQuestion_id(),
                    question.getTopic_id(),
                    question.getQuestion_content(),
                    answers.get(0).getAnswer_content(),
                    answers.get(1).getAnswer_content(),
                    answers.get(2).getAnswer_content(),
                    answers.get(3).getAnswer_content()
            };
            tableModel.addRow(rowData);
        }
    }

    private void addControls() {
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(backgroundColor);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(panelColor);
        searchPanel.setBorder(BorderFactory.createLineBorder(accentColor, 1));

        JLabel lblSearch = new JLabel("Tìm kiếm:");
        txtSearch = new JTextField(20);
        btnSearch = createButton("Tìm kiếm");
        btnLoad = createButton("Load");

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnLoad);

        String[] columnNames = {"Mã câu hỏi", "Mã môn", "Nội dung", "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        createRightPanel();
        add(rightPanel, BorderLayout.EAST);
    }

    private void createRightPanel() {
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(400, 700));
        if (!list_ques.isEmpty()) {
            QuestionModel firstQuestion = list_ques.get(0);
            ArrayList<AnswerModel> answers = answerBUS.selectAnswerByQues_id(firstQuestion.getQuestion_id());
            Componet_Ques component = new Componet_Ques(firstQuestion, answers, havePanelButton);
            scrollPane_rightPanel = new JScrollPane(component);
            rightPanel.add(scrollPane_rightPanel);
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(buttonHoverColor);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(accentColor);
            }
        });
        return button;
    }

    private void addEvents() {
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                updateRightPanel(selectedRow);
            }
        });

        btnLoad.addActionListener(e -> loadDataToTable());

        btnSearch.addActionListener(e -> {
            String searchText = txtSearch.getText().trim().toLowerCase();
            if (searchText.isEmpty()) {
                loadDataToTable();
                return;
            }

            ArrayList<QuestionModel> filteredList = new ArrayList<>();
            for (QuestionModel question : list_ques) {
                String questionContent = question.getQuestion_content().toLowerCase();
                int distance = levenshteinDistance(searchText, questionContent);

                // Chỉ lấy những câu hỏi có khoảng cách nhỏ hơn hoặc bằng 30% độ dài chuỗi nhập vào
                int threshold = Math.max(2, searchText.length() / 3);
                if (distance <= threshold) {
                    filteredList.add(question);
                }
            }

            tableModel.setRowCount(0);
            for (QuestionModel question : filteredList) {
                ArrayList<AnswerModel> answers = answerBUS.selectAnswerByQues_id(question.getQuestion_id());
                if (answers.size() >= 4) {
                    Object[] rowData = {
                            question.getQuestion_id(),
                            question.getTopic_id(),
                            question.getQuestion_content(),
                            answers.get(0).getAnswer_content(),
                            answers.get(1).getAnswer_content(),
                            answers.get(2).getAnswer_content(),
                            answers.get(3).getAnswer_content()
                    };
                    tableModel.addRow(rowData);
                }
            }

            if (filteredList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy câu hỏi phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


    private void updateRightPanel(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= list_ques.size()) {
            return;
        }
        QuestionModel selectedQuestion = list_ques.get(rowIndex);
        ArrayList<AnswerModel> selectedAnswers = answerBUS.selectAnswerByQues_id(selectedQuestion.getQuestion_id());
        Componet_Ques newComponent = new Componet_Ques(selectedQuestion, selectedAnswers, havePanelButton);
        rightPanel.removeAll();
        scrollPane_rightPanel = new JScrollPane(newComponent);
        rightPanel.add(scrollPane_rightPanel);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(
                            dp[i - 1][j] + 1,  // Xóa
                            dp[i][j - 1] + 1   // Chèn
                    ), dp[i - 1][j - 1] + cost);  // Thay thế
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

}
