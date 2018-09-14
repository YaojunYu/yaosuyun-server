package com.yao.express.service.user.dto;

public class RegisterDTO {

    // 注册电话
    private String phone;
    // 账号密码
    private String password;
    // 短信验证码
    private String vcode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
