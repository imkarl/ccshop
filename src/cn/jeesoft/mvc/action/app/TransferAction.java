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
import cn.jeesoft.mvc.bean.Balance;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.bean.TradeTransfer;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.helper.GenerateSN;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.model.TradeState;
import cn.jeesoft.mvc.model.TradeType;
import cn.jeesoft.mvc.services.TradeBalanceService;
import cn.jeesoft.mvc.services.TradeTransferService;
import cn.jeesoft.mvc.services.UsersService;

/**
 * 转账
 * @author king
 */
@RestController("app.transfer")
@RequestMapping("app/transfer")
@Before(UsersLoginInterceptor.class)
public class TransferAction extends BaseTradeAction {
	static class MPagerModel extends PagerModel<TradeTransfer, TradeTransfer> {
	}
	
	
	@Autowired
	private TradeTransferService tradeTransferService;
	@Autowired
	private TradeBalanceService tradeBalanceService;
	@Autowired
	private UsersService usersService;

	/**
	 * 创建订单
     * 参数：money、toId
	 */
    @ResponseBody
    @RequestMapping(value = "insert", method=RequestMethod.POST)
    public String insert(HttpServletRequest request, TradeTransfer transfer) {
    	transfer.setFromId(getLoginUserId());
    	
    	// 参数检查
    	if (transfer.getMoney()==null || transfer.getMoney()<Config.MIN_MONEY_TRANSFER) {
        	return toFailure(ResultCode.ERROE_PARAMETER, "金额不正确");
    	}
    	if (transfer.getToId()==null) {
    		String toPhone = request.getParameter("phone");
    		if (!StringUtils.isPhone(toPhone)) {
            	return toFailure(ResultCode.ERROE_PARAMETER, "收款方手机号不正确");
    		}
    		
    		Users users = new Users();
    		users.setPhone(toPhone);
    		users = usersService.selectOne(users);
    		if (StringUtils.isEmpty(users)) {
            	return toFailure(ResultCode.ERROE_PARAMETER, "收款方手机号不存在");
    		} else if (users.getId() == getLoginUserId()) {
            	return toFailure(ResultCode.ERROE_PARAMETER, "不能转账给自己");
    		} else {
				transfer.setToId(users.getId());
			}
    	}
    	if (transfer.getToId()<=0) {
        	return toFailure(ResultCode.ERROE_PARAMETER, "收款方ID不正确");
		}
    	
    	Balance balance = tradeBalanceService.queryBalance(getLoginUserId());
		// 可用余额不足以取现
		if (balance == null) {
			return toFailure(ResultCode.FAILURE, "余额查询失败，无法转账");
		} else if (balance.getMoney() < transfer.getMoney()) {
			// 可用余额不足以取现
			return toFailure(ResultCode.FAILURE, "可用余额不足，无法转账");
		}
    	
    	// 系统记录
    	SysTrade sys = new SysTrade();
		sys.setSn(GenerateSN.create(TradeType.TRANSFER));
    	sys.setType(TradeType.TRANSFER);
    	sys.setName("转账"+(transfer.getMoney()*0.01)+"元");
    	sys.setFeesMoney(profitService.getHelper().getTransfer(getLoginUserId(), transfer.getMoney()));
    	sys.setMoney(transfer.getMoney()-sys.getFeesMoney());
    	
    	sys.setFromId(getLoginUserId());
    	sys.setToId(transfer.getToId());
    	sys.setState(TradeState.SUCCESS);
    	sys.setCreateTime(new Date());
    	sys.setIsInstant(true);
    	
    	if (isEmptyBean(sys)) {
        	return toFailure(ResultCode.ERROE_PARAMETER, "参数不正确");
    	}
    	
    	// 转账记录
        int sysId = getService().insert(sys);
        if (sysId <= 0) {
        	return toFailure(ResultCode.FAILURE, "创建订单失败");
        } else {
			sys.setId(sysId);
		}
    	
        transfer.setMoney(sys.getMoney());
        transfer.setSn(sys.getSn());
        transfer.setCreateTime(sys.getCreateTime());
        transfer.setIsArrival(true);
        
        // 用户转账记录
        int transferId = tradeTransferService.insert(transfer, sys.getFeesMoney());
        if (transferId <= 0) {
        	getService().deleteById(sysId);
        	setTransactionRollback();
        	return toFailure(ResultCode.FAILURE, "创建订单失败");
        } else {
        	transfer.setId(transferId);

        	// 订单完成（代理收益）
            int finishId = getService().finish(sys);
            if (finishId <= 0) {
            	return toFailure(ResultCode.FAILURE, "订单处理失败");
    		}
	    	return toSuccess(sys);
		}
    }
    
    @ResponseBody
    @RequestMapping("selectList")
    public String selectList(HttpServletRequest request, TradeTransfer query) {
    	query.setFromId(getLoginUserId());
    	return toSuccess(tradeTransferService.selectList(query));
    }
    
}

