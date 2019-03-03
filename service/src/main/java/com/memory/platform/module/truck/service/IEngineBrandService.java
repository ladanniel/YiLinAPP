package com.memory.platform.module.truck.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.truck.EngineBrand;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：发动机品牌业务层
* 版    本    号：  V1.0
*/
public interface IEngineBrandService {
	/**
	* 功 能 描 述：分页查询发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getPage(EngineBrand engineBrand,int start, int limit);
	/**
	* 功 能 描 述：根据id查询发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：EngineBrand
	 */
	EngineBrand getEngineBrandById(String id);
	/**
	* 功 能 描 述：保存发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void saveEngineBrand(EngineBrand engineBrand);
	/**
	* 功 能 描 述：修改发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void updateEngineBrand(EngineBrand engineBrand);
	/**
	* 功 能 描 述：检查发动机品牌是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getEngineBrandByName(String name);
	/**
	* 功 能 描 述：检查发动机品牌是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	boolean getEngineBrandByName(String name, String engineBrandId);
	/**
	* 功 能 描 述：删除发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void removeEngineBrand(String id);
	/**
	* 功 能 描 述：查询所有发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	List<EngineBrand> getEngineBrandList();
	List<Map<String, Object>> getEngineBrandListForMap();
	
}
