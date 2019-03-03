package com.memory.platform.module.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.sys.LgisticsCompanies;
import com.memory.platform.module.system.dao.LgisticsCompaniesDao;
import com.memory.platform.module.system.service.ILgisticsCompaniesService;

@Service
@Transactional
public class LgisticsCompaniesService implements ILgisticsCompaniesService {

	@Autowired
	private LgisticsCompaniesDao lgisticsCompaniesDao;
	
	
	@Override
	public List<LgisticsCompanies> getLgisticsCompanies() {
		return lgisticsCompaniesDao.getLgisticsCompanies();
	}
	@Override
	public List<Map<String, Object>> getLgisticsCompaniesForMap() {
		return lgisticsCompaniesDao.getLgisticsCompaniesForMap();
	}



	@Override
	public LgisticsCompanies getLgisticsCompanies(String lgisticsCode) {
		
		return lgisticsCompaniesDao.getLgisticsCompanies(lgisticsCode);
	}

	
}
