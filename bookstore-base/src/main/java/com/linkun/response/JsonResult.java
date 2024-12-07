package com.linkun.response;

import java.io.Serializable;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 6208599204673674690L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public final static JsonResult<Object> ERROR_RESULT = new JsonResult<>(false);

    private boolean success = true;
    private String errCode = "";
    private String message = "";

    private T data;

    public JsonResult() {
        super();
    }

    public JsonResult(boolean success) {
        super();
        this.success = success;
    }

    public static JsonResult<Object> error(String errorCode, MessageSource messageSource, Object... params) {
        JsonResult<Object> jsonResult = new JsonResult<>();
        jsonResult.setSuccess(false);
        jsonResult.setErrCode(errorCode);
        jsonResult.setMessage(messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE));
        return jsonResult;
    }

    public static JsonResult<Object> error(String errorCode, String msg) {
        JsonResult<Object> jsonResult = new JsonResult<>();
        jsonResult.setSuccess(false);
        jsonResult.setErrCode(errorCode);
        jsonResult.setMessage(msg);
        return jsonResult;
    }

    public JsonResult<T> success(T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult<Object> success() {
        return new JsonResult<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setError(String errorCode, MessageSource messageSource, Object... params) {
        this.setSuccess(false);
        this.setErrCode(errorCode);
        this.setMessage(messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE));
    }

    @Override
    public String toString() {
        return "JsonResult [logger=" + logger + ", success=" + success + ", errCode=" + errCode + ", message=" + message
                + ", data=" + data + "]";
    }

}
