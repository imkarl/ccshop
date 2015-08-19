package cn.jeesoft.mvc.outapi;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.jeesoft.core.utils.Sec;
import cn.jeesoft.core.utils.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 卡卡联支付
 * @author king
 */
public class KklPayHelper {
	/** packet */
	private static String PACKET = "PACKET";
	/** 编码 */
	private static String ENCODE = "UTF-8";
	/** 分隔符 */
	private static String EQUAL = "=";
	
	
	
	/**
	 * 验证响应
	 * @param packet
	 * @param md5Key
	 * @return true:成功,false:失败,null:异常
	 */
	public static Boolean verify(JSONObject packet, String md5Key) {
		String[] signFields = {"merchantCode", "instructCode", "transType", "outOrderId", "transTime","totalAmount"};
		return KklPayHelper.verifySignMd5(md5Key, signFields, packet);
	}
	
	

	
	/**
	 * 获取请求中的通知信息
	 * @param request
	 * @return
	 */
	public static JSONObject getPacket(HttpServletRequest request) {
		String packetStr = getPacketStr(request);
		if (StringUtils.isEmpty(packetStr)) {
			return null;
		}

		try {
			return JSONObject.parseObject(packetStr);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 获取请求中的通知信息
	 * @param request
	 * @return
	 */
	private static String getPacketStr(HttpServletRequest request) {
		String packet = request.getParameter(PACKET);
		if (packet == null) {
			try {
				InputStream is = request.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				// 最大读取b长度数据,如果数据流不足b长度,则当读到流截止符时,停止读取数据
				List<Byte> byteList = new ArrayList<Byte>();
				byte[] temp = new byte[1];
				while (bis.read(temp) != -1) {
					byteList.add(temp[0]);
				}
				int size = byteList.size();

				if (size > 0) {
					byte[] b = new byte[size];
					for (int i = 0; i < size; i++) {
						b[i] = byteList.get(i);
					}
					packet = new String(b, ENCODE);
				} else {
//					System.err.println(" 从request中获取请求数据流中获取为空 ");
				}
			} catch (Exception e) {
//				System.err.println("从request中获取请求数据流异常: " + e.toString());
			}
		}
//		System.out.println("packet＝"+ packet+"");
		return packet;
	}
	
	
	
	/**
	 * 验证签名
	 * @param md5Key
	 * @param signFields
	 * @param json
	 * @return true:成功,false:失败,null:异常
	 */
	public static Boolean verifySignMd5(String md5Key, String[] signFields, JSONObject json) {
		try {
			String sign = json.getString("sign");
			String countSign = countSignMd5(md5Key, signFields, json);
//			System.out.println("sign["+sign+"]");
//			System.out.println("countSign["+countSign+"]");
			return countSign.equals(sign);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 构建签名原文
	 * @param signFilds
	 * @param packet
	 * @return
	 */
	private static String orgSignSrc(String[] signFields, JSONObject packet) {
		if (signFields != null) {
			Arrays.sort(signFields); // 对key按照 字典顺序排序
		}

		StringBuffer signSrc = new StringBuffer("");
		int i = 0;
		for (String key : signFields) {
			signSrc.append(key);
			signSrc.append(EQUAL);
			signSrc.append((StringUtils.isEmpty(packet.getString(key)) ? "" : packet.getString(key)));
			// 最后一个元素后面不加&
			if (i < (signFields.length - 1)) {
				signSrc.append("&");
			}
			i++;
		}
		return signSrc.toString();
	}
	/**
	 * 计算MD5签名
	 * @Description 按照支付规范计算MD5签名
	 * @param md5Key
	 * @param signFields
	 * @param json
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * 
	 * @since 1.0
	 */
	private static String countSignMd5(String md5Key, String[] signFields, JSONObject json)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String signSrc = orgSignSrc(signFields, json);
		signSrc += "&KEY=" + md5Key;
		String signMd5 = Sec.MD5Encrypt(signSrc, 32);
//		System.out.println("生成的MD5="+signMd5);
		return signMd5;
	}
	
}
