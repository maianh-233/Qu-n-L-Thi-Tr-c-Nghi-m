package QuanLyTracNghiem.DTO;

import java.time.LocalDateTime;

public class ResultModel {
    private Integer diem;
    private Integer raw_score;
    private Integer status;
    private Integer exam_id;
    private Integer soCauDung;
    private Integer soCauSai;
    private LocalDateTime tgian_nop;
    private String user_id;

    // Constructor đầy đủ
    public ResultModel(Integer diem, Integer raw_score, Integer status, Integer exam_id,
                       Integer soCauDung, Integer soCauSai, LocalDateTime thoiGianNop, String user_id) {
        this.diem = diem;
        this.raw_score = raw_score;
        this.status = status;
        this.exam_id = exam_id;
        this.soCauDung = soCauDung;
        this.soCauSai = soCauSai;
        this.tgian_nop = thoiGianNop;
        this.user_id = user_id;
    }

    public ResultModel() {

    }

    // Getters and Setters
    public Integer getDiem() {
        return diem;
    }

    public void setDiem(Integer diem) {
        this.diem = diem;
    }

    public Integer getRaw_score() {
        return raw_score;
    }

    public void setRaw_score(Integer raw_score) {
        this.raw_score = raw_score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExam_id() {
        return exam_id;
    }

    public void setExam_id(Integer exam_id) {
        this.exam_id = exam_id;
    }

    public Integer getSoCauDung() {
        return soCauDung;
    }

    public void setSoCauDung(Integer soCauDung) {
        this.soCauDung = soCauDung;
    }

    public Integer getSoCauSai() {
        return soCauSai;
    }

    public void setSoCauSai(Integer soCauSai) {
        this.soCauSai = soCauSai;
    }

    public LocalDateTime getTgian_nop() {
        return tgian_nop;
    }

    public void setTgian_nop(LocalDateTime tgian_nop) {
        this.tgian_nop = tgian_nop;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
