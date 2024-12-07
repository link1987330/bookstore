package com.linkun.api.inventory.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class InventoryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long bookId;

    private Integer quantity;

    public InventoryDto () {}

    public InventoryDto (Long bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
