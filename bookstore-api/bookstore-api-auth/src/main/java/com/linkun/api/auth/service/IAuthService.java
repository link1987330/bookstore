package com.linkun.api.auth.service;


import com.linkun.api.auth.bean.Auth;

public interface IAuthService {

    /**
     * 
     * 功能描述: <br>
     * 获取用户的授权信息
     *
     * @param loginName 登录名
     * @return 授权信息
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    Auth getAuth(String loginName);
}
