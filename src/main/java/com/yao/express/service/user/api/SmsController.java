package com.yao.express.service.user.api;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.services.SmsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("sms")
public class SmsController {

    @Resource
    private SmsService smsService;

    @PostMapping("/send/{phone}")
    public AppResponse<Boolean> sendSMSCode(@PathVariable String phone) {
        return smsService.sendVcode(phone);
    }

}
