package com.memory.platform.module.additional.service;

import java.util.List;
import java.util.Map;

public interface IAdditionalExpensesService {

	/**
	 * 获取所有附加费用类型
	 * */
	List<Map<String, Object>> getAdditionalExpensesAll();

}
