package com.yao.express.service.user.enums;

public enum AccountStatusEnum {

    CREATE("create", "已创建待审核"),
    NORMAL("normal", "账号正常"),
    DELETE("delete", "账号删除"),
    FROZEN("frozen", "账号冻结"),
    ;

    public String value;
    public String desc;

    AccountStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
