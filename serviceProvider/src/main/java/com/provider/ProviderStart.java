package com.provider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.memory.platform.entity.additional.AdditionalExpensesRecord;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.sdk.SDKConfig;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.impl.RobOrderConfirmService;
import com.memory.platform.rocketMQ.broadcasting.BroadcastConsumer;
import com.memory.platform.rocketMQ.broadcasting.BroadcastProducer;

public class ProviderStart {
	static Logger logger = Logger.getLogger(ProviderStart.class);

	public static void main(String[] args) throws Exception {

		String appPath = "";
		if (args != null && args.length > 0) {
			System.out.println(args);
			String p = "";
			for (int i = 0; i < args.length; i++) {
				p += args[i];
			}
			appPath = p;

		}
		if (StringUtil.isEmpty(appPath)) {
			appPath = "classpath:application.xml";
		}
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { appPath });
		// printProvider(context,0);
	//	SDKConfig.getConfig().loadPropertiesFromSrc();
		context.start();
//		IRobOrderConfirmService confirmSvr =  context.getBean(RobOrderConfirmService.class);
//		RobOrderConfirm confirm = confirmSvr.getRobOrderConfirmById("402880005dca07dc015dca2d40dd0002"); 
//		for (AdditionalExpensesRecord record : confirm.getAdditionalExpensesRecords()) {
//			System.out.println(record.getId());
//		}
		Sheduler scheduler = context.getBean(Sheduler.class);
		scheduler.startSchedule();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Map<String, BroadcastConsumer> consumerBeansMap= context.getBeansOfType(BroadcastConsumer.class);
	 
		String line = null;
		do {
			line = reader.readLine();
			
		
			MessageExt msg = new MessageExt();
			msg.setTopic("recharge");
			msg.setTags("EROOR");
			msg.setBody(line.getBytes());
			
			//producer.sendMsg(msg);
		} while (StringUtil.isNotEmpty(line) &&  !"exit".equals(line));
		for (String key : consumerBeansMap.keySet()) {
			consumerBeansMap.get(key).getConsumer().shutdown();
		}
		
	 

	}
}