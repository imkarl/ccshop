package cn.jeesoft.core.utils;


/**
 * UNICODE编码转换工具类
 * @author king
 */
public class UnicodeUtils {
	
	public static String encode(final String str) {
		try {
			char[] utfBytes = str.toCharArray();
			String unicodeBytes = "";
			for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
				String hexB = Integer.toHexString(utfBytes[byteIndex]);
				if (hexB.length() <= 2) {
					hexB = "00" + hexB;
				}
				unicodeBytes = unicodeBytes + "\\u" + hexB;
			}
			return unicodeBytes;
		} catch (Exception e) {
		}
		return str;
	}

	public static String decode(final String str) {
		try {
			int start = 0;
			int end = 0;
			final StringBuffer buffer = new StringBuffer();
			while (start > -1) {
				end = str.indexOf("\\u", start + 2);
				String charStr = "";
				if (end == -1) {
					charStr = str.substring(start + 2, str.length());
				} else {
					charStr = str.substring(start + 2, end);
				}
				char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
				buffer.append(new Character(letter).toString());
				start = end;
			}
			return buffer.toString();	
		} catch (Exception e) {
		}
		return str;
	}
	
	/**
	 * 把中文转成Unicode码
	 * @param str
	 * @return
	 */
	public static String encodeChina(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {  // 汉字范围 \u4e00-\u9fa5 (中文)
				result += "\\u" + Integer.toHexString(chr1);
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 判断是否为中文字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

}
