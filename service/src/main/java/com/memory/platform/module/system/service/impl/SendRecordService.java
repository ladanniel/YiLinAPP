package com.memory.platform.module.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.sys.SendRecord;
import com.memory.platform.entity.sys.SendRecord.SendType;
import com.memory.platform.entity.sys.SendRecord.Status;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.system.dao.SendRecordDao;
import com.memory.platform.module.system.service.ISendRecordService;

/**
 * 创 建 人： longqibo 日 期： 2016年5月31日 下午2:37:20 修 改 人： 日 期： 描 述： 短信记录 － 服务类 版 本 号：
 * V1.0
 */
@Service
public class SendRecordService implements ISendRecordService {

	@Autowired
	private SendRecordDao sendRecordDao;

	@Override
	public Map<String, Object> getPage(SendRecord sendRecord, int start,
			int limit) {
		return sendRecordDao.getPage(sendRecord, start, limit);
	}

	@Override
	public void saveRecord(SendRecord sendRecord) {
		sendRecordDao.save(sendRecord);
	}

	@Override
	public SendRecord getSendRecord(String id) {
		return sendRecordDao.getObjectById(SendRecord.class, id);
	}

	@Override
	public List<SendRecord> getWaitSendMessage(SendRecord queryRecord,
			int start, int limit) {

		return sendRecordDao.getRecordPage(queryRecord, start, limit);
	}
	@Override
	public void updateStatus (SendRecord record ) {
	    String sql = " update  sys_send_record set status =:status, push_msg = :msg where id =:id ";
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("id", record.getId());
	    map.put("status", record.getStatus().ordinal());
	    map.put("msg",StringUtil.isEmpty( record.getPush_msg())?"":record.getPush_msg());
		sendRecordDao.updateSQL(sql, map);
		
	}

	@Override
	public Map<String, Object> getMyPage(SendRecord record, int start, int size) {
		
		return sendRecordDao.getMyPage(record, start, size);
	}
}
