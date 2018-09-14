package com.yao.express.service.user.services;

import com.yao.express.service.user.exception.ResponseErrorCode;
import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.util.sms.rongyun.YtxSendTemplateSMS;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {

    @Resource
    private RedisTemplate redisTemplate;

    private static final String VCODE_TEMP_ID = "192831";
    private static final int VCODE_EXPIRE = 5;
    private static final String VCODE_KEY = "vcode:{phone}";

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    public AppResponse<Boolean> sendVcode(String phone) {
        if (StringUtils.isEmpty(phone) ||
                phone.length() != 11) {
            return new AppResponse(ResponseErrorCode.PHONE_NOT_VALID.code,
                    ResponseErrorCode.PHONE_NOT_VALID.msg, false);
        }

        DecimalFormat df = new DecimalFormat("00000");
        String vcode = df.format(RandomUtils.nextInt(0, 100000));
        boolean sent = YtxSendTemplateSMS.sendSMS(phone, VCODE_TEMP_ID,
                new String[]{vcode, String.valueOf(VCODE_EXPIRE)});
        if (sent) {
            String key = VCODE_KEY.replace("{phone}", phone);
            redisTemplate.opsForValue().set(key, vcode, VCODE_EXPIRE, TimeUnit.MINUTES);
        }

        return AppResponse.success(sent);
    }

    /**
     * 验证码验证
     * @param phone
     * @param vcode
     * @return
     */
    public boolean verifyVcode(String phone, String vcode) {
        String key = VCODE_KEY.replace("{phone}", phone);
        boolean res = null != redisTemplate.opsForValue().get(key);
        if(res) {
            redisTemplate.delete(key);
        }

        return res;
    }
}
