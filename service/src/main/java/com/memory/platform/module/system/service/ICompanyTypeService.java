package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.CompanyType;

/**
 * 
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月23日 下午4:04:42 
* 修 改 人： 
* 日   期： 
* 描   述： 商户类型业务接口
* 版 本 号：  V1.0
 */
public interface ICompanyTypeService {
	
	/** 
	* 功能描述： 通过ID查询商户类型
	* 输入参数:  @param id 商户类型ID 
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月23日下午4:04:38
	* 修 改 人: 
	* 日    期: 
	* 返    回：CompanyType
	 */
	CompanyType getCompanyTypeById(String id);
	
	
	/**
	 * 
	* 功能描述： 修改商户类型数据
	* 输入参数:  @param CompanyType 商户类型实体对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月23日下午4:05:43
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateCompanyType(CompanyType CompanyType);
	
	
	
	/**
	 * 
	* 功能描述： 分页查询商户类型数据
	* 输入参数:  @param CompanyType //商户类型对象
	* 输入参数:  @param start //起始页数
	* 输入参数:  @param limit //最后页数
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月23日下午4:06:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(CompanyType CompanyType,int start, int limit);
	
	/**
	 * 
	* 功能描述： 保存商户类型数据
	* 输入参数:  @param CompanyType 商户类型对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月23日下午4:07:37
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveCompanyType(CompanyType CompanyType);
	
	/**
	 * 
	 
	* 功能描述： 删除商户类型数据
	* 输入参数:  @param id //商户类型数据ID
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月23日下午4:07:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void removeCompanyType(String id); 
	
    /**
     * 
     
    * 功能描述： 查询所有的商户类型数据
    * 输入参数:  @return
    * 异    常： 
    * 创 建 人: yangjiaqiao
    * 日    期: 2016年4月23日下午4:08:31
    * 修 改 人: 
    * 日    期: 
    * 返    回：List<CompanyType>
     */
	List<CompanyType> getCompanyTypeList();
	
	/**
	 * 
	 
	* 功能描述： 通过名称查询该商户类型名称是否存在
	* 输入参数:  @param name
	* 输入参数:  @param companyTypeId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月23日下午4:08:51
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	boolean getCompanyTypeByName(String name,String companyTypeId);
	
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
	List<CompanyType> getCompanyTypeIsRegisterList();
	
}
