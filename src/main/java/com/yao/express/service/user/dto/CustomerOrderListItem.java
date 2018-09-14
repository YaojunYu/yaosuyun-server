package com.yao.express.service.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerOrderListItem {

    private Long id;

    private String orderId;

    private String senderId;

    private Integer capacity;

    private String status;

    private String originAddressTitle;

    private String originAddressRoom;

    private String destinationAddressTitle;

    private String destinationAddressRoom;

    private BigDecimal totalPrice;

    private String deliverType;

    private String orderType;

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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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

    public String getOriginAddressTitle() {
        return originAddressTitle;
    }

    public void setOriginAddressTitle(String originAddressTitle) {
        this.originAddressTitle = originAddressTitle;
    }

    public String getDestinationAddressTitle() {
        return destinationAddressTitle;
    }

    public void setDestinationAddressTitle(String destinationAddressTitle) {
        this.destinationAddressTitle = destinationAddressTitle;
    }

    public String getOriginAddressRoom() {
        return originAddressRoom;
    }

    public void setOriginAddressRoom(String originAddressRoom) {
        this.originAddressRoom = originAddressRoom;
    }

    public String getDestinationAddressRoom() {
        return destinationAddressRoom;
    }

    public void setDestinationAddressRoom(String destinationAddressRoom) {
        this.destinationAddressRoom = destinationAddressRoom;
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
