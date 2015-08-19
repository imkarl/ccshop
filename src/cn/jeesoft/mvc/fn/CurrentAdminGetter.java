package cn.jeesoft.mvc.fn;

import java.lang.reflect.Field;
import java.util.List;

import cn.jeesoft.core.freemarker.fn.TemplateMethod;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.bean.Admin;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import freemarker.template.TemplateModelException;

/**
 * 获取当前登录的管理员
 */
public class CurrentAdminGetter implements TemplateMethod {
    
	@Override
    public Object exec(List<Object> arguments) throws TemplateModelException {
    	Admin loginAdmin = LoginUserHolder.getLoginAdmin();
		if (loginAdmin!=null && StringUtils.isEmpty(loginAdmin.getNickname())) {
			loginAdmin.setNickname(loginAdmin.getPhone());
		}
		if (loginAdmin == null) {
			return null;
		}
		
		// 直接获取某个属性的值
		if (arguments != null && !arguments.isEmpty()) {
			Object fieldNameObj = arguments.get(0);
			String fieldName = fieldNameObj!=null ? String.valueOf(fieldNameObj) : "";
			try {
				Field field = Admin.class.getDeclaredField(fieldName);
				field.setAccessible(true);
				Object fieldValue = field.get(loginAdmin);
				if (fieldValue == null) {
					fieldValue = "";
				}
				return fieldValue;
			} catch (Exception e) { }
		}
        return loginAdmin;
    }
    
}
