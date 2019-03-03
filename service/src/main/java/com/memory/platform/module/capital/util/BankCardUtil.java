package com.memory.platform.module.capital.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.InitializingBean;

public class BankCardUtil implements InitializingBean {

	public static Properties properties = new Properties();
	
	/**获取银行信息字符串
	 * @param paramString
	 * @return
	 * @throws IOException
	 */
	public static String getBankJsonFromAliParamString(String paramString) throws IOException{
		URL url = null;
		String response = "{}";
		url = new URL(paramString);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setConnectTimeout(2000);
		InputStream is = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		response = bufferedReader.readLine();
		return response;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream inputStream = null;
		inputStream = this.getClass().getClassLoader().getResourceAsStream("bankcard.properties");
		properties.load(inputStream);
	}

}
