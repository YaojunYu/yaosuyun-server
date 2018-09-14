package com.yao.express.service.user.response;

import com.yao.express.service.user.util.LocalHost;

/**
 * Rest微服务响应公共实体
 *
 * @author: York.Yu
 * @date: 2017/3/21
 */
public class ServiceResponse<T> {

    /**
     * 服务器机器名
     */
    protected String serverName = LocalHost.getMachineName();

    /**
     * 服务器ip
     */
    protected String serverIp = LocalHost.getLocalIP();

    /**
     * 服务器时间
     */
    protected Long serverTime = System.currentTimeMillis();


    /**请求路径**/
    private String path;


    /**返回码：200表示成功，其他为具体错误编码**/
    private int code;

    /**返回消息：success或者错误信息描述**/
    private String message;

    /**返回结果数据**/
    private T data;

    /**成功响应常量**/
    public static final ServiceResponse SUCCESS = new ServiceResponse(200, "success", null);
    /**错误响应常量**/
    public static final ServiceResponse ERROR = new ServiceResponse(500, "server error", null);

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 默认构造方法
     */
    public ServiceResponse() {
    }

    /**
     * 构造方法
     * @param code 返回码
     * @param message 返回消息
     * @param data 返回数据
     */
    public ServiceResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造成功返回对象，传入
     * @return 公共响应实体
     */
    public static ServiceResponse success(final Object data) {
        ServiceResponse response = new ServiceResponse();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }
}
