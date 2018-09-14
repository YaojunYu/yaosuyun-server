package com.yao.express.service.user.util.sms;


import com.alibaba.fastjson.JSON;
import com.yao.express.service.user.util.sms.CheckSumBuilder;
import com.yao.express.service.user.util.sms.SmsTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 短信通知
 *
 * @author netease
 */
public class SmsSender {

    private final static Log log = LogFactory.getLog(com.yao.express.service.user.util.sms.SmsSender.class);

    /**
     * 是否是测试环境  true就会发送
     */
    public static final boolean isSendMsg = true;
    public static String appKey = "5ee21ace5fcaa4f6cab8f95ed429f4aa";
    public static String appSecret = "f59f0ef2fd09";
    public static String url = "https://api.netease.im/sms/sendtemplate.action";

    public static String messageUrl = "https://api.netease.im/sms/querystatus.action";

    public SmsSender() {}

    public SmsSender(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    public static boolean sendMessage(String phone, String validateCode) {
        SmsTemplate temp = new SmsTemplate(SmsTemplate.VALIDATE_CODE_TEMPLATE_ID, new String[]{validateCode});
        return sendMessage(new String[]{phone}, temp);
    }

    public static boolean sendMessage(String mobile, SmsTemplate sms) {
        return sendMessage(new String[]{mobile}, sms);
    }

    public static boolean sendMessage(String[] mobiles, SmsTemplate temp) {

        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String nonce = "qwertyuiop1234567890";//nonce随机数
            String curTime = String.valueOf((new Date()).getTime() / 1000L);//time
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考计算CheckSum的java代码

            // 设置请求的header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            //设置请求的的参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            //								模板id
            nvps.add(new BasicNameValuePair("templateid", temp.getTemplateId()));
            //								参数			jsonArray形式

            nvps.add(new BasicNameValuePair("mobiles", JSON.toJSONString(mobiles)));

            nvps.add(new BasicNameValuePair("params", JSON.toJSONString(temp.getParams())));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));


            // 执行请求
            if (isSendMsg) {

                // 执行请求
                HttpResponse response = httpClient.execute(httpPost);


                String returnBody = EntityUtils.toString(response.getEntity(), "utf-8");
                // 打印执行结果


                log.info(String.format("sendMessage_req:%s----expData:%s", mobiles, returnBody));
                if (returnBody.indexOf("200") > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param map
     * @return
     */
    public static void sendMessage(final  Map<String,String> map){
        new Thread(() -> {
            try{
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                String nonce = "qwertyuiop1234567890";//nonce随机数
                String curTime = String.valueOf((new Date()).getTime() / 1000L);//time
                String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考计算CheckSum的java代码

                // 设置请求的header
                httpPost.addHeader("AppKey", appKey);
                httpPost.addHeader("Nonce", nonce);
                httpPost.addHeader("CurTime", curTime);
                httpPost.addHeader("CheckSum", checkSum);
                httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                //设置请求的的参数
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                String mobiles="";
                String message ="";


                nvps.add(new BasicNameValuePair("templateid", map.get("id")));
                nvps.add(new BasicNameValuePair("mobiles", "['"+map.get("phone")+"']"));
                nvps.add(new BasicNameValuePair("params", "['" + map.get("ecName") +"','" + map.get("createTime") +"','" + map.get("orderNo")+"','" + map.get("price") +"','"+map.get("serialNumber")+"']"));
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

                // 执行请求
                if (isSendMsg) {
                    HttpResponse response = httpClient.execute(httpPost);
                    String returnBody = EntityUtils.toString(response.getEntity(), "utf-8");
//                    // 打印执行结果
//                    // log.info(String.format("sendMessage_req:%s----expData:%s", mobiles.substring(0,mobiles.length()-1), returnBody));
                    if (returnBody.indexOf("200") > 0) {
                        log.info(String.format("成功----expData:%s",returnBody));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

//    public static void main(String[] args) {
//        SmsSender sender = new SmsSender();
//        SmsTemplate temp = new SmsTemplate(SmsTemplate.VALIDATE_CODE_TEMPLATE_ID, new String[]{"code"});
//        sender.sendMessage("18701258965", temp);
//    }
}
