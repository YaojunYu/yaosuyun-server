package com.yao.express.service.user.dto;

import com.alibaba.fastjson.JSON;

public class HeartbeatRequest {

    private String whoId;

    private String whoType;

    private Double latitude;

    private Double longitude;

    public String getWhoId() {
        return whoId;
    }

    public void setWhoId(String whoId) {
        this.whoId = whoId;
    }

    public String getWhoType() {
        return whoType;
    }

    public void setWhoType(String whoType) {
        this.whoType = whoType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
