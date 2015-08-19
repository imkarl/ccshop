package cn.jeesoft.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求处理工具类
 * @author king
 */
public class RequestUtils {

	/**
	 * 获取请求的URL
	 * @param request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request) {
		String url = request.getRequestURI();
		String queryString = request.getQueryString();
		if (!StringUtils.isEmpty(queryString)) {
			url += '?' + queryString;
		}
		return url;
	}
	

}
