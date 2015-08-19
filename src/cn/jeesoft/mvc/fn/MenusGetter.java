package cn.jeesoft.mvc.fn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.jeesoft.core.freemarker.fn.TemplateMethod;
import cn.jeesoft.mvc.bean.Menu;

/**
 * 菜单列表
 * @author king
 */
public class MenusGetter implements TemplateMethod {
	
	private List<Menu> menus = new ArrayList<Menu>();
	
	/**
	 * 重新加载菜单
	 */
	public void reload() {
		menus.clear();
		
		menus.add(new Menu(1, 0, "/manage/index", "首页", null));
		menus.add(new Menu(2, 0, "#", "系统管理",
				Arrays.asList(new Menu(21, 2, "/manage/update", "管理员信息", null),
					new Menu(22, 2, "/manage/profit/update", "默认费率管理", null)
				)));
		menus.add(new Menu(3, 0, "#", "会员管理",
				Arrays.asList(new Menu(31, 3, "/manage/users/list", "会员管理", null),
					new Menu(32, 3, "/manage/users/insert", "会员注册", null),
					new Menu(32, 3, "/manage/users/verify", "注册审核", null),
					new Menu(33, 3, "/manage/users/auth/list", "资料审核", null),
					new Menu(33, 3, "/manage/users/roles/list", "权限管理", null),
				    new Menu(35,3,"/manage/users/invite","邀请管理",null)
				)));
		menus.add(new Menu(4, 0, "#", "账单管理",
				Arrays.asList(new Menu(41, 4, "/manage/sys/trade/list", "全部交易", null),
						new Menu(42, 4, "/manage/sys/trade/list_call", "话费充值", null),
						new Menu(42, 4, "/manage/sys/trade/list_recharge", "余额充值", null),
						new Menu(42, 4, "/manage/sys/trade/list_withdraw", "提现申请", null),
						new Menu(42, 4, "/manage/sys/trade/list_restore", "掉单补单", null)
				)));
	}

	@Override
	public Object exec(List<Object> args) throws Exception {
//		if (menus==null || menus.isEmpty()) {
			reload();
//		}
		return menus;
	}

}
