package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Customer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CustomerMapper {
    @Delete({
        "delete from customer",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into customer (customer_id, name, ",
        "sex, phone, photo, ",
        "latitude, longitude, ",
        "create_time, update_time)",
        "values (#{customerId,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{sex,jdbcType=CHAR}, #{phone,jdbcType=VARCHAR}, #{photo,jdbcType=VARCHAR}, ",
        "#{latitude,jdbcType=DOUBLE}, #{longitude,jdbcType=DOUBLE}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Customer record);

    @InsertProvider(type=CustomerSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Customer record);

    @Select({
        "select",
        "id, customer_id, name, sex, phone, photo, latitude, longitude, create_time, ",
        "update_time",
        "from customer",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Customer selectByPrimaryKey(Long id);

    @UpdateProvider(type=CustomerSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Customer record);

    @Update({
        "update customer",
        "set customer_id = #{customerId,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "sex = #{sex,jdbcType=CHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "photo = #{photo,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=DOUBLE},",
          "longitude = #{longitude,jdbcType=DOUBLE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Customer record);


    @Select({
            "select",
            "id, customer_id, name, sex, phone, photo, latitude, longitude, create_time, ",
            "update_time",
            "from customer",
            "where customer_id = #{customerId,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="customer_id", property="customerId", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Customer selectByCustomerId(String customerId);


    @UpdateProvider(type=CustomerSqlProvider.class, method="updateByCustomerIdSelective")
    int updateByCustomerIdSelective(Customer record);

    @SelectProvider(type=CustomerSqlProvider.class, method="selectList")
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="customer_id", property="customerId", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Customer> selectList(ListQueryOption queryOption);
}