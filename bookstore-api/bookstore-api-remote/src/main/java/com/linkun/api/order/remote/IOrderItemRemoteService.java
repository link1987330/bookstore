package com.linkun.api.order.remote;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.linkun.order.model.OrderItem;


/**
 * <p>
 * 下单商品明细 remote类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */

public interface IOrderItemRemoteService{
     /**
     * 根据ID获取详情
     *
     * @param id 主键
     * @return
     */
    OrderItem getById(Long id);



    /**
    * 根据ID删除
    *
    * @param id 主键
    * @param operatorId 操作者
    */
    void deleteById(@Min(1) @NotNull Long operatorId, @Min(1) @NotNull Long id);


}

