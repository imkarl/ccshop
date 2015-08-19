package cn.jeesoft.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限拦截器
 * <p>
 * 在需要验证的方法，添加注解
 * </p>
 * 
 * @author king
 */
public class InterceptorAdapter extends HandlerInterceptorAdapter {

	/**
	 * 方法执行前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// 检测是否添加过注解
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			// 获取方法注解
			Before before = ((HandlerMethod) handler).getMethodAnnotation(Before.class);
			if (before == null) {
				if (((HandlerMethod) handler).getBean().getClass().isAnnotationPresent(Before.class)) {
					// 获取类注解
					before = ((HandlerMethod) handler).getBean().getClass().getAnnotation(Before.class);
				}
			}
			
			// 分发处理
			if (before != null) {
				// 判断是否启用拦截
				if (before.enable()) {
					Class<? extends Interceptor>[] interceptors = before.value();
					if (interceptors != null) {
						for (Class<? extends Interceptor> interceptor : interceptors) {
							if (!interceptor.isInterface()) {
								boolean isThrough = interceptor.newInstance().intercept(request, response, handler);
								if (!isThrough) {
									return false;
								}
							}
						}
					}
				}
			}
			
		}

		return true;
	}
	
}
