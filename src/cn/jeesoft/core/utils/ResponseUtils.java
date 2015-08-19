package cn.jeesoft.core.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jeesoft.mvc.model.ResultCode;

import com.alibaba.fastjson.JSONObject;

/**
 * 响应处理
 * @author king
 */
public class ResponseUtils {
	

	/**
	 * 重定向（改变地址栏）
	 * @param request
	 * @param response
	 * @param location
	 */
	public static void sendRedirect(HttpServletRequest request, HttpServletResponse response, String location) {
		System.out.println(request.getContextPath()+location);
		
		try {
			response.sendRedirect(request.getContextPath()+location);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 重定向（不改变地址栏）
	 * @param request
	 * @param response
	 * @param location
	 */
	public static void forward(HttpServletRequest request, HttpServletResponse response, String location) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher(location);
			dispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 写入响应信息
	 * @param response
	 * @param responseBody
	 */
	public static void writer(ServletResponse response, JSONObject responseBody) {
		if (responseBody == null || responseBody.isEmpty()) {
			return;
		}

		writer(response, responseBody.toString());
	}
	/**
	 * 写入响应信息
	 * @param response
	 * @param responseBody
	 */
	public static void writer(ServletResponse response, String responseBody) {
		if (responseBody == null || responseBody.isEmpty()) {
			return;
		}

		try {
			response.reset();
			((HttpServletResponse)response).setContentType("text/html;charset=UTF-8");
		} catch (Exception e) {
		}
		try {
			OutputStream output = response.getOutputStream();
			output.write(UnicodeUtils.encodeChina(responseBody).getBytes());
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

    /**
     * 成功返回
     * @return
     */
	public static String toSuccess() {
    	return toSuccess(null);
    }
    /**
     * 成功返回
     * @param data
     * @return
     */
	public static String toSuccess(Object data) {
    	JSONObject result = new JSONObject();
    	result.put("status", ResultCode.SUCCESS.getCode());
    	if (data != null) {
    		result.put("data", data);
    	}
    	//StringUtils.toCharset(result.toJSONString(), "UTF-8", "ISO8859-1");
    	return UnicodeUtils.encodeChina(result.toJSONString());
    }
    /**
     * 失败返回
     * @param resultCode
     * @param errorMessage
     * @param data
     * @return
     */
	public static String toFailure(ResultCode resultCode, Throwable error) {
		String message = null;
		if (error != null) {
			message = error.getMessage();
		}
		if (StringUtils.isEmpty(message)) {
			message = "未知错误";
		}
    	return toFailure(resultCode, message);
    }
    /**
     * 失败返回
     * @param resultCode
     * @param errorMessage
     * @param data
     * @return
     */
	public static String toFailure(ResultCode resultCode, String errorMessage) {
    	return toFailure(resultCode, errorMessage, null);
    }
    /**
     * 失败返回
     * @param resultCode
     * @param errorMessage
     * @param data
     * @return
     */
	public static String toFailure(ResultCode resultCode, String errorMessage, Object data) {
    	JSONObject result = new JSONObject();
    	if (resultCode == null) {
    		resultCode = ResultCode.FAILURE;
    	}
    	result.put("status", resultCode.getCode());
    	result.put("message", errorMessage);
    	if (data != null) {
    		result.put("data", data);
    	}
    	//StringUtils.toCharset(result.toJSONString(), "UTF-8", "ISO8859-1");
    	return UnicodeUtils.encodeChina(result.toJSONString());
    }
	
}
