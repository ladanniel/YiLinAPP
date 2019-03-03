package com.memory.platform.module.system.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月27日 下午5:02:44 
* 修 改 人： 
* 日   期： 
* 描   述： 角色DAO实现类
* 版 本 号：  V1.0
 */
@Repository
public class RoleDao extends BaseDao<Role> {
	
	/**
	* 功能描述： 通过角色ID,查询角色信息
	* 输入参数:  @param roleId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:03:01
	* 修 改 人: 
	* 日    期: 
	* 返    回：Role
	 */
	public Role getRole(String roleId){
		return this.getObjectById(Role.class, roleId);
	}

	/**
	* 功能描述： 角色信息分页查询
	* 输入参数:  @param start 起始条数
	* 输入参数:  @param limit 截至条数
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:03:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(Role role,int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Role role";
		if(null != role.getSearch() && !"".equals(role.getSearch())){
			hql += " where role.name like :name";
			map.put("name",role.getSearch() + "%");
		}
		return this.getPage(hql, map, start, limit);
	}
	
	
	/**
	* 功能描述： 查询所有角色信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:04:04
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Role>
	 */
	@Cacheable(value="roleCache")
	public List<Role> getRoleInfo(){
		String hql = " from Role";
		return this.getListByHql(hql);
	}
	
