package cn.jeesoft.mvc.action.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.DateUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.action.BaseAppAction;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.model.TradeState;
import cn.jeesoft.mvc.model.TradeType;
import cn.jeesoft.mvc.services.ProfitService;
import cn.jeesoft.mvc.services.SysTradeService;

/**
 * 系统交易信息管理
 * @author king
 */
public class BaseTradeAction extends BaseAppAction<SysTrade, BaseTradeAction.MPagerModel> {
	static class MPagerModel extends PagerModel<SysTrade, SysTrade> {
	}
	
	@Autowired
	private SysTradeService sysTradeService;
	@Autowired
	protected ProfitService profitService;

	@Override
    public final String insert(HttpServletRequest request, SysTrade bean) {
		if (bean == null) {
        	return toFailure(ResultCode.FAILURE, "请提供正确的参数");
    	} else if (isEmptyBean(bean)) {
        	return toFailure(ResultCode.FAILURE, "参数不正确", bean);
    	}
		
		// 修改相关参数
    	bean.setState(TradeState.CREATED);
    	if (bean.getCreateTime() == null) {
    		bean.setCreateTime(new Date());
    	}
        return super.insert(request, bean);
    }
    
	@Override
    public String update(HttpServletRequest request, SysTrade bean) {
		if (bean == null) {
        	return toFailure(ResultCode.FAILURE, "请提供正确的参数");
    	} else if (bean.getId() == null || bean.getId() <= 0) {
        	return toFailure(ResultCode.FAILURE, "参数不正确", bean);
    	}

		// 修改相关参数
    	SysTrade temp = new SysTrade();
    	temp.setId(bean.getId());
    	if (bean.getState() == TradeState.SUCCESS) {
    		if (bean.getEndTime() == null) {
        		bean.setEndTime(new Date());
        	}
    		temp.setEndTime(bean.getEndTime());
    	}
    	if (bean.getPayType() != null) {
    		temp.setPayType(bean.getPayType());
    	}
    	if (!StringUtils.isEmpty(bean.getPaySn())) {
    		temp.setPaySn(bean.getPaySn());
    	}
    	if (!StringUtils.isEmpty(bean.getRemark())) {
    		temp.setRemark(bean.getRemark());
    	}
    	return super.update(null, temp);
    }
	
	@Override
    public String selectList(HttpServletRequest request, SysTrade bean) {
    	return super.selectList(request, bean);
    }
	
    @Override
    public String selectOne(HttpServletRequest request, SysTrade bean) {
    	SysTrade one = getService().selectOne(bean);
    	if (one != null) {
    		return toSuccess(one);
    	} else {
			return toFailure(ResultCode.FAILURE, "没有记录");
		}
    }

    @Override
    public String selectPageList(HttpServletRequest request, MPagerModel pager) {
    	return super.selectPageList(request, pager);
    }
    
    /**
     * 实体是否为空
     * @param bean
     * @return
     */
	protected static boolean isEmptyBean(SysTrade bean) {
		if (bean == null) {
			System.out.println("bean is Empty");
			return true;
		}
		if (bean.getFromId() == null || bean.getFromId() <= 0) {
			System.out.println("bean.fromId is Empty");
			return true;
		}
		if (bean.getToId() == null || bean.getToId() < 0) {
			System.out.println("bean.toId is Empty");
			return true;
		}
		if (StringUtils.isEmpty(bean.getName())) {
			System.out.println("bean.name is Empty");
			return true;
		}
		if (StringUtils.isEmpty(bean.getSn())) {
			System.out.println("bean.sn is Empty");
			return true;
		}
		if (bean.getType() == null || bean.getType()==TradeType.UNKNOWN) {
			System.out.println("bean.type is Empty");
			return true;
		}
		if (bean.getState() == null || bean.getState()==TradeState.UNKNOWN) {
			System.out.println("bean.state is Empty");
			return true;
		}
		if (bean.getMoney() == null || bean.getMoney() == 0) {
			System.out.println("bean.money is Empty");
			return true;
		}
		if (bean.getCreateTime() == null || bean.getCreateTime().getTime() < DateUtils.parse("2015-01-01").getTime()) {
			System.out.println("bean.createTime is Empty");
			return true;
		}
		return false;
	}
	
	@Override
	public SysTradeService getService() {
		return sysTradeService;
	}
	
}

