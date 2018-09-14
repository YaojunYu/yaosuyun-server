package com.yao.express.service.user.mapper;

import com.yao.express.service.user.entity.Heartbeat;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface HeartbeatMapper {
    @Delete({
        "delete from heartbeat",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into heartbeat (who_id, who_type, ",
        "available, msg, latitude, ",
        "longitude, heart_time, ",
        "version, create_time, ",
        "update_time)",
        "values (#{whoId,jdbcType=VARCHAR}, #{whoType,jdbcType=VARCHAR}, ",
        "#{available,jdbcType=BIT}, #{msg,jdbcType=VARCHAR}, #{latitude,jdbcType=DOUBLE}, ",
        "#{longitude,jdbcType=DOUBLE}, #{heartTime,jdbcType=TIMESTAMP}, ",
        "#{version,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Heartbeat record);

    @InsertProvider(type=HeartbeatSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Heartbeat record);

    @Select({
        "select",
        "id, who_id, who_type, available, msg, latitude, longitude, heart_time, version, ",
        "create_time, update_time",
        "from heartbeat",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="who_id", property="whoId", jdbcType=JdbcType.VARCHAR),
        @Result(column="who_type", property="whoType", jdbcType=JdbcType.VARCHAR),
        @Result(column="available", property="available", jdbcType=JdbcType.BIT),
        @Result(column="msg", property="msg", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="heart_time", property="heartTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="version", property="version", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Heartbeat selectByPrimaryKey(Long id);

    @UpdateProvider(type=HeartbeatSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Heartbeat record);

    @Update({
        "update heartbeat",
        "set who_id = #{whoId,jdbcType=VARCHAR},",
          "who_type = #{whoType,jdbcType=VARCHAR},",
          "available = #{available,jdbcType=BIT},",
          "msg = #{msg,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=DOUBLE},",
          "longitude = #{longitude,jdbcType=DOUBLE},",
          "heart_time = #{heartTime,jdbcType=TIMESTAMP},",
          "version = #{version,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Heartbeat record);

    // add
    @Select({
            "select",
            "id, who_id, who_type, available, msg, latitude, longitude, heart_time, version, ",
            "create_time, update_time",
            "from heartbeat",
            "where who_id = #{whoId,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="who_id", property="whoId", jdbcType=JdbcType.VARCHAR),
            @Result(column="who_type", property="whoType", jdbcType=JdbcType.VARCHAR),
            @Result(column="available", property="available", jdbcType=JdbcType.BIT),
            @Result(column="msg", property="msg", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="heart_time", property="heartTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="version", property="version", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Heartbeat selectByWhoId(String whoId);

    @UpdateProvider(type=HeartbeatSqlProvider.class, method="updateByWhoIdSelective")
    int updateByWhoIdSelective(Heartbeat record);

    // 并发下写司机可分配状态
    @UpdateProvider(type=HeartbeatSqlProvider.class, method="updateByWhoIdAndVersionSelective")
    int updateByWhoIdAndVersionSelective(Heartbeat record);
}