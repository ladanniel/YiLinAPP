/**
 * 
 */
package com.memory.platform.module.global.dto;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.memory.platform.core.BeanKVO;

/**   
*    
* 项目名称：qbgwl_app_web   
* 类名称：BaseDTO   
* 类描述：   
* 创建人：ROG   
* 创建时间：2017年4月19日 上午9:31:02   
* 修改人：ROG   
* 修改时间：2017年4月19日 上午9:31:02   
* 修改备注：   
* @version    
*    
*/
/**
 * 功能描述： 输入参数: @param 异 常： 创 建 人: ROG 日 期:2017年4月19日 上午9:31:02 修 改 人:ROG 日
 * 期:2017年4月19日 上午9:31:02 返 回：
 */
public class BaseDTO {
	private String name = "1234567";

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	Logger log = Logger.getLogger(BaseDTO.class);
	BeanKVO kvo = new BeanKVO(this);

	@JsonIgnore
	protected BeanKVO getKVO() {
		return kvo;
	}

	public void setValueWithObject(Object obj) {
		kvo.setValueWithObject(obj);
	}

	@JsonIgnore
	public Object getValueForKey(String key) {
		return kvo.getValueForKey(key);
	}

	public void setValueForKey(String key, Object value) {

		kvo.setValueForKey(key, value);

	}

	// public static void main(String[] arg) {
	// BaseDTO info = new BaseDTO();
	// info.setName("aaa");
	// BaseDTO copy = new BaseDTO();
	// String str = (String) info.getValueForKey("name");
	//
	// info.setValueWithObject(copy);
	// str = (String) info.getValueForKey("name");
	// }
}
