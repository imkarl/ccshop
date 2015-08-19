package cn.jeesoft.mvc.helper;

import java.util.ArrayList;
import java.util.List;

import cn.jeesoft.mvc.bean.Menu;

public class MeunHelper {
	
	public static List<Menu> parser(List<Menu> menus) {
		// 创建菜单集合
		List<Menu> root = new ArrayList<Menu>();
		// 循环添加菜单到菜单集合
		for(int i=0;i<menus.size();i++){
			Menu menu = menus.get(i);
			
			Menu item = new Menu(menu);
			
			// TODO 加载子菜单
			for(int j=0;j<menus.size();i++){ 
				
			}
			
			root.add(item);
		}
		return menus;
	}
	
}
