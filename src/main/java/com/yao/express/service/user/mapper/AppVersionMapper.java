package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.AppUpdateLog;
import com.yao.express.service.user.dto.AppVersionCheckResult;
import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.AppVersion;
import com.yao.express.service.user.entity.Feedback;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AppVersionMapper {
    @Delete({
        "delete from app_version",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into app_version (pkg, version, ",
        "type, name, is_force, ",
        "url, log, create_time, ",
        "update_time)",
        "values (#{pkg,jdbcType=VARCHAR}, #{version,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{isForce,jdbcType=TINYINT}, ",
        "#{url,jdbcType=VARCHAR}, #{log,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(AppVersion record);

    @InsertProvider(type=AppVersionSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(AppVersion record);

    @Select({
        "select",
        "id, pkg, version, type, name, is_force, url, log, create_time, update_time",
        "from app_version",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="pkg", property="pkg", jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_force", property="isForce", jdbcType=JdbcType.TINYINT),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="log", property="log", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    AppVersion selectByPrimaryKey(Long id);

    @UpdateProvider(type=AppVersionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AppVersion record);

    @Update({
        "update app_version",
        "set pkg = #{pkg,jdbcType=VARCHAR},",
          "version = #{version,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "is_force = #{isForce,jdbcType=TINYINT},",
          "url = #{url,jdbcType=VARCHAR},",
          "log = #{log,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(AppVersion record);

    // add by york
    @Select({
            "select",
            "id, pkg, version, type, name, is_force, url, log",
            "from app_version",
            "where pkg = #{pkg,jdbcType=VARCHAR} and type = #{type,jdbcType=VARCHAR}",
            "order by id desc",
            "limit 1"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="pkg", property="pkg", jdbcType=JdbcType.VARCHAR),
            @Result(column="version", property="version", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="is_force", property="isForce", jdbcType=JdbcType.TINYINT),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="log", property="log", jdbcType=JdbcType.VARCHAR)
    })
    AppVersionCheckResult selectLatestAppVersion(String pkg, String type);

    @Select({
            "select",
            "pkg, version, type, log",
            "from app_version",
            "where pkg = #{pkg,jdbcType=VARCHAR} and type = #{type,jdbcType=VARCHAR}",
            "order by id desc"
    })
    @Results({
            @Result(column="pkg", property="pkg", jdbcType=JdbcType.VARCHAR),
            @Result(column="version", property="version", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
            @Result(column="log", property="log", jdbcType=JdbcType.VARCHAR)
    })
    List<AppUpdateLog> selectAppVersionLogs(String pkg, String type);

    @SelectProvider(type=AppVersionSqlProvider.class, method="selectList")
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="pkg", property="pkg", jdbcType=JdbcType.VARCHAR),
            @Result(column="version", property="version", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="is_force", property="isForce", jdbcType=JdbcType.TINYINT),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="log", property="log", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<AppVersion> selectList(ListQueryOption queryOption);
}