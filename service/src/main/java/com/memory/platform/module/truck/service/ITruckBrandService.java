package com.memory.platform.module.truck.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.TruckBrand;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆品牌业务层
* 版    本    号：  V1.0
*/
public interface ITruckBrandService {
	/**
	* 功 能 描 述：分页查询车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getPage(TruckBrand truckBrand,int start, int limit);
	/**
	* 功 能 描 述：根据id查询车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckBrand
	 */
	TruckBrand getTruckBrandById(String id);
	/**
	* 功 能 描 述：保存车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void saveTruckBrand(TruckBrand truckBrand);
	/**
	* 功 能 描 述：修改车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void updateTruckBrand(TruckBrand truckBrand);
	/**
	* 功 能 描 述：检测车辆品牌是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getTruckBrandByName(String name);
	/**
	* 功 能 描 述：检测车辆品牌是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getTruckBrandByName(String name, String truckBrandId);
	/**
	* 功 能 描 述：删除车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void removeTruckBrand(String id);
	/**
	* 功 能 描 述：查询所有车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	List<TruckBrand> getTruckBrandList();
	List<Map<String, Object>> getTruckBrandListForMap();
	List<Map<String, Object>> getTruckBrandListWithMap();
	
}
