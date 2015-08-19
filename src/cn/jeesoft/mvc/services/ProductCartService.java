package cn.jeesoft.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jeesoft.core.exception.DbException;
import cn.jeesoft.mvc.bean.Product;
import cn.jeesoft.mvc.bean.ProductCart;

/**
 * 购物车
 * @author hzy 0.1
 * @author king 0.2
 */
@Service("productCartService")
public class ProductCartService extends BaseService<ProductCart>{
	
	@Autowired
	private ProductService productService;

	/**
	 * 叠加数量
	 */
	@Override
	public int insert(ProductCart bean) {
		return updateOrAppend(bean, true);
	}

	/**
	 * 更新为指定数量
	 */
	@Override
	public int update(ProductCart bean) {
		return updateOrAppend(bean, false);
	}
	
	/**
	 * 按指定数量更新，或数量叠加
	 * @param bean 购物车实体
	 * @param append 是否叠加数量
	 * @return
	 */
	public int updateOrAppend(ProductCart bean, boolean append) {
		if (bean.getProductId() == null || bean.getProductId() <= 0) {
			throw new DbException("商品ID不能为空");
		}
		if (bean.getNumber() == null || bean.getNumber() <= 0) {
			throw new DbException("商品数量不能为空");
		}
		if (bean.getUsersId() == null || bean.getUsersId() <= 0) {
			throw new DbException("用户ID不能为空");
		}
		
		Product product = new Product();
		product.setId(bean.getProductId());
		product = productService.selectOne(product);//根据产品ID查询单个产品信息

		// 库存不足：传过来的商品数量 > 产品库存量就删除错误信息
		if(bean.getNumber() > product.getStockCount()) {
			throw new DbException("库存不足");
		}
		
		int result;
		ProductCart cart = super.selectOne(bean);//查询单个购物车对象
		if(cart == null){
			// 如果不存在，则插入购物车
			result = super.insert(bean);
		} else {
			if (append) {
				bean.setNumber(bean.getNumber() + cart.getNumber());
				// 库存不足：传过来的商品数量 > 产品库存量就删除错误信息
				if(bean.getNumber() > product.getStockCount()) {
					throw new DbException("库存不足");
				}
			}
			
			// 如果此商品存在则更新数量
			result = super.update(bean);//更新数量
		}
		return result;
	}
	
	
	@Override
	public String getTablename() {
		return "product_cart";
	}
	
}
