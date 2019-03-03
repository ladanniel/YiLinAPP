package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.MenuApp;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月18日 上午11:10:28 
* 修 改 人： 
* 日   期： 
* 描   述： APP资源业务接口
* 版 本 号：  V1.0
 */
public interface IMenuAppService {
	
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
	MenuApp getMenuApp(String menuAppId);
	 
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
	Map<String, Object> getPage(MenuApp menuApp,int start, int limit);
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
	void saveMenuApp(MenuApp menuApp);
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
	void updateMenuApp(MenuApp menuApp);
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
	void removeMenuApp(String id);
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
	List<MenuApp> getMenuAppList();
	
	/**
	* 功能描述： 通过角色ID查询关联角色的所有APP资源ID
	* 输入参数:  @param roleId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月18日下午4:50:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	String getRoleMenuAppIds(String roleId);
	
}
