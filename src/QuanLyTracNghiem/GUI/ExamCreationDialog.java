package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.*;
import QuanLyTracNghiem.DTO.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

class ExamCreationDialog extends JDialog {
    private ArrayList<TopicModel> list_topic;
    private TestModel test = new TestModel();
    private ArrayList<ExamModel> list_exam = new ArrayList<>();
    private ArrayList<TopicTestModel> list_topic_test = new ArrayList<>();
    private ArrayList<ExamQuestionModel> list_exam_question = new ArrayList<>();
    private ArrayList<ResultModel> list_result = new ArrayList<>();
    private ArrayList<UserModel> list_user = new ArrayList<>();


    private UserModel user;
    private JButton btnEnterStudentList, btnCreateExam;
    private JSpinner spinner, dateSpinner;
    private JTextField testNameField;
    private TestBUS testBUS = new TestBUS();
    private ExamBUS examBUS = new ExamBUS();
    private TopicBUS topicBUS = new TopicBUS();
    private TopicTestBUS topicTestBUS=new TopicTestBUS();
    private ExamQuestionBUS examQuestionBUS=new ExamQuestionBUS();
    private QuestionBUS questionBUS=new QuestionBUS();
    private UserBUS userBUS = new UserBUS();
    private ResultBUS resultBUS=new ResultBUS();
    private JComboBox<Integer> comboBox, timeComboBox;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
    private JFrame frame;

