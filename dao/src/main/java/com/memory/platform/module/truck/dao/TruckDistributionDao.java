package com.memory.platform.module.truck.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.TruckDistribution;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月15日 下午12:52:22 
* 修 改 人： 
* 日   期： 
* 描   述： 车辆司机分配DAO类
* 版 本 号：  V1.0
 */
@Repository
public class TruckDistributionDao extends BaseDao<TruckDistribution> {
 
	 
	/**
	* 功能描述： 保存车辆司机分配记录信息
	* 输入参数:  @param truckDistribution
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日下午12:53:15
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void saveTruckDistribution(TruckDistribution truckDistribution) {
		this.save(truckDistribution);
	}
	
	/**
	* 功能描述： 分页查询车辆分配的历史纪录信息
	* 输入参数:  @param truckDistribution
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日下午3:31:33
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(TruckDistribution truckDistribution, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from TruckDistribution truckDistribution where truckDistribution.trackId = :trackId ";
		map.put("trackId", truckDistribution.getTrackId());
		hql += " order by truckDistribution.distributionDate";
		return this.getPage(hql, map, start, limit);
	}
	 
}
