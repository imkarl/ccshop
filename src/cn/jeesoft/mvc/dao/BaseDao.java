package cn.jeesoft.mvc.dao;

import java.util.List;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import cn.jeesoft.core.exception.DbException;
import cn.jeesoft.core.exception.PrivilegeException;
import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.StringUtils;

/**
 * 封装mybatis最基本的数据库操作
 * @author king
 */
@Repository("baseDao")
public class BaseDao extends SqlSessionDaoSupport {

	private static final boolean selectPrivilege = false;

	/**
	 * 打开session，mybatis中的session能进行数据库基本的操作
	 * @return
	 */
	private SqlSession openSession() {
		try {
			return super.getSqlSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 查询一条记录
	 * @param arg0
	 * @return
	 */
	public <M extends BaseBean> M selectOne(String arg0) {
		SqlSession session = openSession();
		return session.selectOne(arg0);
	}
	/**
	 * 查询一条记录
	 * @param arg0
	 * @return
	 */
	public <M extends BaseBean> M selectOne(String arg0, int id) {
		if (id <= 0) {
			return null;
		}
		
		SqlSession session = openSession();
		return session.selectOne(arg0, id);
	}
	/**
	 * 查询一条记录
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public <M extends BaseBean> M selectOne(String arg0, M param) {
		if (param == null) {
			return null;
		}
		
		SqlSession session = openSession();
		M bean = null;
		try {
			bean = session.selectOne(arg0, param);
		} catch (MyBatisSystemException e) {
			if (e != null && e.getCause() instanceof TooManyResultsException) {
			} else {
				throw e;
			}
		}
		return bean;
	}

	/**
	 * 分页查询
	 * 
	 * @param selectList
	 * @param selectCount
	 * @param param
	 * @return
	 */
	public <M extends BaseBean, Q extends BaseBean, P extends PagerModel<M, Q>> P selectPageList(String selectList,
			String selectCount, P param) {
		if (param == null) {
			return null;
		}
		
		SqlSession session = openSession();
		
		List<M> list = session.selectList(selectList, param);
		Object oneC = session.selectOne(selectCount, param);
		int count = StringUtils.toInt(oneC, 0);

		if (list==null || list.isEmpty()) {
			list = null;
		}
		
		param.setList(list);
		param.setTotal(count);
		return param;
	}

	/**
	 * 查询多条记录
	 * 
	 * @param arg0
	 * @return
	 */
	public <M extends BaseBean> List<M> selectList(String arg0) {
		SqlSession session = openSession();
		List<M> datas = session.selectList(arg0);
		if (datas==null || datas.isEmpty()) {
			datas = null;
		}
		return datas;
	}

	/**
	 * 查询多条记录
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public <M extends BaseBean> List<M> selectList(String arg0, M arg1) {
		SqlSession session = openSession();
		List<M> datas = session.selectList(arg0, arg1);
		if (datas == null || datas.isEmpty()) {
			datas = null;
		}
		return datas;
	}

	/**
	 * 查询总数
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public int getCount(String arg0) {
		SqlSession session = openSession();
		return session.selectOne(arg0);
	}

	/**
	 * 插入一条记录
	 * 
	 * @param arg0
	 * @return
	 */
	public int insert(String arg0) {
		if(selectPrivilege){
			throw new PrivilegeException("只具备查询的权限！");
		}
		SqlSession session = openSession();
		return session.insert(arg0);
	}

	/**
	 * 插入一条记录，成功则返回插入的ID；失败则抛出异常
	 * 
	 * @param arg0
	 * @param bean
	 * @return
	 */
	public int insert(String arg0, BaseBean bean) {
		if(selectPrivilege){
			throw new PrivilegeException("只具备查询的权限！");
		}
		
		if (bean == null) {
			return -1;
		}
		SqlSession session = openSession();
		int row = session.insert(arg0, bean);
		if(row == 1){
			return bean.getId();
		}
		throw new DbException("数据插入异常");
	}

	/**
	 * 更新一条记录
	 * 
	 * @param arg0
	 * @return
	 */
	public int update(String arg0) {
		if(selectPrivilege){
			throw new PrivilegeException("只具备查询的权限！");
		}
		SqlSession session = openSession();
		return session.update(arg0);
	}

	/**
	 * 更新一条记录
	 * 
	 * @param arg0
	 * @param bean
	 * @return
	 */
	public int update(String arg0, BaseBean bean) {
		if(selectPrivilege){
			throw new PrivilegeException("只具备查询的权限！");
		}

		if (bean == null) {
			return -1;
		}
		SqlSession session = openSession();
		return session.update(arg0, bean);
	}

	/**
	 * 删除一条记录
	 * 
	 * @param arg0
	 * @return
	 */
	public int delete(String arg0) {
		if(selectPrivilege){
			throw new PrivilegeException("只具备查询的权限！");
		}
		SqlSession session = openSession();
		return session.delete(arg0);
	}

	/**
	 * 删除一条记录
	 * 
	 * @param arg0
	 * @param id
	 * @return
	 */
	public int delete(String arg0, int id) {
		if(selectPrivilege){
			throw new PrivilegeException("只具备查询的权限！");
		}

		if (id <= 0) {
			return -1;
		}
		SqlSession session = openSession();
		return session.delete(arg0, id);
	}

	/**
	 * 删除一条记录
	 * 
	 * @param arg0
	 * @param bean
	 * @return
	 */
	public int delete(String arg0, BaseBean bean) {
		if(selectPrivilege){
			throw new PrivilegeException("只具备查询的权限！");
		}

		if (bean == null) {
			return -1;
		}
		SqlSession session = openSession();
		return session.delete(arg0, bean);
	}

}
