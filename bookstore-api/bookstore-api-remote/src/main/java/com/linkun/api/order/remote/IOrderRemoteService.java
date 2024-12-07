package com.linkun.api.order.remote;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.linkun.api.inventory.exception.InventoryException;
import com.linkun.api.order.dto.OrderDto;
import com.linkun.api.order.exception.OrderException;
import com.linkun.order.model.Order;


/**
 * <p>
 * 订单信息 remote类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */

public interface IOrderRemoteService {
     /**
     * 根据ID获取详情
     *
     * @param id 主键
     * @return
     */
    Order getById(Long id);

    /**
    * 根据ID删除
    *
    * @param id 主键
    * @param operatorId 操作者
    */
    void deleteById(@Min(1) @NotNull Long operatorId, @Min(1) @NotNull Long id);

    Order create(Long operatorId, OrderDto orderDto) throws OrderException, InventoryException;

    void cancelById(Long operatorId, Long id);
}

