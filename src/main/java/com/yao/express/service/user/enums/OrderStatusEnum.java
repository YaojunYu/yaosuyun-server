package com.yao.express.service.user.enums;

public enum OrderStatusEnum {
    // 订单类型：实时订单/预约订单
    CREATED("created", "创建"),
    CANCELED("canceled", "取消"),
    TIMEOUT("timeout", "分配超时"),
    DELETED("deleted", "删除"),
    FINISHED("finished", "完成"),
    BLOCKED("blocked", "问题单"),
    GOT("got", "接单"),
    ARRIVED("arrived", "到达"),
    SEND("send", "送货"),
    REJECTED("rejected", "已拒绝"),
    BACK("back", "申请送回"),
    BACKING("backing", "同意送回"),
    BACKED("backed", "已送回"),

    ;

    public String value;
    public String desc;

    OrderStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
