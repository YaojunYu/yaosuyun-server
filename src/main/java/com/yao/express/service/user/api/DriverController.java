package com.yao.express.service.user.api;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.dto.*;
import com.yao.express.service.user.services.AccountService;
import com.yao.express.service.user.services.DriverService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Resource
    private DriverService driverService;
    @Resource
    private AccountService accountService;

    @GetMapping("/points")
    public AppResponse<List<Double[]>> getAllDriverPoints(@RequestParam double lati,
                                                          @RequestParam double longi,
                                                          @RequestParam(required = false) Double radius,
                                                          @RequestParam(required = false) String center) {
        Long count = 30L;
        radius = (null == radius) ? 100 : radius;
        List<Double[]> points = driverService.getAvailabelDriversLocs(
                lati, longi, center, radius, count);
        return AppResponse.success(points);
    }

    @GetMapping("/{id}/points")
    public AppResponse<Double[]> getCurrentDriverPoint(@PathVariable String id) {
        return driverService.getCurrentDriverPoint(id);
    }

    @PostMapping("register")
    public AppResponse<Boolean> register(@RequestBody RegisterDTO driver) {
        return driverService.register(driver);
    }

    @PostMapping("/login")
    public  AppResponse<LoginUser> login(@RequestBody AccLoginDTO loginDTO,
                                         HttpServletRequest req,
                                         HttpServletResponse resp) {
        String sessionId = req.getSession().getId();
        resp.setHeader("Set-Token", sessionId);

        return driverService.login(loginDTO, sessionId);
    }

    @PostMapping("/logout")
    public AppResponse<Boolean> logout() {
        return accountService.logout();
    }

    @PostMapping("/passwords")
    public AppResponse<Boolean> updatePassword(@RequestBody UpdatePwdRequest updatePwdRequest) {
        return driverService.updatePassword(updatePwdRequest);
    }

    @PostMapping("/status/{status}")
    public AppResponse<Boolean> updateDriverStatus(@PathVariable String status) {
        return driverService.updateDriverStatus(status);
    }

    @GetMapping("/status/available")
    public AppResponse<Boolean> getDriverAvailable() {
        return driverService.getDriverAvailableStatus();
    }
}
