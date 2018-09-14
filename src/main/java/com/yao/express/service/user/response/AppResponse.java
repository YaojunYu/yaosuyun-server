package com.yao.express.service.user.response;

/**
 * App应用接口响应公共实体
 *
 * @author: York.Yu
 * @date: 2017/3/21
 */
public class AppResponse<T> {

    /**返回码：200表示成功，其他为具体错误编码**/
    private int code;

    /**返回消息：success或者错误信息描述**/
    private String message;

    /**返回结果数据**/
    private T data;

    /**成功响应常量**/
    public static final AppResponse SUCCESS = new AppResponse(200, "success", null);
    /**错误响应常量**/
    public static final AppResponse ERROR = new AppResponse(500, "server error", null);

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
    public AppResponse() {
    }

    /**
     * 构造方法
     * @param code 返回码
     * @param message 返回消息
     * @param data 返回数据
     */
    public AppResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造成功返回对象，传入
     * @return 公共响应实体
     */
    public static AppResponse success(final Object data) {
        AppResponse response = new AppResponse();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }
}
