package com.itcast.actviti7demo.common.constants;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/11/19 13:32
 * Description:
 */
public enum ApprovalTypeEnum {
    TURNOVER("1", "转正"), ADJUST_POST("2", "调岗"), RESIGNATION("3", "离职"), EMPLOYEE_INFORMATION_REVIEW("4", "员工信息审核")
    , ADJUST_SALARY("5", "changSalary"), SALARY_REVIEW("6", "工资审核"), LEAVE("7", "请假"), SALES_LEAVE("8", "销假")
    , FIELDWORK("9", "外出"), SALES_FIELDWORK("10", "销外出"), TRAVEL("11", "出差"), SALES_TRAVEL("12", "销出差")
    , FIELDWORK_PUNCH("13", "外勤打卡"), COUNTERVAIL_PUNCH("14", "补打卡"), OVERTIME("15", "加班"), RECRUITMENT("16", "招聘")
    , HIRE("17", "录用");
    private String code;
    private String value;

    ApprovalTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ApprovalTypeEnum getByCode(String code) {
        for (ApprovalTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static ApprovalTypeEnum getByValue(String value) {
        for (ApprovalTypeEnum typeEnum : values()) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        return null;
    }
}
