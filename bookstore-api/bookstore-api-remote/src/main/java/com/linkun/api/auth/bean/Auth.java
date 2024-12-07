package com.linkun.api.auth.bean;

public class Auth {

    private String userId;

    private String md5Password;

    public Auth(String userId, String md5Password) {
        super();
        this.userId = userId;
        this.md5Password = md5Password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMd5Password() {
        return md5Password;
    }

    public void setMd5Password(String md5Password) {
        this.md5Password = md5Password;
    }
}
