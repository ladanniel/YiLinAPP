package com.memory.platform.module.system.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.memory.platform.module.global.dao.BaseDao;

@Repository 
public class PersonnelAreaDao extends BaseDao<Object>  {
	
	/**
	 * 查询所有区域信息
	 * @return
	 */
	@Cacheable(value="areaCache")
	public List<Map<String, Object>> getAreaList(){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " SELECT a.id,a.areaid,a.areaname,a.parent_id FROM personnel_area a";
		return this.getListBySQLMap(sql, map);
	}
}
