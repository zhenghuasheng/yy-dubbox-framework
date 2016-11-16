package com.etong.sms.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class Consumer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 658760036889095089L;

    private int id;

    private String memberId;

    private String memberName;

    private String memberIp;

    private Integer creatTime;

    private Integer operateTime;

    private Integer operator;

    private Boolean viable;

    public Integer getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Integer creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Integer operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Boolean getViable() {
        return viable;
    }

    public void setViable(Boolean viable) {
        this.viable = viable;
    }

    @NotEmpty(message = "不能为空")
    public String getMemberIp() {
        return memberIp;
    }

    public void setMemberIp(String memberIp) {
        this.memberIp = memberIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = "不能为空")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @NotEmpty(message = "不能为空")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Consumer(int id, String memberId, String memberName, String memberIp) {
        super();
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberIp = memberIp;
    }

    public Consumer() {
        super();
    }


}
