package com.linkun.c.common.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkun.api.auth.bean.AuthSession;
import com.linkun.api.auth.remote.IAuthorizeSessionRemoteService;
import com.linkun.api.core.exception.BaseException;
import com.linkun.api.user.remote.IUserRemoteService;
import com.linkun.c.core.exception.NeedLoginException;
import com.linkun.response.JsonResult;
import com.linkun.utils.JsonUtil;
import com.linkun.web.CookiesManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class SessionFilter implements Filter {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SessionFilter.class);
    // 请求头token
    public static final String ACCESS_TOKEN = "accessToken";
    // 会话
    public static final String AUTH_SESSION = "authSession";

    private Set<Pattern> freeURIRegexPatternSet;
//    @Value("${free.url.regex:account/v1/register;account/v1/link;account/v1/login}")
    private String freeURIRegex = "account/v1/register;account/v1/link;account/v1/login";
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private IAuthorizeSessionRemoteService authorizeSessionRemoteService;
    @Autowired
    private IUserRemoteService userRemoteService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info(" filter init ......");
    }

    @PostConstruct
    public void postConstruct() {
        freeURIRegexPatternSet = new HashSet<>();
        String[] freeURIRegexSet = freeURIRegex.split(";");
        for (String regex : freeURIRegexSet) {
            if (StringUtils.isBlank(regex)) {
                continue;
            }
            freeURIRegexPatternSet.add(Pattern.compile(regex));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        try {
            rep.setHeader("Content-Type", "application/json;charset=utf-8");
            String accessToken = req.getHeader(ACCESS_TOKEN);
            if (StringUtils.isEmpty(accessToken)) {
                // 尝试从cookie取
                accessToken = CookiesManager.getCookie(req, ACCESS_TOKEN);
            }
            if (StringUtils.isEmpty(accessToken)) {
                // 尝试参数里获取
                accessToken = req.getParameter(ACCESS_TOKEN);
            }
            LOGGER.info("token is : {}", accessToken);

            AuthSession authSession = null;
            String userId = "";
            if (StringUtils.isNotEmpty(accessToken)) {
                authSession = authorizeSessionRemoteService.getSession(accessToken);
                if (authSession != null && StringUtils.isNotEmpty(authSession.getUserId())) {
                    userId = authSession.getUserId();
                }
            }
            req.setAttribute(AUTH_SESSION, authSession);

            boolean freeStyle = false;
            if (StringUtils.isEmpty(userId)) {
                // 过滤掉不需要登录请求通过的路径
                for (Pattern pattern : freeURIRegexPatternSet) {
                    Matcher matcher = pattern.matcher(req.getRequestURI());
                    if (matcher.find()) {
                        freeStyle = true;
                        break;
                    }
                }
            }

            if (StringUtils.isEmpty(userId) && !freeStyle) {
                // 返回错误401，未授权
                rep.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // 当遇到返回401时，需要把token从Cookie中清除
                CookiesManager.delCookie(req, rep, ACCESS_TOKEN);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseWrite(rep, BaseException.SYSTEM_ERROR, e);
        }
    }

    private void responseWrite(HttpServletResponse rep, String errorCode, Exception e) throws IOException {
        // 未登录处理
        if (e.getCause() != null && NeedLoginException.class.equals(e.getCause().getClass())) {
            // 返回错误401，未授权
            rep.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        String errorResult = JsonUtil.toJson(JsonResult.error(errorCode, messageSource));
        rep.setCharacterEncoding("UTF-8");
        rep.setContentType("application/json;charset=UTF-8");
        rep.getWriter().write(errorResult);
    }

    @Override
    public void destroy() {
        LOGGER.info(" filter destroy ......");
    }
}
