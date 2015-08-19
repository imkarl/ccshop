package cn.jeesoft.core.freemarker.fn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.fn.CurrentAdminGetter;
import cn.jeesoft.mvc.fn.DateFormatMethod;
import cn.jeesoft.mvc.fn.I18N;
import cn.jeesoft.mvc.fn.ManageSettingGetter;
import cn.jeesoft.mvc.fn.MenusGetter;
import cn.jeesoft.mvc.fn.RequestGetter;
import cn.jeesoft.mvc.fn.TextShowMethod;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 全局模板方法
 * @author king
 */
public class ExecTemplateMethod implements TemplateMethodModelEx {
	
	private static Map<String, TemplateMethod> mMethods = new HashMap<String, TemplateMethod>();
	
	static {
		mMethods.put("i18N", new I18N());
		mMethods.put("request", new RequestGetter());
		mMethods.put("admin", new CurrentAdminGetter());
		mMethods.put("manageSetting", new ManageSettingGetter());
		mMethods.put("menus", new MenusGetter());
		mMethods.put("date", new DateFormatMethod());
		mMethods.put("text", new TextShowMethod());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args != null && args.size() > 0) {
			if (args.size() > 0) {
				// 获取要执行的方法名
				String methodName = String.valueOf(args.get(0));
				if (StringUtils.isNotEmpty(methodName)) {
					// 获取要执行的方法体
					TemplateMethod method = mMethods.get(methodName);
					if (method != null) {
						try {
							@SuppressWarnings("unchecked")
							List<Object> argsObj = new ArrayList<Object>(args);
							argsObj.remove(0);
							return method.exec(argsObj);
						} catch (Exception e) {
							e.printStackTrace();
							throw new TemplateModelException(e);
						}
					}
				}
			}
		}
		throw new TemplateModelException("donot find methodName");
	}

}
