package com.linkun.api.auth.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linkun.api.auth.bean.Auth;
import com.linkun.api.auth.mapper.PassportMapper;
import com.linkun.api.auth.service.IAuthService;
import com.linkun.api.user.remote.IUserRemoteService;
import com.linkun.auth.model.Passport;
import com.linkun.user.model.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private PassportMapper passportMapper;
    @Autowired
    private IUserRemoteService userRemoteService;

    @Override
    public Auth getAuth(String loginName) {
        final LambdaQueryWrapper<Passport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Passport::getMobile, loginName);
        List<Passport> passports = passportMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(passports)) {
            return null;
        }
        Passport passport = passports.get(0);
        User user = userRemoteService.getByPhone(loginName);

        return new Auth(user.getId().toString(), passport.getPassword());
    }
}
