package cn.jeesoft.mvc.action.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jeesoft.core.interceptor.Before;
import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.interceptor.UsersLoginInterceptor;
import cn.jeesoft.mvc.action.BaseAppAction;
import cn.jeesoft.mvc.bean.ProductCart;
import cn.jeesoft.mvc.helper.LoginUserHolder;
import cn.jeesoft.mvc.model.ResultCode;
import cn.jeesoft.mvc.services.ProductCartService;

/**
 * 购物车管理
 * @author hzy
 *
 */
@RestController("app.product.cart")
@RequestMapping("app/product/cart")
@Before(UsersLoginInterceptor.class)
public class ProductCartAction extends BaseAppAction<ProductCart, ProductCartAction.MPagerModel> {
	static class MPagerModel extends PagerModel<ProductCart, ProductCart> {
	}
	
	@Autowired
	private ProductCartService productCartService;
	
	
	//分页查询购物车列表
	@ResponseBody
	@RequestMapping("list")
	public String list(HttpServletRequest request, MPagerModel pager) {
		if (pager.getQuery() == null) {
			pager.setQuery(new ProductCart());
		}
		pager.getQuery().setUsersId(LoginUserHolder.getLoginUser().getId());
		pager = productCartService.selectPageList(pager);
		return toSuccess(pager);
	}

	/**
	 * 加入购物车
	 * 接口参数 param productId=商品ID number=商品数量 
	 */
	@ResponseBody
	@RequestMapping("append")
	public String append(HttpServletRequest request, ProductCart bean) {
		if (bean.getNumber() == null || bean.getNumber() <= 0) {
			bean.setNumber(1);
		}
		return updateOrAppend(bean, true);
	}
	
	/**
	 * 修改购物车某条商品的数量
	 * 接口参数 param productId=商品ID number=商品数量
	 */
	@ResponseBody
	@RequestMapping("update")
	public String update(HttpServletRequest request, ProductCart bean) {
		if (bean.getNumber() == null || bean.getNumber() <= 0) {
			bean.setNumber(1);
		}
		return updateOrAppend(bean, false);
	}
	
	private String updateOrAppend(ProductCart bean, boolean append) {
		bean.setUsersId(LoginUserHolder.getLoginUser().getId());
		bean.setCreateTime(new Date()); //当前时间
		
		// 更新数量
		int result;
		try {
			result = productCartService.updateOrAppend(bean, append);
			if (result <= 0) {
				return toFailure(ResultCode.FAILURE, "加入购物车失败，请稍后重试");
			}
		} catch (Exception e) {
			return toFailure(ResultCode.FAILURE, e);
		}
		return toSuccess();
	}
	
	/**
	 * 商品移除购物车
	 * 接口参数 param productId=商品ID
	 */
	@ResponseBody
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, ProductCart bean) {
		if(StringUtils.isEmpty(bean.getProductId())){
			return toFailure(ResultCode.ERROE_PARAMETER, "商品ID不能为空");
		}
		
		// 获取SESSION里的userId
		bean.setUsersId(LoginUserHolder.getLoginUser().getId());
		int result= productCartService.delete(bean); //开始删除
		if(result > 0){
			return toSuccess();
		}else{
			return toFailure(ResultCode.FAILURE, "移除购物车失败，请稍后重试"); 
		}
	}

	@Override
	public ProductCartService getService() {
		return productCartService;
	}
}
