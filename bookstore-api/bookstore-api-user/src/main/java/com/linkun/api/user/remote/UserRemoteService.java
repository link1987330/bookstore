package com.linkun.api.user.remote;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linkun.api.auth.enums.UserTypeEnum;
import com.linkun.api.auth.exception.PassportException;
import com.linkun.api.auth.remote.IPassportRemoteService;
import com.linkun.api.user.dto.UserDto;
import com.linkun.api.user.exception.UserException;
import com.linkun.user.model.User;
import com.linkun.api.user.service.IUserService;

import java.util.Date;
import com.linkun.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  remote服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-05
 */
@Service
public class UserRemoteService implements IUserRemoteService {

    private Logger logger = LoggerFactory.getLogger(UserRemoteService.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IPassportRemoteService passportRemoteService;

    @Override
    public User getById(Long id) {
        if (NumberUtils.isInvalid(id)) {
            return null;
        }
        return userService.getById(id);
    }

    @Override
    public User getByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        return userService.getByPhone(phone);
    }


    @Override
    public void deleteById(Long operatorId,Long id) {
        User user = new User();
        user.setDefunct(true);
        user.setId(id);
        user.setUpdateTime(new Date());
        user.setUpdateUserId(operatorId);
        userService.updateById(user);
    }

    @Override
    public Long regist(UserDto userDto) throws UserException, PassportException {
        if (StringUtils.isAnyBlank(userDto.getPhone(), userDto.getPassword())) {
            throw new UserException(UserException.ILLEGAL_PARAMS);
        }
        // checkUserExist
        if (countByPhone(userDto.getPhone()) > 0) {
            logger.info("user exist! phone:[{}]", userDto.getPhone());
            throw new UserException(UserException.ACCOUNT_EXISTENCE);
        }

        User user = new User();
        userDto.forwardConvert(user);

        userService.save(user);
        passportRemoteService.create(user.getPhone(), userDto.getPassword(), UserTypeEnum.BOOKSTORE_C);
        return user.getId();
    }

    private int countByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return 0;
        }
        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);

        return userService.count(wrapper);
    }
}

