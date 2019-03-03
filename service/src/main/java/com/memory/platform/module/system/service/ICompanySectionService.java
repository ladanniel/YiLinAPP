package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.CompanySection;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年4月27日 上午10:39:08 
* 修 改 人： 
* 日   期： 
* 描   述： 组织机构业务接口
* 版 本 号：  V1.0
 */
public interface ICompanySectionService {

	/**
	 * 
	* 功能描述： 取得所有组织机构
	* 输入参数:  @param companyId 商户ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午12:31:07
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	List<CompanySection> getList(String companyId);

	/**
	 * 
	* 功能描述：  保存组织机构对象
	* 输入参数:  @param companySection
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午4:05:03
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String, Object>
	 */
	Map<String, Object> saveCompanySection(CompanySection companySection);

	/**
	 * 
	* 功能描述： 查询出不包含此ID的组织机构
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午8:07:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	List<CompanySection> getListLikeNotById(String id);

	/**
	 * 
	* 功能描述： 根据ID查询组织机构
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午8:34:24
	* 修 改 人: 
	* 日    期: 
	* 返    回：CompanySection
	 */
	CompanySection getCompanySection(String id);

	/**
	 * 
	* 功能描述： 更新组织机构
	* 输入参数:  @param companySection
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午8:35:34
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	Map<String, Object> updateCompanySection(CompanySection companySection);

	/**
	 * 
	* 功能描述： 删除组织机构代码
	* 输入参数:  @param id
	* 输入参数:  @param i
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月27日下午8:55:23
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	Map<String, Object> deleteCompanySection(String id, String companyId);

	/**
	 * 
	* 功能描述： 根据登陆用户取得组织机构
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年4月29日上午10:51:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：CompanySection
	 */
	CompanySection getCompanySectionByCompanyId(String id);
	
	/**
	* 功能描述： 获取商户下组织机构
	* 输入参数:  @param companyId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月30日下午12:29:18
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	public List<CompanySection> getListByCompany(String companyId);

	/**
	 * 
	* 功能描述： 查找顶级组织机构
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: Aug 27, 201611:00:30 PM
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	List<CompanySection> getParentListByCompany(String companyId);
	
	/**
	 * 根据companyId获取所有组织机构
	 * */
	
	List<Map<String,Object>> getCompanySectionByCompanyIdForMap(String companyId);
}
