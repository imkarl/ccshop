package cn.jeesoft.mvc.action.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.interceptor.UsersLoginInterceptor;
import cn.jeesoft.mvc.action.BaseAppAction;
import cn.jeesoft.mvc.bean.ProductFavorite;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.services.BaseService;
import cn.jeesoft.mvc.services.ProductFavoriteService;

/**
 * 商品管理
 * @author king
 */
@RestController("app.product.favorite")
@RequestMapping("app/product/favorite")
@Before(UsersLoginInterceptor.class)
public class ProductFavoriteAction extends BaseAppAction<ProductFavorite, ProductFavoriteAction.MPagerModel> {
	static class MPagerModel extends PagerModel<ProductFavorite, ProductFavorite> {
	}
	
	@Autowired
	private ProductFavoriteService productFavoriteService;

	@ResponseBody
	@RequestMapping("list")
	public String list(HttpServletRequest request, ProductAction.MPagerModel pager) {
		pager = productFavoriteService.list(pager);
		return toSuccess(pager);
	}
	
	@ResponseBody
	@RequestMapping("insert")
	public String insert(HttpServletRequest request, ProductFavorite bean) {
		if (bean.getProductId() == null || bean.getProductId() <= 0) {
			return toFailure(ResultCode.ERROE_PARAMETER, "收藏失败，商品ID不合法");
		}
		
		bean.setCreateTime(null);
		bean.setUsersId(LoginUserHolder.getLoginUser().getId());
		ProductFavorite favorite = productFavoriteService.selectOne(bean);
		if (favorite != null) {
			return toFailure(ResultCode.REPEAT, "商品已收藏");
		}
		
		int insertId = productFavoriteService.insert(bean);
		if (insertId > 0) {
			return toSuccess();
		} else {
			return toFailure(ResultCode.FAILURE, "收藏失败，请稍后重试");
		}
	}

	@Override
	public BaseService<ProductFavorite> getService() {
		return productFavoriteService;
	}

}
