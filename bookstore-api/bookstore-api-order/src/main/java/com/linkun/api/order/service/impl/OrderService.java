package com.linkun.api.order.service.impl;

import java.math.BigDecimal;

import com.linkun.api.order.dto.OrderDto;
import com.linkun.api.order.enums.OrderPayStatusEnum;
import com.linkun.api.order.enums.OrderStatusEnum;
import com.linkun.order.model.Order;
import com.linkun.api.order.mapper.OrderMapper;
import com.linkun.api.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public Order createOrder(Long operatorId, OrderDto orderDto) {

        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.COMPLETE.getValue());
        order.setPayStatus(OrderPayStatusEnum.PAID.getValue());
        order.setTotalAmount(BigDecimal.ZERO);
        order.setCreateUserId(operatorId);
        order.setUpdateUserId(operatorId);

        this.save(order);
        return order;
    }
}
