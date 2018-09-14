package com.yao.express.service.user.api.manager;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.util.login.UserLoginUtils;
import com.yao.express.service.user.dto.AccLoginDTO;
import com.yao.express.service.user.dto.LoginUser;
import com.yao.express.service.user.services.ManagerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("managers")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    @PostMapping("login")
    public AppResponse<LoginUser> login(@RequestBody AccLoginDTO loginDTO,
                                        HttpServletRequest req,
                                        HttpServletResponse resp) {
        String sessionId = req.getSession().getId();
        resp.setHeader("Set-Token", sessionId);

        return managerService.login(loginDTO, sessionId);
    }

    @GetMapping("info")
    public AppResponse<LoginUser> getLoginUser() {
        LoginUser user = (LoginUser) UserLoginUtils.getCurrentUser();
        if (StringUtils.isBlank(user.getPhoto())) {
            user.setPhoto("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"); // FIXME
        }
        return AppResponse.success(UserLoginUtils.getCurrentUser());
    }
}
