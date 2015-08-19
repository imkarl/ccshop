package cn.jeesoft.mvc.action.web;

import org.springframework.beans.factory.annotation.Autowired;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.oscache.ManageCache;
import cn.jeesoft.mvc.action.BaseWebAction;

/**
 * Web Action基类
 * @author king 
 * @param <M>
 */
public abstract class BaseManageAction<M extends BaseBean, P extends PagerModel<M, ?>> extends BaseWebAction<M, P> {

	@Autowired
	private ManageCache manageCache;
	
    
}
