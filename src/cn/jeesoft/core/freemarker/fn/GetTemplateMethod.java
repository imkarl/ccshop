package cn.jeesoft.core.freemarker.fn;

import java.util.List;

import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.helper.RequestHolder;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 全局Getter模板方法
 * @author king
 */
public class GetTemplateMethod implements TemplateMethodModelEx {
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args != null && args.size() > 0) {
			Object defValue = null;
			if (args.size() > 1) {
				defValue = args.get(1);
			}
			if (defValue == null) {
				defValue = "";
			}

			// 要获取的参数名
			String name = String.valueOf(args.get(0));
			if (StringUtils.isNotEmpty(name)) {
				Object value = get(name, defValue);
				System.out.println("name="+value);
				return value;
			}
			return defValue;
		}
		return "";
	}
	
	public Object get(String name, Object defValue) throws TemplateModelException {
		if (StringUtils.isNotEmpty(name)) {
			try {
				String param = RequestHolder.getRequest().getParameter(name);
				if (StringUtils.isNotEmpty(param)) {
					return param;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Object attr = RequestHolder.getSession().getAttribute(name);
				if (attr != null) {
					return attr;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return defValue;
	}

}
