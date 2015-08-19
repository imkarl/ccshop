package cn.jeesoft.mvc.action.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.UsersLoginInterceptor;
import cn.jeesoft.mvc.Config;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.bean.TradeCall;
import cn.jeesoft.mvc.helper.GenerateSN;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.model.TradeState;
import cn.jeesoft.mvc.model.TradeType;
import cn.jeesoft.mvc.outapi.CallRechargeHelper;
import cn.jeesoft.mvc.services.TradeCallService;

/**
 * 话费充值
 * @author king
 */
@RestController("app.call")
@RequestMapping("app/call")
@Before(UsersLoginInterceptor.class)
public class CallAction extends BaseTradeAction {
	static class MPagerModel extends PagerModel<TradeCall, TradeCall> {
	}

	
	@Autowired
	private TradeCallService tradeCallService;

	/**
	 * 创建订单
	 */
    @ResponseBody
    @RequestMapping(value = "insert", method=RequestMethod.POST)
    public String insert(HttpServletRequest request, SysTrade sys, TradeCall call) {
    	if (call.getCallMoney() == null || call.getCallMoney() < Config.MIN_MONEY_CALL) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "充值金额不正确");
    	}
    	if (!StringUtils.isPhone(call.getPhone())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "手机号不正确");
    	}
    	
    	sys.setFromId(LoginUserHolder.getLoginUser().getId());
    	sys.setToId(0);
    	sys.setType(TradeType.CALL);
    	sys.setState(TradeState.CREATED);
    	sys.setIsInstant(true);
    	sys.setName("充值"+((double)call.getCallMoney()/100)+"元--["+call.getPhone()+"]");
    	sys.setSn(GenerateSN.create(TradeType.CALL));
    	sys.setCreateTime(new Date());
    	// 金额
    	sys.setMoney(call.getCallMoney()); // 交易金额
		Integer fees = profitService.getHelper().getCall(getLoginUserId(), call.getCallMoney());
    	sys.setFeesMoney(fees);  // 手续费
    	call.setMoney(call.getCallMoney() + fees); // 总支付金额

    	if (isEmptyBean(sys)) {
        	return toFailure(ResultCode.ERROE_PARAMETER, "参数不正确");
    	}
    	
    	// 创建交易记录
        int sysId = getService().insert(sys);
        if (sysId <= 0) {
        	return toFailure(ResultCode.FAILURE, "创建订单失败");
        } else {
			sys.setId(sysId);
		}
    	
        call.setFromId(sys.getFromId());
        call.setCreateTime(sys.getCreateTime());
        call.setSn(sys.getSn());
        
        // 用户充值记录
        int callId = tradeCallService.insert(call);
        if (callId <= 0) {
        	setTransactionRollback();
        	return toFailure(ResultCode.FAILURE, "创建订单失败");
        } else {
        	call.setId(callId);
	    	return toSuccess(call);
		}
    }
    
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, SysTrade sys) {
    	TradeCall call = new TradeCall();
    	call.setSn(sys.getSn());
    	call = tradeCallService.selectOne(call);
    	
    	if (call == null) {
        	return toFailure(ResultCode.FAILURE, "订单不存在");
//		} else if (call.getIsArrival()) {
//			return toFailure(ResultCode.FAILURE, "重复订单");
		}
    	
    	SysTrade sysTemp = new SysTrade();
    	sysTemp.setId(sys.getId());
    	sysTemp.setSn(sys.getSn());
    	sysTemp.setState(TradeState.SUCCESS);
    	sysTemp.setEndTime(new Date());
    	sysTemp.setPayType(sys.getPayType());
    	sysTemp.setPaySn(sys.getPaySn());

    	// 更新交易记录
        int sysId = getService().update(sysTemp);
        if (sysId <= 0) {
        	return toFailure(ResultCode.FAILURE, "更新订单失败，请联系管理员补单");
		}
    	
        // 用户充值
        if (call.getIsArrival()) {
			return toFailure(ResultCode.FAILURE, "已完成的订单");
		}
		boolean isRecharge = CallRechargeHelper.recharge(sysTemp.getSn(), call.getCallMoney(), call.getPhone());
        if (!isRecharge) {
        	return toFailure(ResultCode.FAILURE, "订单已完成，充值失败");
        } else {
	    	return toSuccess("充值成功");
		}
    }
    
    @ResponseBody
    @RequestMapping("selectList")
    public String selectList(HttpServletRequest request, TradeCall bean) {
    	TradeCall query = new TradeCall();
    	query.setFromId(LoginUserHolder.getLoginUser().getId());
    	if (!StringUtils.isEmpty(bean.getPhone())) {
    		query.setPhone(bean.getPhone());
    	}
    	if (!StringUtils.isEmpty(bean.getSn())) {
    		query.setSn(bean.getSn());
    	}
    	return toSuccess(tradeCallService.selectList(query));
    }

}
