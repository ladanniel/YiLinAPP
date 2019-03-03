package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.Menu;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月27日 下午4:31:00 
* 修 改 人： 
* 日   期： 
* 描   述： 菜单业务层接口
* 版 本 号：  V1.0
 */
public interface IMenuService {
	
	/**
	* 功能描述： 通过菜单ID查询菜单信息
	* 输入参数:  @param menuId 菜单ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:31:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：Menu
	 */
	Menu getMenu(String menuId);
	
	/**
	* 功能描述： 通过PID 查询该菜单下的子菜单
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:33:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Menu>
	 */
	List<Menu> getMenuListByPid(String menuId);
	
	/**
	* 功能描述： 通过用户ID、菜单ID
	* 输入参数:  @param userId 用户ID
	* 输入参数:  @param menuId 菜单ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:33:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Menu>
	 */
	List<Menu> getUserMenuList(String userId, String menuId);
	
	/**
	* 功能描述： 保存菜单信息
	* 输入参数:  @param menu 菜单对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:34:41
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveMenu(Menu menu);
	
	/**
	* 功能描述： 删除菜单信息
	* 输入参数:  @param menuId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:34:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void removeMenu(String menuId);
	
	/**
	* 功能描述： 修改菜单信息 
	* 输入参数:  @param menu 菜单对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:35:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateMenu(Menu menu);
	
	/**
	* 功能描述： 查询所有菜单信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:35:36
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Menu>
	 */
	List<Menu> getMenuList();
	
	
	
	/**
	* 功能描述： 通过角色ID,查询该角色已经拥有的菜单ID
	* 输入参数:  @param roleId 角色ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:37:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	List<Map<String, Object>> getMenuListByRoleId(String roleId);
	
	
	/**
	* 功能描述： 查询所有角色关联的菜单信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月18日上午11:35:38
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	List<Map<String, Object>> getMenuOrCode();
}
