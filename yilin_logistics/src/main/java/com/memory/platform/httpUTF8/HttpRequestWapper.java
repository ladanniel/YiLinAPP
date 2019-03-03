package com.memory.platform.httpUTF8;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import redis.clients.jedis.Jedis;

import com.google.gson.GsonBuilder;
import com.memory.platform.core.AppUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.ThreeDESUtil;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.security.AppEncrypt;

/**
 * @author lil 请求解密解析器
 */
public class HttpRequestWapper extends HttpServletRequestWrapper {
	Map<String, String> bodyMap = new HashMap<String, String>();

	// 请求加密钥匙
	static String key = "66a5d79394b647c18f7d4eba4a56e737992bd63b2c342689";
	static {

		try {
			ThreeDESUtil.des3AppEncodeCBC("123", key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 请求是否加密
	boolean isEncript;

	@SuppressWarnings("unchecked")
	public HttpRequestWapper(HttpServletRequest request) {

		super(request);
		try {

			request.setCharacterEncoding("UTF-8");
			Enumeration enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String name = (String) enumeration.nextElement();
				String vObject = request.getParameter(name);
				 
				 
				//aiqiwu 2018-02-28注释，添加货物中文正常，转换后异常
				//String strValue =  new String(vObject.getBytes("ISO-8859-1"), "UTF-8");
				//System.out.println( " key " + name  + "value : " +strValue);
				    
				bodyMap.put(name, vObject);
				this.isEncript = true;
			}

			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public String[] getParameterValues(String name) {
		if (isEncript == false)
			return super.getParameterValues(name);

		return getBodyParameterValues(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getParameterNames() {
		if (isEncript == false)
			return super.getParameterNames();
		Vector<String> names = new Vector<String>();
		names.addAll(this.bodyMap.keySet());
		return names.elements();
	}

	private String[] getBodyParameterValues(String name) {
		if (bodyMap == null || StringUtil.isEmpty(bodyMap.get(name))) {
			return null;
		}

		String data = bodyMap.get(name);
		// try {
		// data= new String(data.getBytes(),"utf-8");
		// } catch (UnsupportedEncodingException e) {
		//
		// e.printStackTrace();
		// }
		return new String[] { data };
	}

	public static void main(String[] args) throws Exception {
		String data = "{\"mobile\":\"18275612787\",\"areaId\":\"110106\",\"name\":\"艾其武\",\"address\":\"好好干\"}";
		String key = "81702aff95a646ac83426f1967354b164af7b159da0bf3ca";
		String v = ThreeDESUtil.testDes3Encode(data, key);

	}
}
