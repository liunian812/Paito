package com.paito.web.controller;

import com.paito.biz.auction.dao.IAccountInfoDAO;
import com.paito.biz.auction.dto.AccountDO;
import com.paito.biz.frame.base.SessionKeys;
import com.paito.biz.frame.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@SuppressWarnings("serial")
@Controller
@RequestMapping(value="/home")
@Scope("prototype")
public class RegisterController{
	
	static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Resource
	private IAccountInfoDAO accountInfoDAO;

	private String indexUrl = "http://pai.to";
	/**
	 * 注册(跳转到注册页面)
	 */
	@RequestMapping(value = "/register.shtml", method = RequestMethod.GET)
	public String redirectToRegister(ServletRequest request, ServletResponse response){
		String callback = (String)request.getAttribute("oauth_callback");
		return "redirect:/pages/register.html?"+callback;
	}

	/**
	 * 注册
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doregister.action", method = {RequestMethod.POST,RequestMethod.GET})
	public void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String nickName = request.getParameter("nickName");
		String password = request.getParameter("loginpsd");
		String reassword = request.getParameter("repassword");
		String userPhone = request.getParameter("mobilePhone");

		logger.info("register user, nickname: " + nickName);
		if(null == accountInfoDAO.getAccountByUserName(nickName)) {
			AccountDO auctionAccount = new AccountDO();
			auctionAccount.setCreateTime(new Date());
			auctionAccount.setUpdateTime(new Date());
			auctionAccount.setNickName(nickName);
			auctionAccount.setPassword(Tools.StringToMD5(password));
			auctionAccount.setStatus(1);
			auctionAccount.setAccountType(1);
			auctionAccount.setMobileNumber(userPhone);
			//auctionAccount.setAccountRegisterCode(randStr);
			accountInfoDAO.saveAccount(auctionAccount);
			AccountDO accountDO = accountInfoDAO.getAccountByUserName(nickName);

			if (null != accountDO) {
				HttpSession session = request.getSession();
				session.setAttribute(SessionKeys.ATTRIBUTE_USER_ID, accountDO.getUserId());
				session.setAttribute(SessionKeys.ATTRIBUTE_NICK, nickName);
				session.setAttribute(SessionKeys.ATTRIBUTE_TYPE, accountDO.getAccountType());
				String csrf = Tools.buildCsrfId(accountDO.getUserId());
				session.setAttribute(SessionKeys.SESSION_CSRFID, csrf);
				session.setAttribute(SessionKeys.ATTRIBUTE_TOKEN, Tools.buildToken());
			}
		}
		response.sendRedirect(indexUrl);
	}
	
	/*public String validateMail(){logger.info("11111___________");
		String accountFlag = ServletActionContext.getRequest().getParameter("accountFlag");
		String validateCode = ServletActionContext.getRequest().getParameter("validateCode");
		
		if(accountFlag != null && validateCode != null){
			AuctionAccount auctionAccount = iAccountInfo.getAccountInfo(accountFlag);
			if(auctionAccount == null){
				ServletActionContext.getRequest().setAttribute("checkFlag", "1");
				return "validateErr";
			}
			if(!("3").equals(auctionAccount.getAccountType())){
				ServletActionContext.getRequest().setAttribute("checkFlag", "1");
				return "validateErr";
			}
			if(("0").equals(auctionAccount.getAccountStatus())){
				auctionAccount.setAccountStatus("1");
				iAccountInfo.saveAuctionAccount(auctionAccount);
				return "validateSuc";
			}else if(("1").equals(auctionAccount.getAccountStatus())){
				ServletActionContext.getRequest().setAttribute("checkFlag", "2");
				return "validateErr";
			}else if(("2").equals(auctionAccount.getAccountStatus())){
				ServletActionContext.getRequest().setAttribute("checkFlag", "3");
				return "validateErr";
			}else{
				ServletActionContext.getRequest().setAttribute("checkFlag", "1");
				return "validateErr";
			}
		}else{
			ServletActionContext.getRequest().setAttribute("checkFlag", "1");
			return "validateErr";
		}
	}*/


	/*public void setiGuestInfo(IGuestInfoService iGuestInfo) {
		this.iGuestInfo = iGuestInfo;
	}

	public void setiUploadImgInfo(IUploadImgInfoService iUploadImgInfo) {
		this.iUploadImgInfo = iUploadImgInfo;
	}
	
	*/
	
}
