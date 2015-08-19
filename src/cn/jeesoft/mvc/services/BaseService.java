package cn.jeesoft.mvc.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.DateUtils;
import cn.jeesoft.core.utils.DateUtils.FieldType;
import cn.jeesoft.mvc.dao.BaseDao;

/**
 * 服务基类
 * @author king
 */
public abstract class BaseService<M extends BaseBean> {
	
    @Resource
	private BaseDao dao;

    @Resource(name = "baseDao")
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
    public BaseDao getDao() {
    	return dao;
    }
    
    
    /**
     * 获取表名
     */
    public abstract String getTablename();


    /**
     * 查询集合
     * @param bean
     * @return
     */
	public List<M> selectList(M bean) {
		return dao.selectList(getTablename()+".selectList", bean);
	}

    /**
     * 分页查询
     * @param pager
     * @return
     */
	public <Q extends BaseBean, P extends PagerModel<M, Q>> P selectPageList(P pager) {
		return selectPageList("selectPageList", "selectPageCount", pager);
	}
	/**
	 * 分页查询
	 * @param selectList 查询SQL的ID名称
	 * @param selectCount 查询SQL的ID名称
	 * @param pager
	 * @return
	 */
	public <Q extends BaseBean, P extends PagerModel<M, Q>> P selectPageList(String selectList, String selectCount, P pager) {
        if (pager.getOffset() < 0)
        	pager.setOffset(0);
        if (pager.getQuery() == null) {
        	pager.setQuery(pager.createQueryBean());
        }
        
        if (pager.getEndTime() != null) {
        	pager.setEndTime(DateUtils.append(pager.getEndTime(), FieldType.DAY, 1));
        }
        // 执行查询
        pager = (P) dao.selectPageList(getTablename()+"."+selectList,
				getTablename()+"."+selectCount,
				pager);
        if (pager.getEndTime() != null) {
        	pager.setEndTime(DateUtils.append(pager.getEndTime(), FieldType.DAY, -1));
        }

        // 计算总页数
        pager.setPageCount((pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		return pager;
	}

	/**
	 * 查询单个实体
	 * @param bean
	 * @return
	 */
	public M selectOne(M bean) {
		return dao.selectOne(getTablename()+".selectOne", bean);
	}

	/**
	 * 删除单个实体
	 * @param bean
	 * @return 删除的记录条数
	 */
	public int delete(M bean) {
		return dao.delete(getTablename()+".delete", bean);
	}

	/**
	 * 更新单个实体
	 * @param bean
	 * @return 更新的记录条数
	 * @throws Exception 
	 */
	public int update(M bean) {
		return dao.update(getTablename()+".update", bean);
	}
	
	/**
	 * 插入单条记录
	 * @param bean
	 * @return 新插入记录的主键ID
	 */
	public int insert(M bean) {
		return dao.insert(getTablename()+".insert", bean);
	}

	/**
	 * 删除单条记录
	 * @param id
	 * @return 删除的记录条数
	 */
	public int deleteById(int id) {
		return dao.delete(getTablename()+".deleteById", id);
	}

	/**
	 * 查询单条记录
	 * @param id
	 * @return 
	 */
	public M selectById(int id) {
		return dao.selectOne(getTablename()+".selectById", id);
	}

	/**
	 * 统计
	 * @return 统计结果
	 */
	public int selectCount() {
		return dao.getCount(getTablename()+".selectCount");
	}

	/**
	 * 删除多条记录
	 * @param ids 要删除的行ID
	 * @return 删除成功的记录集合
	 */
	public List<Integer> deletes(int... ids) {
		List<Integer> idArray = new ArrayList<Integer>();
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			if (deleteById(id) > 0) {
				idArray.add(id);
			}
		}
		return idArray;
	}

}
