package com.linkun.api.auth.exception;

import com.linkun.api.core.exception.BaseException;

public class AuthorizeException extends BaseException {

    private static final long serialVersionUID = 2685646517662316515L;

    public static final String USER_INEXISTENCE = "01001";
    public static final String PASSWORD_ERROR = "01002";

    public AuthorizeException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public AuthorizeException(String errorCode) {
        super(errorCode);
    }

}
