package cn.jeesoft.mvc.services;

import org.springframework.stereotype.Service;

import cn.jeesoft.mvc.bean.TradeCall;

@Service("tradeCallService")
public class TradeCallService extends BaseService<TradeCall> {

	@Override
	public String getTablename() {
		return "trade_call";
	}

}
