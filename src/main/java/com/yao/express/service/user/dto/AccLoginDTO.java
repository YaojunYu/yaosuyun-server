package com.yao.express.service.user.dto;

/**
 * 普通账号登录
 */
public class AccLoginDTO {

    // 登录账号
    private String account;
    // 登录密码
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
