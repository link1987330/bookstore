package com.linkun.api.user.exception;

import com.linkun.api.core.exception.BaseException;

public class UserException extends BaseException {

    private static final long serialVersionUID = 8548192808654135906L;

    public static final String ACCOUNT_EXISTENCE = "15001";

    public static final String EMAIL_ALREADY_EXIS = "15002";

    public static final String PHONE_ALREADY_EXIS = "15004";

    public static final String PASSWORD_INCONSISTENT = "15005";

    public static final String PHONE_NOT_REGISTER = "15013";


    public UserException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public UserException(String errorCode) {
        super(errorCode);
    }
}
