package cn.jeesoft.mvc.bean;

import cn.jeesoft.core.model.BaseBean;

/**
 * 账户余额信息
 * @author king
 */
@SuppressWarnings("serial")
public class Balance extends BaseBean {

	/** 所属用户 */
	private Integer usersId;
	/** 可用金额 */
	private Integer money;
	/** 锁定金额 */
	private Integer lockMoney;
	/** 账户余额变动是否锁定 */
	private Boolean isLock;
	
	
	
	public Balance() {
		super();
	}
	public Balance(Integer usersId, Integer money, Integer lockMoney, Boolean isLock) {
		super();
		this.usersId = usersId;
		this.money = money;
		this.lockMoney = lockMoney;
		this.isLock = isLock;
	}
	
	
	
	@Override
	public String toString() {
		return "Balance [usersId=" + usersId + ", money=" + money
				+ ", lockMoney=" + lockMoney + ", isLock=" + isLock + ", id="
				+ id + "]";
	}
	
	
	
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getLockMoney() {
		return lockMoney;
	}
	public void setLockMoney(Integer lockMoney) {
		this.lockMoney = lockMoney;
	}
	public Boolean getIsLock() {
		return isLock;
	}
	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}
	
}
