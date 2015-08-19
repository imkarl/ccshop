package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.util.MD5;

/**
 * 管理员
 * @author king
 */
@SuppressWarnings("serial")
public class Admin extends BaseBean {

	private String name;
	private String pass;
	private String nickname;
	private String email;
	private String phone;
	private String portrait;
	
	private Date updatetime;
	

	public Admin() {
		super();
	}
	public Admin(String name, String pass, String nickname,
			String email, String phone, String portrait, Date updatetime) {
		super();
		this.name = name;
		this.pass = pass;
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
		this.portrait = portrait;
		this.updatetime = updatetime;
	}



	public void clear() {
		this.id = -1;
		this.name = null;
		this.pass = null;
		this.nickname = null;
		this.email = null;
		this.phone = null;
		this.portrait = null;
		this.updatetime = null;
	}
	
	
	public static String encryption(String pass) {
		return MD5.md5(pass);
	}



	@Override
	public String toString() {
		return "Users [name=" + name + ", pass=" + pass
				+ ", nickname=" + nickname + ", email=" + email + ", phone="
				+ phone + ", portrait="+ portrait + ", updatetime=" + updatetime + "]";
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
	
}
