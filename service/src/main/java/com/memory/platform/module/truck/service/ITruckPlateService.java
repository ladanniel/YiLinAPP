package com.memory.platform.module.truck.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.TruckPlate;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车牌类型业务层
* 版    本    号：  V1.0
*/
public interface ITruckPlateService {
	/**
	* 功 能 描 述：分页查询车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getPage(TruckPlate truckPlate,int start, int limit);
	/**
	* 功 能 描 述：根据id查询车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckPlate
	 */
	TruckPlate getTruckPlateById(String id);
	/**
	* 功 能 描 述：保存车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void saveTruckPlate(TruckPlate truckPlate);
	/**
	* 功 能 描 述：修改车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void updateTruckPlate(TruckPlate truckPlate);
	/**
	* 功 能 描 述：检测车牌类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getTruckPlateByName(String name);
	/**
	* 功 能 描 述：检测车牌类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getTruckPlateByName(String name, String truckPlateId);
	/**
	* 功 能 描 述：删除车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void removeTruckPlate(String id);
	/**
	* 功 能 描 述：查询所有车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	List<TruckPlate> getTruckPlateList();
	List<Map<String, Object>> getTruckPlateListWithMap();
	
}
