package cn.jeesoft.mvc.action.web.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.AdminLoginInterceptor;
import cn.jeesoft.mvc.action.web.BaseManageAction;
import cn.jeesoft.mvc.bean.Profit;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.services.ProfitService;

@Controller("manage.profit")
@RequestMapping("manage/profit")
@Before(AdminLoginInterceptor.class)
public class ProfitAction extends BaseManageAction<Profit, ProfitAction.MPagerModel> {
	static class MPagerModel extends PagerModel<Profit, Profit> {
	}

	@Autowired
	private ProfitService profitService;

	// 管理员-进入设置利润比例页面
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, ModelMap modelMap) {
		// 根据usersId查询单个利润比例
		Profit profit = profitService.selectOne(0);
		modelMap.addAttribute("profit", profit);
		modelMap.addAttribute("currentMenu", "默认费率管理");
		return FTL_MANAGE + "sys/profit/detail";
	}

	// 管理员-设置利润比例
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, ModelMap modelMap, Profit bean) {
		// 初始化默认值
		bean.setUsersId(0);
		modelMap.addAttribute("profit", doUpdate(request, modelMap, bean));
		modelMap.addAttribute("currentMenu", "默认费率管理");
		return FTL_MANAGE + "sys/profit/detail";
	}
	
	


	// 会员-进入设置利润比例页面
	@RequestMapping(value = "update_users", method = RequestMethod.GET)
	public String updateUsers(HttpServletRequest request, ModelMap modelMap, Profit bean) {
		// 根据usersId查询单个利润比例
		if (bean.getUsersId()==null || bean.getUsersId()<=0) {
			modelMap.addAttribute("errorMessage", "用户ID不能为空");
		} else {
			bean = profitService.selectOne(bean.getUsersId());
		}
		
		Users users = new Users();
		users.setId(bean.getUsersId());
		users.setPhone(request.getParameter("phone"));
		modelMap.addAttribute("users", users);
		modelMap.addAttribute("profit", bean);
		modelMap.addAttribute("currentMenu", "会员管理");
		return FTL_MANAGE + "sys/profit/detail";
	}
	// 会员-设置利润比例
	@RequestMapping(value = "update_users", method = RequestMethod.POST)
	public String doUpdateUsers(HttpServletRequest request, ModelMap modelMap, Profit bean) {
		if (bean.getUsersId()==null || bean.getUsersId()<=0) {
			modelMap.addAttribute("errorMessage", "用户ID不能为空");
		} else {
			bean = doUpdate(request, modelMap, bean);
		}
		modelMap.addAttribute("profit", bean);
		modelMap.addAttribute("currentMenu", "会员管理");
		return FTL_MANAGE + "sys/profit/detail";
	}

	/**
	 * 更新利润比例
	 */
	private Profit doUpdate(HttpServletRequest request, ModelMap modelMap, Profit bean) {
		// 初始化默认值
		if (StringUtils.isEmpty(bean.getOneBalance())) {
			bean.setOneBalance(0);
		}
		if (StringUtils.isEmpty(bean.getTwoBalance())) {
			bean.setTwoBalance(0);
		}
		if (StringUtils.isEmpty(bean.getThreeBalance())) {
			bean.setThreeBalance(0);
		}
		
		Profit profit = profitService.selectOne(bean.getUsersId());
		
		// 限定比例范围
		if (bean.getOneBalance()>10000 || bean.getOneBalance()<0) {
			modelMap.addAttribute("errorMessage", "比例范围在 0%～100% 之间");
		} else if (bean.getTwoBalance()>10000 || bean.getTwoBalance()<0) {
			modelMap.addAttribute("errorMessage", "比例范围在 0%～100% 之间");
		} else if (bean.getThreeBalance()>10000 || bean.getThreeBalance()<0) {
			modelMap.addAttribute("errorMessage", "比例范围在 0%～100% 之间");
		} else if (bean.getTransfer()!=null && bean.getTransfer() > 10000) {
			modelMap.addAttribute("errorMessage", "比例范围在 0%～100% 之间");
		} else if (bean.getWithdraw()!=null && bean.getWithdraw() > 10000) {
			modelMap.addAttribute("errorMessage", "比例范围在 0%～100% 之间");
		} else {
			// 设置对应话费收益
			profit.setOneCall(5000, StringUtils.toInt(request.getParameter("oneCall50"), 0));
			profit.setOneCall(10000, StringUtils.toInt(request.getParameter("oneCall100"), 0));
			profit.setTwoCall(5000, StringUtils.toInt(request.getParameter("twoCall50"), 0));
			profit.setTwoCall(10000, StringUtils.toInt(request.getParameter("twoCall100"), 0));
			
			bean.setOneCall(profit.getOneCall());
			bean.setTwoCall(profit.getTwoCall());
			bean.setThreeCall(profit.getThreeCall());
			
			// 修改单个利润比例
			int update = profitService.update(bean);
			if (update > 0) {
				modelMap.addAttribute("errorMessage", "操作成功");
			} else {
				modelMap.addAttribute("errorMessage", "操作失败！");
			}
		}
		return profitService.selectOne(bean.getUsersId());
	}

	@Override
	public ProfitService getService() {
		return profitService;
	}
	
}
