package com.memory.platform.module.capital.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.capital.CashApplication;
import com.memory.platform.entity.capital.CashApplication.BankStatus;
import com.memory.platform.entity.capital.CashApplication.Status;
import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.capital.MoneyRecord.Type;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DoubleHelper;
import com.memory.platform.module.capital.dao.CapitalAccountDao;
import com.memory.platform.module.capital.dao.CashApplicationDao;
import com.memory.platform.module.capital.dao.MoneyRecordDao;
import com.memory.platform.module.capital.service.ICashApplicationService;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.rocketMQ.broadcasting.BroadcastProducer;
import com.memory.platform.rocketMQ.broadcasting.BroadcastProducer;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午7:13:19 
* 修 改 人： 
* 日   期： 
* 描   述： 提现记录服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class CashApplicationService implements ICashApplicationService {

	@Autowired
	private CashApplicationDao cashApplicationDao;
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	@Autowired
	private MoneyRecordDao moneyRecordDao;
	
	@Autowired
	private IPushService pushService;
	
	@Override
	public Map<String, Object> getPage(CashApplication cashApplication, int start, int limit) {
		return cashApplicationDao.getPage(cashApplication, start, limit);
	}
	/*
	 * 提现申请
	 * */
	@Override
	public void saveCashApplication(CashApplication cashApplication) {
		CapitalAccount capitalAccount = capitalAccountDao.getCapitalAccount(cashApplication.getAccount().getId());
		BigDecimal money = new BigDecimal(cashApplication.getMoney());
		BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());
		BigDecimal frozen = new BigDecimal(capitalAccount.getFrozen());
		if(money.compareTo(avaiable) == -1 || money.compareTo(avaiable) == 0){
			cashApplication.setActualMoney(money.multiply(new BigDecimal(1)).doubleValue());
			capitalAccount.setFrozen(frozen.add(money).doubleValue());//计算冻结资金
			capitalAccount.setAvaiable(avaiable.subtract(money).doubleValue());   //计算可用余额
		}else{
			throw new BusinessException("提现资金大于可提现余额！");
		}
		
		//添加资金记录
		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(cashApplication.getAccount());
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(cashApplication.getMoney());
		
		moneyRecord.setOperator(cashApplication.getAccount().getName());
		moneyRecord.setRemark("申请提现￥" + cashApplication.getMoney() + "元，冻结可用金额￥" + cashApplication.getMoney() +
				"可用余额:"+capitalAccount.getAvaiable() + "【前:" +DoubleHelper.round(avaiable.doubleValue())+"】" +
				"冻结:" + capitalAccount.getFrozen() +"【前:"+DoubleHelper.round(frozen.doubleValue()) +"】" +
				"，流水号：" +cashApplication.getTradeNo());
		moneyRecord.setType(Type.frozen);
		moneyRecord.setSourceAccount("帐户" + "-" + cashApplication.getAccount().getAccount());
		moneyRecord.setSourceType("余额");
		moneyRecord.setTradeAccount(cashApplication.getAccount().getAccount());
		moneyRecord.setTradeName("帐户余额");
		pushService.saveRecordAndSendMessageWithAccountID(cashApplication.getAccount().getId(),
				"【申请提现】", moneyRecord.getRemark(), null,
				"com.memory.platform.module.capital.service.impl.CashApplicationService.saveCashApplication(CashApplication)");
		this.cashApplicationDao.save(cashApplication);
		this.capitalAccountDao.update(capitalAccount);
		this.moneyRecordDao.save(moneyRecord);
	}
	/*
	 * 审核提现
	 * 审核成功 发送消息给MQ  然后MQ进行微信通讯
	 * */
	@Override
	public void updateCashApplication(CashApplication cashApplication) throws Exception   {
	 
		CapitalAccount capitalAccount = capitalAccountDao.getCapitalAccount(cashApplication.getAccount().getId());
		BigDecimal money = new BigDecimal(cashApplication.getMoney());
		BigDecimal frozen = new BigDecimal(capitalAccount.getFrozen());
		BigDecimal total = new BigDecimal(capitalAccount.getTotal());
		BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());
		BigDecimal totalCash = new BigDecimal(capitalAccount.getTotalcash());
		
		//添加资金记录
		MoneyRecord moneyRecord = new MoneyRecord();
		moneyRecord.setAccount(cashApplication.getAccount());
		moneyRecord.setCreate_time(new Date());
		moneyRecord.setMoney(cashApplication.getMoney());
		moneyRecord.setOperator(cashApplication.getVerifytPeopson());
		moneyRecord.setType(Type.cash);
		moneyRecord.setSourceAccount("小镖人帐户"+ "-" +cashApplication.getAccount().getAccount());
		moneyRecord.setSourceType("余额");
		moneyRecord.setTradeAccount(cashApplication.getAccount().getAccount());
		moneyRecord.setTradeName("小镖人帐户余额");
		String title = "【提现成功】";
		//提现成功 － 释放冻结资金
		if(cashApplication.getStatus().equals(Status.success)){
			capitalAccount.setTotalcash(totalCash.add(money).doubleValue());  //计算总提现
			capitalAccount.setTotal(total.subtract(money).doubleValue());   //计算总金额
			capitalAccount.setFrozen(frozen.subtract(money).doubleValue());   //计算冻结资金
			cashApplication.setBank_status(BankStatus.waitProcess);
			moneyRecord.setRemark("提现成功：成功提现¥" + cashApplication.getMoney() + "元,解冻资金¥" + cashApplication.getMoney() + "元， " +
					"可用余额:"+capitalAccount.getAvaiable() + "【前:" +DoubleHelper.round( avaiable.doubleValue())+"】" +
					"冻结:" + capitalAccount.getFrozen() +"【前:"+DoubleHelper.round(avaiable.doubleValue()) +"】" +
			 "流水号："+cashApplication.getTradeNo());
		}
		
		//提现失败 － 还原可用余额
		if(cashApplication.getStatus().equals(Status.failed)){
			title = "【提现失败】";
			moneyRecord.setType(Type.thaw);
			capitalAccount.setFrozen(frozen.subtract(money).doubleValue());   //计算冻结资金
			capitalAccount.setAvaiable(avaiable.add(money).doubleValue());  //计算可用余额
			moneyRecord.setRemark("提现失败：解冻资金¥" + cashApplication.getMoney() + "元,恢复可用余额¥" + cashApplication.getMoney() + "元，"+
					"可用余额:"+capitalAccount.getAvaiable() + "【前:" + DoubleHelper.round(avaiable.doubleValue())+"】" +
					"冻结:" + capitalAccount.getFrozen() +"【前:"+DoubleHelper.round(frozen.doubleValue()) +"】" +
					"流水号："+cashApplication.getTradeNo());
		}
		pushService.saveRecordAndSendMessageWithAccountID(cashApplication.getAccount().getId(),
				title, moneyRecord.getRemark(), null,
				"com.memory.platform.module.capital.service.impl.CashApplicationService.updateCashApplication(CashApplication)");
		
		this.cashApplicationDao.update(cashApplication);
		this.capitalAccountDao.update(capitalAccount);
		this.moneyRecordDao.save(moneyRecord);
		//成功发消息给MQ 用MQ去扣款
		if(cashApplication.getStatus().equals(Status.success) && cashApplication.getBankname().equals("weiXin")) {
			BroadcastProducer rocketMQProucer = (BroadcastProducer) AppUtil.getApplicationContex().getBean("rocketMQProucerWithdraw");
			MessageExt msg = new MessageExt();
			msg.setTopic("withdraw"); //发送消息到MQ里面
			msg.setTags("weiXinWithdraw");
			msg.setBody(cashApplication.getId().getBytes());
			rocketMQProucer.sendMsg(msg);
		}
	}

	@Override
	public CashApplication getCashById(String id) {
		return cashApplicationDao.getObjectById(CashApplication.class, id);
	}

	@Override
	public void updateLock(CashApplication cashApplication) {
		cashApplicationDao.update(cashApplication);
	}

	@Override
	public List<CashApplication> getList(CashApplication cashApplication,String type) {
		return cashApplicationDao.getList(cashApplication,type);
	}

	@Override
	public Map<String, Object> getOsPage(CashApplication cashApplication, int start, int limit) {
		return cashApplicationDao.getOsPage(cashApplication, start, limit);
	}

	@Override
	public Map<String, Object> getOsLogPage(CashApplication cashApplication, int start, int limit) {
		return cashApplicationDao.getOsLogPage(cashApplication, start, limit);
	}
	
	@Override
	public Map<String, Object> getListForMap(String accountId, int start, int size) {
		return cashApplicationDao.getListForMap(accountId,start,size);
	}

	@Override
	public void save(CashApplication cashApplication) {
		 cashApplicationDao.merge(cashApplication);
	}

	@Override
	public void update(CashApplication cashApplication) {
		cashApplicationDao.update(cashApplication);
		
	}
}
