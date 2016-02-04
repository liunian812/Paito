package com.paito.biz.auction.dao.impl;

import com.paito.biz.auction.dao.IVerifyMessageDAO;
import com.paito.biz.auction.dto.VerifyMessage;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by patrick on 16/2/4.
 */
@Repository("verifyMessageDAO")
public class VerifyMessageDAOImpl implements IVerifyMessageDAO {


    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public VerifyMessage findTop1ByMobileno(String mobileno) {
        return sqlSessionTemplate.selectOne("VerifyMessageDAO.findTop1ByMobileno", mobileno);
    }

    @Override
    public void saveVerifyMessage(VerifyMessage verifyMessage) {
        sqlSessionTemplate.insert("VerifyMessageDAO.saveVerifyMessage", verifyMessage);
    }

    @Override
    public void deleteVerifyMessage(Long id) {
        sqlSessionTemplate.delete("VerifyMessageDAO.deleteVerifyMessage", id);
    }


}
