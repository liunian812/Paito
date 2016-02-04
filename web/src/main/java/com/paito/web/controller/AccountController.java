package com.paito.web.controller;

import com.paito.biz.auction.dao.IVerifyMessageDAO;
import com.paito.biz.auction.dto.LoginUser;
import com.paito.biz.auction.dto.VerifyMessage;
import com.paito.biz.auction.util.sms.SmsSender;
import com.paito.biz.frame.base.ThreadObjManager;
import com.paito.biz.frame.util.Tools;
import com.paito.web.common.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by patrick on 16/1/31.
 */
@Controller
@RequestMapping("/user")
public class AccountController {

    @Resource
    private IVerifyMessageDAO verifyMessageDAO;

    @ResponseBody
    @RequestMapping(value = "/accountInfo.json", method = {RequestMethod.GET, RequestMethod.POST})
    public AjaxResult getAccountInfo(){
        LoginUser loginUser = ThreadObjManager.getLoginUser();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("data", loginUser);
        return AjaxResult.succResult(dataMap);
    }

    /**
     * 注册手机号码
     *
     * @return RegisterMobileResponse
     */
    @RequestMapping(value = "/sendSms.action", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult registryMobile(String mobileno) {

        String verifycode = Tools.random(6);

        VerifyMessage message = verifyMessageDAO.findTop1ByMobileno(mobileno);
        if (null != message) {
            Calendar rightNow = Calendar.getInstance();
            rightNow.add(Calendar.MINUTE, -5);
            if (rightNow.getTime().after(message.getCreateTime())) {
                saveVerifyMessage(mobileno, verifycode);
            }
        } else {
            saveVerifyMessage(mobileno, verifycode);
        }

        String msgid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            SmsSender.SendSms(mobileno, "【拍兔】正在手机验证!验证码为:" + verifycode + "。");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return AjaxResult.succResult();
    }

    private void saveVerifyMessage(String mobileno,String verifycode){
        VerifyMessage verifyMessage = new VerifyMessage(mobileno, verifycode);
        verifyMessage.setUserId(ThreadObjManager.getLoginUser().getUserId());
        verifyMessage.setUsedStatus(0);
        verifyMessageDAO.saveVerifyMessage(verifyMessage);
    }
}
