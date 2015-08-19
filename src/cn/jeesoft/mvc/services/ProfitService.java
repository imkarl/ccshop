package cn.jeesoft.mvc.services;

import org.springframework.stereotype.Service;

import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.bean.Profit;
import cn.jeesoft.mvc.helper.ProfitHelper;

/**
 * 收益利润数据管理
 * @author hzy
 */
@Service("profitService")
public class ProfitService extends BaseService<Profit>{

	private final ProfitHelper profitHelper = new ProfitHelper(this);
	
	public ProfitHelper getHelper() {
		return profitHelper;
	}
	

	/**
	 * 根据用户ID查询费率
	 */
	public Profit selectOne(Integer usersId) {
		if (usersId == null || usersId < 0) {
			return null;
		}
		
		Profit bean = new Profit();
		bean.setUsersId(usersId);
		bean = super.selectOne(bean);
		if (bean == null) {
			if (usersId == 0) {
				bean = new Profit();
				bean.resetDefault();
			} else {
				bean = selectOne(0);
			}
		}
		bean.validCall();
		bean.setUsersId(usersId);
		return bean;
	}
	
	/**
	 * 执行更新，没有记录则自动新增
	 */
	@Override
	public int update(Profit bean) {
		if (bean.getUsersId() == null || bean.getUsersId() < 0) {
			return -1;
		}
		
		// 根据userId查询单个利润比例
		Profit profit = super.selectOne(bean);
		if (StringUtils.isEmpty(profit)) { // 为空则插入
			// 插入利润比例
			int result = insert(bean);
			if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		} else { // 不为空则修改
			return super.update(bean);// 修改利润比例
		}
	}
	

	@Override
	public String getTablename() {
		return "sys_profit";
	}

}
