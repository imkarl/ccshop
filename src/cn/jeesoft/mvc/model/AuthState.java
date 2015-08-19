package cn.jeesoft.mvc.model;

/**
 * 用户认证
 * @author king
 */
public enum AuthState {

	YES(1, "已认证"),
	WAIT(2, "等待审核"),
	INVALID(3, "认证失败"),
	NO(0, "未认证"),
	;
	
	private int code;
	private String name;
	private AuthState(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	public int getCode() {
		return this.code;
	}
	public String getName() {
		return this.name;
	}
}
