package QuanLyTracNghiem.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

class UI_ListTest extends JPanel {
    private JFrame mainframe;
    private JButton detailButton;

    public UI_ListTest(JFrame mainframe) {
        this.mainframe = mainframe;
        addControls();
    }

    public void addControls() {
        setPreferredSize(new Dimension(1400, 800));
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        String[] columnNames = {"test_id", "create_at", "test_name", "ngaybd_thi", "tgianlambai"};

        JTable pastTestTable = createStyledTable(columnNames);
        JTable upcomingTestTable = createStyledTable(columnNames);

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
        detailButton.addActionListener(e -> new UI_TestDetail(mainframe).setVisible(true)); // Sửa lỗi
        footerPanel.add(detailButton);

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