package com.linkun.api.book.exception;

import com.linkun.api.core.exception.BaseException;

public class BookException extends BaseException {

    private static final long serialVersionUID = 8548192808654135906L;
    /**
     * 书本已存在
     */
    public static final String BOOK_EXISTENCE = "11001";



    public BookException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public BookException(String errorCode) {
        super(errorCode);
    }
}