	/**
	* 功能描述： 通过角色IDS，得到所有角色的名称
	* 输入参数:  @param roleIds 角色ID数组
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:04:23
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@SuppressWarnings("unchecked")
	public String getRoleInfoNames(String[] roleIds){
		String hql = " from Role a where a.id in (:ids)";
		List<Role> list = getSession().createQuery(hql).setParameterList("ids", roleIds).list();
		String name = "";
		for (Role role : list) {
			name+=role.getName()+",";
		}
		return name;
	}
	
	
	/**
	* 功能描述： 保存角色信息
	* 输入参数:  @param role 角色对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:05:04
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void saveRole(Role role){
		this.save(role);
	}
	
	/**
	* 功能描述：修改角色信息
	* 输入参数:  @param role 角色对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:05:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void updateRole(Role role){
		this.update(role);
	}
	
	/**
	* 功能描述： 删除角色信息
	* 输入参数:  @param roleId 角色ID
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:05:53
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void removeRole(String roleId){
		Role role = new Role();
		role.setId(roleId);
		this.delete(role);
	}
	
	/**
	* 功能描述： 
	* 输入参数:  @param roleId
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:06:12
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getRoleMenuCheckList(String roleId, String menuId){
		String sql = "select id, text, leaf, iconCls,expanded,state,";
		sql += " (select count(*) from s_role_menu rm where roleId=:roleId and menuId=m.id) checked";
		sql += " from sys_menu m where pid=:pid order by m.soft";
//		return this.queryForList(sql, new Object[]{roleId, menuId});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("pid", menuId);
		return this.getListBySQLMap(sql, map);
	}
	
	/**
	* 功能描述： 删除该角色下的关联的菜单ID
	* 输入参数:  @param roleId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:07:10
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void removeRoleMenu(String roleId){
		String sql = "delete from sys_role_menu where role_id=?";
		Object[] args = new Object[] { roleId };
		this.updateSQL(sql, args);
	}
	
	/**
	* 功能描述： 保存角色，菜单的关联ID
	* 输入参数:  @param roleId
	* 输入参数:  @param menuId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:07:39
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	@CacheEvict(value={"menuCache","role_menu_cache"},allEntries=true)
	public void saveRoleMenu(String roleId, String menuId){
		String sql = "insert into sys_role_menu(role_id, menu_id) values(?,?)";
		Object[] args = new Object[] { roleId, menuId };
		this.updateSQL(sql, args);
	}
	
	/**
	* 功能描述： 通过菜单ID，查询该菜单对应的角色ID
	* 输入参数:  @param menuid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:08:19
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<String>
	 */
	@Cacheable(value="role_menu_cache")
	public List<String> findRoleByMenuIdList(String menuid){
		String sql = "select role_id FROM sys_role_menu WHERE menu_id = :menuid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menuid", menuid);
		List<String> list = this.getListBySql(sql,map);
		return list;
	}
	
	/**
	* 功能描述： 查询角色名称是否存在
	* 输入参数:  @param roleName 角色名称
	* 输入参数:  @param companyTypeId 商户类型ID
	* 输入参数:  @param roleId  //角色ID,在修改的时候传值
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午8:59:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：Role
	 */
	public boolean getRoleByName(String roleName, String companyTypeId, String roleId) {
		String hql = " from Role role where role.name=:name and role.companyType.id=:companyTypeId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", roleName);
		map.put("companyTypeId", companyTypeId);
		if(null != roleId){
			hql += " and  role.id != :roleId";
			map.put("roleId", roleId);
		} 
		return  this.getListByHql(hql, map).size()>0?false:true;
	}
	
	/**
	* 功能描述： 查询该类型下，是否存在管理员
	* 输入参数:  @param companyTypeId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午9:33:02
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	public boolean getCompanyTypeByAdmin(String companyTypeId,String roleId) {
		String hql = " from Role role where role.companyType.id=:companyTypeId and role.is_admin = 1";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyTypeId", companyTypeId);
		if(null != roleId){
			hql += " and  role.id != :roleId";
			map.put("roleId", roleId);
		} 
		return  this.getListByHql(hql, map).size()>0?false:true;
	}
	
	/**
	* 功能描述： 获取商户类型下的角色
	* 输入参数:  @param companyTypeId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月30日上午10:08:23
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Role>
	 */
	public List<Role> getList(String companyTypeId){
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer(" from Role role");
		if(StringUtil.isNotEmpty(companyTypeId)){
			hql.append(" where role.companyType.id = :companyTypeId");
			map.put("companyTypeId", companyTypeId);
		}
		return this.getListByHql(hql.toString(), map);
	}
	
	
	/**
	 * 
	* 功能描述：根据类型ID取管理员角色
	* 输入参数:  @param companyTypeId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: 武国庆
	* 日    期: 2016年5月2日上午11:58:36
	* 修 改 人: 
	* 日    期: 
	* 返    回：Role
	 */
	public Role getAdminRoleByCompanyType(String companyTypeId){
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer(" from Role role");
		if(StringUtil.isNotEmpty(companyTypeId)){
			hql.append(" where role.companyType.id = :companyTypeId and is_admin=1");
			map.put("companyTypeId", companyTypeId);
		}
		return this.getObjectByColumn(hql.toString(), map);
	}
	
	/**
	* 功能描述： 查询所有的角色信息（id,role_code）
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月18日上午11:47:15
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	public List<String> getRoleListSecurity(){
		String sql = " SELECT role_code FROM sys_role r";
		return this.getListBySql(sql, null);
	}
	
	
	/**
	* 功能描述： 删除该角色下的所有的APP菜单
	* 输入参数:  @param roleId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月18日下午5:30:33
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void removeRoleMenuApp(String roleId){
		String sql = "delete from sys_role_menu_app where role_id=?";
		Object[] args = new Object[] { roleId };
		this.updateSQL(sql, args);
	}
	
	
	public void saveRoleMenuApp(String roleId, String menuAppId){
		String sql = "insert into sys_role_menu_app(role_id, menu_app_id) values(?,?)";
		Object[] args = new Object[] { roleId, menuAppId };
		this.updateSQL(sql, args);
	}

	/*
	 *aiqiwu 2017-06-05 
	 * */
	public List<Role> getRoleListByCompanyTypeId(String companyTypeId) {
		String hql = " from Role role where role.companyType.id = :typeId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", companyTypeId);
		return this.getListByHql(hql, map);
	}
}
