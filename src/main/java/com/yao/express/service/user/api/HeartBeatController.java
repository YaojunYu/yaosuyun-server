package com.yao.express.service.user.api;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.dto.HeartbeatMsg;
import com.yao.express.service.user.dto.HeartbeatRequest;
import com.yao.express.service.user.services.HeartbeatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/heartbeats")
public class HeartBeatController {

    @Resource
    private HeartbeatService heartbeatService;

    @PostMapping
    public AppResponse<HeartbeatMsg> heartbeat(@RequestBody HeartbeatRequest heartbeatRequest) {
        return heartbeatService.heartbeat(heartbeatRequest);
    }

}
