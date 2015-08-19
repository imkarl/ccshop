package cn.jeesoft.mvc.outapi;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.jeesoft.core.utils.DateUtils;
import cn.jeesoft.core.utils.LogUtils;
import cn.jeesoft.core.utils.Sec;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.Config;

/**
 * 话费充值
 * @author king
 */
public class CallRechargeHelper {

	
	/**
	 * 号码充值
	 * @param orderSn 订单编号
	 * @param money 充值金额（单位：分）
	 * @param phone 充值号码
	 * @return 是否成功提交充值订单
	 */
	public static boolean recharge(String orderSn, int money, String phone) {
		if (StringUtils.isEmpty(orderSn) || money < Config.MIN_MONEY_CALL || !StringUtils.isPhone(phone)) {
			return false;
		}
		
		try {
			String time = DateUtils.format(new Date(), DateUtils.FORMAT_FULL_NOSEPARATOR);
			LogUtils.logFile(time+" | "+phone+" | "+(int)(money*0.01)+" | "+orderSn);
			
			return doGet(Config.CALL_MerID, Config.CALL_MerAccount, orderSn, money, phone, Config.CALL_CallbackURL, Config.CALL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		// 测试充值，实际到账
		System.out.println(recharge("SSN20150727002", 5000, "13713216240"));
	}
	
	private static boolean doGet(String MerID, String MerAccount, String OrderID, int Value,
			String ChargeNo, String MerURL, String KEY) throws Exception {
		return doGetSource(MerID, MerAccount, OrderID, "", Value, -1, "",
				"xml", 9, "007KA_KM", "1.0.1.2", "", "",
				ChargeNo, DateUtils.format(new Date(), "yyyyMMddHHmmss"), "", MerURL, KEY);
	}
	private static boolean doGetSource(String MerID, String MerAccount, String OrderID, String CardType, int Value, int timeout, String Province,
			String ReplyFormat, int Command, String InterfaceName, String InterfaceNumber, String CardSn, String CardKey,
			String ChargeNo, String Datetime, String TranOrder, String MerURL, String KEY) throws Exception {

		String OrderInfo = MerID+"|"+MerAccount+"|"+OrderID+"|"+CardType+"|"+Value+"|"
				+timeout+"|"+Province+"|"+ReplyFormat+"|"+Command+"|"+InterfaceName+"|"+InterfaceNumber+"|"+CardSn+"|"+CardKey
				+"|"+ChargeNo+"|"+Datetime+"|"+TranOrder+"|"+MerURL+"|";
		String Sign = Sec.MD5Encrypt(OrderInfo+"|"+KEY, 32);
		
		System.out.println("OrderInfo="+OrderInfo);
//		System.out.println("Sign="+Sign);
		
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Config.CALL_URL_RECVCDKEY);
		// 多部分的实体
		MultipartEntity reqEntity = new MultipartEntity();
		// 增加
		reqEntity.addPart("Orderinfo", new StringBody(OrderInfo));
		reqEntity.addPart("Sign", new StringBody(Sign));
		// 设置
		httppost.setEntity(reqEntity);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		InputStream inStream = resEntity.getContent();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = -1;
		while ((len=inStream.read(data)) != -1) {
			baos.write(data, 0, len);
		}
		
		byte[] result = baos.toByteArray();
		String message = new String(result, "GB2312");
		if (!StringUtils.isEmpty(message)) {
			if (message.contains("<TranStat>1</TranStat>")) {
				return true;
			} else {
				System.out.println("error="+message);
			}
		}
		return false;
	}
	
}
