package cn.jeesoft.mvc.action.web.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.resolver.FormBean;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.AdminLoginInterceptor;
import cn.jeesoft.mvc.action.web.BaseManageAction;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.bean.UsersRoles;
import cn.jeesoft.mvc.services.UsersRolesService;

/**
 * 后台用户权限管理
 * @author king
 */
@Controller("manage.users.roles")
@RequestMapping("manage/users/roles")
@Before(AdminLoginInterceptor.class)
public class UsersRolesAction extends BaseManageAction<UsersRoles, UsersRolesAction.MPagerModel> {
	static class MPagerModel extends PagerModel<UsersRoles, Users> {
	}

	@Autowired
	private UsersRolesService usersRolesService;


    @RequestMapping(value="list")
    public String list(HttpServletRequest request, ModelMap modelMap, @FormBean("pager") MPagerModel pager)
    		throws Exception {
    	pager = getService().selectPageList(pager);
    	
    	modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "权限管理");
    	
    	return FTL_MANAGE + "users/roles/list";
    }

    @RequestMapping(value="update")
    public String update(HttpServletRequest request, ModelMap modelMap, UsersRoles bean)
    		throws Exception {
    	bean = getService().selectOne(bean.getUsersId());
    	
    	modelMap.addAttribute("roles", bean);
    	modelMap.addAttribute("currentMenu", "权限管理");
    	
    	return FTL_MANAGE + "users/roles/update";
    }
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String updatePost(HttpServletRequest request, ModelMap modelMap, UsersRoles bean)
    		throws Exception {
    	bean.setHasUpdateProfit(StringUtils.toInt(request.getParameter("hasUpdateProfit"), 0));
    	bean.setHasInvite(StringUtils.toInt(request.getParameter("hasInvite"), 0));
    	bean.setHasShop(StringUtils.toInt(request.getParameter("hasShop"), 0));
    	bean.setHasP2p(StringUtils.toInt(request.getParameter("hasP2p"), 0));
    	bean.setHasCall(StringUtils.toInt(request.getParameter("hasCall"), 0));
    	
    	int update = getService().update(bean);
    	bean = getService().selectOne(bean.getUsersId());

    	modelMap.addAttribute("errorMessage", update>0 ? "操作成功" : "操作失败，请稍后重试");
    	modelMap.addAttribute("roles", bean);
    	modelMap.addAttribute("currentMenu", "权限管理");
    	return FTL_MANAGE + "users/roles/update";
    }
	
	
	@Override
	public UsersRolesService getService() {
		return usersRolesService;
	}
	
}
