package cn.jeesoft.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * @author king
 */
public interface Interceptor {
	
	/**
	 * 拦截处理器
	 * @return 通行返回true，拦截则返回false
	 * @throws Exception
	 */
	public boolean intercept(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception;
	
}
