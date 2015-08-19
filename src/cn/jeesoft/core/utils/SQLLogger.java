package cn.jeesoft.core.utils;

import java.util.Date;

/**
 * SQL日志打印
 * @author king
 */
public class SQLLogger extends com.p6spy.engine.logging.appender.Log4jLogger {
	
	public SQLLogger() {
		super();
	}
	
	@Override
	public void logSQL(int connectionId, String now, long elapsed,
			String category, String prepared, String sql) {
		// 去除多余空格
		prepared = prepared.replaceAll("\\s+", " ");
		sql = sql.replaceAll("\\s+", " ").trim();
		
		// 定义输出格式
		String outSql;
		if (!sql.equals(prepared)) {
			outSql = "[SQL] "+prepared+"\n   => "+sql;
		} else {
			if (StringUtils.isEmpty(sql)) {
				outSql = "[SQL] "+category;
			} else {
				outSql = "[SQL] "+sql;
			}
		}

		// 输出日志
		System.out.println(outSql);
		
		// 不保留查询SQL
		if (!sql.toUpperCase().startsWith("SELECT")) {
			// 打印到文件
			String time = DateUtils.format(new Date(StringUtils.toLong(now, 0)), DateUtils.FORMAT_FULL_NOSEPARATOR);
			LogUtils.logFile(time+" | "+(StringUtils.isEmpty(sql) ? category : sql));
		}
		
	}
	

}
