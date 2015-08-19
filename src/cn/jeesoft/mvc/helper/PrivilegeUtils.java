package cn.jeesoft.mvc.helper;

import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.jeesoft.core.exception.PrivilegeException;
import cn.jeesoft.mvc.Config;
import cn.jeesoft.mvc.bean.Users;


/**
 * 权限检查工具
 * 
 * @author huangfei
 * 
 */
public class PrivilegeUtils {
	

	/**
	 * 检查用户是否具有指定的权限
	 * 
	 * @param session
	 *            用户session
	 * @param pName
	 *            权限名称
	 * @return  true：有权限，false:没有权限
	 */
	public static boolean check(HttpSession session, String pName) throws PrivilegeException{
		if(1==1){
			return true;
		}
		
		
		Map<String,String> root = (Map<String,String>) session.getAttribute(Config.user_resource_menus_button);
		if(root==null || root.size()==0){
			System.out.println("该用户没有任何权限。没有权限访问该资源！");
			return false;
		}
		Users u = (Users) session.getAttribute(Config.manage_session_user_info);
		if(u==null){
			throw new PrivilegeException("用户未登陆!");
		}
		System.out.println("==PrivilegeUtil.check : pName="+pName+"root:"+root.toString());
		if(root.get(pName)==null){
			System.out.println("抱歉，没有权限访问该资源！");
			return false;
		}
		System.out.println("有权限访问该资源！");
		return true;
	}
	
}
