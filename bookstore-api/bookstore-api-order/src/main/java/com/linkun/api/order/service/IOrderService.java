package com.linkun.api.order.service;

import com.linkun.api.order.dto.OrderDto;
import com.linkun.order.model.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单信息 服务类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
public interface IOrderService extends IService<Order> {

    Order createOrder(Long operatorId, OrderDto orderDto);
}
