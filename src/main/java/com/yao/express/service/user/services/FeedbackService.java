package com.yao.express.service.user.services;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.FeedbackReqDTO;
import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.dto.LoginUser;
import com.yao.express.service.user.entity.Feedback;
import com.yao.express.service.user.enums.AccountRoleEnum;
import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.mapper.FeedbackMapper;
import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.util.login.UserLoginUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    /**
     * 反馈信息
     * @param feedbackReqDTO
     * @return
     */
    public AppResponse<Boolean> saveFeedback(FeedbackReqDTO feedbackReqDTO) {
        if (null == feedbackReqDTO || StringUtils.isEmpty(feedbackReqDTO.getDesc()) ||
                StringUtils.isEmpty(feedbackReqDTO.getPkg()) ||
                StringUtils.isEmpty(feedbackReqDTO.getVersion())) {
            return new AppResponse<>(
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg,
                    null
            );
        }

        // 保存反馈
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        String user = loginUser.getAccount()+":"+loginUser.getName();
        Feedback feedback = new Feedback();
        feedback.setApp(feedbackReqDTO.getPkg().concat("@").concat(feedbackReqDTO.getVersion()));
        feedback.setDescption(feedbackReqDTO.getDesc());
        feedback.setUser(user);
        int i = feedbackMapper.insertSelective(feedback);

        return AppResponse.success(i > 0);
    }

    /**
     * 获取反馈列表
     * @param queryOption
     * @return
     */
    public AppResponse<PageInfo<Feedback>> getManagerFeedbackList(ListQueryOption queryOption) {
        if (null == queryOption) { // 默认
            queryOption = new ListQueryOption();
            queryOption.setPage(1);
            queryOption.setSize(10);
        }
        if (queryOption.getPage()<=0) {
            queryOption.setPage(1);
        }
        if (queryOption.getSize()<=0) {
            queryOption.setSize(10);
        }


        // 只有管理员可以查看
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        if (!loginUser.getRoles().contains(AccountRoleEnum.MANAGER.value)) {  // FIXME
            return new AppResponse<>(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                    ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, null);
        }

        // 分页
        int pageNum = queryOption.getPage();
        int pageSize = queryOption.getSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Feedback> list = feedbackMapper.selectList(queryOption);
        // 取分页信息
        PageInfo<Feedback> pageInfo = new PageInfo(list);

        return AppResponse.success(pageInfo);
    }
}
