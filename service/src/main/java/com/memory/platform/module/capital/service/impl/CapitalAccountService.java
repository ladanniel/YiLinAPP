package com.memory.platform.module.capital.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.capital.TradeSequence;
import com.memory.platform.entity.capital.MoneyRecord.Type;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DoubleHelper;
import com.memory.platform.module.capital.dao.CapitalAccountDao;
import com.memory.platform.module.capital.dao.CashApplicationDao;
import com.memory.platform.module.capital.dao.MoneyRecordDao;
import com.memory.platform.module.capital.dao.RechargeRecordDao;
import com.memory.platform.module.capital.dao.TradeSequenceDao;
import com.memory.platform.module.capital.dao.TransferDao;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.system.dao.AccountDao;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午7:07:54 修 改 人： 日 期： 描 述： 资金账户服务 版 本 号： V1.0
 */
@Service
@Transactional
public class CapitalAccountService implements ICapitalAccountService {

	@Autowired
	private CapitalAccountDao capitalAccountDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private TradeSequenceDao tradeSequenceDao;
	@Autowired
	private MoneyRecordDao moneyRecordDao;
	@Autowired
	private TransferDao transferDao;
	@Autowired
	CashApplicationDao cashApplicationDao;
	@Autowired
	private RechargeRecordDao rechargeRecordDao;

	@Override
	public CapitalAccount getCapitalAccount(String userId) {
		return this.capitalAccountDao.getCapitalAccount(userId);
	}

	@Override
	@CacheEvict(value = "menuCache", allEntries = true)
	public void savePayPassword(Account account) {
		// 开启资金帐户
		CapitalAccount capitalAccount = new CapitalAccount();
		capitalAccount.setAccount(account);
		capitalAccount.setAvaiable(new Double(0));
		capitalAccount.setCreate_time(new Date());
		capitalAccount.setFrozen(new Double(0));
		capitalAccount.setTotal(new Double(0));
		capitalAccount.setTotalcash(new Double(0));
		capitalAccount.setTotalrecharge(new Double(0));
		capitalAccountDao.save(capitalAccount);

		// 插入支付序列数据
		TradeSequence tradeSequence = new TradeSequence();
		tradeSequence.setAccount(account);
		tradeSequence.setCreate_time(new Date());
		tradeSequence.setHasCash(false);
		tradeSequence.setHasRecharge(false);
		tradeSequence.setHasTransfer(true);
		tradeSequence.setSequenceNo(1);
		tradeSequence.setSourceId(capitalAccount.getId());
		tradeSequence.setSourceAccount("易林帐户" + "-" + account.getAccount());
		tradeSequence.setSourcType("余额");
		tradeSequenceDao.save(tradeSequence);

		// 编辑支付密码
		accountDao.update(account);

	}

	@Override
	public Map<String, Object> getPage(CapitalAccount capitalAccount, int start, int limit) {
		return this.capitalAccountDao.getPage(capitalAccount, start, limit);
	}

	@Override
	public CapitalAccount getCapitalAccountById(String id) {
		return capitalAccountDao.getObjectById(CapitalAccount.class, id);
	}

	@Override
	public List<CapitalAccount> getList(CapitalAccount capitalAccount) {
		return capitalAccountDao.getList(capitalAccount);
	}

	@Override
	public void saveTransferTool(CapitalAccount capitalAccount, CapitalAccount toCapitalAccount, BigDecimal money,
			Type type, String remark, String toRemark) {
		BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());
		if (money.compareTo(avaiable) == 1) {
			throw new BusinessException("您的可用余额不足" + String.format("%.2f", money) + "元，请充值后再操作。");
		}
		capitalAccount.setAvaiable(DoubleHelper.round(avaiable.subtract(money).doubleValue()));
		capitalAccount.setTotal(DoubleHelper.round(new BigDecimal(capitalAccount.getTotal()).subtract(money).doubleValue()));
		capitalAccountDao.merge(capitalAccount);

		toCapitalAccount.setAvaiable(DoubleHelper.round(new BigDecimal(toCapitalAccount.getAvaiable()).add(money).doubleValue()));
		toCapitalAccount.setTotal(DoubleHelper.round(new BigDecimal(toCapitalAccount.getTotal()).add(money).doubleValue()));
		capitalAccountDao.merge(toCapitalAccount);

		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(capitalAccount.getAccount());
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(DoubleHelper.round(-money.doubleValue()));
		moneyRecord.setOperator(capitalAccount.getAccount().getName());
		moneyRecord.setRemark(remark);
		moneyRecord.setType(type);
		moneyRecord.setSourceAccount("易林帐户" + "-" + capitalAccount.getAccount().getAccount());
		moneyRecord.setSourceType("余额");
		moneyRecord.setTradeAccount(capitalAccount.getAccount().getAccount());
		moneyRecord.setTradeName("易林帐户余额");
		moneyRecordDao.save(moneyRecord);

