package cn.jeesoft.mvc.services;

import org.springframework.stereotype.Service;

import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.DateUtils;
import cn.jeesoft.core.utils.DateUtils.FieldType;
import cn.jeesoft.mvc.bean.Product;
import cn.jeesoft.mvc.bean.ProductFavorite;

/**
 * 商品收藏
 * @author king
 */
@Service("productFavoriteService")
public class ProductFavoriteService extends BaseService<ProductFavorite> {
	

    /**
     * 分页查询
     * @param pager
     * @return
     */
	public <P extends PagerModel<Product, Product>> P list(P pager) {
        if (pager.getOffset() < 0)
        	pager.setOffset(0);
        if (pager.getQuery() == null) {
        	pager.setQuery(new Product());
        }
        
        if (pager.getEndTime() != null) {
        	pager.setEndTime(DateUtils.append(pager.getEndTime(), FieldType.DAY, 1));
        }
        // 执行查询
        pager = (P) getDao().selectPageList(getTablename()+".selectPageList",
				getTablename()+".selectPageCount",
				pager);
        if (pager.getEndTime() != null) {
        	pager.setEndTime(DateUtils.append(pager.getEndTime(), FieldType.DAY, -1));
        }

        // 计算总页数
        pager.setPageCount((pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		return pager;
	}


	@Override
	public String getTablename() {
		return "product_favorite";
	}

}
