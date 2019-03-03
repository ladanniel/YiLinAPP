package com.memory.platform.module.order.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;


/**
* 创 建 人：武国庆
* 日    期： 2016年6月16日 下午9:02:25 
* 修 改 人： 
* 日   期： 
* 描   述： 订单统计
* 版 本 号：  V1.0
 */
public interface IRobOrderConfirmStatisticsService {
	
	
	/**
	* 功能描述：     获取订单状态条数
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param payPassword
	* 输入参数:  @param truckIds
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月6日下午2:13:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	List<Map<String, Object>> getRobOrderConfirmStatusCount(RobOrderConfirm robOrderConfirm);
	
	
	/**
	* 功能描述：    运单量统计
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param payPassword
	* 输入参数:  @param truckIds
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月6日下午2:13:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	Map<String, Object> getRobOrderConfirm(Date strDate,Date endDate,RobOrderConfirm robOrderConfirm,SimpleDateFormat sdf1);
	
	/**
	* 功能描述：    运单t重量统计
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param payPassword
	* 输入参数:  @param truckIds
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月6日下午2:13:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	Map<String, Object> getRobOrderConfirmWeight(Date strDate,Date endDate,RobOrderConfirm robOrderConfirm,SimpleDateFormat sdf1);
	
	
	/**
	 * 运单状态数统计
	 * @param account
	 * @return
	 */
	 Map<String, Object> getRobOrderConfirmCount(Account account);
	 
	 
	 
		/**
		* 功能描述：     通过月分查运单数
		* 输入参数:  @param accountId
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年8月24日下午1:00:54
		* 修 改 人: 
		* 日    期: 
		* 返    回：Map<String,Object>
		 */
		@SuppressWarnings("rawtypes")
		List getAllConfirmMonthCount(List<String> months,Account account,String dateType,RobOrderRecord robOrderRecord);
		
		
		/**
		* 功能描述：     通过月分查完结运单数
		* 输入参数:  @param accountId
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年8月24日下午1:00:54
		* 修 改 人: 
		* 日    期: 
		* 返    回：Map<String,Object>
		 */
		@SuppressWarnings("rawtypes")
		List getConfirmCompletionMonthCount(List<String> months,Account account,String dateType,RobOrderRecord robOrderRecord);
		
		
		/**
		* 功能描述：     通过月分查运单重量
		* 输入参数:  @param accountId
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年8月24日下午1:00:54
		* 修 改 人: 
		* 日    期: 
		* 返    回：Map<String,Object>
		 */
		@SuppressWarnings("rawtypes")
		List getAllConfirmMonthWeight(List<String> months,Account account,String dateType,RobOrderRecord robOrderRecord);
		
		
		/**
		* 功能描述：     通过月分查完结运单重量
		* 输入参数:  @param accountId
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年8月24日下午1:00:54
		* 修 改 人: 
		* 日    期: 
		* 返    回：Map<String,Object>
		 */
		@SuppressWarnings("rawtypes")
		List getConfirmCompletionMonthWeight(List<String> months,Account account,String dateType,RobOrderRecord robOrderRecord);
		
		
		/**
		* 功能描述： 统计运单排行
		* 输入参数:  @param ranking
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年8月24日下午3:53:39
		* 修 改 人: 
		* 日    期: 
		* 返    回：List<Map<String,Object>>
		 */
		public Map<String, Object> getConfirmRankingStatistics(int ranking,String type);

}
