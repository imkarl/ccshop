package cn.jeesoft.mvc.action.web.manage;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.AdminLoginInterceptor;
import cn.jeesoft.mvc.action.web.BaseManageAction;
import cn.jeesoft.mvc.bean.Admin;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.services.AdminService;
import cn.jeesoft.mvc.services.BaseService;

/**
 * 后台管理
 * @author king
 */
@Controller("manage")
@RequestMapping("manage")
@Before(AdminLoginInterceptor.class)
public class IndexAction extends BaseManageAction<Admin, IndexAction.MPagerModel> {
	static class MPagerModel extends PagerModel<Admin, Admin> {
	}

	
	@Autowired
	private AdminService adminService;
	

    @Before(enable=false)
    @RequestMapping(value="login", method=RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	Admin admin = LoginUserHolder.getLoginAdmin();
		if (admin != null) {
			// 返回HTML文件内容
			sendRedirect(request, response, "/manage/index");
			return null;
		}
    	return FTL_MANAGE+"login";
    }
    @Before(enable=false)
    @RequestMapping(value="login", method=RequestMethod.POST)
    public String login(HttpServletRequest request, Admin bean, ModelMap modelMap) throws Exception {
		modelMap.addAttribute("phone", bean.getPhone());
		
    	if (StringUtils.isEmpty(bean.getName())) {
    		modelMap.addAttribute("errorMessage", "账号不能为空");
        	return FTL_MANAGE+"login";
    	}
    	if (StringUtils.isEmpty(bean.getPass())) {
    		modelMap.addAttribute("errorMessage", "密码不能为空");
    		return FTL_MANAGE+"login";
    	}
    	
    	
    	Admin admin = new Admin();
//    	admin.setPhone(bean.getPhone());
    	admin.setName(bean.getName());
		admin.setPass(Admin.encryption(bean.getPass()));

    	Admin one = getService().selectOne(admin);
    	if (one != null) {
    		one.setPass(null);
    		LoginUserHolder.setLoginAdmin(one);
        	return Redirect+URL_MANAGE+"index";
    	} else {
    		modelMap.addAttribute("errorMessage", "账户或密码不正确");
        	return FTL_MANAGE+"login";
		}
    }
    @Before(enable=false)
    @RequestMapping("logout")
    public String logout() throws Exception {
		if (LoginUserHolder.setLoginAdmin(null)) {
		}
    	return Redirect+URL_MANAGE+"login";
    }

    @RequestMapping(value="update", method=RequestMethod.GET)
    public String update(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	modelMap.addAttribute("currentMenu", "管理员信息");
    	modelMap.addAttribute("admin", LoginUserHolder.getLoginAdmin());
    	return FTL_MANAGE+"admin/update";
    }
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String update(HttpServletRequest request, Admin admin, ModelMap modelMap) throws Exception {
    	Admin loginAdmin = LoginUserHolder.getLoginAdmin();
    	
    	admin.setName(null);
    	admin.setPass(null);  // admin.setPass(Admin.encryption(admin.getPass()));
    	admin.setPortrait(null);
    	admin.setId(loginAdmin.getId());
    	admin.setUpdatetime(new Date());
    	
    	int update = getService().update(admin);
    	if (update > 0) {
    		modelMap.addAttribute("errorMessage", "修改成功");

        	loginAdmin.setEmail(admin.getEmail());
        	loginAdmin.setNickname(admin.getNickname());
        	loginAdmin.setPhone(admin.getPhone());
    		LoginUserHolder.setLoginAdmin(loginAdmin);
    	} else {
    		modelMap.addAttribute("errorMessage", "修改失败");
		}
    	
    	modelMap.addAttribute("currentMenu", "管理员信息");
    	modelMap.addAttribute("admin", LoginUserHolder.getLoginAdmin());
    	return FTL_MANAGE+"admin/update";
    }
    

	@RequestMapping(value={"", "/", "index", "home", "main"})
	public String index(HttpServletRequest request, ModelMap modelMap) throws Exception {
		modelMap.addAttribute("currentMenu", "首页");
    	return FTL_MANAGE+"home";
	}

	@Override
	public BaseService<Admin> getService() {
		return adminService;
	}

}
