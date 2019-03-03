package com.memory.platform.module.system.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.Menu;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月27日 下午4:39:47 
* 修 改 人： 
* 日   期： 
* 描   述： 菜单DAO层实现类
* 版 本 号：  V1.0
 */
@Repository 
public class MenuDao extends BaseDao<Menu> {
	
	/**
	* 功能描述： 通过菜单ID，查询菜单信息
	* 输入参数:  @param menuId  菜单ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:39:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：Menu
	 */
	public Menu getMenu(String menuId){
		//return this.get(menuId, Menu.class);
		return this.getObjectById(Menu.class, menuId);
	}
	
	/**
	* 功能描述： 查询该菜单下的子菜单
	* 输入参数:  @param menuId 菜单ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:40:12
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Menu>
	 */
	@Cacheable(value="menuCache")
	public List<Menu> getMenuListByPid(String menuId){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Menu m ";
		hql += " where m.parent_id=:parent_id order by m.soft";
		map.put("parent_id", menuId);
		List<Menu> list =  this.getListByHql(hql, map);
		return list;
	}
	
	/**
	* 功能描述： 查询系统里面的所有菜单
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:40:50
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Menu>
	 */
	@Cacheable(value="menuCache")
	public List<Menu> getMenuList(){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Menu m order by m.soft asc";
		List<Menu> list =  this.getListByHql(hql, map);
		return list;
	}
	
	/**
	* 功能描述： 查询该菜单下，是否拥有子菜单
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:41:24
	* 修 改 人: 
	* 日    期: 
	* 返    回：int
	 */
	public int getMenuChildCount(String menuId){
		String sql = "select count(*) from sys_menu where parent_id=:parent_id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_id", menuId);
		return this.getCountSql(sql, map);
	}
	
	
	/**
	* 功能描述： 通过角色ID和菜单ID,查询该角色下的菜单信息
	* 输入参数:  @param roleId
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:42:08
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Menu>
	 */
	@Cacheable(value="menuCache")
	public List<Menu> getUserMenuList(String roleId, String menuId){
		String sql = "select m.* from sys_menu m, sys_resource r";
		sql += " where m.resource_id=r.id and parent_id=:parent_id and type<>4";
		sql += " and m.id in ( SELECT menu_id FROM sys_role_menu where role_id =:roleId) order by m.soft";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_id", menuId);
		map.put("roleId", roleId);
		List<Menu> list = this.getListBySql(sql, map, Menu.class);
		return list;
	}
	
	/**
	* 功能描述： 菜单信息保存
	* 输入参数:  @param menu
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:42:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	@CacheEvict(value="menuCache",allEntries=true)
	public void saveMenu(Menu menu){ 
		this.save(menu);
	}
	
	/**
	* 功能描述： 修改菜单信息
	* 输入参数:  @param menu 菜单对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:43:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	@CacheEvict(value="menuCache",allEntries=true)
	public void updateMenu(Menu menu){
		String sql = "update sys_menu set name=?, parent_id=?,"+
				"soft=?,type=?,resource_id=?,iconcls=? "+
				"where id=?";
		Object[] objects = {menu.getName(),menu.getParent_id(),menu.getSoft(),
		menu.getType(),menu.getResource().getId(),menu.getIconcls(),menu.getId()};
		this.updateSQL(sql, objects);
	}
	
	/**
	* 功能描述： 删除菜单信息
	* 输入参数:  @param menu
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:43:37
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	@CacheEvict(value="menuCache",allEntries=true)
	public void removeMenu(Menu menu) {
		this.delete(menu);
	}
	
 
	
	
	/**’
	* 功能描述： 通过角色ID,查询该角色下，拥有的菜单ID
	* 输入参数:  @param roleId 角色ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:45:26
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getMenuListByRoleId(String roleId){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " SELECT m.menu_id FROM sys_role_menu m WHERE m.role_id = :roleId";
		map.put("roleId", roleId);
		List<Map<String, Object>> list =  this.getListBySQLMap(hql, map);
		return list;
	}
	
	
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
	public List<Map<String, Object>> getMenuOrCode(){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " SELECT GROUP_CONCAT(r.role_code separator ',') role_codes,m.name,res.url FROM "+
	                 " sys_menu m,sys_role r,sys_role_menu rm, sys_resource res "+
	                 " WHERE  res.id = m.resource_id and m.id = rm.menu_id and r.id = rm.role_id GROUP BY res.url"; 
		List<Map<String, Object>> list =  this.getListBySQLMap(sql, map);
		return list;
	}
	
}
