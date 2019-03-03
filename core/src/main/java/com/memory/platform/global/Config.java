package com.memory.platform.global;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.WeiXinPayOrder;
import com.memory.platform.entity.member.Account;
import com.memory.platform.module.system.service.IAccountService;

public class Config {
 

	public static String debug;
	public static String page_size = "18";
	public static String page_list = "[5,10,15,16,18,20,22,24,26,30]";

	public  static  String merId = "821520142154000"; // 银联商户号
}
