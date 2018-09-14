package com.yao.express.service.user.enums;

public enum OrderTypeEnum {
    // 订单类型：实时订单/预约订单
    REAL("real", "实时订单"),
    BOOK("book", "预约订单"),
    ;

    public String value;
    public String desc;

    OrderTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
