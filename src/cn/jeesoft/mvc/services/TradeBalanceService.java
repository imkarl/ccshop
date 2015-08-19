package cn.jeesoft.mvc.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import cn.jeesoft.core.exception.DbException;
import cn.jeesoft.core.utils.StringUtils;
import cn.jeesoft.mvc.bean.Balance;
import cn.jeesoft.mvc.bean.BalanceChange;
import cn.jeesoft.mvc.bean.SysTrade;
import cn.jeesoft.mvc.helper.GenerateSN;
import cn.jeesoft.mvc.model.TradeState;
import cn.jeesoft.mvc.model.TradeType;

@Service("tradeBalanceService")
public class TradeBalanceService extends BaseService<BalanceChange> {

	/**
	 * 余额变动完成
	 * @param sn
	 * @return
	 */
	public int finish(String sn) {
		BalanceChange change = new BalanceChange();
		change.setSn(sn);
		return getDao().update(getTablename()+".finish", change);
	}

	/**
	 * 扣款
	 * @param change
	 * @return
	 */
	public int deduct(BalanceChange change) {
		if (change.getMoney() == 0) {
			return 1;
		}
		
		SysTrade sys = new SysTrade();
		sys.setSn(change.getSn());
		sys.setFromId(change.getFromId());
		sys.setToId(0);
		sys.setMoney(change.getMoney());
		sys.setName("扣款"+((double)change.getMoney()/100)+"元");
		sys.setCreateTime(change.getCreateTime());
		sys.setEndTime(change.getCreateTime());
//		sys.setRemark(remark);
		sys.setState(TradeState.SUCCESS);
		sys.setType(TradeType.WITHDRAW);
		sys.setIsInstant(true);

		// 创建系统订单
		int insertSys = getDao().insert("sys_trade.insert", sys);
		if (insertSys <= 0) {
			throw new DbException("插入记录失败");
		}
		
		// 创建收益订单
		change.setIsArrival(true);
		int insertChange = insert(change);
		if (insertChange <= 0) {
			throw new DbException("插入记录失败");
		}
		
		// 余额变动（入账）
    	int changeUpdate = update(change);
		if (changeUpdate <= 0) {
			throw new DbException("插入记录失败");
		}
		return 1;
	}
	/**
	 * 收益
	 * @return
	 */
	public int profit(int usersId, Integer money, String linkSn) {
		if (money == null || money == 0) {
			return 1;
		}
		
		BalanceChange change = new BalanceChange();
		change.setFromId(usersId);
		change.setCreateTime(new Date());
		change.setSn(GenerateSN.create(TradeType.PROFIT));
		change.setMoney(money);
		change.setIsAdd(true);
		change.setLinkSn(linkSn);
		change.setIsArrival(true);
		
		SysTrade sys = new SysTrade();
		sys.setSn(change.getSn());
		sys.setFromId(change.getFromId());
		sys.setToId(0);
		sys.setMoney(change.getMoney());
		sys.setName("收益"+((double)change.getMoney()/100)+"元");
		sys.setCreateTime(change.getCreateTime());
		sys.setEndTime(change.getCreateTime());
//		sys.setRemark(remark);
		sys.setState(TradeState.SUCCESS);
		sys.setType(TradeType.PROFIT);
		sys.setIsInstant(true);
		
		// 创建系统订单
		int insertSys = getDao().insert("sys_trade.insert", sys);
		if (insertSys <= 0) {
			throw new DbException("插入记录失败");
		}
		
		// 创建收益订单
		change.setIsArrival(true);
		int insertChange = insert(change);
		if (insertChange <= 0) {
			throw new DbException("插入记录失败");
		}
		
		// 余额变动（入账）
    	int changeUpdate = update(change);
		if (changeUpdate <= 0) {
			throw new DbException("插入记录失败");
		}
		
		return 1;
	}
	

