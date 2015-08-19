package cn.jeesoft.mvc.action.app;

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
import cn.jeesoft.mvc.action.BaseAppAction;
import cn.jeesoft.mvc.bean.Profit;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.services.ProfitService;

@RestController("app.profit")
@RequestMapping("app/profit")
@Before(UsersLoginInterceptor.class)
public class ProfitAction extends BaseAppAction<Profit, ProfitAction.MPagerModel> {
	static class MPagerModel extends PagerModel<Profit, Profit> {
	}
	
	@Autowired
	private ProfitService profitService;
	

	/**
	 * 查询接口
	 */
	@ResponseBody
	@RequestMapping(value = "selectOne")
	public String selectOne(HttpServletRequest request) {
		// 根据usersId查询单个利润比例
		Profit profit = profitService.selectOne(LoginUserHolder.getLoginUser().getId());
		if(!StringUtils.isEmpty(profit)){
			return toSuccess(profit);
		} else {
			return toFailure(ResultCode.FAILURE, "查询失败");
		}
	}

	/**
	 * 更新接口
	 * @param oneRevenue 一级代理比例数
	 * @param twoRevenue 二级代理比例数
	 * @param threeRevenue 三级代理比例数
	 * @param call 话费充值费率
	 * @param transfer 转账费率
	 * @param withdraw 提现费率
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, Profit bean) {
		// 初始化默认值
		bean.setUsersId(getLoginUserId());
		if (StringUtils.isEmpty(bean.getOneBalance())) {
			bean.setOneBalance(0);
		}
		if (StringUtils.isEmpty(bean.getTwoBalance())) {
			bean.setTwoBalance(0);
		}
		if (StringUtils.isEmpty(bean.getThreeBalance())) {
			bean.setThreeBalance(0);
		}
		System.out.println(bean);
		
		// 限定比例范围
		if (bean.getOneBalance()>10000 || bean.getOneBalance()<0) {
			return toFailure(ResultCode.ERROE_PARAMETER, "比例范围在 0%～100% 之间");
		} else if (bean.getTwoBalance()>10000 || bean.getTwoBalance()<0) {
			return toFailure(ResultCode.ERROE_PARAMETER, "比例范围在 0%～100% 之间");
		} else if (bean.getThreeBalance()>10000 || bean.getThreeBalance()<0) {
			return toFailure(ResultCode.ERROE_PARAMETER, "比例范围在 0%～100% 之间");
		} else if (bean.getTransfer()!=null && bean.getTransfer() > 10000) {
			return toFailure(ResultCode.ERROE_PARAMETER, "比例范围在 0%～100% 之间");
		} else if (bean.getWithdraw()!=null && bean.getWithdraw() > 10000) {
			return toFailure(ResultCode.ERROE_PARAMETER, "比例范围在 0%～100% 之间");
		} else {
			// 修改单个利润比例
			int update = profitService.update(bean);
			if (update > 0) {
				return toSuccess(bean);
			} else {
				return toFailure(ResultCode.ERROE_PARAMETER, "操作失败");
			}
		}
	}
	
	
	@Override
	public ProfitService getService() {
		return profitService;
	}
	
}
