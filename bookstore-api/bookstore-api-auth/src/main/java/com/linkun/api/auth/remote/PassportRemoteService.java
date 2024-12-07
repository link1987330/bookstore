package com.linkun.api.auth.remote;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linkun.api.auth.enums.UserTypeEnum;
import com.linkun.api.auth.exception.PassportException;
import com.linkun.api.auth.mapper.PassportMapper;
import com.linkun.auth.model.Passport;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PassportRemoteService implements IPassportRemoteService {

    private Logger logger = LoggerFactory.getLogger(PassportRemoteService.class);

    @Autowired
    private PassportMapper passportMapper;

    private boolean mobileExist(String mobile, UserTypeEnum userTypeEnum) {
        if (StringUtils.isBlank(mobile) || userTypeEnum == null) {
            return false;
        }
        final LambdaQueryWrapper<Passport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Passport::getMobile, userTypeEnum.getValue()).eq(Passport::getDefunct, false);
        return passportMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Passport create(String mobile, String password, UserTypeEnum userTypeEnum) throws PassportException {
        if (StringUtils.isAnyBlank(mobile, password) || userTypeEnum == null) {
            throw new PassportException(PassportException.ILLEGAL_OPERATION);
        }

        if (mobileExist(mobile, userTypeEnum)) {
            throw new PassportException(PassportException.MOBILE_EXIST);
        }
        Passport passport = new Passport();
        passport.setMobile(mobile);
        passport.setPassword(password);
        passport.setUserType(userTypeEnum.getValue());
        passport.setCreateTime(new Date());
        passport.setUpdateTime(passport.getCreateTime());
        if (passportMapper.insert(passport) <= 0) {
            logger.error("passport insert error!mobile:[{}]", mobile);
            throw new PassportException(PassportException.SYSTEM_ERROR);
        }
        return passport;
    }

}