	/**
	 * 转账
	 * @param fromId 付款方
	 * @param toId 收款方
	 * @param money 交易金额
	 * @param fees 手续费
	 * @param linkSn 关联订单
	 * @return
	 */
	public int transfer(int fromId, int toId, int money, int fees, String linkSn) {
		if (fromId <= 0 || toId <= 0 || money <= 0 || StringUtils.isEmpty(linkSn)) {
			throw new DbException("参数不正确");
		}
		
		Date createTime = new Date();
		
		/*
		 * 扣款
		 */
		SysTrade sys = new SysTrade();
		sys.setSn(GenerateSN.create(TradeType.WITHDRAW));
		sys.setFromId(fromId);
		sys.setToId(0);
		sys.setMoney(money+fees);
		sys.setName("扣款"+((money+fees)*0.01)+"元");
		sys.setCreateTime(createTime);
		sys.setEndTime(createTime);
		sys.setState(TradeState.SUCCESS);
		sys.setType(TradeType.WITHDRAW);
		sys.setIsInstant(true);

		// 创建系统订单
		int insertSys = getDao().insert("sys_trade.insert", sys);
		if (insertSys <= 0) {
			throw new DbException("插入记录失败");
		}
		
		// 创建扣款订单
		BalanceChange change = new BalanceChange();
		change.setCreateTime(createTime);
		change.setFromId(fromId);
		change.setLinkSn(linkSn);
		change.setMoney(money+fees);
		change.setSn(sys.getSn());
		change.setIsAdd(false);
		change.setIsArrival(true);
		int insertChange = insert(change);
		if (insertChange <= 0) {
			throw new DbException("插入记录失败");
		}
		
		// 余额变动（扣款）
		change.setLinkSn(null);
    	int changeUpdate = update(change);
		if (changeUpdate <= 0) {
			throw new DbException("插入记录失败");
		}
		
		
		/*
		 * 入账
		 */
		sys.setMoney(money);
		sys.setFromId(toId);
		sys.setToId(0);
		sys.setSn(GenerateSN.create(TradeType.RECHARGE));
		sys.setName("入账"+(money*0.01)+"元");
		sys.setState(TradeState.SUCCESS);
		sys.setType(TradeType.RECHARGE);

		// 创建系统订单
		insertSys = getDao().insert("sys_trade.insert", sys);
		if (insertSys <= 0) {
			throw new DbException("插入记录失败");
		}
		
		// 创建扣款订单
		change.setMoney(money);
		change.setFromId(toId);
		change.setSn(sys.getSn());
		change.setLinkSn(linkSn);
		change.setIsAdd(true);
		insertChange = insert(change);
		if (insertChange <= 0) {
			throw new DbException("插入记录失败");
		}
		
		// 余额变动（扣款）
		change.setLinkSn(null);
    	changeUpdate = update(change);
		if (changeUpdate <= 0) {
			throw new DbException("插入记录失败");
		}
		return 1;
	}

	/**
	 * 锁定余额（从可用变为不可用）
	 * @param usersId
	 * @param money
	 * @return
	 */
	public int lockBalance(int usersId, int money) {
		if (usersId <= 0 || money <= 0) {
			return -1;
		}
		
		BalanceChange balance = new BalanceChange();
		balance.setFromId(usersId);
		balance.setMoney(money);
		return getDao().update(getTablename()+".lock", balance);
	}
	
	/**
	 * 释放余额（从不可用变为可用）
	 * @param usersId
	 * @param money
	 * @return
	 */
	public int releaseBalance(int usersId, int money) {
		if (usersId <= 0 || money <= 0) {
			return -1;
		}
		
		BalanceChange balance = new BalanceChange();
		balance.setFromId(usersId);
		balance.setMoney(money);
		return getDao().update(getTablename()+".release", balance);
	}
	
	/**
	 * 查询余额
	 * @param usersId
	 * @return
	 */
	public Balance queryBalance(Integer usersId) {
		if (usersId == null || usersId <= 0) {
			return null;
		}
		
		Balance balance = new Balance();
		balance.setUsersId(usersId);
		balance = getDao().selectOne(getTablename()+".queryOne", balance);
		return balance;
	}
	/**
	 * 查询提现金额
	 * @param usersId
	 * @return 提现金额
	 */
	public Integer queryWithdraw(Integer usersId) {
		if (usersId == null || usersId <= 0) {
			return null;
		}
		
		Balance balance = new Balance();
		balance.setUsersId(usersId);
		balance = getDao().selectOne(getTablename()+".queryWithdraw", balance);
		if (balance != null) {
			return balance.getMoney();
		}
		return 0;
	}
	
	
	@Override
	public String getTablename() {
		return "trade_balance";
	}

}
