package com.paito.biz.auction.dao;

import com.paito.biz.auction.dto.VerifyMessage;

/**
 * Created by patrick on 16/2/1.
 */
public interface IVerifyMessageDAO {

    VerifyMessage findTop1ByMobileno(String mobileno);

    void saveVerifyMessage(VerifyMessage verifyMessage);

    void deleteVerifyMessage(Long id);

}
