package com.memory.platform.module.truck.service;

import java.util.Map;

import com.memory.platform.entity.truck.TruckDistribution;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月15日 下午12:49:09 
* 修 改 人： 
* 日   期： 
* 描   述： 车辆分页记录业务接口
* 版 本 号：  V1.0
 */
public interface ITruckDistributionService {
	
	/**
	* 功能描述： 保存车辆人员分配记录信息
	* 输入参数:  @param truckDistribution
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日下午12:50:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveTruckDistribution(TruckDistribution truckDistribution);
	
	/**
	* 功能描述： 分页查询已分配的历史纪录
	* 输入参数:  @param truckDistribution
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日下午3:30:25
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(TruckDistribution truckDistribution, int start, int limit);
}
