package cn.jeesoft.mvc.action.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.JsonUtils;
import cn.jeesoft.core.utils.ResponseUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.UsersLoginInterceptor;
import cn.jeesoft.interceptor.UsersOrAdminLoginInterceptor;
import cn.jeesoft.mvc.Config;
import cn.jeesoft.mvc.bean.Balance;
import cn.jeesoft.mvc.bean.BalanceChange;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.bean.UsersAuth;
import cn.jeesoft.mvc.helper.GenerateSN;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.PayType;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.model.TradeState;
import cn.jeesoft.mvc.model.TradeType;
import cn.jeesoft.mvc.services.TradeBalanceService;
import cn.jeesoft.mvc.services.UsersAuthService;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户余额
 * @author king
 */
@RestController("app.balance")
@RequestMapping("app/balance")
@Before(UsersLoginInterceptor.class)
public class BalanceAction extends BaseTradeAction {
	static class MPagerModel extends PagerModel<BalanceChange, BalanceChange> {
	}
	
	
	@Autowired
	private TradeBalanceService tradeBalanceService;
	@Autowired
	private UsersAuthService usersAuthService;

	/**
	 * 创建订单（充值or提现）
	 * 参数：{type}[充值或取现]、money[交易金额]、payType[充值方式]、linkSn[关联订单]、remark[备注]
	 */
    @ResponseBody
    @RequestMapping(value="insert/{type}.do", method=RequestMethod.POST)
    public String insert(HttpServletRequest request, SysTrade sys, BalanceChange change, @PathVariable String type) {
    	if (StringUtils.isEmpty(type)) {
        	return toFailure(ResultCode.ERROE_PARAMETER, "无效的接口");
    	}
    	
    	// 关联订单处理
    	if (!StringUtils.isEmpty(change.getLinkSn())) {
	    	BalanceChange queryChange = new BalanceChange();
	    	queryChange.setIsAdd(true);
	    	queryChange.setFromId(sys.getFromId());
	    	queryChange.setLinkSn(change.getLinkSn());

    		// 判断是否重复提交
	    	queryChange = tradeBalanceService.selectOne(change);
	    	if (queryChange != null) {
	    		return toSuccess(queryChange);
	    	}
	    	
	    	SysTrade querySysTrade = new SysTrade();
	    	querySysTrade.setSn(change.getLinkSn());

	    	// 判断关联订单是否存在
	    	querySysTrade = getService().selectOne(querySysTrade);
	    	if (querySysTrade != null) {
	    		// 充值金额=交易金额+手续费
	    		sys.setMoney(querySysTrade.getMoney() + querySysTrade.getFeesMoney());
	    	} else {
	    		return toFailure(ResultCode.ERROE_PARAMETER, "不存在的订单");
	    	}
    	}
    	
    	Integer usersId = LoginUserHolder.getLoginUser().getId();
    	
    	TradeType tradeType = null;
    	String sn = null;
    	String name = null;
    	Integer fees = null;
    	if (TradeType.RECHARGE.name().equalsIgnoreCase(type)) {
        	// 充值金额范围
        	if (sys.getMoney()==null || sys.getMoney()<Config.MIN_MONEY_RECHARGE || sys.getMoney()>Config.MAX_MONEY_RECHARGE) {
            	return toFailure(ResultCode.ERROE_PARAMETER, "无效的金额");
        	}
        	
    		// 充值
    		tradeType = TradeType.RECHARGE;
    		sn = GenerateSN.create(TradeType.RECHARGE);
    		name = "余额充值"+((double)sys.getMoney()*0.01)+"元";
    		
    		// 根据支付方式，计算费率
    		if (sys.getPayType() == null || sys.getPayType() == PayType.BALANCE || sys.getPayType() == PayType.UNKNOWN) {
    			return toFailure(ResultCode.ERROE_PARAMETER, "请选择正确的支付方式");
    		} else {
        		// 如果有关联订单，则手续费另计；无关联订单，则手续费从充值金额扣除
        		if (!StringUtils.isEmpty(change.getLinkSn())) {
        			// TODO 充值金额*费率＝手续费，充值金额＝交易金额
        			fees = profitService.getHelper().getPayFees(getLoginUserId(), sys.getMoney(), sys.getPayType());
            		sys.setMoney(sys.getMoney());
        		} else {
        			// TODO 交易金额*费率＝手续费，充值金额＝交易金额-手续费
        			fees = profitService.getHelper().getPayFees(getLoginUserId(), sys.getMoney(), sys.getPayType());
            		sys.setMoney(sys.getMoney() - fees);
        		}
    		}
    	} else if (TradeType.WITHDRAW.name().equalsIgnoreCase(type)) {
    		// 取现
    		tradeType = TradeType.WITHDRAW;
    		sn = GenerateSN.create(TradeType.WITHDRAW);
    		name = "提现"+((double)sys.getMoney()/100)+"元";
    		fees = profitService.getHelper().getWithdraw(usersId);
    		sys.setMoney(sys.getMoney() - fees);

        	// 最低取现金额
        	if (sys.getMoney()==null || sys.getMoney()<Config.MIN_MONEY_WITHDRAW) {
            	return toFailure(ResultCode.ERROE_PARAMETER, "无效的金额");
        	}
        	
        	UsersAuth auth = new UsersAuth();
        	auth.setUsersId(usersId);
        	auth = usersAuthService.selectOne(auth);
    		sys.setRemark(auth.getBankInfo().toJSONString());
    		
    		Balance balance = tradeBalanceService.queryBalance(usersId);
			// 可用余额不足以取现
    		if (balance == null) {
				return toFailure(ResultCode.FAILURE, "余额查询失败，无法申请提现");
    		} else if (balance.getMoney() < sys.getMoney()+fees) {
    			// 可用余额不足以取现
				return toFailure(ResultCode.FAILURE, "可用余额不足，无法提现");
    		} else {
				// 锁定余额
    			int updateLock = tradeBalanceService.lockBalance(usersId, sys.getMoney()+fees);
    			if (updateLock <= 0) {
    				return toFailure(ResultCode.FAILURE, "扣款失败，无法申请提现");
    			}
			}
    	}
    	
    	if (tradeType != null) {
    		sys.setSn(sn);
        	sys.setType(tradeType);
        	sys.setName(name);
        	sys.setFeesMoney(fees);
        	
        	sys.setFromId(LoginUserHolder.getLoginUser().getId());
        	sys.setToId(0);
        	sys.setState(TradeState.CREATED);
        	sys.setCreateTime(new Date());
        	sys.setIsInstant(true);

        	// 执行创建
    		return doInsert(sys, change, tradeType);
    	}
    	return toFailure(ResultCode.ERROE_PARAMETER, "无效的接口");
    }
    
