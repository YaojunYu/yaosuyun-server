package com.yao.express.service.user.entity;

import java.util.Date;

public class UserLogin {
    private Long id;

    private String userLoginId;

    private String account;

    private String accountType;

    private String loginIp;

    private String clientType;

    private Date loginTime;

    private Double loginLatitude;

    private Double loginLongitude;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Double getLoginLatitude() {
        return loginLatitude;
    }

    public void setLoginLatitude(Double loginLatitude) {
        this.loginLatitude = loginLatitude;
    }

    public Double getLoginLongitude() {
        return loginLongitude;
    }

    public void setLoginLongitude(Double loginLongitude) {
        this.loginLongitude = loginLongitude;
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