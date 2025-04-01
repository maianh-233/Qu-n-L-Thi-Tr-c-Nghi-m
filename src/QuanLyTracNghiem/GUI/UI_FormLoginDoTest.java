package QuanLyTracNghiem.GUI;

import QuanLyTracNghiem.BUS.*;
import QuanLyTracNghiem.DTO.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.ArrayList;

public class UI_FormLoginDoTest extends JDialog {
    private JTextField jTextField1;
    private JButton jButton1;
    private JFrame parentFrame; // Thêm biến để lưu frame chính
    private TestBUS testBus=new TestBUS();
    private ExamBUS examBUS=new ExamBUS();
    private QuestionBUS questionBUS=new QuestionBUS();
    private ExamQuestionBUS examQuestionBUS=new ExamQuestionBUS();
    private AnswerBUS answerBUS=new AnswerBUS();
    private ResultBUS resultBUS=new ResultBUS();
    private ArrayList <ExamModel> list_exam;
    private UserModel login_user;
    private ResultModel resultModel;

    public UI_FormLoginDoTest(JFrame parent, UserModel login_user) {
        super(parent, "Đăng Nhập Bài Thi", true); // JDialog modal
        this.login_user=login_user;
        this.parentFrame = parent; // Lưu frame chính
        initComponents();
        setLocationRelativeTo(parent); // Căn giữa frame cha
    }

    private void initComponents() {
        JLabel jLabel1 = new JLabel("Mã Bài Thi:");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jTextField1 = new JTextField(20);


        jButton1 = new JButton("Bắt Đầu Làm Bài");
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jButton1.setBackground(new Color(51, 153, 255));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFocusPainted(false);
        jButton1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        add(jLabel1, gbc);
        gbc.gridx = 1;
        add(jTextField1, gbc);



        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jButton1, gbc);

        setPreferredSize(new Dimension(400, 250));
        pack();
    }


    private void jButton1ActionPerformed(ActionEvent evt) {
        String maBaiThi = jTextField1.getText().trim();

        if (maBaiThi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TestModel test = testBus.findTestById(Integer.parseInt(maBaiThi));
        if (test == null) {
            JOptionPane.showMessageDialog(this, "Mã làm bài thi của bạn không đúng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Instant now = Instant.now();
        LocalDateTime startTimeLocal = test.getNgbd_thi();
        System.out.println("Thời gian lấy từ MySQL (LocalDateTime): " + startTimeLocal);

        // Chuyển LocalDateTime thành Instant với múi giờ hệ thống
        Instant startTimeInstant = startTimeLocal.atZone(ZoneId.systemDefault()).toInstant();

        System.out.println("Thời gian hiện tại (UTC): " + Instant.now());
        System.out.println("Thời gian hiện tại (LocalDateTime): " + LocalDateTime.now());
        System.out.println("Thời gian hiện tại (ZonedDateTime): " + ZonedDateTime.now(ZoneId.systemDefault()));

        // Tính khoảng cách giữa thời gian hiện tại và thời gian bắt đầu
        Duration duration = Duration.between(startTimeInstant, now);

        // In kết quả kiểm tra
        System.out.println("Khoảng cách thời gian: " + duration.getSeconds() + " giây");

        // Kiểm tra thời gian
        if (duration.isNegative()) {
            JOptionPane.showMessageDialog(this, "Chưa tới thời gian thi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else if (duration.getSeconds() > 300) { // Quá 5 phút
            JOptionPane.showMessageDialog(this, "Bài thi đã kết thúc, bạn không thể vào thi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }


        JOptionPane.showMessageDialog(this, "Bắt đầu làm bài!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        this.list_exam = examBUS.selectByTestID(test.getTest_id());
        this.resultModel = resultBUS.selectResultsForUser(list_exam, login_user.getUser_id());

        if (resultModel == null) {
            JOptionPane.showMessageDialog(this, "Bạn không phải là thí sinh!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ExamModel made = new ExamModel();
        for (ExamModel exam : list_exam) {
            if (resultModel.getExam_id() == exam.getExam_id()) {
                made = exam;
            }
        }

        ArrayList<QuestionModel> questions = questionBUS.getListQuestionInExam(resultModel.getExam_id());
        ArrayList<ExamQuestionModel> list_exam_question = examQuestionBUS.selectByExamQuesID(resultModel.getExam_id());
        ArrayList<ArrayList<AnswerModel>> answers = new ArrayList<>();

        for (QuestionModel question : questions) {
            answers.add(answerBUS.selectAnswerByQues_id(question.getQuestion_id()));
        }

        GiaoDienLamBaiThi giaoDienLamBaiThi = new GiaoDienLamBaiThi(
                parentFrame, resultModel, login_user, test.getTgianlambai(),
                questions, answers, list_exam_question, made
        );
        giaoDienLamBaiThi.showWindow();
        dispose();
        parentFrame.dispose();
    }



}
