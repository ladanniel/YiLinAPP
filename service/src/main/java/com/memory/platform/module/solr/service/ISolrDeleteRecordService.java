package com.memory.platform.module.solr.service;

import java.util.Date;
import java.util.List;

import com.memory.platform.entity.solr.DeleteRecord;

public interface ISolrDeleteRecordService {
	List<DeleteRecord> getDeleteRecordList();
	
	void removeRecord(DeleteRecord record);
}
