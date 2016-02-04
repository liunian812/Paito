package com.paito.biz.auction.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by patrick on 16/2/4.
 */
public class PaimaiDO implements Serializable {

    private static final long serialVersionUID = 1509530446925398358L;
    private Long id;

    // 拍卖时间
    private Date paimaiTime;

    // 成交时间
    private Date chengjiaoTime;

    // 域名id
    private Long aucitonId;

    // 拍卖结果状态
    private Integer paimaiStatus;

    // 拍卖用户id
    private Long paimaiUserId;

    // 成交金额
    private BigDecimal paimaiPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPaimaiTime() {
        return paimaiTime;
    }

    public void setPaimaiTime(Date paimaiTime) {
        this.paimaiTime = paimaiTime;
    }

    public Date getChengjiaoTime() {
        return chengjiaoTime;
    }

    public void setChengjiaoTime(Date chengjiaoTime) {
        this.chengjiaoTime = chengjiaoTime;
    }

    public Long getAucitonId() {
        return aucitonId;
    }

    public void setAucitonId(Long aucitonId) {
        this.aucitonId = aucitonId;
    }

    public Integer getPaimaiStatus() {
        return paimaiStatus;
    }

    public void setPaimaiStatus(Integer paimaiStatus) {
        this.paimaiStatus = paimaiStatus;
    }

    public Long getPaimaiUserId() {
        return paimaiUserId;
    }

    public void setPaimaiUserId(Long paimaiUserId) {
        this.paimaiUserId = paimaiUserId;
    }
}
