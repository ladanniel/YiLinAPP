package com.memory.platform.module.app.messageRecord.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.SendRecord;
import com.memory.platform.entity.sys.SendRecord.SendType;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ISendRecordService;

@Controller
@RequestMapping("app/messageRecord/")
public class MessageRecordController  extends BaseController{
	@Autowired
	ISendRecordService  sendRecordService;
	
	/*
	 * 获取推送消息
	 * */
	@RequestMapping(value = "getMyMessageRecordPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyMessageRecordPage(  int start, int size 
			 ) {
		start-=1;
		Account account = UserUtil.getAccount();
		SendRecord record =  new SendRecord();
		record.setReceive_user_id(account.getId());
		record.setSend_type(SendType.push);
		Map<String, Object> map = sendRecordService.getMyPage(record,start,size);
	
		return jsonView(true, "消息获取成功!", map );
	}
}
