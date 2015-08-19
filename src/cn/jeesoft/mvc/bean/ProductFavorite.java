package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;

/**
 * 商品收藏
 * @author king
 */
@SuppressWarnings("serial")
public class ProductFavorite extends BaseBean {
	
	private Integer usersId;
	private Integer productId;
	private Date createTime;
	

	public ProductFavorite() {
		super();
	}
	public ProductFavorite(Integer usersId, Integer productId,
			Date createTime) {
		super();
		this.usersId = usersId;
		this.productId = productId;
		this.createTime = createTime;
	}
	
	
	@Override
	public void clear() {
		super.clear();
		this.usersId = null;
		this.productId = null;
		this.createTime = null;
	}
	
	
	@Override
	public String toString() {
		return "ProductFavorite [id=" + id + ", usersId=" + usersId
				+ ", productId=" + productId + ", createTime=" + createTime
				+ "]";
	}



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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
