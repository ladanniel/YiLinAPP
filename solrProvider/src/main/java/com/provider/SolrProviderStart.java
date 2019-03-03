package com.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.memory.platform.entity.additional.AdditionalExpensesRecord;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.sdk.SDKConfig;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.impl.RobOrderConfirmService;

public class SolrProviderStart {
	static Logger logger = Logger.getLogger(SolrProviderStart.class);

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
		context.start();
		SolrScheduler scheduler = context.getBean(SolrScheduler.class);
		scheduler.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		do {
			line = reader.readLine();
			if("clear".equals(line)) {
				scheduler.setClear(true);
			}
		} while (StringUtil.isNotEmpty(line) &&  !"exit".equals(line));
		//old(appPath);
	 

	}

	private static void old(String appPath) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { appPath });
		context.start();
 		Sheduler scheduler = context.getBean(Sheduler.class);
      	scheduler.start();
      	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		do {
			line = reader.readLine();
			if("clear".equals(line)) {
				scheduler.setClear(true);
			}
		} while (StringUtil.isNotEmpty(line) &&  !"exit".equals(line));
	}
}