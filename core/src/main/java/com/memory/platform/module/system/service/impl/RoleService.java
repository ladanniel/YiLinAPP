package com.memory.platform.module.system.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.EHCacheUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.dao.RoleDao;
import com.memory.platform.module.system.service.IRoleService;


@Service
@Transactional
public class RoleService implements IRoleService{
	
	@Autowired
	private RoleDao roleDao;

	

	public void saveRole(Role role) {
		role.setCreate_time(new Date());
		role.setAdd_user_id(UserUtil.getUser().getId());
		this.roleDao.saveRole(role);
		EHCacheUtil.initCacheManager(EHCacheUtil.ehcachePath);  
	    EHCacheUtil.initCache("rolelistsecurity");  
	    EHCacheUtil.remove("ALL_CONFIG_ATTRIBUTES_LIST");  
	}

	public void removeRole(String roleId) {
		this.roleDao.removeRole(roleId);
		EHCacheUtil.initCacheManager(EHCacheUtil.ehcachePath);  
	    EHCacheUtil.initCache("rolelistsecurity");  
	    EHCacheUtil.remove("ALL_CONFIG_ATTRIBUTES_LIST");  
	}

	public List<Map<String, Object>> getRoleMenuCheckList(String roleId,
			String menuId) {
		List<Map<String, Object>> list = this.roleDao.getRoleMenuCheckList(roleId, menuId);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			if(Integer.parseInt(map.get("checked").toString()) > 0){
				map.put("checked", true);
			}
			else{
				map.put("checked", false);
			}
			map.put("expanded", true);
			resultList.add(map);
		}
		return resultList;
	}

	public void saveRoleMenu(String roleId, String menuIds) {
		
		this.roleDao.removeRoleMenu(roleId);
		
		if(!"".equals(menuIds)){
			String[] arrMenuId = menuIds.split(",");

			for (String menuId : arrMenuId) {
				this.roleDao.saveRoleMenu(roleId, menuId);
			}
		}
		EHCacheUtil.initCacheManager(EHCacheUtil.ehcachePath);  
	    EHCacheUtil.initCache("rolelistsecurity");  
	    EHCacheUtil.remove("RESOURCE_ROLE_MAP");  
		
	}
	
	
	public void saveRoleMenuApp(String roleId, String menuAppIds) {
		this.roleDao.removeRoleMenuApp(roleId);
		if(!"".equals(menuAppIds)){
			String[] arrMenuId = menuAppIds.split(",");
			for (String menuId : arrMenuId) {
				this.roleDao.saveRoleMenuApp(roleId, menuId);
			}
		}
	}

	public Role getRole(String roleId) {
		return this.roleDao.getRole(roleId);
	}

	public void updateRole(Role role) {
		role.setUpdate_user_id(UserUtil.getUser().getId());
		role.setUpdate_time(new Date());
		this.roleDao.updateRole(role); 
	}

	@Override
	public List<Role> getRoleInfo() {
		// TODO Auto-generated method stub
		return this.roleDao.getRoleInfo();
	}

	@Override
	public List<String> getRoleByMenuIdList(String menuId) {
		// TODO Auto-generated method stub
		return this.roleDao.findRoleByMenuIdList(menuId);
	}

	/*  
	 *  getRoleByName
	 */
	@Override
	public boolean getRoleByName(String roleName, String companyTypeId, String roleId) {
		// TODO Auto-generated method stub
		return roleDao.getRoleByName(roleName, companyTypeId, roleId);
	}

	/*  
	 *  getCompanyTypeByAdmin
	 */
	@Override
	public boolean getCompanyTypeByAdmin(String companyTypeId,String roleId) {
		// TODO Auto-generated method stub
		return roleDao.getCompanyTypeByAdmin(companyTypeId,roleId);
	}

	/*  
	 *  getPage
	 */
	@Override
	public Map<String, Object> getPage(Role role, int start, int limit) {
		// TODO Auto-generated method stub
		return roleDao.getPage(role, start, limit);
	}

	@Override
	public List<Role> getList(String companyTypeId) {
		return roleDao.getList(companyTypeId);
	}

	@Override
	public Role getAdminRoleByCompanyType(String companyTypeId) {
		// TODO Auto-generated method stub
		return roleDao.getAdminRoleByCompanyType(companyTypeId);
	}

	/*  
	 *  getRoleListSecurity
	 */
	@Override
	public List<String> getRoleListSecurity() {
		// TODO Auto-generated method stub
		List<String> list = roleDao.getRoleListSecurity();
		return list;
	}

	/*
	 * aiqiwu 2017-06-05
	 * */
	@Override
	public List<Role> getRoleListByCompanyTypeId(String companyTypeId) {
		// TODO Auto-generated method stub
		return roleDao.getRoleListByCompanyTypeId(companyTypeId);
	}
}
