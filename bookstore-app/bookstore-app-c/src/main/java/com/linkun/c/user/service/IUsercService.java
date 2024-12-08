package com.linkun.c.user.service;

import com.linkun.c.user.view.UserView;

public interface IUsercService {

    /**
     * 根据用户id（hr的id）获取HR信息
     *
     * @param userId 用户ID
     * @return view
     */
    UserView getUserByUserId(Long userId);

    /**
     * 根据手机号获取HR信息
     * 
     * @param phone 手机
     * @return bean
     */
    UserView getByPhone(String phone);

}
