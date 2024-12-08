package com.linkun.c.core.controller;

import javax.servlet.http.HttpServletRequest;
import com.linkun.api.auth.enums.PlatformSystemEnum;
import com.linkun.api.auth.remote.IAuthorizeSessionRemoteService;
import com.linkun.api.core.exception.BaseException;
import com.linkun.utils.HttpRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.linkun.api.auth.bean.AuthSession;
import com.linkun.c.core.exception.NeedLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class BaseController {

    public static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected static final String AUTH_SESSION = "authSession";
    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected IAuthorizeSessionRemoteService authorizeSessionRemoteService;

    protected AuthSession getSession(HttpServletRequest request) {
        return (AuthSession) request.getAttribute("authSession");
    }

    protected AuthSession getOrCreateSession(HttpServletRequest request) {
        AuthSession authSession = (AuthSession) request.getAttribute("authSession");
        if (null == authSession) {
            authSession = authorizeSessionRemoteService.createSession(HttpRequestUtil.getRemoteIp(request),
                    PlatformSystemEnum.BOOKSTORE_C.name());
        }
        return authSession;
    }

    protected AuthSession checkSession(HttpServletRequest request) throws BaseException {
        AuthSession session = getSession(request);
        if (null == session) {
            throw new NeedLoginException();
        }
        return session;
    }

    protected boolean hasLogin(HttpServletRequest request) {
        AuthSession authSession = (AuthSession) request.getAttribute(AUTH_SESSION);
        return authSession != null && StringUtils.isNotEmpty(authSession.getUserId());
    }

    protected Long checkLogin(HttpServletRequest request) throws NeedLoginException {
        AuthSession authSession = getSession(request);
        if (authSession == null) {
            logger.error("Session is null.");
            throw new NeedLoginException();
        }
        // 已登录用户session中应该有用户id
        if (StringUtils.isEmpty(authSession.getUserId())) {
            throw new NeedLoginException();
        }
        return authSession.getNumberUserId();
    }

    protected Long getUserId(HttpServletRequest request) throws NeedLoginException {
        AuthSession authSession = getSession(request);
        return authSession != null ? authSession.getNumberUserId() : null;
    }

//    /**
//     *
//     * 功能描述: <br>
//     * 校验手机验证码
//     *
//     * @param authSession
//     * @param mobile 手机号
//     * @param mobileCaptcha 手机验证码
//     * @param isGetAndRemove 获取验证码并删除
//     * @return
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    protected boolean checkMobileCaptcha(AuthSession authSession, String mobile, String mobileCaptcha,
//            boolean isGetAndRemove) {
//        String orginMobileCaptcha = authSession.getMobileCaptcha();
//        if (logger.isDebugEnabled()) {
//            logger.debug("orginMobileCaptcha:{}, mobileCaptcha:{}, redisMobileCaptcha:{}", orginMobileCaptcha,
//                    mobileCaptcha, mobileCaptchaRemoteService.takeCaptcha(mobile));
//        }
//        if (StringUtils.isEmpty(orginMobileCaptcha) || !StringUtils.equalsIgnoreCase(mobileCaptcha, orginMobileCaptcha)
//                || (isGetAndRemove ? !mobileCaptchaRemoteService.checkAndRemoveCaptcha(mobile, mobileCaptcha)
//                        : !mobileCaptchaRemoteService.isValidCaptcha(mobile, mobileCaptcha))) {
//            return false;
//        }
//        authSession.setMobileCaptcha(StringUtils.EMPTY);
//        authorizeSessionRemoteService.updateSession(authSession);
//        return true;
//    }

}
