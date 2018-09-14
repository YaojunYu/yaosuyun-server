package com.yao.express.service.user.api.manager;

import com.yao.express.service.user.response.AppResponse;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.AppVersion;
import com.yao.express.service.user.services.AppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("managers/apps")
public class AppVersionMngController {

    @Resource
    private AppService appService;

    @GetMapping("versions")
    public AppResponse<PageInfo<AppVersion>> getAppVersions(@RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) List<String> between,
                                                            // 如"+id,-createTime,+status"
                                                            @RequestParam(required = false) String sorts,
                                                            @RequestParam(required = false) String keyword,
                                                            // 如"got,finished"
                                                            @RequestParam(required = false) String type,
                                                            @RequestParam(required = false) Boolean force) {
        ListQueryOption listQueryOption = ListQueryOption.build()
                .page(page).size(size)
                .between(between)
                .sorts(sorts)
                .eq("type", type)
                .search(keyword, Arrays.asList("pkg", "name"));
        if (force != null) {
            listQueryOption.eq("is_force", force?"1":"0");
        }

        return appService.getManagerAppVersionList(listQueryOption);
    }
}
