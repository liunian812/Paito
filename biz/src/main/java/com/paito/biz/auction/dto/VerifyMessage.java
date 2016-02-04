package com.paito.biz.auction.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by patrick on 16/2/1.
 */
public class VerifyMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7201243189478581054L;

    /**
     * JPA 主键
     */
    private Long id;

    /**
     * 手机号
     */
    private String mobileno;

    /**
     * 验证码
     */
    private String verifycode;

    /**
     * 是否已验证 1: 已经验证， 0 : 没有
     */
    private Integer usedStatus;

    private Date createTime;

    private Date updateTime;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public Integer getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Integer usedStatus) {
        this.usedStatus = usedStatus;
    }

    public VerifyMessage(String mobileno,String verifycode){
        this.mobileno = mobileno;
        this.verifycode = verifycode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    VerifyMessage(){

    }
}
