package QuanLyTracNghiem.BUS;
import QuanLyTracNghiem.DTO.AnswerModel;
import QuanLyTracNghiem.DTO.QuestionModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
public class ExcelImporter {
    private ArrayList<QuestionModel> list_question = new ArrayList<>();
    private ArrayList<AnswerModel> list_answer = new ArrayList<>();
    private QuestionBUS questionBUS = new QuestionBUS();
    private AnswerBUS answerBUS = new AnswerBUS();
    private Gson gson = new Gson(); // Khởi tạo Gson
    public ArrayList<QuestionModel> getList_question(){
        return this.list_question;
    }
    public ArrayList<AnswerModel> getList_answer(){
        return this.list_answer;
    }

    // Lớp dùng để ánh xạ JSON từ Excel
    private static class QuestionData {
        String question_content;
        String question_picture;
        int question_level;

    }

    private static class AnswerData {
        String answer_content;
        String answer_picture;
        Integer is_Right;
    }

    public void importExcel(String filePath, int topic_id) {
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            int maxQuestionId = questionBUS.finMaxQuestion_Id()+1;
            int maxAnswerId = answerBUS.getMaxAnswerId()+1;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua dòng tiêu đề

                // Đọc JSON câu hỏi từ cột đầu tiên
                Cell questionCell = row.getCell(0);
                if (questionCell == null || questionCell.getCellType() != CellType.STRING) continue;

                try {
                    QuestionData questionData = gson.fromJson(questionCell.getStringCellValue(), QuestionData.class);
                    if (questionData == null) continue;

                    // Tạo đối tượng QuestionModel
                    //String question_content, Integer question_id, Integer question_level, String question_picture, Integer question_status, Integer topic_id
                    QuestionModel question = new QuestionModel(
                           questionData.question_content,
                           maxQuestionId,
                           questionData.question_level,
                           questionData.question_picture,
                           0,
                           topic_id
                    );
                    list_question.add(question);

                    // Đọc JSON câu trả lời từ các cột tiếp theo
                    for (int i = 1; i <= 4; i++) {
                        Cell answerCell = row.getCell(i);
                        if (answerCell != null && answerCell.getCellType() == CellType.STRING) {
                            AnswerData answerData = gson.fromJson(answerCell.getStringCellValue(), AnswerData.class);
                            if (answerData == null) continue;
                            // Tạo đối tượng AnswerModel
                            //String answer_content, Integer answer_id, String answer_picture, Integer status, Integer isRight, Integer question_id
                            AnswerModel answer = new AnswerModel(
                                    answerData.answer_content,
                                    maxAnswerId,
                                    answerData.answer_picture,
                                    0,
                                    answerData.is_Right,
                                    maxQuestionId
                            );
                            list_answer.add(answer);
                            maxAnswerId++; // Tăng ID câu trả lời
                        }
                    }

                    maxQuestionId++; // Tăng ID câu hỏi

                } catch (JsonSyntaxException e) {
                    System.err.println("Lỗi JSON ở dòng " + row.getRowNum() + ": " + e.getMessage());
                }
            }

            System.out.println("Danh sách câu hỏi đã đọc:");
            for (QuestionModel q : list_question) {
                System.out.println("ID: " + q.getQuestion_id() + " | Nội dung: " + q.getQuestion_content());
            }

            System.out.println("\nDanh sách câu trả lời đã đọc:");
            for (AnswerModel a : list_answer) {
                System.out.println("ID: " + a.getAnswer_id() + " | QID: " + a.getQuestion_id() + " | Nội dung: " + a.getAnswer_content());
            }
            questionBUS.insertListQuestionToDB(list_question);
            answerBUS.insertListAnswerToDB(list_answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
