package com.yao.express.service.user.util.login;

/**
 * [注释]
 *
 * @author: York.Yu
 * @date: 2017/5/15
 */
public class User {
    private String username;
    private String usersex;

    private String userage;

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getUserage() {
        return userage;
    }

    public void setUserage(String userage) {
        this.userage = userage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
