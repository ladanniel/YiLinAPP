package com.memory.platform.module.truck.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.TruckDistribution;
import com.memory.platform.module.truck.dao.TruckDistributionDao;
import com.memory.platform.module.truck.service.ITruckDistributionService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月15日 下午12:51:24 
* 修 改 人： 
* 日   期： 
* 描   述：车辆司机分配业务接口实现类
* 版 本 号：  V1.0
 */
@Service
public class TruckDistributionService implements ITruckDistributionService {
	@Autowired
	private TruckDistributionDao truckDistributionDao;

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
	@Override
	public void saveTruckDistribution(TruckDistribution truckDistribution) {
		// TODO Auto-generated method stub
		truckDistributionDao.save(truckDistribution);
	}

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
	@Override
	public Map<String, Object> getPage(TruckDistribution truckDistribution, int start, int limit) {
		// TODO Auto-generated method stub
		return truckDistributionDao.getPage(truckDistribution, start, limit);
	}

	 
}
