package com.memory.platform.module.truck.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.TruckPlate;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车牌类型DAO层
* 版    本    号：  V1.0
*/
@Repository
public class TruckPlateDao extends BaseDao<TruckPlate> {
	/**
	 * 
	* 功 能 描 述：查询所有车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<TruckPlate> getTruckPlateList(){
		String hql = " from TruckPlate";
		return this.getListByHql(hql);
	}

	/**
	 *
	* 功 能 描 述：通过车牌ID获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckPlate
	 */
	public TruckPlate getTruckPlateById(String id) {
		return this.getObjectById(TruckPlate.class,id);
	}
	
	/**
	 * 
	* 功 能 描 述：分页查询车牌列表
	* 输 入 参 数： @param truckPlate
	* 输 入 参 数： @param start
	* 输 入 参 数： @param limit
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:13:01
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：Map<String,Object>
	 */
	public Map<String, Object> getPage(TruckPlate truckPlate, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from TruckPlate truckPlate where 1=1 ";
		if(truckPlate.getSearch() != null && !"".equals(truckPlate.getSearch())){
			hql += " and truckPlate.name like :name"; 
			map.put("name", "%"+truckPlate.getSearch()+"%");
		}
		hql += " order by truckPlate.name";
		return this.getPage(hql, map, start, limit);
	}
	
	/**
	 * 
	* 功 能 描 述：保存车牌
	* 输 入 参 数： @param truckPlate
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:15:13
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckPlate(TruckPlate truckPlate) {
		truckPlate.setCreate_time(new Date());
		this.save(truckPlate);
	}
	
	/**
	 * 
	* 功 能 描 述：更新车牌
	* 输 入 参 数： @param truckPlate
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:16:35
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckPlate(TruckPlate truckPlate) {
		this.update(truckPlate);
	}
	
	/**
	 * 
	* 功 能 描 述：判断同名车辆品牌是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:18:11
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	public boolean getTruckPlateByName(String name) {
		Map<String, Object> resMap = new HashMap<String,Object>();
		String sql = "SELECT COUNT(*) FROM bas_truck_plate as truckPlate WHERE truckPlate.name=:name";
		resMap.put("name", name);
		return this.getCountSqlIs(sql, resMap);
	}
	
	
	/**
	 * 
	* 功 能 描 述：通过车辆品牌名称和ID查询，判断数据库中是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @param truckPlateId
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:19:51
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean--存在返回false，不存在返回true
	 */
	public boolean getTruckPlateByName(String name, String truckPlateId) {
		String hql = " from TruckPlate truckPlate where truckPlate.name=:name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if (null != truckPlateId) {
			hql += " and truckPlate.id != :truckPlateId";
			map.put("truckPlateId", truckPlateId);
		}
		return this.getListByHql(hql, map).size()>0?false:true;
	}
	
	/**
	 * 
	* 功 能 描 述：删除车辆品牌
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeTruckPlate(String id){
		TruckPlate  truckPlate = new TruckPlate();
		truckPlate.setId(id);
		this.delete(truckPlate);
	}
	/**
	 * 
	* 功 能 描 述：查询所有车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<Map<String, Object>> getTruckPlateListWithMap(){
		String sql = "select id,name from bas_truck_plate order by name";
		return this.getListBySQLMap(sql, new HashMap<String, Object>());
	}
}
