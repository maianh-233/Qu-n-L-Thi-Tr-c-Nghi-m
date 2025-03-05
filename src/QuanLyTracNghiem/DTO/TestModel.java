package QuanLyTracNghiem.DTO;

import java.time.LocalDateTime;

public class TestModel {
    private LocalDateTime create_at;
    private String create_by;
    private Integer luotlambai;
    private LocalDateTime ngbd_thi;
    private Integer socaude;
    private Integer socauhoi;
    private Integer socaukho;
    private Integer socauthuong;
    private Integer solgmade;
    private Integer test_id;
    private String test_name;
    private Integer test_status;
    private Integer tgianlambai;

    // Constructor
    public TestModel(LocalDateTime create_at, String create_by, Integer luotlambai, LocalDateTime ngbd_thi,
                     Integer socaude, Integer socauhoi, Integer socaukho, Integer socauthuong, Integer solgmade,
                     Integer test_id, String test_name,
                     Integer test_status, Integer tgianlambai) {
        this.create_at = create_at;
        this.create_by = create_by;
        this.luotlambai = luotlambai;
        this.ngbd_thi = ngbd_thi;
        this.socaude = socaude;
        this.socauhoi = socauhoi;
        this.socaukho = socaukho;
        this.socauthuong = socauthuong;
        this.solgmade = solgmade;
        this.test_id = test_id;
        this.test_name = test_name;
        this.test_status = test_status;
        this.tgianlambai = tgianlambai;
    }

    public TestModel() {}

    // Getters and Setters
    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Integer getLuotlambai() {
        return luotlambai;
    }

    public void setLuotlambai(Integer luotlambai) {
        this.luotlambai = luotlambai;
    }

    public LocalDateTime getNgbd_thi() {
        return ngbd_thi;
    }

    public void setNgbd_thi(LocalDateTime ngbd_thi) {
        this.ngbd_thi = ngbd_thi;
    }

    public Integer getSocaude() {
        return socaude;
    }

    public void setSocaude(Integer socaude) {
        this.socaude = socaude;
    }

    public Integer getSocauhoi() {
        return socauhoi;
    }

    public void setSocauhoi(Integer socauhoi) {
        this.socauhoi = socauhoi;
    }

    public Integer getSocaukho() {
        return socaukho;
    }

    public void setSocaukho(Integer socaukho) {
        this.socaukho = socaukho;
    }

    public Integer getSocauthuong() {
        return socauthuong;
    }

    public void setSocauthuong(Integer socauthuong) {
        this.socauthuong = socauthuong;
    }

    public Integer getSolgmade() {
        return solgmade;
    }

    public void setSolgmade(Integer solgmade) {
        this.solgmade = solgmade;
    }

    public Integer getTest_id() {
        return test_id;
    }

    public void setTest_id(Integer test_id) {
        this.test_id = test_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public Integer getTest_status() {
        return test_status;
    }

    public void setTest_status(int test_status) {
        this.test_status = test_status;
    }

    public Integer getTgianlambai() {
        return tgianlambai;
    }

    public void setTgianlambai(Integer tgianlambai) {
        this.tgianlambai = tgianlambai;
    }
}
