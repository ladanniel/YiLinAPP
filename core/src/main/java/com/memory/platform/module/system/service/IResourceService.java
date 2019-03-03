package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.Resource;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月27日 下午2:04:19 
* 修 改 人： 
* 日   期： 
* 描   述： 资源业务层接口
* 版 本 号：  V1.0
 */
public interface IResourceService {
	
	/**
	* 功能描述： 通过资源ID查询资源信息
	* 输入参数:  @param resourceId 资源ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:04:39
	* 修 改 人: 
	* 日    期: 
	* 返    回：Resource
	 */
	Resource getResource(String resourceId);
	/**
	* 功能描述： 通过连接地址查询资源信息
	* 输入参数:  @param url  资源地址
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:05:05
	* 修 改 人: 
	* 日    期: 
	* 返    回：Resource
	 */
	Resource getResourceByUrl(String url); 
	
	/**
	* 功能描述：  通过NAME查询是否存在相同的资源信息
	* 输入参数:  @param name 资源名称
	* 输入参数:  @param companyTypeId 所属类型ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:08:04
	* 修 改 人: 
	* 日    期: 
	* 返    回：Resource
	 */
	boolean getResourceByName(String name,String companyTypeId); 
	/**
	* 功能描述： 分页查询资源信息
	* 输入参数:  @param resource  资源对象
	* 输入参数:  @param start 起始条数
	* 输入参数:  @param limit 截至条数
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:05:38
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(Resource resource,int start, int limit);
	/**
	* 功能描述：  保存资源信息
	* 输入参数:  @param resource 资源对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:06:17
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveResource(Resource resource);
	/**
	* 功能描述： 修改资源信息
	* 输入参数:  @param resource
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:06:41
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateResource(Resource resource);
	/**
	* 功能描述： 删除资源信息
	* 输入参数:  @param resourceId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:06:54
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void removeResource(String resourceId);
	/**
	* 功能描述： 查询所有资源信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:07:06
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Resource>
	 */
	List<Resource> getResourceList();
	
}
