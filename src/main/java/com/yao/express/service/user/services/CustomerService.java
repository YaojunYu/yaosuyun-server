package com.yao.express.service.user.services;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.*;
import com.yao.express.service.user.entity.Account;
import com.yao.express.service.user.entity.Customer;
import com.yao.express.service.user.entity.Driver;
import com.yao.express.service.user.entity.UserLogin;
import com.yao.express.service.user.enums.AccountRoleEnum;
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
import com.yao.express.service.user.util.login.UserLoginUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerService {
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private UserLoginMapper userLoginMapper;
    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${session.expire.time}")
    private Long sessionTime;

    /**
     * 客户注册账号
     * @param customer
     * @return
     */
    @Transactional
    public AppResponse<Boolean> register(RegisterDTO customer) {
        if (null == customer || StringUtils.isBlank(customer.getPhone())
                || StringUtils.isEmpty(customer.getPassword())
                || StringUtils.isEmpty(customer.getVcode())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, false);
        }

        // 检测验证码是否正确
        boolean codeIsValid = smsService.verifyVcode(customer.getPhone(), customer.getVcode());
        if (!codeIsValid) {
            return new AppResponse<>(ResponseErrorCode.VCODE_NOT_VALID.code,
                    ResponseErrorCode.VCODE_NOT_VALID.msg, false);
        }
        // 检测账号是否已经注册
        Account customerAccount = accountMapper.selectByAccount(customer.getPhone());
        if (null != customerAccount) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_HAS_EXIST.code,
                    ResponseErrorCode.ACCOUNT_HAS_EXIST.msg, false);
        }

        // 注册顾客账号
        String customerId = UUID.randomUUID().toString();
        String password = SHAEncryptUtils.encryptSHA(customer.getPassword());
        Account account = new Account();
        account.setAccount(customer.getPhone());
        account.setPassword(password);
        account.setCustomerId(customerId);
        account.setStatus(AccountStatusEnum.NORMAL.value);
        int res = accountMapper.insertSelective(account);
        if (res<1) {
            throw new RuntimeException();
        }

        Customer newCustomer = new Customer();
        newCustomer.setCustomerId(customerId);
        newCustomer.setPhone(customer.getPhone());
        res = customerMapper.insertSelective(newCustomer);
        if (res<1) {
            throw new RuntimeException();
        }

        return AppResponse.success(true);
    }

    /**
     * 顾客账号登录
     * @param loginDTO
     * @return
     */
    public AppResponse<LoginUser> login(AccLoginDTO loginDTO, String sessionId) {
        if(null == loginDTO || StringUtils.isEmpty(loginDTO.getAccount())
                || StringUtils.isEmpty(loginDTO.getPassword())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        String password = SHAEncryptUtils.encryptSHA(loginDTO.getPassword());
        Account customerAccount = accountMapper.selectByAccount(loginDTO.getAccount());
        if(null == customerAccount ||
                !password.equals(customerAccount.getPassword())) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_LOGIN_FAIL.code,
                    ResponseErrorCode.ACCOUNT_LOGIN_FAIL.msg, null);
        } else if (!AccountStatusEnum.NORMAL.value.equals(customerAccount.getStatus())) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_NOT_NORMAL.code,
                    ResponseErrorCode.ACCOUNT_NOT_NORMAL.msg, null);
        }

        // 记录登录信息
        UserLogin userLogin = new UserLogin();
        userLogin.setUserLoginId(UUID.randomUUID().toString());
        userLogin.setAccount(customerAccount.getAccount());
        userLogin.setAccountType(AccountTypeEnum.CUSTOMER.value);
        userLogin.setClientType(ClientTypeEnum.H5.value); // FIXME
        userLogin.setLoginIp(LocalHost.getLocalIP());

        userLogin.setLoginTime(new Date());
        if (userLoginMapper.insertSelective(userLogin) <= 0) {
            throw new RuntimeException();
        }

        // 登录成功，保存redis
        Customer customer = customerMapper.selectByCustomerId(customerAccount.getCustomerId());
        LoginUser loginUser = new LoginUser();
        loginUser.setAccount(customerAccount.getAccount());
        loginUser.setUid(customer.getCustomerId());
        loginUser.setToken(sessionId);
        loginUser.setRoles(Arrays.asList(AccountRoleEnum.CUSTOMER.value));
        if(null != customer) {
            loginUser.setName(customer.getName());
            loginUser.setPhoto(customer.getPhoto());
            loginUser.setSex(customer.getSex());
            // FIXME
        }
        sessionTime = (null == sessionTime) ? -1 : sessionTime;
        redisTemplate.opsForValue().set("session:"+sessionId, loginUser, sessionTime, TimeUnit.MILLISECONDS);

        return AppResponse.success(loginUser);
    }

    /**
     * 绑定手机号
     * @param updatePhoneReq
     * @return
     */
    public AppResponse<Boolean> updatePhone(UpdatePhoneReq updatePhoneReq) {
        if (null == updatePhoneReq ||
                StringUtils.isBlank(updatePhoneReq.getPhone()) ||
                StringUtils.isBlank(updatePhoneReq.getVcode())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        // 检测验证码是否正确
        boolean codeIsValid = smsService.verifyVcode(updatePhoneReq.getPhone(), updatePhoneReq.getVcode());
        if (!codeIsValid) {
            return new AppResponse<>(ResponseErrorCode.VCODE_NOT_VALID.code,
                    ResponseErrorCode.VCODE_NOT_VALID.msg, false);
        }

        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();

        Customer customer = new Customer();
        customer.setCustomerId(loginUser.getUid());
        customer.setPhone(updatePhoneReq.getPhone());

        int i = customerMapper.updateByCustomerIdSelective(customer);

        return AppResponse.success(i>0);
    }

    /**
     * 更新用户信息
     * @param updateUser
     * @return
     */
    public AppResponse<Boolean> updateInfo(LoginUser updateUser) {
        if (null == updateUser) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();

        Customer customer = new Customer();
        customer.setCustomerId(loginUser.getUid());
        customer.setName(updateUser.getName());
        customer.setPhone(updateUser.getPhoto());

        int i = customerMapper.updateByCustomerIdSelective(customer);

        return AppResponse.success(i>0);
    }

    /**
     * 获取顾客列表
     * @param queryOption
     * @return
     */
    public AppResponse<PageInfo<Customer>> getManagerCustomerList(ListQueryOption queryOption) {
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
        List<Customer> list = customerMapper.selectList(queryOption);
        // 取分页信息
        PageInfo<Driver> pageInfo = new PageInfo(list);

        return AppResponse.success(pageInfo);
    }
}
