package com.memory.platform.module.truck.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.BearingNum;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆轴数业务层
* 版    本    号：  V1.0
*/
public interface IBearingNumService {
	/**
	* 功 能 描 述：分页查询车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getPage(BearingNum bearingNum,int start, int limit);
	/**
	* 功 能 描 述：根据id查询车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：BearingNum
	 */
	BearingNum getBearingNumById(String id);
	/**
	* 功 能 描 述：保存车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void saveBearingNum(BearingNum bearingNum);
	/**
	* 功 能 描 述：修改车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void updateBearingNum(BearingNum bearingNum);
	/**
	* 功 能 描 述：检查车辆轴数是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getBearingNumByName(String name);
	/**
	* 功 能 描 述：检查车辆轴数是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getBearingNumByName(String name, String bearingNumId);
	/**
	* 功 能 描 述：删除车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void removeBearingNum(String id);
	/**
	* 功 能 描 述：查询所有车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	List<BearingNum> getBearingNumList();
	List<Map<String, Object>> getBearingNumListForMap();
}
