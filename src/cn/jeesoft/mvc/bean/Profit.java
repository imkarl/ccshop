package cn.jeesoft.mvc.bean;

import com.alibaba.fastjson.JSONObject;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.utils.JsonUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.Config;

/**
 * 收益利润实体
 * @author hzy
 */
@SuppressWarnings("serial")
public class Profit extends BaseBean implements Cloneable {

	private Integer usersId;//用户ID (0=管理员, others=其他用户设置)
	private Integer oneBalance;//一级余额充值收益
	private Integer twoBalance;//二级余额充值收益
	private Integer threeBalance;//三级余额充值收益
	private Integer oneWithdraw;//一级余额提现收益
	private Integer twoWithdraw;//二级余额提现收益
	private Integer threeWithdraw;//三级余额提现收益
	private String oneCall;//一级话费充值收益
	private String twoCall;//二级话费充值收益
	private String threeCall;//三级话费充值收益
	private Integer oneShop;//一级购物收益
	private Integer twoShop;//二级购物收益
	private Integer threeShop;//三级购物收益
	private Integer oneTransfer;//一级转账收益
	private Integer twoTransfer;//二级转账收益
	private Integer threeTransfer;//三级转账收益
	private Integer call_50;//话费充值费率
	private Integer call_100;//话费充值费率
	private Integer call_150;//话费充值费率
	private Integer call_200;//话费充值费率
	private Integer call_300;//话费充值费率
	private Integer call_500;//话费充值费率
	private Integer call_1000;//话费充值费率
	private Integer transfer;//转账费率
	private Integer withdraw;//提现费率
	private Integer alipay;//支付宝费率
	private Integer kklpay;//卡卡联NFC费率
	private Integer auth;//卡卡联认证支付费率
	private Integer bank;//网银费率
	private Integer allinpay;//通联支付费率
	
	public Profit(){
		super();
	}
	public Profit(Integer usersId, Integer oneBalance, Integer twoBalance,
			Integer threeBalance, Integer oneWithdraw, Integer twoWithdraw,
			Integer threeWithdraw, String oneCall, String twoCall,
			String threeCall, Integer oneShop, Integer twoShop,
			Integer threeShop, Integer oneTransfer, Integer twoTransfer, Integer threeTransfer,
			Integer call_50, Integer call_100,
			Integer call_150, Integer call_200, Integer call_300,
			Integer call_500, Integer call_1000, Integer transfer,
			Integer withdraw, Integer alipay, Integer kklpay, Integer auth, Integer bank, Integer allinpay) {
		super();
		this.usersId = usersId;
		this.oneBalance = oneBalance;
		this.twoBalance = twoBalance;
		this.threeBalance = threeBalance;
		this.oneWithdraw = oneWithdraw;
		this.twoWithdraw = twoWithdraw;
		this.threeWithdraw = threeWithdraw;
		this.oneCall = oneCall;
		this.twoCall = twoCall;
		this.threeCall = threeCall;
		this.oneShop = oneShop;
		this.twoShop = twoShop;
		this.threeShop = threeShop;
		this.oneTransfer = oneTransfer;
		this.twoTransfer = twoTransfer;
		this.threeTransfer = threeTransfer;
		this.call_50 = call_50;
		this.call_100 = call_100;
		this.call_150 = call_150;
		this.call_200 = call_200;
		this.call_300 = call_300;
		this.call_500 = call_500;
		this.call_1000 = call_1000;
		this.transfer = transfer;
		this.withdraw = withdraw;
		this.alipay = alipay;
		this.kklpay = kklpay;
		this.auth = auth;
		this.bank = bank;
		this.allinpay = allinpay;
	}


