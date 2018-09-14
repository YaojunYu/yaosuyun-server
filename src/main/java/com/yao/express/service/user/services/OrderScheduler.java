package com.yao.express.service.user.services;

import com.alibaba.fastjson.JSON;
import com.yao.express.service.user.dto.HeartbeatMsg;
import com.yao.express.service.user.entity.Driver;
import com.yao.express.service.user.entity.Heartbeat;
import com.yao.express.service.user.entity.Orderr;
import com.yao.express.service.user.entity.Vehicle;
import com.yao.express.service.user.enums.HeartbeatMsgType;
import com.yao.express.service.user.enums.OrderStatusEnum;
import com.yao.express.service.user.mapper.DriverMapper;
import com.yao.express.service.user.mapper.OrderrMapper;
import com.yao.express.service.user.mapper.VehicleMapper;
import com.yao.express.service.user.services.cache.HeartbeatCacheDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 订单调度器
 */
@Service
public class OrderScheduler {

    private static Logger logger = LoggerFactory.getLogger(OrderScheduler.class);

    @Resource
    private HeartbeatCacheDao heartbeatCacheDao;
    @Resource
    private HeartbeatService heartbeatService;
    @Resource
    private OrderrMapper orderrMapper;
    @Resource
    private DriverMapper driverMapper;
    @Resource
    private VehicleMapper vehicleMapper;

    // 订单分配调度超时时间s
    @Value("${order.distribute.max.time}")
    private Long MAX_DISTRIBUTE_TIME;
    @Value("${driver.distance.max.radius}")
    private Double MAX_DISTANCE_RADIUS;

    /**
     * 分配订单，异步线程处理
     * @param order
     */
    @Async
    public void distributeOrder(Orderr order) {
        logger.info("start distribute order, order = {}", JSON.toJSONString(order));
        final long starttime = System.nanoTime();
        // 获取附近最近的可用的司机
        double lati = order.getOriginLocationLati();
        double longi = order.getOriginLocationLongi();
        boolean success = false;

        while (success == false) {
            long nowtime = System.nanoTime();
            logger.info("调度耗时：{} | {}", nowtime-starttime, MAX_DISTRIBUTE_TIME * 1000000);
            if (nowtime - starttime > MAX_DISTRIBUTE_TIME * 1000000) {
                // 超时未分配，修改订单状态，并发送消息给客户
                if (!success) {
                    Orderr updateOrder = new Orderr();
                    updateOrder.setOrderId(order.getOrderId());
                    updateOrder.setStatus(OrderStatusEnum.TIMEOUT.value);
                    orderrMapper.updateByOrderIdSelective(updateOrder);

                    HeartbeatMsg customerMsg = new HeartbeatMsg();
                    customerMsg.setType(HeartbeatMsgType.ORDER_TIMEOUT.value);
                    customerMsg.setTip(HeartbeatMsgType.ORDER_TIMEOUT.desc);

                    Heartbeat cusHb = new Heartbeat();
                    cusHb.setMsg(JSON.toJSONString(customerMsg));
                    cusHb.setWhoId(order.getSenderId());

                    heartbeatCacheDao.updateByWhoIdSelective(cusHb);

                    break;
                }
            } else {
                Map<String, Double[]> results = heartbeatCacheDao.nearestDriverLocs(lati, longi, null, MAX_DISTANCE_RADIUS, null); // FIXME
                for (String driverId : results.keySet()) {
                    double startLati = results.get(driverId)[0];
                    double startLongi = results.get(driverId)[1];
                    order.setStartLocationLatitude(startLati);
                    order.setStartLocationLongitude(startLongi);
                    order.setDriverId(driverId);
                    order.setStatus(OrderStatusEnum.GOT.value);
                    // TODO 获取司机信息，车辆信息
                    Driver driver = driverMapper.selectByDriverId(driverId);
                    if (null == driver) {
                        logger.info("司机信息为空分配下一个司机");
                        continue;
                    } else {
                        Vehicle vehicle = vehicleMapper.selectByVehicleId(driver.getVehicleId());
                        if (null != vehicle) {
                            order.setDriverName(driver.getName());
                            order.setDriverPhone(driver.getPhone());
                            order.setVehicleBrand(vehicle.getBrand());
                            order.setVehicleColor(vehicle.getColor());
                            order.setVehicleId(vehicle.getVehicleId());
                            order.setVehicleType(vehicle.getType());
                            order.setLicenseNo(vehicle.getLicenseNo());
                        } else {
                            logger.info("没有车辆信息分配下一个司机");
                            continue;
                        }
                    }

                    // 修改司机状态并发送司机消息
                    Heartbeat driverHb = new Heartbeat();
                    driverHb.setWhoId(driverId);
                    driverHb.setAvailable(false);
                    HeartbeatMsg driverMsg = new HeartbeatMsg();
                    driverMsg.setTip(HeartbeatMsgType.ORDER_NEW.desc);
                    driverMsg.setType(HeartbeatMsgType.ORDER_NEW.value);
                    driverMsg.setContent(order.getOrderId());
                    driverHb.setMsg(JSON.toJSONString(driverMsg));
                    if (null != heartbeatCacheDao.updateByWhoIdAndVersionSelective(driverHb)) {

                        // 修改订单状态
                        if (0 < orderrMapper.updateByPrimaryKeySelective(order)) {
                            logger.info("distribute order success...");
                            success = true;
                            // 锁定司机，分派订单成功
                            heartbeatService.sendHeartbeatMsg(order.getSenderId(), HeartbeatMsgType.ORDER_GOT.value,
                                    order.getOrderId(),"恭喜，订单分配成功！");
                            break;
                        }
                    }
                }
            }
        }

        logger.info("order scheduler finished in {}纳秒", System.nanoTime()-starttime);
    }
}
