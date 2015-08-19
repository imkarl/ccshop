package cn.jeesoft.mvc.helper;

import javax.servlet.http.HttpSession;

import cn.jeesoft.mvc.Config;
import cn.jeesoft.mvc.bean.Admin;
import cn.jeesoft.mvc.bean.Users;

/**
 * 登陆用户
 * @author king
 */
public class LoginUserHolder {

    public static Users getLoginUser(){
        HttpSession session = RequestHolder.getSession();
        return session == null ? null : (Users)session.getAttribute(Config.manage_session_user_info);
    }
    public static boolean setLoginUser(Users users){
        HttpSession session = RequestHolder.getSession();
        if (session != null) {
        	if (users != null) {
        		session.setAttribute(Config.manage_session_user_info, users);
        	} else {
				session.removeAttribute(Config.manage_session_user_info);
			}
        	return true;
        }
        
        return false;
    }
    
    
    

    public static Admin getLoginAdmin(){
        HttpSession session = RequestHolder.getSession();
        return session == null ? null : (Admin)session.getAttribute(Config.manage_session_admin_info);
    }
    public static boolean setLoginAdmin(Admin admin){
        HttpSession session = RequestHolder.getSession();
        if (session != null) {
        	if (admin != null) {
        		session.setAttribute(Config.manage_session_admin_info, admin);
        	} else {
				session.removeAttribute(Config.manage_session_admin_info);
			}
        	return true;
        }
        
        return false;
    }
    
}
