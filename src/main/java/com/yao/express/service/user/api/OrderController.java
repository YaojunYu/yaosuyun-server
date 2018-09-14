package com.yao.express.service.user.api;

import com.yao.express.service.user.response.AppResponse;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.CustomerOrderListItem;
import com.yao.express.service.user.dto.DriverOrderListItem;
import com.yao.express.service.user.dto.OrderRequest;
import com.yao.express.service.user.entity.Orderr;
import com.yao.express.service.user.services.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 下单接口
     * @param orderRequest
     * @return
     */
    @PostMapping
    public AppResponse<Orderr> order(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.order(orderRequest);
    }

    @GetMapping("/prices")
    public AppResponse<Map> calculateOrderPrice(@RequestParam(required = false) Double fromLati,
                                                @RequestParam(required = false) Double fromLongi,
                                                @RequestParam(required = false) Double toLati,
                                                @RequestParam(required = false) Double toLongi,
                                                @RequestParam(required = false) Integer capacity,
                                                @RequestParam(required = false) String deliverType,
                                                @RequestParam(required = false) Date bookTime) {

        return orderService.calculateOrderPrice(fromLati, fromLongi, toLati, toLongi, capacity, deliverType, bookTime);
    }

    @GetMapping("/{orderId}")
    public AppResponse<Orderr> getOrderDetail(@PathVariable String orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @GetMapping("/customer")
    public AppResponse<PageInfo<CustomerOrderListItem>> getCustomerOrders(@RequestParam String customerId,
                                                                          @RequestParam List<String> status,
                                                                          @RequestParam int pageNum,
                                                                          @RequestParam int pageSize) {
        return orderService.getCustomerOrders(customerId, status, pageNum, pageSize);
    }

    @PostMapping("/{orderId}/cancel")
    public AppResponse<Boolean> cancelOrder(@PathVariable String orderId) {

        return orderService.cancelOrder(orderId);
    }

    @PostMapping("/{orderId}/delete")
    public AppResponse<Boolean> deleteOrder(@PathVariable String orderId) {

        return orderService.deleteOrder(orderId);
    }

    @PostMapping("/{orderId}/back")
    public AppResponse<Boolean> backOrder(@PathVariable String orderId) {

        return orderService.backOrder(orderId);
    }

    @PostMapping("/{orderId}/unback")
    public AppResponse<Boolean> unbackOrder(@PathVariable String orderId) {

        return orderService.unbackOrder(orderId);
    }

    @GetMapping("/driver")
    public AppResponse<PageInfo<DriverOrderListItem>> getDriverOrders(@RequestParam String driverId,
                                                                      @RequestParam List<String> status,
                                                                      @RequestParam int pageNum,
                                                                      @RequestParam int pageSize) {
        return orderService.getDriverOrders(driverId, status, pageNum, pageSize);
    }

    /**
     * 订单状态流转
     * @return
     */
    @PostMapping("/{orderId}/status/{status}")
    public AppResponse<Boolean> updateOrder(@PathVariable String orderId,
                                            @PathVariable String status,
                                            // 输入原因
                                            @RequestBody(required = false) String reason) {
        return orderService.updateOrderStatus(orderId, status, reason);
    }

}
