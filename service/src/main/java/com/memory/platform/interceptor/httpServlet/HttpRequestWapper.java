package com.memory.platform.interceptor.httpServlet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import redis.clients.jedis.Jedis;

import com.google.gson.GsonBuilder;
import com.memory.platform.core.AppUtil;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DoubleHelper;
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
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String body = request.getParameter("body");
		String tokenID =  request.getHeader("token");
		System.out.print( request.getContentType());	
		System.out.print(request.getMethod());
		if (StringUtil.isNotEmpty( tokenID) || StringUtil.isNotEmpty(body) ) {
			try {
				
				if(StringUtil.isEmpty(body) && request.getMethod().equals("POST")) {
					 System.out.print( request.getContentType());
					 
				}
				AppEncrypt appEncrypt = AppEncrypt.getInstance();
				if(StringUtil.isNotEmpty( body))
					body =  appEncrypt.decryptPrivateKey(body, tokenID);
				 
 		//	body = ThreeDESUtil.des3DecodeCBC(body, key);
//				 
 			bodyMap = (Map<String, String>) AppUtil.getGson().fromJson(
 						body, bodyMap.getClass());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			isEncript = true;
		} else {
			isEncript = false;
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
//		double count = 0;
//		for (int i = 0; i <10000000; i++) {
//			count += 0.01;
//			String str = count+ "";
//			int  dotCount  = str.length() -1 - str.indexOf(".");
//			if(dotCount>2) {
//				throw new BusinessException("111");
//			}
//		}
		
//	  if(	10000000 !=  data){
//		  throw new BusinessException("111");
//	  }
		
	//	String data = "{\"mobile\":\"18275612787\",\"areaId\":\"110106\",\"name\":\"艾其武\",\"address\":\"好好干\"}";
		//String key = "81702aff95a646ac83426f1967354b164af7b159da0bf3ca";
		//String v = ThreeDESUtil.testDes3Encode(data, key);

	}
}
