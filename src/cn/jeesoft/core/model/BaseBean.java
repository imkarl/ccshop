package cn.jeesoft.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * 实体类基类
 * @author king
 */
public abstract class BaseBean implements ClearBean, Serializable {
	private static final long serialVersionUID = 1L;
	

	/**
	 * 状态
	 */
	public static enum State {
		YES(1), NO(0), UNKNOWN(-1);
		
		private int code;
		private State(int code) {
			this.code = code;
		}
		public int getCode() {
			return this.code;
		}
	}
	/**
	 * 排序
	 */
	public static enum Sort {
		DESC("desc"), ASC("asc");
		
		private String name;
		private Sort(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}
	

	/**
	 * 重置实体类数据
	 * @param list
	 */
	public static void clearListBean(List<? extends ClearBean> list){
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			ClearBean item = list.get(i);
			item.clear();
			item = null;
		}
		try {
			list.clear();
		} catch (Exception e) {
		}
		list = null;
	}
	
	
	protected Integer id;

	@Override
	public void clear() {
		this.id = null;
	}
	
	/*
	 * getter\setter
	 */
	public Integer getId() {
		if (id == null || id<0) {
			return null;
		}
		return id;
	}
	public void setId(Integer id) {
		if (id == null || id < 0) {
			this.id = null;
		} else {
			this.id = id;
		}
	}

}
