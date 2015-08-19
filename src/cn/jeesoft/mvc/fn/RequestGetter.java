package cn.jeesoft.mvc.fn;

import java.util.List;

import cn.jeesoft.core.freemarker.fn.TemplateMethod;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.helper.RequestHolder;

public class RequestGetter implements TemplateMethod {
	
	@Override
	public Object exec(List<Object> args) throws Exception {
		if (args.isEmpty()) {
			return null;
		}
		
		String name = StringUtils.trim(String.valueOf(args.get(0)));
		String value = RequestHolder.getRequest().getParameter(name);
		if (!StringUtils.isEmpty(value)) {
			value = StringUtils.toCharset(value, "UTF-8");
		} else {
			value = "";
		}
		return value;
	}

}
