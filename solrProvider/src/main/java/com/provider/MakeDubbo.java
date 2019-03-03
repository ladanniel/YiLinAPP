package com.provider;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.memory.platform.global.StringUtil;

public class MakeDubbo {
	static  Logger logger = Logger.getLogger(SolrProviderStart.class);
	public  static void  main (String[] argsStrings ) throws ClassNotFoundException {
		
	 
			String  appPath = "classpath:application.xml";
	 
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { appPath });
		
		logger.info("provider*****************");
		printProvider(context,0);
		logger.info("consumer*****************");
		printProvider(context,1);
	}
	private static void printProvider(ClassPathXmlApplicationContext context,int type ) throws ClassNotFoundException {
		//接口
		//类包
		 logger.info("--------------------&&&&&&&&&&begin&&&&&&&&&&&&-------------------");
		for(String name : context.getBeanDefinitionNames()) {
			
			Object object =   context.getBean(name);
			Package p = object.getClass().getPackage();
		
			if(p.toString().indexOf("com.memory.platform.module")!=-1 &&p.toString().indexOf("service") !=-1
					&& p.toString().indexOf( "impl")!=-1
					) {
				 
					
				   String className = object.getClass().getName();
				   className = className.substring(0, className.indexOf('$'));
					Class orignalClass = Class.forName(className);
					Class[] interfaces = orignalClass.getInterfaces();
					
					 String interfaceName = "";
					for (Class class1 : interfaces) {
						if( class1.getName().indexOf( "com.memory.platform.module" ) !=-1 &&class1.getName().indexOf("service")!=-1   ) {
							interfaceName = class1.getName();
							break;
						}
						 
					}
				 if(StringUtil.isNotEmpty( interfaceName)) {
					 if(type==0) {
						 printProviderXml(name,className,interfaceName,object.getClass(),interfaces);
					 }else if(type ==1) {
						 printConsumerXml(name,className,interfaceName,object.getClass(),interfaces);
					 }
					 
				 }
			}
		}
		
		
		 logger.info("--------------------&&&&&&&&&end&&&&&&&&&&&&&-------------------");
	}

	private static void printConsumerXml(String name, String className,
			String interfaceName, Class<? extends Object> class1,
			Class[] interfaces) {
	 
		String str =  String.format( "<dubbo:reference id=\"%s\"  interface=\"%s\"/>",name,interfaceName);
		 logger.info(str);
	}
	private static void printProviderXml(String name, String className,
			String interfaceName, Class<? extends Object> class1,
			Class[] interfaces) {
		
		 String str = String.format( "<dubbo:service interface=\"%s\" "
		 		+ "ref=\"%s\" />"
				 , interfaceName,name);
		 String benString = String.format(  "<bean id=\"%s\" class=\"%s\" />",name,className);
		 logger.info(str);
		 logger.info(benString);
	}
}
