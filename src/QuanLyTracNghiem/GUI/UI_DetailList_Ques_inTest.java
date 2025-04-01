package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.AnswerBUS;
import QuanLyTracNghiem.BUS.ExamQuestionBUS;
import QuanLyTracNghiem.BUS.QuestionBUS;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.ExamQuestionModel;
import QuanLyTracNghiem.DTO.QuestionModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UI_DetailList_Ques_inTest extends JPanel{
    // Khai báo các thành phần UI

    private JPanel  centerPanel, rightPanel, buttonPanel;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSearch, btnPrint, btnLoad;

    private JScrollPane scrollPane_rightPanel;

    // Định nghĩa các màu sắc chung
    private final Color backgroundColor = new Color(245, 245, 250);
    private final Color panelColor = new Color(255, 255, 255);
    private final Color accentColor = new Color(70, 130, 180);
    private final Color buttonHoverColor = new Color(100, 149, 237);
    private ArrayList<ExamModel>list_exam;


    private QuestionBUS questionBUS=new QuestionBUS();
    private AnswerBUS answerBUS=new AnswerBUS();
    private ExamQuestionBUS examQuestionBUS=new ExamQuestionBUS();
    private Integer havePanelButton;
    private ArrayList<QuestionModel> list_ques;
    private ArrayList<AnswerModel> list_answer;
    private ArrayList<ExamQuestionModel> list_exam_question;


    public UI_DetailList_Ques_inTest (ArrayList<ExamModel>list_exam, Integer havePanelButton) {
        this.list_exam=list_exam;
        this.havePanelButton=havePanelButton;
        addControls();
        addEvents();

    }

    public void getListQuestionAndExam_Question (){
        ArrayList <QuestionModel> list1=new ArrayList<>();
        ArrayList <ExamQuestionModel> list2=new ArrayList<>();
        for (int i=0; i<list_exam.size(); i++){
            list1.addAll(questionBUS.getListQuestionInExam(list_exam.get(i).getExam_id()));
            list2.addAll(examQuestionBUS.selectByExamQuesID(list_exam.get(i).getExam_id()));
        }
        this.list_ques=list1;
        this.list_exam_question=list2;

    }

    private void loadDataToTable() {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        if (list_ques == null || list_ques.isEmpty() || list_answer == null || list_answer.isEmpty()||list_exam_question==null||list_exam_question.isEmpty()) {
            System.out.println("Danh sách câu hỏi hoặc danh sách đáp án trống.");
            JOptionPane.showMessageDialog(null,"Đã xãy ra lỗi khi load dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Duyệt qua danh sách câu hỏi
        for (int i=0; i<list_ques.size(); i++) {
            // Lấy danh sách 4 đáp án của câu hỏi hiện tại
            ArrayList<AnswerModel> answers = new ArrayList<>();
            for (AnswerModel answer : list_answer) {
                if (answer.getQuestion_id().equals(list_ques.get(i).getQuestion_id())) {
                    answers.add(answer);
                }
            }
            // Thêm dữ liệu vào bảng
            Object[] rowData = {
                    list_ques.get(i).getQuestion_id(), // Mã câu hỏi
                    list_ques.get(i).getTopic_id(),  // Mã môn
                    list_exam_question.get(i).getExam_id(),     // Mã Đề
                    list_ques.get(i).getQuestion_content(),     // Nội dung câu hỏi
                    answers.get(0).getAnswer_content(),            // Đáp án A
                    answers.get(1).getAnswer_content(),            // Đáp án B
                    answers.get(2).getAnswer_content(),            // Đáp án C
                    answers.get(3).getAnswer_content(),             // Đáp án D
            };
            tableModel.addRow(rowData);
        }
    }






    private void addControls() {
        getListQuestionAndExam_Question();
        this.list_answer=answerBUS.getListAnswerInExam(list_exam);
        setLayout(new BorderLayout()); // Đặt layout chính cho JPanel
        setPreferredSize(new Dimension(1500, 900)); // Thiết lập kích thước panel chính

        createCenterPanel();
        createRightPanel();


        // Thêm các thành phần vào panel chính
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        loadDataToTable();
    }




    private void createCenterPanel() {
        centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(backgroundColor);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        searchPanel.setBackground(panelColor);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Components tìm kiếm
        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 18));
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 18));

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 18));
        btnSearch.setBackground(accentColor);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSearch.setBackground(buttonHoverColor);
            }
            public void mouseExited(MouseEvent e) {
                btnSearch.setBackground(accentColor);
            }
        });

        // Nút Load lại dự liệu
        btnLoad = new JButton("Load");
        btnLoad .setFont(new Font("Arial", Font.BOLD, 18));
        btnLoad .setBackground(accentColor);
        btnLoad .setForeground(Color.WHITE);
        btnLoad .setFocusPainted(false);
        btnLoad .setBorderPainted(false);
        btnLoad .setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLoad .addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLoad .setBackground(buttonHoverColor);
            }
            public void mouseExited(MouseEvent e) {
                btnLoad .setBackground(accentColor);
            }
        });

        btnPrint = new JButton("Xuất Document");
        btnPrint .setFont(new Font("Arial", Font.BOLD, 18));
        btnPrint .setBackground(accentColor);
        btnPrint .setForeground(Color.WHITE);
        btnPrint .setFocusPainted(false);
        btnPrint .setBorderPainted(false);
        btnPrint .setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPrint .addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLoad .setBackground(buttonHoverColor);
            }
            public void mouseExited(MouseEvent e) {
                btnLoad .setBackground(accentColor);
            }
        });

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnLoad);
        searchPanel.add(btnPrint);

        // Bảng câu hỏi
        String[] columnNames = {"Mã câu hỏi", "Mã môn","Mã Đế", "Nội dung", "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        
        // Style cho bảng
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(accentColor);
        table.getTableHeader().setForeground(Color.WHITE);
        
        // Thêm bảng vào scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createRightPanel() {
        rightPanel = new JPanel(new BorderLayout()); // Dùng BorderLayout giúp chiếm toàn bộ không gian
        rightPanel.setBackground(panelColor);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Đặt kích thước lớn hơn để JScrollPane có thể hiển thị thanh cuộn
        rightPanel.setPreferredSize(new Dimension(500, 900));

        // Thêm component lớn vào panel
        ArrayList<AnswerModel> list_answer=answerBUS.selectAnswerByQues_id(list_ques.get(0).getQuestion_id());
        Componet_Ques componetQuest = new Componet_Ques(list_ques.get(0),list_answer ,havePanelButton);

        // Tạo JScrollPane bọc rightPanel
        scrollPane_rightPanel = new JScrollPane(componetQuest );
        scrollPane_rightPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_rightPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_rightPanel.setPreferredSize(new Dimension(500, 900));
        rightPanel.add(scrollPane_rightPanel);

        // Thêm scrollPane_rightPanel vào container chính (ví dụ: mainPanel)
        this.add(rightPanel, BorderLayout.EAST);
    }




    // Phương thức tạo nút chức năng
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void addEvents() {
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component parentComponent = SwingUtilities.getRoot(UI_DetailList_Ques_inTest.this);
                if (parentComponent instanceof Window) {
                    ExportTestDialog exportTestDialog = new ExportTestDialog((JDialog) parentComponent, list_exam);
                    exportTestDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy cửa sổ cha hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                System.out.println(selectedRow);
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
            int i=0;
            for (QuestionModel question : filteredList) {

                ArrayList<AnswerModel> answers = answerBUS.selectAnswerByQues_id(question.getQuestion_id());
                if (answers.size() >= 4) {
                    Object[] rowData = {
                            question.getQuestion_id(), // Mã câu hỏi
                            question.getTopic_id(),  // Mã môn
                            "",     // Mã Đề
                            question.getQuestion_content(),     // Nội dung câu hỏi
                            answers.get(0).getAnswer_content(),            // Đáp án A
                            answers.get(1).getAnswer_content(),            // Đáp án B
                            answers.get(2).getAnswer_content(),            // Đáp án C
                            answers.get(3).getAnswer_content(),
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
            System.out.println("Row index không hợp lệ.");
            return;
        }

        // Lấy thông tin câu hỏi từ danh sách
        QuestionModel selectedQuestion = list_ques.get(rowIndex);
        ArrayList<AnswerModel> selectedAnswers = answerBUS.selectAnswerByQues_id(selectedQuestion.getQuestion_id());

        // Tạo lại component hiển thị thông tin câu hỏi
        Componet_Ques newComponent = new Componet_Ques(selectedQuestion, selectedAnswers,havePanelButton);

        // Cập nhật panel bên phải
        rightPanel.removeAll();
        scrollPane_rightPanel = new JScrollPane(newComponent);
        scrollPane_rightPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_rightPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_rightPanel.setPreferredSize(new Dimension(500, 900));

        rightPanel.add(scrollPane_rightPanel);
        rightPanel.revalidate();
        rightPanel.repaint();
        System.out.println("Panel phải đã được cập nhật.");
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
