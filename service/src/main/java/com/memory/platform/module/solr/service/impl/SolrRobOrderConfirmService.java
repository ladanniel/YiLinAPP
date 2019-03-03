package com.memory.platform.module.solr.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.module.solr.dao.SolrRobOrderConfirmDao;
import com.memory.platform.module.solr.service.ISolrRobOrderConfirmService;

public class SolrRobOrderConfirmService implements ISolrRobOrderConfirmService{
	@Autowired
	private SolrRobOrderConfirmDao robOrderConfirmDao;
	@Override
	public List<RobOrderConfirm> getRobOrderConfirmListWithTimestamp(Date timeStamp) {
		return robOrderConfirmDao.getRobOrderConfirmListWithTimestamp(timeStamp);
	}
	@Override
	public List<Map<String, Object>> getSolrRoborderConfirmListByTimeStamp(Date timestamp) { 
		return robOrderConfirmDao.getSolrRoborderConfirmListByTimeStamp(timestamp);
	}
	@Override
	public Map<String, Object> getSolrRobOrderConfirmById(String id) {
		// TODO Auto-generated method stub
		return robOrderConfirmDao.getSolrRobOrderConfirmById(id);
	}
	
}
