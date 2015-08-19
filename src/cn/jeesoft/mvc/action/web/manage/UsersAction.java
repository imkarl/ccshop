package cn.jeesoft.mvc.action.web.manage;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.BaseBean.State;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.resolver.FormBean;
import cn.jeesoft.core.utils.FileUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.core.utils.UnicodeUtils;
import cn.jeesoft.interceptor.AdminLoginInterceptor;
import cn.jeesoft.mvc.Config;
import cn.jeesoft.mvc.action.web.BaseManageAction;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.services.UsersService;

import com.alibaba.fastjson.JSONObject;

/**
 * 后台用户管理
 * @author king
 */
@Controller("manage.users")
@RequestMapping("manage/users")
@Before(AdminLoginInterceptor.class)
public class UsersAction extends BaseManageAction<Users, UsersAction.MPagerModel> {
	public static class MPagerModel extends PagerModel<Users, Users> {
	}
	
	
	@Autowired
	private UsersService usersService;

    @RequestMapping("list")
    public String list(HttpServletRequest request, ModelMap modelMap, @FormBean("pager") MPagerModel pager) {
    	if (pager.getQuery()==null) {
    		pager.setQuery(new Users());
    	}
    	
		pager.getQuery().setState(State.YES);
    	if (!StringUtils.isEmpty(pager.getQuery().getPass())) {
    		pager.getQuery().setPass(Users.encryption(pager.getQuery().getPass()));
    	}
    	
    	pager = getService().selectPageList(pager);

		modelMap.addAttribute("pager", pager);
		modelMap.addAttribute("currentMenu", "会员管理");
		
    	return FTL_MANAGE + "users/list";
    }

    @RequestMapping(value="verify")
    public String verify(HttpServletRequest request, ModelMap modelMap, @FormBean("pager") MPagerModel pager) {
    	if (pager.getQuery()==null) {
    		pager.setQuery(new Users());
    	}
    	
		pager.getQuery().setState(State.NO);
    	if (!StringUtils.isEmpty(pager.getQuery().getPass())) {
    		pager.getQuery().setPass(Users.encryption(pager.getQuery().getPass()));
    	}
    	
    	pager = getService().selectPageList(pager);
    	
    	modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "注册审核");
    	
