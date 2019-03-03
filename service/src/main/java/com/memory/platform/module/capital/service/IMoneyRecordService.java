package com.memory.platform.module.capital.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.memory.platform.entity.capital.MoneyRecord;

/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午7:00:35 
* 修 改 人： 
* 日   期： 
* 描   述：资金记录接口 
* 版 本 号：  V1.0
 */
public interface IMoneyRecordService {

	/**
	* 功能描述： 分页资金记录
	* 输入参数:  @param moneyRecord
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月29日上午11:03:34
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(MoneyRecord moneyRecord,int start, int limit);
	
	/**
	* 功能描述： 根据id获取交易记录
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月25日下午4:52:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：MoneyRecord
	 */
	MoneyRecord getMoneyRecordById(String id);
	
	/**
	* 功能描述： excel数据源
	* 输入参数:  @param moneyRecord
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月28日上午10:31:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<MoneyRecord>
	 */
	List<MoneyRecord> getList(MoneyRecord moneyRecord);
	
	/**
	* 功能描述： 根据账户统计各类型交易数据信息
	* 输入参数:  @param accountId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年8月6日下午5:25:19
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	List<Map<String, Object>> getMoneyRecordInfo(String accountId);
	
	/**
	* 功能描述： 统计平台交易信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年8月20日下午12:31:31
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	List<Map<String, Object>> getMoneyRecordInfo();
	
	/**
	* 功能描述： 根据账户及时间段统计个类型月交易数据信息
	* 输入参数:  @param accountId
	* 输入参数:  @param startTime
	* 输入参数:  @param endTime
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年8月10日下午3:39:38
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	List<Map<String, Object>> getMoneyRecordInfo(String accountId,String startTime,String endTime);
	
	public Map<String,Object> getAllMoneyRecord(List<String> dates,String accountId,String dateType,String type);

	Map<String, Object> getListForMap(String accountId,String type,int start,int size,Date startTime,Date endTime);
	
	/**
	 * 获取分页资金记录
	 * aiqiwu 2017-09-18
	 * */
	Map<String,Object> getCapitalRecordPage(MoneyRecord moneyRecord,int start, int limit);
}
