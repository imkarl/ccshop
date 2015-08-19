package cn.jeesoft.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jeesoft.core.interceptor.Interceptor;
import cn.jeesoft.core.utils.RequestUtils;
import cn.jeesoft.core.utils.ResponseUtils;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.ResultCode;

/**
 * 前台用户登录验证
 * @author king
 */
public class UsersLoginInterceptor implements Interceptor {

	@Override
	public boolean intercept(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Users users = LoginUserHolder.getLoginUser();

		String url = RequestUtils.getUrl(request);
		System.out.println(url+" -- "+(users==null ? "未登陆" : "已登陆"));
		
		if (users == null) {
			String responseBody = ResponseUtils.toFailure(ResultCode.LOGOUT, "请先登陆");
			ResponseUtils.writer(response, responseBody);
			return false;
		}
		return true;
	}

}
