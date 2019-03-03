package com.memory.platform.module.additional.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.additional.AdditionalExpenses;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class AdditionalExpensesDao extends BaseDao<AdditionalExpenses>{

	public List<Map<String, Object>> getAdditionalExpensesAll() {
		String sqlStr = " select id,name from additional_expenses group by create_time ";
		Map<String,Object> map = new HashMap<String,Object>();
		return this.getListBySQLMap(sqlStr,map);
	}
}
