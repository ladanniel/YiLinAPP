package com.memory.platform.module.additional.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.additional.AdditionalExpensesRecord;
import com.memory.platform.module.additional.dao.AdditionalExpensesRecordDao;
import com.memory.platform.module.additional.service.IAdditionalExpensesRecordService;

@Service
public class AdditionalExpensesRecordService implements IAdditionalExpensesRecordService{
	@Autowired
	private AdditionalExpensesRecordDao additionalExpensesRecordDao;

	@Override
	public Map<String, Object> getRecordByRobOrderConfirmId(String robOrderConfirmId) {
		// TODO Auto-generated method stub
		return additionalExpensesRecordDao.getRecordByRobOrderConfirmId(robOrderConfirmId);
	}

	@Override
	public void saveRecord(AdditionalExpensesRecord additionalExpensesRecord) {
		// TODO Auto-generated method stub
		additionalExpensesRecordDao.saveRecord(additionalExpensesRecord);
	}
}
