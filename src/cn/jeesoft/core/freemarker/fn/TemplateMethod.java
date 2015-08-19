package cn.jeesoft.core.freemarker.fn;

import java.util.List;

/**
 * 模板方法
 * @author king
 */
public interface TemplateMethod {
	
	/**
	 * 执行模板方法，返回结果
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object exec(List<Object> args) throws Exception;

}
