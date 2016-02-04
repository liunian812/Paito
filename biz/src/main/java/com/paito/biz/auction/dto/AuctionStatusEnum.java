package com.paito.biz.auction.dto;

/**
 * Created by patrick on 16/2/4.
 */
public enum AuctionStatusEnum {

    WAIT_AUDIT(1,"待审核"),
    AUDIT_PASS(2, "审核通过"),
    AUDIT_REFUSE(3, "审核拒绝"),
    ON_BIDING(4, "拍卖中");

    private int code;

    private String desc;

    AuctionStatusEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer value() {
        return this.code;
    }
}
