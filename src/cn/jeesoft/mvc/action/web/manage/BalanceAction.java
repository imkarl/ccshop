package cn.jeesoft.mvc.action.web.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.UsersOrAdminLoginInterceptor;
import cn.jeesoft.mvc.action.web.BaseManageAction;
import cn.jeesoft.mvc.bean.BalanceChange;
import cn.jeesoft.mvc.services.BaseService;
import cn.jeesoft.mvc.services.TradeBalanceService;

/**
 * 用户余额
 * @author king
 */
@Controller("manage.balance")
@RequestMapping("/manage/balance")
@Before(UsersOrAdminLoginInterceptor.class)
public class BalanceAction extends BaseManageAction<BalanceChange, BalanceAction.MPagerModel> {
	static class MPagerModel extends PagerModel<BalanceChange, BalanceChange> {
	}
	
	
	@Autowired
	private TradeBalanceService tradeBalanceService;

	/**
	 * 余额变动列表
	 * @param request
	 * @param modelMap
	 * @param pager
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("changes")
    public String changes(HttpServletRequest request, ModelMap modelMap, MPagerModel pager)
    		throws Exception {
    	
		BalanceChange query = pager.getQuery();
		if (query != null) {
			query = new BalanceChange();
			pager.setQuery(query);
		}
		query.setFromId(StringUtils.toInt(request.getParameter("pager.query.fromId"), 0));
		String isArrival = request.getParameter("pager.query.isArrival");
		if (!StringUtils.isEmpty(isArrival)) {
			query.setIsArrival("true".equalsIgnoreCase(isArrival));
		}
    	if (query.getFromId() != null && query.getFromId() > 0) {
        	pager = tradeBalanceService.selectPageList(pager);
		}
		modelMap.addAttribute("pager", pager);
		modelMap.addAttribute("currentMenu", "会员管理");
		
    	return FTL_MANAGE + "balance/changes";
    }

	@Override
	public BaseService<BalanceChange> getService() {
		return tradeBalanceService;
	}

}
