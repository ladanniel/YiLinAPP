package com.memory.platform.module.truck.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.BearingNum;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆轴数DAO层
* 版    本    号：  V1.0
*/
@Repository
public class BearingNumDao extends BaseDao<BearingNum> {
	
	/**
	 * 
	* 功 能 描 述：查询所有轴数类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<BearingNum> getBearingNumList(){
		String hql = " from BearingNum";
		return this.getListByHql(hql);
	}

	/**
	 *
	* 功 能 描 述：通过轴数ID获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：BearingNum
	 */
	public BearingNum getBearingNumById(String id) {
		return this.getObjectById(BearingNum.class,id);
	}
	
	/**
	 * 
	* 功 能 描 述：分页查询轴数列表
	* 输 入 参 数： @param BearingNum
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
	public Map<String, Object> getPage(BearingNum bearingNum, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from BearingNum bearingNum where 1=1 ";
		if(bearingNum.getSearch() != null && !"".equals(bearingNum.getSearch())){
			hql += " and bearingNum.name like :name"; 
			map.put("name", "%"+bearingNum.getSearch()+"%");
		}
		hql += " order by bearingNum.name";
		return this.getPage(hql, map, start, limit);
	}
	
	/**
	 * 
	* 功 能 描 述：保存车辆轴数
	* 输 入 参 数： @param BearingNum
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:15:13
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveBearingNum(BearingNum bearingNum) {
		bearingNum.setCreate_time(new Date());
		this.save(bearingNum);
	}
	
	/**
	 * 
	* 功 能 描 述：更新车辆轴数
	* 输 入 参 数： @param BearingNum
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:16:35
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateBearingNum(BearingNum bearingNum) {
		this.update(bearingNum);
	}
	
	/**
	 * 
	* 功 能 描 述：判断同名轴数名称是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:18:11
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	public boolean getBearingNumByName(String name) {
		Map<String, Object> resMap = new HashMap<String,Object>();
		String sql = "SELECT COUNT(*) FROM bas_ bearing_num as bearingNum WHERE bearingNum.name=:name";
		resMap.put("name", name);
		return this.getCountSqlIs(sql, resMap);
	}
	
	
	/**
	 * 
	* 功 能 描 述：通过车辆轴数名称和ID查询，判断数据库中是否存在
	* 输 入 参 数： @param name
	* 输 入 参 数： @param BearingNum
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:19:51
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean--存在返回false，不存在返回true
	 */
	public boolean getBearingNumByName(String name, String bearingNumId) {
		String hql = " from BearingNum bearingNum where bearingNum.name=:name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if (null != bearingNumId) {
			hql += " and bearingNum.id != :bearingNumId";
			map.put("bearingNumId", bearingNumId);
		}
		return this.getListByHql(hql, map).size()>0?false:true;
	}
	
	/**
	 * 
	* 功 能 描 述：删除车辆轴数
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeBearingNum(String id){
		BearingNum  bearingNum = new BearingNum();
		bearingNum.setId(id);
		this.delete(bearingNum);
	}
	
	/**
	 * 
	* 功 能 描 述：查询所有轴数类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	public List<Map<String, Object>> getBearingNumListForMap(){
		String sql = "select id,name from bas_bearing_num order by name";
		return this.getListBySQLMap(sql, new HashMap<String, Object>());
	}
	
}
