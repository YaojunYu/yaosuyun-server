package com.yao.express.service.user.exception;

public enum ResponseErrorCode {
    WX_CODE_2_SESSION_FAILED(5001, "Http GET wx code to session failed"),
    WX_MAP_SERVICE_REQUEST_FAIL(5002, "请求地图服务失败"),
    REQUEST_PARAMS_INVALID(4000, "请求参数非法，请检查请求参数格式"),
    WX_CODE_2_SESSION_CODE_EMPTY(4001, "param code cannot be null"),
    ORDER_REQUEST_SENDER_ID_REQUIRED(4002, "下单发货人id缺失"),
    ORDER_SENDER_NOT_FOUND(4003, "下单人信息未找到"),
    ORDER_REQUEST_ORDER_TYPE_INVALID(4004, "订单类型orderType非法，应为real/book"),
    ORDER_REQUEST_ORDER_BOOK_TIME_REQUIRED(4005, "订单类型为book时，bookTime必传"),
    HEART_BEAT_WHO_TYPE_INVALID(4006, "心跳对象类型不对"),
    ORDER_QUERY_FORBIDDEN(4007, "订单查看权限受限"),
    ORDER_NOT_FOUND(4008, "订单未找到"),
    ORDER_OPS_FORBIDDEN(4009, "没有操作该订单的权限"),
    VCODE_NOT_VALID(4010, "验证码错误"),
    ACCOUNT_HAS_EXIST(4011, "账号已经存在"),
    ACCOUNT_LOGIN_FAIL(4012, "账号或密码不正确"),
    PHONE_NOT_VALID(4013, "手机格式不正确"),
    ACCOUNT_NOT_NORMAL(4013, "账号已被禁用"),
    ORDER_STATUS_OPS_FORBIDDEN(4014, "订单当前状态不允许该操作"),
    DRIVER_ACCOUNT_NOT_FOUND(4015, "司机账号未找到"),
    DRIVER_ACCOUNT_PASSWORD_INVALID(4016, "账号原密码不正确"),
    DRIVER_HAS_NOT_COMPLETE_ORDER(4017, "司机尚有未完成的订单，请尽快完成订单后再操作"),
    DATE_FORMAT_INVALID(4018, "日期格式不正确，yyyy-MM-dd HH:mm:ss"),
    ACCOUNT_NOT_FOUND(4019, "账号不存在"),
    NEW_CONFIRM_PWD_NOT_SAME(4020, "确认密码和密码不一致"),
    OLD_PWD_INVALID(4021, "原密码不正确"),
    ACCOUNT_IS_NOT_PHONE(4022, "账号非电话号码注册")
    ;




    public int code;
    public String msg;
    ResponseErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
