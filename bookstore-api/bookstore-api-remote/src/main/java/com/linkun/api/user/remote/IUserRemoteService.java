package com.linkun.api.user.remote;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.linkun.api.auth.exception.PassportException;
import com.linkun.api.user.dto.UserDto;
import com.linkun.api.user.exception.UserException;
import com.linkun.user.model.User;


/**
 * <p>
 *  remote类
 * </p>
 *
 * @author linkun
 * @since 2024-12-05
 */

public interface IUserRemoteService{
     /**
     * 根据ID获取详情
     *
     * @param id 主键
     * @return
     */
    User getById(Long id);

    User getByPhone(String phone);

    /**
    * 根据ID删除
    *
    * @param id 主键
    * @param operatorId 操作者
    */
    void deleteById(@Min(1) @NotNull Long operatorId, @Min(1) @NotNull Long id);

    /**
     *
     * @param userDto
     * @return
     */
    Long regist(UserDto userDto) throws UserException, PassportException;

}

