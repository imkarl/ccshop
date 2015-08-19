package cn.jeesoft.mvc.action.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.BaseBean.State;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.util.MD5;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.UsersOrAdminLoginInterceptor;
import cn.jeesoft.mvc.action.BaseAppAction;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.AuthState;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.services.UsersService;

@RestController("app.users")
@RequestMapping("app/users")
@Before(UsersOrAdminLoginInterceptor.class)
public class UsersAction extends BaseAppAction<Users, UsersAction.MPagerModel> {
	static class MPagerModel extends PagerModel<Users, Users> {
	}
	
	
	@Autowired
	private UsersService usersService;

	@Before(enable=false)
    @ResponseBody
    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public String insert(HttpServletRequest request, Users bean) {
		if (bean == null) {
        	return toFailure(ResultCode.FAILURE, "请提供正确的参数");
    	} else if (StringUtils.isEmpty(bean.getPhone())
    			|| StringUtils.isEmpty(bean.getPass())) {
        	return toFailure(ResultCode.FAILURE, "账户或密码不能为空", bean);
    	} else if (!StringUtils.isPhone(bean.getPhone())) {
        	return toFailure(ResultCode.FAILURE, "手机格式不正确", bean);
    	} else if (bean.getPass().length() != 32) {
        	return toFailure(ResultCode.FAILURE, "密码格式不正确", bean);
    	}
		
    	bean.setCreatetime(new Date());
		bean.setPass(bean.getPass());
		// 生成随机昵称
		if (StringUtils.isEmpty(bean.getNickname())) {
			bean.setNickname(StringUtils.getRandomStr(8));
		}
		// 注册时无需审核
    	bean.setState(State.YES);
    	bean.setAuthState(AuthState.NO);
        return super.insert(null, bean);
    }
    
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, Users bean) {
    	bean.setUpdatetime(new Date());
    	if (!StringUtils.isEmpty(bean.getPass())) {
    		bean.setPass(Users.encryption(bean.getPass()));
    	}
    	return super.update(null, bean);
    }
    @ResponseBody
    @RequestMapping("selectList")
    @Override
    public String selectList(HttpServletRequest request, Users bean) {
    	if (!StringUtils.isEmpty(bean.getPass())) {
    		bean.setPass(Users.encryption(bean.getPass()));
    	}
    	return super.selectList(request, bean);
    }
    @ResponseBody
    @RequestMapping("selectOne")
    @Override
    public String selectOne(HttpServletRequest request, Users bean) {
//    	if (!StringUtils.isEmpty(bean.getPass())) {
//    		bean.setPass(Users.encryption(bean.getPass()));
//    	}
    	bean.setPass(null);
    	
    	Users one = getService().selectOne(bean);
    	if (one != null) {
    		one.setPass(null);
    		return toSuccess(one);
    	} else {
			return toFailure(ResultCode.ERROE_PARAMETER, "没有记录", one);
		}
    }
    @Before(enable=false)
    @ResponseBody
    @RequestMapping("check")
    public String check(Users bean) {
    	bean.setPass(null);
    	bean.setPortrait(null);
    	bean.setCreatetime(null);
    	bean.setUpdatetime(null);
    	bean.setState(null);
    	bean.setInviteId(null);
    	
    	if (StringUtils.isEmpty(bean.getEmail(), bean.getName(),
    			bean.getNickname(), bean.getPhone())) {
			return toFailure(ResultCode.ERROE_PARAMETER, "参数不能为空");
    	}
    	
    	Users one = getService().selectOne(bean);
    	if (one != null) {
    		if (StringUtils.isEmpty(bean.getEmail())) {
    			return toFailure(ResultCode.ERROE_PARAMETER, "该邮箱已注册");
        	} else if (StringUtils.isEmpty(bean.getNickname())) {
        		return toFailure(ResultCode.ERROE_PARAMETER, "该昵称已注册");
        	} else if (StringUtils.isEmpty(bean.getPhone())) {
    			return toFailure(ResultCode.REPEAT, "该手机号已注册");
        	} else if (StringUtils.isEmpty(bean.getName())) {
    			return toFailure(ResultCode.ERROE_PARAMETER, "该账户已注册");
        	} else {
    			return toFailure(ResultCode.ERROE_PARAMETER, "检验失败");
        	}
    	} else {
    		return toSuccess();
		}
    }

    @Before(enable=true)
    @ResponseBody
    @RequestMapping("check_pass")
    public String checkPass(HttpServletRequest request, Users bean) {
    	if (StringUtils.isEmpty(bean.getPass())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "密码不能为空");
    	}
    	
    	Users users = new Users();
    	users.setId(getLoginUserId());
    	users.setPass(bean.getPass());
