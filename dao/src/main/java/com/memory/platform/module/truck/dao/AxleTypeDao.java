package com.memory.platform.module.truck.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.AxleType;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：拖挂轮轴类型DAO层
* 版    本    号：  V1.0
*/
@Repository
public class AxleTypeDao extends BaseDao<AxleType> {
	/**
	 * 
	* 功 能 描 述：查询所有轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<AxleType> getAxleTypeList(){
		String hql = " from AxleType";
		return this.getListByHql(hql);
	}

	/**
	 *
	* 功 能 描 述：通过ID获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：AxleType
	 */
	public AxleType getAxleTypeById(String id) {
		return this.getObjectById(AxleType.class,id);
	}
	
	/**
	 * 
	* 功 能 描 述：分页查询拖挂轮轴类型列表
	* 输 入 参 数： @param AxleType
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
	public Map<String, Object> getPage(AxleType axleType, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from AxleType axleType where 1=1 ";
		if(axleType.getSearch() != null && !"".equals(axleType.getSearch())){
			hql += " and (axleType.name like :name or axleType.first_letter=:first_letter)"; 
			map.put("name", "%"+axleType.getSearch()+"%");
			map.put("first_letter", axleType.getSearch());
		}
		hql += " order by axleType.first_letter";
		return this.getPage(hql, map, start, limit);
	}
	
	/**
	 * 
	* 功 能 描 述：保存类型
	* 输 入 参 数： @param AxleType
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:15:13
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveAxleType(AxleType axleType) {
		axleType.setCreate_time(new Date());
		this.save(axleType);
	}
	
	/**
	 * 
	* 功 能 描 述：更新类型
	* 输 入 参 数： @param AxleType
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:16:35
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateAxleType(AxleType axleType) {
		this.update(axleType);
	}
	
	/**
	 * 
	* 功 能 描 述：判断同名类型是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:18:11
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	public boolean getAxleTypeByName(String name) {
		Map<String, Object> resMap = new HashMap<String,Object>();
		String sql = "SELECT COUNT(*) FROM bas_axle_type as axleType WHERE axleType.name=:name";
		resMap.put("name", name);
		return this.getCountSqlIs(sql, resMap);
	}
	
	
	/**
	 * 
	* 功 能 描 述：通过类型名称和ID查询，判断数据库中是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @param AxleType
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:19:51
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean--存在返回false，不存在返回true
	 */
	public boolean getAxleTypeByName(String name, String axleTypeId) {
		String hql = " from AxleType axleType where axleType.name=:name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if (null != axleTypeId) {
			hql += " and axleType.id != :axleTypeId";
			map.put("axleTypeId", axleTypeId);
		}
		return this.getListByHql(hql, map).size()>0?false:true;
	}
	
	/**
	 * 
	* 功 能 描 述：删除类型
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeAxleType(String id){
		AxleType  axleType = new AxleType();
		axleType.setId(id);
		this.delete(axleType);
	}
	/**
	 * 
	* 功 能 描 述：查询所有轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<Map<String, Object>> getAxleTypeListForMap(){
		String sql = "select id,name,first_letter from bas_axle_type order by first_letter";
		return this.getListBySQLMap(sql, new HashMap<String, Object>());
	}
}