		MoneyRecord moneyRecordYl = new MoneyRecord();
		moneyRecordYl.setAccount(toCapitalAccount.getAccount());
		moneyRecordYl.setCreate_time(new Date());
		moneyRecordYl.setMoney(DoubleHelper.round(money.doubleValue()));
		moneyRecordYl.setOperator(capitalAccount.getAccount().getName());
		moneyRecordYl.setRemark(toRemark);
		moneyRecordYl.setType(MoneyRecord.Type.income);
		moneyRecordYl.setSourceAccount("易林帐户" + "-" + capitalAccount.getAccount().getAccount());
		moneyRecordYl.setSourceType("余额");
		moneyRecordYl.setTradeAccount(capitalAccount.getAccount().getAccount());
		moneyRecordYl.setTradeName("易林帐户余额");
		moneyRecordDao.save(moneyRecordYl);
	}

	/**
	 * 仲裁赔偿
	 */
	@Override
	public void saveArbitrationCompensateFor(CapitalAccount capitalAccount, CapitalAccount toCapitalAccount,
			BigDecimal money, Type type, String remark, String toRemark) {
		BigDecimal frozen = new BigDecimal(capitalAccount.getFrozen());
		if ((frozen.compareTo(money) == -1)) {
			throw new BusinessException("您的冻结资金" + String.format("%.2f", money) + "异常，请联系系统管理员。");
		}
		capitalAccount.setFrozen(DoubleHelper.round(new BigDecimal(capitalAccount.getFrozen()).subtract(money).doubleValue()));
		capitalAccount.setTotal(DoubleHelper.round(new BigDecimal(capitalAccount.getTotal()).subtract(money).doubleValue()));
		capitalAccountDao.merge(capitalAccount);

		toCapitalAccount.setAvaiable(DoubleHelper.round(new BigDecimal(toCapitalAccount.getAvaiable()).add(money).doubleValue()));
		toCapitalAccount.setTotal(DoubleHelper.round(new BigDecimal(toCapitalAccount.getTotal()).add(money).doubleValue()));
		capitalAccountDao.merge(toCapitalAccount);

		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(capitalAccount.getAccount());
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(DoubleHelper.round(-money.doubleValue()));
		moneyRecord.setOperator(capitalAccount.getAccount().getName());
		moneyRecord.setRemark(remark);
		moneyRecord.setType(type);
		moneyRecord.setSourceAccount("易林帐户" + "-" + capitalAccount.getAccount().getAccount());
		moneyRecord.setSourceType("余额");
		moneyRecord.setTradeAccount(capitalAccount.getAccount().getAccount());
		moneyRecord.setTradeName("易林帐户余额");
		moneyRecordDao.save(moneyRecord);

		MoneyRecord moneyRecordYl = new MoneyRecord();
		moneyRecordYl.setAccount(toCapitalAccount.getAccount());
		moneyRecordYl.setCreate_time(new Date());
		moneyRecordYl.setMoney(DoubleHelper.round(money.doubleValue()));
		moneyRecordYl.setOperator(capitalAccount.getAccount().getName());
		moneyRecordYl.setRemark(toRemark);
		moneyRecordYl.setType(MoneyRecord.Type.income);
		moneyRecordYl.setSourceAccount("易林帐户" + "-" + capitalAccount.getAccount().getAccount());
		moneyRecordYl.setSourceType("余额");
		moneyRecordYl.setTradeAccount(capitalAccount.getAccount().getAccount());
		moneyRecordYl.setTradeName("易林帐户余额");
		moneyRecordDao.save(moneyRecordYl);
	}

	@Override
	public void savePayment(CapitalAccount capitalAccount, CapitalAccount toCapitalAccount, BigDecimal money, Type type,
			String remark, String toRemark) {
		BigDecimal frozen = new BigDecimal(capitalAccount.getFrozen());
		if (frozen.compareTo(money) == -1) {
			throw new BusinessException("您的冻结资金异常" + String.format("%.2f", money) + "元，请联系管理员。");
		}
		capitalAccount.setFrozen(DoubleHelper.round(frozen.subtract(money).doubleValue()));
		capitalAccount.setTotal(DoubleHelper.round(new BigDecimal(capitalAccount.getTotal()).subtract(money).doubleValue()));
		capitalAccountDao.merge(capitalAccount);

		toCapitalAccount.setAvaiable(DoubleHelper.round(new BigDecimal(toCapitalAccount.getAvaiable()).add(money).doubleValue()));
		toCapitalAccount.setTotal(DoubleHelper.round(new BigDecimal(toCapitalAccount.getTotal()).add(money).doubleValue()));
		capitalAccountDao.merge(toCapitalAccount);

		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(capitalAccount.getAccount());
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(DoubleHelper.round(-money.doubleValue()));
		moneyRecord.setOperator(capitalAccount.getAccount().getName());
		moneyRecord.setRemark(remark);
		moneyRecord.setType(type);
		moneyRecord.setSourceAccount("易林帐户" + "-" + capitalAccount.getAccount().getAccount());
		moneyRecord.setSourceType("余额");
		moneyRecord.setTradeAccount(capitalAccount.getAccount().getAccount());
		moneyRecord.setTradeName("易林帐户余额");
		moneyRecordDao.save(moneyRecord);

		MoneyRecord moneyRecordYl = new MoneyRecord();
		moneyRecordYl.setAccount(toCapitalAccount.getAccount());
		moneyRecordYl.setCreate_time(new Date());
		moneyRecordYl.setMoney(DoubleHelper.round(money.doubleValue()));
		moneyRecordYl.setOperator(capitalAccount.getAccount().getName());
		moneyRecordYl.setRemark(toRemark);
		moneyRecordYl.setType(MoneyRecord.Type.income);
		moneyRecordYl.setSourceAccount("易林帐户" + "-" + capitalAccount.getAccount().getAccount());
		moneyRecordYl.setSourceType("余额");
		moneyRecordYl.setTradeAccount(capitalAccount.getAccount().getAccount());
		moneyRecordYl.setTradeName("易林帐户余额");
		moneyRecordDao.save(moneyRecordYl);
	}

	@Override
	public void saveFreezeTool(Account account, BigDecimal money, String remark, String operator) {
		CapitalAccount capitalAccount = this.getCapitalAccount(account.getId());
		if (null == capitalAccount) {
			throw new BusinessException("未开通资金账户。");
		}
		BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());

		BigDecimal forzen = new BigDecimal(capitalAccount.getFrozen());
		if (money.compareTo(new BigDecimal(0)) == -1) { // 补足运费<0 押金大于运费
			if (forzen.add(money).compareTo(new BigDecimal(0)) < 0) {
				throw new BusinessException("您的冻结金额" + String.format("%.2f", forzen) + "小于可用"
						+ String.format("%.2f", money) + "元，请与管理员联系。");
			}
		} else if (money.compareTo(avaiable) == 1) {
			throw new BusinessException("您的可用余额不足" + String.format("%.2f", money) + "元，请充值后再操作。");
		}
		capitalAccount.setAvaiable(DoubleHelper.round(avaiable.subtract(money).doubleValue()));
		capitalAccount.setFrozen(DoubleHelper.round(new BigDecimal(capitalAccount.getFrozen()).add(money).doubleValue()));

		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(account);
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(DoubleHelper.round(-money.doubleValue()));
		moneyRecord.setOperator(operator);
		moneyRecord.setRemark(remark);
		moneyRecord.setType(Type.frozen);
		moneyRecord.setSourceAccount("易林帐户" + "-" + account.getAccount());
		moneyRecord.setSourceType("余额");
		moneyRecord.setTradeAccount(account.getAccount());
		moneyRecord.setTradeName("易林帐户余额");
		// Map<String,String> ret=null;
		// ret.clear();
		moneyRecordDao.save(moneyRecord);
		capitalAccountDao.update(capitalAccount);
	}

	@Override
	public void updateFreezeTool(Account account, BigDecimal money, String remark, String operator) {
		CapitalAccount capitalAccount = this.getCapitalAccount(account.getId());
		if (null == capitalAccount) {
			throw new BusinessException("未开通资金账户。");
		}

		BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());
		BigDecimal frozen = new BigDecimal(capitalAccount.getFrozen());

		double surplusForozen = frozen.subtract(money).doubleValue();
		if (surplusForozen < 0) {
			throw new BusinessException("数据异常，解冻资金不足，无法解冻，请联系管理员。");
		}
		capitalAccount.setAvaiable(DoubleHelper.round(avaiable.add(money).doubleValue()));
		capitalAccount.setFrozen(DoubleHelper.round(surplusForozen));

		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(account);
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(DoubleHelper.round(money.doubleValue()));
		moneyRecord.setOperator(operator);
		moneyRecord.setRemark(remark);
		moneyRecord.setType(Type.thaw);
		moneyRecord.setSourceAccount("易林帐户" + "-" + account.getAccount());
		moneyRecord.setSourceType("余额");
		moneyRecord.setTradeAccount(account.getAccount());
		moneyRecord.setTradeName("易林帐户余额");

		moneyRecordDao.save(moneyRecord);
		capitalAccountDao.update(capitalAccount);
	}

	@Override
	public void updateArbitrationCompensateFor(CapitalAccount capitalAccount, BigDecimal money, String remark,
			String operator) {
		if (null == capitalAccount) {
			throw new BusinessException("未开通资金账户。");
		}
		Account account = capitalAccount.getAccount();
		BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());
		BigDecimal frozen = new BigDecimal(capitalAccount.getFrozen());

		// DecimalFormat df = new java.text.DecimalFormat("#.00");
		// String fmoney = df.format(money);
		// money = new BigDecimal(fmoney);

		if (money.compareTo(frozen) == 1) {
			throw new BusinessException("数据异常，解冻资金不足，无法解冻，请联系管理员。");
		}
		capitalAccount.setAvaiable(DoubleHelper.round(avaiable.add(money).doubleValue()));
		capitalAccount.setFrozen(DoubleHelper.round(frozen.subtract(money).doubleValue()));

		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(account);
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(DoubleHelper.round(money.doubleValue()));
		moneyRecord.setOperator(operator);
		moneyRecord.setRemark(remark);
		moneyRecord.setType(Type.thaw);
		moneyRecord.setSourceAccount("易林帐户" + "-" + account.getAccount());
		moneyRecord.setSourceType("余额");
		moneyRecord.setTradeAccount(account.getAccount());
		moneyRecord.setTradeName("易林帐户余额");

		moneyRecordDao.save(moneyRecord);
		capitalAccountDao.update(capitalAccount);
	}

	@Override
	public Map<String, Object> getSystemCapitalInfo() {
		return capitalAccountDao.getSystemCapitalInfo();
	}

	@Override
	public Map<String, Object> getTotalCapital() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalCapital", getSystemCapitalInfo());
		map.put("totalTransfer", transferDao.getTotalTransfer());
		map.put("cashWaitProcess", cashApplicationDao.getToatlWaitProcess());
		map.put("cashSuccess", cashApplicationDao.getTotalSuccess());
		map.put("cashFail", cashApplicationDao.getTotalFail());
		map.put("monthCash", cashApplicationDao.getMonthTotalCash(null));
		map.put("monthTransfer", transferDao.getMonthTotalTransfer());
		map.put("monthRecharge", rechargeRecordDao.getMonthTotalRecharge(null));
		return map;
	}

	@Override
	public Map<String, Object> getTotalCapital(Account account) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalCapital", getCapitalAccount(account.getId()));
		map.put("totalTransfer", transferDao.getTotalTransfer(account));
		map.put("cashWaitProcess", cashApplicationDao.getTotalCashWaitProcess(account));
		map.put("cashSuccess", cashApplicationDao.getTotalCashSuccess(account));
		map.put("cashFail", cashApplicationDao.getTotalCashFail(account));
		map.put("monthCash", cashApplicationDao.getMonthTotalCash(account));
		map.put("monthTransfer", transferDao.getMonthTotalTransfer(account));
		map.put("monthRecharge", rechargeRecordDao.getMonthTotalRecharge(account));
		return map;
	}

	@Override
	public Map<String, Object> getCapitalForMap(String userId) {
		// TODO Auto-generated method stub
		return this.capitalAccountDao.getCapitalForMap(userId);
	}
	//
	// public void saveCapitalAccount(String capitalAccountID, BigDecimal
	// addAvaiable ,BigDecimal addFrozen,BigDecimal addTotal){
	// String strSql = String.format("update mem_capital_account set
	// avaiable=avaiable+:avaiable , frozen=frozen+:frozen ,total=total+:total
	// where id=:id ");
	// //this.capitalAccountDao.
	// }
}
