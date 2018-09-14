package com.yao.express.service.user.enums;

public enum WhoTypeEnum {
    CUSTOMER("customer", "顾客账号"),
    DRIVER("driver", "司机账号"),
    ;

    public String value;
    public String desc;

    WhoTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
