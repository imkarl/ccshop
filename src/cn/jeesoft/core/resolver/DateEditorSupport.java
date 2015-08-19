package cn.jeesoft.core.resolver;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import cn.jeesoft.core.utils.StringUtils;

/**
 * 自定义Data类型解析器
 * @author king
 */
public class DateEditorSupport extends CustomDateEditor {

	public DateEditorSupport() {
		super(df, false);
	}
	
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
		private static final long serialVersionUID = 1L;
		private final DateFormat mShortFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		@Override
		public Date parse(String source, ParsePosition pos) {
			if (StringUtils.isEmpty(source)) {
				return null;
			}
			
			if (!source.contains(":")) {
				return mShortFormat.parse(source, pos);
			} else {
				return super.parse(source, pos);
			}
		}
	};

}
