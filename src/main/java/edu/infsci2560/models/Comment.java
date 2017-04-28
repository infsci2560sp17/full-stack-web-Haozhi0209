package edu.infsci2560.models;

import com.blade.jdbc.annotation.Table;

import java.io.Serializable;

@Table(name = "t_comment", pk = "cid")
public class Comment implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer cid;

    private Integer uid;

    private Integer to_uid;

    private Integer tid;

    private String content;

    private String device;

    private Integer create_time;

    public Comment() {
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(Integer to_uid) {
        this.to_uid = to_uid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
