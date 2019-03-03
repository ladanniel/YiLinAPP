package com.memory.platform.module.system.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.base.Area;
import com.memory.platform.module.global.dao.BaseDao;

@Repository 
public class AreaDao extends BaseDao<Area> {
	
	
	/**
	* 功能描述： 通过区域ID，查询区域ID的xia
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月24日上午11:54:34
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	public String getAreaParentIds(String id){
		String areaIds = "";
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " SELECT GROUP_CONCAT(a.id separator ',') areaIds FROM bas_area a WHERE a.parent_id = :id";
		map.put("id", id);
		List<Map<String, Object>> list =  this.getListBySQLMap(sql, map);
		if(list.size() > 0){
			if(!StringUtils.isEmpty(list.get(0).get("aresIds").toString())){
				areaIds = list.get(0).get("aresIds").toString();
			}
		}
		return areaIds;
	}
	@Cacheable(value="areaCache")
	public List<Area> getAll() {
		String hql = "from Area";
		return  super.getListByHql(hql);
	}
	
}
