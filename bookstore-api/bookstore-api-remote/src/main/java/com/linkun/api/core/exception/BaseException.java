package com.linkun.api.core.exception;

public class BaseException extends Exception {

    private static final long serialVersionUID = -463492418595191842L;

    public static final String SYSTEM_ERROR = "00001";

    public static final String ILLEGAL_OPERATION = "00002";

    public static final String IMAGE_CAPTCHA_ERROR = "00003";

    public static final String MOBILE_CAPTCHA_ERROR = "00004";

    public static final String UNLOGIN_ERROR = "00005";

    public static final String PAGE_INVALID = "00006";

    public static final String CAPTCHA_SENDED = "00007";

    public static final String UNAUTHORIZED_ACCESS = "00008";

    public static final String ILLEGAL_PARAMS = "00009";

    public BaseException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public BaseException(String errorCode) {
        super(errorCode);
    }

    public BaseException() {
        super();
    }

    public String getErrorCode() {
        return super.getMessage();
    }
}
