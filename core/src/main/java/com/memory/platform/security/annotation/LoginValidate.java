/**
 * 
 */
package com.memory.platform.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
*    
* 项目名称：qbgwl   
* 类名称：LoginAnnotation   
* 类描述：   
* 创建人：ROG   
* 创建时间：2017年4月17日 上午11:10:49   
* 修改人：ROG   
* 修改时间：2017年4月17日 上午11:10:49   
* 修改备注：   
* @version    
*    
*/
/**
	* 功能描述：
	* 输入参数: @param
	* 异 常：
	* 创 建 人: ROG
	* 日 期:2017年4月17日 上午11:10:49
	* 修 改 人:ROG
	* 日 期:2017年4月17日 上午11:10:49 
	* 返 回：
*/
@Target(value={ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginValidate {
	/*
	 * 是否需要登录 默认为true
	 * */
	 boolean value() default true;
}
