package com.linkun.api.user.service;

import com.linkun.user.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  用户服务类
 * </p>
 *
 * @author linkun
 * @since 2024-12-05
 */
public interface IUserService extends IService<User> {
    User getByPhone(String phone);
}
