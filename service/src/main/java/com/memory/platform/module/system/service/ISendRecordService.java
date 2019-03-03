package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.SendRecord;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月31日 下午2:35:29 
* 修 改 人： 
* 日   期： 
* 描   述： 短信记录 － 服务接口
* 版 本 号：  V1.0
 */
public interface ISendRecordService {

	/**
	* 功能描述： 短信记录列表
	* 输入参数:  @param sendRecord
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午2:33:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(SendRecord sendRecord,int start, int limit);
	
	/**
	* 功能描述： 添加短信记录
	* 输入参数:  @param sendRecord
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午2:34:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveRecord(SendRecord sendRecord);
	
	SendRecord getSendRecord(String id);

	List<SendRecord> getWaitSendMessage(SendRecord queryRecord, int start,
			int limit);

	void updateStatus(SendRecord record);

	Map<String, Object> getMyPage(SendRecord record, int start, int size);

	 

	 
	
}
