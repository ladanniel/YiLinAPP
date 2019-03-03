package com.memory.platform.module.system.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 
* 创 建 人： yangjiaqiao
* 日       期： 2016年4月23日 下午4:10:37 
* 修 改 人： 
* 日       期： 
* 描       述： 商户类型DAO类
* 版 本 号：  V1.0
 */
@Repository
public class CompanyTypeDao extends BaseDao<CompanyType> {
	
	/**
	 * 通过ID查询表信息
	 * @param roleId
	 * @return
	 */
	public CompanyType getCompanyTypeById(String id){
		return this.getObjectById(CompanyType.class, id);
	}

	/**
	 * 类型分页列表
	 * @param start
	 * @param limit
	 * @return
	 */
	public Map<String, Object> getPage(CompanyType companytype, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from CompanyType companytype where 1=1 ";
		if(companytype.getSearch() != null && !"".equals(companytype.getSearch())){
			hql += " and companytype.name like :name"; 
			map.put("name", companytype.getSearch()+"%");
		}
		hql += " order by companytype.name";
		return this.getPage(hql, map, start, limit);
	}
	
	
	/**
	 * 查询所有类型信息，且状态为0的数据
	 * @return
	 */
	
	public List<CompanyType> getCompanyTypeList(){
		String hql = " from CompanyType companytype ";
		return this.getListByHql(hql);
	}
	
	/**
	 * 保存类型信息
	 * @param role
	 */
	public void saveCompanyType(CompanyType companyType){
		companyType.setAdd_user_id(UserUtil.getUser().getId());
		companyType.setCreate_time(new Date());
		this.save(companyType);
	} 
	 
	/**
	 * 修改类型信息
	 * @param role
	 */
	public void updateCompanyType(CompanyType companyType){
		companyType.setUpdate_user_id(UserUtil.getUser().getId());
		companyType.setUpdate_time(new Date());
		this.update(companyType);
	}
	
	/**
	 * 删除类型
	 * @param roleId
	 */
	public void removeCompanyType(String id){
		CompanyType CompanyType = new CompanyType();
		CompanyType.setId(id);
		this.delete(CompanyType); 
	}
	
	
	/**
	 * 判断该名称在数据库是否存在
	 * @param role
	 */
	public boolean getCompanyTypeByName(String name){
		Map<String, Object> resMap = new HashMap<String,Object>();
		String sql = "SELECT COUNT(*) FROM sys_company_type as a WHERE a.name=:name";
		resMap.put("name", name);
		return this.getCountSqlIs(sql, resMap);
	}
	 
	/**
	* 功能描述： 通过名称和ID查询，该名称在数据库中是否存在
	* 输入参数:  @param name
	* 输入参数:  @param companyTypeId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月30日下午12:44:19
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	public boolean getCompanyTypeByName(String name, String companyTypeId) {
		String hql = " from CompanyType companyType where companyType.name=:name ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if(null != companyTypeId){
			hql += " and  companyType.id != :companyTypeId";
			map.put("companyTypeId", companyTypeId);
		} 
		return  this.getListByHql(hql, map).size()>0?false:true;
	}
	
	
	/**
	* 功能描述： 查询商户类型中在注册页面显示的数据
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月30日下午12:42:53
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanyType>
	 */
	public List<CompanyType> getCompanyTypeIsRegisterList(){
		String hql = " from CompanyType companyType where companyType.is_register=:is_register ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("is_register", true);
		return  this.getListByHql(hql, map);
	}
	 
}
