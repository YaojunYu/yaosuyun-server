package com.yao.express.service.user.api.manager;

import com.yao.express.service.user.response.AppResponse;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.AppVersion;
import com.yao.express.service.user.entity.Feedback;
import com.yao.express.service.user.services.AppService;
import com.yao.express.service.user.services.FeedbackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("managers/feedbacks")
public class FeedbackMngController {

    @Resource
    private FeedbackService feedbackService;

    @GetMapping
    public AppResponse<PageInfo<Feedback>> getFeedbacks(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size,
                                                          @RequestParam(required = false) List<String> between,
                                                          // 如"+id,-createTime,+status"
                                                          @RequestParam(required = false) String sorts,
                                                          @RequestParam(required = false) String keyword) {
        ListQueryOption listQueryOption = ListQueryOption.build()
                .page(page).size(size)
                .between(between)
                .sorts(sorts)
                .search(keyword, Arrays.asList("app", "description"));

        return feedbackService.getManagerFeedbackList(listQueryOption);
    }
}
