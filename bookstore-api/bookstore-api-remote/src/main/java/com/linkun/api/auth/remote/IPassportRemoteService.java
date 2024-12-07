package com.linkun.api.auth.remote;

import com.linkun.api.auth.enums.PlatformSystemEnum;
import com.linkun.api.auth.enums.UserTypeEnum;
import com.linkun.api.auth.exception.PassportException;
import com.linkun.auth.model.Passport;


public interface IPassportRemoteService {

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉创建账号根据类型
     *
     * @param mobile 手机
     * @param password 密码
     * @param userTypeEnum 用户类型
     * @return
     * @throws PassportException
     */
    Passport create(String mobile, String password, UserTypeEnum userTypeEnum) throws PassportException;

}
