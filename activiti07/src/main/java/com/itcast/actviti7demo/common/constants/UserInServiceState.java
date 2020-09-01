package com.itcast.actviti7demo.common.constants;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/9/25 11:01
 * Description:
 */
public enum UserInServiceState {
    IN_SERVICE(1, "在职"), RESIGNATION(2, "离职");
    private Integer code;
    private String value;

    UserInServiceState(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static UserInServiceState getByCode(int code) {
        for (UserInServiceState state : values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        return null;
    }
}
