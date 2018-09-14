package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Driver;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface DriverMapper {
    @Delete({
        "delete from driver",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into driver (driver_id, name, ",
        "sex, photo, id_No, ",
        "phone, driving_licence, ",
        "vehicle_id, latitude, ",
        "longitude, create_time, ",
        "update_time)",
        "values (#{driverId,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{sex,jdbcType=CHAR}, #{photo,jdbcType=VARCHAR}, #{idNo,jdbcType=VARCHAR}, ",
        "#{phone,jdbcType=VARCHAR}, #{drivingLicence,jdbcType=VARCHAR}, ",
        "#{vehicleId,jdbcType=VARCHAR}, #{latitude,jdbcType=DOUBLE}, ",
        "#{longitude,jdbcType=DOUBLE}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Driver record);

    @InsertProvider(type=DriverSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Driver record);

    @Select({
        "select",
        "id, driver_id, name, sex, photo, id_No, phone, driving_licence, vehicle_id, ",
        "latitude, longitude, create_time, update_time",
        "from driver",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="driver_id", property="driverId", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
        @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_No", property="idNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="driving_licence", property="drivingLicence", jdbcType=JdbcType.VARCHAR),
        @Result(column="vehicle_id", property="vehicleId", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Driver selectByPrimaryKey(Long id);

    @UpdateProvider(type=DriverSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Driver record);

    @Update({
        "update driver",
        "set driver_id = #{driverId,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "sex = #{sex,jdbcType=CHAR},",
          "photo = #{photo,jdbcType=VARCHAR},",
          "id_No = #{idNo,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "driving_licence = #{drivingLicence,jdbcType=VARCHAR},",
          "vehicle_id = #{vehicleId,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=DOUBLE},",
          "longitude = #{longitude,jdbcType=DOUBLE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Driver record);

    // 根据driverId查询司机
    @Select({
            "select",
            "id, driver_id, name, sex, photo, id_No, phone, driving_licence, vehicle_id, ",
            "latitude, longitude, create_time, update_time",
            "from driver",
            "where driver_id = #{driverId,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="driver_id", property="driverId", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
            @Result(column="id_No", property="idNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="driving_licence", property="drivingLicence", jdbcType=JdbcType.VARCHAR),
            @Result(column="vehicle_id", property="vehicleId", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Driver selectByDriverId(String driverId);

    // add by york
    @SelectProvider(type=DriverSqlProvider.class, method="selectList")
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="driver_id", property="driverId", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
            @Result(column="id_No", property="idNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="driving_licence", property="drivingLicence", jdbcType=JdbcType.VARCHAR),
            @Result(column="vehicle_id", property="vehicleId", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Driver> selectList(ListQueryOption queryOption);

}