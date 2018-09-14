package com.yao.express.service.user.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Heartbeat implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String whoId;

    private String whoType;

    private Boolean available;

    private String msg;

    private Double latitude;

    private Double longitude;

    private Date heartTime;

    private Long version;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public Date getHeartTime() {
        return heartTime;
    }

    public void setHeartTime(Date heartTime) {
        this.heartTime = heartTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}