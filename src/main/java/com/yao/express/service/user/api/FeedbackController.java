package com.yao.express.service.user.api;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.dto.FeedbackReqDTO;
import com.yao.express.service.user.services.FeedbackService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("feedbacks")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping
    public AppResponse<Boolean> feedback(@RequestBody FeedbackReqDTO feedbackReqDTO) {
        return feedbackService.saveFeedback(feedbackReqDTO);
    }
}
