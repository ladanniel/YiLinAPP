package com.memory.platform.module.push.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.SendRecord;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.device.TagAliasResult;

public interface IPushService {

	void updateAliasAndTag(String accountID, String push_id, String alias, String tag)
			throws APIConnectionException, APIRequestException;

	void saveRecordAndSendMessageWithAccountID(String accountID, String title, String content,
			Map<String, String> extendData, String sendPoint);

	void saveRecordAndSendMessageWithAccountIDArray(List<String> accountIDS, String title, String content,
			Map<String, String> extendData, String sendPoint);

	void pushMessage(List<SendRecord> records);

	 

}
