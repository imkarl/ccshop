package cn.jeesoft.mvc.action.web.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.interceptor.UsersOrAdminLoginInterceptor;
import cn.jeesoft.mvc.action.web.BaseManageAction;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.model.TradeState;
import cn.jeesoft.mvc.model.TradeType;
import cn.jeesoft.mvc.services.BaseService;
import cn.jeesoft.mvc.services.SysTradeService;

/**
 * 系统交易
 * @author king
 */
@Controller("manage/sys/trade")
@RequestMapping("manage/sys/trade")
@Before(UsersOrAdminLoginInterceptor.class)
public class SysTradeAction extends BaseManageAction<SysTrade, SysTradeAction.MPagerModel> {
	static class MPagerModel extends PagerModel<SysTrade, SysTrade> {
	}
	
	
	@Autowired
	private SysTradeService sysTradeService;

    @RequestMapping("list")
    public String list(HttpServletRequest request, ModelMap modelMap, MPagerModel pager) throws Exception {
    	pager = sysTradeService.selectPageList(pager);
    	
    	modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "全部交易");
    	
    	return FTL_MANAGE + "sys/trade/list";
    }

    @RequestMapping("list_call")
    public String listCall(HttpServletRequest request, ModelMap modelMap, MPagerModel pager) throws Exception {
    	if (pager.getQuery() == null) {
    		pager.setQuery(new SysTrade());
    	}
    	pager.getQuery().setType(TradeType.CALL);
    	pager = sysTradeService.selectPageList(pager);
    	
    	modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "话费充值");
    	
    	return FTL_MANAGE + "sys/trade/list_call";
    }
    
    @RequestMapping("list_recharge")
    public String listRecharge(HttpServletRequest request, ModelMap modelMap, MPagerModel pager) throws Exception {
    	if (pager.getQuery() == null) {
    		pager.setQuery(new SysTrade());
    	}
    	pager.getQuery().setType(TradeType.RECHARGE);
    	pager = sysTradeService.selectPageList(pager);
    	
    	modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "余额充值");
    	
    	return FTL_MANAGE + "sys/trade/list";
    }
    
    @RequestMapping("list_restore")
    public String listRestore(HttpServletRequest request, ModelMap modelMap, MPagerModel pager) throws Exception {
    	if (pager.getQuery() == null) {
    		pager.setQuery(new SysTrade());
        	pager.getQuery().setState(TradeState.PAYOFF);
    	}
    	pager = sysTradeService.selectPageList(pager);
    	
    	modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "掉单补单");
    	
    	return FTL_MANAGE + "sys/trade/list_restore";
    }
    
    @RequestMapping("list_withdraw")
    public String listWithdraw(HttpServletRequest request, ModelMap modelMap, MPagerModel pager) throws Exception {
    	if (pager.getQuery() == null) {
    		pager.setQuery(new SysTrade());
    	}
		pager.getQuery().setType(TradeType.WITHDRAW);
		pager.getQuery().setName("提现");
		
    	pager = sysTradeService.selectPageList(pager);
    	
    	modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "提现申请");
    	
    	return FTL_MANAGE + "sys/trade/list_withdraw";
    }

	@Override
	public BaseService<SysTrade> getService() {
		return sysTradeService;
	}
    
}