    private String doInsert(SysTrade sys, BalanceChange change, TradeType type) {
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
    	
        change.setFromId(sys.getFromId());
        change.setCreateTime(sys.getCreateTime());
        change.setIsAdd(type == TradeType.RECHARGE);
        change.setMoney(sys.getMoney());
        change.setSn(sys.getSn());
        
        // 用户充值记录
        int callId = tradeBalanceService.insert(change);
        if (callId <= 0) {
        	setTransactionRollback();
        	return toFailure(ResultCode.FAILURE, "创建订单失败");
        } else {
        	change.setId(callId);
	    	return toSuccess(sys);
		}
    }
    
    /**
     * 完成支付
     * 参数：sn、payType、paySn
     */
    @ResponseBody
    @RequestMapping(value = "payoff", method = RequestMethod.POST)
    public String update(HttpServletRequest request, SysTrade sys) {
    	SysTrade sysTemp = new SysTrade();
    	sysTemp.setSn(sys.getSn());
    	sysTemp = getService().selectOne(sysTemp);
    	if (sysTemp == null) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "订单不存在");
    	} else if (sysTemp.getState() == TradeState.SUCCESS) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "已完成的订单");
		}
    	
    	sysTemp.setId(sys.getId());
    	sysTemp.setSn(sys.getSn());
    	sysTemp.setState(TradeState.PAYOFF);
    	sysTemp.setEndTime(new Date());
    	sysTemp.setPayType(sys.getPayType());
    	sysTemp.setPaySn(sys.getPaySn());

        // 完成支付，更新交易记录
        int sysId = getService().update(sysTemp);
        if (sysId <= 0) {
        	return toFailure(ResultCode.FAILURE, "更新订单失败，请联系管理员补单");
        } else {
	    	return toSuccess("支付成功，订单已完成");
		}
    }
    

	/**
	 * 提现完成
	 * @param sysTrade 【sn\paySn\payType】
	 * @return
	 */
    @Before(UsersOrAdminLoginInterceptor.class)
    @ResponseBody
    @RequestMapping(value="withdraw")
    public String withdraw(SysTrade sysTrade) {
    	if (sysTrade.getState() == null) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "参数不正确");
    	}
    	if (sysTrade.getState() != TradeState.SUCCESS
    			&& sysTrade.getState() != TradeState.FAILED) {
    		System.out.println(sysTrade.getState());
    		return toFailure(ResultCode.ERROE_PARAMETER, "参数不正确");
    	}
    	
    	// 查询该订单
    	BalanceChange change = new BalanceChange();
    	change.setSn(sysTrade.getSn());
    	change = tradeBalanceService.selectOne(change);
    	
    	if (change != null) {
	    	// 更新订单为到账
	    	SysTrade sys = new SysTrade();
	    	sys.setSn(sysTrade.getSn());
	    	
	    	sys = getService().selectOne(sys);
	    	if (sys == null) {
	    		return toFailure(ResultCode.FAILURE, "订单号找不到");
	    	} else {
	    		if (sys.getState() == TradeState.SUCCESS
	    				|| sys.getState() == TradeState.FAILED) {
	    			// 已处理的订单
	    			return ResponseUtils.toSuccess();
	    		} else {
	    			// 未处理的订单
		        	sys.setState(sysTrade.getState());
		        	sys.setType(null);
		        	if (sysTrade.getState() == TradeState.SUCCESS) {
			        	sys.setPaySn(sysTrade.getPaySn());
			    		sys.setPayType(PayType.BALANCE);
		        	}
		        	sys.setEndTime(new Date());
		    		// 执行更新
		        	int sysUpdate = getService().finish(sys);
		        	
		        	if (sysUpdate > 0) {
		        		change.setMoney(sys.getMoney() + sys.getFeesMoney()); // 变动金额＝交易金额+手续费
		        		
		        		int changeUpdate;
		        		if (sys.getState()==TradeState.SUCCESS) {
			        		// 扣除余额：从锁定余额扣除
			        		change.setLinkSn("0000"); // 从锁定余额中扣款
				        	changeUpdate = tradeBalanceService.update(change);
		        		} else {
		        			// 是否锁定余额：从锁定余额变为可用余额
		        			changeUpdate = tradeBalanceService.releaseBalance(change.getFromId(), change.getMoney());
		        		}
			        	if (changeUpdate > 0) {
			        		int finish = tradeBalanceService.finish(change.getSn());
			        		System.out.println("更新余额变动状态："+finish);
			    			return ResponseUtils.toSuccess();
			        	}
		        	}
	    		}
	    	}
    	}
		return toFailure(ResultCode.FAILURE, "处理失败");
    }
    
    @ResponseBody
    @RequestMapping("selectList")
    public String selectList(HttpServletRequest request, BalanceChange bean) {
    	bean.setFromId(LoginUserHolder.getLoginUser().getId());
    	return toSuccess(tradeBalanceService.selectList(bean));
    }
    
    @Before(UsersOrAdminLoginInterceptor.class)
    @ResponseBody
    @RequestMapping("queryOne")
    public String queryOne(HttpServletRequest request, Users users) {
    	if (users != null && users.getId() != null && users.getId() > 0) {
    		Balance balance = tradeBalanceService.queryBalance(users.getId());
    		
    		JSONObject json = (JSONObject) JsonUtils.toJson(balance);
    		json.put("withdrawMoney", tradeBalanceService.queryWithdraw(users.getId()));
    		return toSuccess(json);
    	} else {
			return toFailure(ResultCode.ERROE_PARAMETER, "用户ID不正确");
		}
    }

}
