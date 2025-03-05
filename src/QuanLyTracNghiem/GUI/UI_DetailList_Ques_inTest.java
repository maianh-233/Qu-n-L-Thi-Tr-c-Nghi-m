package QuanLyTracNghiem.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UI_DetailList_Ques_inTest extends JPanel{
    // Khai báo các thành phần UI

    private JPanel  centerPanel, rightPanel, buttonPanel;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete , btnExport , btnPrint;

    private JScrollPane scrollPane_rightPanel;

    // Định nghĩa các màu sắc chung
    private final Color backgroundColor = new Color(245, 245, 250);
    private final Color panelColor = new Color(255, 255, 255);
    private final Color accentColor = new Color(70, 130, 180);
    private final Color buttonHoverColor = new Color(100, 149, 237);



    public UI_DetailList_Ques_inTest () {
        addControls();
        addEvents();
    }



    private void addControls() {
        setLayout(new BorderLayout()); // Đặt layout chính cho JPanel
        setPreferredSize(new Dimension(1500, 900)); // Thiết lập kích thước panel chính

        createCenterPanel();
        createRightPanel();
        createButtonPanel();

        // Thêm các thành phần vào panel chính
        add(centerPanel, BorderLayout.CENTER);
        add(scrollPane_rightPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    private void addEvents() {
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
        
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

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
        Componet_Ques componetQuest = new Componet_Ques();
        rightPanel.add(componetQuest, BorderLayout.CENTER);

        // Tạo JScrollPane bọc rightPanel
        scrollPane_rightPanel = new JScrollPane(rightPanel);
        scrollPane_rightPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_rightPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_rightPanel.setPreferredSize(new Dimension(500, 900));

        // Thêm scrollPane_rightPanel vào container chính (ví dụ: mainPanel)
        this.add(scrollPane_rightPanel, BorderLayout.EAST);
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

        btnEdit= createActionButton("Sửa", new Color(46, 204, 113));
        btnAdd = createActionButton("Thêm", new Color(46, 204, 113));

        btnDelete = createActionButton("Xóa", new Color(231, 76, 60));
        buttonPanel.add(btnEdit);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnAdd);
        buttonPanel.add(Box.createHorizontalStrut(10));

        buttonPanel.add(btnDelete);
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

    // Thêm phương thức hiển thị dialog import

}
