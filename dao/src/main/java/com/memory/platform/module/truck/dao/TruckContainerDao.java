package com.memory.platform.module.truck.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.TruckContainer;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆货箱属性DAO层
* 版    本    号：  V1.0
*/
@Repository
public class TruckContainerDao extends BaseDao<TruckContainer> {

	
	
	/**
	 *
	* 功 能 描 述：通过货箱属性id号获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckContainer
	 */
	public TruckContainer getTruckContainerById(String id) {
		return this.getObjectById(TruckContainer.class,id);
	}
	/**
	 *
	* 功 能 描 述：通过车辆ID获取货箱属性
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckContainer
	 */
	public TruckContainer checkTruckContainerByTruckno(String no) {
		String sql = " from TruckContainer truckContainer where truckContainer.truck.id=:id";
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("id", no);
		TruckContainer truckContainer=this.getObjectByColumn(sql, map);
		return truckContainer;
	}
	
	/**
	 * 
	* 功 能 描 述：保存车辆货箱属性
	* 输 入 参 数： @param TruckContainer
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:15:13
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckContainer(TruckContainer truckContainer) {
		this.save(truckContainer);
	}
	
	/**
	 * 
	* 功 能 描 述：更新车辆货箱属性
	* 输 入 参 数： @param TruckContainer
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:16:35
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckContainer(TruckContainer truckContainer) {
		this.update(truckContainer);
	}
	
	
	
	
	/**
	 * 
	* 功 能 描 述：删除车辆货箱属性
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeTruckContainer(String id){
		TruckContainer  truckContainer = new TruckContainer();
		truckContainer.setId(id);
		this.delete(truckContainer);
	}
	
	/**
	 *
	* 功 能 描 述：通过车辆ID获取货箱属性
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckContainer
	 */
	public List<Map<String, Object>> checkTruckContainerByTrucknoWithMap(String truckid) {
		String sql = "select a.id,b.id as truckid,b.track_no,c.id as containers_type_id,c.name as containerstype,a.volume,a.container_no,a.containers_long from sys_track_container as a,sys_track as b,bas_containers_type as c where a.track_id=b.id and a.containers_type_id=c.id and a.track_id=:truckid";
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("truckid", truckid);
		return this.getListBySQLMap(sql, map);
	}
}
