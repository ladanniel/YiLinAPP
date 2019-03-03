package com.memory.platform.module.truck.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.AxleType;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：拖挂轮轴类型业务层
* 版    本    号：  V1.0
*/
public interface IAxleTypeService {
	/**
	 * 
	* 功 能 描 述：分页查询所有轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getPage(AxleType axleType,int start, int limit);
	/**
	 * 
	* 功 能 描 述：根据编号查询轮轴类型实体
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：AxleType
	 */
	AxleType getAxleTypeById(String id);
	/**
	 * 
	* 功 能 描 述：保存轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void saveAxleType(AxleType axleType);
	/**
	 * 
	* 功 能 描 述：修改轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void updateAxleType(AxleType axleType);
	/**
	 * 
	* 功 能 描 述：检测轮轴类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getAxleTypeByName(String name);
	/**
	 * 
	* 功 能 描 述：检测轮轴类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getAxleTypeByName(String name, String axleTypeId);
	/**
	 * 
	* 功 能 描 述：删除轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void removeAxleType(String id);
	/**
	 * 
	* 功 能 描 述：查询所有轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	List<AxleType> getAxleTypeList();
	List<Map<String, Object>> getAxleTypeListForMap();
}
