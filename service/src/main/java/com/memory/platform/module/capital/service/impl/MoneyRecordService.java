package com.memory.platform.module.capital.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.module.capital.dao.MoneyRecordDao;
import com.memory.platform.module.capital.service.IMoneyRecordService;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午7:16:52 
* 修 改 人： 
* 日   期： 
* 描   述： 资金流水服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class MoneyRecordService implements IMoneyRecordService {

	@Autowired
	private MoneyRecordDao moneyRecordDao;

	@Override
	public Map<String, Object> getPage(MoneyRecord moneyRecord, int start, int limit) {
		return moneyRecordDao.getPage(moneyRecord, start, limit);
	}

	@Override
	public MoneyRecord getMoneyRecordById(String id) {
		return moneyRecordDao.getObjectById(MoneyRecord.class, id);
	}

	@Override
	public List<MoneyRecord> getList(MoneyRecord moneyRecord) {
		return moneyRecordDao.getList(moneyRecord);
	}

	@Override
	public List<Map<String, Object>> getMoneyRecordInfo(String accountId) {
		return moneyRecordDao.getMoneyRecordInfo(accountId);
	}

	@Override
	public List<Map<String, Object>> getMoneyRecordInfo(String accountId, String startTime, String endTime) {
		return moneyRecordDao.getMoneyRecordInfo(accountId,startTime,endTime);
	}

	@Override
	public List<Map<String, Object>> getMoneyRecordInfo() {
		return moneyRecordDao.getMoneyRecordInfo();
	}

	@Override
	public Map<String,Object> getAllMoneyRecord(List<String> dates, String accountId, String dateType, String type) {
		return moneyRecordDao.getAllMoneyRecord(dates, accountId, dateType, type);
	}
	
	@Override
	public Map<String, Object> getListForMap(String accountId, String type, int start, int size,Date startTime,Date endTime) {
		return moneyRecordDao.getListForMap(accountId, type, start, size,startTime,endTime);
	}

	@Override
	public Map<String, Object> getCapitalRecordPage(MoneyRecord moneyRecord, int start, int limit) {
		// TODO Auto-generated method stub
		return moneyRecordDao.getCapitalRecordPage(moneyRecord, start, limit);
	}
}
