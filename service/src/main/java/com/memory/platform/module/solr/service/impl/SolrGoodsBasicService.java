package com.memory.platform.module.solr.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.module.solr.dao.SolrGoodsBasicDao;
import com.memory.platform.module.solr.service.ISolrGoodsBasicService;

 
public class SolrGoodsBasicService implements ISolrGoodsBasicService {
	@Autowired
	private SolrGoodsBasicDao goodsBasicDao;
	@Override
	public List<Map<String, Object>> querySqlListMap(String strSql,Map<String,Object> paramsMap) {
		return goodsBasicDao.getListBySQLMap(strSql, paramsMap);
	}
	/**
	 * 获取大于时间戳的数据集
	 */
	@Override
	public List<GoodsBasic> getGoodsBasicListWithTimestamp(Date timeStamp) {
		return goodsBasicDao.getGoodsBasicListWithTimestamp(timeStamp);
	}
}
