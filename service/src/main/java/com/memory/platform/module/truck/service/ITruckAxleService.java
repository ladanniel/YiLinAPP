package com.memory.platform.module.truck.service;


import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.TruckAxle;
 
 

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：轮轴属性业务层
* 版    本    号：  V1.0
*/
public interface ITruckAxleService {
	/**
	* 功 能 描 述：根据车辆id查询轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckAxle
	 */
	public TruckAxle checkTruckAxleByTruckno(String no);
	/**
	* 功 能 描 述：根据id查询轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckAxle
	 */
	public TruckAxle getTruckAxleById(String id);
	/**
	* 功 能 描 述：保存轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckAxle(TruckAxle truckAxle);
	/**
	* 功 能 描 述：修改轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckAxle(TruckAxle truckAxle);
	/**
	* 功 能 描 述：删除轮轴属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeTruckAxle(String id);
	List<Map<String, Object>> checkTruckAxleByTrucknoWithMap(String no);
 
	
}
