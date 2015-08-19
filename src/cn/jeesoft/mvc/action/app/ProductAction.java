package cn.jeesoft.mvc.action.app;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.FileUtils;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.UsersLoginInterceptor;
import cn.jeesoft.mvc.action.BaseAppAction;
import cn.jeesoft.mvc.bean.Product;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.ProductState;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.services.ProductService;

/**
 * 商品管理
 * @author king
 */
@RestController("app.product")
@RequestMapping("app/product")
@Before(UsersLoginInterceptor.class)
public class ProductAction extends BaseAppAction<Product, ProductAction.MPagerModel> {
	static class MPagerModel extends PagerModel<Product, Product> {
		private Boolean myself;
		
		@Override
		public String toString() {
			return super.toString()+", myself="+myself;
		}

		public Boolean getMyself() {
			return myself;
		}
		public void setMyself(Boolean myself) {
			this.myself = myself;
		}
	}
	
	@Autowired
	private ProductService productService;

	@ResponseBody
	@RequestMapping("list")
	public String list(HttpServletRequest request, MPagerModel pager) {
		if (pager.getQuery() == null) {
			pager.setQuery(new Product());
		}
		pager.getQuery().setUsersId(LoginUserHolder.getLoginUser().getId());
		
		if (pager.myself!=null && pager.myself) {
			pager = productService.selectPageList(pager);
		} else {
			pager = productService.selectPageList("selectPageListBuy", "selectPageCountBuy", pager);
		}
		return toSuccess(pager);
	}

    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(HttpServletRequest request, Product bean) {
    	if (StringUtils.isEmpty(bean.getName())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "商品名称不能为空");
    	} else if (StringUtils.isEmpty(bean.getDescription())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "商品描述不能为空");
    	} else if (StringUtils.isEmpty(bean.getInfo())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "商品详细描述不能为空");
    	} else if (bean.getUnitPrice()==null || bean.getUnitPrice()<=0) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "市场价不能为空");
    	} else if (bean.getNowPrice()==null || bean.getNowPrice()<=0) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "当前售价不能为空");
    	} else if (StringUtils.isEmpty(bean.getUnit())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "商品单位不能为空");
    	} else if (StringUtils.isEmpty(bean.getKeywords())) {
    		return toFailure(ResultCode.ERROE_PARAMETER, "关键字不能为空");
    	}
    	
    	Map<String, File> files = FileUtils.saveMultipartFiles(request, LoginUserHolder.getLoginUser().getPhone());
    	if (files.containsKey("picture")) {
    		bean.setPicture(FileUtils.getRelativePath(files.get("picture")));
    	} else {
    		return toFailure(ResultCode.ERROE_PARAMETER, "商品照片不能为空");
    	}

    	bean.setUsersId(LoginUserHolder.getLoginUser().getId());
    	bean.setIsTop(false);
    	bean.setCreateTime(new Date());
    	bean.setState(ProductState.YES);
    	if (bean.getStockCount()==null || bean.getStockCount()<=0) {
    		bean.setStockCount(0);
    	}

    	return super.insert(null, bean);
    }

	@Override
	public ProductService getService() {
		return productService;
	}

}
