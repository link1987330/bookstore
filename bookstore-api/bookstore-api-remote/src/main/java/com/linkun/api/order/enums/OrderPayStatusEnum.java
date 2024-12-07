package com.linkun.api.order.enums;

/**
 * 订单支付状态
 */
public enum OrderPayStatusEnum {

    UN_PAID(0),
    PAID(1);

    private int value;

    private OrderPayStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }}
