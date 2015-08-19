package cn.jeesoft.mvc.action.web.manage;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.FileUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.AdminLoginInterceptor;
import cn.jeesoft.mvc.action.web.BaseManageAction;
import cn.jeesoft.mvc.bean.UsersAuth;
import cn.jeesoft.mvc.model.AuthState;
import cn.jeesoft.mvc.services.UsersAuthService;

@Controller("manage.users.auth")
@RequestMapping("manage/users/auth")
@Before(AdminLoginInterceptor.class)
public class UsersAuthAction extends BaseManageAction<UsersAuth, UsersAuthAction.MPagerModel> {
	static class MPagerModel extends PagerModel<UsersAuth, UsersAuth> {
	}
	
	
	@Autowired
	private UsersAuthService usersAuthService;

    @RequestMapping(value="detail", method=RequestMethod.GET)
    public String detail(HttpServletRequest request, ModelMap modelMap, UsersAuth auth)
    		throws Exception {
    	if (auth.getUsersId() == null || auth.getUsersId() == 0) {
    		modelMap.addAttribute("errorMessage", "用户ID 不能为空");
    	} else {
    		UsersAuth selectAuth = getService().selectOne(auth);
        	if (selectAuth != null) {
        		auth = selectAuth;
        	}
		}

    	modelMap.addAttribute("auth", auth);
    	modelMap.addAttribute("currentMenu", "会员管理");
    	
    	return FTL_MANAGE + "users/auth/detail";
    }
    @RequestMapping(value="detail", method=RequestMethod.POST)
    public String update(HttpServletRequest request, ModelMap modelMap, UsersAuth auth)
    		throws Exception {
    	if (StringUtils.isNotEmpty(auth.getIdcard()) && auth.getIdcard().length() > 18) {
    		modelMap.addAttribute("errorMessage", "更新失败，身份证号码格式不正确");
    	} else {
	    	String phone = request.getParameter("phone");
	    	if (!StringUtils.isEmpty(phone)) {
	        	Map<String, File> files = FileUtils.saveMultipartFiles(request, phone);
	        	if (files.containsKey("photo")) {
	        		auth.setPhoto(FileUtils.getRelativePath(files.get("photo")));
	        	}
	        	
	        	int update = getService().update(auth);
	        	modelMap.addAttribute("errorMessage", update>0 ? "更新成功" : "更新失败");
	    	}
    	}

    	if (auth.getUsersId() == null || auth.getUsersId() == 0) {
    		modelMap.addAttribute("errorMessage", "用户ID 不能为空");
    	} else {
    		UsersAuth selectAuth = new UsersAuth();
    		selectAuth.setUsersId(auth.getUsersId());
    		selectAuth = getService().selectOne(selectAuth);
    		if (selectAuth != null) {
    			auth = selectAuth;
    		}
		}

    	modelMap.addAttribute("auth", auth);
    	modelMap.addAttribute("currentMenu", "会员管理");
    	
    	return FTL_MANAGE + "users/auth/detail";
    }
    
    @RequestMapping("list")
    public String list(HttpServletRequest request, ModelMap modelMap, MPagerModel pager) throws Exception {
    	if (pager.getQuery() == null) {
    		pager.setQuery(new UsersAuth());
    	}
    	pager.getQuery().setAuthState(AuthState.WAIT);
    	pager = getService().selectPageList(pager);
    	
    	System.out.println(pager);
    	modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "资料审核");
    	
    	return FTL_MANAGE + "users/auth/list";
    }

	@Override
	public UsersAuthService getService() {
		return usersAuthService;
	}

}
