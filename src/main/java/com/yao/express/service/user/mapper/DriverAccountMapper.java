package com.yao.express.service.user.mapper;

import com.yao.express.service.user.entity.DriverAccount;
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

public interface DriverAccountMapper {
    @Delete({
        "delete from driver_account",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into driver_account (account, password, ",
        "driver_id, status, last_login, ",
        "wx_no, create_time, ",
        "update_time)",
        "values (#{account,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{driverId,jdbcType=VARCHAR}, #{status,jdbcType=CHAR}, #{lastLogin,jdbcType=TIMESTAMP}, ",
        "#{wxNo,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(DriverAccount record);

    @InsertProvider(type=DriverAccountSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(DriverAccount record);

    @Select({
        "select",
        "id, account, password, driver_id, status, last_login, wx_no, create_time, update_time",
        "from driver_account",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="driver_id", property="driverId", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="last_login", property="lastLogin", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="wx_no", property="wxNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    DriverAccount selectByPrimaryKey(Long id);

    @UpdateProvider(type=DriverAccountSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DriverAccount record);

    @Update({
        "update driver_account",
        "set account = #{account,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "driver_id = #{driverId,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=CHAR},",
          "last_login = #{lastLogin,jdbcType=TIMESTAMP},",
          "wx_no = #{wxNo,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DriverAccount record);

    // add by york
    @Select({
            "select",
            "id, account, password, driver_id, status, last_login, wx_no, create_time, update_time",
            "from driver_account",
            "where account = #{account,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="driver_id", property="driverId", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
            @Result(column="last_login", property="lastLogin", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="wx_no", property="wxNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    DriverAccount selectByAccount(String account);
}