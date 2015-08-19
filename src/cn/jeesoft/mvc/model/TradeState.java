package cn.jeesoft.mvc.model;

/**
 * 交易状态
 */
public enum TradeState {
	/** 已创建 */
	CREATED(0, "未支付"),
	/** 交易失效 */
	FAILED(1, "失败"),
	/** 交易成功 */
	SUCCESS(2, "成功"),
	/** 已支付 */
	PAYOFF(3, "已支付"),
	/** 未知 */
	UNKNOWN(-1, "未知");
	
	private int code;
	private String name;
	private TradeState(int code, String name) {
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