    public ExamCreationDialog(JFrame parent, UserModel user) {
        super(parent, "Form tạo bài thi", true);
        this.frame=parent;
        this.user = user;
        this.list_topic = topicBUS.selectAll();
        test.setTest_id(testBUS.finMaxTestId() + 1);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblTitle = new JLabel("Form tạo bài thi", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        mainPanel.add(new JLabel("Tên bài thi:"), gbc);
        gbc.gridx = 1;
        testNameField = new JTextField(15);
        mainPanel.add(testNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Mã vào phòng thi:"), gbc);
        gbc.gridx = 1;
        JLabel lbl_testid = new JLabel(test.getTest_id() + "");
        mainPanel.add(lbl_testid, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Số lượng mã đề:"), gbc);
        gbc.gridx = 1;
        comboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        mainPanel.add(comboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Thời gian làm bài:"), gbc);
        gbc.gridx = 1;
        timeComboBox = new JComboBox<>(new Integer[]{30, 60, 90});
        mainPanel.add(timeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Chọn số lượng câu hỏi:"), gbc);
        gbc.gridx = 1;
        spinner = new JSpinner(new SpinnerNumberModel(2, 2, 100, 2));
        mainPanel.add(spinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Ngày bắt đầu thi:"), gbc);
        gbc.gridx = 1;
        Calendar calendar = Calendar.getInstance();
        dateSpinner = new JSpinner(new SpinnerDateModel(calendar.getTime(), calendar.getTime(), null, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm");
        dateSpinner.setEditor(dateEditor);
        mainPanel.add(dateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Chọn môn học:"), gbc);
        gbc.gridx = 1;
        JPanel subjectPanel = new JPanel(new GridLayout(0, 2));
        for (TopicModel subject : list_topic) {
            JCheckBox checkBox = new JCheckBox(subject.getTopic_name());
            checkBoxes.add(checkBox);
            subjectPanel.add(checkBox);
        }
        JScrollPane scrollPane = new JScrollPane(subjectPanel);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        mainPanel.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Danh sách thí sinh:"), gbc);
        gbc.gridx = 1;
        btnEnterStudentList = new JButton("Nhập danh sách");
        mainPanel.add(btnEnterStudentList, gbc);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCreateExam = new JButton("Tạo bài thi");
        footerPanel.add(btnCreateExam);

        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
        addEvents();
        setVisible(true);

    }

    // Hàm lấy danh sách môn học được chọn
    private ArrayList<String> getSelectedSubjects() {
        ArrayList<String> selectedSubjects = new ArrayList<>();
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                selectedSubjects.add(checkBox.getText());
            }
        }
        return selectedSubjects;

    }

    private void addEvents (){
        btnEnterStudentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn file Excel");
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    //Danh sách thí sinh chưa được lọc
                    ArrayList<UserModel> luser = readUserIdsFromExcel(selectedFile.getAbsolutePath());
                    if (!luser.isEmpty()) {
                        list_user = userBUS.getExistingUsersFromDB(luser);
                        if (list_user.isEmpty()){
                            System.out.println("KHÔNG LẤY ĐƯỢC DANH SÁCH THÍ SINH");
                            return;
                        }
                        for (UserModel u:list_user){
                            System.out.println(u.getUser_id());
                        }
                        JOptionPane.showMessageDialog(null, "Lấy dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu hợp lệ trong file!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                }
            }
        });

        btnCreateExam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createExam();
                UI_TestDetail uiTestDetail=new UI_TestDetail (frame,test.getTest_id()+"",1);
                uiTestDetail.setVisible(true);
                dispose();
            }
        });

    }

    // ĐỌC FILE EXCEL DANH SÁCH THÍ SINH
    private ArrayList<UserModel> readUserIdsFromExcel(String filePath) {
        ArrayList<UserModel> userList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            Iterator<Row> rowIterator = sheet.iterator();

            boolean isFirstRow = true; // Bỏ qua header

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                Cell cell = row.getCell(0); // Cột A (index 0)
                if (cell != null) {
                    String userId = cell.toString().trim(); // Đọc user_id dưới dạng String
                    UserModel user = new UserModel();
                    user.setUser_id(userId);// Tạo UserModel
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return userList;
    }

    private void createExam() {
        // Kiểm tra tên bài thi
        String testName = testNameField.getText().trim();
        if (testName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên bài thi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (testName.length() > 50) {
            JOptionPane.showMessageDialog(this, "Tên bài thi không được quá dài!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Kiểm tra số lượng mã đề
        Integer numberOfVersions = (Integer) comboBox.getSelectedItem();
        if (numberOfVersions == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn số lượng mã đề!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Kiểm tra thời gian làm bài
        Integer examDuration = (Integer) timeComboBox.getSelectedItem();
        System.out.println("Thời gian làm bài đã chọn: " + examDuration);
        test.setTgianlambai(examDuration);
        if (examDuration == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thời gian làm bài!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra số lượng câu hỏi
        Integer questionCount = (Integer) spinner.getValue();
        if (questionCount == null || questionCount <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn số lượng câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Lấy giá trị từ dateSpinner


        // Lấy thời gian từ dateSpinner
        Date selectedDate = (Date) dateSpinner.getValue();
        Date currentDate = new Date();
        // Kiểm tra ngày hợp lệ
        if (selectedDate.before(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu thi phải sau thời gian hiện tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Chuyển từ Date sang LocalDateTime theo múi giờ Việt Nam
        ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime localDateTime = selectedDate.toInstant()
                .atZone(vietnamZone)
                .toLocalDateTime();
        // Lưu LocalDateTime vào test
        test.setNgbd_thi(localDateTime);

        // Kiểm tra môn học đã chọn
        ArrayList<String> selectedSubjects = getSelectedSubjects();
        if (selectedSubjects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selectedSubjects.size() > 2) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tối đa 2 môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Kiểm tra danh sách thí sinh
        if (list_user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập danh sách thí sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // ===========================TẠO BÀI THI=============================================//
        test.setCreate_by(user.getUser_id());
        test.setTest_name(testName);//Lưu tên bài thi
        test.setSocauhoi(questionCount);
        test.setSolgmade(numberOfVersions);
        test.setLuotlambai(0);
        test.setTest_status(0);
        int ques_level []=calculateQuestionDistribution(questionCount,selectedSubjects.size());
        test.setSocaude(ques_level[0]*2);
        test.setSocauthuong(ques_level[1]*2);
        test.setSocaukho(ques_level[2]*2);
        int total_score=ques_level[0]*2+2*ques_level[1]*2+3*ques_level[2]*2;
        int passing_score=(int) (total_score * 0.5);
        //Topictest
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                TopicTestModel ttmodel = new TopicTestModel(test.getTest_id(), list_topic.get(i).getTopic_id());
                list_topic_test.add(ttmodel);
            }
        }

        String examcode[]={"A","B","C","D"};
        int made1=examBUS.findMaxExamId()+1;
        // Exam
        for (int i=0; i<numberOfVersions; i++){
            ExamModel exam_inTest=new ExamModel();
            exam_inTest.setExam_id(made1);
            exam_inTest.setExam_code(examcode[i]);
            exam_inTest.setTest_id(test.getTest_id());
            exam_inTest.setTotal_score(total_score);
            exam_inTest.setPassing_score(passing_score);
            list_exam.add(exam_inTest);
        }
        ArrayList<QuestionModel> list_ques=new ArrayList<>();
        //Exam_Question
        // Lấy danh sách câu hỏi cho từng mã đề
        list_exam_question=generateExamQuestions(list_exam, list_topic_test,ques_level);
        list_result=generateResult(list_user,  list_exam);

        //Thêm bài test vô trước
        if(testBUS.addTest(test.getCreate_by(), test.getLuotlambai(),test.getNgbd_thi(),test.getSocaude(),test.getSocauhoi(),test.getSocaukho(),test.getSocauthuong(),test.getSolgmade(),test.getTest_id(),test.getTest_name(),test.getTest_status(),test.getTgianlambai())){
            topicTestBUS.insertListTopic_TestToDB(list_topic_test);
            examBUS.insertListExamToDB(list_exam);
            examQuestionBUS.insertListExam_QuestionToDB(list_exam_question);
            resultBUS.insertListResultToDB(list_result);
            JOptionPane.showMessageDialog(this, "Tạo bài thi thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }else {
            JOptionPane.showMessageDialog(this, "Tạo bài thi không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }



    }
    //Tinh toán số lượng câu hỏi trong topic
    private int[] calculateQuestionDistribution(int totalQuestions, int  solgtopic) {
        if (totalQuestions % 2 != 0) {
            throw new IllegalArgumentException("Tổng số câu hỏi phải là số chẵn");
        }
        int easyQuestions, hardQuestions, mediumQuestions;
        if(solgtopic==2){
            int questionsPerTopic = totalQuestions / 2;
            easyQuestions = (int) (questionsPerTopic * 0.4);
            hardQuestions = (int) (questionsPerTopic * 0.2);
            mediumQuestions = questionsPerTopic - (easyQuestions + hardQuestions);
        }else {
            easyQuestions = (int) ( totalQuestions * 0.4);
            hardQuestions = (int) ( totalQuestions * 0.2);
            mediumQuestions =  totalQuestions - (easyQuestions + hardQuestions);
        }
        return new int[]{easyQuestions, mediumQuestions, hardQuestions};
    }
    // Tạo câu hỏi cho tất cả các mã đề trong bài thi
    private ArrayList<ExamQuestionModel> generateExamQuestions(ArrayList<ExamModel> list_exam,
                                                               ArrayList<TopicTestModel> list_topic_test,
                                                               int[] ques_level) {
        ArrayList<ExamQuestionModel> examQuestionList = new ArrayList<>();

        for (ExamModel exam : list_exam) {
            for (TopicTestModel topic : list_topic_test) {
                List<QuestionModel> questions = new ArrayList<>();

                questions.addAll(questionBUS.getQuestionForExam(ques_level[0], topic.getTopic_id(), 1));
                questions.addAll(questionBUS.getQuestionForExam(ques_level[1], topic.getTopic_id(), 2));
                questions.addAll(questionBUS.getQuestionForExam(ques_level[2], topic.getTopic_id(), 3));



                // Thêm câu hỏi vào danh sách, KHÔNG truyền ID
                for (QuestionModel question : questions) {
                    examQuestionList.add(new ExamQuestionModel( exam.getExam_id(), question.getQuestion_id()));

                }
            }
        }
        return examQuestionList;
    }



    private Random random = new Random();

    public ArrayList<ResultModel> generateResult(ArrayList<UserModel> list_user, ArrayList<ExamModel> list_exam) {
        ArrayList<ResultModel> list = new ArrayList<>();

        if (list_user.isEmpty()) {
            System.out.println("Danh sách người dùng rỗng! Không thể tạo kết quả.");
            return list;
        }

        if (list_exam.isEmpty()) {
            System.out.println("Danh sách bài thi rỗng! Không thể tạo kết quả.");
            return list;
        }

        Random random = new Random(); // Khai báo random trước để tránh tạo lại nhiều lần

        for (UserModel user : list_user) {
            // Chọn ngẫu nhiên một exam_id từ list_exam
            ExamModel randomExam = list_exam.get(random.nextInt(list_exam.size()));

            // Tạo ResultModel và thêm vào danh sách
            ResultModel resultModel = new ResultModel();
            resultModel.setUser_id(user.getUser_id());
            resultModel.setExam_id(randomExam.getExam_id());

            list.add(resultModel);
        }

        return list;
    }



}
