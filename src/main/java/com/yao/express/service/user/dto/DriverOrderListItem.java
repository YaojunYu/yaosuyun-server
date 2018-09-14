package com.yao.express.service.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yao.express.service.user.entity.Orderr;

import java.math.BigDecimal;
import java.util.Date;

public class DriverOrderListItem {

    // id
    private Long id;
    // 订单id
    private String orderId;
    // 发货人电话
    private String senderPhone;
    // 运输空间
    private Integer capacity;
    // 订单状态
    private String status;
    // 起始地点纬度
    private Double originLocationLati;
    // 起始地点经度
    private Double originLocationLongi;
    // 起始点地址
    private String originAddressTitle;
    // 起始点取货人姓名
    private String originPerson;
    // 起始点取货人电话
    private String originPhone;
    // 目的地纬度
    private Double destinationLocationLati;
    // 目的地经度
    private Double destinationLocationLongi;
    // 目的地
    private String destinationAddressTitle;
    // 目的地收货人姓名
    private String destinationPerson;
    // 目的地收货人电话
    private String destinationPhone;

    // 里程价格
    private BigDecimal mileagePrice;
    // 等待计价
    private BigDecimal waitPrice;
    // 订单总价
    private BigDecimal totalPrice;
    // 发货类型
    private String deliverType;
    // 订单类型
    private String orderType;
    // 发货人姓名
    private String senderName;
    // 估计里程
    private Integer distance;
    // 估计耗时
    private Integer duration;

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm", locale="zh", timezone="GMT+8")
    private Date bookTime;

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm", locale="zh", timezone="GMT+8")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getOriginLocationLati() {
        return originLocationLati;
    }

    public void setOriginLocationLati(Double originLocationLati) {
        this.originLocationLati = originLocationLati;
    }

    public Double getOriginLocationLongi() {
        return originLocationLongi;
    }

    public void setOriginLocationLongi(Double originLocationLongi) {
        this.originLocationLongi = originLocationLongi;
    }

    public String getOriginAddressTitle() {
        return originAddressTitle;
    }

    public void setOriginAddressTitle(String originAddressTitle) {
        this.originAddressTitle = originAddressTitle;
    }

    public String getOriginPerson() {
        return originPerson;
    }

    public void setOriginPerson(String originPerson) {
        this.originPerson = originPerson;
    }

    public String getOriginPhone() {
        return originPhone;
    }

    public void setOriginPhone(String originPhone) {
        this.originPhone = originPhone;
    }

    public Double getDestinationLocationLati() {
        return destinationLocationLati;
    }

    public void setDestinationLocationLati(Double destinationLocationLati) {
        this.destinationLocationLati = destinationLocationLati;
    }

    public Double getDestinationLocationLongi() {
        return destinationLocationLongi;
    }

    public void setDestinationLocationLongi(Double destinationLocationLongi) {
        this.destinationLocationLongi = destinationLocationLongi;
    }

    public String getDestinationAddressTitle() {
        return destinationAddressTitle;
    }

    public void setDestinationAddressTitle(String destinationAddressTitle) {
        this.destinationAddressTitle = destinationAddressTitle;
    }

    public String getDestinationPerson() {
        return destinationPerson;
    }

    public void setDestinationPerson(String destinationPerson) {
        this.destinationPerson = destinationPerson;
    }

    public String getDestinationPhone() {
        return destinationPhone;
    }

    public void setDestinationPhone(String destinationPhone) {
        this.destinationPhone = destinationPhone;
    }

    public BigDecimal getMileagePrice() {
        return mileagePrice;
    }

    public void setMileagePrice(BigDecimal mileagePrice) {
        this.mileagePrice = mileagePrice;
    }

    public BigDecimal getWaitPrice() {
        return waitPrice;
    }

    public void setWaitPrice(BigDecimal waitPrice) {
        this.waitPrice = waitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getBookTime() {
        return bookTime;
    }

    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
