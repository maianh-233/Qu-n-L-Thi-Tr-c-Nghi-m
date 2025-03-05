package QuanLyTracNghiem.DTO;

import java.time.LocalDateTime;

public class ResultModel {
    private Float diem;
    private Float raw_score;
    private Integer status;
    private Integer exam_id;
    private Integer soCauDung;
    private Integer soCauSai;
    private LocalDateTime tgian_nop;
    private String user_id;

    // Constructor đầy đủ
    public ResultModel(Float diem, Float raw_score, Integer status, Integer exam_id,
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

    // Getters and Setters
    public Float getDiem() {
        return diem;
    }

    public void setDiem(Float diem) {
        this.diem = diem;
    }

    public Float getRaw_score() {
        return raw_score;
    }

    public void setRaw_score(Float raw_score) {
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
