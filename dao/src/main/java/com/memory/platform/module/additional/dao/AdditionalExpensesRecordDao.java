package com.memory.platform.module.additional.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.additional.AdditionalExpensesRecord;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class AdditionalExpensesRecordDao extends BaseDao<AdditionalExpensesRecord> {

	public Map<String, Object> getRecordByRobOrderConfirmId(String robOrderConfirmId) {
		return null;
	}

	public void saveRecord(AdditionalExpensesRecord additionalExpensesRecord) {
		// TODO Auto-generated method stub
		this.save(additionalExpensesRecord);
	}
}
