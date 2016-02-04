package com.paito.biz.auction.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 域名信息
 * Created by patrick on 16/2/4.
 */
public class AuctionDO implements Serializable {
    private static final long serialVersionUID = 7464481129777234400L;

    private Long id;

    private String domain;

    /**
     * 域名后缀
     */
    private String domainSuffix;

    /**
     * 简介
     */
    private String description;

    /**
     * 平台
     */
    private String platform;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 保留价
     */
    private BigDecimal basePrice;

    /**
     * 手机号码
     */
    private String mobileno;

    /**
     * 微信号
     */
    private String weixin;

    /**
     * 1:待审核  2：审核通过  3：审核拒绝  4：拍卖中
     */
    private Integer status;

    private Long userId;

    /**
     * 审核时间
     */
    private Date auditTime;

    private Date createTime;

    private Date updateTime;

    /**
     * 审核人员
     */
    private Long auditorId;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomainSuffix() {
        return domainSuffix;
    }

    public void setDomainSuffix(String domainSuffix) {
        this.domainSuffix = domainSuffix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
