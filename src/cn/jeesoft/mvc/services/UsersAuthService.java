package cn.jeesoft.mvc.services;

import org.springframework.stereotype.Service;

import cn.jeesoft.mvc.services.BaseService;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.bean.UsersAuth;
import cn.jeesoft.mvc.model.AuthState;

@Service("usersAuthService")
public class UsersAuthService extends BaseService<UsersAuth> {

	@Override
	public int update(UsersAuth bean) {
		if (bean.getUsersId() <= 0) {
			return -1;
		}
		
		int update = super.update(bean);
		if (update==0) {
			Users users = new Users();
			users.setId(bean.getUsersId());
			int insert = getDao().insert("users.createAuth", users);
			if (insert > 0) {
				update = 1;
			}
		} else {
			update = super.update(bean);
		}
		
		if (update > 0) {
			if (bean.getAuthState()==null) {
				bean.setAuthState(AuthState.WAIT);
			}
			switch (bean.getAuthState()) {
			case YES:
				getDao().update(getTablename()+".finish", bean);
				break;
			case NO:
				getDao().update(getTablename()+".no", bean);
				break;
			case WAIT:
				getDao().update(getTablename()+".wait", bean);
				break;
			case INVALID:
				getDao().update(getTablename()+".invalid", bean);
				break;

			default:
				break;
			}
		}
		return update;
	}
	
	/**
	 * 审核通过
	 * @param usersId
	 * @return
	 */
	public int finish(int usersId) {
		UsersAuth auth = new UsersAuth();
		auth.setUsersId(usersId);
		
		// 更新时间
		int update = getDao().update(getTablename()+".finish_auth", auth);
		if (update > 0) {
			// 更新状态
			getDao().update(getTablename()+".finish", auth);
		}
		return update;
	}
	
	/**
	 * 资料退回
	 * @param usersId
	 * @return
	 */
	public int invalid(int usersId) {
		UsersAuth auth = new UsersAuth();
		auth.setUsersId(usersId);
		int update = getDao().update(getTablename()+".invalid", auth);
		return update;
	}

	@Override
	public String getTablename() {
		return "users_auth";
	}

}
