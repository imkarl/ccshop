package cn.jeesoft.mvc.services;

import org.springframework.stereotype.Service;

import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.bean.UsersRoles;

/**
 * 用户权限
 * @author king
 */
@Service("usersRolesService")
public class UsersRolesService extends BaseService<UsersRoles>{

	/**
	 * 根据用户ID查询
	 */
	public UsersRoles selectOne(Integer usersId) {
		if (usersId == null || usersId < 0) {
			return null;
		}
		
		UsersRoles bean = new UsersRoles();
		bean.setUsersId(usersId);
		bean = super.selectOne(bean);
		if (bean == null) {
			bean = new UsersRoles();
			bean.setHasShop(1);
			bean.setHasP2p(1);
			bean.setHasCall(1);
			bean.setUsersId(usersId);
		}
		return bean;
	}
	
	/**
	 * 执行更新，没有记录则自动新增
	 */
	@Override
	public int update(UsersRoles bean) {
		if (bean.getUsersId() == null || bean.getUsersId() < 0) {
			return -1;
		}
		
		// 根据userId查询
		UsersRoles roles = super.selectOne(bean);
		if (StringUtils.isEmpty(roles)) { // 为空则插入
			// 插入利润比例
			int result = insert(bean);
			if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		} else { // 不为空则修改
			return super.update(bean);// 修改
		}
	}
	

	@Override
	public String getTablename() {
		return "users_roles";
	}

}
