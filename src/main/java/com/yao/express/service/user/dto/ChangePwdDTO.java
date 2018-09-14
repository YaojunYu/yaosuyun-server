package com.yao.express.service.user.dto;

public class ChangePwdDTO {

    // 原来的
    private String oldpwd;
    // 账号密码
    private String newpwd;
    // 确认密码
    private String confirmpwd;

    public String getOldpwd() {
        return oldpwd;
    }

    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }

    public String getNewpwd() {
        return newpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }

    public String getConfirmpwd() {
        return confirmpwd;
    }

    public void setConfirmpwd(String confirmpwd) {
        this.confirmpwd = confirmpwd;
    }
}
