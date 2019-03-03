package com.memory.platform.module.additional.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.module.additional.dao.AdditionalExpensesDao;
import com.memory.platform.module.additional.service.IAdditionalExpensesService;

@Service
public class AdditionalExpensesService implements IAdditionalExpensesService{
	@Autowired
	private AdditionalExpensesDao additionalExpensesDao;
	
	@Override
	public List<Map<String, Object>> getAdditionalExpensesAll() {
		// TODO Auto-generated method stub
		return additionalExpensesDao.getAdditionalExpensesAll();
	}
}
