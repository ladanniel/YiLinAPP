package com.memory.platform.module.system.service;

import java.io.IOException;
import java.util.Map;

import com.memory.platform.entity.sys.Bank;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月25日 上午9:44:48 
* 修 改 人： 
* 日   期： 
* 描   述： 银行数据字典服务接口
* 版 本 号：  V1.0
 */
public interface IBankService {

	void saveInfo(Bank bank,String path) throws IOException ;
	
	void updateInfo(Bank bank,String path) throws IOException ;
	
	void removeInfo(String id);
	
	Bank getBankByShortName(String shortName);
	
	Bank getBankByCnName(String cnName);
	
	Bank getBankById(String id);
	
	Map<String, Object> getPage(Bank bank,int start,int limit);
	
}
