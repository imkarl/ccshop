package cn.jeesoft.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_FULL_NOSEPARATOR = "yyyyMMddHHmmss";
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	
	public enum FieldType {
		YEAR(Calendar.YEAR),
		MONTH(Calendar.MONTH),
		DAY(Calendar.DAY_OF_MONTH),
		HOUR(Calendar.HOUR_OF_DAY),
		MINUTE(Calendar.MINUTE),
		SECOND(Calendar.SECOND),
		;
		
		private int code;
		private FieldType(int code) {
			this.code = code;
		}
		public int getCode() {
			return this.code;
		}
	}
	
	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		} else if (StringUtils.isEmpty(format)) {
			format = FORMAT_FULL;
		}
		
		return new SimpleDateFormat(format).format(date);
	}
	public static String format(Date date) {
		return format(date, FORMAT_FULL);
	}

	public static Date parse(String date, String fromat) {
		try {
			return new SimpleDateFormat(fromat).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	public static Date parse(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e1) {
			try {
				return new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
			} catch (ParseException e2) {
				try {
					return new SimpleDateFormat("yyyy-MM-dd").parse(date);
				} catch (ParseException e3) {
					return null;
				}
			}
		}
	}
	
	/**
	 * 修改时间时间
	 * @param date
	 * @param type
	 * @param amount
	 * @return
	 */
	public static Date append(Date date, FieldType type, int amount) {
		if (date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type.getCode(), amount);
		return calendar.getTime();
	}
	
}
