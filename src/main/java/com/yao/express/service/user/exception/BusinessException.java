package com.yao.express.service.user.exception;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常
 *
 *         定义异常时，需要先确定异常所属模块。例如：添加商户报错 可以定义为 [10020001] 前四位数为系统模块编号，后4位为错误代码 ,唯一 <br>
 *         商户门户异常 1002 <br>
 *         会员门户异常 1004 <br>
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -5875371379845226068L;

	/**
	 * 数据库操作,insert返回0
	 */
	public static final BusinessException DB_INSERT_RESULT_0 = new BusinessException(90040001, "数据库操作,insert返回0");

	/**
	 * 数据库操作,update返回0
	 */
	public static final BusinessException DB_UPDATE_RESULT_0 = new BusinessException(90040002, "数据库操作,update返回0");

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final BusinessException DB_SELECTONE_IS_NULL = new BusinessException(90040003, "数据库操作,selectOne返回null");

	/**
	 * 数据库操作,list返回null
	 */
	public static final BusinessException DB_LIST_IS_NULL = new BusinessException(90040004, "数据库操作,list返回null");

	/**
	 * Token 验证不通过
	 */
	public static final BusinessException TOKEN_IS_ILLICIT = new BusinessException(90040005, "Token 验证非法");
	/**
	 * 会话超时　获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
	 */
	public static final BusinessException SESSION_IS_OUT_TIME = new BusinessException(90040006, "会话超时");

	/**
	 * 获取序列出错
	 */
	public static final BusinessException DB_GET_SEQ_NEXT_VALUE_ERROR = new BusinessException(90040007, "获取序列出错");

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public BusinessException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BusinessException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 *
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public BusinessException newInstance(String msgFormat, Object... args) {
		return new BusinessException(this.code, msgFormat, args);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}
}
