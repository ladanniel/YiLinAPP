package com.memory.platform.module.system.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.LgisticsCompanies;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class LgisticsCompaniesDao extends BaseDao< LgisticsCompanies> {
	
	/**
	* 功能描述： 查询所有快递公司信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:57:21
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Resource>
	 */
	public List<LgisticsCompanies> getLgisticsCompanies(){
		String hql = " from LgisticsCompanies lgistics order by lgistics.index";
		return this.getListByHql(hql);
	}
	
	
	/**
	 * 根据物流公司编号获取物流信息
	 * @param lgisticsCode
	 * @return
	 */
	public LgisticsCompanies getLgisticsCompanies(String lgisticsCode){
		String hql = " from LgisticsCompanies lgistics where lgistics.code=:lgisticsCode";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lgisticsCode", lgisticsCode);
		return this.getObjectByColumn(hql, map);
	}
	
	/**
	* 功能描述： 查询所有快递公司信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:57:21
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Resource>
	 */
	public List<Map<String, Object>> getLgisticsCompaniesForMap(){
		String sql = "SELECT name,code from sys_lgistics_companies order by `index`";
		Map<String, Object> map = new HashMap<String, Object>();
		return this.getListBySQLMap(sql, map);
	}
	
	
}
