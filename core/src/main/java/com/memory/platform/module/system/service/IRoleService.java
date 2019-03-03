package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.Role;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月27日 下午5:02:28 
* 修 改 人： 
* 日   期： 
* 描   述： 角色业务层接口
* 版 本 号：  V1.0
 */
public interface IRoleService {
	
	
	/**
	* 功能描述： 分页查询角色信息
	* 输入参数:  @param role 角色对象
	* 输入参数:  @param start 起始条数
	* 输入参数:  @param limit 截至条数
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月29日下午12:18:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
    Map<String, Object> getPage(Role role,int start, int limit);
	
	/**
	* 功能描述： 通过角色ID,查询角色信息
	* 输入参数:  @param roleId 角色ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:58:34
	* 修 改 人: 
	* 日    期: 
	* 返    回：Role
	 */
	Role getRole(String roleId);
	
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
	boolean getRoleByName(String roleName,String companyTypeId,String roleId);
	
	/**
	* 功能描述： 查询该类型下，是否存在管理员
	* 输入参数:  @param companyTypeId 商户类型ID
	* 输入参数:  @param roleId 角色ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午9:33:02
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	boolean getCompanyTypeByAdmin(String companyTypeId,String roleId);
	
	/**
	* 功能描述： 修改角色信息
	* 输入参数:  @param role 角色对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:58:52
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateRole(Role role);
	
	
	/**
	* 功能描述： 角色信息保存
	* 输入参数:  @param role 角色对象
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:59:42
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveRole(Role role);
	
	/**
	* 功能描述： 删除角色信息
	* 输入参数:  @param roleId 角色ID
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:59:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void removeRole(String roleId);
	
	/**
	* 功能描述： 通过角色ID,菜单ID,查询该角色已经选择的角色信息
	* 输入参数:  @param roleId
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:00:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	List<Map<String, Object>> getRoleMenuCheckList(String roleId, String menuId);
	
	/**
	* 功能描述： 角色菜单配置保存
	* 输入参数:  @param roleId 角色ID
	* 输入参数:  @param menuIds 菜单IDs,多个用","分割
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:00:58
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveRoleMenu(String roleId, String menuIds);
	
	/**
	* 功能描述： 查询所有角色信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午5:01:54
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Role>
	 */
	List<Role> getRoleInfo();
	
	/**
	* 功能描述： 通过菜单ID查询所有的角色ID
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月26日下午12:14:17
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<String>
	 */
	List<String> getRoleByMenuIdList(String menuId);
	
	/**
	* 功能描述： 获取商户类型下的角色
	* 输入参数:  @param companyTypeId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月30日上午10:09:23
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Role>
	 */
	public List<Role> getList(String companyTypeId);
	
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
	public Role getAdminRoleByCompanyType(String companyTypeId);
	
	
	
	/**
	* 功能描述： 查询所有的角色信息（id,role_code）
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月18日上午11:47:15
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<String>
	 */
	List<String> getRoleListSecurity();
	
	/**
	* 功能描述： 保存设置角色的APP菜单
	* 输入参数:  @param roleId
	* 输入参数:  @param menuAppIds
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月18日下午5:31:37
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveRoleMenuApp(String roleId, String menuAppIds);

	/*
	 * aiqiwu 2017-06-05
	 * */
	List<Role> getRoleListByCompanyTypeId(String companyTypeId);
	
}
