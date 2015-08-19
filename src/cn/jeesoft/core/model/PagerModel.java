package cn.jeesoft.core.model;

import java.util.Date;
import java.util.List;

import cn.jeesoft.core.model.BaseBean.Sort;
import cn.jeesoft.core.utils.ClassUtils;
import cn.jeesoft.core.utils.DateUtils;
import cn.jeesoft.mvc.Config;

/**
 * 分页模型
 * @author king 
 * @param <M> 返回数据类型
 * @param <Q> 查询条件类型
 */
public class PagerModel<M extends BaseBean, Q extends BaseBean> implements ClearBean {
	
	private int total; // 总数
	private int pageSize = Config.PAGE_SIZE;// 每页显示记录数
	private int pageCount;// 总页数
	private int offset; // 偏移量
	private Q query; // 查询条件
	private String orderby; // 排序条件
	private Sort sort; // 排序方式

	private Date startTime;
	private Date endTime;
	
	private List<M> list; // 分页数据集合


	public void clear() {
		total = 0;
		pageSize = Config.PAGE_SIZE;
		pageCount = 0;
		offset = 0;
		query = null;
		list = null;
		orderby = null;
		sort = null;
		startTime = null;
		endTime = null;
	}
	
	
	@Override
	public String toString() {
		return "total="+total+",offset="+offset+",query="+query+",orderby="+orderby+",sort="+sort
				+", startTime="+DateUtils.format(startTime)+", endTime="+DateUtils.format(endTime)
				+",list="+(list==null?"null":list.size());
	}
	
	@SuppressWarnings("unchecked")
	public Q createQueryBean() {
		Q bean = null;
		try {
			bean = (Q) ClassUtils.newInstance(ClassUtils.getGenericSuperclass(getClass())[1]);
		} catch (Exception e) {
		}
		return bean;
	}
	
	
	/*
	 * getter\setter
	 */
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public List<M> getList() {
		return list;
	}
	public void setList(List<M> list) {
		this.list = list;
	}
	public Q getQuery() {
		return query;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public void setQuery(Q query) {
		this.query = query;
	}
	
}
