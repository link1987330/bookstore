package com.linkun.api.order.service;

import java.util.List;

import com.linkun.api.order.dto.OrderDto;
import com.linkun.api.order.exception.OrderException;
import com.linkun.order.model.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 下单商品明细 服务类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
public interface IOrderItemService extends IService<OrderItem> {

    List<OrderItem> createOrderItems(Long operatorId, OrderDto orderDto) throws OrderException;
}
