package com.memory.platform.module.solr.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.module.solr.dao.SolrRobOrderRecordDao;
import com.memory.platform.module.solr.service.ISolrRobOrderRecordService;

public class SolrRobOrderRecordService implements ISolrRobOrderRecordService{
	@Autowired
	private SolrRobOrderRecordDao robOrderRecordDao;
//	@Override
//	public List<RobOrderRecord> getRobOrderRecordListWithTimestamp(Date timeStamp) {
//		// TODO Auto-generated method stub
//		return robOrderRecordDao.getRobOrderRecordListWithTimestamp(timeStamp);
//	}
	@Override
	public  List<Map<String, Object>> getSolrRobOrderRecordByTimeStamp(Date timeStamp) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getSolrRobOrderRecordByTimeStamp(timeStamp);
	}

}
