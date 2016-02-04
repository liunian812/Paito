package com.paito.biz.auction.dao.impl;

import com.paito.biz.auction.dao.IAccountInfoDAO;
import com.paito.biz.auction.dto.AccountDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 16/1/19.
 */
@Repository("accountInfoDAO")
public class AccountInfoDAOImpl implements IAccountInfoDAO {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public AccountDO getAccountByUserName(String accountName) {
        return sqlSessionTemplate.selectOne("AccountInfoDAO.getAccountByUserName", accountName);
    }

    @Override
    public void saveAccount(AccountDO auctionAccount) {
        sqlSessionTemplate.insert("AccountInfoDAO.saveAccount", auctionAccount);
    }

    @Override
    public AccountDO getAccountInfoByID(Long userId) {
        return sqlSessionTemplate.selectOne("AccountInfoDAO.getAccountInfoByID", userId);
    }

    @Override
    public void updateAccountInfo(AccountDO accountDO) {
        sqlSessionTemplate.update("AccountInfoDAO.updateAccountInfo", accountDO);
    }
}
