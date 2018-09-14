package com.yao.express.service.user.dto;

/**
 * 心跳消息体
 */
public class HeartbeatMsg {

    // 消息类型
    private String type;
    // 消息类型关联内容，定制
    private Object content;
    // 提示用户消息
    private String tip;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
