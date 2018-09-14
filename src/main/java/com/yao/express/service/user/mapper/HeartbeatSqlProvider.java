package com.yao.express.service.user.mapper;

import com.yao.express.service.user.entity.Heartbeat;
import org.apache.ibatis.jdbc.SQL;

public class HeartbeatSqlProvider {

    public String insertSelective(Heartbeat record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("heartbeat");
        
        if (record.getWhoId() != null) {
            sql.VALUES("who_id", "#{whoId,jdbcType=VARCHAR}");
        }
        
        if (record.getWhoType() != null) {
            sql.VALUES("who_type", "#{whoType,jdbcType=VARCHAR}");
        }
        
        if (record.getAvailable() != null) {
            sql.VALUES("available", "#{available,jdbcType=BIT}");
        }
        
        if (record.getMsg() != null) {
            sql.VALUES("msg", "#{msg,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            sql.VALUES("latitude", "#{latitude,jdbcType=DOUBLE}");
        }
        
        if (record.getLongitude() != null) {
            sql.VALUES("longitude", "#{longitude,jdbcType=DOUBLE}");
        }
        
        if (record.getHeartTime() != null) {
            sql.VALUES("heart_time", "#{heartTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVersion() != null) {
            sql.VALUES("version", "#{version,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Heartbeat record) {
        SQL sql = new SQL();
        sql.UPDATE("heartbeat");
        
        if (record.getWhoId() != null) {
            sql.SET("who_id = #{whoId,jdbcType=VARCHAR}");
        }
        
        if (record.getWhoType() != null) {
            sql.SET("who_type = #{whoType,jdbcType=VARCHAR}");
        }
        
        if (record.getAvailable() != null) {
            sql.SET("available = #{available,jdbcType=BIT}");
        }
        
        if (record.getMsg() != null) {
            sql.SET("msg = #{msg,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            sql.SET("latitude = #{latitude,jdbcType=DOUBLE}");
        }
        
        if (record.getLongitude() != null) {
            sql.SET("longitude = #{longitude,jdbcType=DOUBLE}");
        }
        
        if (record.getHeartTime() != null) {
            sql.SET("heart_time = #{heartTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVersion() != null) {
            sql.SET("version = #{version,jdbcType=BIGINT}");
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

    // 根据whoId更新msg
    public String updateByWhoIdSelective(Heartbeat record) {
        SQL sql = new SQL();
        sql.UPDATE("heartbeat");

        if (record.getWhoType() != null) {
            sql.SET("who_type = #{whoType,jdbcType=VARCHAR}");
        }

        if (record.getAvailable() != null) {
            sql.SET("available = #{available,jdbcType=BIT}");
        }

        if (record.getMsg() != null) {
            sql.SET("msg = #{msg,jdbcType=VARCHAR}");
        }

        if (record.getLatitude() != null) {
            sql.SET("latitude = #{latitude,jdbcType=DOUBLE}");
        }

        if (record.getLongitude() != null) {
            sql.SET("longitude = #{longitude,jdbcType=DOUBLE}");
        }

        if (record.getHeartTime() != null) {
            sql.SET("heart_time = #{heartTime,jdbcType=TIMESTAMP}");
        }

        if (record.getVersion() != null) {
            sql.SET("version = #{version,jdbcType=BIGINT}");
        }

        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }

        sql.WHERE("who_id = #{whoId,jdbcType=VARCHAR}");

        return sql.toString();
    }

    // 乐观锁
    public String updateByWhoIdAndVersionSelective(Heartbeat record) {
        SQL sql = new SQL();
        sql.UPDATE("heartbeat");

        if (record.getWhoType() != null) {
            sql.SET("who_type = #{whoType,jdbcType=VARCHAR}");
        }

        if (record.getAvailable() != null) {
            sql.SET("available = #{available,jdbcType=BIT}");
        }

        if (record.getMsg() != null) {
            sql.SET("msg = #{msg,jdbcType=VARCHAR}");
        }

        if (record.getLatitude() != null) {
            sql.SET("latitude = #{latitude,jdbcType=DOUBLE}");
        }

        if (record.getLongitude() != null) {
            sql.SET("longitude = #{longitude,jdbcType=DOUBLE}");
        }

        if (record.getHeartTime() != null) {
            sql.SET("heart_time = #{heartTime,jdbcType=TIMESTAMP}");
        }

        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }

        sql.SET("version = #{version,jdbcType=BIGINT} + 1");

        sql.WHERE("who_id = #{whoId,jdbcType=VARCHAR}", "version = #{version,jdbcType=BIGINT}");

        return sql.toString();
    }
}