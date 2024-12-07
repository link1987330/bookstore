package com.linkun.api.order.remote;

import com.linkun.order.model.OrderItem;
import com.linkun.api.order.service.IOrderItemService;

import java.util.Date;

import com.linkun.utils.NumberUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 下单商品明细 remote服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Service
public class OrderItemRemoteService implements IOrderItemRemoteService {

    @Autowired
    private IOrderItemService orderItemService;



    @Override
    public OrderItem getById(Long id) {
        if (NumberUtils.isInvalid(id)) {
            return null;
        }
        return orderItemService.getById(id);
    }


    @Override
    public void deleteById(Long operatorId,Long id) {
        OrderItem orderItem = new OrderItem();
        orderItem.setDefunct(true);
        orderItem.setId(id);
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUserId(operatorId);
        orderItemService.updateById(orderItem);
    }




}

