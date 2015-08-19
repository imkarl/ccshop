package cn.jeesoft.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import cn.jeesoft.core.interceptor.Interceptor;
import cn.jeesoft.core.utils.RequestUtils;
import cn.jeesoft.core.utils.ResponseUtils;
import cn.jeesoft.mvc.bean.Admin;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.ResultCode;

/**
 * 前台用户or后台管理员登录验证
 * @author king
 */
public class UsersOrAdminLoginInterceptor implements Interceptor {

	@Override
	public boolean intercept(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Users users = LoginUserHolder.getLoginUser();
		Admin admin = LoginUserHolder.getLoginAdmin();

		String url = RequestUtils.getUrl(request);
		System.out.println(url+" -- "+((users == null && admin == null) ? "未登陆" : "已登陆"));

		if (users == null && admin == null) {
			// 判断是否返回消息主体
			if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
				ResponseBody body = ((HandlerMethod) handler).getMethodAnnotation(ResponseBody.class);
				if (body != null) {
					String responseBody = ResponseUtils.toFailure(ResultCode.LOGOUT, "请先登陆");
					ResponseUtils.writer(response, responseBody);
					return false;
				}
			}
			
			// 返回HTML文件内容
			ResponseUtils.sendRedirect(request, response, "/manage/login");
			return false;
		}
		return true;
	}

}
