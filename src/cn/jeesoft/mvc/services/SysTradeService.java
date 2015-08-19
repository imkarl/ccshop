package cn.jeesoft.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jeesoft.core.exception.DbException;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.bean.Users;
import cn.jeesoft.mvc.helper.GenerateSN;
import cn.jeesoft.mvc.model.TradeState;

@Service("sysTradeService")
public class SysTradeService extends BaseService<SysTrade> {
	
	@Autowired
	private TradeBalanceService tradeBalanceService;
	@Autowired
	private ProfitService profitService;
	
	/**
	 * 完成订单
	 * @param bean 必须包含[fromId]、[sn]、[money]、[feesMoney]
	 * @return 大于0表示成功
	 */
	public int finish(SysTrade bean) {
		// 获取123级代理\邀请人
		Users oneUsers = findInviteUsers(bean.getFromId());
		Users twoUsers = null;
//		Users threeUsers = null;
		if (oneUsers != null) {
			twoUsers = findInviteUsers(oneUsers.getId());
		}
//		if (twoUsers != null) {
//			threeUsers = findInviteUsers(twoUsers.getId());
//		}
		
    	// 计算123级代理收益
		countProfit(bean, oneUsers, twoUsers);
		System.out.println(oneUsers);
		System.out.println(twoUsers);
		System.out.println(bean);
		
		
		// 更新订单
		int updateSys = super.update(bean);
		if (updateSys > 0 && bean.getState()==TradeState.SUCCESS) {
			if (oneUsers != null) {
	        	// 123级代理收益
				int updateOne = tradeBalanceService.profit(oneUsers.getId(), bean.getOneProfitMoney(), bean.getSn());
				if (updateOne <= 0) {
					throw new DbException("插入记录失败");
				}

				if (twoUsers != null) {
					int updateTwo = tradeBalanceService.profit(twoUsers.getId(), bean.getTwoProfitMoney(), bean.getSn());
					if (updateTwo <= 0) {
						throw new DbException("插入记录失败");
					}
				}
			}
		}
		return updateSys;
	}
	
	
	/**
	 * 根据交易类型，计算123级代理收益
	 */
	private void countProfit(SysTrade sys, Users oneUsers, Users twoUsers) {
		// 订单不为空、完成的订单、订单号金额参数正常
		if (sys == null || sys.getState()!=TradeState.SUCCESS
				|| sys.getSn()==null || sys.getMoney()==null) {
			return;
		}
		sys.setType(GenerateSN.get(sys.getSn()));
		System.out.println(sys.getSn()+", "+sys.getType());
		
		int one = 0;
		int two = 0;
		
		switch (sys.getType()) {
		case CALL:
			if (oneUsers != null && oneUsers.getId() != null) {
				one = profitService.getHelper().getOneCall(oneUsers.getId(), sys.getMoney());
			}
			if (twoUsers != null && twoUsers.getId() != null) {
				two = profitService.getHelper().getTwoCall(twoUsers.getId(), sys.getMoney());
			}
			break;
		case TRANSFER:
			if (oneUsers != null && oneUsers.getId() != null) {
				one = profitService.getHelper().getOneTransfer(oneUsers.getId(), sys.getMoney()+sys.getFeesMoney());
			}
			if (twoUsers != null && twoUsers.getId() != null) {
				two = profitService.getHelper().getTwoTransfer(twoUsers.getId(), sys.getMoney()+sys.getFeesMoney());
			}
			break;
		case SHOP:
			// TODO
			if (oneUsers != null && oneUsers.getId() != null) {
//				one = profitService.getHelper().getOneCall(oneUsers.getId(), sys.getMoney());
			}
			if (twoUsers != null && twoUsers.getId() != null) {
//				two = profitService.getHelper().getTwoCall(twoUsers.getId(), sys.getMoney());
			}
			break;
		case P2P:
			// TODO
			if (oneUsers != null && oneUsers.getId() != null) {
//				one = profitService.getHelper().getOneCall(oneUsers.getId(), sys.getMoney());
			}
			if (twoUsers != null && twoUsers.getId() != null) {
//				two = profitService.getHelper().getTwoCall(twoUsers.getId(), sys.getMoney());
			}
			break;
		case RECHARGE:
			if (oneUsers != null && oneUsers.getId() != null) {
				one = profitService.getHelper().getOneBalance(oneUsers.getId(), sys.getMoney()+sys.getFeesMoney());
			}
			if (twoUsers != null && twoUsers.getId() != null) {
				two = profitService.getHelper().getTwoBalance(twoUsers.getId(), sys.getMoney()+sys.getFeesMoney());
			}
			break;
		case WITHDRAW:
			if (oneUsers != null && oneUsers.getId() != null) {
				one = profitService.getHelper().getOneWithdraw(oneUsers.getId(), sys.getMoney()+sys.getFeesMoney());
			}
			if (twoUsers != null && twoUsers.getId() != null) {
				two = profitService.getHelper().getTwoWithdraw(twoUsers.getId(), sys.getMoney()+sys.getFeesMoney());
			}
			break;

		default:
			break;
		}
		sys.setOneProfitMoney(one);
		sys.setTwoProfitMoney(two);
	}
	
	/**
	 * 查找邀请人
	 * @return
	 */
	private Users findInviteUsers(Integer usersId) {
		if (usersId == null || usersId <= 0) {
			return null;
		}

		Users usersOne = new Users();
		usersOne.setId(usersId);
		return getDao().selectOne("users.selectInviteUsers", usersOne);
	}

	@Override
	public String getTablename() {
		return "sys_trade";
	}

}
