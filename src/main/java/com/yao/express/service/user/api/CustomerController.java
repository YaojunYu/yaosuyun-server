package com.yao.express.service.user.api;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.dto.*;
import com.yao.express.service.user.services.AccountService;
import com.yao.express.service.user.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Resource
    private CustomerService customerService;
    @Resource
    private AccountService accountService;

    @PostMapping("/phones")
    public AppResponse<Boolean> updatePhone(@RequestBody UpdatePhoneReq updatePhoneReq) {
        return customerService.updatePhone(updatePhoneReq);
    }

    @PostMapping("/register")
    public AppResponse<Boolean> register(@RequestBody RegisterDTO customer) {
        return customerService.register(customer);
    }

    @PostMapping("/login")
    public AppResponse<LoginUser> login(@RequestBody AccLoginDTO loginDTO,
                                         HttpServletRequest req,
                                         HttpServletResponse resp) {
        String sessionId = req.getSession().getId();
        resp.setHeader("Set-Token", sessionId);

        return customerService.login(loginDTO, sessionId);
    }

    @PostMapping("/logout")
    public AppResponse<Boolean> logout() {
        return accountService.logout();
    }

    @GetMapping("/token")
    public AppResponse<String> tokenRefresh() {
        return accountService.getToken();
    }

    @PostMapping("/passwords/forgot")
    public AppResponse<Boolean> forgot(@RequestBody ForgotDTO forgotDTO) {
        return accountService.forgot(forgotDTO);
    }

    @PostMapping("/passwords/change")
    public AppResponse<Boolean> changePassword(@RequestBody ChangePwdDTO changePwdDTO) {
        return accountService.changePwd(changePwdDTO);
    }


    @PostMapping("/info/update")
    public AppResponse<Boolean> updateInfo(@RequestBody LoginUser loginUser) {
        return customerService.updateInfo(loginUser);
    }
}
