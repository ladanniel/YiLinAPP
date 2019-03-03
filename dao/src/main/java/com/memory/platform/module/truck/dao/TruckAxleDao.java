package com.memory.platform.module.truck.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.TruckAxle;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆轮轴属性DAO层
* 版    本    号：  V1.0
*/
@Repository
public class TruckAxleDao extends BaseDao<TruckAxle> {

	
	
	/**
	 *
	* 功 能 描 述：通过轮轴属性id号获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckAxle
	 */
	public TruckAxle getTruckAxleById(String id) {
		return this.getObjectById(TruckAxle.class,id);
	}
	/**
	 *
	* 功 能 描 述：通过车辆ID获取轮轴属性
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckAxle
	 */
	public TruckAxle checkTruckAxleByTruckno(String no) {
		String sql = " from TruckAxle truckAxle where truckAxle.truck.id=:id";
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("id", no);
		TruckAxle truckAxle=this.getObjectByColumn(sql, map);
		return truckAxle;
	}
	
	/**
	 * 
	* 功 能 描 述：保存车辆轮轴属性
	* 输 入 参 数： @param TruckAxle
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:15:13
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckAxle(TruckAxle truckAxle) {
		this.save(truckAxle);
	}
	
	/**
	 * 
	* 功 能 描 述：更新车辆轮轴属性
	* 输 入 参 数： @param TruckAxle
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:16:35
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckAxle(TruckAxle truckAxle) {
		this.update(truckAxle);
	}
	
	
	
	
	/**
	 * 
	* 功 能 描 述：删除车辆轮轴属性
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeTruckAxle(String id){
		TruckAxle  truckAxle = new TruckAxle();
		truckAxle.setId(id);
		this.delete(truckAxle);
	}
	/**
	 *
	* 功 能 描 述：通过车辆ID获取轮轴属性
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckAxle
	 */
	public List<Map<String, Object>> checkTruckAxleByTrucknoWithMap(String truckid) {
		String sql="select a.id,b.id as truckid,b.track_no,c.id as axletypeid,c.name as axletype,d.id as bearingnumid,d.name as bearingnum from sys_track_axle as a,sys_track as b,bas_axle_type as c,bas_bearing_num as d where a.track_id=b.id and a.axle_type_id=c.id and a.bearing_num_id=d.id and a.track_id=:truckid";
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("truckid", truckid);
		return this.getListBySQLMap(sql, map);
	}
	
}
