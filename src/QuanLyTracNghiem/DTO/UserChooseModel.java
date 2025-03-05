package QuanLyTracNghiem.DTO;

public class UserChooseModel {
    private Integer answer_id;
    private Integer exam_question_id;
    private String user_id;

    // Constructor
    public UserChooseModel(Integer answer_id, Integer exam_question_id, String user_id) {
        this.answer_id = answer_id;
        this.exam_question_id = exam_question_id;
        this.user_id = user_id;
    }

    // Getters and Setters
    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public Integer getExam_question_id() {
        return exam_question_id;
    }

    public void setExam_question_id(Integer exam_question_id) {
        this.exam_question_id = exam_question_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
