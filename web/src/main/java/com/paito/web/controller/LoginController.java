package com.paito.web.controller;

import com.paito.biz.auction.dao.IAccountInfoDAO;
import com.paito.biz.auction.dto.AccountDO;
import com.paito.biz.frame.base.SessionKeys;
import com.paito.biz.frame.base.ThreadObjManager;
import com.paito.biz.frame.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 跳转链接controller
 */
@Controller
@RequestMapping(value="/home")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private IAccountInfoDAO accountInfoDAO;

	/**
	 * 用户登出
	 * @return
	 */
	@RequestMapping(value="/logout.shtml",method= RequestMethod.GET)
	public String logout(ServletRequest request, ServletResponse response){
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		ThreadObjManager.clear();
		httpRequest.getSession().invalidate();
		return "/index.html";
	}

	@RequestMapping(value = "/login.shtml", method = RequestMethod.GET)
	public String redirectToLogin(ServletRequest request, ServletResponse response){
		return "/login.htm";
	}

	@ResponseBody
	@RequestMapping(value = "/doLogin.action", method = {RequestMethod.POST})
	public String doLogin(HttpServletRequest request){

		String nickName = request.getParameter("nickName");
		String password = request.getParameter("loginpsd");
		AccountDO accountDO = accountInfoDAO.getAccountByUserName(nickName);
		if(null != accountDO && !StringUtils.isEmpty(password)){
			if(accountDO.getPassword().equals(Tools.StringToMD5(password))){
				HttpSession session = request.getSession();
				session.setAttribute(SessionKeys.ATTRIBUTE_USER_ID, accountDO.getUserId());
				session.setAttribute(SessionKeys.ATTRIBUTE_NICK, nickName);
				session.setAttribute(SessionKeys.ATTRIBUTE_TYPE, accountDO.getAccountType());
				String csrf = Tools.buildCsrfId(accountDO.getUserId());
				session.setAttribute(SessionKeys.SESSION_CSRFID, csrf);
				session.setAttribute(SessionKeys.ATTRIBUTE_TOKEN, Tools.buildToken());
			}
		}

		return "/index.html";
	}
	
}
