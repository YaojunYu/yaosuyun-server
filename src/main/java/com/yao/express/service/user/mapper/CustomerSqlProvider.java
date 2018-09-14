package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Customer;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class CustomerSqlProvider {

    public String insertSelective(Customer record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("customer");
        
        if (record.getCustomerId() != null) {
            sql.VALUES("customer_id", "#{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=CHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getPhoto() != null) {
            sql.VALUES("photo", "#{photo,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            sql.VALUES("latitude", "#{latitude,jdbcType=DOUBLE}");
        }
        
        if (record.getLongitude() != null) {
            sql.VALUES("longitude", "#{longitude,jdbcType=DOUBLE}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Customer record) {
        SQL sql = new SQL();
        sql.UPDATE("customer");
        
        if (record.getCustomerId() != null) {
            sql.SET("customer_id = #{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.SET("sex = #{sex,jdbcType=CHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getPhoto() != null) {
            sql.SET("photo = #{photo,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            sql.SET("latitude = #{latitude,jdbcType=DOUBLE}");
        }
        
        if (record.getLongitude() != null) {
            sql.SET("longitude = #{longitude,jdbcType=DOUBLE}");
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

    public String updateByCustomerIdSelective(Customer record) {
        SQL sql = new SQL();
        sql.UPDATE("customer");

        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }

        if (record.getSex() != null) {
            sql.SET("sex = #{sex,jdbcType=CHAR}");
        }

        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }

        if (record.getPhoto() != null) {
            sql.SET("photo = #{photo,jdbcType=VARCHAR}");
        }

        if (record.getLatitude() != null) {
            sql.SET("latitude = #{latitude,jdbcType=DOUBLE}");
        }

        if (record.getLongitude() != null) {
            sql.SET("longitude = #{longitude,jdbcType=DOUBLE}");
        }

        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }

        sql.WHERE("customer_id = #{customerId,jdbcType=VARCHAR}");

        return sql.toString();
    }

    // add by york
    public String selectList(ListQueryOption queryOption) {
        return new ListQuerySqlBuilder().build("customer", queryOption).toString();
    }
}