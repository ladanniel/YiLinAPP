package com.memory.platform.global.sdk;

import com.memory.platform.core.XmlUtils;
import com.memory.platform.entity.capital.WeiXinPayOrder;

public class HttpService {
	
	/**
	 * 微信http请求
	 * */
	public static WeiXinPayOrder post(String reqData, String reqUrl, String encoding) {
		WeiXinPayOrder rspData = null;
		LogUtil.writeLog("请求微信复接口地址:" + reqUrl);
		// 发送后台请求数据
		HttpClient hc = new HttpClient(reqUrl, 30000, 30000);
		try {
			int status = hc.send(reqData, encoding);
			if (200 == status) {
				String resultString = hc.getResult();
				if (null != resultString && !"".equals(resultString)) {
					rspData = new WeiXinPayOrder();
					rspData = XmlUtils.converyToJavaBean(resultString, WeiXinPayOrder.class);
				}
			} else {
				LogUtil.writeLog("返回http状态码[" + status + "]，请检查请求报文或者请求地址是否正确");
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}
		return rspData;
	}

	/**
	 * 通用http请求
	 * */
	public static String b2bPost(String reqData, String reqUrl, String encoding) {
		String data="";
		HttpClient hc = new HttpClient(reqUrl, 30000, 30000);
		try {
			int status = hc.send(reqData, encoding);
			if (200 == status) {
				String resultString = hc.getResult();
				if (null != resultString && !"".equals(resultString)) {
					data = resultString;
				}
			} else {
				LogUtil.writeLog("返回http状态码[" + status + "]，请检查请求报文或者请求地址是否正确");
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}
		
		
		return data;
	}
	
}
