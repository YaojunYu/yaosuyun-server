package com.yao.express.service.user.mapper;

import com.yao.express.service.user.entity.UserLogin;
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

public interface UserLoginMapper {
    @Delete({
        "delete from user_login",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_login (user_login_id, account, ",
        "account_type, login_ip, ",
        "client_type, login_time, ",
        "login_latitude, login_longitude, ",
        "create_time, update_time)",
        "values (#{userLoginId,jdbcType=VARCHAR}, #{account,jdbcType=VARCHAR}, ",
        "#{accountType,jdbcType=CHAR}, #{loginIp,jdbcType=VARCHAR}, ",
        "#{clientType,jdbcType=VARCHAR}, #{loginTime,jdbcType=TIMESTAMP}, ",
        "#{loginLatitude,jdbcType=DOUBLE}, #{loginLongitude,jdbcType=DOUBLE}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(UserLogin record);

    @InsertProvider(type=UserLoginSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(UserLogin record);

    @Select({
        "select",
        "id, user_login_id, account, account_type, login_ip, client_type, login_time, ",
        "login_latitude, login_longitude, create_time, update_time",
        "from user_login",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_login_id", property="userLoginId", jdbcType=JdbcType.VARCHAR),
        @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR),
        @Result(column="account_type", property="accountType", jdbcType=JdbcType.CHAR),
        @Result(column="login_ip", property="loginIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="client_type", property="clientType", jdbcType=JdbcType.VARCHAR),
        @Result(column="login_time", property="loginTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="login_latitude", property="loginLatitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="login_longitude", property="loginLongitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserLogin selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserLoginSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserLogin record);

    @Update({
        "update user_login",
        "set user_login_id = #{userLoginId,jdbcType=VARCHAR},",
          "account = #{account,jdbcType=VARCHAR},",
          "account_type = #{accountType,jdbcType=CHAR},",
          "login_ip = #{loginIp,jdbcType=VARCHAR},",
          "client_type = #{clientType,jdbcType=VARCHAR},",
          "login_time = #{loginTime,jdbcType=TIMESTAMP},",
          "login_latitude = #{loginLatitude,jdbcType=DOUBLE},",
          "login_longitude = #{loginLongitude,jdbcType=DOUBLE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserLogin record);
}