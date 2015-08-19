package cn.jeesoft.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * LOG工具类
 * @author king
 */
public class LogUtils {
	
	private static FileWriteHelper logfile;
	
	private static String createFileName() {
		return DateUtils.format(new Date(), DateUtils.FORMAT_DATE)+".log";
	}
	private static FileWriteHelper getLogFile() {
		if (logfile != null) {
			File file = logfile.getFile();
			if (file != null) {
				String fileName = logfile.getFile().getName();
				if (!createFileName().equals(fileName)) {
					logfile.stop();
					logfile = null;
				}
			}
		}
		
		if (logfile == null) {
			File file = new File(FileUtils.getRootPath(), "logs");
			file.mkdir();
			try {
				logfile = new FileWriteHelper(new File(file, createFileName()));
				logfile.start();
			} catch (FileNotFoundException e) {
				return null;
			}
		}
		
		return logfile;
	}
	
	/**
	 * 将日志记录到本地文件
	 * @param message
	 */
	public static void logFile(String message) {
		FileWriteHelper helper = getLogFile();
		if (helper != null) {
			helper.append(message);
		}
	}

}
