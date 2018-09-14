package com.yao.express.service.user.api;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.dto.LoginUser;
import com.yao.express.service.user.dto.WxLoginDTO;
import com.yao.express.service.user.services.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/wx")
public class WxController {

    @Resource
    private AccountService accountService;

    @PostMapping("/login")
    public AppResponse<LoginUser> postCode(@RequestBody WxLoginDTO wxLoginDTO,
                                           HttpServletRequest req,
                                           HttpServletResponse resp) {
        String sessionId = req.getSession().getId();
        resp.setHeader("Set-Token", sessionId);

        return accountService.wxLogin(wxLoginDTO, sessionId);

    }
}
