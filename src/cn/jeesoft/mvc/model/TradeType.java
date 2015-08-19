package cn.jeesoft.mvc.model;

/**
 * 交易类型
 */
public enum TradeType {
	/** 话费 */
	CALL(1, "话费充值"),
	/** 转账 */
	TRANSFER(2, "转账"),
	/** 购物 */
	SHOP(3, "购物"),
	/** 理财 */
	P2P(4, "理财"),
	/** 收益 */
	PROFIT(5, "收益"),
	/** 手续费 */
	FEES(6, "手续费"),
	/** 充值 */
	RECHARGE(7, "余额充值"),
	/** 取现 */
	WITHDRAW(8, "余额提现"),
	/** 未知 */
	UNKNOWN(-1, "未知");
	
	private int code;
	private String name;
	
	private TradeType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public int getCode() {
		return this.code;
	}
	public String getName() {
		return this.name;
	}
}
