/**
 * 
 */
package com.memory.platform.security;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**   
*    
* 项目名称：qbgwl   
* 类名称：SpringContextUtil   
* 类描述：   
* 创建人：ROG   
* 创建时间：2017年4月17日 下午2:23:12   
* 修改人：ROG   
* 修改时间：2017年4月17日 下午2:23:12   
* 修改备注：   
* @version    
*    
*/
/**
 * 功能描述： 输入参数: @param 异 常： 创 建 人: ROG 日 期:2017年4月17日 下午2:23:12 修 改 人:ROG 日
 * 期:2017年4月17日 下午2:23:12 返 回：
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext; // Spring应用上下文环境

	/*
	 * 实现了ApplicationContextAware 接口，必须实现该方法；
	 * 通过传递applicationContext参数初始化成员变量applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

}