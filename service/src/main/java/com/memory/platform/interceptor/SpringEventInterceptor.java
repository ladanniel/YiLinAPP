package com.memory.platform.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.memory.platform.entity.sys.SendRecord;
import com.memory.platform.entity.sys.SendRecord.SendType;
import com.memory.platform.entity.sys.SendRecord.Status;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.system.service.ISendRecordService;
/*
 * bylil
 * 服务器定时任务类
 * */
public class SpringEventInterceptor implements ApplicationListener<ContextRefreshedEvent>,Runnable{

	@Override
	public void run() {
	 
		
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		 
		
	}
//	Logger logger = Logger.getLogger(SpringEventInterceptor.class);
//	 
//	ScheduledExecutorService service=null;
//	@Autowired
//	ISendRecordService sendRecordService;
//	@Autowired
//	ISendMessageService sendMessageService;
//	@Autowired
//	IPushService pushService;
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//		
//		if(event.getApplicationContext().getParent() == null){//Spring加载完成.
//			 startSchedule();
//		}
//	}
//	private void startSchedule() {
//		service = Executors    
//		        .newSingleThreadScheduledExecutor();
//	 
//			service.scheduleAtFixedRate(this, 60, 2, TimeUnit.SECONDS);
//	 
//	}
//	@Override
//	public void run() {
//		try {
//			SendPushMessage();
//		} catch (Exception e) {
//			 logger.error(e.toString());
//		}
//		//发送推送
//		 
//		
//	}
//	private void SendPushMessage() {
//		SendRecord queryRecord = new SendRecord();
//		queryRecord.setStatus(Status.waitSend);
//		queryRecord.setSend_type(SendType.push);
//		List<SendRecord> lst =  sendRecordService.getWaitSendMessage(queryRecord, 0, 100); //一次最多100条
//		pushService.pushMessage(lst);
//	}
}
