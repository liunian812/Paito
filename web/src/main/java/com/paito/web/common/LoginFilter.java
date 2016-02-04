package com.paito.web.common;

import com.paito.biz.auction.dao.IAccountInfoDAO;
import com.paito.biz.auction.dto.AccountDO;
import com.paito.biz.auction.dto.LoginUser;
import com.paito.biz.frame.base.SessionKeys;
import com.paito.biz.frame.base.ThreadObjManager;
import com.paito.biz.frame.util.LoginUtils;
import com.paito.biz.frame.util.Tools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by patrick on 16/1/17.
 */
public class LoginFilter implements Filter {

    private static final Log log		= LogFactory.getLog(LoginFilter.class);

    @Autowired
    private IAccountInfoDAO accountInfoDAO;

    private String					redirectUrl = "http://www.paito.com/login/login.html";

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();

        // 判断用户有没有登录
        if (!LoginUtils.isUserLogined(httpRequest.getSession())) {
            AjaxResult r = AjaxResult.errorResult("unlogin");
            httpResponse.getWriter().append(r.toSimpleJsonString());
            return;
        }

        // 构建用户对象
        LoginUser user = LoginUtils.buildLoginUser(httpRequest.getSession());
        if(null == user){
            AjaxResult r = AjaxResult.errorResult("unlogin");
            httpResponse.getWriter().append(r.toSimpleJsonString());
            return;
        }

        try{
            AccountDO customer= accountInfoDAO.getAccountInfoByID(user.getUserId());

            if(customer==null){
                AjaxResult r = AjaxResult.errorResult("unlogin");
                httpResponse.getWriter().append(r.toSimpleJsonString());
                return;
            }
            user.setUserStatus(customer.getStatus());
            user.setUserType(customer.getAccountType());
        }catch(Exception e){
            log.error("fail to check user",e);
        }
        ThreadObjManager.clear();
        ThreadObjManager.setLoginUser(user);

        String csrfId = session.getAttribute(SessionKeys.SESSION_CSRFID) != null ? (String) session.getAttribute(SessionKeys.SESSION_CSRFID) : null;

        setCsrfIdToSession(session, user.getUserId(), csrfId);
        // 将当前用户存放到当前的request中
        LoginUtils.setLoginUser(httpRequest, user);

        // 进入下一个filter
        chain.doFilter(httpRequest, httpResponse);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // do nothing
    }

    private void setCsrfIdToSession(HttpSession session, Long memberId, String csrfId) {
        if (csrfId == null) {
            String csrf = Tools.buildCsrfId(memberId);
            session.setAttribute(SessionKeys.SESSION_CSRFID, csrf);
        }
    }
}
