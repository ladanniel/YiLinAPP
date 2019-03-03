package com.memory.platform.module.solr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.solr.DeleteRecord;
import com.memory.platform.module.global.dao.BaseDao;
@Repository
public class SolrDeleteRecordDao  extends BaseDao<DeleteRecord>{
	
	public List<DeleteRecord> getDeleteRecordList() {
		String strSql = " SELECT  * from delete_record ";
		Map<String, Object> map = new HashMap<String, Object>();
		return this.getListBySql(strSql, map, DeleteRecord.class);
	}
	
	
}
