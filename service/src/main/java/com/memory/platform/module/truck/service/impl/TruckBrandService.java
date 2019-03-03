package com.memory.platform.module.truck.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.TruckBrand;
import com.memory.platform.module.truck.dao.TruckBrandDao;
import com.memory.platform.module.truck.service.ITruckBrandService;
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
public class TruckBrandService implements ITruckBrandService {
	@Autowired
	private TruckBrandDao truckBrandDao;
	/**
	* 功 能 描 述：分页查询车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@Override
	public Map<String, Object> getPage(TruckBrand truckBrand, int start, int limit) {
		return truckBrandDao.getPage(truckBrand, start, limit);
	}
	/**
	* 功 能 描 述：根据id查询车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckBrand
	 */
	@Override
	public TruckBrand getTruckBrandById(String id) {
		return truckBrandDao.getTruckBrandById(id);
	}
	/**
	* 功 能 描 述：保存车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveTruckBrand(TruckBrand truckBrand) {
		truckBrandDao.saveTruckBrand(truckBrand);

	}
	/**
	* 功 能 描 述：修改车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateTruckBrand(TruckBrand truckBrand) {
		truckBrandDao.updateTruckBrand(truckBrand);
	}
	/**
	* 功 能 描 述：检测车辆品牌是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getTruckBrandByName(String name) {
		return truckBrandDao.getTruckBrandByName(name);
	}
	/**
	* 功 能 描 述：检测车辆品牌是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getTruckBrandByName(String name, String truckBrandId) {
		return truckBrandDao.getTruckBrandByName(name, truckBrandId);
	}
	/**
	* 功 能 描 述：删除车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeTruckBrand(String id) {
		truckBrandDao.removeTruckBrand(id);
	}
	/**
	* 功 能 描 述：查询所有车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public List<TruckBrand> getTruckBrandList() {
		return truckBrandDao.getTruckBrandList();
	}
	@Override
	public List<Map<String, Object>> getTruckBrandListForMap() {
		return truckBrandDao.getTruckBrandListWithMap();
	}
	
	/**
	* 功能描述：查询所有车辆品牌
	* 输入参数:  @param name
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：list
	 */
	@Override
	public List<Map<String, Object>> getTruckBrandListWithMap() {
		return truckBrandDao.getTruckBrandListWithMap();
	}
}
