package QuanLyTracNghiem.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DetailList_User_doTest extends JPanel {
    // Khai báo các components
    private JPanel topPanel, centerPanel, rightPanel, buttonPanel, searchPanel;
    private JTextField txt_search;
    private JButton btnSearch, btnExport, btnPrint, btnAdd , btnEdit , btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel labeltimkiem;
    private JScrollPane scrollPane_rightPanel;


    // Định nghĩa các màu sắc chung
    private final Color backgroundColor = new Color(245, 245, 250);
    private final Color panelColor = new Color(255, 255, 255);
    private final Color accentColor = new Color(70, 130, 180);
    private final Color buttonHoverColor = new Color(100, 149, 237);

    public DetailList_User_doTest() {
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
        add( scrollPane_rightPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
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

        searchPanel.add(labeltimkiem);
        searchPanel.add(txt_search);
        searchPanel.add(btnSearch);

        tableModel = new DefaultTableModel(new String[]{"uer_id", "Họ tên", "Điểm", "Thời gian nộp", "Số câu đúng","Số câu sai", "Kết quả"}, 0);
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

        Component_Result component=new Component_Result();

        rightPanel.add(component, BorderLayout.CENTER);

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
}
