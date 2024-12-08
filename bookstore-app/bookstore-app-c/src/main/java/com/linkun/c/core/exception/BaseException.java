//package com.linkun.c.core.exception;
//
//public class BaseException extends Exception {
//
//    private static final long serialVersionUID = -463492418595191842L;
//
//    public static final String SYSTEM_ERROR = "00001";
//
//    public static final String ILLEGAL_OPERATION = "00002";
//
//    public static final String IMAGE_CAPTCHA_ERROR = "00003";
//
//    public static final String MOBILE_CAPTCHA_ERROR = "00004";
//
//    public static final String UNLOGIN_ERROR = "00005";
//
//    public static final String PAGE_INVALID = "00006";
//
//    public static final String CAPTCHA_SENDED = "00007";
//
//    public static final String UNAUTHORIZED_ACCESS = "00008";
//
//    public static final String UPLOAD_FAILED = "00009";
//
//    public static final String FORMAT_ERROR = "000010";
//
//    public static final String GET_MOBILE_CAPTCHA_AGAIN = "000011";
//    // 测试用户权限限制
//    public static final String TEST_USER_ILLEGAL_OPERATION = "000012";
//
//    public static final String NONSUPPORT_FILE_TYPE = "000013";
//
//    public static final String INVITATION_CODE_NO_EXIST = "000014";
//
//    public BaseException(String errorCode, Throwable cause) {
//        super(errorCode, cause);
//    }
//
//    public BaseException(String errorCode) {
//        super(errorCode);
//    }
//
//    public BaseException() {
//        super();
//    }
//
//    public String getErrorCode() {
//        return super.getMessage();
//    }
//}
