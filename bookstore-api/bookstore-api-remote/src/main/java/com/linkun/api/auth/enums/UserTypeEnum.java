package com.linkun.api.auth.enums;

/**
 * 用户类型
 */
public enum UserTypeEnum {

    BOOKSTORE_C(1);

    private int value;

    private UserTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }}
