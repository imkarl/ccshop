package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;

/**
 * 交易-话费充值
 * @author king
 */
@SuppressWarnings("serial")
public class TradeCall extends BaseBean {
	
	/** 付款人 */
	private Integer fromId;
	/** 充值手机号 */
	private String phone;
	/** 话费面额(单位：分) */
	private Integer callMoney;
	/** 交易编号 */
	private String sn;
	/** 交易金额(单位：分) */
	private Integer money;
	/** 创建时间 */
	private Date createTime;
	/** 充值订单 */
	private String orderSn;
	/** 是否到账 */
	private Boolean isArrival;
	

	public TradeCall() {
		super();
	}
	public TradeCall(Integer fromId, String phone, String sn, Integer money,
			Integer callMoney, Date createTime, String orderSn, Boolean isArrival) {
		super();
		this.fromId = fromId;
		this.phone = phone;
		this.callMoney = callMoney;
		this.sn = sn;
		this.money = money;
		this.createTime = createTime;
		this.orderSn = orderSn;
		this.isArrival = isArrival;
	}



	public void clear() {
		super.clear();
		this.id = null;
		this.fromId = null;
		this.phone = null;
		this.sn = null;
		this.money = null;
		this.createTime = null;
		this.callMoney = null;
		this.orderSn = null;
		this.isArrival = null;
	}
	
	
	
	@Override
	public String toString() {
		return "TradeCall [getId()=" + getId() +", fromId="+fromId + ", phone=" + phone + ", sn=" + sn
				+ ", money=" + money +", callMoney="+callMoney + ", createTime=" + createTime
				+ ", orderSn="+orderSn+", isArrival=" + isArrival + "]";
	}
	
	
	
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Boolean getIsArrival() {
		return isArrival;
	}
	public void setIsArrival(Boolean isArrival) {
		this.isArrival = isArrival;
	}
	public Integer getCallMoney() {
		return callMoney;
	}
	public void setCallMoney(Integer callMoney) {
		this.callMoney = callMoney;
	}
	public Integer getFromId() {
		return fromId;
	}
	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
