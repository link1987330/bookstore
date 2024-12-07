package com.linkun.api.order.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrderItemDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 下单数量
     */
    private Integer quantity;

}
