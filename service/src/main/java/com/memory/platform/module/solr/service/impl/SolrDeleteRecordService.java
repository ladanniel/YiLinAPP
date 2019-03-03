package com.memory.platform.module.solr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.entity.goods.Contacts;
import com.memory.platform.entity.solr.DeleteRecord;
import com.memory.platform.module.solr.dao.SolrDeleteRecordDao;
import com.memory.platform.module.solr.service.ISolrDeleteRecordService;

public class SolrDeleteRecordService implements ISolrDeleteRecordService {
	@Autowired
	private SolrDeleteRecordDao deleteRecordDao;

	@Override
	public List<DeleteRecord> getDeleteRecordList() {
		// TODO Auto-generated method stub
		return deleteRecordDao.getDeleteRecordList();
	}

	@Override
	public void removeRecord(DeleteRecord record) {
		// TODO Auto-generated method stub
		deleteRecordDao.delete(record);
	}
}
