package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;

/**
 * 用户权限
 * @author king
 */
@SuppressWarnings("serial")
public class UsersRoles extends BaseBean {
	
	public static final int VAL_YES = 1;
	public static final int VAL_NO = 0;

	/** 用户ID */
	private Integer usersId;
	/** 用户手机号 */
	private String phone;
	/** 更新时间 */
	private Date updateTime;
	/** 是否可修改利润比例 */
	private Integer hasUpdateProfit;
	/** 是否可邀请他人 */
	private Integer hasInvite;
	/** 是否可开通微店 */
	private Integer hasShop;
	/** 是否可理财 */
	private Integer hasP2p;
	/** 是否可充值话费 */
	private Integer hasCall;
	
	public UsersRoles(){
		super();
	}
	public UsersRoles(Integer usersId, String phone, Date updateTime, Integer hasUpdateProfit,
			Integer hasInvite, Integer hasShop, Integer hasP2p, Integer hasCall) {
		super();
		this.usersId = usersId;
		this.phone = phone;
		this.updateTime = updateTime;
		this.hasUpdateProfit = hasUpdateProfit;
		this.hasInvite = hasInvite;
		this.hasShop = hasShop;
		this.hasP2p = hasP2p;
		this.hasCall = hasCall;
	}

	
	
	@Override
	public String toString() {
		return "UsersRoles [usersId=" + usersId +", phone="+phone + ", updateTime="+updateTime + ", hasUpdateProfit="
				+ hasUpdateProfit + ", hasInvite=" + hasInvite + ", hasShop="
				+ hasShop + ", hasP2p=" + hasP2p + ", hasCall=" + hasCall + "]";
	}
	
	
	
	@Override
	public void clear() {
		super.clear();
		this.id = null;
		this.usersId = null;
		this.phone = null;
		this.updateTime = null;
		this.hasUpdateProfit = null;
		this.hasInvite = null;
		this.hasShop = null;
		this.hasP2p = null;
		this.hasCall = null;
	}
	
	/*
	 * getter\setter
	 */
	public Integer getHasUpdateProfit() {
		return hasUpdateProfit;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setHasUpdateProfit(Integer hasUpdateProfit) {
		this.hasUpdateProfit = hasUpdateProfit;
	}
	public Integer getHasInvite() {
		return hasInvite;
	}
	public void setHasInvite(Integer hasInvite) {
		this.hasInvite = hasInvite;
	}
	public Integer getHasShop() {
		return hasShop;
	}
	public void setHasShop(Integer hasShop) {
		this.hasShop = hasShop;
	}
	public Integer getHasP2p() {
		return hasP2p;
	}
	public void setHasP2p(Integer hasP2p) {
		this.hasP2p = hasP2p;
	}
	public Integer getHasCall() {
		return hasCall;
	}
	public void setHasCall(Integer hasCall) {
		this.hasCall = hasCall;
	}
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	
}
