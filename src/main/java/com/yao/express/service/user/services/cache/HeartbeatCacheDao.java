package com.yao.express.service.user.services.cache;

import com.alibaba.fastjson.JSON;
import com.yao.express.service.user.entity.Heartbeat;
import com.yao.express.service.user.enums.WhoTypeEnum;
import com.yao.express.service.user.mapper.HeartbeatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class HeartbeatCacheDao {

    private static Logger logger = LoggerFactory.getLogger(HeartbeatCacheDao.class);

    private static final String DRIVER_CACHE_KEY = "drivers_locations";

    @Value("${driver.heartbeat.interval.time}")
    private Long DRIVER_HEARTBEAT_INTERVAL;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private HeartbeatMapper heartbeatMapper;

    @CachePut(value = "heartbeat", key = "#record.whoId", unless = "#result == null")
    public Heartbeat insert(Heartbeat record) {
        if (0 < heartbeatMapper.insert(record)) {
            cacheAvailableDriverLocation(record);
            return record;
        }
        return null;
    }

    /**
     * 缓存可分配司机位置
     * @param record
     * @return
     */
    private long cacheAvailableDriverLocation(Heartbeat record) {
        // 缓存司机位置
        if (WhoTypeEnum.DRIVER.value.equals(record.getWhoType()) &&
                null != record.getLatitude() && null != record.getLongitude()) {
            // 定时过时
            redisTemplate.expire(DRIVER_CACHE_KEY, DRIVER_HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
            if (record.getAvailable()) {
                // add
                Point point = new Point(record.getLongitude(), record.getLatitude());
                GeoLocation<String> geoLocation = new GeoLocation<>(record.getWhoId(), point);
                return redisTemplate.opsForGeo().add(DRIVER_CACHE_KEY, geoLocation);
            } else {
                // remove
                return redisTemplate.opsForGeo().remove(DRIVER_CACHE_KEY, record.getWhoId());
            }
        }
        return 0L;
    }

    @Cacheable(value = "heartbeat", key = "#p0", unless = "#result == null")
    public Heartbeat selectByWhoId(String whoId) {
        return heartbeatMapper.selectByWhoId(whoId);
    }

    @CachePut(value = "heartbeat", key = "#record.whoId", unless = "#result == null")
    public Heartbeat updateByWhoIdSelective(Heartbeat record) {
        if (0 < heartbeatMapper.updateByWhoIdSelective(record)) {
            cacheAvailableDriverLocation(record);
            return heartbeatMapper.selectByWhoId(record.getWhoId());
        }
        return null;
    }

    @CachePut(value = "heartbeat", key = "#record.whoId", unless = "#result == null")
    public Heartbeat updateByWhoIdAndVersionSelective(Heartbeat record) {
        Heartbeat heartbeat = heartbeatMapper.selectByWhoId(record.getWhoId());
        if (null != heartbeat) {
            record.setVersion(heartbeat.getVersion());
            if (0 < heartbeatMapper.updateByWhoIdAndVersionSelective(record)) {
                heartbeat = heartbeatMapper.selectByWhoId(record.getWhoId());
                cacheAvailableDriverLocation(record);
                return heartbeat;
            }
        }

        return null;
    }

    /**
     * 获取附近radius公里内的count个司机位置
     * @param lati
     * @param longi
     * @param radius
     * @param count
     * @return
     */
    public List<GeoResult<GeoLocation<String>>> nearestDrivers(double lati, double longi, String member, double radius, Long count) {
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        Circle within = new Circle(new Point(longi, lati), distance);
        GeoRadiusCommandArgs args = GeoRadiusCommandArgs.newGeoRadiusArgs()
                        /*.includeCoordinates().includeDistance()*/.sortAscending();
        if (null != count) {
            args.limit(count);
        }

        GeoResults<GeoLocation<String>> results;
        if (StringUtils.isEmpty(member)) {
            results = redisTemplate.opsForGeo().radius(DRIVER_CACHE_KEY, within, args);
        } else {
            results = redisTemplate.opsForGeo().radius(DRIVER_CACHE_KEY, member, radius);
        }

        logger.info("geo results = {}", JSON.toJSONString(results));
        return results.getContent();
    }

    /**
     * 获取附近司机坐标集合
     * @param lati
     * @param longi
     * @param radius
     * @param count
     * @return
     */
    public Map<String, Double[]> nearestDriverLocs(double lati, double longi, String member, double radius, Long count) {
        Map<String, Double[]> locs = new LinkedHashMap<>();

        List<GeoResult<GeoLocation<String>>> results = nearestDrivers(lati, longi, member, radius, count);
        for (GeoResult<GeoLocation<String>> result : results) {
            String eachmember = result.getContent().getName();
            // 过滤掉自己
            if (!eachmember.equals(member)) {
//                List<Point> points = redisTemplate.opsForGeo().position(DRIVER_CACHE_KEY, eachmember);
//                if (null != points && points.size() > 0) {
//                    Point point = points.get(0);
//                    locs.put(eachmember, new Double[]{point.getY(), point.getX()});
//                }
                // 查询可用司机
                Heartbeat memberHeartbeat = selectByWhoId(eachmember);
                if (checkDriverAvailable(memberHeartbeat)) {
                    locs.put(eachmember, new Double[]{memberHeartbeat.getLatitude(), memberHeartbeat.getLongitude()});
                }
            }
        }

        return locs;
    }

    /**
     * 获取司机可用状态
     * @param driverId
     * @return
     */
    public boolean getDriverAvailable(String driverId) {
        Heartbeat memberHeartbeat = selectByWhoId(driverId);
        if (memberHeartbeat == null) {
            return false;
        }
        return memberHeartbeat.getAvailable();
    }

    /**
     * 计算可用状态，available和heartbeatTime组合
     * @param memberHeartbeat
     * @return
     */
    private boolean checkDriverAvailable(Heartbeat memberHeartbeat) {
        // 超过8s过期，心跳时间5s
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.MILLISECOND, (int) -this.DRIVER_HEARTBEAT_INTERVAL);
        boolean available = (null != memberHeartbeat) ? memberHeartbeat.getAvailable() : false;
        Date heartTime = memberHeartbeat.getHeartTime();
        available = available && heartTime.after(cl.getTime());
        return available;
    }
}
