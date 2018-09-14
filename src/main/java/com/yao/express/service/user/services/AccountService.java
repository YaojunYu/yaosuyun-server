package com.yao.express.service.user.services;

import com.alibaba.fastjson.JSON;
import com.yao.express.service.user.conf.WxMiniConfig;
import com.yao.express.service.user.dto.ChangePwdDTO;
import com.yao.express.service.user.dto.ForgotDTO;
import com.yao.express.service.user.dto.LoginUser;
import com.yao.express.service.user.dto.WxLoginDTO;
import com.yao.express.service.user.entity.Account;
import com.yao.express.service.user.entity.Customer;
import com.yao.express.service.user.entity.UserLogin;
import com.yao.express.service.user.enums.AccountStatusEnum;
import com.yao.express.service.user.enums.AccountTypeEnum;
import com.yao.express.service.user.enums.ClientTypeEnum;
import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.mapper.AccountMapper;
import com.yao.express.service.user.mapper.CustomerMapper;
import com.yao.express.service.user.mapper.UserLoginMapper;
import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.util.LocalHost;
import com.yao.express.service.user.util.SHAEncryptUtils;
import com.yao.express.service.user.util.http.HttpUtils;
import com.yao.express.service.user.util.login.UserLoginUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.yao.express.service.user.exception.ResponseErrorCode.REQUEST_PARAMS_INVALID;
import static com.yao.express.service.user.exception.ResponseErrorCode.WX_CODE_2_SESSION_CODE_EMPTY;

