package QuanLyTracNghiem.DTO;

public class TopicTestModel {
    private Integer test_id;
    private Integer topic_id;

    // Constructor
    public TopicTestModel(Integer test_id, Integer topic_id) {
        this.test_id = test_id;
        this.topic_id = topic_id;
    }

    // Getters and Setters
    public Integer getTest_id() {
        return test_id;
    }

    public void setTest_id(Integer test_id) {
        this.test_id = test_id;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }
}
