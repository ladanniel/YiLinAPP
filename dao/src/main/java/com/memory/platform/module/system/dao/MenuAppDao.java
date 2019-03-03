package com.memory.platform.module.system.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.MenuApp;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class MenuAppDao extends BaseDao<MenuApp> {
	
	/**
	* 功能描述： 通过资源ID,查询APP资源信息
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:54:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：Resource
	 */
	public MenuApp getResource(String id){
		return this.getObjectById(MenuApp.class, id);
	}
	

	/**
	* 功能描述： APP资源信息分页查询
	* 输入参数:  @param menuApp APP资源对象
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
	public Map<String, Object> getPage(MenuApp menuApp,int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from MenuApp menuApp ";
		if(null != menuApp.getSearch() && !"".equals(menuApp.getSearch())){
			hql += " where menuApp.name like :name";
			map.put("name",menuApp.getSearch() + "%");
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
	public void saveMenuApp(MenuApp menuApp){
		this.save(menuApp);
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
	public void updateMenuApp(MenuApp menuApp){
		this.merge(menuApp);
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
	public void removeMenuApp(String id){
		MenuApp menuApp = new MenuApp();
		menuApp.setId(id);
		this.delete(menuApp);
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
	public List<MenuApp> getMenuAppList(){
		String hql = " from MenuApp menuApp order by menuApp.create_time";
		return this.getListByHql(hql);
	}
	
	/**
	* 功能描述： 通过角色ID查询关联角色的所有APP资源ID
	* 输入参数:  @param roleId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月18日下午4:51:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	public String getRoleMenuAppIds(String roleId){
		String sql = " SELECT GROUP_CONCAT(a.menu_app_id separator ',') as menu_app_ids  FROM sys_role_menu_app as a WHERE a.role_id = :roleId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		List<String>  list = this.getListBySql(sql,map);
		return list.get(0);
	}
	
	
	
}
