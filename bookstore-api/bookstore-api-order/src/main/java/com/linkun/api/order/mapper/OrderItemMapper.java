package com.linkun.api.order.mapper;

import com.linkun.order.model.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 下单商品明细 Mapper 接口
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
 @Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

}
