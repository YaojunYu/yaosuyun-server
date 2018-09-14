package com.yao.express.service.user.api.manager;

import com.yao.express.service.user.response.AppResponse;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Customer;
import com.yao.express.service.user.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("managers/customers")
public class CustomerMngController {

    @Resource
    private CustomerService customerService;

    @GetMapping
    public AppResponse<PageInfo<Customer>> getCustomers(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) List<String> between,
                                                        // 如"+id,-createTime"
                                                        @RequestParam(required = false) String sorts,
                                                        @RequestParam(required = false) String keyword) {

        ListQueryOption listQueryOption = ListQueryOption.build()
                .page(page).size(size)
                .between(between)
                .sorts(sorts)
                .search(keyword, Arrays.asList("name", "phone"));

        return customerService.getManagerCustomerList(listQueryOption);
    }
}
