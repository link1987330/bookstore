package com.linkun.api.auth.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 2685646517662316515L;
    /**
     * 账号
     */
    private String account;
    /**
     * 加密授权码
     */
    private String authorizeCode;
    /**
     * 是否记住登录
     */
    private boolean isRememberLogin;
}
