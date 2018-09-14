package com.yao.express.service.user.services;

import com.alibaba.fastjson.JSON;
import com.yao.express.service.user.dto.HeartbeatMsg;
import com.yao.express.service.user.dto.HeartbeatRequest;
import com.yao.express.service.user.entity.Heartbeat;
import com.yao.express.service.user.enums.WhoTypeEnum;
import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.services.cache.HeartbeatCacheDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class HeartbeatService {

    private static Logger logger = LoggerFactory.getLogger(HeartbeatService.class);

    @Resource
    private HeartbeatCacheDao heartbeatCacheDao;

    /**
     * 上传心跳
     * @param heartbeatRequest
     * @return
     */
    public AppResponse<HeartbeatMsg> heartbeat(final HeartbeatRequest heartbeatRequest) {
        logger.info("heartbeat: {}", heartbeatRequest);

        if (null == heartbeatRequest) {
            return new AppResponse(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        if (StringUtils.isBlank(heartbeatRequest.getWhoId()) ||
                StringUtils.isBlank(heartbeatRequest.getWhoType())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        boolean whoTypeOk = false;
        for (WhoTypeEnum whoTypeEnum : WhoTypeEnum.values()) {
            if (heartbeatRequest.getWhoType().equals(whoTypeEnum.value))
                whoTypeOk = true;
        }
        if (!whoTypeOk) {
            return new AppResponse<>(ResponseErrorCode.HEART_BEAT_WHO_TYPE_INVALID.code,
                    ResponseErrorCode.HEART_BEAT_WHO_TYPE_INVALID.msg, null);
        }

        // 保存心跳
        Date heartTime = new Date();
        String whoId = heartbeatRequest.getWhoId();
        Heartbeat heartbeat = heartbeatCacheDao.selectByWhoId(whoId);
        String msg = null;
        if (null == heartbeat) {
            // 第一次上传心跳
            Heartbeat hb = new Heartbeat();
            hb.setWhoId(whoId);
            hb.setAvailable(true);
            hb.setWhoType(heartbeatRequest.getWhoType());
            hb.setHeartTime(heartTime);
            hb.setLatitude(heartbeatRequest.getLatitude());
            hb.setLongitude(heartbeatRequest.getLongitude());
            hb.setMsg("");
            hb.setVersion(0L);
            hb.setCreateTime(heartTime);
            hb.setUpdateTime(heartTime);

            heartbeatCacheDao.insert(hb);
        } else {
            // 已存活
            msg = heartbeat.getMsg();
            if (StringUtils.isNotBlank(msg)) {
                // 消息取走后置空
                heartbeat.setMsg("");
            }
            heartbeat.setLatitude(heartbeatRequest.getLatitude());
            heartbeat.setLongitude(heartbeatRequest.getLongitude());
            heartbeat.setHeartTime(heartTime);
            heartbeat.setUpdateTime(heartTime);

            heartbeatCacheDao.updateByWhoIdSelective(heartbeat);
        }

        // 返回消息
        HeartbeatMsg hbMsg = null;
        if (StringUtils.isNotBlank(msg)) {
            hbMsg = JSON.parseObject(msg, HeartbeatMsg.class);
        }

        return  AppResponse.success(hbMsg);
    }

    /**
     * 保存heartbeat的消息
     * @param whoId
     * @param hbMsg
     * @return
     */
    public boolean saveHeartBeatMsg(String whoId, HeartbeatMsg hbMsg) {
        if (null != hbMsg) {
            String msg = JSON.toJSONString(hbMsg);
            Heartbeat hb = new Heartbeat();
            hb.setWhoId(whoId);
            hb.setMsg(msg);

            Heartbeat result = heartbeatCacheDao.updateByWhoIdSelective(hb);

            return result != null;
        }

        return false;
    }

    /**
     * 更新司机接单状态
     * @param whoId
     * @param available
     * @return
     */
    public boolean updateDriverAvailable(String whoId, boolean available) {

        Heartbeat hb = new Heartbeat();
        hb.setWhoId(whoId);
        hb.setAvailable(available);

        Heartbeat result = heartbeatCacheDao.updateByWhoIdAndVersionSelective(hb);

        return result != null;
    }

    /**
     * 从缓存中查询附近miters公里内可分配订单的的司机，按距离升序排序
     * @param latitude 当前中心位置纬度
     * @param longitude 当前中心位置经度
     * @param miters 方圆米数
     * @param count 返回个数
     * @return
     */
    public List<String> getNearestAvailableDrivers(double latitude, double longitude, Integer miters, int count) {
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

//        SortQuery<String> query = SortQueryBuilder.sort("test-user-1").noSort().get("#").get("test-map-*->uid").get("test-map-*->content").build();
//        GeoResults<RedisGeoCommands.GeoLocation<Heartbeat>> results = redisTemplate.opsForGeo().radius("heartbeat:*", new Heartbeat(), 10);
        return null;
    }

    /**
     * 获取某个心跳
     * @param whoId
     * @return
     */
    public Heartbeat getHeartbeat(String whoId) {
        if (StringUtils.isBlank(whoId)) {
            return null;
        }
        return heartbeatCacheDao.selectByWhoId(whoId);
    }

    /**
     * 发送消息
     * @param whoId
     * @param msgType
     * @param content
     * @param tip
     * @return
     */
    public Heartbeat sendHeartbeatMsg(String whoId, String msgType, String content, String tip) {
        // 通知顾客消息
        HeartbeatMsg customerMsg = new HeartbeatMsg();
        customerMsg.setTip(tip);
        customerMsg.setType(msgType);
        customerMsg.setContent(content);

        Heartbeat customerHb = new Heartbeat();
        customerHb.setWhoId(whoId);
        customerHb.setMsg(JSON.toJSONString(customerMsg));

        return heartbeatCacheDao.updateByWhoIdSelective(customerHb);
    }
}
