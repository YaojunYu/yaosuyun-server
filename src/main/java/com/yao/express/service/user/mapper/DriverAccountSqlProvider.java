package com.yao.express.service.user.mapper;

import com.yao.express.service.user.entity.DriverAccount;
import org.apache.ibatis.jdbc.SQL;

public class DriverAccountSqlProvider {

    public String insertSelective(DriverAccount record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("driver_account");
        
        if (record.getAccount() != null) {
            sql.VALUES("account", "#{account,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getDriverId() != null) {
            sql.VALUES("driver_id", "#{driverId,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=CHAR}");
        }
        
        if (record.getLastLogin() != null) {
            sql.VALUES("last_login", "#{lastLogin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getWxNo() != null) {
            sql.VALUES("wx_no", "#{wxNo,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DriverAccount record) {
        SQL sql = new SQL();
        sql.UPDATE("driver_account");
        
        if (record.getAccount() != null) {
            sql.SET("account = #{account,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getDriverId() != null) {
            sql.SET("driver_id = #{driverId,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=CHAR}");
        }
        
        if (record.getLastLogin() != null) {
            sql.SET("last_login = #{lastLogin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getWxNo() != null) {
            sql.SET("wx_no = #{wxNo,jdbcType=VARCHAR}");
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