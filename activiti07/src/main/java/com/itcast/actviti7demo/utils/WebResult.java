package com.itcast.actviti7demo.utils;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class WebResult implements Serializable {
    private boolean success;
    private Object data;
    private Integer errorCode;
    private String errorMsg;

    public WebResult() {
    }

    public WebResult(boolean success, Object data, Integer errorCode, String errorMsg) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private WebResult(boolean success) {
        this.success = success;
    }

    public static WebResult success() {
        return new WebResult(true);
    }

    public static WebResult failed() {
        WebResult failed = new WebResult(false);
        failed.errorCode = WebResult.ErrorCode.UNDEFINED.code;
        return failed;
    }

    public WebResult withErrorCode(WebResult.ErrorCode errorCode) {
        this.errorCode = errorCode.code;
        return this;
    }

    public WebResult withErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public WebResult withData(Object data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Object getData() {
        return this.data;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public enum ErrorCode {
        UNDEFINED(1),
        PARAMETER_ILLEGAL(11),
        PERSISTENT_ERROR(21),
        RESOURCE_BUSY(31),
        METHOD_NOT_ALLOWED(405),
        ACCESS_DENIED(999);

        private Integer code;

        private ErrorCode(Integer code) {
            this.code = code;
        }
    }
}
