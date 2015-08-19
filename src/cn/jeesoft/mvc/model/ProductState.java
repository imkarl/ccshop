package cn.jeesoft.mvc.model;

/**
 * 商品状态
 * @author king
 */
public enum ProductState {
	
	YES(1, "上架"),
	NO(0, "下架"),
	;
	
	private int code;
	private String name;
	private ProductState(int code, String name) {
		this.code = code;
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
	public int getCode() {
		return this.code;
	}
	public String getName() {
		return this.name;
	}
}
