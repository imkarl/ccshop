package cn.jeesoft.mvc.action.app;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.FileUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.UsersLoginInterceptor;
import cn.jeesoft.interceptor.UsersOrAdminLoginInterceptor;
import cn.jeesoft.mvc.action.BaseAppAction;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.bean.UsersAuth;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.services.UsersAuthService;

@RestController("app.users.auth")
@RequestMapping("app/users/auth")
@Before(UsersLoginInterceptor.class)
public class UsersAuthAction extends BaseAppAction<UsersAuth, UsersAuthAction.MPagerModel> {
	static class MPagerModel extends PagerModel<UsersAuth, UsersAuth> {
	}
	
	
	@Autowired
	private UsersAuthService usersAuthService;
    
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, UsersAuth bean) {
    	if (StringUtils.isEmpty(bean.getName())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "姓名不能为空");
    	} else if (StringUtils.isEmpty(bean.getIdcard())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "身份证不能为空");
    	} else if (StringUtils.isEmpty(bean.getBankCard())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "银行卡号不能为空");
    	} else if (StringUtils.isEmpty(bean.getBankName())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "开户银行不能为空");
    	} else if (StringUtils.isEmpty(bean.getBankBranch())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "开户网点不能为空");
    	} else if (StringUtils.isEmpty(bean.getBankAddr())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "开户地址不能为空");
    	}

    	Map<String, File> files = FileUtils.saveMultipartFiles(request, LoginUserHolder.getLoginUser().getPhone());
    	if (files.containsKey("photo")) {
    		bean.setPhoto(FileUtils.getRelativePath(files.get("photo")));
    	} else {
    		return toFailure(ResultCode.ERROE_PARAMETER, "认证照片不能为空");
    	}
    	
    	bean.setUsersId(LoginUserHolder.getLoginUser().getId());
    	bean.setCreateTime(new Date());
    	return super.update(null, bean);
    }
    
    @Before(UsersOrAdminLoginInterceptor.class)
    @ResponseBody
    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public String finish(HttpServletRequest request, UsersAuth auth) {
    	int update = usersAuthService.finish(auth.getUsersId());
    	if (update > 0) {
    		return toSuccess();
    	}
    	return toFailure(ResultCode.FAILURE, "更新失败，请稍后重试");
    }
    
    @Before(UsersOrAdminLoginInterceptor.class)
    @ResponseBody
    @RequestMapping(value = "invalid", method = RequestMethod.POST)
    public String invalid(HttpServletRequest request, UsersAuth auth) {
    	int update = usersAuthService.invalid(auth.getUsersId());
    	if (update > 0) {
    		return toSuccess();
    	}
    	return toFailure(ResultCode.FAILURE, "更新失败，请稍后重试");
    }
    
    @ResponseBody
    @RequestMapping("selectOne")
    @Override
    public String selectOne(HttpServletRequest request, UsersAuth bean) {
    	Users users = LoginUserHolder.getLoginUser();
    	
    	if (bean.getUsersId() == null) {
    		bean.setUsersId(users.getId());
//    		return toFailure(ResultCode.ERROE_PARAMETER, "用户ID 不能为空");
    	}
    	
    	UsersAuth one = getService().selectOne(bean);
    	if (one != null) {
    		one.setAuthState(users.getAuthState());
    		return toSuccess(one);
    	} else {
			return toFailure(ResultCode.ERROE_PARAMETER, "没有记录", one);
		}
    }
    @ResponseBody
    @RequestMapping("list")
    public String list(HttpServletRequest request, MPagerModel pager) {
    	return super.selectPageList(request, pager);
    }

	@Override
	public UsersAuthService getService() {
		return usersAuthService;
	}

}
