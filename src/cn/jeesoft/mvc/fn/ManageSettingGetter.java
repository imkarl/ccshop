package cn.jeesoft.mvc.fn;

import java.util.List;

import cn.jeesoft.core.SystemManager;
import cn.jeesoft.core.freemarker.fn.TemplateMethod;
import cn.jeesoft.mvc.bean.ManageSetting;
import freemarker.template.TemplateModelException;

/**
 * 获取系统参数的配置
 */
public class ManageSettingGetter implements TemplateMethod {
    @Override
    public Object exec(List<Object> arguments) throws TemplateModelException {
    	
    	ManageSetting manage = new ManageSetting();
    	manage.setName("CCShop");
    	manage.setDescription("我的网站");
    	manage.setKeywords("我的关键字");
    	manage.setShortcuticon("/jeesoft_fav.ico");
    	manage.setVersion("v1.0");
    	manage.setAddress("中国广东");
    	manage.setSystemCode("10001");
    	manage.setWww("www.jeesoft.cn");
    	manage.setLog("log...");
    	manage.setEmail("alafighting@163.com");
    	manage.setIcp("icp");
    	manage.setIsopen("open");
    	manage.setCloseMsg("关闭网站了");
    	manage.setImageRootPath("/images");
    	
    	SystemManager.manageSetting = manage;
        return SystemManager.manageSetting;
    }
}
