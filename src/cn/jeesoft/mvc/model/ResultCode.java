package cn.jeesoft.mvc.model;

/**
 * 返回码
 */
public enum ResultCode {
	/** 成功 */
	SUCCESS(1000),
	/** 未登陆 */
	LOGOUT(1010),
	
	FAILURE(1001),//失败
	ERROE_PARAMETER(1002),//参数不正确
	ERROE_JSON(1003),//JSON格式不正确
	REPEAT(1004),//重复操作
	ERROE_DB(1005),//数据库操作失败
	UNKNOWN(0);//未知错误
	
	private int code;
	private ResultCode(int code) {
		this.code = code;
	}
	public int getCode() {
		return this.code;
	}
}
