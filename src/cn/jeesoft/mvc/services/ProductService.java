package cn.jeesoft.mvc.services;

import org.springframework.stereotype.Service;

import cn.jeesoft.mvc.bean.Product;

/**
 * 商品
 * @author king
 */
@Service("productService")
public class ProductService extends BaseService<Product> {

	@Override
	public String getTablename() {
		return "product";
	}

}
