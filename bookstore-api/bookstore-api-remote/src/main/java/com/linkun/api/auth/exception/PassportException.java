package com.linkun.api.auth.exception;


import com.linkun.api.core.exception.BaseException;

public class PassportException extends BaseException {

    private static final long serialVersionUID = 3753956119439557208L;

    public static final String MOBILE_EXIST = "02001";

    public static final String CONFIRM_PASSWORD_NOT_EQUAL = "02002";

    public PassportException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public PassportException(String errorCode) {
        super(errorCode);
    }
}
