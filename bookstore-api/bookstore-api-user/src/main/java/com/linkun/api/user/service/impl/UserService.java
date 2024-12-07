package com.linkun.api.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linkun.user.model.User;
import com.linkun.api.user.mapper.UserMapper;
import com.linkun.api.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-05
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone).eq(User::getDefunct, false);

        return this.getOne(wrapper);
    }
}
