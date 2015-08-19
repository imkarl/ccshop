package cn.jeesoft.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.jeesoft.core.oscache.FrontCache;
import cn.jeesoft.core.oscache.ManageCache;

/**
 * 系统配置加载监听器
 * @author huangf
 */
public class SystemListener implements ServletContextListener {
	
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
//			SystemManager.getInstance();

			WebApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
			FrontCache frontCache = (FrontCache) app.getBean("frontCache");
			ManageCache manageCache = (ManageCache) app.getBean("manageCache");
			// TODO 
//			frontCache.loadAllCache();
//			manageCache.loadAllCache();
			
//			TaskManager taskManager = (TaskManager) app.getBean("taskManager");
//			taskManager.start();
		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println("System load faild!"+e.getMessage());
			try {
				throw new Exception("系统初始化失败！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
