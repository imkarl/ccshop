package cn.jeesoft.core.model;


/**
 * 系统中所有实体类必须继承的接口，此为顶级接口，负责清理实体类中的字段数据。
 */
public interface ClearBean {
	
	/**
	 * 清空实体类的属性的值
	 */
	void clear();
	
}