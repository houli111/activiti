package com.itcast.actviti7demo;

public enum CodeEnum {
    SUCCESS(200, "SUCCESS"),
    PARAMETER_ERROR(400, "参数错误"),
    FILE_ERROR(410,"文件保存失败"),
    LOGIN_ERROR(420,"登录失败"),
    LOGIN_TIME_OUT(421,"登录已失效"),
    SERVER_ERROR(500, "服务端错误");//服务端错误
    private Integer code;
    private String message;

    CodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
