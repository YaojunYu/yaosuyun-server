package com.yao.express.service.user.util.http;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String postWithJson(String url, Map<String, String> headerMap, Map<String, Object> dataMap) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);

        // 设置请求的header
        if(null != headerMap && !headerMap.isEmpty()) {
            for(String key : headerMap.keySet()) {
                if(StringUtils.isNotBlank(key)) {
                    httpPost.addHeader(key, headerMap.get(key));
                }
            }

            // 构造body
            String dataJson = JSON.toJSONString(dataMap);
            StringEntity bodyEntity = new StringEntity(dataJson, Charset.forName("UTF-8"));
            bodyEntity.setContentEncoding("UTF-8");
            bodyEntity.setContentType("application/json");
            httpPost.setEntity(bodyEntity);
        }


        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                result =  EntityUtils.toString(entity, "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != entity) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpPost.releaseConnection();
        }
        return result;
    }

    public static String postWithForm(String url, Map<String, String> headerMap, Map<String, String> dataMap) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);

        // 设置请求的header
        if(null != headerMap && !headerMap.isEmpty()) {
            for(String key : headerMap.keySet()) {
                if(StringUtils.isNotBlank(key)) {
                    httpPost.addHeader(key, headerMap.get(key));
                }
            }
        }

        //设置请求的的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(null != dataMap && !dataMap.isEmpty()) {
            for (String key : dataMap.keySet()) {
                if(StringUtils.isNotBlank(key)) {
                    nvps.add(new BasicNameValuePair(key, dataMap.get(key)));
                }
            }
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, "utf-8");
                httpPost.setEntity(entity);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }

        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                result =  EntityUtils.toString(entity, "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != entity) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpPost.releaseConnection();
        }

        return result;
    }

    public static String get(String url, Map<String, String> headerMap, Map<String, String> paramsMap) {
        return get(url, headerMap, paramsMap, "utf-8");
    }

    public static String get(String url, Map<String, String> headerMap, Map<String, String> paramsMap, String enc) {
        String result = null;
        if (null != paramsMap && !paramsMap.isEmpty()) {
            if (!url.trim().endsWith("?")) {
                url = url.trim() + "?";
            }
            int index = 0;
            for (String key : paramsMap.keySet()) {
                try {
                    if (null != paramsMap.get(key)) {
                        if (StringUtils.isBlank(enc)) {
                            url += key + "=" + paramsMap.get(key);
                        } else {
                            url += URLEncoder.encode(key, enc) + "=" +
                                    URLEncoder.encode(paramsMap.get(key), enc);
                        }

                        if (++index < paramsMap.size()) {
                            url += "&";
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        HttpGet httpGet = new HttpGet(url);

        // 设置请求的header
        if (null != headerMap && !headerMap.isEmpty()) {
            for (String key : headerMap.keySet()) {
                httpGet.addHeader(key, headerMap.get(key));
            }
        }

        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                result =  EntityUtils.toString(entity, enc);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != entity) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpGet.releaseConnection();
        }

        return result;
    }
}
