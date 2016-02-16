package com.paito.web.controller;

import com.paito.biz.auction.dao.IAuctionDAO;
import com.paito.biz.auction.dto.AuctionDO;
import com.paito.biz.auction.dto.AuctionStatusEnum;
import com.paito.biz.frame.base.ThreadObjManager;
import com.paito.web.common.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by patrick on 16/2/4.
 */
@Controller
@RequestMapping(value="/auction")
public class AuctionController {

    @Resource
    private IAuctionDAO auctionDAO;

    @ResponseBody
    @RequestMapping(value = "/submitdomain.json", method = {RequestMethod.GET, RequestMethod.POST})
    public AjaxResult addAuction(AuctionDO auctionDO){
        auctionDO.setUserId(ThreadObjManager.getLoginUser().getUserId());
        auctionDO.setStatus(AuctionStatusEnum.WAIT_AUDIT.value());
        auctionDAO.saveAuction(auctionDO);
        return AjaxResult.succResult();
    }

    @ResponseBody
    @RequestMapping(value = "/findAuctionList.action", method = {RequestMethod.GET, RequestMethod.POST})
    public AjaxResult findAuctionList(){
        return AjaxResult.succResult("auctionList", auctionDAO.findAuctionListByStatus(AuctionStatusEnum.ON_BIDING.value()));
    }
}
