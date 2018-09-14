package com.yao.express.service.user.enums;

public enum HeartbeatMsgType {
    ORDER_GOT("order_got", "订单分配成功"),
    ORDER_NEW("order_new", "您有新的速运订单，请及时处理"),
    ORDER_REJECTED("order_rejected", "订单被司机拒绝，请重新下单"),
    ORDER_BLOCKED("order_blocked", "司机已申请货物运输故障，请联系客服：010-122323"),
    ORDER_FINISHED("order_finished", "货物已成功签收"),
    ORDER_SENT("order_sent", "订单已发货"),
    ORDER_BACK("order_back", "司机申请送回订单货物，请您及时处理"),
    ORDER_BACKED("order_backed", "货物已送回"),
    ORDER_BACKING("order_backing", "顾客已经同意送回订单货物，请及时处理"),
    ORDER_CANCELED("order_canceled", "订单已被顾客取消，请知晓"),
    ORDER_TIMEOUT("order_timeout", "订单无人应答，请重试"),
    NORMAL_TIP("tip", "新消息"),
    ORDER_ARRIVED("order_arrived", "司机已到达取货地点，请及时联系");

    public String value;
    public String desc;

    HeartbeatMsgType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
