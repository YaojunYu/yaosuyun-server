package com.yao.express.service.user.api.manager;

import com.yao.express.service.user.response.AppResponse;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Orderr;
import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.services.OrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("managers/orders")
public class OrderMngController {

    @Resource
    private OrderService orderService;

    @GetMapping
    public AppResponse<PageInfo<Orderr>> getOrders(@RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size,
                                                   @RequestParam(required = false) List<String> between,
                                                   // 如"+id,-createTime,+status"
                                                   @RequestParam(required = false) String sorts,
                                                   @RequestParam(required = false) String keyword,
                                                   // 如"got,finished"
                                                   @RequestParam(required = false) List<String> status) {
        ListQueryOption listQueryOption = ListQueryOption.build()
                .page(page).size(size)
                .between(between)
                .sorts(sorts)
                .ins("status", status)
                .search(keyword, Arrays.asList("order_id", "origin_person", "origin_phone", "driver_name",
                        "destination_person", "destination_phone", "origin_address_title", "driver_phone",
                        "destination_address_title", "origin_address_detail", "destination_address_detail"));

        return orderService.getManagerOrderList(listQueryOption);
    }
}
