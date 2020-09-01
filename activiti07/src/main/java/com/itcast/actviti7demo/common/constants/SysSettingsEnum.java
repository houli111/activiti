package com.itcast.actviti7demo.common.constants;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/10/18 14:13
 * Description:
 */
public enum SysSettingsEnum {
    EMPLOYEE_SETTINGS(1, "员工设置");
    private int code;
    private String value;

    SysSettingsEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SysSettingsEnum getByCode(int code) {
        for (SysSettingsEnum fileType : values()) {
            if (fileType.getCode() == code) {
                return fileType;
            }
        }
        return null;
    }
}
