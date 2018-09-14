package com.yao.express.service.user.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * RedisTemplate 配置类
 * 配置spring-data-redis序列化策略
 * 配置redis缓存支持
 *
 * @author: York.Yu
 * @date: 2017/5/12
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    private static final String DRIVER_CACHE_KEY = "drivers_locations";

    @Value("${driver.heartbeat.interval.time}")
    private Long DRIVER_HEARTBEAT_INTERVAL;
    @Value("${session.expire.time}")
    private Long SESSION_EXPIRE_TIME;
    @Value("${redis.default.expire.time}")
    private Long DEFAULT_REDIS_EXPIRE_TIME;

    /**
     * 缓存管理器
     */
    @Bean(name = "cacheManager")
    @Primary
    public CacheManager redisCacheManager(RedisConnectionFactory factory) {

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMillis(DEFAULT_REDIS_EXPIRE_TIME))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        RedisCacheConfiguration heartbeatCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMillis(DRIVER_HEARTBEAT_INTERVAL))
                .prefixKeysWith("heartbeat")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        RedisCacheConfiguration driverLocsCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMillis(DRIVER_HEARTBEAT_INTERVAL))
                .disableKeyPrefix()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        RedisCacheConfiguration sessionCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMillis(SESSION_EXPIRE_TIME))
                .prefixKeysWith("session")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        cacheConfigurationMap.put("heartbeat", heartbeatCacheConfig);
        cacheConfigurationMap.put(DRIVER_CACHE_KEY, driverLocsCacheConfig);
        cacheConfigurationMap.put("session", sessionCacheConfig);

        RedisCacheManager cacheManager =
                RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(factory)
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .cacheDefaults(defaultCacheConfig)
                .build();

        return cacheManager;
    }

    /**
     * 自定义缓存键名生成策略
     */
    @Bean(name = "keyGenerator")
    public KeyGenerator customKeyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

//    @Bean
//    public RedisTemplate<?, String> stringRedisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(new StringRedisSerializer());
//        template.setValueSerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//
//        template.setConnectionFactory(factory);
//        return template;
//    }

    /**
     * 实例化 RedisTemplate 对象
     */
    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置数据存入 redis 的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     */
    @Bean
    public HashOperations<?, ?, ?> hashOperations(
            RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 实例化 ValueOperations 对象,可以使用 String 操作
     */
    @Bean
    public ValueOperations<?, ?> valueOperations(
            RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 实例化 ListOperations 对象,可以使用 List 操作
     */
    @Bean
    public ListOperations<?, ?> listOperations(
            RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 实例化 SetOperations 对象,可以使用 Set 操作
     */
    @Bean
    public SetOperations<?, ?> setOperations(
            RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
     */
    @Bean
    public ZSetOperations<?, ?> zSetOperations(
            RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

}
