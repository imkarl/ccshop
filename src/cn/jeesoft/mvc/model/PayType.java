package cn.jeesoft.mvc.model;

/**
 * 付款方式
 */
public enum PayType {
	/** 余额 */
	BALANCE(0, "余额"),
	/** 支付宝 */
	ALIPAY(1, "支付宝"),
	/** 银行 */
	BANK(2, "银行卡"),
	/** 卡卡联 */
	KKLPAY(3, "NFC支付"),
	/** 认证支付 */
	AUTH(4, "认证支付"),
	/** 通联支付 */
	ALLINPAY(5, "通联支付"),
	/** 未知 */
	UNKNOWN(-1, "未知");
	
	private int code;
	private String name;
	private PayType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	public int getCode() {
		return this.code;
	}
	public String getName() {
		return this.name;
	}
}
