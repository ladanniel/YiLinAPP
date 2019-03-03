package com.memory.platform.module.order.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.order.OrderAutLog;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月16日 下午8:18:23 
* 修 改 人： 
* 日   期： 
* 描   述： 订单操作记录服务接口
* 版 本 号：  V1.0
 */
public interface IOrderAutLogService {

	/**
	* 功能描述： 操作记录分页
	* 输入参数:  @param log
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月16日下午8:16:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(OrderAutLog log,int start,int limit);
	
	/**
	* 功能描述： 保存操作记录
	* 输入参数:  @param log
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月16日下午8:17:11
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveLog(OrderAutLog log);
	
	/**
	* 功能描述： 根据id获取操作记录
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月16日下午8:18:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：OrderAutLog
	 */
	OrderAutLog getLogById(String id);
	
	List<Map<String, Object>> getListForMap(String orderId);
	
	/**
	* 功能描述： 查询日志记录信息
	* 输入参数:  @param orderId  订单ID
	* 输入参数:  @param order 配许方式 DESC、ASC
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月5日上午11:13:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	List<Map<String, Object>> getListForMap(String orderId,String order);
	
	/**
	* 功能描述： 根据属性获取操作记录
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月16日下午8:18:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：OrderAutLog
	 */
	OrderAutLog getLog(OrderAutLog log);
	
	
	/**
	 * 获取订单日志
	 * @param robConfirmId
	 * @return
	 */
	List<OrderAutLog> getOrderlog(String robConfirmId);

	List<Map<String, Object>> getOrderLog(String orderId);

	List<Map<String, Object>> getOrderlogListMap(String robConfirmId);

	List<Map<String, Object>> getOrderAndConfirmLogListMap(String robOrderID,
			String robConfirmId);
	
}
