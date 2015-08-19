package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.mvc.model.PayType;
import cn.jeesoft.mvc.model.TradeState;
import cn.jeesoft.mvc.model.TradeType;

/**
 * 交易信息
 * @author king
 */
@SuppressWarnings("serial")
public class SysTrade extends BaseBean {
	
	/** 付款人 */
	private Integer fromId;
	/** 收款人 */
	private Integer toId;
	/** 交易名称 */
	private String name;
	/** 交易编号 */
	private String sn;
	/** 交易类型 */
	private TradeType type;
	/** 交易状态 */
	private TradeState state;
	/** 交易金额 */
	private Integer money;
	/** 创建时间 */
	private Date createTime;
	/** 完成时间 */
	private Date endTime;
	/** 支付方式 */
	private PayType payType;
	/** 支付编号 */
	private String paySn;
	/** 是否即时到帐 */
	private Boolean isInstant;
	/** 备注 */
	private String remark;

	/** 手续费 */
	private Integer feesMoney;
	/** 一级收益 */
	private Integer oneProfitMoney;
	/** 二级收益 */
	private Integer twoProfitMoney;
	/** 三级收益 */
	private Integer threeProfitMoney;
	

	public SysTrade() {
		super();
	}
	public SysTrade(Integer fromId, Integer toId, String name, String sn,
			TradeType type, TradeState state, Integer money, Date createTime,
			Date endTime, PayType payType, String paySn, Boolean isInstant, String remark,
			Integer feesMoney, Integer oneProfitMoney, Integer twoProfitMoney, Integer threeProfitMoney) {
		super();
		this.fromId = fromId;
		this.toId = toId;
		this.name = name;
		this.sn = sn;
		this.type = type;
		this.state = state;
		this.money = money;
		this.createTime = createTime;
		this.endTime = endTime;
		this.payType = payType;
		this.paySn = paySn;
		this.isInstant = isInstant;
		this.remark = remark;
		this.feesMoney = feesMoney;
		this.oneProfitMoney = oneProfitMoney;
		this.twoProfitMoney = twoProfitMoney;
		this.threeProfitMoney = threeProfitMoney;
	}



	public void clear() {
		this.id = null;
		this.fromId = null;
		this.toId = null;
		this.name = null;
		this.sn = null;
		this.type = null;
		this.state = null;
		this.money = null;
		this.createTime = null;
		this.endTime = null;
		this.payType = null;
		this.paySn = null;
		this.remark = null;
		this.isInstant = null;
		this.feesMoney = null;
		this.oneProfitMoney = null;
		this.twoProfitMoney = null;
		this.threeProfitMoney = null;
	}
	
	
	
	@Override
	public String toString() {
		return "SysTrade [getId()=" + getId() + ", isInstant="+", fromId=" + fromId + ", toId=" + toId + ", name="
				+ name + ", sn=" + sn + ", type=" + type + ", state=" + state
				+ ", money=" + money + ", createTime=" + createTime
				+ ", endTime=" + endTime + ", payType=" + payType + ", paySn="
				+ paySn + ", remark=" + remark + ", feesMoney=" + feesMoney
				+ ", oneProfitMoney=" + oneProfitMoney + ", twoProfitMoney=" + twoProfitMoney + ", threeProfitMoney=" + threeProfitMoney + "]";
	}
	
	
	
	
	public Integer getFeesMoney() {
		if (feesMoney == null) {
			return 0;
		}
		return feesMoney;
	}
	public void setFeesMoney(Integer feesMoney) {
		this.feesMoney = feesMoney;
	}
	public Integer getOneProfitMoney() {
		return oneProfitMoney;
	}
	public void setOneProfitMoney(Integer oneProfitMoney) {
		this.oneProfitMoney = oneProfitMoney;
	}
	public Integer getTwoProfitMoney() {
		return twoProfitMoney;
	}
	public void setTwoProfitMoney(Integer twoProfitMoney) {
		this.twoProfitMoney = twoProfitMoney;
	}
	public Integer getThreeProfitMoney() {
		return threeProfitMoney;
	}
	public void setThreeProfitMoney(Integer threeProfitMoney) {
		this.threeProfitMoney = threeProfitMoney;
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
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public TradeState getState() {
		return state;
	}
	public void setState(TradeState state) {
		this.state = state;
	}
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TradeType getType() {
		return type;
	}
	public void setType(TradeType type) {
		this.type = type;
	}
	public String getPaySn() {
		return paySn;
	}
	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}
	public Boolean getIsInstant() {
		return isInstant;
	}
	public void setIsInstant(Boolean isInstant) {
		this.isInstant = isInstant;
	}
	
}
