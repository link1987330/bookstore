package com.linkun.api.order.enums;

/**
 * 订单状态
 */
public enum OrderStatusEnum {

    COMPLETE(1);

    private int value;

    private OrderStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }}
