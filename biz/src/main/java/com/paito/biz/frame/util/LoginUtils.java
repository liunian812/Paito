package com.paito.biz.frame.util;

import com.paito.biz.auction.dto.LoginUser;
import com.paito.biz.frame.base.SessionKeys;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * Created by patrick on 16/1/17.
 */
public class LoginUtils {
    private final static String REQUEST_LOGIN_USER = "loginUser";

    public static final boolean isUserLogined(HttpSession session) {

        String nk = (String) session.getAttribute(SessionKeys.ATTRIBUTE_NICK);
        String userIDNum = (String) session.getAttribute(SessionKeys.ATTRIBUTE_USER_ID);
        String token = (String)session.getAttribute(SessionKeys.ATTRIBUTE_TOKEN);
        // 是否主账户登录
        if (StringUtils.isBlank(nk) || StringUtils.isBlank(userIDNum) || !Tools.checkToken(token)) {
            return false;
        }
        return true;
    }

    public static final LoginUser buildLoginUser(HttpSession session) {

        LoginUser user = new LoginUser();

        Long userId = session.getAttribute(SessionKeys.ATTRIBUTE_USER_ID) != null ?
                Long.valueOf((String) session.getAttribute(SessionKeys.ATTRIBUTE_USER_ID)) : null;

        String nickname = session.getAttribute(SessionKeys.ATTRIBUTE_NICK) != null ? (String) session.getAttribute(SessionKeys.ATTRIBUTE_NICK) : null;

        user.setUserId(userId);
        user.setNickname(nickname);

        return user;
    }

    /**
     * 设置当前登陆用户到request请求中
     *
     * @param request
     * @param user
     * @author weijie.zhuangwj
     * @date 2013-3-25
     */
    public static final void setLoginUser(HttpServletRequest request, LoginUser user) {
        request.setAttribute(REQUEST_LOGIN_USER, user);
    }

    /**
     * 从request请求中或者当前用户
     *
     * @param request
     * @return
     * @author weijie.zhuangwj
     * @date 2013-3-25
     */
    public static final LoginUser getLoginUser(HttpServletRequest request) {
        return (LoginUser) request.getAttribute(REQUEST_LOGIN_USER);
    }
    /**
     * 构建重定向url
     *
     * @param request
     * @param urlPre
     * @return
     * @author weijie.zhuangwj
     * @date 2013-5-9
     */
    public static final String getRedirectUrl(HttpServletRequest request, String urlPre, String charset) {

        String requestUrl = request.getRequestURL().toString();

        String query = request.getQueryString();
        if (!StringUtils.isBlank(query)) {
            requestUrl = requestUrl + "?" + query;
        }

        try {
            requestUrl = URLEncoder.encode(requestUrl, charset);
        } catch (Exception e) {
            //do nothing
        }

        return new StringBuilder(urlPre).append(requestUrl).toString();
    }

    public static final String getRedirectUrl( String urlPre, String targetUrl,String charset) {
        if(StringUtils.isEmpty(targetUrl)){
            return urlPre;
        }

        String requestUrl = targetUrl;
        try {
            requestUrl = URLEncoder.encode(requestUrl, charset);
        } catch (Exception e) {
            //do nothing
        }

        return new StringBuilder(urlPre).append(requestUrl).toString();
    }
}
