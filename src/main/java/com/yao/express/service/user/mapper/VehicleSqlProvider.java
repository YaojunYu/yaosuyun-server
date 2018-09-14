package com.yao.express.service.user.mapper;

import com.yao.express.service.user.entity.Vehicle;
import org.apache.ibatis.jdbc.SQL;

public class VehicleSqlProvider {

    public String insertSelective(Vehicle record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("vehicle");
        
        if (record.getVehicleId() != null) {
            sql.VALUES("vehicle_id", "#{vehicleId,jdbcType=VARCHAR}");
        }
        
        if (record.getLicenseNo() != null) {
            sql.VALUES("license_no", "#{licenseNo,jdbcType=VARCHAR}");
        }
        
        if (record.getColor() != null) {
            sql.VALUES("color", "#{color,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=CHAR}");
        }
        
        if (record.getBrand() != null) {
            sql.VALUES("brand", "#{brand,jdbcType=VARCHAR}");
        }
        
        if (record.getMaxCapacity() != null) {
            sql.VALUES("max_capacity", "#{maxCapacity,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Vehicle record) {
        SQL sql = new SQL();
        sql.UPDATE("vehicle");
        
        if (record.getVehicleId() != null) {
            sql.SET("vehicle_id = #{vehicleId,jdbcType=VARCHAR}");
        }
        
        if (record.getLicenseNo() != null) {
            sql.SET("license_no = #{licenseNo,jdbcType=VARCHAR}");
        }
        
        if (record.getColor() != null) {
            sql.SET("color = #{color,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=CHAR}");
        }
        
        if (record.getBrand() != null) {
            sql.SET("brand = #{brand,jdbcType=VARCHAR}");
        }
        
        if (record.getMaxCapacity() != null) {
            sql.SET("max_capacity = #{maxCapacity,jdbcType=INTEGER}");
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