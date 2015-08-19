package cn.jeesoft.mvc.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.ResponseUtils;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.helper.RequestHolder;
import cn.jeesoft.mvc.services.BaseService;

/**
 * Action基类
 * @author king 
 * @param <M>
 */
public abstract class BaseAction<M extends BaseBean, P extends PagerModel<M, ?>> extends ResponseUtils {

    public static final String Redirect = "redirect:";
    
    public abstract BaseService<M> getService();


    /**
     * 事务回滚
     */
    public static boolean setTransactionRollback() {
    	try {
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        	return true;
		} catch (Exception e) {
        	return false;
		}
	}
    
    /**
     * 获取登陆用户ID
     * @return
     */
    public static Integer getLoginUserId() {
    	Users users = LoginUserHolder.getLoginUser();
    	if (users == null) {
    		return null;
    	}
    	return users.getId();
    }
    
    
    /*
     * getRequest()
     */
    public static HttpServletRequest getRequest() {
    	return RequestHolder.getRequest();
    }
    public static String getParameter(String name) {
    	return getRequest().getParameter(name);
    }
    @SuppressWarnings("rawtypes")
	public static Map getParameterMap() {
    	return getRequest().getParameterMap();
    }
    public static String[] getParameterValues(String name) {
    	return getRequest().getParameterValues(name);
    }
    @SuppressWarnings("rawtypes")
	public static Enumeration getParameterNames() {
    	return getRequest().getParameterNames();
    }
    public static List<String> getParameterNameLists() {
    	List<String> nameList = new ArrayList<String>();
    	
    	@SuppressWarnings("rawtypes")
		Enumeration names = getParameterNames();
    	while (names.hasMoreElements()) {
    		nameList.add(String.valueOf(names.nextElement()));
    	}
    	
    	return nameList;
    }

    /*
     * getSession()
     */
    public static HttpSession getSession() {
    	return RequestHolder.getSession();
    }
    
}
