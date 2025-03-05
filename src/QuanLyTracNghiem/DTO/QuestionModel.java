package QuanLyTracNghiem.DTO;

public class QuestionModel {
    private String question_content;
    private Integer question_id;
    private Integer question_level;
    private String question_picture;
    private Integer question_status;
    private Integer topic_id;

    // Constructor
    public QuestionModel(String question_content, Integer question_id, Integer question_level, String question_picture, Integer question_status, Integer topic_id) {
        this.question_content = question_content;
        this.question_id = question_id;
        this.question_level = question_level;
        this.question_picture = question_picture;
        this.question_status = question_status;
        this.topic_id = topic_id;
    }

    public QuestionModel() {

    }

    // Getters and Setters
    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getQuestion_level() {
        return question_level;
    }

    public void setQuestion_level(Integer question_level) {
        this.question_level = question_level;
    }

    public String getQuestion_picture() {
        return question_picture;
    }

    public void setQuestion_picture(String question_picture) {
        this.question_picture = question_picture;
    }

    public Integer getQuestion_status() {
        return question_status;
    }

    public void setQuestion_status(Integer question_status) {
        this.question_status = question_status;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }
}