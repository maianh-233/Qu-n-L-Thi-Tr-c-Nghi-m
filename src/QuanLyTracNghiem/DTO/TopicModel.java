package QuanLyTracNghiem.DTO;

public class TopicModel {
    private Integer topic_id;
    private String topic_name;

    // Constructor
    public TopicModel(Integer topic_id, String topic_name) {
        this.topic_id = topic_id;
        this.topic_name = topic_name;
    }

    // Getters and Setters
    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
}
