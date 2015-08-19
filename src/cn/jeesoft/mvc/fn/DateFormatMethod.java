package cn.jeesoft.mvc.fn;

import java.util.Date;
import java.util.List;

import cn.jeesoft.core.freemarker.fn.TemplateMethod;
import cn.jeesoft.core.utils.DateUtils;

/**
 * 日期格式化
 * @author king
 */
public class DateFormatMethod implements TemplateMethod {

	@Override
	public Object exec(List<Object> args) throws Exception {
		if (args == null || args.isEmpty()) {
			return "";
		}
		
		Object dateObj = args.get(0);
		if (dateObj != null) {
			Date date = null;
			if (dateObj instanceof java.util.Date) {
				date = (Date) dateObj;
			} else if (dateObj instanceof java.sql.Date) {
				date = new Date(((java.sql.Date) dateObj).getTime());
			} else if (dateObj instanceof freemarker.template.SimpleDate) {
				date = ((freemarker.template.SimpleDate) dateObj).getAsDate();
			}
			
			String format = DateUtils.FORMAT_FULL;
			if (args.size() > 1) {
				format = String.valueOf(args.get(1));
			}
			return DateUtils.format(date, format);
		}
		return "";
	}
	
	
	
}
