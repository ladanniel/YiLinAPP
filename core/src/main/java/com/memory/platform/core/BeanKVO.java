/**
 * 
 */
package com.memory.platform.core;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.SimpleTypeConverter;

import com.memory.platform.global.FreemarkerUtils;

/**   
*    
* 项目名称：qbgwl_app_web   
* 类名称：BeanKVO   
* 类描述：   
* 创建人：ROG   
* 创建时间：2017年4月19日 上午10:00:29   
* 修改人：ROG   
* 修改时间：2017年4月19日 上午10:00:29   
* 修改备注：   
* @version    
*    
*/
/**
 * 功能描述： 输入参数: @param 异 常： 创 建 人: ROG 日 期:2017年4月19日 上午10:00:29 修 改 人:ROG 日
 * 期:2017年4月19日 上午10:00:29 返 回：
 */
public class BeanKVO {
	Object me;
	SimpleTypeConverter simpleConvert = new SimpleTypeConverter();
	Logger log = Logger.getLogger(BeanKVO.class);
	public BeanKVO(Object o) {
		me = o;
	}

	public BeanKVO setValueWithObject(Object obj) {
	//	simpleConvert.registerCustomEditor(requiredType, propertyEditor);
		Map<Object, Object> map = new org.apache.commons.beanutils.BeanMap(obj);
		Map<Object, Object> meMap = new org.apache.commons.beanutils.BeanMap(me);
		BeanKVO kvo = new BeanKVO(obj);
		for (Object key : meMap.keySet()) {
			if (map.containsKey(key)) {
				Object value = map.get(key);
				this.setValueForKey(key.toString(),  value);
			}
		}
		return this;
	}

	public Object getValueForKey(String key) {
		BeanInfo beanInfo = null;
		Object ret = null;
		do {
			try {
				beanInfo = Introspector.getBeanInfo(me.getClass());
			} catch (IntrospectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (beanInfo == null)
				break;
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String propertyName = property.getName();
				if (!propertyName.equals(key)) {
					continue;
				}
				Method getter = property.getReadMethod();
				// Method setter = property.getWriteMethod();

				if (getter != null)
					try {
						ret = getter.invoke(me);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}

		} while (false);
		return ret;
	}
	public static  interface EnumPropertyCallBack  {
		boolean callback(String propertyName,Object value);
	}
 
	public static  void enumProperty(Object o ,EnumPropertyCallBack callback){
		BeanInfo beanInfo = null;
		 
		 
		do { 
			try {
				beanInfo = Introspector.getBeanInfo( o.getClass());
			} catch (IntrospectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (beanInfo == null)
				break;
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String propertyName = property.getName();
			 
				Method getter = property.getReadMethod();
				// Method setter = property.getWriteMethod();

				if (getter != null)
					try {
						Object value = getter.invoke(o);
						if(false == callback.callback(propertyName,value)) {
							break;
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						 
						e.printStackTrace();
					}

			}

		} while (false);
		 
	} 
	public void setValueForKey(String key, Object value) {

		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(me.getClass());
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (beanInfo == null)
			return;
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String propertyName = property.getName();
			if (!propertyName.equals(key)) {
				continue;
			}
			// Method getter = property.getReadMethod();
			Method setter = property.getWriteMethod();

			if (setter != null){
				try {
				
					Class toType = setter.getParameterTypes()[0];
					if(key.equals("bankType")){
						log.info(toType.toString());
					}
					if(value!=null){
						value = FreemarkerUtils.Convert(value, toType);
//						if(value.getClass()!=toType){
//							value = simpleConvert.convertIfNecessary(value, setter.getParameterTypes()[0]);
//						}
						setter.invoke(me, value);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					log.info( setter.getParameterTypes()[0].toString() + " value:" + value.getClass().toString());
				 
					e.printStackTrace();
				}
				break;
			}

		}
	}
}
