package cn.jeesoft.mvc.bean;

import java.util.Date;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.mvc.model.ProductState;

/**
 * 商品
 * @author king
 */
@SuppressWarnings("serial")
public class Product extends BaseBean {

	private Integer usersId; //创建者
	
	private Integer catalogId;// 商品目录ID
	private String name;// 商品名称
	private String description;//商品描述
	private String info; //商品详细描述
	private Integer unitPrice;// 定价
	private Integer nowPrice;// 现价
	private String picture;// 图片地址
	private Boolean isTop; // 是否推荐商品
	private String unit;//商品单位。默认“件”
	private Date createTime;// 录入时间
	private Date updateTime;  // 更新时间
	private ProductState state;  // 商品状态（1：已上架，0：已下架）
	private Integer viewCount;// 浏览次数
	private Integer sellCount;// 销售量
	private Integer stockCount;// 库存量
	private String keywords;//页面关键字
	

	public Product() {
		super();
	}
	public Product(Integer usersId, Integer catalogId, String name,
			String description, String info, Integer unitPrice,
			Integer nowPrice, String picture, Boolean isTop, String unit,
			Date createTime, Date updateTime, ProductState state,
			Integer viewCount, Integer sellCount, Integer stockCount,
			String keywords) {
		super();
		this.usersId = usersId;
		this.catalogId = catalogId;
		this.name = name;
		this.description = description;
		this.info = info;
		this.unitPrice = unitPrice;
		this.nowPrice = nowPrice;
		this.picture = picture;
		this.isTop = isTop;
		this.unit = unit;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
		this.viewCount = viewCount;
		this.sellCount = sellCount;
		this.stockCount = stockCount;
		this.keywords = keywords;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", usersId=" + usersId + ", catalogId="
				+ catalogId + ", name=" + name + ", description=" + description
				+ ", info=" + info + ", unitPrice=" + unitPrice + ", nowPrice="
				+ nowPrice + ", picture=" + picture + ", isTop=" + isTop
				+ ", unit=" + unit + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", state=" + state
				+ ", viewCount=" + viewCount + ", sellCount=" + sellCount
				+ ", stockCount=" + stockCount + ", keywords=" + keywords + "]";
	}
	
	
	public void clear() {
		super.clear();
		this.id = null;
		this.usersId = null;
		this.catalogId = null;
		this.name = null;
		this.description = null;
		this.info = null;
		this.unitPrice = null;
		this.nowPrice = null;
		this.picture = null;
		this.isTop = null;
		this.unit = null;
		this.createTime = null;
		this.updateTime = null;
		this.state = null;
		this.viewCount = null;
		this.sellCount = null;
		this.stockCount = null;
		this.keywords = null;
		this.description = null;
	}
	
	
	/*
	 * getter\setter
	 */
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getNowPrice() {
		return nowPrice;
	}
	public void setNowPrice(Integer nowPrice) {
		this.nowPrice = nowPrice;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public ProductState getState() {
		return state;
	}
	public void setState(ProductState state) {
		this.state = state;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public Integer getSellCount() {
		return sellCount;
	}
	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	
}
