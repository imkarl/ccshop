package cn.jeesoft.mvc.bean;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.mvc.model.AuthState;

/**
 * 用户认证资料
 * @author king
 */
@SuppressWarnings("serial")
public class UsersAuth extends BaseBean {

	/** 所属用户 */
	private Integer usersId;
	/** 照片 */
	private String photo;
	/** 姓名 */
	private String name;
	/** 身份证 */
	private String idcard;
	/** 银行卡号 */
	private String bankCard;
	/** 开户银行 */
	private String bankName;
	/** 银行网点 */
	private String bankBranch;
	/** 开户地址 */
	private String bankAddr;
	/** 提交认证时间 */
	private Date createTime;
	/** 认证通过时间 */
	private Date updateTime;
	/** 认证状态 */
	private AuthState authState;
	
	
	public UsersAuth() {
		super();
	}
	public UsersAuth(Integer usersId, String photo, String name, String idcard,
			String bankCard, String bankName, String bankBranch,
			String bankAddr, Date createTime, Date updateTime, AuthState authState) {
		super();
		this.usersId = usersId;
		this.photo = photo;
		this.name = name;
		this.idcard = idcard;
		this.bankCard = bankCard;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.bankAddr = bankAddr;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.authState = authState;
	}



	@Override
	public String toString() {
		return "UsersAuth [usersId=" + usersId + ", photo=" + photo + ", name="
				+ name + ", idcard=" + idcard + ", bankCard=" + bankCard
				+ ", bankName=" + bankName + ", bankBranch=" + bankBranch
				+ ", bankAddr=" + bankAddr + ", createTime=" + createTime
				+ ", updateTime=" + updateTime+", authState="+authState + "]";
	}
	
	/**
	 * 获取银行信息
	 * @return
	 */
	public JSONObject getBankInfo() {
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("idcard", idcard);
		json.put("bankCard", bankCard);
		json.put("bankName", bankName);
		json.put("bankBranch", bankBranch);
		json.put("bankAddr", bankAddr);
		return json;
	}
	
	public AuthState getAuthState() {
		return authState;
	}
	public void setAuthState(AuthState authState) {
		this.authState = authState;
	}
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getBankAddr() {
		return bankAddr;
	}
	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
