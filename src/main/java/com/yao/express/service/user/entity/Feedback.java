package com.yao.express.service.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Feedback {
    private Long id;

    private String app;

    private String user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date updateTime;

    private String descption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getDescption() {
        return descption;
    }

    public void setDescption(String descption) {
        this.descption = descption;
    }
}