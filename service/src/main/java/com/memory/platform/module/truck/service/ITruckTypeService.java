package com.memory.platform.module.truck.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.TruckType;

public interface ITruckTypeService {
	/**
	* 功 能 描 述：分页查询所有车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getPage(TruckType truckType,int start, int limit);
	/**
	* 功 能 描 述：根据id查询车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckType
	 */
	TruckType getTruckTypeById(String id);
	/**
	* 功 能 描 述：保存车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void saveTruckType(TruckType truckType );
	/**
	* 功 能 描 述：修改车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void updateTruckType(TruckType truckType);
	/**
	* 功 能 描 述：检测车辆类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getTruckTypeByName(String name);
	/**
	* 功 能 描 述：检测车辆类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getTruckTypeByName(String name, String truckTypeId);
	/**
	* 功 能 描 述：删除车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void removeTruckType(String id);
	/**
	* 功 能 描 述：查询所有车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	List<TruckType> getTruckTypeList();
	List<Map<String, Object>> getTruckTypeListForMap();
	
}
