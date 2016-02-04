package com.paito.biz.auction.dao;

import com.paito.biz.auction.dto.AuctionDO;

import java.util.List;

/**
 * Created by patrick on 16/2/4.
 */
public interface IAuctionDAO {

    public List<AuctionDO> findAuctionList(Long userId, Integer status);

    void saveAuction(AuctionDO auctionDO);

    void auditAuction(AuctionDO auctionDO);

    List<AuctionDO> findAuctionListByStatus(Integer status);

}
