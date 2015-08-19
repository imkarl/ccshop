package cn.jeesoft.mvc.action.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.interceptor.UsersLoginInterceptor;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.helper.LoginUserHolder;

/**
 * 系统交易
 * @author king
 */
@Controller("app/sys/trade")
@RequestMapping("app/sys/trade")
@Before(UsersLoginInterceptor.class)
public class SysTradeAction extends BaseTradeAction {
	
	@ResponseBody
    @RequestMapping("list")
    public String list(HttpServletRequest request, ModelMap modelMap, MPagerModel pager) throws Exception {
		if (pager.getQuery() == null) {
			pager.setQuery(new SysTrade());
		}
		pager.getQuery().setFromId(LoginUserHolder.getLoginUser().getId());
    	pager = getService().selectPageList(pager);
    	return toSuccess(pager);
    }
    
}

