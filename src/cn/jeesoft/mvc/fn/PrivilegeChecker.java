package cn.jeesoft.mvc.fn;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import cn.jeesoft.mvc.helper.PrivilegeUtils;
import cn.jeesoft.mvc.helper.RequestHolder;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class PrivilegeChecker implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if(arguments == null || arguments.size() == 0){
            return true;
        }
        if(!(arguments.get(0) instanceof String)){
            return true;
        }
        String res = (String)arguments.get(0);
        if(StringUtils.isBlank(res)){
            return true;
        }
        HttpSession session = RequestHolder.getSession();
        System.out.println(String.format("check privilege ,res : {}, session id :{}", res, session == null ? null : session.getId()));
        return PrivilegeUtils.check(session, res);
    }
}