	/**
	 * 回复默认初始费率
	 */
	public void resetDefault() {
		this.oneBalance = 0;
		this.twoBalance = 0;
		this.threeBalance = 0;
		this.oneWithdraw = 0;
		this.twoWithdraw = 0;
		this.threeWithdraw = 0;
		this.oneShop = 0;
		this.twoShop = 0;
		this.threeShop = 0;
		this.oneTransfer = 0;
		this.twoTransfer = 0;
		this.threeTransfer = 0;
		this.oneCall = createDefaultCall().toJSONString();
		this.twoCall = createDefaultCall().toJSONString();
		this.threeCall = createDefaultCall().toJSONString();
		this.call_50 = 0;
		this.call_100 = 0;
		this.call_150 = 0;
		this.call_200 = 0;
		this.call_300 = 0;
		this.call_500 = 0;
		this.call_1000 = 0;
		this.transfer = 0;
		this.withdraw = 0;
		this.alipay = 0;
		this.kklpay = 0;
		this.auth = 0;
		this.bank = 0;
		this.allinpay = 0;
	}

	/**
	 * 验证话费收益
	 */
	public void validCall() {
		if (StringUtils.isEmpty(this.oneCall)) {
			this.oneCall = createDefaultCall().toJSONString();
		}
		if (StringUtils.isEmpty(this.twoCall)) {
			this.twoCall = createDefaultCall().toJSONString();
		}
		if (StringUtils.isEmpty(this.threeCall)) {
			this.threeCall = createDefaultCall().toJSONString();
		}
	}
	
	/**
	 * 获取默认话费充值收益JSON
	 * @return 收益金额JSON
	 */
	public JSONObject createDefaultCall() {
		JSONObject json = new JSONObject();
		for (int money : Config.CALL_MONEYs) {
			json.put(String.valueOf(money), 0);
		}
		return json;
	}


	@Override
	public String toString() {
		return "Profit [usersId=" + usersId + ", oneBalance=" + oneBalance
				+ ", twoBalance=" + twoBalance + ", threeBalance="
				+ threeBalance + ", oneWithdraw=" + oneWithdraw
				+ ", twoWithdraw=" + twoWithdraw + ", threeWithdraw="
				+ threeWithdraw + ", oneCall=" + oneCall + ", twoCall="
				+ twoCall + ", threeCall=" + threeCall + ", oneShop=" + oneShop
				+ ", twoShop=" + twoShop + ", threeShop=" + threeShop
				+", threeTransfer="+threeTransfer+ ", twoTransfer=" + twoTransfer + ", threeTransfer=" + threeTransfer
				+ ", call_50=" + call_50 + ", call_100=" + call_100
				+ ", call_150=" + call_150 + ", call_200=" + call_200
				+ ", call_300=" + call_300 + ", call_500=" + call_500
				+ ", call_1000=" + call_1000 + ", transfer=" + transfer
				+ ", withdraw=" + withdraw + ", alipay=" + alipay + ", kklpay="
				+ kklpay+", auth=" +auth+ ", bank=" + bank+", allinpay="+allinpay + "]";
	}
	
	
	/**
	 * 一级收益
	 * @param money 充值面额（单位：分）
	 * @return 根据充值话费，获取对应收益
	 */
	public Integer getOneCall(int money) {
		if (money <= 0) {
			return null;
		}
		
		try {
			JSONObject json = (JSONObject) JsonUtils.fromJson(oneCall);
			return json.getInteger(String.valueOf(money));
		} catch (Throwable e) {
			return null;
		}
	}
	/**
	 * 二级收益
	 * @param money 充值面额（单位：分）
	 * @return 根据充值话费，获取对应收益
	 */
	public Integer getTwoCall(int money) {
		if (money <= 0) {
			return null;
		}
		
		try {
			JSONObject json = (JSONObject) JsonUtils.fromJson(twoCall);
			return json.getInteger(String.valueOf(money));
		} catch (Throwable e) {
			return null;
		}
	}
	/**
	 * 三级收益
	 * @param money 充值面额（单位：分）
	 * @return 根据充值话费，获取对应收益
	 */
	public Integer getThreeCall(int money) {
		if (money <= 0) {
			return null;
		}
		
		try {
			JSONObject json = (JSONObject) JsonUtils.fromJson(threeCall);
			return json.getInteger(String.valueOf(money));
		} catch (Throwable e) {
			return null;
		}
	}
	
