package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.util.MD5;
import cn.jeesoft.mvc.model.AuthState;

/**
 * 用户实体类
 * @author king
 */
@SuppressWarnings("serial")
public class Users extends BaseBean {

	private String name;
	private String pass;
	private String nickname;
	private String email;
	private String phone;
	private String portrait;  // 头像
	
	private Date createtime;
	private Date updatetime;
	private State state;
	private AuthState authState;
	private Integer inviteId;  // 邀请人ID
	private String inviteCode;  // 邀请码
	

	public Users() {
		super();
	}
	public Users(String username, String password, String nickname,
			String email, String phone, String portrait, Date createtime, Date updatetime,
			State status, AuthState authState, Integer inviteId, String inviteCode) {
		super();
		this.name = username;
		this.pass = password;
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
		this.portrait = portrait;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.state = status;
		this.authState = authState;
		this.inviteId = inviteId;
		this.inviteCode = inviteCode;
	}



	public void clear() {
		super.clear();
		this.id = null;
		this.name = null;
		this.pass = null;
		this.nickname = null;
		this.email = null;
		this.phone = null;
		this.portrait = null;
		this.createtime = null;
		this.updatetime = null;
		this.state = null;
		this.authState = null;
		this.inviteId = null;
		this.inviteCode = null;
	}
	
	
	public static String encryption(String pass) {
		return MD5.md5(pass);
	}



	@Override
	public String toString() {
		return "Users [id="+id+", name=" + name + ", pass=" + pass
				+ ", nickname=" + nickname + ", email=" + email + ", phone="
				+ phone + ", portrait="+ portrait+ ", createtime=" + createtime + ", updatetime="
				+ updatetime + ", state=" + state + "authState="+authState+ ", inviteId=" + inviteId +", inviteCode="+inviteCode
				+ "]";
	}




	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public AuthState getAuthState() {
		return authState;
	}
	public void setAuthState(AuthState authState) {
		this.authState = authState;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getInviteId() {
		return inviteId;
	}
	public void setInviteId(Integer inviteId) {
		this.inviteId = inviteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
}