    	return FTL_MANAGE + "users/verify";
    }
    @ResponseBody
    @RequestMapping(value="verifyYes", method=RequestMethod.POST)
    public String verifyYes(HttpServletRequest request, Users users) {
		Users updateUsers = new Users();
		updateUsers.setId(users.getId());
		updateUsers.setState(State.YES);
		
		int updateNum = getService().update(updateUsers);
		if (updateNum > 0) {
			return toSuccess();
		} else {
	    	return toFailure(ResultCode.FAILURE, "操作失败，请重试");
		}
    }
    @ResponseBody
    @RequestMapping(value="verifyNo", method=RequestMethod.POST)
    public String verifyNo(HttpServletRequest request, Users users) {
    	Users updateUsers = new Users();
    	updateUsers.setId(users.getId());
    	updateUsers.setState(State.NO);
    	
    	int updateNum = getService().update(updateUsers);
    	if (updateNum > 0) {
    		return toSuccess();
    	} else {
    		return toFailure(ResultCode.FAILURE, "操作失败，请重试");
    	}
    }
    
    //邀请人管理
    @RequestMapping("invite")
	public String selectPageListByInviteId(HttpServletRequest request, ModelMap modelMap, @FormBean("pager") MPagerModel pager) {
    	if (pager.getQuery()==null) {
    		pager.setQuery(new Users());
    	}
		//pager.getQuery().setState(State.YES);
		pager= usersService.list(pager);//根据邀请人ID查询用户列表
		modelMap.addAttribute("pager", pager);
    	modelMap.addAttribute("currentMenu", "邀请管理");
    	
		return FTL_MANAGE+"users/invite";
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, ModelMap modelMap, Users bean) {
		if (bean.getId() != null) {
			// 根据usersId查询
			Users users = usersService.selectById(bean.getId());
			modelMap.addAttribute("users", users);
		} else {
			modelMap.addAttribute("errorMessage", "用户ID 不能为空");
		}
		modelMap.addAttribute("currentMenu", "会员管理");
		return FTL_MANAGE + "users/update";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String doUpdate(HttpServletRequest request, ModelMap modelMap, Users bean) {
    	Map<String, File> files = FileUtils.saveMultipartFiles(request, bean.getPhone());
    	if (files.containsKey("portrait")) {
    		bean.setPortrait(FileUtils.getRelativePath(files.get("portrait")));
    	}
    	
    	int update = usersService.update(bean);
    	if (update > 0) {
    		modelMap.addAttribute("errorMessage", "更新成功");
    	} else {
    		modelMap.addAttribute("errorMessage", "更新失败");
		}
    	
		Users users = usersService.selectById(bean.getId());
		modelMap.addAttribute("users", users);
		modelMap.addAttribute("currentMenu", "会员管理");
		return FTL_MANAGE + "users/update";
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insert(HttpServletRequest request, ModelMap modelMap, Users bean) {
		modelMap.addAttribute("currentMenu", "会员注册");
		return FTL_MANAGE + "users/update";
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String doInsert(HttpServletRequest request, ModelMap modelMap, Users bean) {
		if (!StringUtils.isPhone(bean.getPhone())) {
    		modelMap.addAttribute("errorMessage", "手机号格式不正确");
		} else if (StringUtils.isEmpty(bean.getPass())) {
    		modelMap.addAttribute("errorMessage", "密码不能为空");
		} else {

	    	Map<String, File> files = FileUtils.saveMultipartFiles(request, bean.getPhone());
	    	if (files.containsKey("portrait")) {
	    		bean.setPortrait(FileUtils.getRelativePath(files.get("portrait")));
	    	}

    		String pass = bean.getPass();
    		bean.setPass(Users.encryption(pass));
	    	try {
	    		bean.setState(State.YES);
	    		int insert = usersService.insert(bean);
		    	if (insert > 0) {
		        	return Redirect+"/manage/users/list?pager.query.id="+insert;
		    	} else {
		    		modelMap.addAttribute("errorMessage", "更新失败");
				}
			} catch (DuplicateKeyException e) {
	    		modelMap.addAttribute("errorMessage", "唯一性约束");
	    		
	    		String cause = e.getMessage();
	    		if (!StringUtils.isEmpty(cause)) {
		    		if (cause.contains("phone_UNIQUE")) {
			    		modelMap.addAttribute("errorMessage", "该手机号已注册");
		    		} else if (cause.contains("idcard_UNIQUE")) {
			    		modelMap.addAttribute("errorMessage", "该身份证已注册");
		    		} else if (cause.contains("nickname_UNIQUE")) {
		    			modelMap.addAttribute("errorMessage", "该昵称已注册");
		    		} else if (cause.contains("name_UNIQUE")) {
		    			modelMap.addAttribute("errorMessage", "该用户名已注册");
		    		}
	    		}
	    		e.printStackTrace();
			}
    		bean.setPass(pass);
		}
    	
		modelMap.addAttribute("users", bean);
		modelMap.addAttribute("currentMenu", "会员注册");
		return FTL_MANAGE + "users/update";
	}
	

    @ResponseBody
    @RequestMapping("selectPageList")
    public String selectPageList(HttpServletRequest request, MPagerModel pager) {
    	int usersId = StringUtils.toInt(request.getParameter("usersId"), 0);
    	
		int pageSize = StringUtils.toInt(request.getParameter("length"), Config.PAGE_SIZE);
		if (pageSize != Config.PAGE_SIZE) {
			pager.setPageSize(pageSize);
		}
    	if (pager.getOffset() <= 0) {
    		pager.setOffset(StringUtils.toInt(request.getParameter("start"), 0));
    	}
    	
    	pager = getService().selectPageList(pager);
    	if (pager.getList()!=null) {
    		for (int i=0; i<pager.getList().size(); i++) {
    			Users users = pager.getList().get(i);
    			if (users.getId() == usersId) {
    				pager.getList().remove(i);
    				break;
    			}
    		}
    	}
    	
    	JSONObject json = new JSONObject();
    	json.put("data", pager.getList());
    	json.put("length", pager.getPageSize());
    	json.put("total", pager.getTotal());
    	json.put("iTotalRecords", pager.getTotal());
    	json.put("iTotalDisplayRecords", pager.getTotal());
    	return UnicodeUtils.encodeChina(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("update_invite")
    public String updateInvite(HttpServletRequest request, Users users) {
    	System.out.println(users);
    	
    	if (users.getId() == null || users.getInviteId()==null) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "参数不正确");
    	}
    	
    	Users bean = new Users();
    	bean.setId(users.getId());
    	bean.setInviteId(users.getInviteId());
    	int update = usersService.update(bean);
    	
    	if (update > 0) {
    		return toSuccess();
    	} else {
			return toFailure(ResultCode.FAILURE, "修改失败");
		}
    }

	@Override
	public UsersService getService() {
		return usersService;
	}

}
