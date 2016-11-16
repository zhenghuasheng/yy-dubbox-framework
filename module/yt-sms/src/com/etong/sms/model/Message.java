package com.etong.sms.model;

import com.etong.sms.utility.DateUtil;

import java.io.Serializable;

public class Message implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String mobile;

    private String content;

    private String ext;

    private String stime;

    private String rrid;

    private int state;

    private String type;

    private String memberId;

    private Consumer consumer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        if (stime.isEmpty() || stime == null) {
            this.stime = DateUtil.getCurrentDate();
        } else {
            this.stime = stime;
        }
    }

    public String getRrid() {
        return rrid;
    }

    public void setRrid(String rrid) {
        this.rrid = rrid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public Message(Long id, String mobile, String content, String ext,
                   String stime, String rrid, int state, String type, String memberId,
                   Consumer consumer) {
        super();
        this.id = id;
        this.mobile = mobile;
        this.content = content;
        this.ext = ext;
        this.stime = stime;
        this.rrid = rrid;
        this.state = state;
        this.type = type;
        this.memberId = memberId;
        this.consumer = consumer;
    }

    public Message() {
        super();
    }


}
