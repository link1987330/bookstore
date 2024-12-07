package com.linkun.api.auth.remote;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.linkun.api.auth.bean.Auth;
import com.linkun.api.auth.bean.AuthSession;
import com.linkun.api.auth.constants.SessionAttribute;
import com.linkun.api.auth.exception.AuthorizeException;
import com.linkun.api.auth.redis.RedisKeyGenerator;
import com.linkun.api.auth.service.IAuthService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeSessionRemoteService implements IAuthorizeSessionRemoteService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 3600秒
    private static final int NONCE_SESSION_EXPIRED_TIME = 60 * 60;
    // 非记住登录状态的session过期时间为一天
    private static final int SESSION_EXPIRED_TIME = 12 * 60 * 60;
    // 记住登录状态的session过期时间为30天
    private static final int REMEMBER_SESSION_EXPIRED_TIME = 30 * 24 * 60 * 60;

    @Resource(name = "jdkRedisTemplate")
    private RedisTemplate<String, AuthSession> sessionRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;
    @Autowired
    private IAuthService authService;

    @Override
    public AuthSession createSession(String ip, String platformSystem) {
        if (StringUtils.isBlank(platformSystem)) {
            return null;
        }

        // 生成一个新的随机临时token
        String sessionId = randomASessionId();
        String nonce = nonce();

        AuthSession session = new AuthSession();
        session.setSessionId(sessionId);
        session.setRealm(platformSystem);
        session.setNonce(nonce);
        session.setRemoteIp(ip);
        session.setExpiredTimeSeconds(NONCE_SESSION_EXPIRED_TIME);

        saveSession(session);

        return session;
    }

    @Override
    public void updateSession(AuthSession session) {
        saveSession(session);
    }

    private String randomASessionId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private String nonce() {
        List<String> nonce = Arrays.asList(UUID.randomUUID().toString().replaceAll("-", "").split(""));
        Collections.shuffle(nonce);
        return StringUtils.join(nonce.iterator(), "");
    }

    @Override
    public AuthSession login(String sessionId, String ip, String requestURI, String loginName, String authorizeCode,
            boolean isRememberLogin) throws AuthorizeException {
        if (StringUtils.isEmpty(sessionId)) {
            loginFailed(null, ip);
            throw new AuthorizeException(AuthorizeException.ILLEGAL_OPERATION);
        }

        AuthSession session = fetchSession(sessionId);
        if (session == null) {
            loginFailed(session, ip);
            throw new AuthorizeException(AuthorizeException.SYSTEM_ERROR);
        }

        // authorize first.
        String userId = null;
        try {
            userId = authorize(session.getRealm(), session.getNonce(), requestURI, loginName, authorizeCode);
        } catch (AuthorizeException e) {
            loginFailed(session, ip);
            throw e;
        }
        if (StringUtils.isEmpty(userId)) {
            loginFailed(session, ip);
            throw new AuthorizeException(AuthorizeException.USER_INEXISTENCE);
        }
        session.setUserId(userId);
        session.setRememberLogin(isRememberLogin);
        session.setExpiredTimeSeconds(getSessionExpiredTime(isRememberLogin));

        saveSession(session);

        return session;
    }


    private int getSessionExpiredTime(boolean isRememberLogin) {
        return isRememberLogin ? REMEMBER_SESSION_EXPIRED_TIME : SESSION_EXPIRED_TIME;
    }

    private void saveSession(AuthSession session) {
        if (session != null && StringUtils.isNotEmpty(session.getSessionId())) {
            // 获得key
            String sessionKey = RedisKeyGenerator.genSessionKey(session.getSessionId());
            sessionRedisTemplate.opsForValue().set(sessionKey, session);

            if (StringUtils.isNotEmpty(session.getUserId())) {
                String sessionSetKey = RedisKeyGenerator.genSessionIdListKey(session.getUserId());
                stringRedisTemplate.opsForSet().add(sessionSetKey, session.getSessionId());
                // 记录用户当前session
                String userType = session.getAttribute(SessionAttribute.USER_TYPE_KEY) != null
                        ? String.valueOf(session.getAttribute(SessionAttribute.USER_TYPE_KEY))
                        : "";
                String currentSessionKey = RedisKeyGenerator.genCurrentSessionKey(session.getUserId(),
                        session.getRealm(), userType);
                stringRedisTemplate.opsForValue().set(currentSessionKey, session.getSessionId());
            }
            refreshSession(session);
        }
    }

    private void refreshSession(AuthSession session) {
        if (session != null && StringUtils.isNotEmpty(session.getSessionId())) {
            // 获得key
            String sessionKey = RedisKeyGenerator.genSessionKey(session.getSessionId());
            sessionRedisTemplate.expire(sessionKey, session.getExpiredTimeSeconds(), TimeUnit.SECONDS);

            if (StringUtils.isNotEmpty(session.getUserId())) {
                String sessionSetKey = RedisKeyGenerator.genSessionIdListKey(session.getUserId());
                stringRedisTemplate.expire(sessionSetKey, session.getExpiredTimeSeconds(), TimeUnit.SECONDS);
                // 记录用户当前session
                String userType = session.getAttribute(SessionAttribute.USER_TYPE_KEY) != null
                        ? String.valueOf(session.getAttribute(SessionAttribute.USER_TYPE_KEY))
                        : "";
                String currentSessionKey = RedisKeyGenerator.genCurrentSessionKey(session.getUserId(),
                        session.getRealm(), userType);
                stringRedisTemplate.expire(currentSessionKey, session.getExpiredTimeSeconds(), TimeUnit.SECONDS);
            }

        }
    }

    private AuthSession fetchSession(String sessionId) {
        try {
            String sessionKey = RedisKeyGenerator.genSessionKey(sessionId);
            return sessionRedisTemplate.opsForValue().get(sessionKey);
        } catch (Exception e) {
            return null;
        }
    }


    private String authorize(String platformSystem, String nonce, String requestURI, String loginName,
            String authorizeCode) throws AuthorizeException {
        if (StringUtils.isEmpty(platformSystem) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(requestURI)
                || StringUtils.isEmpty(loginName) || StringUtils.isEmpty(authorizeCode)) {
            throw new AuthorizeException(AuthorizeException.ILLEGAL_OPERATION);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("realm:" + platformSystem + "------------------------");
            logger.debug("nonce:" + nonce + "------------------------");
            logger.debug("authorizeCode:" + authorizeCode + "------------------------");
        }

        String lowerLoginName = StringUtils.lowerCase(loginName);



        Auth auth = authService.getAuth(lowerLoginName);
        if (auth == null || StringUtils.isEmpty(auth.getUserId())) {
            throw new AuthorizeException(AuthorizeException.USER_INEXISTENCE);
        }
        String pwd = auth.getMd5Password();
        String userId = auth.getUserId();


        String myAuthorizeCode = generateAuthorizeCode(loginName, platformSystem, pwd, requestURI, nonce);
        logger.debug("myAuthorizeCode:" + myAuthorizeCode + "------------------------");

        if (!myAuthorizeCode.equals(authorizeCode)) {
            throw new AuthorizeException(AuthorizeException.PASSWORD_ERROR);
        }

        return userId;
    }

    private String generateAuthorizeCode(String loginName, String realm, String md5Password, String requestURI,
            String nonce) {
        StringBuilder a1 = new StringBuilder();
        a1.append(loginName).append(":").append(realm).append(":").append(md5Password);
        if (logger.isDebugEnabled()) {
            logger.debug("a1:" + a1 + "------------------------");
        }
        StringBuilder a2 = new StringBuilder();
        a2.append("POST:").append(requestURI);
        if (logger.isDebugEnabled()) {
            logger.debug("a2:" + a2 + "------------------------");
        }
        String ha1 = DigestUtils.md5Hex(a1.toString());
        if (logger.isDebugEnabled()) {
            logger.debug("ha1:" + ha1 + "------------------------");
        }
        String ha2 = DigestUtils.md5Hex(a2.toString());
        if (logger.isDebugEnabled()) {
            logger.debug("ha2:" + ha2 + "------------------------");
        }
        return DigestUtils.md5Hex(ha1 + ":" + nonce + ":" + ha2);
    }

    private void loginFailed(AuthSession session, String ip) {
        if (session != null) {
            session.setLoginFails(session.getLoginFails() + 1);
            saveSession(session);
        }

        String ipFailsKey = RedisKeyGenerator.genIpLoginFailsKey(ip);
        stringRedisTemplate.opsForValue().increment(ipFailsKey, 1);
        stringRedisTemplate.expireAt(ipFailsKey,
                DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DAY_OF_MONTH));
    }

    private void captchaMobileLoginFailed(AuthSession session) {
        if (session != null) {
            session.setMobileCaptchaLoginFails(session.getMobileCaptchaLoginFails() + 1);
            saveSession(session);
        }
    }

    @Override
    public AuthSession getSession(String sessionId) {
        AuthSession session = fetchSession(sessionId);
        if (session != null) {
            refreshSession(session);
        }
        return session;
    }

    @Override
    public void logoutSession(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return;
        }
        AuthSession session = fetchSession(sessionId);
        if (null == session) {
            return;
        }

        String sessionSetKey = RedisKeyGenerator.genSessionIdListKey(session.getUserId());

        if (StringUtils.isNotEmpty(session.getUserId())) {
            // 移除登录的session
            stringRedisTemplate.opsForSet().remove(sessionSetKey, sessionId);
        }
        // gen key.
        String sessionKey = RedisKeyGenerator.genSessionKey(sessionId);
        sessionRedisTemplate.delete(sessionKey);
    }
}
