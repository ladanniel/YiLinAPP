package com.memory.platform.module.system.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年4月27日 上午10:42:18 
* 修 改 人： 
* 日   期： 
* 描   述： 组织机构DAO类
* 版 本 号：  V1.0
 */
@Repository
public class CompanySectionDao extends BaseDao<CompanySection> {

	/**
	 * 
	* 功能描述： 
	* 输入参数:  @param companyId 商户ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午12:33:51
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public List<CompanySection> getList(String companyId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyId",companyId);
		return this.getListByHql(" from CompanySection where company_id = :companyId order by order_path,create_time asc ", map);
	//	return this.getListByHql(" from CompanySection where company_id = :companyId order by order_path,create_time asc ", map);
		
	}

	/**
	 * 
	* 功能描述： 查询出不包含此ID的组织机构
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午8:09:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	public List<CompanySection> getListLikeNotById(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",id);
		return this.getListByHql(" from CompanySection where parent_id = :id order by order_path,create_time asc ", map);
	}

	/**
	 * 
	* 功能描述： 根据companyId查询用户 
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午10:08:04
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Account>
	 */
	@SuppressWarnings("unchecked")
	public List<Account> getListAccount(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",id);
		return getSession().createQuery(" from Account where company_section_id = :id  ")
				.setProperties(map)
				.list();
	}

	/**
	 * 
	* 功能描述： 根据用户ID取得
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月29日上午10:55:51
	* 修 改 人: 
	* 日    期: 
	* 返    回：CompanySection
	 */
	public CompanySection getCompanySectionByCompanyId(String id) {
		if(id == null || "".equals(id))
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", id);
		List<CompanySection> list = this.getListByHql(" from CompanySection where company_id=:companyId", map);
		return list.size()>0?list.get(0):null;
	}

	/**
	* 功能描述： 获取商户下组织机构
	* 输入参数:  @param companyId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月30日下午12:27:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	public List<CompanySection> getListByCompany(String companyId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		return this.getListByHql(" from CompanySection where company_id=:companyId order by order_path", map);
	}

	/**
	* 功能描述： 获取所有子集id
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月3日上午10:58:44
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<String>
	 */
	public List<String> getIds(String id){
		List<String> list = new ArrayList<String>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", "%"+id+"%");
		List<CompanySection> lust = this.getListByHql(" from CompanySection where order_path like :id", map);
		for (CompanySection companySection : lust) {
			list.add(companySection.getId());
		}
		return list;
	}

	/**
	 * 
	* 功能描述： 获取顶级的组织机构
	* 输入参数:  @param companyId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: Aug 27, 201611:02:13 PM
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	public List<CompanySection> getParentListByCompany(String companyId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		return this.getListByHql(" from CompanySection where leav=1 and company_id=:companyId order by order_path", map);
	}
	 
	/**
	 * 
	* 功能描述： 获取顶级的组织机构
	* 输入参数:  @param companyId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: Aug 27, 201611:02:13 PM
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	public List<Map<String,Object>> getCompanySectionByCompanyIdForMap(String companyId) {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql=" select section.id,section.name,section.parent_id,section.parent_name,section.company_id,"
				+ "section.descs from sys_company_section section where section.company_id = :companyId ";
		map.put("companyId", companyId);
		return this.getListBySQLMap(sql, map);
	}
}
