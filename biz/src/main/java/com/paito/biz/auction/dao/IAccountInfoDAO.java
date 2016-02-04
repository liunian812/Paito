package com.paito.biz.auction.dao;

import com.paito.biz.auction.dto.AccountDO;

import java.util.List;

/**
 * Created by patrick on 16/1/19.
 */
public interface IAccountInfoDAO {

    AccountDO getAccountByUserName(String accountName);

    void saveAccount(AccountDO auctionAccount);

    AccountDO getAccountInfoByID(Long id);

    void updateAccountInfo(AccountDO accountDO);
}
