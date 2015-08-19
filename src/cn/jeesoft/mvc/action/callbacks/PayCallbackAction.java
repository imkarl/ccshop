package cn.jeesoft.mvc.action.callbacks;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.utils.LogUtils;
import cn.jeesoft.core.utils.ResponseUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.core.utils.Txt;
import cn.jeesoft.mvc.Config;
import cn.jeesoft.mvc.action.app.BaseTradeAction;
import cn.jeesoft.mvc.bean.BalanceChange;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.bean.TradeCall;
import cn.jeesoft.mvc.helper.GenerateSN;
import cn.jeesoft.mvc.model.PayType;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.model.TradeState;
import cn.jeesoft.mvc.model.TradeType;
import cn.jeesoft.mvc.outapi.AllinpayHelper;
import cn.jeesoft.mvc.outapi.AllinpayHelper.AllinpayEntity;
import cn.jeesoft.mvc.outapi.CallRechargeHelper;
import cn.jeesoft.mvc.outapi.KklPayHelper;
import cn.jeesoft.mvc.services.TradeBalanceService;
import cn.jeesoft.mvc.services.TradeCallService;

import com.alibaba.fastjson.JSONObject;

/**
 * 支付回调
 * @author king
 */
@RestController("pay")
@RequestMapping("pay")
public class PayCallbackAction extends BaseTradeAction {

	@Autowired
	private TradeCallService tradeCallService;
	@Autowired
	private TradeBalanceService tradeBalanceService;


