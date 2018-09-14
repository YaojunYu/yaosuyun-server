package com.yao.express.service.user.services;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.*;
import com.yao.express.service.user.entity.*;
import com.yao.express.service.user.enums.AccountRoleEnum;
import com.yao.express.service.user.enums.AccountStatusEnum;
import com.yao.express.service.user.enums.AccountTypeEnum;
import com.yao.express.service.user.enums.ClientTypeEnum;
import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.mapper.*;
import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.services.cache.HeartbeatCacheDao;
import com.yao.express.service.user.util.LocalHost;
import com.yao.express.service.user.util.SHAEncryptUtils;
import com.yao.express.service.user.util.login.UserLoginUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DriverService {

    private static Logger logger = LoggerFactory.getLogger(DriverService.class);

    @Resource
    private HeartbeatCacheDao heartbeatCacheDao;
    @Resource
    private HeartbeatService heartbeatService;
    @Resource
    private UserLoginMapper userLoginMapper;
    @Resource
    private OrderrMapper orderrMapper;
    @Resource
    private SmsService smsService;
    @Resource
    private DriverAccountMapper driverAccountMapper;
    @Resource
    private DriverMapper driverMapper;
    @Resource
    private VehicleMapper vehicleMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${session.expire.time}")
    private Long sessionTime;

    /**
     * 获取所有可调度的司机的位置
     * @param lati
     * @param longi
     * @param radius
     * @param count
     * @return
     */
    public List<Double[]> getAvailabelDriversLocs(double lati, double longi, String member, double radius, Long count) {
        Map<String, Double[]> result = heartbeatCacheDao.nearestDriverLocs(lati, longi, member, radius, count);
        return new ArrayList<>(result.values());
    }

    @Transactional
    public AppResponse<Boolean> register(RegisterDTO driver) {
        if (null == driver || StringUtils.isBlank(driver.getPhone())
                || StringUtils.isEmpty(driver.getPassword())
                || StringUtils.isEmpty(driver.getVcode())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, false);
        }

        // 检测验证码是否正确
        boolean codeIsValid = smsService.verifyVcode(driver.getPhone(), driver.getVcode());
        if (!codeIsValid) {
            return new AppResponse<>(ResponseErrorCode.VCODE_NOT_VALID.code,
                    ResponseErrorCode.VCODE_NOT_VALID.msg, false);
        }
        // 检测账号是否已经注册
        DriverAccount driverAccount = driverAccountMapper.selectByAccount(driver.getPhone());
        if (null != driverAccount) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_HAS_EXIST.code,
                    ResponseErrorCode.ACCOUNT_HAS_EXIST.msg, false);
        }

        // 注册司机账号
        String driverId = UUID.randomUUID().toString();
        String password = SHAEncryptUtils.encryptSHA(driver.getPassword());
        DriverAccount account = new DriverAccount();
        account.setAccount(driver.getPhone());
        account.setPassword(password);
        account.setDriverId(driverId);
        account.setStatus(AccountStatusEnum.CREATE.value);
        int res = driverAccountMapper.insertSelective(account);
        if (res<1) {
            throw new RuntimeException();
        }

        Driver newDriver = new Driver();
        newDriver.setDriverId(driverId);
        newDriver.setPhone(driver.getPhone());
        res = driverMapper.insertSelective(newDriver);
        if (res<1) {
            throw new RuntimeException();
        }

        return AppResponse.success(true);
    }

    /**
     * 司机账号登录
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
        DriverAccount driverAccount = driverAccountMapper.selectByAccount(loginDTO.getAccount());
        if(null == driverAccount ||
                !password.equals(driverAccount.getPassword())) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_LOGIN_FAIL.code,
                    ResponseErrorCode.ACCOUNT_LOGIN_FAIL.msg, null);
        } else if (!AccountStatusEnum.NORMAL.value.equals(driverAccount.getStatus())) {
            return new AppResponse<>(ResponseErrorCode.ACCOUNT_NOT_NORMAL.code,
                    ResponseErrorCode.ACCOUNT_NOT_NORMAL.msg, null);
        }

        // 记录登录信息
        UserLogin userLogin = new UserLogin();
        userLogin.setUserLoginId(UUID.randomUUID().toString());
        userLogin.setAccount(driverAccount.getAccount());
        userLogin.setAccountType(AccountTypeEnum.DRIVER.value);
        userLogin.setClientType(ClientTypeEnum.ANDROID_APP.value); // FIXME
        userLogin.setLoginIp(LocalHost.getLocalIP());

        userLogin.setLoginTime(new Date());
        if (userLoginMapper.insertSelective(userLogin) <= 0) {
            throw new RuntimeException();
        }

        // 登录成功，保存redis
        Driver driver = driverMapper.selectByDriverId(driverAccount.getDriverId());
        LoginUser loginUser = new LoginUser();
        loginUser.setAccount(driverAccount.getAccount());
        loginUser.setUid(driver.getDriverId());
        loginUser.setToken(sessionId);
        loginUser.setRoles(Arrays.asList(AccountRoleEnum.DRIVER.value));
        if(null != driver) {
           loginUser.setName(driver.getName());
           loginUser.setPhoto(driver.getPhoto());
           loginUser.setSex(driver.getSex());
           // FIXME
        }
        sessionTime = (null == sessionTime) ? -1 : sessionTime;
        redisTemplate.opsForValue().set("session:"+sessionId, loginUser, sessionTime, TimeUnit.MILLISECONDS);

        return AppResponse.success(loginUser);
    }

    /**
     * 修改密码
     * @param updatePwdRequest
     * @return
     */
    public AppResponse<Boolean> updatePassword(UpdatePwdRequest updatePwdRequest) {
        if(null == updatePwdRequest || StringUtils.isEmpty(updatePwdRequest.getOldPsw())
            || StringUtils.isEmpty(updatePwdRequest.getNewPsw())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        // 判断原密码是否正确
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        DriverAccount oldAccount = driverAccountMapper.selectByAccount(loginUser.getAccount());
        if (null == oldAccount) {
            return new AppResponse<>(ResponseErrorCode.DRIVER_ACCOUNT_NOT_FOUND.code,
                    ResponseErrorCode.DRIVER_ACCOUNT_NOT_FOUND.msg, null);
        } else {
            String oldPsw = SHAEncryptUtils.encryptSHA(updatePwdRequest.getOldPsw());
            if (!oldPsw.equals(oldAccount.getPassword())) {
                return new AppResponse<>(ResponseErrorCode.DRIVER_ACCOUNT_PASSWORD_INVALID.code,
                        ResponseErrorCode.DRIVER_ACCOUNT_PASSWORD_INVALID.msg, null);
            }
        }

        // 修改密码
        String newPsw = SHAEncryptUtils.encryptSHA(updatePwdRequest.getNewPsw());
        DriverAccount driverAccount = new DriverAccount();
        driverAccount.setId(oldAccount.getId());
        driverAccount.setPassword(newPsw);

        int i = driverAccountMapper.updateByPrimaryKeySelective(driverAccount);
        return AppResponse.success(i > 0);
    }

    public AppResponse<Boolean> updateDriverStatus(String status) {
        if (StringUtils.isEmpty(status) ||
                (!status.equals("receive")&&!status.equals("reject"))) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        String driverId = loginUser.getUid();
        boolean available = "receive".equals(status) ? true : false;

        // 如果打开则检测司机当前有无订单未完成
        if (available && 0 < orderrMapper.countNotCompleteOrder(driverId)) {
            return new AppResponse<>(ResponseErrorCode.DRIVER_HAS_NOT_COMPLETE_ORDER.code,
                    ResponseErrorCode.DRIVER_HAS_NOT_COMPLETE_ORDER.msg, null);
        }

        // 更新司机状态
        boolean success = heartbeatService.updateDriverAvailable(driverId, available);
        if (success) {
            return AppResponse.success(!available);
        } else {
            return AppResponse.success(available);
        }
    }

    /**
     * 获取司机可用状态
     * @return
     */
    public AppResponse<Boolean> getDriverAvailableStatus() {
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        String driverId = loginUser.getUid();

        return AppResponse.success(heartbeatCacheDao.getDriverAvailable(driverId));
    }

    /**
     * 获取司机信息列表
     * @param queryOption
     * @return
     */
    public AppResponse<PageInfo<Driver>> getManagerDriverList(ListQueryOption queryOption) {
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
        List<Driver> list = driverMapper.selectList(queryOption);
        // 取分页信息
        PageInfo<Driver> pageInfo = new PageInfo(list);

        return AppResponse.success(pageInfo);
    }

    /**
     * 管理员增加司机账号
     * @param driverCreateRequest
     * @return
     */
    @Transactional
    public AppResponse<Driver> createDriver(DriverCreateRequest driverCreateRequest) {
        if (null == driverCreateRequest ||
                StringUtils.isBlank(driverCreateRequest.getName()) ||
                StringUtils.isBlank(driverCreateRequest.getPassword()) ||
                StringUtils.isBlank(driverCreateRequest.getPhone()) ||
                StringUtils.isBlank(driverCreateRequest.getIdNo()) ||
                StringUtils.isBlank(driverCreateRequest.getSex()) ||
                StringUtils.isBlank(driverCreateRequest.getStatus()) ||
                null == driverCreateRequest.getVehicle() ||
                StringUtils.isBlank(driverCreateRequest.getVehicle().getLicenseNo()) ||
                StringUtils.isBlank(driverCreateRequest.getVehicle().getType())) {
            return new AppResponse<>(ResponseErrorCode.REQUEST_PARAMS_INVALID.code,
                    ResponseErrorCode.REQUEST_PARAMS_INVALID.msg, null);
        }

        // 只有管理员权限
        LoginUser loginUser = (LoginUser) UserLoginUtils.getCurrentUser();
        if (!loginUser.getRoles().contains(AccountRoleEnum.MANAGER.value)) {  // FIXME
            return new AppResponse<>(ResponseErrorCode.ORDER_OPS_FORBIDDEN.code,
                    ResponseErrorCode.ORDER_OPS_FORBIDDEN.msg, null);
        }

        // 保存司机账号
        Driver driver = new Driver();
        String driverId = UUID.randomUUID().toString();
        driver.setPhone(driverCreateRequest.getPhone());
        driver.setDriverId(driverId);
        driver.setIdNo(driverCreateRequest.getIdNo());
        driver.setName(driverCreateRequest.getName());
        driver.setSex(driverCreateRequest.getSex());
//        driver.setPhoto();
//        driver.setDrivingLicence();
        String vehicleId = UUID.randomUUID().toString();
        driver.setVehicleId(vehicleId);

        DriverAccount driverAccount = new DriverAccount();
        driverAccount.setAccount(driverCreateRequest.getPhone());
        String pwd = SHAEncryptUtils.encryptSHA(driverCreateRequest.getPassword());
        driverAccount.setPassword(pwd);
        driverAccount.setStatus(driverCreateRequest.getStatus());
        driverAccount.setDriverId(driverId);

        // 保存车辆信息
        Vehicle vehicle = new Vehicle();
        DriverCreateRequest.VehicleForm vhc = driverCreateRequest.getVehicle();
        vehicle.setBrand(vhc.getBrand());
        vehicle.setColor(vhc.getColor());
        vehicle.setVehicleId(vehicleId);
        vehicle.setLicenseNo(vhc.getLicenseNo());
        vehicle.setType(vhc.getType());
        vehicle.setMaxCapacity(vhc.getMaxCapacity());

        driverAccountMapper.insertSelective(driverAccount);
        driverMapper.insertSelective(driver);
        vehicleMapper.insertSelective(vehicle);

        return AppResponse.success(driver);
    }


    public AppResponse<Driver> updateDriver(Driver driver) {
        return null;
    }

    /**
     * 获取当前司机的定位
     * @param driverId
     * @return
     */
    public AppResponse<Double[]> getCurrentDriverPoint(String driverId) {
        Heartbeat heartbeat = heartbeatService.getHeartbeat(driverId);
        if (null == heartbeat) {
            return AppResponse.success(null);
        } else {
            return AppResponse.success(new Double[]{heartbeat.getLatitude(),
                    heartbeat.getLongitude()});
        }
    }
}
