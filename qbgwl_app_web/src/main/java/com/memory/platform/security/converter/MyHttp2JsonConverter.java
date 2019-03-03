/**
 * 
 */
package com.memory.platform.security.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.ThreeDESUtil;
import com.memory.platform.security.AppEncrypt;

/**   
*    
* 项目名称：qbgwl_app_web   
* 类名称：MyHttp2JsonConverter   
* 类描述：   
* 创建人：ROG   
* 创建时间：2017年4月18日 上午10:38:49   
* 修改人：ROG   
* 修改时间：2017年4月18日 上午10:38:49   
* 修改备注：   
* @version    
*    
*/
/**
 * 功能描述： 输入参数: @param 异 常： 创 建 人: ROG 日 期:2017年4月18日 上午10:38:49 修 改 人:ROG 日
 * 期:2017年4月18日 上午10:38:49 返 回：
 */
public class MyHttp2JsonConverter extends MappingJackson2HttpMessageConverter {
	Logger log = Logger.getLogger(MyHttp2JsonConverter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.http.converter.json.
	 * AbstractJackson2HttpMessageConverter#writeInternal(java.lang.Object,
	 * org.springframework.http.HttpOutputMessage)
	 */
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		Map<String, Object> map = null;
		do {
			if (object != null) {
				map = (Map<String, Object>) object;
				HttpServletRequest requset = AppUtil.getThreadLocalObjectForKey("REQUEST");
				if (requset != null && map != null && map.containsKey("data")) {
					Account acc = (Account) AppUtil.getThreadLocalObjectForKey("USER");
					String token = null;
				
					
					AppEncrypt appEncrypt = AppEncrypt.getInstance();
					if (acc != null) {
						token = acc.getToken();
					}
					Object data = map.get("data");
					if(map.containsKey("isEncrypt")) { //不加密
						Boolean isEncrypt =(Boolean) map.get("isEncrypt");
						if(isEncrypt == false) {
							map.remove("isEncrypt");
							break;
						}
					}
					if(data != null) {
						String className = data.getClass().getName();
						String json = null;
						String noArrWrap = requset.getHeader("noArrWrap");
						Boolean noWrap = StringUtil.isNotEmpty(noArrWrap)&&noArrWrap.endsWith("1");
						if (noWrap || className.equals("java.util.LinkedList") || className.equals("java.util.ArrayList")) {
							 json = AppUtil.getGson().toJson(data);
							 
						} else {
							ArrayList<Object> list = new ArrayList<Object>();
							list.add(data);
							json = AppUtil.getGson().toJson(list);
						}
						if(StringUtil.isNotEmpty(json)) {
							try {
								map.put("data", appEncrypt.encryptPrivateKey(json,	token));
							} catch (Exception e) {
								map.put("data", null);
							   
								e.printStackTrace();
							}
						}
					}
					
//					if (data != null) {
//						String className = data.getClass().getName();
//						if (className.equals("java.util.LinkedList") || className.equals("java.util.ArrayList")) {
//							map.put("data", AppUtil.desJsonEncode(data, token));
//
//						} else {
//							ArrayList<Object> list = new ArrayList<Object>();
//							list.add(data);
//							map.put("data", list);
//							if (StringUtil.isEmpty(token) == false) {
//
//								map.put("data", AppUtil.desJsonEncode(map.get("data"), token));
//							}
//						}
//					}
				}
			}
		} while (false);
		if(log.isInfoEnabled())
		log.info("http2JSON convert" + object);
		super.writeInternal(map != null ? map : object, outputMessage);

	}
}
