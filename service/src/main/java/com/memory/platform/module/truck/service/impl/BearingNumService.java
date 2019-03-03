package com.memory.platform.module.truck.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.BearingNum;
import com.memory.platform.module.truck.dao.BearingNumDao;
import com.memory.platform.module.truck.service.IBearingNumService;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆轴数业务层实现类
* 版    本    号：  V1.0
*/
@Service
public class BearingNumService implements IBearingNumService {
	@Autowired
	private BearingNumDao bearingNumDao;
	/**
	* 功 能 描 述：分页查询车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@Override
	public Map<String, Object> getPage(BearingNum bearingNum, int start, int limit) {
		return bearingNumDao.getPage(bearingNum, start, limit);
	}
	/**
	* 功 能 描 述：根据id查询车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：BearingNum
	 */
	@Override
	public BearingNum getBearingNumById(String id) {
		return bearingNumDao.getBearingNumById(id);
	}
	/**
	* 功 能 描 述：保存车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveBearingNum(BearingNum bearingNum) {
		bearingNumDao.saveBearingNum(bearingNum);

	}
	/**
	* 功 能 描 述：修改车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateBearingNum(BearingNum bearingNum) {
		bearingNumDao.updateBearingNum(bearingNum);
	}
	/**
	* 功 能 描 述：检查车辆轴数是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getBearingNumByName(String name) {
		return bearingNumDao.getBearingNumByName(name);
	}
	/**
	* 功 能 描 述：检查车辆轴数是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getBearingNumByName(String name, String bearingNumId) {
		return bearingNumDao.getBearingNumByName(name, bearingNumId);
	}
	/**
	* 功 能 描 述：删除车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeBearingNum(String id) {
		bearingNumDao.removeBearingNum(id);
	}
	/**
	* 功 能 描 述：查询所有车辆轴数
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	@Override
	public List<BearingNum> getBearingNumList() {
		return bearingNumDao.getBearingNumList();
	}
	@Override
	public List<Map<String, Object>> getBearingNumListForMap() {
		return bearingNumDao.getBearingNumListForMap();
	}
}
