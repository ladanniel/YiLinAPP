package com.memory.platform.module.solr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.memory.platform.entity.order.RobOrderConfirm;

public interface ISolrRobOrderConfirmService {
	List<RobOrderConfirm> getRobOrderConfirmListWithTimestamp(Date timeStamp);
	Map<String, Object> getSolrRobOrderConfirmById(String id);
 
	List<Map<String, Object>> getSolrRoborderConfirmListByTimeStamp(
			Date timestamp);
}
