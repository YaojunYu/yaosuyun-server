package com.yao.express.service.user.mapper;

import com.yao.express.service.user.entity.Vehicle;
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

public interface VehicleMapper {
    @Delete({
        "delete from vehicle",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into vehicle (vehicle_id, license_no, ",
        "color, type, brand, ",
        "max_capacity, create_time, ",
        "update_time)",
        "values (#{vehicleId,jdbcType=VARCHAR}, #{licenseNo,jdbcType=VARCHAR}, ",
        "#{color,jdbcType=VARCHAR}, #{type,jdbcType=CHAR}, #{brand,jdbcType=VARCHAR}, ",
        "#{maxCapacity,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Vehicle record);

    @InsertProvider(type=VehicleSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Vehicle record);

    @Select({
        "select",
        "id, vehicle_id, license_no, color, type, brand, max_capacity, create_time, update_time",
        "from vehicle",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="vehicle_id", property="vehicleId", jdbcType=JdbcType.VARCHAR),
        @Result(column="license_no", property="licenseNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="color", property="color", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.CHAR),
        @Result(column="brand", property="brand", jdbcType=JdbcType.VARCHAR),
        @Result(column="max_capacity", property="maxCapacity", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Vehicle selectByPrimaryKey(Long id);

    @UpdateProvider(type=VehicleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Vehicle record);

    @Update({
        "update vehicle",
        "set vehicle_id = #{vehicleId,jdbcType=VARCHAR},",
          "license_no = #{licenseNo,jdbcType=VARCHAR},",
          "color = #{color,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=CHAR},",
          "brand = #{brand,jdbcType=VARCHAR},",
          "max_capacity = #{maxCapacity,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Vehicle record);


    @Select({
            "select",
            "id, vehicle_id, license_no, color, type, brand, max_capacity, create_time, update_time",
            "from vehicle",
            "where vehicle_id = #{vehicleId,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="vehicle_id", property="vehicleId", jdbcType=JdbcType.VARCHAR),
            @Result(column="license_no", property="licenseNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="color", property="color", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.CHAR),
            @Result(column="brand", property="brand", jdbcType=JdbcType.VARCHAR),
            @Result(column="max_capacity", property="maxCapacity", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Vehicle selectByVehicleId(String vehicleId);
}