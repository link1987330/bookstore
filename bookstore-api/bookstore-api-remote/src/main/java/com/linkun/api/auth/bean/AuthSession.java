package com.linkun.api.auth.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AuthSession implements Serializable {

    private static final long serialVersionUID = -9147848378687245205L;

    private String sessionId;

    private String realm;

    private String nonce;

    private String userId;

    private String imageCaptcha;

    private String mobileCaptcha;

    private int loginFails;

    private int mobileCaptchaLoginFails;

    private int registerFails;

    private boolean isRememberLogin;

    private boolean notRegister = false;

    /**
     * 过期时间，秒
     */
    private int expiredTimeSeconds;

    private String remoteIp;

    private Map<String, Object> attributes = new HashMap<>();

    public String getSessionId() {
        return sessionId;
    }

    public int getMobileCaptchaLoginFails() {
        return mobileCaptchaLoginFails;
    }

    public void setMobileCaptchaLoginFails(int mobileCaptchaLoginFails) {
        this.mobileCaptchaLoginFails = mobileCaptchaLoginFails;
    }

    public boolean isNotRegister() {
        return notRegister;
    }

    public void setNotRegister(boolean notRegister) {
        this.notRegister = notRegister;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public boolean isRememberLogin() {
        return isRememberLogin;
    }

    public void setRememberLogin(boolean isRememberLogin) {
        this.isRememberLogin = isRememberLogin;
    }

    public int getExpiredTimeSeconds() {
        return expiredTimeSeconds;
    }

    public void setExpiredTimeSeconds(int expiredTimeSeconds) {
        this.expiredTimeSeconds = expiredTimeSeconds;
    }

    public String getImageCaptcha() {
        return imageCaptcha;
    }

    public void setImageCaptcha(String imageCaptcha) {
        this.imageCaptcha = imageCaptcha;
    }

    public String getMobileCaptcha() {
        return mobileCaptcha;
    }

    public void setMobileCaptcha(String mobileCaptcha) {
        this.mobileCaptcha = mobileCaptcha;
    }

    public int getLoginFails() {
        return loginFails;
    }

    public void setLoginFails(int loginFails) {
        this.loginFails = loginFails;
    }

    public int getRegisterFails() {
        return registerFails;
    }

    public void setRegisterFails(int registerFails) {
        this.registerFails = registerFails;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * 功能描述: <br>
     * 获取整数类型用户id
     *
     * @return 整数类型用户id
     */
    public long getNumberUserId() {
        long numberUserId;
        try {
            numberUserId = Long.valueOf(userId);
        } catch (NumberFormatException e) {
            numberUserId = 0L;
        }
        return numberUserId;
    }
}
