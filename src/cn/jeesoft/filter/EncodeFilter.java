package cn.jeesoft.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.NestedServletException;

import cn.jeesoft.core.exception.DbException;
import cn.jeesoft.core.exception.JsonException;
import cn.jeesoft.core.utils.ResponseUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.model.ResultCode;

import com.alibaba.fastjson.JSONObject;

/**
 * 编码过滤器
 * @author king
 */
public class EncodeFilter implements Filter {
	/** 统一编码 */
	private static final String CHARACTER_ENCODING = "UTF-8";
	
	/**
	 * 自动修正编码的自定义请求
	 * @author king
	 */
	private static class EncodeServletRequest extends HttpServletRequestWrapper {

		public EncodeServletRequest(HttpServletRequest request) {
			super(request);
			try {
				setCharacterEncoding(CHARACTER_ENCODING);
			} catch (UnsupportedEncodingException e) {
			}
		}
		
		@Override
		public String getParameter(String name) {
			if (StringUtils.isEmpty(name)) {
				return null;
			}
			String param = StringUtils.trim(super.getParameter(name));
			param = StringUtils.toCharset(param, CHARACTER_ENCODING);
			return param;
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] values = super.getParameterValues(name);
			if (values==null || values.length==0) {
				return null;
			}
			
			for (int i = 0; i < values.length; i++) {
				String value = StringUtils.trim(values[i]);
				values[i] = StringUtils.toCharset(value, CHARACTER_ENCODING);
			}
			return values;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
			@SuppressWarnings("unchecked")
			Enumeration<String> names = super.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				parameterMap.put(name, getParameterValues(name));
			}
			return parameterMap;
		}
		
	}


	public void init(FilterConfig arg0) throws ServletException {

	}
	
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		EncodeServletRequest request = new EncodeServletRequest((HttpServletRequest) req);
		
		try {
			chain.doFilter(request, response);
		} catch (Throwable e) {
			if (e != null && e.getClass().getName().contains("org.apache.jasper.JasperException")) {
				ResponseUtils.sendRedirect((HttpServletRequest)request, (HttpServletResponse)response, "/error/404");
				return;
			}
			doException(request, response, e);
		}
	}
	
	
	private static void doException(ServletRequest request, ServletResponse response, Throwable exception) {
		ResultCode code = ResultCode.FAILURE;
		String message = "服务器异常";
		
		if (exception != null) {
			if (exception instanceof NestedServletException) {
				Throwable cause = exception.getCause();
				if (cause != null) {
					if (cause instanceof DbException) {
						code = ResultCode.ERROE_DB;
						message = cause.getMessage();
						if (StringUtils.isEmpty(message)) {
							message = "数据库操作失败";
						}
					} else if (cause instanceof IllegalArgumentException) {
						code = ResultCode.ERROE_PARAMETER;
						message = "参数错误";
					} else if (exception instanceof JsonException
							|| exception instanceof com.alibaba.fastjson.JSONException) {

						code = ResultCode.ERROE_JSON;
						message = "JSON格式错误";
					}
				}
			} else if (exception instanceof JsonException
					|| exception instanceof com.alibaba.fastjson.JSONException) {

				code = ResultCode.ERROE_JSON;
				message = "JSON格式错误";
			}
		}
		
		System.err.println("["+EncodeFilter.class.getSimpleName()+"]Error: "+exception.getMessage());
		if (exception instanceof NestedServletException) {
			Throwable cause = exception.getCause();
			if (cause != null) {
				cause.printStackTrace(System.err);
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", code.getCode());
		jsonObject.put("message", message);
		ResponseUtils.writer(response, jsonObject);
	}

}
