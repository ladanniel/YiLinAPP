package com.memory.platform.module.solr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.memory.platform.entity.goods.GoodsBasic;

public interface ISolrGoodsBasicService {
	List<GoodsBasic> getGoodsBasicListWithTimestamp(Date timeStamp);

	List<Map<String, Object>> querySqlListMap(String strSql,
			Map<String, Object> paramsMap);
}
