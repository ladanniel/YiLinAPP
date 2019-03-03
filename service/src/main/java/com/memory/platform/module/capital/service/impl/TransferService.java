package com.memory.platform.module.capital.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.capital.MoneyRecord.Type;
import com.memory.platform.entity.capital.RechargeRecord;
import com.memory.platform.entity.capital.Transfer;
import com.memory.platform.entity.capital.Transfer.Status;
import com.memory.platform.entity.sys.Paymentinterfac;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DateUtil;
import com.memory.platform.module.capital.dao.CapitalAccountDao;
import com.memory.platform.module.capital.dao.MoneyRecordDao;
import com.memory.platform.module.capital.dao.RechargeRecordDao;
import com.memory.platform.module.capital.dao.TransferDao;
import com.memory.platform.module.capital.service.ITransferService;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月4日 下午8:25:18 
* 修 改 人： 
* 日   期： 
* 描   述： 转账 － 业务服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class TransferService implements ITransferService {

	@Autowired
	private TransferDao transferDao;
	@Autowired
	private MoneyRecordDao moneyRecordDao;
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	@Autowired
	private RechargeRecordDao rechargeRecordDao;

	@Override
	public Map<String, Object> getPage(Transfer transfer, int start, int limit) {
		return this.transferDao.getPage(transfer, start, limit);
	}

	@Override
	public void saveTransfer(Transfer transfer) {
		//转账人记录保存
		transfer.setTradeNo("TN" + DateUtil.dateNo());
		transfer.setCreate_time(new Date());
		transfer.setMoney(0 - transfer.getMoney());
		transfer.setStatus(Status.success);
		transferDao.save(transfer);
		
		//收款人记录保存
		Transfer totransfer = new Transfer();
		totransfer.setAccount(transfer.getToAccount());
		totransfer.setToAccount(transfer.getAccount());
		totransfer.setMoney(0 - transfer.getMoney());
		totransfer.setTradeNo(transfer.getTradeNo());
		totransfer.setCreate_time(transfer.getCreate_time());
		totransfer.setRemark(transfer.getRemark());
		totransfer.setSourceAccount("易林帐户-"+transfer.getAccount().getAccount());
		totransfer.setSourcType("余额");
		totransfer.setStatus(Status.success);
		totransfer.setTradeAccount(transfer.getAccount().getAccount());
		totransfer.setTradeName("易林帐户余额");
		totransfer.setTransferType(transfer.getTransferType());
		transferDao.save(totransfer);
		
		//计算转账人资金
		CapitalAccount capitalAccount = capitalAccountDao.getCapitalAccount(transfer.getAccount().getId());
		BigDecimal money = new BigDecimal(totransfer.getMoney());
		BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());
		BigDecimal total = new BigDecimal(capitalAccount.getTotal()); 
		if(money.compareTo(avaiable) == -1 || money.compareTo(avaiable) == 0){//可转账
			capitalAccount.setAvaiable(avaiable.subtract(money).doubleValue());   //计算可用余额
			capitalAccount.setTotal(total.subtract(money).doubleValue());  //计算总资金
		}else{
			throw new BusinessException("余额不足！");
		}
		capitalAccountDao.update(capitalAccount);
		
		//计算收款人资金
		CapitalAccount toCapitalAccount = capitalAccountDao.getCapitalAccount(transfer.getToAccount().getId());
		BigDecimal toavaiable = new BigDecimal(toCapitalAccount.getAvaiable());
		BigDecimal tototal = new BigDecimal(toCapitalAccount.getTotal()); 
		toCapitalAccount.setAvaiable(toavaiable.add(money).doubleValue());   //计算可用余额
		toCapitalAccount.setTotal(tototal.add(money).doubleValue());  //计算总资金
		capitalAccountDao.update(toCapitalAccount);
		
		//记录转账人资金记录
		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(transfer.getAccount());
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(transfer.getMoney());
		moneyRecord.setOperator(transfer.getAccount().getName());
		moneyRecord.setRemark("转账成功：转账金额¥" + transfer.getMoney() +"元，流水号：" + transfer.getTradeNo());
		moneyRecord.setType(Type.transfer);
		moneyRecord.setSourceAccount(transfer.getSourceAccount());
		moneyRecord.setSourceType(transfer.getSourcType());
		moneyRecord.setTradeAccount(transfer.getTradeAccount());
		moneyRecord.setTradeName(transfer.getTradeName());
		moneyRecordDao.save(moneyRecord);
		
		//记录收账人资金记录
		MoneyRecord toMoneyRecord = new MoneyRecord();
		toMoneyRecord.setAccount(transfer.getToAccount());
		toMoneyRecord.setCreate_time(new Date());
		toMoneyRecord.setMoney(0 - transfer.getMoney());
		toMoneyRecord.setOperator(transfer.getAccount().getName());
		toMoneyRecord.setRemark("收款成功：收款金额¥" + totransfer.getMoney() +"元，流水号：" + transfer.getTradeNo());
		toMoneyRecord.setType(Type.income);
		toMoneyRecord.setSourceAccount("易林帐户-"+transfer.getAccount().getAccount());
		toMoneyRecord.setSourceType("余额");
		toMoneyRecord.setTradeAccount(transfer.getAccount().getAccount());
		toMoneyRecord.setTradeName("易林帐户余额");
		moneyRecordDao.save(toMoneyRecord);
	}

	@Override
	public void saveRechargeToTransfer(Transfer transfer) {
		//充值
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setMoney(transfer.getMoney());
		rechargeRecord.setSourceAccount(transfer.getSourceAccount());
		rechargeRecord.setTradeAccount(transfer.getTradeAccount());
		rechargeRecord.setTradeName(transfer.getTradeName());
		rechargeRecord.setSourceType(transfer.getSourcType());  
		rechargeRecord.setCreate_time(new Date());
		rechargeRecord.setAccount(transfer.getAccount());
		rechargeRecord.setBankName("测试银行");
		rechargeRecord.setStatus(com.memory.platform.entity.capital.RechargeRecord.Status.success);
		rechargeRecord.setRemark("测试数据");
		rechargeRecord.setTradeNo("RN" + DateUtil.dateNo());
		this.saveRecharge(rechargeRecord);
		
		//转账
		this.saveTransfer(transfer);
		
	}
	
	/**
	* 功能描述：充值 
	* 输入参数:  @param rechargeRecord
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月18日上午10:19:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void saveRecharge(RechargeRecord rechargeRecord) {
		CapitalAccount capitalAccount = capitalAccountDao.getCapitalAccount(rechargeRecord.getAccount().getId());
		BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());
		BigDecimal money = new BigDecimal(rechargeRecord.getMoney());
		BigDecimal totalRecharge = new BigDecimal(capitalAccount.getTotalrecharge());
		BigDecimal total = new BigDecimal(capitalAccount.getTotal());
		capitalAccount.setAvaiable(avaiable.add(money).doubleValue());    //计算可用余额
		capitalAccount.setTotalrecharge(totalRecharge.add(money).doubleValue());   //计算总充值
		capitalAccount.setTotal(total.add(money).doubleValue());    //计算总资产
		
		//添加资金记录
		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(rechargeRecord.getAccount());
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(rechargeRecord.getMoney());
		moneyRecord.setOperator(rechargeRecord.getAccount().getName());
		moneyRecord.setRemark("在线充值成功，充值金额¥"+rechargeRecord.getMoney()+"元，流水号："+rechargeRecord.getTradeNo());
		moneyRecord.setType(Type.recharge);
		this.rechargeRecordDao.save(rechargeRecord);
		this.capitalAccountDao.update(capitalAccount);
		moneyRecord.setSourceAccount(rechargeRecord.getSourceAccount());
		moneyRecord.setSourceType(rechargeRecord.getSourceType());
		moneyRecord.setTradeAccount(rechargeRecord.getTradeAccount());
		moneyRecord.setTradeName(rechargeRecord.getTradeName());
		this.moneyRecordDao.save(moneyRecord);
	}

	@Override
	public Transfer getTransferById(String id) {
		return this.transferDao.getObjectById(Transfer.class, id);
	}

	@Override
	public List<Transfer> getList(Transfer transfer) {
		return transferDao.getList(transfer);
	}

	@Override
	public List<Transfer> getListByTypeId(String id) {
		return null;
	}
	
	@Override
	public Map<String, Object> getListForMap(String accountId, int start, int size) {
		return transferDao.getListForMap(accountId, start, size);
	}
}
