package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;

/**
 * 购物车实体
 * @author hzy
 */
@SuppressWarnings("serial")
public class ProductCart extends BaseBean{
	
	private Integer usersId;//用户ID
	private Integer productId;//商品ID
	private Integer number;//购物车里当前商品数量
	private Date createTime;//加入购物车时间
	
	
	public ProductCart(){
		super();
	}
	
	public ProductCart(Integer id,Integer usersId,Integer productId,
			Integer number,Date createTime){
		super();
		this.id=id;
		this.usersId=usersId;
		this.productId=productId;
		this.number=number;
		this.createTime=createTime;
	}
	
	@Override
	public String toString() {
		return "ProductCart [id="+id+",usersId="+usersId+"," +
				"productId="+productId+",number="+number+"" +
						",createTime="+createTime+"]";
	}
	
	@Override
	public void clear() {
		super.clear();
		this.id=null;
		this.usersId=null;
		this.productId=null;
		this.number=null;
		this.createTime=null;
	}

	/*
	 * getter\setter
	 */
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
