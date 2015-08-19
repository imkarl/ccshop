package cn.jeesoft.mvc.action.web.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.BaseBean.State;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.UsersLoginInterceptor;
import cn.jeesoft.mvc.action.web.BaseManageAction;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.model.AuthState;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.outapi.SmsHelper;
import cn.jeesoft.mvc.outapi.SmsHelper.CodeStatus;
import cn.jeesoft.mvc.services.BaseService;
import cn.jeesoft.mvc.services.UsersService;

@Controller("front.users")
@RequestMapping("front/users")
@Before(UsersLoginInterceptor.class)
public class UsersAction extends BaseManageAction<Users, UsersAction.MPagerModel> {
	static class MPagerModel extends PagerModel<Users, Users> {
	}
	
	
	@Autowired
	private UsersService usersService;

	@Before(enable=false)
    @RequestMapping(value = "insert",method = RequestMethod.GET)
    public String insert(HttpServletRequest request, ModelMap modelMap) throws Exception {
		modelMap.addAttribute("title", "会员注册");
        return FTL_FRONT+"users/insert";
	}
	@Before(enable=false)
	@RequestMapping(value = "insert",method = RequestMethod.POST)
	public String insert(HttpServletRequest request, ModelMap modelMap, Users users) throws Exception {
		modelMap.addAttribute("title", "会员注册");
		
		String verifyCode = request.getParameter("verify");
		
		if (!StringUtils.isPhone(users.getPhone())) {
			modelMap.addAttribute("errorMessage", "手机号不正确");
		} else if (StringUtils.isEmpty(users.getPass())) {
			modelMap.addAttribute("errorMessage", "密码不能为空");
		} else if (StringUtils.isEmpty(verifyCode)) {
			modelMap.addAttribute("errorMessage", "验证码不能为空");
		} else {
			CodeStatus status = SmsHelper.verify(verifyCode);
    		if (status == CodeStatus.NO) {
				modelMap.addAttribute("errorMessage", "验证码不正确");
    		} else if (status == CodeStatus.EXPIRE) {
				modelMap.addAttribute("errorMessage", "验证码已过期");
    		} else {
    			// 合理请求
    			Users one = new Users();
    			one.setPhone(users.getPhone());
    	    	one = getService().selectOne(one);
    	    	if (one != null) {
    				modelMap.addAttribute("errorMessage", "该手机号已注册");
    	    	} else {
        			users.setState(State.YES);
        			users.setAuthState(AuthState.NO);
        			users.setPass(Users.encryption(users.getPass()));
    	    		// 生成随机昵称
    	    		if (StringUtils.isEmpty(users.getNickname())) {
    	    			users.setNickname(StringUtils.getRandomStr(8));
    	    		}
        			
        			int usersId = usersService.insert(users);
        			if (usersId > 0) {
        				SmsHelper.remove(verifyCode);
        				return FTL_FRONT+"users/insert_success";
        			} else if (usersId == -2) {
        				modelMap.addAttribute("errorMessage", "该邀请人无权邀请用户");
        			} else {
        				modelMap.addAttribute("errorMessage", "注册失败，请重试");
        			}
				}
    		}
		}
		return FTL_FRONT+"users/insert";
	}

    @Before(enable=false)
    @ResponseBody
    @RequestMapping("check")
    public String check(Users bean) throws Exception {
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

	@Override
	public BaseService<Users> getService() {
		return usersService;
	}

}
