package com.linkun.api.user.mapper;

import com.linkun.user.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linkun
 * @since 2024-12-05
 */
 @Mapper
public interface UserMapper extends BaseMapper<User> {

}
