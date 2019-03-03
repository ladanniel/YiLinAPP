package com.memory.platform.module.truck.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.module.truck.dao.TruckContainerDao;
import com.memory.platform.module.truck.service.ITruckContainerService;
import com.memory.platform.entity.truck.TruckContainer;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：货箱属性业务层实现类
* 版    本    号：  V1.0
*/
@Service
public class TruckContainerService implements ITruckContainerService {
	@Autowired
	private TruckContainerDao truckContainerDao;
	/**
	* 功 能 描 述：根据车辆id查询货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckContainer
	 */
	@Override
	public TruckContainer checkTruckContainerByTruckno(String no) {
		return truckContainerDao.checkTruckContainerByTruckno(no);
	}
	/**
	* 功 能 描 述：根据id查询货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckContainer
	 */
	@Override
	public TruckContainer getTruckContainerById(String id) {
		return truckContainerDao.getTruckContainerById(id);
	}
	/**
	* 功 能 描 述：保存货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveTruckContainer(TruckContainer truckContainer) {
		truckContainerDao.saveTruckContainer(truckContainer);
	}
	/**
	* 功 能 描 述：修改货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateTruckContainer(TruckContainer truckContainer) {
		truckContainerDao.updateTruckContainer(truckContainer);
	}
	/**
	* 功 能 描 述：删除货箱属性
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeTruckContainer(String id) {
		truckContainerDao.removeTruckContainer(id);
	}

	@Override
	public List<Map<String, Object>> checkTruckContainerByTrucknoWithMap(String truckid){
		return truckContainerDao.checkTruckContainerByTrucknoWithMap(truckid);
	}

}
