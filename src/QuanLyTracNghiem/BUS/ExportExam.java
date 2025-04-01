package QuanLyTracNghiem.BUS;

import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.QuestionModel;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ExportExam {
    public static void exportToDoc(String fileName, ArrayList<QuestionModel> list_ques, ArrayList<ArrayList<AnswerModel>> list_ans) {
        if (list_ques == null || list_ques.isEmpty() || list_ans == null || list_ans.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Danh sách câu hỏi hoặc câu trả lời trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(fileName)) {

            // Tạo tiêu đề
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("ĐỀ THI TRẮC NGHIỆM");
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            // Duyệt danh sách câu hỏi
            for (int i = 0; i < list_ques.size(); i++) {
                QuestionModel question = list_ques.get(i);
                ArrayList<AnswerModel> answers = (i < list_ans.size()) ? list_ans.get(i) : new ArrayList<>();

                XWPFParagraph questionPara = document.createParagraph();
                XWPFRun questionRun = questionPara.createRun();
                questionRun.setText((i + 1) + ". " + question.getQuestion_content());
                questionRun.setBold(true);
                questionRun.setFontSize(12);

                // Chèn ảnh câu hỏi (nếu có)
                if (question.getQuestion_picture() != null && !question.getQuestion_picture().isEmpty()) {
                    addImageToDocument(document, question.getQuestion_picture(), 150, 150);
                }

                // Duyệt danh sách đáp án
                // Duyệt danh sách đáp án
                for (int j = 0; j < answers.size(); j++) {
                    AnswerModel answer = answers.get(j);

                    // Định dạng đáp án theo A., B., C., D.
                    char option = (char) ('A' + j);
                    String formattedAnswer = option + ". " + answer.getAnswer_content();

                    XWPFParagraph answerPara = document.createParagraph();
                    XWPFRun answerRun = answerPara.createRun();
                    answerRun.setText(formattedAnswer);
                    answerRun.setFontSize(11);

                    // Chèn ảnh đáp án (nếu có) với kích thước 150x150
                    if (answer.getAnswer_picture() != null && !answer.getAnswer_picture().isEmpty()) {
                        addImageToDocument(document, answer.getAnswer_picture(), 150, 150);
                    }
                }

            }

            // Ghi dữ liệu ra file
            document.write(out);
            JOptionPane.showMessageDialog(null, "Xuất file thành công: " + fileName);

            // Mở file sau khi xuất
            openFile(fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static void addImageToDocument(XWPFDocument document, String imgPath, int i, int i1) {
        try (FileInputStream inputStream = new FileInputStream(imgPath)) {
            XWPFParagraph imgPara = document.createParagraph();
            XWPFRun imgRun = imgPara.createRun();
            imgRun.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_JPEG, imgPath, Units.toEMU(200), Units.toEMU(150));
        } catch (Exception e) {
            System.out.println("Không thể chèn ảnh: " + imgPath);
            e.printStackTrace();
        }
    }

    private static void openFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + filePath, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Không thể mở file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
