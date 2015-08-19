package cn.jeesoft.mvc.action;

import cn.jeesoft.core.model.BaseBean;
import cn.jeesoft.core.model.PagerModel;

/**
 * Web Action基类
 * @author king 
 * @param <M>
 */
public abstract class BaseWebAction<M extends BaseBean, P extends PagerModel<M, ?>> extends BaseAction<M, P> {

    public static final String Redirect = "redirect:";
    public static final String WEB_INF = "/WEB-INF/";
    public static final String FTL_FRONT = WEB_INF + "ftl/front/";
    public static final String FTL_MANAGE = WEB_INF + "ftl/manage/";
    
    public static final String URL_MANAGE = "/manage/";
    
}
