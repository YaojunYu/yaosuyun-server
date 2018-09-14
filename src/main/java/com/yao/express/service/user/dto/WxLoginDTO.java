package com.yao.express.service.user.dto;

public class WxLoginDTO {

    private String code;
    private WxLocation location;
    private WxLoginRes res;

    public WxLocation getLocation() {
        return location;
    }

    public void setLocation(WxLocation location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public WxLoginRes getRes() {
        return res;
    }

    public void setRes(WxLoginRes res) {
        this.res = res;
    }
}
