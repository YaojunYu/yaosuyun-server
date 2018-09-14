package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Driver;
import org.apache.ibatis.jdbc.SQL;

public class DriverSqlProvider {

    public String insertSelective(Driver record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("driver");
        
        if (record.getDriverId() != null) {
            sql.VALUES("driver_id", "#{driverId,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=CHAR}");
        }
        
        if (record.getPhoto() != null) {
            sql.VALUES("photo", "#{photo,jdbcType=VARCHAR}");
        }
        
        if (record.getIdNo() != null) {
            sql.VALUES("id_No", "#{idNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getDrivingLicence() != null) {
            sql.VALUES("driving_licence", "#{drivingLicence,jdbcType=VARCHAR}");
        }
        
        if (record.getVehicleId() != null) {
            sql.VALUES("vehicle_id", "#{vehicleId,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Driver record) {
        SQL sql = new SQL();
        sql.UPDATE("driver");
        
        if (record.getDriverId() != null) {
            sql.SET("driver_id = #{driverId,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.SET("sex = #{sex,jdbcType=CHAR}");
        }
        
        if (record.getPhoto() != null) {
            sql.SET("photo = #{photo,jdbcType=VARCHAR}");
        }
        
        if (record.getIdNo() != null) {
            sql.SET("id_No = #{idNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getDrivingLicence() != null) {
            sql.SET("driving_licence = #{drivingLicence,jdbcType=VARCHAR}");
        }
        
        if (record.getVehicleId() != null) {
            sql.SET("vehicle_id = #{vehicleId,jdbcType=VARCHAR}");
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

    // add by york
    public String selectList(ListQueryOption queryOption) {
        return new ListQuerySqlBuilder().build("driver", queryOption).toString();
    }
}