@Service
public class AccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SmsService smsService;
    @Resource
    private WxMiniConfig wxMiniConfig;
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private UserLoginMapper userLoginMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${session.expire.time}")
    private Long sessionTime;

    /**
     * 微信小程序登录流程：
     *      请求微信服务器获取openId等信息；
     *      判断是否已经注册，否则注册；
     *      记录登录信息；
     *
     * @param wxLoginDTO
     * @return
     */
    public AppResponse<LoginUser> wxLogin(WxLoginDTO wxLoginDTO, String sessionId) {
        logger.info("postCode params wxLoginDTO:{}", JSON.toJSONString(wxLoginDTO));
        // 入参校验
        if (null == wxLoginDTO) {
            return new AppResponse(REQUEST_PARAMS_INVALID.code,
                    REQUEST_PARAMS_INVALID.msg, null);
        }
        if(StringUtils.isBlank(wxLoginDTO.getCode())) {
            return new AppResponse(WX_CODE_2_SESSION_CODE_EMPTY.code,
                    WX_CODE_2_SESSION_CODE_EMPTY.msg, null);
        }

        // 请求微信服务器获取openId等信息
        logger.info("wxConfig={}", JSON.toJSONString(wxMiniConfig));
        String url = wxMiniConfig.getUrl();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("appid", wxMiniConfig.getAppid());
        paramsMap.put("secret", wxMiniConfig.getSecret());
        paramsMap.put("grant_type", "authorization_code");
        paramsMap.put("js_code", wxLoginDTO.getCode());

        String body = HttpUtils.get(url, null, paramsMap);
        if (StringUtils.isNotBlank(body)) {
            String sessionKey = (String) JSON.parseObject(body).get("session_key");
            String openId = (String) JSON.parseObject(body).get("openid");
            logger.info("response entity = {}", body);

            // 检测openId是否已经注册
            return checkUserLogin(wxLoginDTO, sessionId, sessionKey, openId);
        } else {
            logger.warn("wx code2session get request response failed : {}", body);
            return new AppResponse(ResponseErrorCode.WX_CODE_2_SESSION_FAILED.code,
                    ResponseErrorCode.WX_CODE_2_SESSION_FAILED.msg, null);
        }
    }

    @Transactional
    public AppResponse<LoginUser> checkUserLogin(WxLoginDTO wxLoginDTO, String sessionId, String sessionKey, String openId) {
        if (StringUtils.isBlank(openId)) {
            return AppResponse.ERROR;
        }
        Account account = accountMapper.selectByAccount(openId);
        String customerId = null;
        Customer customer = null;

        if (null == account) {
            // 注册账号未注册进行注册
            customerId = UUID.randomUUID().toString();
            account = new Account();
            account.setAccount(openId);
            account.setLastLogin(new Date());
            account.setStatus(AccountStatusEnum.NORMAL.value);
            account.setWxNo(openId);
            account.setCustomerId(customerId);
            int id = accountMapper.insertSelective(account);
            logger.info("accountMapper insert result = {}", id);
            logger.info("insert account = {}", JSON.toJSONString(account));
            if (id <= 0) {
                throw new RuntimeException();
            }

            customer = new Customer();
            customer.setCustomerId(customerId);
            if (null != wxLoginDTO.getLocation()) {
                customer.setLatitude(wxLoginDTO.getLocation().getLatitude());
                customer.setLongitude(wxLoginDTO.getLocation().getLongitude());
            }
            if (null != wxLoginDTO.getRes() && null != wxLoginDTO.getRes().getUserInfo()) {
                customer.setName(wxLoginDTO.getRes().getUserInfo().getNickName());
                customer.setPhoto(wxLoginDTO.getRes().getUserInfo().getAvatarUrl());
                String sex = wxLoginDTO.getRes().getUserInfo().getGender() == 1 ? "m" : "f";
                customer.setSex(sex);
            }
            id  = customerMapper.insertSelective(customer);
            if (id <= 0) {
                throw new RuntimeException();
            }
        } else {
            if(!AccountStatusEnum.NORMAL.value.equals(account.getStatus())) {
                return new AppResponse<>(ResponseErrorCode.ACCOUNT_NOT_NORMAL.code,
                        ResponseErrorCode.ACCOUNT_NOT_NORMAL.msg, null);
            }
            // 账号已经注册，记录登录信息
            customerId = account.getCustomerId();
            customer = customerMapper.selectByCustomerId(customerId);
            long accountId = account.getId();
            account = new Account();
            account.setId(accountId);
            account.setLastLogin(new Date());
            int id = accountMapper.updateByPrimaryKeySelective(account);
            if (id <= 0) {
                throw new RuntimeException();
            }
        }
        // 记录登录信息
        UserLogin userLogin = new UserLogin();
        userLogin.setUserLoginId(UUID.randomUUID().toString());
        userLogin.setAccount(openId);
        userLogin.setAccountType(AccountTypeEnum.CUSTOMER.value);
        userLogin.setClientType(ClientTypeEnum.WX_MINI.value);
        userLogin.setLoginIp(LocalHost.getLocalIP());
        if (null != wxLoginDTO.getLocation()) {
            userLogin.setLoginLatitude(wxLoginDTO.getLocation().getLatitude());
            userLogin.setLoginLongitude(wxLoginDTO.getLocation().getLongitude());
        }
        userLogin.setLoginTime(new Date());

        if (userLoginMapper.insertSelective(userLogin) <= 0) {
            throw new RuntimeException();
        }

        // 返回用户信息
        LoginUser loginUser = new LoginUser();
        logger.info("customer={}", customer);
        loginUser.setUid(customerId);
        loginUser.setAccount(openId);
        if (null != customer) {
            loginUser.setName(customer.getName());
            loginUser.setPhone(customer.getPhone());
            loginUser.setPhoto(customer.getPhoto());
            loginUser.setSex(customer.getSex());
        }

        // 保存登录状态
        // TODO 以sessionId为key，sessionKey+openId为value存储
        redisTemplate.opsForValue().set("wx:"+sessionId, sessionKey+":"+openId);
        sessionTime = (null == sessionTime) ? -1 : sessionTime;
        redisTemplate.opsForValue().set("session:"+sessionId, loginUser, sessionTime, TimeUnit.MILLISECONDS);

        logger.info("return user:{}", loginUser);
        return AppResponse.success(loginUser);
    }

    /**
     * 注销登陆
     * @return
     */
    public AppResponse<Boolean> logout() {
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        String token = loginUser.getToken();
        return AppResponse.success(this.redisTemplate.delete("session:"+token));
    }

    /**
     * 获取token
     * @return
     */
    public AppResponse<String> getToken() {
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        String token = loginUser.getToken();
        return AppResponse.success(token);
    }

    /**
     * 忘记密码
     * @param forgotDTO
     * @return
     */
    public AppResponse<Boolean> forgot(ForgotDTO forgotDTO) {
        if (null == forgotDTO || StringUtils.isBlank(forgotDTO.getPhone())
                || StringUtils.isEmpty(forgotDTO.getNewpwd())
                || StringUtils.isEmpty(forgotDTO.getConfirm())
                || StringUtils.isEmpty(forgotDTO.getVcode())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, false);
        }

        // 检测验证码是否正确
        boolean codeIsValid = smsService.verifyVcode(forgotDTO.getPhone(), forgotDTO.getVcode());
        if (!codeIsValid) {
            return new AppResponse<>(ResponseErrorCode.VCODE_NOT_VALID.code,
                    ResponseErrorCode.VCODE_NOT_VALID.msg, false);
        }
        // 检测账号是否已经注册
        Account customerAccount = accountMapper.selectByAccount(forgotDTO.getPhone());
        if (null == customerAccount) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_NOT_FOUND.code,
                    ResponseErrorCode.ACCOUNT_NOT_FOUND.msg, false);
        } else {
            if (!AccountStatusEnum.NORMAL.value.equals(customerAccount.getStatus())) {
                return new AppResponse<>(ResponseErrorCode.ACCOUNT_NOT_NORMAL.code,
                        ResponseErrorCode.ACCOUNT_NOT_NORMAL.msg, false);
            }
        }

        // 修改账号密码
        String password = SHAEncryptUtils.encryptSHA(forgotDTO.getNewpwd());
        Account account = new Account();
        account.setId(customerAccount.getId());
        account.setPassword(password);
        int res = accountMapper.updateByPrimaryKeySelective(account);

        return AppResponse.success(res>0);
    }

    /**
     * 修改密码
     * @param changePwdDTO
     * @return
     */
    public AppResponse<Boolean> changePwd(ChangePwdDTO changePwdDTO) {
        if (null == changePwdDTO || StringUtils.isBlank(changePwdDTO.getOldpwd())
                || StringUtils.isEmpty(changePwdDTO.getNewpwd())
                || StringUtils.isEmpty(changePwdDTO.getConfirmpwd())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, false);
        }

        // 检测验证码是否正确
        if (!changePwdDTO.getNewpwd().equals(changePwdDTO.getConfirmpwd())) {
            return new AppResponse<>(ResponseErrorCode.VCODE_NOT_VALID.code,
                    ResponseErrorCode.VCODE_NOT_VALID.msg, false);
        }
        // 检测账号是否已经注册
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        String phone = loginUser.getAccount();
        if (StringUtils.isBlank(phone)) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_IS_NOT_PHONE.code,
                    ResponseErrorCode.VCODE_NOT_VALID.msg, false);
        }
        Account customerAccount = accountMapper.selectByAccount(phone);
        if (null == customerAccount) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_NOT_FOUND.code,
                    ResponseErrorCode.ACCOUNT_NOT_FOUND.msg, false);
        } else {
            if (!AccountStatusEnum.NORMAL.value.equals(customerAccount.getStatus())) {
                return new AppResponse<>(ResponseErrorCode.ACCOUNT_NOT_NORMAL.code,
                        ResponseErrorCode.ACCOUNT_NOT_NORMAL.msg, false);
            } else if (!customerAccount.getPassword().equals(SHAEncryptUtils.encryptSHA(changePwdDTO.getOldpwd()))) {
                return new AppResponse<>(ResponseErrorCode.OLD_PWD_INVALID.code,
                        ResponseErrorCode.OLD_PWD_INVALID.msg, false);
            }
        }

        // 修改账号密码
        String password = SHAEncryptUtils.encryptSHA(changePwdDTO.getNewpwd());
        Account account = new Account();
        account.setId(customerAccount.getId());
        account.setPassword(password);
        int res = accountMapper.updateByPrimaryKeySelective(account);

        return AppResponse.success(res>0);
    }
}
