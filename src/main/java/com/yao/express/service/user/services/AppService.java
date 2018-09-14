package com.yao.express.service.user.services;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.AppUpdateLog;
import com.yao.express.service.user.dto.AppVersionCheckResult;
import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.dto.LoginUser;
import com.yao.express.service.user.entity.AppVersion;
import com.yao.express.service.user.enums.AccountRoleEnum;
import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.mapper.AppVersionMapper;
import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.util.login.UserLoginUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppService {

    @Resource
    private AppVersionMapper appVersionMapper;

    /**
     * 检查app是否有更新
     * @param pkg
     * @param type
     * @return
     */
    public AppResponse<AppVersionCheckResult> getLatestAppVersion(String pkg, String type) {
        if (StringUtils.isEmpty(pkg)) {
            return new AppResponse<>(
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg,
                    null
            );
        }
        return AppResponse.success(appVersionMapper.selectLatestAppVersion(pkg, type));
    }

    /**
     * 获取更新日志
     * @param pkg
     * @param type
     * @return
     */
    public AppResponse<AppUpdateLog> getAppUpdateLogs(String pkg, String type) {
        if (StringUtils.isEmpty(pkg)) {
            return new AppResponse<>(
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg,
                    null
            );
        }
        return AppResponse.success(appVersionMapper.selectAppVersionLogs(pkg, type));
    }

    /**
     * 获取app版本列表
     * @param queryOption
     * @return
     */
    public AppResponse<PageInfo<AppVersion>> getManagerAppVersionList(ListQueryOption queryOption) {
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
        List<AppVersion> list = appVersionMapper.selectList(queryOption);
        // 取分页信息
        PageInfo<AppVersion> pageInfo = new PageInfo(list);

        return AppResponse.success(pageInfo);
    }
}
