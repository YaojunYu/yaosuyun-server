package com.yao.express.service.user.mapper;

import com.yao.express.service.user.entity.UserLogin;
import org.apache.ibatis.jdbc.SQL;

public class UserLoginSqlProvider {

    public String insertSelective(UserLogin record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user_login");
        
        if (record.getUserLoginId() != null) {
            sql.VALUES("user_login_id", "#{userLoginId,jdbcType=VARCHAR}");
        }
        
        if (record.getAccount() != null) {
            sql.VALUES("account", "#{account,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountType() != null) {
            sql.VALUES("account_type", "#{accountType,jdbcType=CHAR}");
        }
        
        if (record.getLoginIp() != null) {
            sql.VALUES("login_ip", "#{loginIp,jdbcType=VARCHAR}");
        }
        
        if (record.getClientType() != null) {
            sql.VALUES("client_type", "#{clientType,jdbcType=VARCHAR}");
        }
        
        if (record.getLoginTime() != null) {
            sql.VALUES("login_time", "#{loginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginLatitude() != null) {
            sql.VALUES("login_latitude", "#{loginLatitude,jdbcType=DOUBLE}");
        }
        
        if (record.getLoginLongitude() != null) {
            sql.VALUES("login_longitude", "#{loginLongitude,jdbcType=DOUBLE}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserLogin record) {
        SQL sql = new SQL();
        sql.UPDATE("user_login");
        
        if (record.getUserLoginId() != null) {
            sql.SET("user_login_id = #{userLoginId,jdbcType=VARCHAR}");
        }
        
        if (record.getAccount() != null) {
            sql.SET("account = #{account,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountType() != null) {
            sql.SET("account_type = #{accountType,jdbcType=CHAR}");
        }
        
        if (record.getLoginIp() != null) {
            sql.SET("login_ip = #{loginIp,jdbcType=VARCHAR}");
        }
        
        if (record.getClientType() != null) {
            sql.SET("client_type = #{clientType,jdbcType=VARCHAR}");
        }
        
        if (record.getLoginTime() != null) {
            sql.SET("login_time = #{loginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginLatitude() != null) {
            sql.SET("login_latitude = #{loginLatitude,jdbcType=DOUBLE}");
        }
        
        if (record.getLoginLongitude() != null) {
            sql.SET("login_longitude = #{loginLongitude,jdbcType=DOUBLE}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}