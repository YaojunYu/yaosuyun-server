package com.yao.express.service.user.response;

public enum Project {

    BRAND_UI(1),
    BRAND_API(2),
    MEMBER_API(3),
    MARKET_API(4),
    PAYMENT_API(5),
    SETTLEMENT_API(6),
    TRANSACTION_API(7),
    WECHAT_API(8),
    ERP_API(9);

    private String no;

    private Project(int no) {
        this.no = String.format("%03d", no);
    }

    public String getNo() {
        return no;
    }
}