	/**
	 * 卡卡联支付（NFC支付）
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="kklpay_nfc_notify")
	public String kklpayNfcNotify(HttpServletRequest request) {
    	return payKklNotify(request, PayType.KKLPAY);
    }
	/**
	 * 卡卡联支付（认证支付）
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="kklpay_auth_notify")
	public String kklpayAuthNotify(HttpServletRequest request) {
    	return payKklNotify(request, PayType.AUTH);
    }
    /**
     * 通联支付
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="allinpay_normal_notify")
    public String allinpayNormalNotify(HttpServletRequest request) {
		// 验签是商户为了验证接收到的报文数据确实是支付网关发送的
		AllinpayEntity entity = AllinpayHelper.getEntity(request);
		LogUtils.logFile(entity.toString());
		
		if (entity.isVerify()) {
        	// 更新订单支付信息
        	String sn = entity.getOrderNo();
        	String orderId = entity.getPaymentOrderId();
        	
			//验签成功，还需要判断订单状态，为"1"表示支付成功。
			if(entity.isPaySuccess()){
	        	if (payFinish(sn, orderId, PayType.ALLINPAY)) {
	        		return "true";
	        	}
			} else {
	        	if (payFail(sn, orderId, PayType.ALLINPAY)) {
	        		return "true";
	        	}
			}
		}
    	return "false";
    }
    
    
    /**
     * 卡卡联支付回调
     * @param request
     * @param payType
     * @return
     */
    private String payKklNotify(HttpServletRequest request, PayType payType) {
//    	商户入网代码		支付订单号			交易类型		商户订单ID		交易时间				交易金额
//    	"merchantCode"	"instructCode"	"transType"	"outOrderId"	"transTime"			"totalAmount"
//    	"1000000183"	"150515002063"	"00200"		"356386839588"	"20150515170332"	10
    	JSONObject packet = KklPayHelper.getPacket(request);
    	
    	String md5Key = Config.KKL_KEY;
    	Boolean verify = KklPayHelper.verify(packet, md5Key);
    	if(verify != null && verify) {
        	// 更新订单支付信息
        	String sn = packet.getString("outOrderId");
        	String orderId = packet.getString("instructCode");
        	//Integer money = packet.getIntValue("totalAmount");

    		LogUtils.logFile("KKLPay [payType="+payType.getName()+", sn="+sn+", orderId="+orderId+"]");
        	if (payFinish(sn, orderId, payType)) {
        		return "{\"code\":\"00\"}";
			}
    	}
		return "{\"code\":\"01\",\"msg\":\"verify fail\"}";
    }

    
    /**
     * 支付失败
     * @param sn 系统订单号
     * @param paySn 支付订单号
     * @param payType 支付方式
     * @return
     */
    private boolean payFail(String sn, String paySn, PayType payType) {
    	SysTrade sys = new SysTrade();
    	sys.setSn(sn);
    	sys.setState(TradeState.FAILED);
    	sys.setType(null);
    	sys.setPaySn(paySn);
    	sys.setPayType(payType);
    	sys.setEndTime(new Date());
		// 执行更新
    	int sysUpdate = 0;
    	try {
        	sysUpdate = getService().update(sys);
		} catch (Exception e) {
		}
		return sysUpdate > 0;
    }
	/**
	 * 支付完成
	 * @param sn 系统订单号
	 * @param paySn 支付订单号
	 * @param payType 支付方式
	 * @return
	 */
    private boolean payFinish(String sn, String paySn, PayType payType) {
    	// 查询该订单
    	BalanceChange change = new BalanceChange();
    	change.setSn(sn);
    	change = tradeBalanceService.selectOne(change);
    	
    	if (change != null) {
	    	// 更新充值订单为到账
	    	SysTrade sys = new SysTrade();
	    	sys.setSn(sn);
	    	
	    	sys = getService().selectOne(sys);
	    	if (sys == null) {
	    		System.out.println("订单号找不到");
	    	} else {
	    		if (sys.getState() == TradeState.SUCCESS) {
	    			// 已处理的订单
	    			return true;
	    		} else {
	    			// 未处理的订单
		        	sys.setState(TradeState.SUCCESS);
		        	sys.setType(null);
		        	sys.setPaySn(paySn);
		        	sys.setPayType(payType);
		        	sys.setEndTime(new Date());
		    		// 执行更新
		        	int sysUpdate = 0;
		        	try {
			        	sysUpdate = getService().finish(sys);
					} catch (Exception e) {
					}
		        	
		        	if (sysUpdate > 0) {
		        		// 为用户充值
		        		
		        		// TODO begin 更改余额 应通过 存储过程实现
		        		// 以订单产生的金额为准，可提供付款单号查询实际付款金额，进行人工修正
	//		        	change.setMoney(money);
			        	int changeUpdate = tradeBalanceService.update(change);
			        	if (changeUpdate > 0) {
			        		int finish = tradeBalanceService.finish(change.getSn());
			        		System.out.println("更新余额变动状态："+finish);
	
				        	// 处理关联账单
				        	boolean handle = handleLinkSn(change.getLinkSn());
				        	if (handle) {
				    			return true;
				        	}
			        	}
		        		// TODO end 更改余额 应通过 存储过程实现
			        	
		        	}
	    		}
	    	}
    	}
		return false;
    }
    

