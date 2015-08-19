package cn.jeesoft.mvc.helper;

import cn.jeesoft.mvc.bean.Profit;
import cn.jeesoft.mvc.model.PayType;
import cn.jeesoft.mvc.services.ProfitService;

/**
 * 费用计算帮助类
 * @author king
 */
public class ProfitHelper {
	
	private final ProfitService mService;
	
	public ProfitHelper(ProfitService service) {
		this.mService = service;
	}

    /**
     * 获取用户的费率
     * @return
     */
    public Profit findUsers(int usersId) {
		Profit profit = mService.selectOne(usersId);
		return profit;
    }
    public Profit findSys() {
    	return mService.selectOne(0);
    }
    
    /**
     * 计算手续费（按百分比）
     * @param money 交易金额（单位：分）
     * @param percent 百分比（1个单位，表示0.01%）
     * @param min 最小费用（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public static int count(int money, Integer percent, int min) {
    	if (percent==null || Math.abs(percent)>=10000 || percent==0) {
    		// 没有设置则为0
    		return 0;
    	}
    	
    	int value = (int) (money * percent * 0.0001);
    	if (value == 0) {
    		return min;
    	}
    	return value;
    }
    
    
    
    /**
     * 话费充值手续费
     * @param usersId 用户ID
     * @param money 充值面额（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public int getCall(int usersId, int money) {
    	Integer value = findUsers(usersId).getCallFees(money);
    	if (value == null || Math.abs(value)>money) {
    		value = findSys().getCallFees(money);
    	}
    	if (value == null || Math.abs(value)>money) {
    		// 没有设置则为0
    		return 0;
    	}
    	return value;
    }
    /**
     * 转账手续费
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public int getTransfer(int usersId, int money) {
    	Integer percent = findUsers(usersId).getTransfer();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getTransfer();
    	}
    	return count(money, percent, 1);
    }
    /**
     * 取现手续费
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public int getWithdraw(int usersId) {
    	Integer value = findUsers(usersId).getWithdraw();
    	if (value == null) {
    		value = findSys().getWithdraw();
    	}
    	if (value == null) {
    		// 没有设置则为0
    		return 0;
    	}
    	return value;
    }
    

    /**
     * 计算收款手续费
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @param type 交易方式（ALIPAY、KKLPAY、BANK）
     * @return 实际应付手续费（单位：分）
     */
    public int getPayFees(int usersId, int money, PayType type) {
    	int fees = 0;
    	switch (type) {
		case ALIPAY:
			fees = getAlipay(usersId, money);
			break;
		case KKLPAY:
			fees = getKklpay(usersId, money);
			break;
		case BANK:
			fees = getBank(usersId, money);
			break;
		case AUTH:
			fees = getAuth(usersId, money);
		case ALLINPAY:
			fees = getAllinpay(usersId, money);
			break;

		default:
			break;
		}
    	return fees;
    }
    /**
     * 支付宝收款手续费
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public int getAlipay(int usersId, int money) {
    	Integer percent = findUsers(usersId).getAlipay();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getAlipay();
    	}
    	return count(money, percent, 1);
    }
    /**
     * 卡卡联NFC收款手续费
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public int getKklpay(int usersId, int money) {
    	Integer percent = findUsers(usersId).getKklpay();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getKklpay();
    	}
    	return count(money, percent, 1);
    }
    /**
     * 卡卡联认证收款手续费
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public int getAuth(int usersId, int money) {
    	Integer percent = findUsers(usersId).getAuth();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getAuth();
    	}
    	return count(money, percent, 1);
    }
    /**
     * 通联支付收款手续费
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public int getAllinpay(int usersId, int money) {
    	Integer percent = findUsers(usersId).getAllinpay();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getAllinpay();
    	}
    	return count(money, percent, 1);
    }
    
    /**
     * 银联收款手续费
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际应付手续费（单位：分）
     */
    public int getBank(int usersId, int money) {
    	Integer percent = findUsers(usersId).getBank();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getBank();
    	}
    	return count(money, percent, 1);
    }

    /**
     * 一级余额充值收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getOneBalance(int usersId, int money) {
    	Integer percent = findUsers(usersId).getOneBalance();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getOneBalance();
    	}
    	return count(money, percent, 0);
    }
    /**
     * 二级余额充值收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getTwoBalance(int usersId, int money) {
    	Integer percent = findUsers(usersId).getTwoBalance();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getTwoBalance();
    	}
    	return count(money, percent, 0);
    }
    /**
     * 三级余额充值收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getThreeBalance(int usersId, int money) {
    	Integer percent = findUsers(usersId).getThreeBalance();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getThreeBalance();
    	}
    	return count(money, percent, 0);
    }

    /**
     * 一级余额提现收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getOneWithdraw(int usersId, int money) {
    	Integer value = findUsers(usersId).getOneWithdraw();
    	if (value == null) {
    		value = findSys().getOneWithdraw();
    	}
    	if (value == null) {
    		// 没有设置则为0
    		return 0;
    	}
    	return value;
    }
    /**
     * 二级余额提现收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getTwoWithdraw(int usersId, int money) {
    	Integer value = findUsers(usersId).getTwoWithdraw();
    	if (value == null) {
    		value = findSys().getTwoWithdraw();
    	}
    	if (value == null) {
    		// 没有设置则为0
    		return 0;
    	}
    	return value;
    }
    /**
     * 三级余额提现收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getThreeWithdraw(int usersId, int money) {
    	Integer value = findUsers(usersId).getThreeWithdraw();
    	if (value == null) {
    		value = findSys().getThreeWithdraw();
    	}
    	if (value == null) {
    		// 没有设置则为0
    		return 0;
    	}
    	return value;
    }
    
    /**
     * 一级转账收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getOneTransfer(int usersId, int money) {
    	Integer percent = findUsers(usersId).getOneTransfer();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getOneTransfer();
    	}
    	return count(money, percent, 0);
    }
    /**
     * 二级转账收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getTwoTransfer(int usersId, int money) {
    	Integer percent = findUsers(usersId).getTwoTransfer();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getTwoTransfer();
    	}
    	return count(money, percent, 0);
    }
    /**
     * 三级转账收益
     * @param usersId 用户ID
     * @param money 交易金额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getThreeTransfer(int usersId, int money) {
    	Integer percent = findUsers(usersId).getThreeTransfer();
    	if (percent == null || Math.abs(percent)>10000) {
    		percent = findSys().getThreeTransfer();
    	}
    	return count(money, percent, 0);
    }

    /**
     * 一级话费充值收益
     * @param usersId 用户ID
     * @param money 充值面额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getOneCall(int usersId, int money) {
    	Integer value = findUsers(usersId).getOneCall(money);
    	if (value == null) {
    		value = findSys().getOneCall(money);
    	}
    	if (value == null) {
    		// 没有设置则为0
    		return 0;
    	}
    	return value;
    }
    /**
     * 二级话费充值收益
     * @param usersId 用户ID
     * @param money 充值面额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getTwoCall(int usersId, int money) {
    	Integer value = findUsers(usersId).getTwoCall(money);
    	if (value == null) {
    		value = findSys().getTwoCall(money);
    	}
    	if (value == null) {
    		// 没有设置则为0
    		return 0;
    	}
    	return value;
    }
    /**
     * 三级话费充值收益
     * @param usersId 用户ID
     * @param money 充值面额（单位：分）
     * @return 实际收益（单位：分）
     */
    public int getThreeCall(int usersId, int money) {
    	Integer value = findUsers(usersId).getThreeCall(money);
    	if (value == null) {
    		value = findSys().getThreeCall(money);
    	}
    	if (value == null) {
    		// 没有设置则为0
    		return 0;
    	}
    	return value;
    }
    
	
}
