package com.linkun.api.order.exception;

import com.linkun.api.core.exception.BaseException;

public class OrderException extends BaseException {

    private static final long serialVersionUID = 8548192808654135906L;

    public static final String BOOK_NOT_EXIST = "12001";

    public OrderException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public OrderException(String errorCode) {
        super(errorCode);
    }
}
