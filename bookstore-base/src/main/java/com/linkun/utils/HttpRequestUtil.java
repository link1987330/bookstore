package com.linkun.utils;

import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtil {
    public final static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static final String NGINX_IP_HEADER = "X-Real-IP";
    private static final String NGINX_URL_HEADER = "X-Real-Url";
    private static final String NGINX_X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * 功能描述: 获取ip（兼容nginx转发）
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ips = request.getHeader(NGINX_X_FORWARDED_FOR);
        String[] ipArray = StringUtils.split(ips, ",");
        if (ArrayUtils.isNotEmpty(ipArray)) {
            return StringUtils.trim(ipArray[0]);
        } else {
            String ip = request.getHeader(NGINX_IP_HEADER);
            if (StringUtils.isEmpty(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }
    }

    /**
     * 从request中抽取当前url(兼容nginx转发模式)
     * 
     * @param request
     * @see #NGINX_URL_HEADER
     * @return
     */
    public static String getRemoteUrl(HttpServletRequest request) {
        if (checkParamNull(request)) {
            return null;
        }
        String url = request.getHeader(NGINX_URL_HEADER);
        if (StringUtils.isEmpty(url)) {
            return request.getRequestURL().toString();
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("NGINX_URL_HEADER:" + url);
            }
            return url;
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 获取hostname
     * 
     * @param request
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getHostName(HttpServletRequest request) {
        String host = request.getHeader("Host");
        return host;
    }

    /**
     * 获取用户代理
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent;
    }

    private static boolean checkParamNull(Object... params) {
        for (Object param : params) {
            if (null == param) {
                logger.error("Invalid Parameter.");
                return true;
            }
        }
        return false;
    }

    public static String getDataBodyByRequest(HttpServletRequest request) {
        // 获取输入流
        ServletInputStream in = null;
        StringBuilder sb = null;
        try {
            in = request.getInputStream();
            byte[] b = new byte[4096];
            sb = new StringBuilder();
            for (int n; (n = in.read(b)) != -1;) {
                sb.append(new String(b, 0, n, StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            logger.error("getDataBodyByRequest error.", e);
            return null;
        }
        return sb.toString();
    }
}
