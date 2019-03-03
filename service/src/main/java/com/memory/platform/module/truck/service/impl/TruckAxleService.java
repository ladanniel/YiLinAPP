package com.memory.platform.module.truck.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.TruckAxle;
import com.memory.platform.module.truck.dao.TruckAxleDao;
import com.memory.platform.module.truck.service.ITruckAxleService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：轮轴属性业务层实现类
* 版    本    号：  V1.0
*/
@Service
public class TruckAxleService implements ITruckAxleService {
	@Autowired
	private TruckAxleDao truckAxleDao;
	/**
	* 功 能 描 述：根据车辆id查询轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckAxle
	 */
	@Override
	public TruckAxle checkTruckAxleByTruckno(String no) {
		return truckAxleDao.checkTruckAxleByTruckno(no);
	}
	/**
	* 功 能 描 述：根据id查询轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckAxle
	 */
	@Override
	public TruckAxle getTruckAxleById(String id) {
		return truckAxleDao.getTruckAxleById(id);
	}
	/**
	* 功 能 描 述：保存轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveTruckAxle(TruckAxle truckAxle) {
		truckAxleDao.saveTruckAxle(truckAxle);
	}
	/**
	* 功 能 描 述：修改轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateTruckAxle(TruckAxle truckAxle) {
		truckAxleDao.updateTruckAxle(truckAxle);
	}
	/**
	* 功 能 描 述：删除轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeTruckAxle(String id) {
		truckAxleDao.removeTruckAxle(id);
	}
	@Override
	public List<Map<String, Object>> checkTruckAxleByTrucknoWithMap(String no) {
		return truckAxleDao.checkTruckAxleByTrucknoWithMap(no);
	}

}
