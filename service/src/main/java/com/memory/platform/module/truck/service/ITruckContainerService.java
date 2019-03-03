package com.memory.platform.module.truck.service;


import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.TruckContainer;
 

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：货箱属性业务层
* 版    本    号：  V1.0
*/
public interface ITruckContainerService {
	/**
	* 功 能 描 述：根据车辆id查询货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckContainer
	 */
	public TruckContainer checkTruckContainerByTruckno(String no);
	/**
	* 功 能 描 述：根据id查询货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckContainer
	 */
	public TruckContainer getTruckContainerById(String id);
	/**
	* 功 能 描 述：保存货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckContainer(TruckContainer truckContainer);
	/**
	* 功 能 描 述：修改货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckContainer(TruckContainer truckContainer);
	/**
	* 功 能 描 述：删除货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeTruckContainer(String id);
	List<Map<String, Object>> checkTruckContainerByTrucknoWithMap(String truckid);
	
}
