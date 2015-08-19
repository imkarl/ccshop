package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;

/**
 * 余额变动信息
 * @author king
 */
@SuppressWarnings("serial")
public class BalanceChange extends BaseBean {

	/** 所属用户 */
	private Integer fromId;
	/** 交易编号 */
	private String sn;
	/** 交易金额 */
	private Integer money;
	/** 创建时间 */
	private Date createTime;
	/** 是否充值 */
	private Boolean isAdd;
	/** 关联订单（会存入不可用金额，如话费充值时的余额充值） */
	private String linkSn;
	/** 是否到账 */
	private Boolean isArrival;
	

	
	public BalanceChange() {
		super();
	}
	public BalanceChange(Integer fromId, String sn, Integer money,
			Date createTime, Boolean isAdd, String linkSn, Boolean isArrival) {
		super();
		this.fromId = fromId;
		this.sn = sn;
		this.money = money;
		this.createTime = createTime;
		this.isAdd = isAdd;
		this.linkSn = linkSn;
		this.isArrival = isArrival;
	}
	
	
	@Override
	public void clear() {
		super.clear();
		this.fromId = null;
		this.sn = null;
		this.money = null;
		this.createTime = null;
		this.isAdd = null;
		this.linkSn = null;
		this.isArrival = null;
	}
	

	@Override
	public String toString() {
		return "BalanceChange [fromId=" + fromId + ", sn=" + sn + ", money="
				+ money + ", createTime=" + createTime + ", isAdd=" + isAdd
				+ ", linkSn=" + linkSn +", isArrival="+isArrival + "]";
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
	public Boolean getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}
	public String getLinkSn() {
		return linkSn;
	}
	public void setLinkSn(String linkSn) {
		this.linkSn = linkSn;
	}
	
}
