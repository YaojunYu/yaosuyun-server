package com.yao.express.service.user.enums;

public enum DeliverTypeEnum {
    PRIVATE("private", "专属配送"),
    PUBLIC("public", "公共配送"),
    ;

    public String value;
    public String desc;

    DeliverTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
