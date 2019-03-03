package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.LgisticsCompanies;
 



public interface ILgisticsCompaniesService {
	
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
	public List<LgisticsCompanies> getLgisticsCompanies();
	
	/**
	 * 根据物流公司编号获取物流信息
	 * @param lgisticsCode
	 * @return
	 */
	public LgisticsCompanies getLgisticsCompanies(String lgisticsCode);

	List<Map<String, Object>> getLgisticsCompaniesForMap();
}
