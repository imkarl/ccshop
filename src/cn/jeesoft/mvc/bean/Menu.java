package cn.jeesoft.mvc.bean;

import java.util.List;

import cn.jeesoft.core.model.BaseBean;

/**
 * 菜单
 * @author king
 */
@SuppressWarnings("serial")
public class Menu extends BaseBean {

	private Integer pid;// 菜单项的父亲节点
	private String url;// 菜单的URL地址
	private String name;// 菜单名称
	private List<Menu> children;// 子节点
	

	public Menu() {
		super();
	}
	public Menu(Integer id, Integer pid, String url, String name, List<Menu> children) {
		super();
		this.id = id;
		this.pid = pid;
		this.url = url;
		this.name = name;
		this.children = children;
	}
	public Menu(Menu menu) {
		super();
		this.id = menu.getId();
		this.pid = menu.getPid();
		this.url = menu.getUrl();
		this.name = menu.getName();
		this.children = menu.getChildren();
	}

	
	@Override
	public void clear() {
		super.clear();
		this.id = null;
		this.pid = null;
		this.url = null;
		this.name = null;
		BaseBean.clearListBean(this.getChildren());
		this.children = null;
	}
	
	
	@Override
	public String toString() {
		return "Menu [id=" + id + ", pid=" + pid + ", url=" + url + ", name=" + name
				+ ", children=" + children + "]";
	}




	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	
}
