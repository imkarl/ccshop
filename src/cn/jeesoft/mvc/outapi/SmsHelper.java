package cn.jeesoft.mvc.outapi;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.jeesoft.core.util.MD5;
import cn.jeesoft.core.utils.DateUtils;
import cn.jeesoft.core.utils.JsonUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.Config;

import com.alibaba.fastjson.JSONObject;

/**
 * 短信验证码
 * @author king
 */
public class SmsHelper {

	private static HashMap<String, Long> mSms = new HashMap<String, Long>();
	/**
	 * 验证码过期时长：秒
	 */
	private static final int ValidityDuration = 3 * 60;
	/**
	 * 重复发送时间间隔：秒
	 */
	private static final int RepeatDuration = 30;
	
	/**
	 * 验证码状态
	 * @author king
	 */
	public static enum CodeStatus {
		YES, // 有效
		NO, // 无效
		EXPIRE, // 过期
		;
	}

	/**
	 * 发送验证码
	 * @param phone
	 * @return 1:成功，0:失败，-1:余额不足
	 */
	public static synchronized int sendMessage(String phone) {
		String code = getCode();
		mSms.put(code, System.currentTimeMillis());
		return sendCode("淘金站", code, ValidityDuration, phone);
	}

	/**
	 * 验证是否正确
	 * @param code
	 * @return
	 */
	public static synchronized CodeStatus verify(String code) {
		if (StringUtils.isEmpty(code) || !mSms.containsKey(code)) {
			// 无效
			return CodeStatus.NO;
		}

		Long time = mSms.get(code);
		if (System.currentTimeMillis() - time > ValidityDuration * 1000) {
			// 过期
			return CodeStatus.EXPIRE;
		}
		// 有效
		return CodeStatus.YES;
	}
	/**
	 * 删除验证码
	 * @param code
	 */
	public static synchronized void remove(String code) {
		mSms.remove(code);
	}
	
	
	
	
	/**
	 * 获取随机不重复的验证码
	 * @return
	 */
	private static synchronized String getCode() {
		clearCode();
		
		int num = 1000;
		String code = StringUtils.getRandomNum(6);
		while (mSms.containsKey(code)) {
			if (num-- < 0) {
				break;
			}
			code = StringUtils.getRandomNum(6);
		}
		
		if (StringUtils.isEmpty(code)) {
			num = 1000;
			while (mSms.containsKey(code)) {
				if (num-- < 0) {
					break;
				}
				code = StringUtils.getRandomStr(6);
			}
		}
		return code;
	}
	private static synchronized void clearCode() {
		try {
			Iterator<Entry<String, Long>> iterator = mSms.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Long> entry = iterator.next();
				Long time = entry.getValue();
				if (System.currentTimeMillis() - time > ValidityDuration * 1000) {
					iterator.remove();
				}
			}
		} catch (Exception e) { }
	}
	
	/**
	 * 发送短信验证码
	 * @param appName 应用名称
	 * @param code 验证码
	 * @param second 有效时长[秒]
	 * @param phone 目标手机，用于接收验证码
	 * @return 1:成功，0:失败，-1:余额不足
	 */
	private static int sendCode(String appName, String code, int second, String phone) {
		try {
			String message = doSendCode(appName, code, second, phone);
			if (!StringUtils.isEmpty(message)) {
				JSONObject json = (JSONObject) JsonUtils.fromJson(message);
				if (json != null) {
					JSONObject resp = json.getJSONObject("resp");
					String respCode = resp.getString("respCode");
					if (!StringUtils.isEmpty(respCode) && "000000".equals(respCode)) {
						return 1;
					} else if (!StringUtils.isEmpty(respCode) && "100001".equals(respCode)) {
						return -1;
					}
				}
			}
		} catch (Throwable e) {
		}
		return 0;
	}
	private static String doSendCode(String appName, String code, int second, String phone) throws Throwable {
		Date time = new Date();
		String timestamp = DateUtils.format(time, "yyyyMMddHHmmssSSS");

		final String param = appName+","+code+","+(second/60);

		String url = Config.UCPAAS_HOST;
		url += "?sid=" + Config.UCPAAS_ACCOUNTSID;
		url += "&appId=" + Config.UCPAAS_APPID;
		url += "&time=" + timestamp;
		url += "&sign=" + getSignature(Config.UCPAAS_ACCOUNTSID, Config.UCPAAS_AUTHTOKEN, timestamp);
		url += "&to=" + phone;
		url += "&templateId=" + Config.UCPAAS_TEMPLATEID;
		url += "&param=" + param;

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity resEntity = response.getEntity();
		InputStream inStream = resEntity.getContent();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = -1;
		while ((len = inStream.read(data)) != -1) {
			baos.write(data, 0, len);
		}

		byte[] result = baos.toByteArray();
		return new String(result, "UTF-8");
	}

	/**
	 * 生成sig
	 * @param accountSid 账户SID
	 * @param authToken 账户TOKEN
	 * @param timestamp 时间戳
	 * @return
	 */
	private static String getSignature(String accountSid, String authToken, String timestamp) {
		String sig = accountSid + timestamp + authToken;
		return MD5.md5(sig);
	}

}
