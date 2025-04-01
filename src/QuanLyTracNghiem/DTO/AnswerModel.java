package QuanLyTracNghiem.DTO;

public class AnswerModel {
    private String answer_content;
    private Integer answer_id;
    private String answer_picture;
    private Integer status;
    private Integer isRight;
    private Integer question_id;

    // Constructor
    public AnswerModel(String answer_content, Integer answer_id, String answer_picture, Integer status, Integer isRight, Integer question_id) {
        this.answer_content = answer_content;
        this.answer_id = answer_id;
        this.answer_picture = answer_picture;
        this.status = status;
        this.isRight = isRight;
        this.question_id = question_id;
    }

    public AnswerModel() {

    }

    // Getters and Setters
    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_picture() {
        return answer_picture;
    }

    public void setAnswer_picture(String answer_picture) {
        this.answer_picture = answer_picture;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }
}
