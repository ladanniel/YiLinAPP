package com.memory.platform.module.truck.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.EngineBrand;
import com.memory.platform.module.truck.dao.EngineBrandDao;
import com.memory.platform.module.truck.service.IEngineBrandService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：品牌业务层实现类
* 版    本    号：  V1.0
*/
@Service
public class EngineBrandService implements IEngineBrandService {
	@Autowired
	private EngineBrandDao engineBrandDao;
	/**
	* 功 能 描 述：分页查询发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@Override
	public Map<String, Object> getPage(EngineBrand engineBrand, int start, int limit) {
		return engineBrandDao.getPage(engineBrand, start, limit);
	}
	/**
	* 功 能 描 述：根据id查询发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：EngineBrand
	 */
	@Override
	public EngineBrand getEngineBrandById(String id) {
		return engineBrandDao.getEngineBrandById(id);
	}
	/**
	* 功 能 描 述：保存发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveEngineBrand(EngineBrand engineBrand) {
		engineBrandDao.saveEngineBrand(engineBrand);

	}
	/**
	* 功 能 描 述：修改发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateEngineBrand(EngineBrand engineBrand) {
		engineBrandDao.updateEngineBrand(engineBrand);
	}
	/**
	* 功 能 描 述：检查发动机品牌是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getEngineBrandByName(String name) {
		return engineBrandDao.getEngineBrandByName(name);
	}
	/**
	* 功 能 描 述：检查发动机品牌是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getEngineBrandByName(String name, String engineBrandId) {
		return engineBrandDao.getEngineBrandByName(name, engineBrandId);
	}
	/**
	* 功 能 描 述：删除发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeEngineBrand(String id) {
		engineBrandDao.removeEngineBrand(id);
	}
	/**
	* 功 能 描 述：查询所有发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	@Override
	public List<EngineBrand> getEngineBrandList() {
		return engineBrandDao.getEngineBrandList();
	}

	
	/**
	* 功能描述：查询所有发动机品牌
	* 输入参数:  @param 
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：list
	 */
	@Override
	public List<Map<String, Object>> getEngineBrandListForMap() {
		return engineBrandDao.getEngineBrandListForMap();
	}
}
