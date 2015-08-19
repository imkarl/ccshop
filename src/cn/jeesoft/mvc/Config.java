package cn.jeesoft.mvc;

public class Config {
	
	public static final String manage_session_user_info = "manage_session_user_info"; //登陆用户信息
	public static final String manage_session_admin_info = "manage_session_admin_info"; //登陆管理员信息
	public static final String user_resource_menus_button = "user_resource_menus_button";
	
	
	/** 最小余额充值金额 */
	public static final int MIN_MONEY_RECHARGE = 100;
	/** 最大余额充值金额 */
	public static final int MAX_MONEY_RECHARGE = 10000000;
	
	/** 最小提现金额 */
	public static final int MIN_MONEY_WITHDRAW = 100;
	/** 最小转账金额 */
	public static final int MIN_MONEY_TRANSFER = 100;
	/** 最小话费充值金额 */
	public static final int MIN_MONEY_CALL = 100;
	
	/** 默认分页条数 */
	public static final int PAGE_SIZE = 10;

	/** 图片最大宽度 */
	public static final int MAX_IMAGE_WIDTH = 720;
	/** 图片最大高度 */
	public static final int MAX_IMAGE_HEIGHT = 1200;
	
	public static final int[] CALL_MONEYs = new int[]{5000, 10000, 15000, 20000, 30000, 50000, 100000};
	
	
	

	/*
	 * 收款：卡卡联
	 */
//	测试地址
//	public static final String KKL_KEY = "46740daa9faa72d9fad56dc34c230401";
//	public static final String KKL_MerID = "1000000183";
	
	/** 约定的KEY（32位MD5） */
	public static final String KKL_KEY = "自己补充";
	/** 商户ID */
	public static final String KKL_MerID = "自己补充";
	


	/*
	 * 话费充值：007ka
	 */
//	测试地址
//	private static final String CALL_URL_RECVCDKEY = "http://112.95.172.89:7080/recvcdkey.php";
//	private static final String CALL_MerID = "1000000007";
//	private static final String CALL_MerAccount = "100000000000007";
//	private static final String CALL_CallbackURL = "http://jeesoft.ngrok.io/pay/recharge_notify.do";
//	private static final String CALL_KEY = "I5oCbrolpr4=";
	
	public static final String CALL_URL_RECVCDKEY = "http://www.007ka.cn/recvcdkey.php";
	public static final String CALL_MerID = "自己补充";
	public static final String CALL_MerAccount = "自己补充";
	public static final String CALL_CallbackURL = "自己补充/pay/recharge_notify.do";
	public static final String CALL_KEY = "01i2q3r@30q4fawi";
	
	
	/*
	 * 短信验证码：云之讯
	 */
	public static final String UCPAAS_ACCOUNTSID = "自己补充"; // 开发者自己的账号
	public static final String UCPAAS_AUTHTOKEN = "自己补充"; // TOKEN
	public static final String UCPAAS_APPID = "自己补充"; // 应用ID
	public static final String UCPAAS_TEMPLATEID = "自己补充"; // 模板ID
	public static final String UCPAAS_HOST = "http://www.ucpaas.com/maap/sms/code";
	
	
	/*
	 * 通联支付
	 */
	public static final String ALLINPAY_MD5KEY = "自己补充";
	
	
	
	
}
