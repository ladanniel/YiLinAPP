package com.memory.platform.module.solr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.memory.platform.entity.order.RobOrderRecord;

public interface ISolrRobOrderRecordService {
	   List<Map<String, Object>> getSolrRobOrderRecordByTimeStamp(Date timeStamp);
	//Map<String,Object> getSolrRobOrderRecordById(String id);
}
