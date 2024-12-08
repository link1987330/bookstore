package com.linkun.c.core.exception;

import com.linkun.api.core.exception.BaseException;

public class NeedLoginException extends BaseException {

    private static final long serialVersionUID = -6779527704085948243L;

    public NeedLoginException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public NeedLoginException(String errorCode) {
        super(errorCode);
    }

    public NeedLoginException() {
        super();
    }

    public String getErrorCode() {
        return super.getMessage();
    }
}
