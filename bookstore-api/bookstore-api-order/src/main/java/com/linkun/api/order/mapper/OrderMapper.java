package com.linkun.api.order.mapper;

import com.linkun.order.model.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单信息 Mapper 接口
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
 @Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
