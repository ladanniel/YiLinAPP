package com.memory.platform.module.truck.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.ContainersType;


/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：货箱形式业务层
* 版    本    号：  V1.0
*/
public interface IContainersTypeService {
	/**
	* 功 能 描 述：分页查询货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getPage(ContainersType containersType,int start, int limit);
	/**
	* 功 能 描 述：根据id查询货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：ContainersType
	 */
	ContainersType getContainersTypeById(String id);
	/**
	* 功 能 描 述：保存货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void saveContainersType(ContainersType containersType);
	/**
	* 功 能 描 述：修改货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void updateContainersType(ContainersType containersType);
	/**
	* 功 能 描 述：检查货箱形式是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getContainersTypeByName(String name);
	/**
	* 功 能 描 述：检查货箱形式是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getContainersTypeByName(String name, String containersTypeId);
	/**
	* 功 能 描 述：删除货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void removeContainersType(String id);
	/**
	* 功 能 描 述：查询所有货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	List<ContainersType> getContainersTypeList();
	List<Map<String, Object>> getContainersTypeListForMap();
}
