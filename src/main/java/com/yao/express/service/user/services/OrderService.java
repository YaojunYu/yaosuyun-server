package com.yao.express.service.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.*;
import com.yao.express.service.user.entity.Customer;
import com.yao.express.service.user.entity.Orderr;
import com.yao.express.service.user.enums.AccountRoleEnum;
import com.yao.express.service.user.enums.HeartbeatMsgType;
import com.yao.express.service.user.enums.OrderStatusEnum;
import com.yao.express.service.user.enums.OrderTypeEnum;
import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.mapper.CustomerMapper;
import com.yao.express.service.user.mapper.OrderrMapper;
import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.util.http.HttpUtils;
import com.yao.express.service.user.util.login.UserLoginUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private OrderScheduler orderScheduler;

    @Resource
    private HeartbeatService heartbeatService;
    @Resource
    private OrderrMapper orderMapper;
    @Resource
    private CustomerMapper customerMapper;

    @Value("${map.service.qqmapkey}")
    private String qqmapkey;
    @Value("${map.service.qqmap.driving.url}")
    private String qqmapDrivingUrl;

    private static final HashMap<String, String[]> statusMap = new HashMap<>();
    static {
        statusMap.put("created", new String[]{"timeout", "got"});
        statusMap.put("timeout", new String[]{"deleted"});
        statusMap.put("got", new String[]{"canceled", "rejected", "arrived"});
        statusMap.put("canceled", new String[]{"deleted"});
        statusMap.put("deleted", new String[]{});
        statusMap.put("rejected", new String[]{"deleted"});
        statusMap.put("arrived", new String[]{"send","blocked"});
        statusMap.put("blocked", new String[]{});
        statusMap.put("send", new String[]{"finished", "back"});
        statusMap.put("finished", new String[]{});
        statusMap.put("back", new String[]{"backing"});
        statusMap.put("backing", new String[]{"backed"});
        statusMap.put("backed", new String[]{});
    }

    /**
     * 客户下单
     * @param orderRequest
     * @return
     */
    public AppResponse<Orderr> order(OrderRequest orderRequest) {
        logger.info("order params {}", JSON.toJSONString(orderRequest));
        // 入参检测
        if (null == orderRequest) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        } else {
            if (!orderRequest.getOrderType().equals(OrderTypeEnum.REAL.value) &&
                    !orderRequest.getOrderType().equals(OrderTypeEnum.BOOK.value)) {
                return new AppResponse<>(ResponseErrorCode.ORDER_REQUEST_ORDER_TYPE_INVALID.code,
                        ResponseErrorCode.ORDER_REQUEST_ORDER_TYPE_INVALID.msg, null);
            }
            if (orderRequest.getOrderType().equals(OrderTypeEnum.BOOK.value) &&
                    orderRequest.getBookTime() == null) {
                return new AppResponse<>(ResponseErrorCode.ORDER_REQUEST_ORDER_BOOK_TIME_REQUIRED.code,
                        ResponseErrorCode.ORDER_REQUEST_ORDER_BOOK_TIME_REQUIRED.msg, null);
            }
        }

        /* 保存订单 */
        Orderr order = new Orderr();
        // 获取发货人信息
        String sendId = ((LoginUser) UserLoginUtils.getCurrentUser()).getUid();
        orderRequest.setSenderId(sendId);
        Customer customer = customerMapper.selectByCustomerId(sendId);
        if (null == customer) {
            return new AppResponse<>(ResponseErrorCode.ORDER_SENDER_NOT_FOUND.code,
                    ResponseErrorCode.ORDER_SENDER_NOT_FOUND.msg, null);
        }
        order.setSenderId(sendId);
        order.setSenderName(customer.getName());
        order.setSenderPhone(customer.getPhone());
        // 取货信息
        order.setOriginAddressTitle(orderRequest.getOriginAddressTitle());
        order.setOriginAddressDetail(orderRequest.getOriginAddressDetail());
        order.setOriginAddressRoom(orderRequest.getOriginAddressRoom());
        order.setOriginLocationLati(orderRequest.getOriginLocationLati());
        order.setOriginLocationLongi(orderRequest.getOriginLocationLongi());
        order.setOriginPerson(orderRequest.getOriginPerson());
        order.setOriginPhone(orderRequest.getOriginPhone());
        // 收货信息
        order.setDestinationAddressTitle(orderRequest.getDestinationAddressTitle());
        order.setDestinationAddressDetail(orderRequest.getDestinationAddressDetail());
        order.setDestinationAddressRoom(orderRequest.getDestinationAddressRoom());
        order.setDestinationLocationLati(orderRequest.getDestinationLocationLati());
        order.setDestinationLocationLongi(orderRequest.getDestinationLocationLongi());
        order.setDestinationPerson(orderRequest.getDestinationPerson());
        order.setDestinationPhone(orderRequest.getDestinationPhone());
        // 订单基本属性
        order.setOrderType(orderRequest.getOrderType());
        order.setBookTime(orderRequest.getBookTime());
        order.setCapacity(orderRequest.getCapacity());
        order.setDeliverType(orderRequest.getDeliverType());
        order.setStatus(OrderStatusEnum.CREATED.value);
        String orderId = UUID.randomUUID().toString();
        order.setOrderId(orderId);
        order.setNote(orderRequest.getNote());
        // 订单价格计算，请求地图服务计算驾车里程和耗时
        Map<String, String> data = calculatePrice(orderRequest.getOriginLocationLati(), orderRequest.getOriginLocationLongi(),
                orderRequest.getDestinationLocationLati(), orderRequest.getDestinationLocationLongi(), orderRequest.getCapacity(),
                orderRequest.getDeliverType(), orderRequest.getBookTime());
        if (null == data) {
            return new AppResponse(ResponseErrorCode.WX_MAP_SERVICE_REQUEST_FAIL.code,
                    ResponseErrorCode.WX_MAP_SERVICE_REQUEST_FAIL.msg, null);
        }
        order.setDistance(Integer.parseInt(data.get("distance")));
        order.setDuration(Integer.parseInt(data.get("duration")));
        order.setPlanPoints(data.get("planPoints"));
        order.setWaitMinutes(0);
        order.setMileagePrice(new BigDecimal(Double.parseDouble(data.get("mileagePrice"))));
        order.setWaitPrice(new BigDecimal(0.0));
        order.setTotalPrice(new BigDecimal(Double.parseDouble(data.get("totalPrice"))));

        logger.info("insertSelect order = {}", JSON.toJSONString(order));
        if (0 >= orderMapper.insertSelective(order)) {
            return AppResponse.ERROR;
        } else {
            // TODO 另起线程分配订单
            orderScheduler.distributeOrder(order);

            return AppResponse.success(order);
        }
    }

    /**
     * 预估订单价格
     * @param fromLati
     * @param fromLongi
     * @param toLati
     * @param toLongi
     * @param capacity
     * @param deliverType
     * @param bookTime
     * @return
     */
    public AppResponse<Map> calculateOrderPrice(Double fromLati, Double fromLongi,
                                                Double toLati, Double toLongi,
                                                Integer capacity, String deliverType,
                                                Date bookTime) {
        Map<String, String> result = calculatePrice(fromLati, fromLongi, toLati, toLongi, capacity, deliverType, bookTime);
        if (null == result) {
            return new AppResponse(ResponseErrorCode.WX_MAP_SERVICE_REQUEST_FAIL.code,
                    ResponseErrorCode.WX_MAP_SERVICE_REQUEST_FAIL.msg, null);
        } else {
            return AppResponse.success(result);
        }
    }

    /**
     * 顾客和司机查看自己的订单详情
     * @param orderId
     * @return
     */
    public AppResponse<Orderr> getOrderDetail(String orderId) {
        if (!StringUtils.isEmpty(orderId)) {
            Orderr order = orderMapper.selectByOrderId(orderId);
            if (null != order) {
                String uid = null;
                Object user = UserLoginUtils.getCurrentUser();
                if (user instanceof LoginUser) {
                    uid = ((LoginUser)user).getUid();
                }

                if (uid.equals(order.getSenderId()) ||
                        uid.equals(order.getDriverId())) {
                    return AppResponse.success(order);
                } else {
                    return new AppResponse<>(ResponseErrorCode.ORDER_QUERY_FORBIDDEN.code,
                            ResponseErrorCode.ORDER_QUERY_FORBIDDEN.msg, null);
                }
            }
        }

        return AppResponse.success(null);
    }

    /**
     * 计算订单价格
     * @param fromLati
     * @param fromLongi
     * @param toLati
     * @param toLongi
     * @param capacity
     * @param deliverType
     * @param bookTime
     * @return
     */
    private Map<String, String> calculatePrice(Double fromLati, Double fromLongi,
                                              Double toLati, Double toLongi,
                                              Integer capacity, String deliverType,
                                              Date bookTime) {
        Map<String, String> data = null;
        // 请求腾讯地图计算里程

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("from", fromLati+","+fromLongi);
        paramsMap.put("to", toLati+","+toLongi);
        paramsMap.put("waypoints", null); // FIXME
        paramsMap.put("output", "json");
        paramsMap.put("key", qqmapkey);
        String responseData = HttpUtils.get(qqmapDrivingUrl, null, paramsMap);
        if (!StringUtils.isEmpty(responseData)) {
            JSONObject responseObject = JSON.parseObject(responseData);
            if (0 == responseObject.getInteger("status")) {
                data = new HashMap<>();
                // 成功
                int distance = 0;
                int duration = 0;
                String polyline = null;
                JSONObject result = responseObject.getJSONObject("result");
                if (null != result) {
                    JSONArray routes = result.getJSONArray("routes");
                    if (null != routes && routes.size() > 0) {
                        JSONObject route = routes.getJSONObject(0);
                        distance = route.getInteger("distance");
                        duration = route.getInteger("duration");
                        polyline = JSON.toJSONString(route.getJSONArray("polyline"));
                    }
                }
                data.put("distance", String.valueOf(distance));
                data.put("duration", String.valueOf(duration));
                data.put("planPoints", polyline);

                // FIXME 计价规则
                DecimalFormat df = new DecimalFormat("#.00");
                // 起步价
                BigDecimal stepPrice = new BigDecimal(10.0);
                // 里程单价/公里
                BigDecimal pricePerKm = new BigDecimal(3.0);
                // 等待计费
                BigDecimal waitPrice = new BigDecimal(0.0);
                // 里程费用
                BigDecimal mileagePrice = pricePerKm.multiply(new BigDecimal(0.001)).multiply(new BigDecimal(distance));
                // 总价
                BigDecimal totalPrice = mileagePrice.add(stepPrice);
                data.put("stepPrice", df.format(stepPrice.doubleValue()));
                data.put("pricePerKm", df.format(pricePerKm.doubleValue()));
                data.put("mileagePrice", df.format(mileagePrice.doubleValue()));
                data.put("totalPrice", df.format(totalPrice.doubleValue()));
            }
        } else {
            logger.error("获取腾讯地图驾车路线失败");
        }

        return data;
    }

    /**
     * 分页获取客户订单数据
     * @param customerId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public AppResponse<PageInfo<CustomerOrderListItem>> getCustomerOrders(String customerId, List<String> statuses, int pageNum, int pageSize) {

        List<CustomerOrderListItem> list = new ArrayList<>();
        if (StringUtils.isEmpty(customerId) || null == statuses || statuses.size() == 0
                || pageNum < 1 || pageSize < 1) {
            return AppResponse.success(new PageInfo<>(list));
        }
        // 只有顾客自己能查看自己的订单
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        if (!customerId.equals(loginUser.getUid())) {
            return new AppResponse<>(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                    ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, null);
        }

        // 分页
        String status = getStatusString(statuses);
        PageHelper.startPage(pageNum, pageSize);
        list = orderMapper.selectByCustomerIdAndStatus(customerId, status);
        // 取分页信息
        PageInfo<CustomerOrderListItem> pageInfo = new PageInfo(list);

        return AppResponse.success(pageInfo);
    }

    /**
     * 获取司机订单，按状态查询
     * @param driverId
     * @param statuses
     * @param pageNum
     * @param pageSize
     * @return
     */
    public AppResponse<PageInfo<DriverOrderListItem>> getDriverOrders(String driverId, List<String> statuses, int pageNum, int pageSize) {
        List<DriverOrderListItem> list = new ArrayList<>();
        if (StringUtils.isEmpty(driverId) || null == statuses || statuses.size()==0
                || pageNum < 1 || pageSize < 1) {
            return AppResponse.success(new PageInfo<>(list));
        }
        // 只有自己能查看自己的订单
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        if (!driverId.equals(loginUser.getUid())) {
            return new AppResponse<>(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                    ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, null);
        }

        // 分页
        String status = getStatusString(statuses);
        PageHelper.startPage(pageNum, pageSize);
        list = orderMapper.selectByDriverIdAndStatus(driverId, status);
        // 取分页信息
        PageInfo<CustomerOrderListItem> pageInfo = new PageInfo(list);

        return AppResponse.success(pageInfo);
    }

    private String getStatusString(List<String> statuses) {
        String status = "";
        if (null != statuses && statuses.size()>0) {
            for(int i = 0; i < statuses.size(); i++) {
                status += "\""+statuses.get(i)+"\"";
                if (i < statuses.size()-1) {
                    status += ",";
                }
            }
        }
        return status;
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    public AppResponse<Boolean> cancelOrder(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return AppResponse.success(false);
        }

        // 判断是否是自己的订单
        LoginUser user = (LoginUser) UserLoginUtils.getCurrentUser();
        String senderId = user.getUid();

        Orderr oldOrder = orderMapper.selectByOrderId(orderId);
        if (null != oldOrder) {
            if (!oldOrder.getSenderId().equals(senderId)) {
                return new AppResponse(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                        ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, false);
            }
        } else {
            return new AppResponse(ResponseErrorCode.ORDER_NOT_FOUND.code,
                    ResponseErrorCode.ORDER_NOT_FOUND.msg, false);
        }

        // 修改状态
        Orderr order = new Orderr();
        order.setOrderId(orderId);
        order.setStatus(OrderStatusEnum.CANCELED.value);

        int i = orderMapper.updateByOrderIdSelective(order);
        if(i > 0) {
            // 修改司机状态可用
            heartbeatService.updateDriverAvailable(oldOrder.getDriverId(), true);
            // 发送消息给司机
            HeartbeatMsgType msgType = HeartbeatMsgType.ORDER_CANCELED;
            heartbeatService.sendHeartbeatMsg(oldOrder.getDriverId(), msgType.value,
                    oldOrder.getOrderId(), msgType.desc);
        }

        return AppResponse.success(i > 0);
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    public AppResponse<Boolean> deleteOrder(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return AppResponse.success(false);
        }

        // 判断权限
        LoginUser user = (LoginUser) UserLoginUtils.getCurrentUser();
        String senderId = user.getUid();

        Orderr oldOrder = orderMapper.selectByOrderId(orderId);
        if (null != oldOrder) {
            if (!oldOrder.getSenderId().equals(senderId) ||
                    !OrderStatusEnum.CANCELED.value.equals(oldOrder.getStatus())) {
                return new AppResponse(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                        ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, false);
            }
        } else {
            return new AppResponse(ResponseErrorCode.ORDER_NOT_FOUND.code,
                    ResponseErrorCode.ORDER_NOT_FOUND.msg, false);
        }

        // 修改状态
        Orderr order = new Orderr();
        order.setOrderId(orderId);
        order.setStatus(OrderStatusEnum.DELETED.value);

        int i = orderMapper.updateByOrderIdSelective(order);

        return AppResponse.success(i > 0);
    }

    /**
     * 订单接受送回
     * @param orderId
     * @return
     */
    public AppResponse<Boolean> backOrder(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return AppResponse.success(false);
        }

        // 判断权限
        LoginUser user = (LoginUser) UserLoginUtils.getCurrentUser();
        String senderId = user.getUid();

        Orderr oldOrder = orderMapper.selectByOrderId(orderId);
        if (null != oldOrder) {
            if (!oldOrder.getSenderId().equals(senderId) ||
                    !OrderStatusEnum.BACK.value.equals(oldOrder.getStatus())) {
                return new AppResponse(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                        ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, false);
            }
        } else {
            return new AppResponse(ResponseErrorCode.ORDER_NOT_FOUND.code,
                    ResponseErrorCode.ORDER_NOT_FOUND.msg, false);
        }

        // 修改状态
        Orderr order = new Orderr();
        order.setOrderId(orderId);
        order.setStatus(OrderStatusEnum.BACKING.value);

        int i = orderMapper.updateByOrderIdSelective(order);
        // 通知司机
        if (i > 0) {
            HeartbeatMsgType msgType = HeartbeatMsgType.ORDER_BACKING;
            heartbeatService.sendHeartbeatMsg(oldOrder.getDriverId(), msgType.value,
                    oldOrder.getOrderId(), msgType.desc);
        }

        return AppResponse.success(i > 0);
    }

    /**
     * 订单拒绝送回
     * @param orderId
     * @return
     */
    public AppResponse<Boolean> unbackOrder(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return AppResponse.success(false);
        }

        // 判断权限
        LoginUser user = (LoginUser) UserLoginUtils.getCurrentUser();
        String senderId = user.getUid();

        Orderr oldOrder = orderMapper.selectByOrderId(orderId);
        if (null != oldOrder) {
            if (!oldOrder.getSenderId().equals(senderId) ||
                    !OrderStatusEnum.BACK.value.equals(oldOrder.getStatus())) {
                return new AppResponse(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                        ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, false);
            }
        } else {
            return new AppResponse(ResponseErrorCode.ORDER_NOT_FOUND.code,
                    ResponseErrorCode.ORDER_NOT_FOUND.msg, false);
        }

        // 修改状态
        Orderr order = new Orderr();
        order.setOrderId(orderId);
        order.setStatus(OrderStatusEnum.BLOCKED.value);

        int i = orderMapper.updateByOrderIdSelective(order);
        // 通知司机
        if (i > 0) {
            HeartbeatMsgType msgType = HeartbeatMsgType.ORDER_BLOCKED;
            heartbeatService.sendHeartbeatMsg(oldOrder.getDriverId(), msgType.value,
                    oldOrder.getOrderId(), "顾客拒绝了送回申请，订单自动进入问题件，等待公司处理");
            // 修改司机状态可用
            heartbeatService.updateDriverAvailable(oldOrder.getDriverId(), true);
        }

        return AppResponse.success(i > 0);
    }


    /**
     * 修改订单状态
     * @param orderId
     * @param status
     * @return
     */
    public AppResponse<Boolean> updateOrderStatus(String orderId, String status, String reason) {
        if (StringUtils.isEmpty(orderId)) {
            return AppResponse.success(false);
        }

        // 判断权限
        LoginUser user = (LoginUser) UserLoginUtils.getCurrentUser();
        String driverId = user.getUid();
        Orderr oldOrder = orderMapper.selectByOrderId(orderId);
        String oldStatus;
        if (null != oldOrder) {
            // 司机操作权限检测
            if (!oldOrder.getDriverId().equals(driverId)) {
                return new AppResponse(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                        ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, false);
            }
            // 订单状态检测
            oldStatus = oldOrder.getStatus();
            if (!Arrays.asList(statusMap.get(oldStatus)).contains(status)) {
                return new AppResponse<>(ResponseErrorCode.ORDER_STATUS_OPS_FORBIDDEN.code,
                        ResponseErrorCode.ORDER_STATUS_OPS_FORBIDDEN.msg, null);
            }
            // 当前状态无需修改，直接返回成功
            if (oldStatus.equals(status)) {
                return AppResponse.success(true);
            }
        } else {
            // 订单为空
            return new AppResponse(ResponseErrorCode.ORDER_NOT_FOUND.code,
                    ResponseErrorCode.ORDER_NOT_FOUND.msg, false);
        }

        // 修改状态
        Orderr order = new Orderr();
        order.setOrderId(orderId);
        order.setStatus(status);
        if (OrderStatusEnum.REJECTED.value.equals(status)) {
            // 拒绝订单
            order.setRejectReason(reason);
        } else if(OrderStatusEnum.BACK.value.equals(status)) {
            order.setBackReason(reason);
        } else if(OrderStatusEnum.BLOCKED.value.equals(status)) {
            order.setBlockReason(reason);
        }
        int i = orderMapper.updateByOrderIdSelective(order);

        // 如果订单状态修改完成，进行消息通知，
        // 如果是订单完成状态，则重置司机状态为可用
        if(i > 0) {
            boolean available = false;
            HeartbeatMsgType msgType = HeartbeatMsgType.NORMAL_TIP;
            if (status.equals(OrderStatusEnum.REJECTED.value)) {
                msgType = HeartbeatMsgType.ORDER_REJECTED;
                available = true;
            } else if (status.equals(OrderStatusEnum.ARRIVED.value)){
                msgType = HeartbeatMsgType.ORDER_ARRIVED;
            } else if (status.equals(OrderStatusEnum.BLOCKED.value)){
                msgType = HeartbeatMsgType.ORDER_BLOCKED;
                available = true;
            } else if (status.equals(OrderStatusEnum.SEND.value)){
                msgType = HeartbeatMsgType.ORDER_SENT;
            } else if (status.equals(OrderStatusEnum.BACK.value)){
                msgType = HeartbeatMsgType.ORDER_BACK;
            } else if (status.equals(OrderStatusEnum.BACKED.value)){
                msgType = HeartbeatMsgType.ORDER_BACKED;
                available = true;
            } else if (status.equals(OrderStatusEnum.FINISHED.value)){
                msgType = HeartbeatMsgType.ORDER_FINISHED;
                available = true;
            }

            if (available) {
                heartbeatService.updateDriverAvailable(driverId, available);
            }
            heartbeatService.sendHeartbeatMsg(oldOrder.getSenderId(), msgType.value,
                    oldOrder.getOrderId(), msgType.desc);

        }

        return AppResponse.success(i > 0);

    }

    /**
     * 管理系统获取订单列表
     * @param queryOption
     * @return
     */
    public AppResponse<PageInfo<Orderr>> getManagerOrderList(ListQueryOption queryOption) {
        if (null == queryOption) { // 默认
            queryOption = new ListQueryOption();
            queryOption.setPage(1);
            queryOption.setSize(10);
        }
        if (queryOption.getPage()<=0) {
            queryOption.setPage(1);
        }
        if (queryOption.getSize()<=0) {
            queryOption.setSize(10);
        }


        // 只有管理员可以查看
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        if (!loginUser.getRoles().contains(AccountRoleEnum.MANAGER.value)) {  // FIXME
            return new AppResponse<>(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                    ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, null);
        }

        // 分页
        int pageNum = queryOption.getPage();
        int pageSize = queryOption.getSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Orderr> list = orderMapper.selectList(queryOption);
        // 取分页信息
        PageInfo<Orderr> pageInfo = new PageInfo(list);

        return AppResponse.success(pageInfo);
    }
}
