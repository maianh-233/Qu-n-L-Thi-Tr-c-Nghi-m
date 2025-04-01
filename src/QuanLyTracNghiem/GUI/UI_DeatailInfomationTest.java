package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.ExamBUS;
import QuanLyTracNghiem.BUS.TestBUS;
import QuanLyTracNghiem.BUS.TopicTestBUS;
import QuanLyTracNghiem.DTO.ExamModel;
import QuanLyTracNghiem.DTO.TestModel;
import QuanLyTracNghiem.DTO.TopicModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class UI_DetailInformationTest extends JPanel {
    private JLabel lbl_testid, lbl_testname, lbl_createat, lbl_socaude, lbl_socauthuong, lbl_socaukho, lbl_tongcauhoi,lbl_date;
    private JComboBox<String> combobox_tgianlambai;
    private JButton btn_edit, btn_load, btn_chonNgay;
    private List<JLabel> lbl_listMade, lbl_listTopics;
    private TestBUS testBUS = new TestBUS();
    private TopicTestBUS topicTestBUS=new TopicTestBUS();
    private ExamBUS examBUS=new ExamBUS();
    private Integer test_id, have_panelbutton;
    private TestModel testModel;
    private LocalDateTime selectedDateTime;

    private void showDatePickerDialog() {
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MM-dd HH:mm:ss");
        dateSpinner.setEditor(dateEditor);

        int option = JOptionPane.showConfirmDialog(null, dateSpinner, "Chọn thời gian", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            Date selectedDate = (Date) dateSpinner.getValue();
            LocalDateTime selectedDateTime = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int currentYear = LocalDateTime.now().getYear();
            selectedDateTime = selectedDateTime.withYear(currentYear);

            if (selectedDateTime.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(null, "Thời gian phải sau thời gian hiện tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                showDatePickerDialog();
            } else {
                this.selectedDateTime = selectedDateTime;
                String test_name = null; // Không cập nhật tên bài kiểm tra
                Integer tgianlambai = null; // Không cập nhật thời gian làm bài

                if (testBUS.editTest(selectedDateTime, test_name, tgianlambai, test_id)) {
                    System.out.println("Cập nhật thời gian bắt đầu thi thành công!");
                    JOptionPane.showMessageDialog(null, "Cập nhật thời gian bắt đầu thi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    lbl_date.setText(selectedDate+"");
                } else {
                    System.out.println("Cập nhật thời gian bắt đầu thi thất bại!");
                    JOptionPane.showMessageDialog(null, "Cập nhật thời gian bắt đầu thi thất bại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public UI_DetailInformationTest(String test, int have_panelbutton) {

        this.test_id = Integer.parseInt(test);
        this.have_panelbutton = have_panelbutton;
        this.testModel = testBUS.findTestById(test_id);

        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 600));

        Font titleFont = new Font("Arial", Font.BOLD, 18);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Color accentColor = new Color(70, 130, 180);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(1000, 550));

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                "Thông tin bài thi", TitledBorder.LEFT, TitledBorder.TOP, titleFont, accentColor));
        infoPanel.setPreferredSize(new Dimension(450, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;

        lbl_testid = createLabel(testModel.getTest_id() + "", labelFont);
        lbl_testname = createLabel(testModel.getTest_name(), labelFont);
        lbl_createat = createLabel(testModel.getCreate_at() + "", labelFont);
        lbl_tongcauhoi = createLabel(testModel.getSocauhoi() + "", labelFont);
        lbl_socaude = createLabel(testModel.getSocaude() + "", labelFont);
        lbl_socaukho = createLabel(testModel.getSocaukho() + "", labelFont);
        lbl_socauthuong = createLabel(testModel.getSocauthuong() + "", labelFont);
        lbl_date = createLabel(testModel.getNgbd_thi() + "", labelFont);
        String[] timeOptions = {"30", "60", "90"};
        combobox_tgianlambai = new JComboBox<>(timeOptions);
        combobox_tgianlambai.setSelectedItem(testModel.getTgianlambai() + "");
        combobox_tgianlambai.setFont(labelFont);
        combobox_tgianlambai.setPreferredSize(new Dimension(200, 40));

        btn_chonNgay = createButton("Chọn Ngày", labelFont, 200, 40);

        addRow(infoPanel, "Mã vào phòng:", lbl_testid, gbc, labelFont);
        addRow(infoPanel, "Tên bài:", lbl_testname, gbc, labelFont);
        addRow(infoPanel, "Ngày tạo:", lbl_createat, gbc, labelFont);
        addRow(infoPanel, "Câu hỏi:", lbl_tongcauhoi, gbc, labelFont);
        addRow(infoPanel, "Câu dễ:", lbl_socaude, gbc, labelFont);
        addRow(infoPanel, "Câu khó:", lbl_socaukho, gbc, labelFont);
        addRow(infoPanel, "Câu thường:", lbl_socauthuong, gbc, labelFont);
        addRow(infoPanel, "Thời gian làm bài:", combobox_tgianlambai, gbc, labelFont);
        addRow(infoPanel, "Thời gian bắt đầu:",lbl_date, gbc, labelFont);
        addRow(infoPanel, "Chọn thời gian: " , btn_chonNgay, gbc, labelFont);
        btn_chonNgay.setVisible(have_panelbutton == 1);

        btn_chonNgay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDatePickerDialog();
            }
        });

        JPanel subPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        subPanel.setBackground(Color.WHITE);

        JPanel madePanel = createBorderedPanel("Danh sách mã đề", titleFont, accentColor);
        lbl_listMade = new ArrayList<>();
        ArrayList<ExamModel>list_examintest= examBUS.selectByTestID(test_id);
        for (int i = 0; i < list_examintest.size(); i++) {
            JLabel lbl_made = createLabel(list_examintest.get(i).getExam_code(), labelFont);
            lbl_listMade.add(lbl_made);
            addRow(madePanel, "Mã đề " + (i + 1) + ":", lbl_made, gbc, labelFont);
        }

        JPanel topicPanel = createBorderedPanel("Chủ đề bài test", titleFont, accentColor);

        List<TopicModel> list_topic_intesst=topicTestBUS.selectTopicsInTest(test_id);
        lbl_listTopics = new ArrayList<>();
        for (int i = 0; i < list_topic_intesst.size(); i++) {
            JLabel lbl_topic = createLabel(list_topic_intesst.get(i).getTopic_name(), labelFont);
            lbl_listTopics.add(lbl_topic);
            addRow(topicPanel, "Chủ đề " + (i + 1) + ":", lbl_topic, gbc, labelFont);
        }

        subPanel.add(madePanel);
        subPanel.add(topicPanel);
        JPanel footer=create_PanelButton();
        footer.setVisible(have_panelbutton == 1);
        mainPanel.add(infoPanel);
        mainPanel.add(subPanel);
        add(mainPanel, BorderLayout.CENTER);
        add(footer,BorderLayout.SOUTH);
        addEvents();
    }
    private JPanel create_PanelButton(){
        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        Font font=new Font("Arial", Font.PLAIN, 16);
        btn_load=createButton("Tải Lại", font, 200, 40);
        btn_edit=createButton("Chỉnh Sửa", font, 200, 40);
        panel.add(btn_load);
        panel.add(btn_edit);
        return panel;

    }

    private JPanel createBorderedPanel(String title, Font font, Color color) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(color, 2), title, TitledBorder.LEFT, TitledBorder.TOP, font, color));
        return panel;
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
    public void addEvents() {
        btn_load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cập nhật lại thông tin bài kiểm tra
                testModel = testBUS.findTestById(test_id);

                // Hiển thị lại thông tin mới nhất
                lbl_testid.setText(testModel.getTest_id() + "");
                lbl_testname.setText(testModel.getTest_name());
                lbl_createat.setText(testModel.getCreate_at() + "");
                lbl_tongcauhoi.setText(testModel.getSocauhoi() + "");
                lbl_socaude.setText(testModel.getSocaude() + "");
                lbl_socaukho.setText(testModel.getSocaukho() + "");
                lbl_socauthuong.setText(testModel.getSocauthuong() + "");
                lbl_date.setText(testModel.getNgbd_thi() + "");
                combobox_tgianlambai.setSelectedItem(testModel.getTgianlambai() + "");

                System.out.println("Đã tải lại dữ liệu của bài kiểm tra.");
            }
        });

        btn_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy giá trị được chọn từ JComboBox
                String selectedTime = (String) combobox_tgianlambai.getSelectedItem();
                System.out.println("Người dùng đã chọn thời gian làm bài: " + selectedTime + " phút");

                try {
                    // Chuyển đổi thời gian làm bài sang Integer
                    int examDuration = Integer.parseInt(selectedTime);

                    // Lấy thời gian hiện tại theo múi giờ Việt Nam
                    ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");
                    ZonedDateTime vietnamTime = ZonedDateTime.now(vietnamZone);

                    System.out.println("Thời gian hiện tại tại Việt Nam: " + vietnamTime);

                    // Cập nhật thời gian làm bài trong CSDL
                    if (testBUS.editTest(null, null, examDuration, test_id)) {
                        System.out.println("Cập nhật thời gian làm bài thành công!");
                        JOptionPane.showMessageDialog(null, "Cập nhật thời gian làm bài thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        System.out.println("Cập nhật thời gian làm bài thất bại!");
                        JOptionPane.showMessageDialog(null, "Cập nhật thời gian làm bài thất bại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Lỗi: Giá trị thời gian làm bài không hợp lệ!");
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn thời gian làm bài hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

}