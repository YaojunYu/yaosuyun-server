package com.yao.express.service.user.services;

import com.yao.express.service.user.dto.AccLoginDTO;
import com.yao.express.service.user.dto.LoginUser;
import com.yao.express.service.user.entity.Manager;
import com.yao.express.service.user.entity.ManagerAccount;
import com.yao.express.service.user.entity.UserLogin;
import com.yao.express.service.user.enums.AccountRoleEnum;
import com.yao.express.service.user.enums.AccountStatusEnum;
import com.yao.express.service.user.enums.AccountTypeEnum;
import com.yao.express.service.user.enums.ClientTypeEnum;
import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.mapper.ManagerAccountMapper;
import com.yao.express.service.user.mapper.ManagerMapper;
import com.yao.express.service.user.mapper.UserLoginMapper;
import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.util.LocalHost;
import com.yao.express.service.user.util.SHAEncryptUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ManagerService {
    @Resource
    private ManagerAccountMapper managerAccountMapper;
    @Resource
    private ManagerMapper managerMapper;
    @Resource
    private UserLoginMapper userLoginMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${session.expire.time}")
    private Long sessionTime;

    public AppResponse<LoginUser> login(AccLoginDTO loginDTO, String sessionId) {
        if(null == loginDTO || StringUtils.isEmpty(loginDTO.getAccount())
                || StringUtils.isEmpty(loginDTO.getPassword())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        String password = SHAEncryptUtils.encryptSHA(loginDTO.getPassword());
        ManagerAccount managerAccount = managerAccountMapper.selectByAccount(loginDTO.getAccount());
        if(null == managerAccount ||
                !password.equals(managerAccount.getPassword())) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_LOGIN_FAIL.code,
                    ResponseErrorCode.ACCOUNT_LOGIN_FAIL.msg, null);
        } else if (!AccountStatusEnum.NORMAL.value.equals(managerAccount.getStatus())) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_NOT_NORMAL.code,
                    ResponseErrorCode.ACCOUNT_NOT_NORMAL.msg, null);
        }

        // 记录登录信息
        UserLogin userLogin = new UserLogin();
        userLogin.setUserLoginId(UUID.randomUUID().toString());
        userLogin.setAccount(managerAccount.getAccount());
        userLogin.setAccountType(AccountTypeEnum.MANAGER.value);
        userLogin.setClientType(ClientTypeEnum.WEB_APP.value); // FIXME
        userLogin.setLoginIp(LocalHost.getLocalIP());

        userLogin.setLoginTime(new Date());
        if (userLoginMapper.insertSelective(userLogin) <= 0) {
            throw new RuntimeException();
        }

        // 登录成功，保存redis
        Manager manager = managerMapper.selectByManagerId(managerAccount.getManagerId());
        LoginUser loginUser = new LoginUser();
        loginUser.setAccount(managerAccount.getAccount());
        loginUser.setUid(manager.getManagerId());
        loginUser.setToken(sessionId);
        loginUser.setRoles(Arrays.asList(AccountRoleEnum.MANAGER.value));
        if(null != manager) {
            loginUser.setName(manager.getName());
//            loginUser.setPhoto(manager.getPhoto());
            loginUser.setSex(manager.getSex());
            // FIXME
        }
        sessionTime = (null == sessionTime) ? -1 : sessionTime;
        redisTemplate.opsForValue().set("session:"+sessionId, loginUser, sessionTime, TimeUnit.MILLISECONDS);

        return AppResponse.success(loginUser);
    }
}
