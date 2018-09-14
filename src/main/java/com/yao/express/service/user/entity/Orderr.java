package com.yao.express.service.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Orderr {
    private Long id;

    private String orderId;

    private String senderId;

    private String senderPhone;

    private String driverId;

    private String driverName;

    private String driverPhone;

    private Integer capacity;

    private String status;

    private Double originLocationLati;

    private Double originLocationLongi;

    private String originAddressTitle;

    private String originAddressDetail;

    private String originAddressRoom;

    private String originPerson;

    private String originPhone;

    private Double destinationLocationLati;

    private Double destinationLocationLongi;

    private String destinationAddressTitle;

    private String destinationAddressDetail;

    private String destinationAddressRoom;

    private String destinationPerson;

    private String destinationPhone;

    private BigDecimal mileagePrice;

    private BigDecimal waitPrice;

    private BigDecimal totalPrice;

    private String deliverType;

    private String orderType;

    private Date bookTime;

    private String senderName;

    private String vehicleId;

    private String licenseNo;

    private String vehicleColor;

    private String vehicleType;

    private String vehicleBrand;

    private Integer distance;

    private Integer duration;

    private Integer waitMinutes;

    private Double startLocationLatitude;

    private Double startLocationLongitude;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date arriveTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date sendTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date receiveTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date completeTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date updateTime;

    private String note;

    private String blockReason;

    private String rejectReason;

    private String backReason;

    private String planPoints;

    private String realPoints;

    public String getPlanPoints() {
        return planPoints;
    }

    public void setPlanPoints(String planPoints) {
        this.planPoints = planPoints;
    }

    public String getRealPoints() {
        return realPoints;
    }

    public void setRealPoints(String realPoints) {
        this.realPoints = realPoints;
    }


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

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
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

    public String getDestinationAddressRoom() {
        return destinationAddressRoom;
    }

    public void setDestinationAddressRoom(String destinationAddressRoom) {
        this.destinationAddressRoom = destinationAddressRoom;
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

    public Date getBookTime() {
        return bookTime;
    }

    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
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

    public Integer getWaitMinutes() {
        return waitMinutes;
    }

    public void setWaitMinutes(Integer waitMinutes) {
        this.waitMinutes = waitMinutes;
    }

    public Double getStartLocationLatitude() {
        return startLocationLatitude;
    }

    public void setStartLocationLatitude(Double startLocationLatitude) {
        this.startLocationLatitude = startLocationLatitude;
    }

    public Double getStartLocationLongitude() {
        return startLocationLongitude;
    }

    public void setStartLocationLongitude(Double startLocationLongitude) {
        this.startLocationLongitude = startLocationLongitude;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }
}