	/**
	 * 设置一级收益
	 * @param money 充值面额（单位：分）
	 * @param fees 收益（单位：分）
	 */
	public void setOneCall(int money, int fees) {
		if (money <= 0) {
			return;
		}
		
		try {
			JSONObject json = (JSONObject) JsonUtils.fromJson(oneCall);
			json.put(String.valueOf(money), fees);
			oneCall = json.toJSONString();
		} catch (Throwable e) {
		}
	}
	/**
	 * 设置二级收益
	 * @param money 充值面额（单位：分）
	 * @param fees 收益（单位：分）
	 */
	public void setTwoCall(int money, int fees) {
		if (money <= 0) {
			return;
		}
		
		try {
			JSONObject json = (JSONObject) JsonUtils.fromJson(twoCall);
			json.put(String.valueOf(money), fees);
			twoCall = json.toJSONString();
		} catch (Throwable e) {
		}
	}
	/**
	 * 设置三级收益
	 * @param money 充值面额（单位：分）
	 * @param fees 收益（单位：分）
	 */
	public void setThreeCall(int money, int fees) {
		if (money <= 0) {
			return;
		}
		
		try {
			JSONObject json = (JSONObject) JsonUtils.fromJson(threeCall);
			json.put(String.valueOf(money), fees);
			threeCall = json.toJSONString();
		} catch (Throwable e) {
		}
	}

	/**
	 * 根据充值话费，获取对应手续费
	 * @param money 要充值的面额
	 * @return
	 */
	public Integer getCallFees(int money) {
		Integer fees = null;
		switch (money) {
		case 5000:
			fees = call_50;
			break;
		case 10000:
			fees = call_100;
			break;
		case 15000:
			fees = call_150;
			break;
		case 20000:
			fees = call_200;
			break;
		case 30000:
			fees = call_300;
			break;
		case 50000:
			fees = call_500;
			break;
		case 100000:
			fees = call_1000;
			break;

		default:
			break;
		}
		return fees;
	}
	
	
	@Override
	public Profit clone() {
		Profit profit = new Profit();
		profit.id = id;
		profit.usersId = usersId;
		profit.oneBalance = oneBalance;
		profit.twoBalance = twoBalance;
		profit.threeBalance = threeBalance;
		profit.oneCall = oneCall;
		profit.twoCall = twoCall;
		profit.threeCall = threeCall;
		profit.oneShop = oneShop;
		profit.twoShop = twoShop;
		profit.threeShop = threeShop;
		profit.call_50 = call_50;
		profit.call_100 = call_100;
		profit.call_150 = call_150;
		profit.call_200 = call_200;
		profit.call_300 = call_300;
		profit.call_500 = call_500;
		profit.call_1000 = call_1000;
		profit.transfer = transfer;
		profit.withdraw = withdraw;
		profit.alipay = alipay;
		profit.kklpay = kklpay;
		profit.auth = auth;
		profit.bank = bank;
		profit.allinpay = allinpay;
		return profit;
	}
	
	@Override
	public void clear() {
		super.clear();
		this.id=null;
		this.usersId=null;
		this.oneBalance = null;
		this.twoBalance = null;
		this.threeBalance = null;
		this.oneCall = null;
		this.twoCall = null;
		this.threeCall = null;
		this.oneShop = null;
		this.twoShop = null;
		this.threeShop = null;
		this.call_50 = null;
		this.call_100 = null;
		this.call_150 = null;
		this.call_200 = null;
		this.call_300 = null;
		this.call_500 = null;
		this.call_1000 = null;
		this.transfer = null;
		this.withdraw = null;
		this.alipay = null;
		this.kklpay = null;
		this.auth = null;
		this.bank = null;
		this.allinpay = null;
	}
	
	
	
