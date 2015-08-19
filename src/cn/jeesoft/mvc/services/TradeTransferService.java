package cn.jeesoft.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jeesoft.core.exception.DbException;
import cn.jeesoft.mvc.bean.TradeTransfer;

@Service("tradeTransferService")
public class TradeTransferService extends BaseService<TradeTransfer> {
	
	@Autowired
	private TradeBalanceService tradeBalanceService;
	
	/**
	 * 转账（插入记录，并转移金额）
	 */
	public int insert(TradeTransfer bean, int fees) {
		int transferId = super.insert(bean);
		if (transferId > 0) {
			int update = tradeBalanceService.transfer(bean.getFromId(), bean.getToId(),
					bean.getMoney(), fees, bean.getSn());
			if (update <= 0) {
				throw new DbException("转账失败");
			}
		}
		return transferId;
	}

	@Override
	public String getTablename() {
		return "trade_transfer";
	}

}
