package cn.jeesoft.mvc.outapi;

import javax.servlet.http.HttpServletRequest;

import cn.jeesoft.core.util.MD5;
import cn.jeesoft.mvc.Config;

/**
 * 通联支付帮助类
 * @author king
 */
public class AllinpayHelper {
	
	public static class AllinpayEntity {
		/** 通联订单号 */
		private String paymentOrderId;
		/** 商户订单号 */
		private String orderNo;
		/** 支付状态 */
		private boolean paySuccess;
		/** 验签状态 */
		private boolean verify;
		
		public AllinpayEntity() {
			super();
		}
		public AllinpayEntity(String paymentOrderId, String orderNo,
				boolean paySuccess, boolean verify) {
			super();
			this.paymentOrderId = paymentOrderId;
			this.orderNo = orderNo;
			this.paySuccess = paySuccess;
			this.verify = verify;
		}
		
		@Override
		public String toString() {
			return "AllinpayEntity [paymentOrderId=" + paymentOrderId
					+ ", orderNo=" + orderNo + ", paySuccess=" + paySuccess
					+ ", verify=" + verify + "]";
		}
		
		public String getPaymentOrderId() {
			return paymentOrderId;
		}
		public void setPaymentOrderId(String paymentOrderId) {
			this.paymentOrderId = paymentOrderId;
		}
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public boolean isPaySuccess() {
			return paySuccess;
		}
		public void setPaySuccess(boolean paySuccess) {
			this.paySuccess = paySuccess;
		}
		public boolean isVerify() {
			return verify;
		}
		public void setVerify(boolean verify) {
			this.verify = verify;
		}
	}

    /**
     * 获取通联支付通知信息
     * @param request HTTP请求
     * @return
     */
	public static AllinpayEntity getEntity(HttpServletRequest request) {
		String merchantId=request.getParameter("merchantId");
		String version=request.getParameter("version");
		String language=request.getParameter("language");
		String signType=request.getParameter("signType");
		String payType=request.getParameter("payType");
		String issuerId=request.getParameter("issuerId");
		String paymentOrderId=request.getParameter("paymentOrderId");
		String orderNo=request.getParameter("orderNo");
		String orderDatetime=request.getParameter("orderDatetime");
		String orderAmount=request.getParameter("orderAmount");
		String payDatetime=request.getParameter("payDatetime");
		String payAmount=request.getParameter("payAmount");
		String ext1=request.getParameter("ext1");
		String ext2=request.getParameter("ext2");
		String payResult=request.getParameter("payResult");
		String errorCode=request.getParameter("errorCode");
		String returnDatetime=request.getParameter("returnDatetime");
		String signMsg=request.getParameter("signMsg");
	
		AllinpayEntity entity = new AllinpayEntity();
		entity.paymentOrderId = paymentOrderId;
		entity.orderNo = orderNo;
		// 判断订单状态，为"1"表示支付成功
		entity.paySuccess = payResult.equals("1");
		// 验签
		entity.verify = AllinpayHelper.verify(merchantId, version, signType, 
				payType, paymentOrderId, orderNo, orderDatetime, 
				orderAmount, payDatetime, payAmount, payResult, 
				returnDatetime,  signMsg);
		return entity;
	}
    
    /**
     * 验证签名
     * @param merchantId
     * @param version
     * @param signType
     * @param payType
     * @param paymentOrderId
     * @param orderNo
     * @param orderDatetime
     * @param orderAmount
     * @param payDatetime
     * @param payAmount
     * @param payResult
     * @param returnDatetime
     * @param signMsg
     * @return ture:验证成功，false:失败
     */
    public static boolean verify(String merchantId, String version, String signType, String payType,
    		String paymentOrderId, String orderNo, String orderDatetime, String orderAmount,
    		String payDatetime, String payAmount, String payResult,
    		String returnDatetime, String signMsg) {
    	try {
    		StringBuffer buffer = new StringBuffer();
    		buffer.append("merchantId="+merchantId+"&");
    		buffer.append("version="+version+"&");
    		buffer.append("signType="+signType+"&");
    		buffer.append("payType="+payType+"&");
    		buffer.append("paymentOrderId="+paymentOrderId+"&");
    		buffer.append("orderNo="+orderNo+"&");
    		buffer.append("orderDatetime="+orderDatetime+"&");
    		buffer.append("orderAmount="+orderAmount+"&");
    		buffer.append("payDatetime="+payDatetime+"&");
    		buffer.append("payAmount="+payAmount+"&");
    		buffer.append("payResult="+payResult+"&");
    		buffer.append("returnDatetime="+returnDatetime+"&");
    		buffer.append("key="+Config.ALLINPAY_MD5KEY);

    		return signMsg.equalsIgnoreCase(MD5.md5(buffer.toString()));
		} catch (Exception e) {
		}
    	return false;
    }
	
}
