package com.yao.express.service.user.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 登录返回实体
 */
public class LoginUser implements Serializable {

    // 账号id
    private String uid;
    // 司机登录账号
    private String account;
    // 司机姓名
    private String name;
    // 司机性别
    private String sex;
    // 司机头像
    private String photo;
    // token
    private String token;
    // 角色
    private List<String> roles;

    private String phone;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
