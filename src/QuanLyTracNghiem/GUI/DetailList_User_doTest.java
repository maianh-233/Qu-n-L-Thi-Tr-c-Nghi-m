package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.ResultBUS;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.ResultModel;
import QuanLyTracNghiem.DTO.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DetailList_User_doTest extends JPanel {
    // Khai báo các components
    private JPanel topPanel, centerPanel, rightPanel, buttonPanel, searchPanel;
    private JTextField txt_search;
    private JButton btnSearch, btnExport, btnPrint, btnAdd , btnEdit , btnDelete, btnLoad;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel labeltimkiem;
    private JScrollPane scrollPane_rightPanel;


    // Định nghĩa các màu sắc chung
    private final Color backgroundColor = new Color(245, 245, 250);
    private final Color panelColor = new Color(255, 255, 255);
    private final Color accentColor = new Color(70, 130, 180);
    private final Color buttonHoverColor = new Color(100, 149, 237);
    private ArrayList<ExamModel> list_exam;
    private ArrayList<UserModel> list_user;
    private ArrayList<ResultModel>list_resultmodel;
    private ResultBUS resultBUS =new ResultBUS();
    private Integer havePanelButton;

    public DetailList_User_doTest(ArrayList<ExamModel>list_exam, Integer havePanelButton) {


        this.havePanelButton=havePanelButton;
        this.list_exam=list_exam;
        this.list_user=resultBUS.selectListInfoCuaUserThamGiaTest(this.list_exam);
        this.list_resultmodel=resultBUS.selectListKetQuaCuaUserThamGiaTest(this.list_exam);
        setPreferredSize(new Dimension(1500, 900));
        setLayout(new BorderLayout(15, 15));
        initializePanels();
    }

    private void initializePanels() {
        createTopPanel();
        createCenterPanel();
        createRightPanel();
        createButtonPanel();

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add( rightPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setVisible(havePanelButton==1);
        loadDataToTable();
        addTableSelectionListener();
        // Gán sự kiện cho nút Tìm kiếm

    }

    private void createTopPanel() {
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, accentColor, w, h, buttonHoverColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
    }

    private void createCenterPanel() {
        centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(backgroundColor);

        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        searchPanel.setBackground(panelColor);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        labeltimkiem = new JLabel("Tìm Kiếm: ");
        labeltimkiem.setFont(new Font("Arial", Font.BOLD, 18));
        txt_search = new JTextField(15);
        txt_search.setFont(new Font("Arial", Font.PLAIN, 18));

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 18));
        btnSearch.setBackground(accentColor);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLoad = new JButton("Load");
        btnLoad.setFont(new Font("Arial", Font.BOLD, 18));
        btnLoad.setBackground(accentColor);
        btnLoad.setForeground(Color.WHITE);
        btnLoad.setFocusPainted(false);
        btnLoad.setBorderPainted(false);
        btnLoad.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchPanel.add(labeltimkiem);
        searchPanel.add(txt_search);
        searchPanel.add(btnSearch);
        searchPanel.add(btnLoad);

        tableModel = new DefaultTableModel(new String[]{"user_id", "Họ tên", "Điểm", "Thời gian nộp", "Số câu đúng","Số câu sai", "Kết quả"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(accentColor);
        table.getTableHeader().setForeground(Color.WHITE);

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

        Component_Result component=new Component_Result(this.list_user.get(0),this.list_resultmodel.get(0));

        rightPanel.add(component, BorderLayout.CENTER);

        // Tạo JScrollPane bọc rightPanel
        scrollPane_rightPanel = new JScrollPane(component);
        scrollPane_rightPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_rightPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_rightPanel.setPreferredSize(new Dimension(500, 900));
        rightPanel.add(scrollPane_rightPanel);
        // Thêm scrollPane_rightPanel vào container chính (ví dụ: mainPanel)
        this.add(rightPanel, BorderLayout.EAST);


    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setBackground(panelColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm các nút xuất báo cáo
        btnPrint = createActionButton("In", new Color(52, 152, 219));


        buttonPanel.add(btnPrint);
        buttonPanel.add(Box.createHorizontalStrut(10));

        // Thêm các nút chỉnh sửa
        btnAdd = createActionButton("Thêm", new Color(46, 204, 113));

        btnDelete = createActionButton("Xóa", new Color(231, 76, 60));

        buttonPanel.add(btnAdd);
        buttonPanel.add(Box.createHorizontalStrut(10));

        buttonPanel.add(btnDelete);
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    private void loadDataToTable() {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        if (list_resultmodel == null || list_resultmodel.isEmpty() || list_user == null || list_user.isEmpty()) {
            System.out.println("Danh sách kết quả hoặc danh sách thí sinh trống.");
            JOptionPane.showMessageDialog(null,"Đã xãy ra lỗi khi load dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Duyệt qua danh sách người dùng
        for (int i=0; i<list_user.size(); i++) {
            // Thêm dữ liệu vào bảng
            //(new String[]{"user_id", "Họ tên", "Điểm", "Thời gian nộp", "Số câu đúng","Số câu sai", "Kết quả"}, 0);
            Object[] rowData = {
                    list_user.get(i).getUser_id(), // User_id
                    list_user.get(i).getFullname(), //Họ tên
                    list_resultmodel.get(i).getDiem(),  // Điểm
                    list_resultmodel.get(i).getTgian_nop(),     // Thòi gian nộp
                    list_resultmodel.get(i).getSoCauDung(),     // Số câu đúng
                    list_resultmodel.get(i).getSoCauSai(),            // Số câu sai
                    (list_resultmodel.get(i).getStatus() == 1) ? "Đạt" : "Không Đạt"// Kết quả
            };
            tableModel.addRow(rowData);
        }
    }


    private void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();

                // Lấy dữ liệu từ danh sách dựa trên chỉ số dòng đã chọn
                UserModel selectedUser = list_user.get(selectedRow);
                ResultModel selectedResult = list_resultmodel.get(selectedRow);

                // Cập nhật rightPanel với thông tin mới
                updateRightPanel(selectedUser, selectedResult);
            }
        });

        btnSearch.addActionListener(e -> searchUser());

        // Gán sự kiện cho nút Load lại dữ liệu
        btnLoad.addActionListener(e -> loadDataToTable());

    }

    private void updateRightPanel(UserModel user, ResultModel result) {
        rightPanel.removeAll(); // Xóa nội dung cũ

        Component_Result component = new Component_Result(user, result);
        scrollPane_rightPanel = new JScrollPane(component);
        scrollPane_rightPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_rightPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_rightPanel.setPreferredSize(new Dimension(500, 900));

        rightPanel.add(scrollPane_rightPanel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void searchUser() {
        String searchText = txt_search.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            loadDataToTable(); // Nếu ô tìm kiếm trống, hiển thị lại toàn bộ danh sách
            return;
        }

        ArrayList<UserModel> filteredUsers = new ArrayList<>();
        ArrayList<ResultModel> filteredResults = new ArrayList<>();

        for (int i = 0; i < list_user.size(); i++) {
            UserModel user = list_user.get(i);
            String userId = String.valueOf(user.getUser_id());
            String fullName = user.getFullname().toLowerCase();

            // Tính khoảng cách Levenshtein
            int distance = levenshteinDistance(searchText, fullName);
            int threshold = Math.max(2, searchText.length() / 3); // Cho phép sai lệch 30%

            // Kiểm tra nếu tìm kiếm gần đúng hoặc khớp ID
            if (fullName.contains(searchText) || userId.contains(searchText) || distance <= threshold) {
                filteredUsers.add(user);
                filteredResults.add(list_resultmodel.get(i));
            }
        }

        // Cập nhật bảng
        tableModel.setRowCount(0);
        for (int i = 0; i < filteredUsers.size(); i++) {
            Object[] rowData = {
                    filteredUsers.get(i).getUser_id(), // User_id
                    filteredUsers.get(i).getFullname(), // Họ tên
                    filteredResults.get(i).getDiem(), // Điểm
                    filteredResults.get(i).getTgian_nop(), // Thời gian nộp
                    filteredResults.get(i).getSoCauDung(), // Số câu đúng
                    filteredResults.get(i).getSoCauSai(), // Số câu sai
                    (filteredResults.get(i).getStatus() == 1) ? "Đạt" : "Không Đạt" // Kết quả
            };
            tableModel.addRow(rowData);
        }

        if (filteredUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thí sinh phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
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
