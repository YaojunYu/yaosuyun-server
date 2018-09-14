package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Feedback;
import org.apache.ibatis.jdbc.SQL;

public class FeedbackSqlProvider {

    public String insertSelective(Feedback record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("feedback");
        
        if (record.getApp() != null) {
            sql.VALUES("app", "#{app,jdbcType=VARCHAR}");
        }
        
        if (record.getUser() != null) {
            sql.VALUES("user", "#{user,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDescption() != null) {
            sql.VALUES("descption", "#{descption,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Feedback record) {
        SQL sql = new SQL();
        sql.UPDATE("feedback");
        
        if (record.getApp() != null) {
            sql.SET("app = #{app,jdbcType=VARCHAR}");
        }
        
        if (record.getUser() != null) {
            sql.SET("user = #{user,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDescption() != null) {
            sql.SET("descption = #{descption,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    // add by york
    public String selectList(ListQueryOption queryOption) {
        return new ListQuerySqlBuilder().build("feedback", queryOption).toString();
    }
}