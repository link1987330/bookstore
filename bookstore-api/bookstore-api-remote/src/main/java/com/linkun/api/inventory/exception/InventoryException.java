package com.linkun.api.inventory.exception;

import com.linkun.api.core.exception.BaseException;

public class InventoryException extends BaseException {

    private static final long serialVersionUID = 8548192808654135906L;

    public static final String LOW_INVENTORY = "13001";

    public InventoryException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public InventoryException(String errorCode) {
        super(errorCode);
    }
}
