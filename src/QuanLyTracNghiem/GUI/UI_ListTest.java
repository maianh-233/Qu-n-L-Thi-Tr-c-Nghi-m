package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.TestBUS;
import QuanLyTracNghiem.DTO.TestModel;
import QuanLyTracNghiem.DTO.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

class UI_ListTest extends JPanel {
    //test đang được chọn
    private String testId_selected;
    private Integer have_PanelButton;
    private JFrame mainframe;
    private JButton detailButton;
    private  JTable pastTestTable,upcomingTestTable;
    private UserModel login_user;

    private TestBUS testBUS; // Giả sử bạn có lớp BUS tên TestBUS

    public UI_ListTest(JFrame mainframe,UserModel login_user) {
        this.mainframe = mainframe;
        this.login_user=login_user;
        this.testBUS = new TestBUS(); // Khởi tạo BUS
        addControls();
        loadPastTests(login_user.getUser_id()); // Thay thế "creator_id" bằng giá trị thực tế
        loadUpcomingTests(login_user.getUser_id());
    }
    public void loadPastTests(String creatorId) {
        DefaultTableModel model = (DefaultTableModel) pastTestTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        ArrayList<TestModel> pastTests = testBUS.getPastTestsByCreator(creatorId);
        for (TestModel test : pastTests) {
            model.addRow(new Object[]{
                    test.getTest_id(),
                    test.getCreate_at(),
                    test.getTest_name(),
                    test.getNgbd_thi(),
                    test.getTgianlambai()
            });
        }
    }

    public void loadUpcomingTests(String creatorId) {
        DefaultTableModel model = (DefaultTableModel) upcomingTestTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        ArrayList<TestModel> upcomingTests = testBUS.getUpcomingTestsByCreator(creatorId);
        for (TestModel test : upcomingTests) {
            model.addRow(new Object[]{
                    test.getTest_id(),
                    test.getCreate_at(),
                    test.getTest_name(),
                    test.getNgbd_thi(),
                    test.getTgianlambai()
            });
        }
    }

    public void addControls() {
        setPreferredSize(new Dimension(1400, 800));
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        String[] columnNames = {"test_id", "create_at", "test_name", "ngaybd_thi", "tgianlambai"};

        pastTestTable = createStyledTable(columnNames);
        upcomingTestTable = createStyledTable(columnNames);


        JScrollPane pastScroll = new JScrollPane(pastTestTable);
        JScrollPane upcomingScroll = new JScrollPane(upcomingTestTable);

        pastScroll.setPreferredSize(new Dimension(600, 500));
        upcomingScroll.setPreferredSize(new Dimension(600, 500));

        JPanel pastTestPanel = new JPanel(new BorderLayout());
        JLabel pastLabel = new JLabel("Danh sách các bài test đã diễn ra", SwingConstants.CENTER);
        pastLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pastTestPanel.add(pastLabel, BorderLayout.NORTH);
        pastTestPanel.add(pastScroll, BorderLayout.CENTER);

        JPanel upcomingTestPanel = new JPanel(new BorderLayout());
        JLabel upcomingLabel = new JLabel("Danh sách các bài test chưa diễn ra", SwingConstants.CENTER);
        upcomingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        upcomingTestPanel.add(upcomingLabel, BorderLayout.NORTH);
        upcomingTestPanel.add(upcomingScroll, BorderLayout.CENTER);

        centerPanel.add(pastTestPanel);
        centerPanel.add(upcomingTestPanel);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        detailButton = new JButton("Xem Chi Tiết");
        detailButton.setPreferredSize(new Dimension(200, 50));

        footerPanel.add(detailButton);

        JButton reloadButton = new JButton("Tải lại");
        reloadButton.setPreferredSize(new Dimension(200, 50));
        reloadButton.addActionListener(e -> {
            loadPastTests(login_user.getUser_id());
            loadUpcomingTests(login_user.getUser_id());
        });
        footerPanel.add(reloadButton);

        pastTestTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && pastTestTable.getSelectedRow() != -1) {
                int selectedRow = pastTestTable.getSelectedRow();
                testId_selected = pastTestTable.getValueAt(selectedRow, 0).toString();
                have_PanelButton=0;
                System.out.println("Test ID (Past): " + testId_selected);
            }
        });

        upcomingTestTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && upcomingTestTable.getSelectedRow() != -1) {
                int selectedRow = upcomingTestTable.getSelectedRow();
                testId_selected = upcomingTestTable.getValueAt(selectedRow, 0).toString();
                have_PanelButton=1;
                System.out.println("Test ID (Upcoming): " + testId_selected);
            }
        });

        detailButton.addActionListener(e -> {
            new UI_TestDetail(mainframe, testId_selected, have_PanelButton).setVisible(true);

        }); // Sửa lỗi



        add(centerPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JTable createStyledTable(String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setForeground(Color.BLACK); // Chữ màu đen
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(Color.BLUE);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        return table;
    }

}