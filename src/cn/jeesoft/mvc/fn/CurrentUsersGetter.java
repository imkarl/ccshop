package cn.jeesoft.mvc.fn;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

import cn.jeesoft.mvc.helper.LoginUserHolder;

/**
 * 获取当前登录的用户(前端用户)
 * @author dylan
 */
public class CurrentUsersGetter implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return LoginUserHolder.getLoginUser();
    }
}
