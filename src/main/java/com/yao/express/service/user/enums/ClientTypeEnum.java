package com.yao.express.service.user.enums;

public enum ClientTypeEnum {
    // 登录客户端类型：=wx_mini，安卓客户端=，苹果客户端=，web管理端=web
    WX_MINI("wx_mini", "微信小程序"),
    ANDROID_APP("android_app", "安卓客户端"),
    IOS_APP("ios_app", "苹果客户端"),
    WEB_APP("web_app", "web管理系统"),
    H5("h5", "h5应用")
    ;

    public String value;
    public String desc;

    ClientTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
