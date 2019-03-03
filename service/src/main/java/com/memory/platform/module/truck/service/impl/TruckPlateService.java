package com.memory.platform.module.truck.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.TruckPlate;
import com.memory.platform.module.truck.dao.TruckPlateDao;
import com.memory.platform.module.truck.service.ITruckPlateService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车牌类型业务层实现类
* 版    本    号：  V1.0
*/
@Service
public class TruckPlateService implements ITruckPlateService {
	@Autowired
	private TruckPlateDao truckPlateDao;
	/**
	* 功 能 描 述：分页查询车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@Override
	public Map<String, Object> getPage(TruckPlate truckPlate, int start, int limit) {
		return truckPlateDao.getPage(truckPlate, start, limit);
	}
	/**
	* 功 能 描 述：根据id查询车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckPlate
	 */
	@Override
	public TruckPlate getTruckPlateById(String id) {
		return truckPlateDao.getTruckPlateById(id);
	}
	/**
	* 功 能 描 述：保存车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveTruckPlate(TruckPlate truckPlate) {
		truckPlateDao.saveTruckPlate(truckPlate);

	}
	/**
	* 功 能 描 述：修改车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateTruckPlate(TruckPlate truckPlate) {
		truckPlateDao.updateTruckPlate(truckPlate);
	}
	/**
	* 功 能 描 述：检测车牌类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getTruckPlateByName(String name) {
		return truckPlateDao.getTruckPlateByName(name);
	}
	/**
	* 功 能 描 述：检测车牌类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getTruckPlateByName(String name, String truckPlateId) {
		return truckPlateDao.getTruckPlateByName(name, truckPlateId);
	}
	/**
	* 功 能 描 述：删除车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeTruckPlate(String id) {
		truckPlateDao.removeTruckPlate(id);
	}
	/**
	* 功 能 描 述：查询所有车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	@Override
	public List<TruckPlate> getTruckPlateList() {
		return truckPlateDao.getTruckPlateList();
	}
	
	/**
	* 功能描述：查询所有车牌类型
	* 输入参数:  @param name
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：list
	 */
	@Override
	public List<Map<String, Object>> getTruckPlateListWithMap() {
		return truckPlateDao.getTruckPlateListWithMap();
	}

}