	/**
	 * 掉单补单
	 * @param sysTrade 【sn\paySn\payType】
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="restore")
    public String restore(SysTrade sysTrade) {
    	if (StringUtils.isEmpty(sysTrade.getSn())) {
    		return toFailure(ResultCode.FAILURE, "参数不正确");
    	}
    	
    	TradeType type = GenerateSN.get(sysTrade.getSn());
    	if (StringUtils.isEmpty(type)) {
    		return toFailure(ResultCode.FAILURE, "参数不正确");
    	}
    	
    	switch (type) {
		case RECHARGE:
	    	if (payFinish(sysTrade.getSn(), sysTrade.getPaySn(), sysTrade.getPayType())) {
				return ResponseUtils.toSuccess();
	    	}
			break;
		case CALL:
	    	if (handleLinkSn(sysTrade.getSn())) {
				return ResponseUtils.toSuccess();
	    	}
			break;
    	}
    	
		return ResponseUtils.toFailure(ResultCode.FAILURE, "处理失败");
    }
    
    
    /**
     * 处理关联账单
     * @param linkSn
     * @return
     */
    private boolean handleLinkSn(String sn) {
    	if (StringUtils.isEmpty(sn)) {
    		return true;
    	}
    	
    	boolean isHandle = false;
    	TradeType type = GenerateSN.get(sn);
    	switch (type) {
		case CALL:
			// 话费充值
			TradeCall call = new TradeCall();
			call.setSn(sn);
			call = tradeCallService.selectOne(call);
			
			if (call != null) {
				isHandle = CallRechargeHelper.recharge(call.getSn(), call.getCallMoney(), call.getPhone());

				// 修改话费充值订单为已支付/充值
				SysTrade sys = new SysTrade();
				sys.setFromId(call.getFromId());
				sys.setMoney(call.getCallMoney());
				sys.setFeesMoney(call.getMoney()-call.getCallMoney());
				sys.setSn(sn);
				sys.setEndTime(new Date());
//				sys.setPayType(PayType.BALANCE);
				
				// 修改话费充值订单的状态
				if (isHandle) {
					// 修改话费充值订单为已充值
					sys.setState(TradeState.SUCCESS);

		    		// 执行更新
		        	int sysUpdate = 0;
		        	try {
			        	sysUpdate = getService().finish(sys);
					} catch (Exception e) {
					}
					
					if (sysUpdate > 0) {
						// 发起扣款的订单
						BalanceChange change = new BalanceChange();
						change.setCreateTime(new Date());
						change.setFromId(call.getFromId());
						change.setIsAdd(false);
						change.setIsArrival(true);
						change.setLinkSn(call.getSn());
						change.setMoney(call.getMoney());
						change.setSn(GenerateSN.create(TradeType.WITHDRAW));
						tradeBalanceService.deduct(change);
					}
				} else {
					// 修改话费充值订单为已充值
					sys.setState(TradeState.PAYOFF);
					
		    		// 执行更新
		        	int sysUpdate = 0;
		        	try {
			        	sysUpdate = getService().update(sys);
					} catch (Exception e) {
					}
					
				}
			}
			break;
		case TRANSFER:
			// TODO 转账
			break;
		case SHOP:
			// TODO 购物
			break;
		case P2P:
			// TODO P2P金融
			break;

		default:
			isHandle = false;
			break;
		}
    	return isHandle;
    }
    
    
    /**
     * 话费充值
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="recharge_notify")
	public String rechargeNotify(HttpServletRequest request) {
    	String OrderInfo = request.getParameter("Orderinfo");
    	String Sign = request.getParameter("Sign");
    	
    	if (!StringUtils.isEmpty(OrderInfo) && !StringUtils.isEmpty(Sign)) {
    		OrderInfo = StringUtils.toCharset(OrderInfo, "ISO8859-1", "GB2312");
    		
    		String[] orderArray = Txt.split(OrderInfo, "|");
    		if (orderArray.length >= 12) {
        		synchronized (this) {
		    		String sn = orderArray[2];
//		    		boolean isSuccess = "1".equals(orderArray[3]);
//		    		int money = StringUtils.toInt(orderArray[6], 0) / 100;
//		    		Date time = DateUtils.parse(orderArray[10]);
		    		String orderId = orderArray[11];
//		    		
//		    		System.out.println("编号："+orderSn);
//		    		System.out.println("是否成功："+isSuccess);
//		    		System.out.println("到账金额："+money);
//		    		System.out.println("到账时间："+DateUtils.format(time, "yyyy-MM-dd HH:mm:ss"));
//		    		System.out.println("充值编号："+orderId);
		    		
		    		// 更新数据库
		    		TradeCall call = new TradeCall();
		    		call.setSn(sn);
		    		call.setOrderSn(orderId);
		    		call.setIsArrival(true);
		    		int updateId = tradeCallService.update(call);
		    		if (updateId > 0) {
		    			return "OK";
		    		}
    			}
    		}
    	}
    	
		return "ERROR";
	}
	
}
