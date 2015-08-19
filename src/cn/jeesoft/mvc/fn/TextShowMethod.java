package cn.jeesoft.mvc.fn;

import java.util.List;

import cn.jeesoft.core.freemarker.fn.TemplateMethod;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModel;

/**
 * 文本展示
 * @author king
 */
public class TextShowMethod implements TemplateMethod {

	@Override
	public Object exec(List<Object> args) throws Exception {
		if (args != null && !args.isEmpty()) {
			Object value = args.get(0);
			if (value != null) {
				if (value instanceof TemplateModel) {
					if (value instanceof TemplateBooleanModel) {
						return String.valueOf(((TemplateBooleanModel) value).getAsBoolean());
					}
				} else if (value instanceof Enum) {
				}
				return value.toString();
			}
		}
		return "";
	}

}
