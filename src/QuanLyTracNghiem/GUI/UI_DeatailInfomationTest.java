package QuanLyTracNghiem.GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class UI_DetailInformationTest extends JPanel {
    private JLabel lbl_testid, lbl_testname, lbl_createat, lbl_socaude, lbl_socauthuong, lbl_socaukho, lbl_tongcauhoi;
    private JComboBox<String> combobox_tgianlambai;
    private JButton btn_edit, btn_load, btn_chonNgay;
    private List<JLabel> lbl_listMade;
    private List<JLabel> lbl_listTopics;

    public UI_DetailInformationTest() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 600));

        Font titleFont = new Font("Arial", Font.BOLD, 18);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Color accentColor = new Color(70, 130, 180);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(950, 550));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                "Thông tin bài thi", TitledBorder.LEFT, TitledBorder.TOP, titleFont, accentColor));
        infoPanel.setPreferredSize(new Dimension(450, 400));

        lbl_testid = createLabel("1233344", labelFont);
        lbl_testname = createLabel("Bài Thi Cuối Kì", labelFont);
        lbl_createat = createLabel("02/03/2025 00:04:40", labelFont);
        lbl_tongcauhoi = createLabel("6", labelFont);
        lbl_socaude = createLabel("2", labelFont);
        lbl_socaukho = createLabel("2", labelFont);
        lbl_socauthuong = createLabel("2", labelFont);

        String[] timeOptions = {"30 phút", "60 phút", "90 phút"};
        combobox_tgianlambai = new JComboBox<>(timeOptions);
        combobox_tgianlambai.setSelectedIndex(0);
        combobox_tgianlambai.setFont(labelFont);
        combobox_tgianlambai.setPreferredSize(new Dimension(200, 40));

        btn_chonNgay = createButton("Chọn Ngày", labelFont, 200, 40);

        addRow(infoPanel, "Mã vào phòng:", lbl_testid, gbc, labelFont);
        addRow(infoPanel, "Tên bài thi:", lbl_testname, gbc, labelFont);
        addRow(infoPanel, "Ngày tạo bài thi:", lbl_createat, gbc, labelFont);
        addRow(infoPanel, "Tổng số câu hỏi:", lbl_tongcauhoi, gbc, labelFont);
        addRow(infoPanel, "Số câu dễ:", lbl_socaude, gbc, labelFont);
        addRow(infoPanel, "Số câu khó:", lbl_socaukho, gbc, labelFont);
        addRow(infoPanel, "Số câu thường:", lbl_socauthuong, gbc, labelFont);
        addRow(infoPanel, "Thời gian làm bài:", combobox_tgianlambai, gbc, labelFont);
        addRow(infoPanel, "Chọn Ngày:", btn_chonNgay, gbc, labelFont);

        JPanel topicPanel = new JPanel(new GridBagLayout());
        topicPanel.setBackground(Color.WHITE);
        topicPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                "Chủ đề bài test", TitledBorder.LEFT, TitledBorder.TOP, titleFont, accentColor));
        topicPanel.setPreferredSize(new Dimension(450, 200));

        lbl_listTopics = new ArrayList<>();
        String[] topicNames = {"Toán", "Lý", "Hóa", "Sinh", "Tin"};

        for (int i = 0; i < topicNames.length; i++) {
            JLabel lbl_topic = createLabel(topicNames[i], labelFont);
            lbl_listTopics.add(lbl_topic);
            addRow(topicPanel, "Chủ đề " + (i + 1) + ":", lbl_topic, gbc, labelFont);
        }

        JPanel madePanel = new JPanel(new GridBagLayout());
        madePanel.setBackground(Color.WHITE);
        madePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                "Danh sách mã đề", TitledBorder.LEFT, TitledBorder.TOP, titleFont, accentColor));
        madePanel.setPreferredSize(new Dimension(450, 200));

        lbl_listMade = new ArrayList<>();
        String[] madeNames = {
                "Mã đề 101: Dành cho học sinh trung bình.",
                "Mã đề 202: Dành cho học sinh khá.",
                "Mã đề 303: Dành cho học sinh giỏi.",
                "Mã đề 404: Mã đề nâng cao."
        };

        for (int i = 0; i < madeNames.length; i++) {
            JLabel lbl_made = createLabel(madeNames[i], labelFont);
            lbl_listMade.add(lbl_made);
            addRow(madePanel, "Mã đề " + (i + 1) + ":", lbl_made, gbc, labelFont);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        btn_edit = createButton("Chỉnh sửa", titleFont, 200, 40);
        btn_load = createButton("Tải lại", titleFont, 200, 40);

        buttonPanel.add(btn_edit);
        buttonPanel.add(btn_load);
        buttonPanel.setPreferredSize(new Dimension(900, 60));


        mainPanel.add(infoPanel, BorderLayout.WEST);

        mainPanel.add(topicPanel, BorderLayout.CENTER);


        mainPanel.add(madePanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

    }
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setPreferredSize(new Dimension(350, 40));
        return label;
    }

    private JButton createButton(String text, Font font, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    private void addRow(JPanel panel, String labelText, Component comp, GridBagConstraints gbc, Font labelFont) {
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(200, 40));
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(comp, gbc);
    }
}