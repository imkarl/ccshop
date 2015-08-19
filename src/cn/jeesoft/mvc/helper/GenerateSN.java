package cn.jeesoft.mvc.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.model.TradeType;

/**
 * 交易编号生成器
 * @author king
 */
public class GenerateSN {
	
	private static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
	private static final Random mRandom = new Random();
	
	private static final String CALL = "CN";
	private static final String TRANSFER = "TN";
	private static final String SHOP = "SN";
	private static final String P2P = "MN";  // money
	private static final String PROFIT = "PN";
	private static final String FEES = "FN";
	private static final String RECHARGE = "RN";
	private static final String WITHDRAW = "WN";
	
//	public static String create(String prefix) {
//		return prefix+mDateFormat.format(new Date())+getSuffix(4);
//	}

	public static String create(TradeType type) {
		String prefix = null;
		switch (type) {
		case CALL:
			prefix = CALL;
			break;
		case TRANSFER:
			prefix = TRANSFER;
			break;
		case SHOP:
			prefix = SHOP;
			break;
		case P2P:
			prefix = P2P;
			break;
		case PROFIT:
			prefix = PROFIT;
			break;
		case FEES:
			prefix = FEES;
			break;
		case RECHARGE:
			prefix = RECHARGE;
			break;
		case WITHDRAW:
			prefix = WITHDRAW;
			break;

		default:
			prefix = null;
			break;
		}
		
		if (StringUtils.isEmpty(prefix)) {
			return null;
		}
		return prefix+mDateFormat.format(new Date())+getSuffix(4);
	}
	public static TradeType get(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}
		
		TradeType type = null;
		if (sn.startsWith(CALL)) {
			type = TradeType.CALL;
		} else if (sn.startsWith(TRANSFER)) {
			type = TradeType.TRANSFER;
		} else if (sn.startsWith(SHOP)) {
			type = TradeType.SHOP;
		} else if (sn.startsWith(P2P)) {
			type = TradeType.P2P;
		} else if (sn.startsWith(PROFIT)) {
			type = TradeType.PROFIT;
		} else if (sn.startsWith(FEES)) {
			type = TradeType.FEES;
		} else if (sn.startsWith(RECHARGE)) {
			type = TradeType.RECHARGE;
		} else if (sn.startsWith(WITHDRAW)) {
			type = TradeType.WITHDRAW;
		}
		return type;
	}
	
	/**
	 * 获取后缀
	 * @return
	 */
	private static String getSuffix(int len) {
		String suffix = "";
		for (int i=0; i<len; i++) {
			suffix += mRandom.nextInt(10);
		}
		return suffix;
	}
	
}
