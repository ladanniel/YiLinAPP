package com.memory.platform.module.additional.service;

import java.util.Map;

import com.memory.platform.entity.additional.AdditionalExpensesRecord;

public interface IAdditionalExpensesRecordService {

	Map<String,Object> getRecordByRobOrderConfirmId(String robOrderConfirmId);
	
	/**
	 * 保存
	 * */
	void saveRecord(AdditionalExpensesRecord additionalExpensesRecord);
}
