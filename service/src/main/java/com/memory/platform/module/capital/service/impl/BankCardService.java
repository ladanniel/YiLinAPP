package com.memory.platform.module.capital.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONObject;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.capital.BankCard.BankType;
import com.memory.platform.entity.sys.Bank;
import com.memory.platform.entity.capital.TradeSequence;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.exception.SystemException;
import com.memory.platform.global.AES;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.capital.dao.BankCardDao;
import com.memory.platform.module.capital.dao.TradeSequenceDao;
import com.memory.platform.module.capital.service.IBankCardService;
import com.memory.platform.module.capital.util.BankCardUtil;
import com.memory.platform.module.system.dao.BankDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午7:05:25 
* 修 改 人： 
* 日   期： 
* 描   述： 银行卡服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class BankCardService implements IBankCardService {

	@Autowired
	private BankCardDao bankCardDao;
	@Autowired
	private TradeSequenceDao tradeSequenceDao;
	@Autowired
	private BankDao bankDao;

	@Override
	public Map<String, Object> saveBankCard(BankCard bankCard) {
		Map<String, Object> map = new HashMap<String,Object>();
		if(StringUtil.isEmpty(bankCard.getAccount().getIdcard_id()) && StringUtil.isEmpty(bankCard.getAccount().getCompany().getIdcard_id())){
			map.put("success",false);
			map.put("msg", "先进行实名认证再绑定银行卡！");
			return map;
		}
		String number = bankCard.getBankCard().replaceAll(" ", "");
		String bankCardString = null;
		
		if(number == null || number.equals("")){
			map.put("success",false);
			map.put("msg", "银行卡号不能为空。");
			return map;
		}
		
		BankCard b = null;
		try {
			b = getBankCardByBankCard(AES.Encrypt(number));
		} catch (Exception e1) {
			throw new DataBaseException("银行卡加密失败。");
		}
		if(b != null && b.getBandStatus().equals(BankCard.BandStatus.success)){
			map.put("success",false);
			map.put("msg", "银行卡已被绑定，请更换新银行卡。");
			return map;
		}else if(b != null && b.getBandStatus().equals(BankCard.BandStatus.defalut)){
			bankCardDao.delete(b);
		}
		
		try {
			bankCardString = BankCardUtil.getBankJsonFromAliParamString("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo="+number+"&cardBinCheck=true");
		} catch (IOException e) {
			map.put("success",false);
			map.put("msg", "无法认证银行卡号,认证接口连接错误。");
			return map;
		}
		JSONObject bankJson = new JSONObject(bankCardString);
		String validated = bankJson.getString("validated");
		if(validated.equals("false")){
			map.put("success",false);
			map.put("msg", "已存在该绑定银行卡,或银行卡错误。");
			return map;
		}
		String bankName = bankJson.getString("bank");
		String cardType = bankJson.getString("cardType");
		bankCard.setBankName(bankName);
		Bank bank = bankDao.getBankByShortName(bankName);
		if(null != bank){
			bankCard.setImage(bank.getImage());
			bankCard.setCnName(bank.getCnName());
			bankCard.setMarkImage(bank.getMarkImage());
			bankCard.setOpenBank(bank.getCnName());
		}else{
			bankCard.setCnName(bankName);
			bankCard.setOpenBank(bankName);
		}
		if(cardType.equals("DC")){
			bankCard.setBankType(BankType.debitCard);;
		}
		if(cardType.equals("CC")){
			bankCard.setBankType(BankType.creditCard);
		}
		bankCard.setBankName(bankName);
		bankCard.setBankCard(number);
		this.bankCardDao.save(bankCard);
		map.put("success",true);
		map.put("msg", "成功绑定银行卡信息。");
		return map;
	}
	
	@Override
	public Map<String, Object> verifyApp(String number) {
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> returnMap = new HashMap<String,Object>();
		number = number.replaceAll(" ", "");
		String bankCardString = null;
		if(number == null || number.equals("")){
			returnMap.put("success", false);
			returnMap.put("msg", "银行卡号不能为空");
			return returnMap;
		}
		try {
			bankCardString = BankCardUtil.getBankJsonFromAliParamString("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo="+number+"&cardBinCheck=true");
		} catch (IOException e) {
			returnMap.put("success", false);
			returnMap.put("msg", "无法认证银行卡号,认证接口连接错误" + e.getMessage());
			return returnMap;
		}
		JSONObject bankJson = new JSONObject(bankCardString);
		String validated = bankJson.getString("validated");
		if(validated.equals("false")){
			returnMap.put("success", false);
			returnMap.put("msg", "请输入正确的银行卡号");
			return returnMap;
		}
		String bankName = bankJson.getString("bank");
		String cardType = bankJson.getString("cardType");
		map.put("bankName", bankName);
		if(cardType.equals("DC")){
			map.put("cardType", "储蓄卡");
		}
		if(cardType.equals("CC")){
			map.put("cardType", "信用卡");
		}
		map.put("bankCard", number);
		returnMap.put("success", true);
		returnMap.put("msg", "成功获取银行卡信息。");
		returnMap.put("data", map);
		return returnMap;
	}



	@Override
	public void removeBankCard(BankCard card) {
		/*TradeSequence tradeSequence = tradeSequenceDao.getTradeSequenceBySourceId(id);
		List<TradeSequence> list = tradeSequenceDao.getListByNo(tradeSequence);
		tradeSequenceDao.delete(tradeSequence);
		for (TradeSequence tradeSequence2 : list) {
			tradeSequence2.setSequenceNo(tradeSequence2.getSequenceNo() - 1);
			tradeSequenceDao.update(tradeSequence2);
		}
		this.bankCardDao.delete(bankCardDao.getObjectById(BankCard.class, id));*/
		this.bankCardDao.delete(card);
	}

	@Override
	public List<BankCard> getAll(String userId) {
		return this.bankCardDao.getAll(userId);
	}

	@Override
	public BankCard getBankCard(String id) {
		return this.bankCardDao.getObjectById(BankCard.class, id);
	}

	@Override
	public BankCard getBankCardByBankCard(String bankCard) {
		return bankCardDao.getBankCardByBankCard(bankCard);
	}

	@Override
	public BankCard getBankCardByOrderId(String orderId) {
		return bankCardDao.getBankCardByOrderId(orderId);
	}

	@Override
	public void updateBankCard(BankCard bankCard) {
		//插入支付序列数据
		int size = tradeSequenceDao.getTotal(bankCard.getAccount());
		TradeSequence tradeSequence = new TradeSequence();
		tradeSequence.setAccount(bankCard.getAccount());
		tradeSequence.setCreate_time(new Date());
		if(bankCard.getBankType().equals(BankType.creditCard)){
			tradeSequence.setSourcType("信用卡");
			tradeSequence.setHasRecharge(false);
			tradeSequence.setHasTransfer(false);
			tradeSequence.setHasCash(false);
		}else{
			tradeSequence.setSourcType("储蓄卡");
			tradeSequence.setHasRecharge(true);
			tradeSequence.setHasTransfer(false);
			tradeSequence.setHasCash(true);
		}
		tradeSequence.setSourceId(bankCard.getId());
		tradeSequence.setSequenceNo(size + 1);
		tradeSequence.setSourceAccount(bankCard.getCnName() + "-" + bankCard.getBankCard());
		tradeSequenceDao.save(tradeSequence);
		bankCardDao.update(bankCard);
	}
	
	@Override
	public List<Map<String, Object>> getAllForMap(String userId) {
		return bankCardDao.getAllForMap(userId);
	}
	
	@Override
	public BankCard verify(String number) {
		BankCard bankCard = new BankCard();
		number = number.replaceAll(" ", "");
		String bankCardString = null;
		if(number == null || number.equals("")){
			throw new BusinessException("银行卡号不能为空");
		}
		try {
			bankCardString = BankCardUtil.getBankJsonFromAliParamString("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo="+number+"&cardBinCheck=true");
		} catch (IOException e) {
			throw new SystemException("无法认证银行卡号,认证接口连接错误" + e.getMessage());
		}
		JSONObject bankJson = new JSONObject(bankCardString);
		String validated = bankJson.getString("validated");
		if(validated.equals("false")){
			throw new BusinessException("请输入正确的银行卡号");
		}
		String bankName = bankJson.getString("bank");
		String cardType = bankJson.getString("cardType");
		bankCard.setBankName(bankName);
		Bank bank = bankDao.getBankByShortName(bankName);
		if(null != bank){
			bankCard.setImage(bank.getImage());
			bankCard.setCnName(bank.getCnName());
		}else{
			bankCard.setCnName(bankName);
		}
		if(cardType.equals("DC")){
			bankCard.setBankType(BankType.debitCard);;
		}
		if(cardType.equals("CC")){
			bankCard.setBankType(BankType.creditCard);
		}
		bankCard.setBankName(bankName);
		bankCard.setBankCard(number);
		return bankCard;
	}
	
	@Override
	public Map<String,Object> getMyBindBankCardPage(String userId,int start,int limit) {
		return this.bankCardDao.getMyBindBankCardPage(userId,start,limit);
	}
}
