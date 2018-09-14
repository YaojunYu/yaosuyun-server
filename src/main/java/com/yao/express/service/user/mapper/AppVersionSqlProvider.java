package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.AppVersion;
import org.apache.ibatis.jdbc.SQL;

public class AppVersionSqlProvider {

    public String insertSelective(AppVersion record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("app_version");
        
        if (record.getPkg() != null) {
            sql.VALUES("pkg", "#{pkg,jdbcType=VARCHAR}");
        }
        
        if (record.getVersion() != null) {
            sql.VALUES("version", "#{version,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getIsForce() != null) {
            sql.VALUES("is_force", "#{isForce,jdbcType=TINYINT}");
        }
        
        if (record.getUrl() != null) {
            sql.VALUES("url", "#{url,jdbcType=VARCHAR}");
        }
        
        if (record.getLog() != null) {
            sql.VALUES("log", "#{log,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(AppVersion record) {
        SQL sql = new SQL();
        sql.UPDATE("app_version");
        
        if (record.getPkg() != null) {
            sql.SET("pkg = #{pkg,jdbcType=VARCHAR}");
        }
        
        if (record.getVersion() != null) {
            sql.SET("version = #{version,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getIsForce() != null) {
            sql.SET("is_force = #{isForce,jdbcType=TINYINT}");
        }
        
        if (record.getUrl() != null) {
            sql.SET("url = #{url,jdbcType=VARCHAR}");
        }
        
        if (record.getLog() != null) {
            sql.SET("log = #{log,jdbcType=VARCHAR}");
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
        return new ListQuerySqlBuilder().build("app_version", queryOption).toString();
    }
}