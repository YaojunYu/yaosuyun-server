package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Driver;
import com.yao.express.service.user.entity.Feedback;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface FeedbackMapper {
    @Delete({
        "delete from feedback",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into feedback (app, user, ",
        "create_time, update_time, ",
        "descption)",
        "values (#{app,jdbcType=VARCHAR}, #{user,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{descption,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Feedback record);

    @InsertProvider(type=FeedbackSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Feedback record);

    @Select({
        "select",
        "id, app, user, create_time, update_time, descption",
        "from feedback",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="app", property="app", jdbcType=JdbcType.VARCHAR),
        @Result(column="user", property="user", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="descption", property="descption", jdbcType=JdbcType.LONGVARCHAR)
    })
    Feedback selectByPrimaryKey(Long id);

    @UpdateProvider(type=FeedbackSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Feedback record);

    @Update({
        "update feedback",
        "set app = #{app,jdbcType=VARCHAR},",
          "user = #{user,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "descption = #{descption,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Feedback record);

    @Update({
        "update feedback",
        "set app = #{app,jdbcType=VARCHAR},",
          "user = #{user,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Feedback record);

    // add by york
    @SelectProvider(type=FeedbackSqlProvider.class, method="selectList")
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="app", property="app", jdbcType=JdbcType.VARCHAR),
            @Result(column="user", property="user", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="descption", property="descption", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Feedback> selectList(ListQueryOption queryOption);
}