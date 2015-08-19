package cn.jeesoft.mvc.fn;

import java.util.List;

import cn.jeesoft.core.freemarker.fn.TemplateMethod;
import cn.jeesoft.core.i18n.MessageLoader;
import freemarker.template.TemplateModelException;

/**
 * 国际化配置
 */
public class I18N implements TemplateMethod {
	
	@Override
    public Object exec(List<Object> arguments) throws TemplateModelException {
        return MessageLoader.instance().getMessage(arguments.get(0).toString());
    }

}
