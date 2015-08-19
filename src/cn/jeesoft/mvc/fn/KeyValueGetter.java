package cn.jeesoft.mvc.fn;

import java.util.List;

import cn.jeesoft.mvc.helper.KeyValueHelper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class KeyValueGetter implements TemplateMethodModelEx {
    
	@Override
    public Object exec(List arguments) throws TemplateModelException {
        return KeyValueHelper.get(arguments.get(0).toString());
    }
    
}
