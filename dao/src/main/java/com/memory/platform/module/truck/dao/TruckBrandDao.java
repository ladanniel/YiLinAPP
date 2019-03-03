package com.memory.platform.module.truck.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.TruckBrand;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：货车品牌DAO层
* 版    本    号：  V1.0
*/
@Repository
public class TruckBrandDao extends BaseDao<TruckBrand> {
	/**
	 * 
	* 功 能 描 述：查询所有车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<TruckBrand> getTruckBrandList(){
		String hql = " from TruckBrand";
		return this.getListByHql(hql);
	}

	/**
	 *
	* 功 能 描 述：通过品牌ID获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckBrand
	 */
	public TruckBrand getTruckBrandById(String id) {
		return this.getObjectById(TruckBrand.class,id);
	}
	
	/**
	 * 
	* 功 能 描 述：分页查询品牌列表
	* 输 入 参 数： @param TruckBrand
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
	public Map<String, Object> getPage(TruckBrand truckBrand, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from TruckBrand truckBrand where 1=1 ";
		if(truckBrand.getSearch() != null && !"".equals(truckBrand.getSearch())){
			hql += " and (truckBrand.name like :name or truckBrand.first_letter=:first_letter)"; 
			map.put("name", "%"+truckBrand.getSearch()+"%");
			map.put("first_letter", truckBrand.getSearch());
		}
		hql += " order by truckBrand.first_letter";
		return this.getPage(hql, map, start, limit);
	}
	
	
	/**
	 * 
	* 功 能 描 述：保存品牌
	* 输 入 参 数： @param TruckBrand
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:15:13
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckBrand(TruckBrand truckBrand) {
		truckBrand.setCreate_time(new Date());
		this.save(truckBrand);
	}
	
	/**
	 * 
	* 功 能 描 述：更新品牌
	* 输 入 参 数： @param truckBrand
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:16:35
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckBrand(TruckBrand truckBrand) {
		this.update(truckBrand);
	}
	
	/**
	 * 
	* 功 能 描 述：判断同名品牌是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:18:11
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	public boolean getTruckBrandByName(String name) {
		Map<String, Object> resMap = new HashMap<String,Object>();
		String sql = "SELECT COUNT(*) FROM bas_truck_brand as truckBrand WHERE truckBrand.name=:name";
		resMap.put("name", name);
		return this.getCountSqlIs(sql, resMap);
	}
	
	
	/**
	 * 
	* 功 能 描 述：通过品牌名称和ID查询，判断数据库中是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @param TruckBrand
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:19:51
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean--存在返回false，不存在返回true
	 */
	public boolean getTruckBrandByName(String name, String truckBrandId) {
		String hql = " from TruckBrand truckBrand where truckBrand.name=:name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if (null != truckBrandId) {
			hql += " and truckBrand.id != :truckBrandId";
			map.put("truckBrandId", truckBrandId);
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
	public void removeTruckBrand(String id){
		TruckBrand  truckBrand = new TruckBrand();
		truckBrand.setId(id);
		this.delete(truckBrand);
	}
	
	
	/**
	 * 
	* 功 能 描 述：查询所有车辆品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<Map<String, Object>> getTruckBrandListWithMap(){
		String sql = "select id,name,first_letter from bas_truck_brand order by first_letter";
		return this.getListBySQLMap(sql, new HashMap<String, Object>());
	}
	
}
