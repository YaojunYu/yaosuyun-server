package com.yao.express.service.user.enums;

public enum VehicleTypeEnum {
    // 摩托车/三轮车/面包车/货车/轿车
    MOTOR("motor", "摩托车"),
    TRIKE("trike", "三轮车"),
    MINIBUS("minibus", "面包车"),
    TRUCK("truck", "货车"),
    CAR("car", "轿车"),
    ;

    public String value;
    public String desc;

    VehicleTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
