package com.memory.platform.auto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.memory.platform.global.sdk.SDKConfig;

/**
 * 
 * 加载acp_sdk.properties属性文件并将该属性文件中的键值对赋值到SDKConfig类中 
 * 
 */
public class AutoLoadServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8512544171997292934L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		//test
		//SDKConfig.getConfig().loadPropertiesFromSrc();
		super.init();
	}
}
