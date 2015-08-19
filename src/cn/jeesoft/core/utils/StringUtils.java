package cn.jeesoft.core.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Random;

/**
 * 字符串处理工具类
 * @author king
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	
	/**
	 * 判断对象是否为空
	 * @param val
	 * @return boolean
	 */
	public static boolean isEmpty(Object val){
    	if(val == null) {
    		return true;
    	} else {
    		return isEmpty(String.valueOf(val));
    	}
    }

	public static boolean isEmpty(String str) {
		return !isNotEmpty(str);
	}
	public static boolean isNotEmpty(String str) {
		if(str == null || str.isEmpty() || "null".equals(str) || "NULL".equals(str)) {
			return false;
		} else {
			return org.apache.commons.lang.StringUtils.isNotEmpty(str);
		}
	}
	public static boolean isEmpty(String... str) {
		if (str == null || str.length == 0) {
			return true;
		}
		
		for (String item : str) {
			if (isNotEmpty(item)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断是否手机号
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str) {
		if (isNotEmpty(str) && isNumeric(str)) {
			if (str.length() == 11) {
				return true;
			}
		}
		return false;
	}
	
	public static String trim(String str) {
		return (str==null ? null : org.apache.commons.lang.StringUtils.trim(str));
	}

	public static long toLong(Object num, long defValue) {
		if (num != null) {
			String value = String.valueOf(num);
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
			}
		}
		return defValue;
	}
	public static int toInt(Object num, int defValue) {
		if (num != null) {
			String value = String.valueOf(num);
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
			}
		}
		return defValue;
	}
	public static boolean toBoolean(Object val, boolean defValue) {
		if (val != null) {
			String value = String.valueOf(val);
			try {
				return Boolean.valueOf(value) || toInt(val, 0)==1;
			} catch (Exception e) {
			}
		}
		return defValue;
	}

	public static String toCharset(String str, Charset charset, Charset toCharset) {
		if (str == null) {
			return null;
		}
		if (charset == toCharset) {
			return str;
		}
		
		return new String(str.getBytes(charset), toCharset);
	}

	public static String toCharset(String str, String charset, String toCharset) {
		if (str == null) {
			return null;
		}
		if (charset == toCharset) {
			return str;
		}
		
		try {
			return new String(str.getBytes(charset), toCharset);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
	/**
	 * 自动匹配原编码，转换为目标编码
	 * @param str
	 * @param toCharset
	 * @return
	 */
	public static String toCharset(String str, String toCharset) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}

		String encode = StringUtils.getEncoding(str);
		if (!StringUtils.isEmpty(encode)) {
			str = StringUtils.toCharset(str, encode, toCharset);
		}
		return str;
	}
	
	public static String getEncoding(String str) {
		if (isEmpty(str)) {
			return null;
		}
		
		String encode;

		encode = "ISO-8859-1";
		if (isEncoding(str, encode)) {
			return encode;
		}
		
		encode = "UTF-8";
		if (isEncoding(str, encode)) {
			return encode;
		}
		
		encode = "GB2312";
		if (isEncoding(str, encode)) {
			return encode;
		}
		
		encode = "GBK";
		if (isEncoding(str, encode)) {
			return encode;
		}
		
		return null;
	}
	/**
	 * 判断是否指定编码
	 * @param str
	 * @param encode
	 * @return
	 */
	public static boolean isEncoding(String str, String encode) {
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	
	/**
	 * 转化数值的进制单位
	 * @param value 要转化的数值
	 * @param source 原始进制（2～36之间的任意进制）
	 * @param target 目标进制（2～36之间的任意进制）
	 * @return 转化后的数值
	 */
	public static String coverNumberUnit(String value, int source, int target) {
		if (isEmpty(value)) {
			return null;
		}
		if (source < Character.MIN_RADIX || source > Character.MAX_RADIX) {
			throw new IllegalArgumentException("'source' must be between "
					+Character.MIN_RADIX+" and "+Character.MAX_RADIX+".");
		}
		if (target < Character.MIN_RADIX || target > Character.MAX_RADIX) {
			throw new IllegalArgumentException("'target' must be between "
					+Character.MIN_RADIX+" and "+Character.MAX_RADIX+".");
		}
		
		if (source == target) {
			return value;
		}
		
		long tmp = coverNumberToDecimal(value, source);
		return Long.toString(tmp, target);
	}
	/**
	 * 转化数值的进制单位（转化为十进制）
	 * @param value 要转化的数值
	 * @param radix 原始进制（2～36之间的任意进制）
	 * @return 十进制的数值
	 */
	public static long coverNumberToDecimal(String value, int radix) {
		return Long.parseLong(value, radix);
	}
	

	/**
	 * 是否指定的小数位数
	 * @param number 要判断的数值
	 * @return true:是，false:否
	 */
	public static boolean isFixDigit(float number) {
		double numberD = Double.parseDouble(String.valueOf(number));
		return isFixDigit(numberD);
	}
	/**
	 * 是否指定的小数位数
	 * @param number 要判断的数值
	 * @return true:是，false:否
	 */
	public static boolean isFixDigit(double number) {
		//设置小数点后面的位数
    	NumberFormat nFormat = NumberFormat.getNumberInstance(); 
        nFormat.setMaximumFractionDigits(2);
        return nFormat.format(number).equals(String.valueOf(number));
	}

	/**
	 * 生成随机数字
	 * @param num 数字个数
	 * @return
	 */
	public static String getRandomNum(int len) {
		Random random = new Random();
		StringBuffer strNum = new StringBuffer();
		for (int i=0; i<len; i++) {
			int num = random.nextInt(10);
			strNum.append(num);
		}
		return strNum.toString();
	}
	private static final String _BaseCode = "abcdefghijklmnopqrstuvwxyz0123456789";
	/**
	 * 生成随机字符串
	 * @param num 字符串个数
	 * @return
	 */
	public static String getRandomStr(int len) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < len; i++) {     
	        int number = random.nextInt(_BaseCode.length());     
	        sb.append(_BaseCode.charAt(number));     
	    }
		return sb.toString();
	}
	
	
}
