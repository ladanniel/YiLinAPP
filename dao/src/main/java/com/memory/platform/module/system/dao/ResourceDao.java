package com.memory.platform.module.system.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.Resource;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class ResourceDao extends BaseDao<Resource> {
	
	/**
	* 功能描述： 通过资源ID,查询资源信息
	* 输入参数:  @param resourceId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:54:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：Resource
	 */
	public Resource getResource(String resourceId){
		return this.getObjectById(Resource.class, resourceId);
	}
	
	/**
	* 功能描述： 通过URL查询资源信息
	* 输入参数:  @param url
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:55:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：Resource
	 */
	@Cacheable(value="resourceByUrl",key="#url")
	public Resource getResourceByUrl(String url){
		String hql = " from Resource resource where resource.url=:url";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		return this.getObjectByColumn(hql, map);
	}
	
	/**
	* 功能描述： 通过companyTypeId、NAME查询是否存在相同的资源信息
	* 输入参数:  @param name 资源名称
	* 输入参数:  @param companyTypeId  所属类型ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午2:09:32
	* 修 改 人: 
	* 日    期: 
	* 返    回：Resource
	 */
	public boolean getResourceByName(String name,String companyTypeId){
		String hql = " from Resource resource where resource.name=:name and resource.companyType.id=:companyTypeId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("companyTypeId", companyTypeId);
		return  this.getListByHql(hql, map).size()>0?false:true;
	}

	/**
	* 功能描述： 资源信息分页查询
	* 输入参数:  @param resource 资源对象
	* 输入参数:  @param start 起始条数
	* 输入参数:  @param limit 截至条数
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:55:51
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(Resource resource,int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Resource resource ";
		if(null != resource.getSearch() && !"".equals(resource.getSearch())){
			hql += " where resource.name like :name";
			map.put("name",resource.getSearch() + "%");
		}
		return this.getPage(hql, map, start, limit);
	}
	
	/**
	* 功能描述： 保存资源信息
	* 输入参数:  @param resource
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:56:23
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void saveResource(Resource resource){
		this.save(resource);
	}
	
	 
	
	/**
	* 功能描述： 修改资源信息
	* 输入参数:  @param resource
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:56:36
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void updateResource(Resource resource){
		this.update(resource);
	}
	
	/**
	* 功能描述： 删除资源信息
	* 输入参数:  @param resourceId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:57:08
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void removeResource(String resourceId){
		Resource resource = new Resource();
		resource.setId(resourceId);
		this.delete(resource);
	}
	
	/**
	* 功能描述： 查询所有资源信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:57:21
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Resource>
	 */
	public List<Resource> getResourceList(){
		String hql = " from Resource resource order by resource.url";
		return this.getListByHql(hql);
	}
	
}
