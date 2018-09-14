package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Orderr;
import org.apache.ibatis.jdbc.SQL;

public class OrderrSqlProvider {

    public String insertSelective(Orderr record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("orderr");
        
        if (record.getOrderId() != null) {
            sql.VALUES("order_id", "#{orderId,jdbcType=VARCHAR}");
        }
        
        if (record.getSenderId() != null) {
            sql.VALUES("sender_id", "#{senderId,jdbcType=VARCHAR}");
        }
        
        if (record.getSenderPhone() != null) {
            sql.VALUES("sender_phone", "#{senderPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getDriverId() != null) {
            sql.VALUES("driver_id", "#{driverId,jdbcType=VARCHAR}");
        }
        
        if (record.getDriverName() != null) {
            sql.VALUES("driver_name", "#{driverName,jdbcType=VARCHAR}");
        }
        
        if (record.getDriverPhone() != null) {
            sql.VALUES("driver_phone", "#{driverPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getCapacity() != null) {
            sql.VALUES("capacity", "#{capacity,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginLocationLati() != null) {
            sql.VALUES("origin_location_lati", "#{originLocationLati,jdbcType=DOUBLE}");
        }
        
        if (record.getOriginLocationLongi() != null) {
            sql.VALUES("origin_location_longi", "#{originLocationLongi,jdbcType=DOUBLE}");
        }
        
        if (record.getOriginAddressTitle() != null) {
            sql.VALUES("origin_address_title", "#{originAddressTitle,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginAddressDetail() != null) {
            sql.VALUES("origin_address_detail", "#{originAddressDetail,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginAddressRoom() != null) {
            sql.VALUES("origin_address_room", "#{originAddressRoom,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginPerson() != null) {
            sql.VALUES("origin_person", "#{originPerson,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginPhone() != null) {
            sql.VALUES("origin_phone", "#{originPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationLocationLati() != null) {
            sql.VALUES("destination_location_lati", "#{destinationLocationLati,jdbcType=DOUBLE}");
        }
        
        if (record.getDestinationLocationLongi() != null) {
            sql.VALUES("destination_location_longi", "#{destinationLocationLongi,jdbcType=DOUBLE}");
        }
        
        if (record.getDestinationAddressTitle() != null) {
            sql.VALUES("destination_address_title", "#{destinationAddressTitle,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationAddressDetail() != null) {
            sql.VALUES("destination_address_detail", "#{destinationAddressDetail,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationAddressRoom() != null) {
            sql.VALUES("destination_address_room", "#{destinationAddressRoom,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationPerson() != null) {
            sql.VALUES("destination_person", "#{destinationPerson,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationPhone() != null) {
            sql.VALUES("destination_phone", "#{destinationPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getMileagePrice() != null) {
            sql.VALUES("mileage_price", "#{mileagePrice,jdbcType=DECIMAL}");
        }
        
        if (record.getWaitPrice() != null) {
            sql.VALUES("wait_price", "#{waitPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getTotalPrice() != null) {
            sql.VALUES("total_price", "#{totalPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getDeliverType() != null) {
            sql.VALUES("deliver_type", "#{deliverType,jdbcType=VARCHAR}");
        }
        
        if (record.getOrderType() != null) {
            sql.VALUES("order_type", "#{orderType,jdbcType=VARCHAR}");
        }
        
        if (record.getBookTime() != null) {
            sql.VALUES("book_time", "#{bookTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSenderName() != null) {
            sql.VALUES("sender_name", "#{senderName,jdbcType=VARCHAR}");
        }
        
        if (record.getVehicleId() != null) {
            sql.VALUES("vehicle_id", "#{vehicleId,jdbcType=VARCHAR}");
        }
        
        if (record.getLicenseNo() != null) {
            sql.VALUES("license_no", "#{licenseNo,jdbcType=VARCHAR}");
        }
        
        if (record.getVehicleColor() != null) {
            sql.VALUES("vehicle_color", "#{vehicleColor,jdbcType=VARCHAR}");
        }
        
        if (record.getVehicleType() != null) {
            sql.VALUES("vehicle_type", "#{vehicleType,jdbcType=CHAR}");
        }
        
        if (record.getVehicleBrand() != null) {
            sql.VALUES("vehicle_brand", "#{vehicleBrand,jdbcType=VARCHAR}");
        }
        
        if (record.getDistance() != null) {
            sql.VALUES("distance", "#{distance,jdbcType=INTEGER}");
        }
        
        if (record.getDuration() != null) {
            sql.VALUES("duration", "#{duration,jdbcType=INTEGER}");
        }
        
        if (record.getWaitMinutes() != null) {
            sql.VALUES("wait_minutes", "#{waitMinutes,jdbcType=INTEGER}");
        }
        
        if (record.getStartLocationLatitude() != null) {
            sql.VALUES("start_location_latitude", "#{startLocationLatitude,jdbcType=DOUBLE}");
        }
        
        if (record.getStartLocationLongitude() != null) {
            sql.VALUES("start_location_longitude", "#{startLocationLongitude,jdbcType=DOUBLE}");
        }
        
        if (record.getStartTime() != null) {
            sql.VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getArriveTime() != null) {
            sql.VALUES("arrive_time", "#{arriveTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSendTime() != null) {
            sql.VALUES("send_time", "#{sendTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getReceiveTime() != null) {
            sql.VALUES("receive_time", "#{receiveTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCompleteTime() != null) {
            sql.VALUES("complete_time", "#{completeTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getNote() != null) {
            sql.VALUES("note", "#{note,jdbcType=VARCHAR}");
        }
        
        if (record.getBlockReason() != null) {
            sql.VALUES("block_reason", "#{blockReason,jdbcType=VARCHAR}");
        }
        
        if (record.getRejectReason() != null) {
            sql.VALUES("reject_reason", "#{rejectReason,jdbcType=VARCHAR}");
        }
        
        if (record.getBackReason() != null) {
            sql.VALUES("back_reason", "#{backReason,jdbcType=VARCHAR}");
        }
        
        if (record.getPlanPoints() != null) {
            sql.VALUES("plan_points", "#{planPoints,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRealPoints() != null) {
            sql.VALUES("real_points", "#{realPoints,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Orderr record) {
        SQL sql = new SQL();
        sql.UPDATE("orderr");
        
        if (record.getOrderId() != null) {
            sql.SET("order_id = #{orderId,jdbcType=VARCHAR}");
        }
        
        if (record.getSenderId() != null) {
            sql.SET("sender_id = #{senderId,jdbcType=VARCHAR}");
        }
        
        if (record.getSenderPhone() != null) {
            sql.SET("sender_phone = #{senderPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getDriverId() != null) {
            sql.SET("driver_id = #{driverId,jdbcType=VARCHAR}");
        }
        
        if (record.getDriverName() != null) {
            sql.SET("driver_name = #{driverName,jdbcType=VARCHAR}");
        }
        
        if (record.getDriverPhone() != null) {
            sql.SET("driver_phone = #{driverPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getCapacity() != null) {
            sql.SET("capacity = #{capacity,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginLocationLati() != null) {
            sql.SET("origin_location_lati = #{originLocationLati,jdbcType=DOUBLE}");
        }
        
        if (record.getOriginLocationLongi() != null) {
            sql.SET("origin_location_longi = #{originLocationLongi,jdbcType=DOUBLE}");
        }
        
        if (record.getOriginAddressTitle() != null) {
            sql.SET("origin_address_title = #{originAddressTitle,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginAddressDetail() != null) {
            sql.SET("origin_address_detail = #{originAddressDetail,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginAddressRoom() != null) {
            sql.SET("origin_address_room = #{originAddressRoom,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginPerson() != null) {
            sql.SET("origin_person = #{originPerson,jdbcType=VARCHAR}");
        }
        
        if (record.getOriginPhone() != null) {
            sql.SET("origin_phone = #{originPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationLocationLati() != null) {
            sql.SET("destination_location_lati = #{destinationLocationLati,jdbcType=DOUBLE}");
        }
        
        if (record.getDestinationLocationLongi() != null) {
            sql.SET("destination_location_longi = #{destinationLocationLongi,jdbcType=DOUBLE}");
        }
        
        if (record.getDestinationAddressTitle() != null) {
            sql.SET("destination_address_title = #{destinationAddressTitle,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationAddressDetail() != null) {
            sql.SET("destination_address_detail = #{destinationAddressDetail,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationAddressRoom() != null) {
            sql.SET("destination_address_room = #{destinationAddressRoom,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationPerson() != null) {
            sql.SET("destination_person = #{destinationPerson,jdbcType=VARCHAR}");
        }
        
        if (record.getDestinationPhone() != null) {
            sql.SET("destination_phone = #{destinationPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getMileagePrice() != null) {
            sql.SET("mileage_price = #{mileagePrice,jdbcType=DECIMAL}");
        }
        
        if (record.getWaitPrice() != null) {
            sql.SET("wait_price = #{waitPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getTotalPrice() != null) {
            sql.SET("total_price = #{totalPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getDeliverType() != null) {
            sql.SET("deliver_type = #{deliverType,jdbcType=VARCHAR}");
        }
        
        if (record.getOrderType() != null) {
            sql.SET("order_type = #{orderType,jdbcType=VARCHAR}");
        }
        
        if (record.getBookTime() != null) {
            sql.SET("book_time = #{bookTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSenderName() != null) {
            sql.SET("sender_name = #{senderName,jdbcType=VARCHAR}");
        }
        
        if (record.getVehicleId() != null) {
            sql.SET("vehicle_id = #{vehicleId,jdbcType=VARCHAR}");
        }
        
        if (record.getLicenseNo() != null) {
            sql.SET("license_no = #{licenseNo,jdbcType=VARCHAR}");
        }
        
        if (record.getVehicleColor() != null) {
            sql.SET("vehicle_color = #{vehicleColor,jdbcType=VARCHAR}");
        }
        
        if (record.getVehicleType() != null) {
            sql.SET("vehicle_type = #{vehicleType,jdbcType=CHAR}");
        }
        
        if (record.getVehicleBrand() != null) {
            sql.SET("vehicle_brand = #{vehicleBrand,jdbcType=VARCHAR}");
        }
        
        if (record.getDistance() != null) {
            sql.SET("distance = #{distance,jdbcType=INTEGER}");
        }
        
        if (record.getDuration() != null) {
            sql.SET("duration = #{duration,jdbcType=INTEGER}");
        }
        
        if (record.getWaitMinutes() != null) {
            sql.SET("wait_minutes = #{waitMinutes,jdbcType=INTEGER}");
        }
        
        if (record.getStartLocationLatitude() != null) {
            sql.SET("start_location_latitude = #{startLocationLatitude,jdbcType=DOUBLE}");
        }
        
        if (record.getStartLocationLongitude() != null) {
            sql.SET("start_location_longitude = #{startLocationLongitude,jdbcType=DOUBLE}");
        }
        
        if (record.getStartTime() != null) {
            sql.SET("start_time = #{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getArriveTime() != null) {
            sql.SET("arrive_time = #{arriveTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSendTime() != null) {
            sql.SET("send_time = #{sendTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getReceiveTime() != null) {
            sql.SET("receive_time = #{receiveTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCompleteTime() != null) {
            sql.SET("complete_time = #{completeTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getNote() != null) {
            sql.SET("note = #{note,jdbcType=VARCHAR}");
        }
        
        if (record.getBlockReason() != null) {
            sql.SET("block_reason = #{blockReason,jdbcType=VARCHAR}");
        }
        
        if (record.getRejectReason() != null) {
            sql.SET("reject_reason = #{rejectReason,jdbcType=VARCHAR}");
        }
        
        if (record.getBackReason() != null) {
            sql.SET("back_reason = #{backReason,jdbcType=VARCHAR}");
        }
        
        if (record.getPlanPoints() != null) {
            sql.SET("plan_points = #{planPoints,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRealPoints() != null) {
            sql.SET("real_points = #{realPoints,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }



    // add by myself
    public String updateByOrderIdSelective(Orderr record) {
        SQL sql = new SQL();
        sql.UPDATE("orderr");

        if (record.getOrderId() != null) {
            sql.SET("order_id = #{orderId,jdbcType=VARCHAR}");
        }

        if (record.getSenderId() != null) {
            sql.SET("sender_id = #{senderId,jdbcType=VARCHAR}");
        }

        if (record.getSenderPhone() != null) {
            sql.SET("sender_phone = #{senderPhone,jdbcType=VARCHAR}");
        }

        if (record.getDriverId() != null) {
            sql.SET("driver_id = #{driverId,jdbcType=VARCHAR}");
        }

        if (record.getDriverName() != null) {
            sql.SET("driver_name = #{driverName,jdbcType=VARCHAR}");
        }

        if (record.getDriverPhone() != null) {
            sql.SET("driver_phone = #{driverPhone,jdbcType=VARCHAR}");
        }

        if (record.getCapacity() != null) {
            sql.SET("capacity = #{capacity,jdbcType=INTEGER}");
        }

        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=VARCHAR}");
        }

        if (record.getOriginLocationLati() != null) {
            sql.SET("origin_location_lati = #{originLocationLati,jdbcType=DOUBLE}");
        }

        if (record.getOriginLocationLongi() != null) {
            sql.SET("origin_location_longi = #{originLocationLongi,jdbcType=DOUBLE}");
        }

        if (record.getOriginAddressTitle() != null) {
            sql.SET("origin_address_title = #{originAddressTitle,jdbcType=VARCHAR}");
        }

        if (record.getOriginAddressDetail() != null) {
            sql.SET("origin_address_detail = #{originAddressDetail,jdbcType=VARCHAR}");
        }

        if (record.getOriginAddressRoom() != null) {
            sql.SET("origin_address_room = #{originAddressRoom,jdbcType=VARCHAR}");
        }

        if (record.getOriginPerson() != null) {
            sql.SET("origin_person = #{originPerson,jdbcType=VARCHAR}");
        }

        if (record.getOriginPhone() != null) {
            sql.SET("origin_phone = #{originPhone,jdbcType=VARCHAR}");
        }

        if (record.getDestinationLocationLati() != null) {
            sql.SET("destination_location_lati = #{destinationLocationLati,jdbcType=DOUBLE}");
        }

        if (record.getDestinationLocationLongi() != null) {
            sql.SET("destination_location_longi = #{destinationLocationLongi,jdbcType=DOUBLE}");
        }

        if (record.getDestinationAddressTitle() != null) {
            sql.SET("destination_address_title = #{destinationAddressTitle,jdbcType=VARCHAR}");
        }

        if (record.getDestinationAddressDetail() != null) {
            sql.SET("destination_address_detail = #{destinationAddressDetail,jdbcType=VARCHAR}");
        }

        if (record.getDestinationAddressRoom() != null) {
            sql.SET("destination_address_room = #{destinationAddressRoom,jdbcType=VARCHAR}");
        }

        if (record.getDestinationPerson() != null) {
            sql.SET("destination_person = #{destinationPerson,jdbcType=VARCHAR}");
        }

        if (record.getDestinationPhone() != null) {
            sql.SET("destination_phone = #{destinationPhone,jdbcType=VARCHAR}");
        }

        if (record.getMileagePrice() != null) {
            sql.SET("mileage_price = #{mileagePrice,jdbcType=DECIMAL}");
        }

        if (record.getWaitPrice() != null) {
            sql.SET("wait_price = #{waitPrice,jdbcType=DECIMAL}");
        }

        if (record.getTotalPrice() != null) {
            sql.SET("total_price = #{totalPrice,jdbcType=DECIMAL}");
        }

        if (record.getDeliverType() != null) {
            sql.SET("deliver_type = #{deliverType,jdbcType=VARCHAR}");
        }

        if (record.getOrderType() != null) {
            sql.SET("order_type = #{orderType,jdbcType=VARCHAR}");
        }

        if (record.getBookTime() != null) {
            sql.SET("book_time = #{bookTime,jdbcType=TIMESTAMP}");
        }

        if (record.getSenderName() != null) {
            sql.SET("sender_name = #{senderName,jdbcType=VARCHAR}");
        }

        if (record.getVehicleId() != null) {
            sql.SET("vehicle_id = #{vehicleId,jdbcType=VARCHAR}");
        }

        if (record.getLicenseNo() != null) {
            sql.SET("license_no = #{licenseNo,jdbcType=VARCHAR}");
        }

        if (record.getVehicleColor() != null) {
            sql.SET("vehicle_color = #{vehicleColor,jdbcType=VARCHAR}");
        }

        if (record.getVehicleType() != null) {
            sql.SET("vehicle_type = #{vehicleType,jdbcType=CHAR}");
        }

        if (record.getVehicleBrand() != null) {
            sql.SET("vehicle_brand = #{vehicleBrand,jdbcType=VARCHAR}");
        }

        if (record.getDistance() != null) {
            sql.SET("distance = #{distance,jdbcType=INTEGER}");
        }

        if (record.getDuration() != null) {
            sql.SET("duration = #{duration,jdbcType=INTEGER}");
        }

        if (record.getWaitMinutes() != null) {
            sql.SET("wait_minutes = #{waitMinutes,jdbcType=INTEGER}");
        }

        if (record.getStartLocationLatitude() != null) {
            sql.SET("start_location_latitude = #{startLocationLatitude,jdbcType=DOUBLE}");
        }

        if (record.getStartLocationLongitude() != null) {
            sql.SET("start_location_longitude = #{startLocationLongitude,jdbcType=DOUBLE}");
        }

        if (record.getStartTime() != null) {
            sql.SET("start_time = #{startTime,jdbcType=TIMESTAMP}");
        }

        if (record.getArriveTime() != null) {
            sql.SET("arrive_time = #{arriveTime,jdbcType=TIMESTAMP}");
        }

        if (record.getSendTime() != null) {
            sql.SET("send_time = #{sendTime,jdbcType=TIMESTAMP}");
        }

        if (record.getReceiveTime() != null) {
            sql.SET("receive_time = #{receiveTime,jdbcType=TIMESTAMP}");
        }

        if (record.getCompleteTime() != null) {
            sql.SET("complete_time = #{completeTime,jdbcType=TIMESTAMP}");
        }

        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }

        if (record.getNote() != null) {
            sql.SET("note = #{note,jdbcType=VARCHAR}");
        }

        if (record.getBlockReason() != null) {
            sql.SET("block_reason = #{blockReason,jdbcType=VARCHAR}");
        }

        if (record.getRejectReason() != null) {
            sql.SET("reject_reason = #{rejectReason,jdbcType=VARCHAR}");
        }

        if (record.getBackReason() != null) {
            sql.SET("back_reason = #{backReason,jdbcType=VARCHAR}");
        }

        if (record.getPlanPoints() != null) {
            sql.SET("plan_points = #{planPoints,jdbcType=LONGVARCHAR}");
        }

        if (record.getRealPoints() != null) {
            sql.SET("real_points = #{realPoints,jdbcType=LONGVARCHAR}");
        }

        sql.WHERE("order_id = #{orderId,jdbcType=VARCHAR}");

        return sql.toString();
    }

    public String selectList(ListQueryOption queryOption) {
        SQL sql = new ListQuerySqlBuilder().build("orderr", queryOption);
        return sql.toString();
    }
}