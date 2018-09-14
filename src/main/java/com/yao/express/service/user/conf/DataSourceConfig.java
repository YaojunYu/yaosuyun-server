package com.yao.express.service.user.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据源配置类
 *
 * @author: York.Yu
 * @date: 2017/5/11
 */
@Configuration
public class DataSourceConfig {

    @Primary //默认数据源
    @Bean(name = "primaryDataSource", destroyMethod = "close")
    public DruidDataSource Construction(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String dbUserName,
            @Value("${spring.datasource.password}") String dbPassword,
            @Value("${spring.datasource.driver-class-name}") String dbDriver,
            @Value("${spring.datasource.maxActive}") Integer maxActive,
            @Value("${spring.datasource.initialSize}") Integer initialSize,
            @Value("${spring.datasource.minIdle}") Integer minIdle,
            @Value("${spring.datasource.maxWait}") Long maxWait,
            @Value("${spring.datasource.timeBetweenEvictionRunsMillis}") Long timeBetweenEvictionRunsMillis,
            @Value("${spring.datasource.minEvictableIdleTimeMillis}") Long minEvictableIdleTimeMillis,
            @Value("${spring.datasource.validationQuery}") String validationQuery,
            @Value("${spring.datasource.testWhileIdle}") Boolean testWhileIdle,
            @Value("${spring.datasource.testOnBorrow}") Boolean testOnBorrow,
            @Value("${spring.datasource.testOnReturn}") Boolean testOnReturn,
            @Value("${spring.datasource.poolPreparedStatements}") Boolean poolPreparedStatements,
            @Value("${spring.datasource.maxOpenPreparedStatements}") Integer maxOpenPreparedStatements,
            @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}") Integer maxPoolPreparedStatementPerConnectionSize,
            @Value("${spring.datasource.filters}") String filters,
            @Value("${spring.datasource.connectionProperties}") String connectionProperties) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        //配置数据连接的参数
        dataSource.setUrl(url);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName(dbDriver);

        //配置最大连接
        dataSource.setMaxActive(maxActive);
        //配置初始连接
        dataSource.setInitialSize(initialSize);
        //配置最小连接
        dataSource.setMinIdle(minIdle);
        //连接等待超时时间
        dataSource.setMaxWait(maxWait);
        //间隔多久进行检测,关闭空闲连接
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //一个连接最小生存时间
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //用来检测是否有效的sql
        dataSource.setValidationQuery(validationQuery);//"select 'x'"
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        //打开PSCache,并指定每个连接的PSCache大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        // 数据源额外属性
        Properties properties = new Properties();
        for(String pro : connectionProperties.split(";", 0)) {
            String[] els = pro.trim().split("=", 0);
            properties.setProperty(els[0].trim(), els[1].trim());
        }
        dataSource.setConnectProperties(properties);
        //配置sql监控的filter
        dataSource.setFilters(filters);
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException("druid datasource init fail");
        }
        return dataSource;
    }
}
