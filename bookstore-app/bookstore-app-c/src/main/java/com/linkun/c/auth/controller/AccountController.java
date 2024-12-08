package com.linkun.c.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkun.api.auth.bean.AuthSession;
import com.linkun.api.auth.exception.AuthorizeException;
import com.linkun.api.auth.exception.PassportException;
import com.linkun.api.core.exception.BaseException;
import com.linkun.api.user.dto.UserDto;
import com.linkun.api.user.exception.UserException;
import com.linkun.api.user.remote.IUserRemoteService;
import com.linkun.c.core.controller.BaseController;
import com.linkun.c.user.service.IUsercService;
import com.linkun.c.user.view.UserView;
import com.linkun.response.JsonResult;
import com.linkun.utils.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("account")
public class AccountController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IUserRemoteService userRemoteService;
    @Autowired
    private IUsercService usercService;

    /**
     * 登陆前验证
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/link", method = RequestMethod.GET)
    public JsonResult link(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult();
        Map<String, Object> returnData = new HashMap<String, Object>();
        AuthSession session = getOrCreateSession(request);
        if (session != null) {
            returnData.put("nonce", session.getNonce());
            returnData.put("realm", session.getRealm());
            returnData.put("sessionId", session.getSessionId());
        }
        result.setData(returnData);
        return result;
    }

    /**
     * 
     * 注册
     *
     */
    @RequestMapping(value = "v1/register", method = RequestMethod.POST)
    public JsonResult register(HttpServletRequest request, @RequestBody UserDto userDto) {

        try {
            userRemoteService.regist(userDto);
        } catch (UserException | PassportException e) {
            logger.error("regist error", e);
            return JsonResult.error(e.getErrorCode(), messageSource);
        }

        return JsonResult.success();
    }

    @RequestMapping(value = "/v1/login", method = RequestMethod.POST)
    public JsonResult<UserView> login(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "account", required = true) String account,
                                      @RequestParam(value = "authorizeCode", required = true) String authorizeCode,
                                      @RequestParam(value = "isRememberLogin", required = false, defaultValue = "false") boolean isRememberLogin,
                                      String imageCaptcha) throws BaseException, AuthorizeException {
        AuthSession authSession = loginMethod(request, response, account, authorizeCode, isRememberLogin, imageCaptcha);
        return new JsonResult<UserView>().success(usercService.getUserByUserId(Long.valueOf(authSession.getUserId())));
    }

    private AuthSession loginMethod(HttpServletRequest request, HttpServletResponse response, String account,
                                    String authorizeCode, boolean isRememberLogin, String imageCaptcha) throws BaseException, AuthorizeException {
        AuthSession authSession = getSession(request);
        if (authSession == null) {
            logger.error("Login error. Session is null.");
            throw new BaseException(BaseException.PAGE_INVALID);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(
                    "login by session[{}], ip[{}], requestURI[{}], loginName[{}], authorizeCode[{}], isRememberLogin[{}]",
                    authSession.getSessionId(), HttpRequestUtil.getRemoteIp(request), "auth/v1/login", account,
                    authorizeCode, isRememberLogin);
        }

        authSession = authorizeSessionRemoteService.login(authSession.getSessionId(),
                HttpRequestUtil.getRemoteIp(request), "/auth/v1/login", account, authorizeCode, isRememberLogin);
        authSession.setLoginFails(0);
        authorizeSessionRemoteService.updateSession(authSession);

        return authSession;
    }

}
