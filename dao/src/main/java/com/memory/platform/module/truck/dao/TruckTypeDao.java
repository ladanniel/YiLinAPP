package com.memory.platform.module.truck.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.TruckType;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：田超*
* 日             期： 2016年5月2日 下午2:59:18
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆类型DAO层
* 版    本    号：  V1.0
*/
@Repository
public class TruckTypeDao extends BaseDao<TruckType> {

	/**
	 *
	* 功 能 描 述：通过车辆类型ID获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： 田超
	* 日             期： 2016年5月2日 下午3:00:20
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckType
	 */
	public TruckType getTruckTypeById(String id) {
		return this.getObjectById(TruckType.class,id);
	}
	
	/**
	 * 
	* 功 能 描 述：分页查询车辆类型列表
	* 输 入 参 数： @param truckType
	* 输 入 参 数： @param start
	* 输 入 参 数： @param limit
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： 田超
	* 日             期： 2016年5月2日 下午3:00:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：Map<String,Object>
	 */
	public Map<String, Object> getPage(TruckType truckType, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from TruckType truckType where 1=1 ";
		if(truckType.getSearch() != null && !"".equals(truckType.getSearch())){
			hql += " and truckType.name like :name"; 
			map.put("name", "%"+truckType.getSearch()+"%");
		}
		hql += " order by truckType.name";
		return this.getPage(hql, map, start, limit);
	}
	
	/**
	 * 
	* 功 能 描 述：保存车辆类型
	* 输 入 参 数： @param truckType
	* 异             常：
	* 创    建    人： 田超
	* 日             期： 2016年5月2日 下午3:37:23
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckType(TruckType truckType ) {
		truckType.setAdd_user_id(UserUtil.getUser().getId());
		truckType.setCreate_time(new Date());
		this.save(truckType);
	}
	
	/**
	 * 
	* 功 能 描 述：更新车辆类型
	* 输 入 参 数： @param truckType
	* 异             常：
	* 创    建    人： 田超
	* 日             期： 2016年5月2日 下午3:43:47
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckType(TruckType truckType) {
		//truckType.setUpdate_time(UserUtil.getUser().getUpdate_time());
		//truckType.setUpdate_time(new Date());
		this.merge(truckType);
	}
	
	/**
	 * 
	* 功 能 描 述：判断同名车辆类型是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： 田超
	* 日             期： 2016年5月2日 下午3:37:57
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	public boolean getTruckTypeByName(String name) {
		Map<String, Object> resMap = new HashMap<String,Object>();
		String sql = "SELECT COUNT(*) FROM bas_truck_type as truckType WHERE truckType.name=:name";
		resMap.put("name", name);
		return this.getCountSqlIs(sql, resMap);
	}
	
	
	/**
	 * 
	* 功 能 描 述：通过车辆类型名称和ID查询，判断数据库中是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @param truckTypeId
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： 田超
	* 日             期： 2016年5月2日 下午3:38:15
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean--存在返回false，不存在返回true
	 */
	public boolean getTruckTypeByName(String name, String truckTypeId) {
		String hql = " from TruckType truckType where truckType.name=:name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if (null != truckTypeId) {
			hql += " and truckType.id != :truckTypeId";
			map.put("truckTypeId", truckTypeId);
		}
		return this.getListByHql(hql, map).size()>0?false:true;
	}
	
	/**
	 * 
	* 功 能 描 述：删除车辆类型
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： 田超
	* 日             期： 2016年5月2日 下午10:09:14
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeTruckType(String id){
		TruckType truckType = new TruckType();
		truckType.setId(id);
		this.delete(truckType);
	}
	/**
	 * 
	* 功 能 描 述：查询所有车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<TruckType> getTruckTypeList(){
		String hql = " from TruckType";
		return this.getListByHql(hql);
	}
	
	/**
	 * 
	* 功 能 描 述：查询所有车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<Map<String, Object>> getTruckTypeListWithMap(){
		String sql = "select id,name from bas_truck_type order by name";
		return this.getListBySQLMap(sql,new HashMap<String, Object>());
	}
}
