package com.yao.express.service.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 客户端下单请求
 */
public class OrderRequest {

    private String senderId;
    // 下单类型
    @NotBlank
    private String clientType;

    @NotNull
    private Integer capacity;

    @NotNull
    private Double originLocationLati;

    @NotNull
    private Double originLocationLongi;

    @NotBlank
    private String originAddressTitle;

    @NotBlank
    private String originAddressDetail;
    private String originAddressRoom;

    private String originPerson;

    @NotBlank
    private String originPhone;

    @NotNull
    private Double destinationLocationLati;

    @NotNull
    private Double destinationLocationLongi;

    @NotBlank
    private String destinationAddressTitle;

    @NotBlank
    private String destinationAddressDetail;

    private String destinationAddressRoom;

    private String destinationPerson;

    private String destinationPhone;

    @NotBlank
    private String deliverType;

    @NotBlank
    private String orderType;

    private Date bookTime;

    private String note;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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

    public String getOriginAddressDetail() {
        return originAddressDetail;
    }

    public void setOriginAddressDetail(String originAddressDetail) {
        this.originAddressDetail = originAddressDetail;
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

    public String getDestinationAddressDetail() {
        return destinationAddressDetail;
    }

    public void setDestinationAddressDetail(String destinationAddressDetail) {
        this.destinationAddressDetail = destinationAddressDetail;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
