package cn.jeesoft.mvc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.mvc.model.ResultCode;

/**
 * App Action基类
 * @author king 
 * @param <M>
 */
public abstract class BaseAppAction<M extends BaseBean, P extends PagerModel<M, M>> extends BaseAction<M, P> {
    

    public String selectOne(HttpServletRequest request, @ModelAttribute M bean) {
    	M one = getService().selectOne(bean);
        return toSuccess(one);
    }
    public String selectOne(HttpServletRequest request, @ModelAttribute int id) {
    	M one = getService().selectById(id);
        return toSuccess(one);
    }
    
    public String selectList(HttpServletRequest request, @ModelAttribute M bean) {
    	List<M> datas = getService().selectList(bean);
    	Map<String, Object> kvPair = new HashMap<String, Object>();
    	kvPair.put("data", datas);
    	kvPair.put("count", datas.size());
        return toSuccess(kvPair);
    }
    public String selectPageList(HttpServletRequest request, @ModelAttribute P pager) {
        pager = getService().selectPageList(pager);
        return toSuccess(pager);
    }

    /**
     * 公共的批量删除数据的方法
     * @return
     * @throws Exception
     */
    public String deletes(HttpServletRequest request, int[] ids, @ModelAttribute M bean) {
        return toSuccess(getService().deletes(ids));
    }

    /**
     * 公共的更新数据的方法
     * @return
     * @throws Exception
     */
    public String update(HttpServletRequest request, @ModelAttribute M bean) {
        int updateId = getService().update(bean);
        if (updateId > 0) {
        	return toSuccess(bean);
        } else {
        	return toFailure(ResultCode.FAILURE, "更新数据失败", bean);
        }
    }
    
    /**
     * 公共的插入数据方法
     * @return
     * @throws Exception
     */
    public String insert(HttpServletRequest request, @ModelAttribute M bean) {
        int insertId = getService().insert(bean);
        if (insertId > 0) {
        	return toSuccess(bean);
        } else {
        	return toFailure(ResultCode.FAILURE, "插入数据失败", bean);
        }
    }

}
