package com.yao.express.service.user.enums;

public enum AccountRoleEnum {
    // 顾客账号=customer，司机账号=driver，管理员账号=manager
    CUSTOMER("customer", "顾客账号"),
    DRIVER("driver", "司机账号"),
    MANAGER("manager", "管理员账号")
    ;

    public String value;
    public String desc;

    AccountRoleEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
