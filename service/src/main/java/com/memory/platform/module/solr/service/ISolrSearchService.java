package com.memory.platform.module.solr.service;

import java.util.Map;

import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;

public interface ISolrSearchService {
	Map<String, Object> searchRobOrderRecord(String key,RobOrderRecord robOrderRecord, int start, int size);
	
	Map<String, Object> searchRobOrderConfirmRecord(String key, RobOrderConfirm robOrderConfirm,int start,int size);
}
