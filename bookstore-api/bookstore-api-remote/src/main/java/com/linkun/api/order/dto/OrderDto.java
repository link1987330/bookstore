package com.linkun.api.order.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.springframework.util.CollectionUtils;

@Data
public class OrderDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;

    /**
     * 下单商品详情
     */
    private List<OrderItemDto> orderItemDtos;
    // 实际应该是地址对象，持久化到地址表中
    private String address;

    public boolean checkCreateParams() {
        if (CollectionUtils.isEmpty(orderItemDtos)) {
            return false;
        }
        return true;
    }
}
