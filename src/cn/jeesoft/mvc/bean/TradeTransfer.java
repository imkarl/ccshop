package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;

/**
 * 交易-转账
 * @author king
 */
@SuppressWarnings("serial")
public class TradeTransfer extends BaseBean {
	
	/** 付款人 */
	private Integer fromId;
	/** 收款人 */
	private Integer toId;
	/** 交易编号 */
	private String sn;
	/** 交易金额(单位：分) */
	private Integer money;
	/** 创建时间 */
	private Date createTime;
	/** 是否到账 */
	private Boolean isArrival;
	

	public TradeTransfer() {
		super();
	}
	public TradeTransfer(Integer fromId, Integer toId, String sn, Integer money, Date createTime, Boolean isArrival) {
		super();
		this.fromId = fromId;
		this.toId = toId;
		this.sn = sn;
		this.money = money;
		this.createTime = createTime;
		this.isArrival = isArrival;
	}



	public void clear() {
		this.id = null;
		this.fromId = null;
		this.toId = null;
		this.sn = null;
		this.money = null;
		this.createTime = null;
		this.isArrival = null;
	}
	
	
	
	@Override
	public String toString() {
		return "SysTrade [isInstant="+", fromId=" + fromId + ", toId=" + toId
				+ ", sn=" + sn + ", money=" + money + ", createTime=" + createTime
				+ ", id=" + getId() +", isArrival="+isArrival + "]";
	}
	
	
	
	
	public Boolean getIsArrival() {
		return isArrival;
	}
	public void setIsArrival(Boolean isArrival) {
		this.isArrival = isArrival;
	}
	public Integer getFromId() {
		return fromId;
	}
	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}
	public Integer getToId() {
		return toId;
	}
	public void setToId(Integer toId) {
		this.toId = toId;
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
	
}
