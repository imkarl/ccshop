package cn.jeesoft.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jeesoft.core.model.BaseBean.State;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.DateUtils;
import cn.jeesoft.core.utils.DateUtils.FieldType;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.bean.UsersRoles;

@Service("usersService")
public class UsersService extends BaseService<Users> {
	
	@Autowired
	private UsersRolesService usersRolesService;
	
	@Override
	public int insert(Users bean) {
		// 根据邀请码获取邀请人
		if (StringUtils.isNotEmpty(bean.getInviteCode())) {
			Users users = new Users();
			users.setInviteCode(bean.getInviteCode());
			
			users = selectOne(users);
	    	if (users != null) {
	    		bean.setInviteId(users.getId());
	    		
	    		// 检查邀请人是否拥有权限
	    		UsersRoles roles = usersRolesService.selectOne(bean.getInviteId());
	    		if (roles==null || roles.getHasInvite()==null || roles.getHasInvite()!=UsersRoles.VAL_YES) {
	    			// TODO 无权限的邀请者
	    			bean.setInviteId(null);
//	    			return -2;
	    		}
	    	}
		}
    	
		// 自动生成邀请码
		bean.setInviteCode(StringUtils.coverNumberUnit(String.valueOf(System.currentTimeMillis()), 10, 36));
		// 用户状态
		if (bean.getState() == null) {
			bean.setState(State.YES);
		}
		int id = super.insert(bean);
		if (id > 0) {
			getDao().insert(getTablename()+".createMoney", bean);
			getDao().insert(getTablename()+".createAuth", bean);
		}
		return id;
	}
	
	@Override
	public Users selectById(int id) {
		Users bean = new Users();
		bean.setId(id);
		return super.selectOne(bean);
	}
	
	/**
     * 分页查询
     * @param pager
     * @return
     */
	public <P extends PagerModel<Users, Users>> P list(P pager) {
        if (pager.getOffset() < 0)
        	pager.setOffset(0);
        if (pager.getQuery() == null) {
        	pager.setQuery(new Users());
        }
        
        if (pager.getEndTime() != null) {
        	pager.setEndTime(DateUtils.append(pager.getEndTime(), FieldType.DAY, 1));
        }
        // 执行查询
        pager = (P) getDao().selectPageList(getTablename()+".selectPageListByInviteId",
				getTablename()+".selectPageCountByInviteId",
				pager);
        if (pager.getEndTime() != null) {
        	pager.setEndTime(DateUtils.append(pager.getEndTime(), FieldType.DAY, -1));
        }

        // 计算总页数
        pager.setPageCount((pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		return pager;
	}


	@Override
	public String getTablename() {
		return "users";
	}

}
