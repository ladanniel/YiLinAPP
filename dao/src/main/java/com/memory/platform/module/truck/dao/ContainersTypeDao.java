package com.memory.platform.module.truck.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.ContainersType;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：货箱形式DAO层
* 版    本    号：  V1.0
*/
@Repository
public class ContainersTypeDao extends BaseDao<ContainersType> {
	
	
	/**
	 * 
	* 功 能 描 述：查询所有货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<ContainersType> getContainersTypeList(){
		String hql = " from ContainersType";
		return this.getListByHql(hql);
	}

	/**
	 *
	* 功 能 描 述：通过货箱形式ID获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：ContainersType
	 */
	public ContainersType getContainersTypeById(String id) {
		return this.getObjectById(ContainersType.class,id);
	}
	
	/**
	 * 
	* 功 能 描 述：分页查询货箱形式列表
	* 输 入 参 数： @param ContainersType
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
	public Map<String, Object> getPage(ContainersType containersType, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from ContainersType containersType where 1=1 ";
		if(containersType.getSearch() != null && !"".equals(containersType.getSearch())){
			hql += " and containersType.name like :name"; 
			map.put("name", "%"+containersType.getSearch()+"%");
		}
		hql += " order by containersType.name";
		return this.getPage(hql, map, start, limit);
	}
	
	/**
	 * 
	* 功 能 描 述：保存货箱形式
	* 输 入 参 数： @param BearingNum
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:15:13
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveContainersType(ContainersType containersType) {
		containersType.setCreate_time(new Date());
		this.save(containersType);
	}
	
	/**
	 * 
	* 功 能 描 述：更新货箱形式
	* 输 入 参 数： @param ContainersType
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:16:35
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateContainersType(ContainersType containersType) {
		this.update(containersType);
	}
	
	/**
	 * 
	* 功 能 描 述：判断货箱形式名称是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:18:11
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	public boolean getContainersTypeByName(String name) {
		Map<String, Object> resMap = new HashMap<String,Object>();
		String sql = "SELECT COUNT(*) FROM bas_containers_type as containersType WHERE containersType.name=:name";
		resMap.put("name", name);
		return this.getCountSqlIs(sql, resMap);
	}
	
	
	/**
	 * 
	* 功 能 描 述：通过货箱形式名称和ID查询，判断数据库中是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @param ContainersType
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:19:51
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean--存在返回false，不存在返回true
	 */
	public boolean getContainersTypeByName(String name, String containersTypeId) {
		String hql = " from ContainersType containersType where containersType.name=:name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if (null != containersTypeId) {
			hql += " and containersType.id != :containersTypeId";
			map.put("containersTypeId", containersTypeId);
		}
		return this.getListByHql(hql, map).size()>0?false:true;
	}
	
	/**
	 * 
	* 功 能 描 述：删除货箱形式
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeContainersType(String id){
		ContainersType  containersType = new ContainersType();
		containersType.setId(id);
		this.delete(containersType);
	}
	

	/**
	 * 
	* 功 能 描 述：查询所有货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<Map<String, Object>> getContainersTypeListForMap(){
		String sql = "select id,name from bas_containers_type order by name";
		return this.getListBySQLMap(sql, new HashMap<String,Object>());
	}
	
}
