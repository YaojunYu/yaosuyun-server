package com.yao.express.service.user.api;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.dto.AppUpdateLog;
import com.yao.express.service.user.dto.AppVersionCheckResult;
import com.yao.express.service.user.services.AppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("apps")
public class AppController {

    @Resource
    private AppService appService;

    @GetMapping("/versions/latest")
    public AppResponse<AppVersionCheckResult> checkLatestVersion(@RequestParam String pkg,
                                                          @RequestParam String type) {
        return appService.getLatestAppVersion(pkg, type);
    }

    @GetMapping("/versions/logs")
    public AppResponse<AppUpdateLog> getAppUpdateLogs(@RequestParam String pkg,
                                                      @RequestParam String type) {
        return appService.getAppUpdateLogs(pkg, type);
    }
}