//    	users.setState(State.YES);
    	
    	Users one = getService().selectOne(users);
    	if (one != null) {
    		one.setPass(null);
    		users.setState(null);
    		if (one.getState() == State.YES) {
	    		LoginUserHolder.setLoginUser(one);
	    		return toSuccess();
    		} else {
    			return toFailure(ResultCode.ERROE_PARAMETER, "账户不可用", bean);
			}
    	} else {
    		users.setPass(null);
    		users.setState(null);
			return toFailure(ResultCode.ERROE_PARAMETER, "密码不正确", bean);
		}
    }

    @Before(enable=false)
    @ResponseBody
    @RequestMapping("login")
    public String login(HttpServletRequest request, Users bean) {
    	Users users = new Users();
    	users.setName(bean.getName());
    	users.setNickname(bean.getNickname());
    	users.setPhone(bean.getPhone());
//    	users.setState(State.YES);
    	if (!StringUtils.isEmpty(bean.getPass())) {
    		users.setPass(bean.getPass());
    	} else {
    		return toFailure(ResultCode.ERROE_PARAMETER, "密码不能为空");
    	}
    	
    	Users one = getService().selectOne(users);
    	if (one != null) {
    		one.setPass(null);
    		users.setState(null);
    		if (one.getState() == State.YES) {
	    		LoginUserHolder.setLoginUser(one);
	    		return toSuccess(one);
    		} else {
    			return toFailure(ResultCode.ERROE_PARAMETER, "账户不可用", users);
			}
    	} else {
    		users.setPass(bean.getPass());
    		users.setState(null);
			return toFailure(ResultCode.ERROE_PARAMETER, "账户或密码不正确", users);
		}
    }
    @Before(enable=false)
    @ResponseBody
    @RequestMapping("loginout")
    public String loginout(HttpServletRequest request) {
		if (LoginUserHolder.setLoginUser(null)) {
    		return toSuccess("退出成功");
    	} else {
			return toFailure(ResultCode.ERROE_PARAMETER, "退出失败");
		}
    }
    @ResponseBody
    @RequestMapping("selectPageList")
    @Override
    public String selectPageList(HttpServletRequest request, MPagerModel pager) {
    	if (pager.getQuery()!=null && !StringUtils.isEmpty(pager.getQuery().getPass())) {
    		pager.getQuery().setPass(MD5.md5(pager.getQuery().getPass()));
    	}
    	return super.selectPageList(request, pager);
    }
	
	/**
	 * 根据邀请人ID查询用户列表
	 * @param request
	 * @param pager
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list")
	public String selectPageListByInviteId(HttpServletRequest request, MPagerModel pager)
		throws Exception{
		if (pager.getQuery() == null) {
			pager.setQuery(new Users());
		}
		if (StringUtils.isEmpty(pager.getQuery().getInviteId())) {
			pager.getQuery().setInviteId(LoginUserHolder.getLoginUser().getId());
		}
		pager= usersService.list(pager);//根据邀请人ID查询用户列表
		return toSuccess(pager);
	}


	@Override
	public UsersService getService() {
		return usersService;
	}
	
}
