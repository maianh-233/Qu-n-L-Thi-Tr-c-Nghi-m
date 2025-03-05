package QuanLyTracNghiem.DTO;

public class ExamModel {
    private Integer exam_id;
    private Integer test_id;
    private String exam_code;
    private Float passing_score;  // Đổi tên cho rõ nghĩa hơn
    private Float total_score;

    // Constructor đầy đủ
    public ExamModel(Integer exam_id, Integer test_id, String exam_code, Float passing_score, Float total_score) {
        this.exam_id = exam_id;
        this.test_id = test_id;
        this.exam_code = exam_code;
        this.passing_score = passing_score;
        this.total_score = total_score;
    }

    // Getters and Setters
    public Integer getExam_id() {
        return exam_id;
    }

    public void setExam_id(Integer exam_id) {
        this.exam_id = exam_id;
    }

    public Integer getTest_id() {
        return test_id;
    }

    public void setTest_id(Integer test_id) {
        this.test_id = test_id;
    }

    public String getExam_code() {
        return exam_code;
    }

    public void setExam_code(String exam_code) {
        this.exam_code = exam_code;
    }

    public Float getPassing_score() {
        return passing_score;
    }

    public void setPassing_score(Float passing_score) {
        this.passing_score = passing_score;
    }

    public Float getTotal_score() {
        return total_score;
    }

    public void setTotal_score(Float total_score) {
        this.total_score = total_score;
    }
}