	/*
	 * getter\setter
	 */
	public Integer getOneBalance() {
		return oneBalance;
	}
	public Integer getOneTransfer() {
		return oneTransfer;
	}
	public void setOneTransfer(Integer oneTransfer) {
		this.oneTransfer = oneTransfer;
	}
	public Integer getTwoTransfer() {
		return twoTransfer;
	}
	public void setTwoTransfer(Integer twoTransfer) {
		this.twoTransfer = twoTransfer;
	}
	public Integer getThreeTransfer() {
		return threeTransfer;
	}
	public void setThreeTransfer(Integer threeTransfer) {
		this.threeTransfer = threeTransfer;
	}
	public Integer getAllinpay() {
		return allinpay;
	}
	public void setAllinpay(Integer allinpay) {
		this.allinpay = allinpay;
	}
	public Integer getAuth() {
		return auth;
	}
	public void setAuth(Integer auth) {
		this.auth = auth;
	}
	public Integer getOneWithdraw() {
		return oneWithdraw;
	}
	public void setOneWithdraw(Integer oneWithdraw) {
		this.oneWithdraw = oneWithdraw;
	}
	public Integer getTwoWithdraw() {
		return twoWithdraw;
	}
	public void setTwoWithdraw(Integer twoWithdraw) {
		this.twoWithdraw = twoWithdraw;
	}
	public Integer getThreeWithdraw() {
		return threeWithdraw;
	}
	public void setThreeWithdraw(Integer threeWithdraw) {
		this.threeWithdraw = threeWithdraw;
	}
	public void setOneBalance(Integer oneBalance) {
		this.oneBalance = oneBalance;
	}
	public Integer getTwoBalance() {
		return twoBalance;
	}
	public void setTwoBalance(Integer twoBalance) {
		this.twoBalance = twoBalance;
	}
	public Integer getThreeBalance() {
		return threeBalance;
	}
	public void setThreeBalance(Integer threeBalance) {
		this.threeBalance = threeBalance;
	}
	public String getOneCall() {
		return oneCall;
	}
	public void setOneCall(String oneCall) {
		this.oneCall = oneCall;
	}
	public String getTwoCall() {
		return twoCall;
	}
	public void setTwoCall(String twoCall) {
		this.twoCall = twoCall;
	}
	public String getThreeCall() {
		return threeCall;
	}
	public void setThreeCall(String threeCall) {
		this.threeCall = threeCall;
	}
	public Integer getOneShop() {
		return oneShop;
	}
	public void setOneShop(Integer oneShop) {
		this.oneShop = oneShop;
	}
	public Integer getTwoShop() {
		return twoShop;
	}
	public void setTwoShop(Integer twoShop) {
		this.twoShop = twoShop;
	}
	public Integer getThreeShop() {
		return threeShop;
	}
	public void setThreeShop(Integer threeShop) {
		this.threeShop = threeShop;
	}
	public Integer getCall_50() {
		return call_50;
	}
	public void setCall_50(Integer call_50) {
		this.call_50 = call_50;
	}
	public Integer getCall_100() {
		return call_100;
	}
	public void setCall_100(Integer call_100) {
		this.call_100 = call_100;
	}
	public Integer getCall_150() {
		return call_150;
	}
	public void setCall_150(Integer call_150) {
		this.call_150 = call_150;
	}
	public Integer getCall_200() {
		return call_200;
	}
	public void setCall_200(Integer call_200) {
		this.call_200 = call_200;
	}
	public Integer getCall_300() {
		return call_300;
	}
	public void setCall_300(Integer call_300) {
		this.call_300 = call_300;
	}
	public Integer getCall_500() {
		return call_500;
	}
	public void setCall_500(Integer call_500) {
		this.call_500 = call_500;
	}
	public Integer getCall_1000() {
		return call_1000;
	}
	public void setCall_1000(Integer call_1000) {
		this.call_1000 = call_1000;
	}
	public Integer getAlipay() {
		return alipay;
	}
	public void setAlipay(Integer alipay) {
		this.alipay = alipay;
	}
	public Integer getKklpay() {
		return kklpay;
	}
	public void setKklpay(Integer kklpay) {
		this.kklpay = kklpay;
	}
	public Integer getBank() {
		return bank;
	}
	public void setBank(Integer bank) {
		this.bank = bank;
	}
	public Integer getTransfer() {
		return transfer;
	}
	public void setTransfer(Integer transfer) {
		this.transfer = transfer;
	}
	public Integer getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(Integer withdraw) {
		this.withdraw = withdraw;
	}
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	
}
