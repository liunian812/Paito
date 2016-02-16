package com.paito.biz.auction.dao.impl;

import com.paito.biz.auction.dao.IAuctionDAO;
import com.paito.biz.auction.dto.AuctionDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 16/2/4.
 */
@Repository("auctionDAO")
public class AuctionDAOImpl implements IAuctionDAO {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<AuctionDO> findAuctionList(Long userId, Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("status", status);
        return sqlSessionTemplate.selectList("AuctionDAO.findAuctionList", map);
    }

    @Override
    public void saveAuction(AuctionDO auctionDO) {
        sqlSessionTemplate.insert("AuctionDAO.saveAuction", auctionDO);
    }

    @Override
    public void auditAuction(AuctionDO auctionDO) {
        sqlSessionTemplate.update("AuctionDAO.auditAuction", auctionDO);
    }

    @Override
    public List<AuctionDO> findAuctionListByStatus(Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        return sqlSessionTemplate.selectList("AuctionDAO.findAuctionList", map);
    }
}
