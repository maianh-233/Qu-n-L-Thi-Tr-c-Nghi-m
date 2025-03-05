package QuanLyTracNghiem.DTO;

public class ExamQuestionModel {
    private Integer exam_id;
    private Integer exam_question_id;
    private Integer question_id;

    // Constructor
    public ExamQuestionModel(Integer exam_id, Integer exam_question_id, Integer question_id) {
        this.exam_id = exam_id;
        this.exam_question_id = exam_question_id;
        this.question_id = question_id;
    }

    // Getters and Setters
    public Integer getExam_id() {
        return exam_id;
    }

    public void setExam_id(Integer exam_id) {
        this.exam_id = exam_id;
    }

    public Integer getExam_question_id() {
        return exam_question_id;
    }

    public void setExam_question_id(Integer exam_question_id) {
        this.exam_question_id = exam_question_id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }
}