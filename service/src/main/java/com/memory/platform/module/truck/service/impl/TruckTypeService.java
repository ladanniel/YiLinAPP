package com.memory.platform.module.truck.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.TruckType;
import com.memory.platform.module.truck.dao.TruckTypeDao;
import com.memory.platform.module.truck.service.ITruckTypeService;

@Service
public class TruckTypeService implements ITruckTypeService {

	@Autowired
	private TruckTypeDao truckTypeDao;
	/**
	* 功 能 描 述：分页查询所有车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@Override
	public Map<String, Object> getPage(TruckType truckType, int start, int limit) {
		// TODO Auto-generated method stub
		return truckTypeDao.getPage(truckType, start, limit);
	}
	/**
	* 功 能 描 述：根据id查询车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckType
	 */
	@Override
	public TruckType getTruckTypeById(String id) {
		return truckTypeDao.getTruckTypeById(id);
	}
	/**
	* 功 能 描 述：保存车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveTruckType(TruckType truckType) {
		truckTypeDao.saveTruckType(truckType);
	}
	/**
	* 功 能 描 述：修改车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateTruckType(TruckType truckType) {
		truckTypeDao.updateTruckType(truckType);	
	}
	/**
	* 功 能 描 述：检测车辆类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getTruckTypeByName(String name) {
		return truckTypeDao.getTruckTypeByName(name);
	}
	/**
	* 功 能 描 述：检测车辆类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getTruckTypeByName(String name, String truckTypeId) {
		return truckTypeDao.getTruckTypeByName(name, truckTypeId);
	}
	/**
	* 功 能 描 述：删除车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeTruckType(String id) {
		truckTypeDao.removeTruckType(id);
	}
	/**
	* 功 能 描 述：查询所有车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	@Override
	public List<TruckType> getTruckTypeList() {
		return truckTypeDao.getTruckTypeList();
	}
	/**
	* 功能描述：查询所有车辆类型
	* 输入参数:  @param id
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：list
	 */
	@Override
	public List<Map<String, Object>> getTruckTypeListForMap(){
		return truckTypeDao.getTruckTypeListWithMap();
	}
}
