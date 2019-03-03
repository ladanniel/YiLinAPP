package com.memory.platform.module.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Menu;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyTypeService;
import com.memory.platform.module.system.service.IMenuAppService;
import com.memory.platform.module.system.service.IMenuService;
import com.memory.platform.module.system.service.IRoleService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月29日 下午1:53:26 
* 修 改 人： 
* 日   期： 
* 描   述： 角色管理控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
	@Autowired
	private IRoleService roleService;// 角色业务层接口
	@Autowired
	private IAccountService accountService;// 角色业务层接口
	@Autowired
	private IMenuService menuService;// 菜单业务层接口
	@Autowired
	private IMenuAppService menuAppService;// APP菜单业务层接口
	@Autowired
	private ICompanyTypeService companyTypeService; // 商户类型业务层接口

	/**
	* 功能描述： 分页查询系统角色信息
	* 输入参数:  @param role //角色对象
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月29日下午1:53:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Role role,HttpServletRequest request) {
		return this.roleService.getPage(role,getStart(request), getLimit(request));
	}

	/**
	* 功能描述：  角色信息修改页面
	* 输入参数:  @param model
	* 输入参数:  @param roleId 角色ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月29日下午1:54:17
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String roleId) {
		model.addAttribute("role", this.roleService.getRole(roleId));
		return "/system/role/edit";
	}

	/**
	* 功能描述： 角色信息的保存
	* 输入参数:  @param role
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月29日下午1:54:52
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveRole(Role role) {
		if (this.roleService.getRoleByName(role.getName(), role.getCompanyType().getId(), null)) {
			if(role.getIs_admin()){
				if(this.roleService.getCompanyTypeByAdmin(role.getCompanyType().getId(),null)){
					role.setRole_code("ROLE_" + AppUtil.getUUID());
					this.roleService.saveRole(role);
					return jsonView(true, SAVE_SUCCESS);
				}else{
					CompanyType companyType = companyTypeService.getCompanyTypeById(role.getCompanyType().getId());
					return jsonView(false, "【" + companyType.getName() + "】类型，管理员角色在系统中已经存在！");
				}
			}else{
				role.setRole_code("ROLE_" + AppUtil.getUUID());
				this.roleService.saveRole(role);
				return jsonView(true, SAVE_SUCCESS);
			}
		} else {
			CompanyType companyType = companyTypeService.getCompanyTypeById(role.getCompanyType().getId());
			return jsonView(false, "【" + companyType.getName() + "】类型，角色名称：【" + role.getName() + "】，在系统中已经存在！");
		}

	}

	/**
	* 功能描述： 角色信息的修改
	* 输入参数:  @param role 角色对象
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月29日下午1:55:06
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editRole(Role role) {
		if (this.roleService.getRoleByName(role.getName(), role.getCompanyType().getId(), role.getId())) {
			if(role.getIs_admin()){
				if(this.roleService.getCompanyTypeByAdmin(role.getCompanyType().getId(),role.getId())){
					role.setRole_code("ROLE_" + AppUtil.getUUID());
					this.roleService.updateRole(role);
					return jsonView(true, SAVE_SUCCESS);
				}else{
					CompanyType companyType = companyTypeService.getCompanyTypeById(role.getCompanyType().getId());
					return jsonView(false, "【" + companyType.getName() + "】类型，管理员角色在系统中已经存在！");
				}
			}else{
				role.setRole_code("ROLE_" + AppUtil.getUUID());
				this.roleService.updateRole(role);
				return jsonView(true, SAVE_SUCCESS);
			}
		} else {
			CompanyType companyType = companyTypeService.getCompanyTypeById(role.getCompanyType().getId());
			return jsonView(false, "【" + companyType.getName() + "】类型，角色名称：【" + role.getName() + "】，在系统中已经存在！");
		}
	}

	/**
	* 功能描述： 角色权限菜单功能设置页面
	* 输入参数:  @param model
	* 输入参数:  @param roleId 角色ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月29日下午1:55:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/set", method = RequestMethod.GET)
	protected String set(Model model, String roleId) {
		model.addAttribute("role", this.roleService.getRole(roleId));
		List<Menu> list = menuService.getMenuList();
		List<Map<String, Object>> mapList = menuService.getMenuListByRoleId(roleId);
		JSONArray jsonMenu = new JSONArray();
		for (Menu menu : list) {
			JSONObject jo = new JSONObject();
			jo.put("id", menu.getId());	
			for (Map<String, Object> map : mapList) { 
				if (menu.getId().equals(map.get("menu_id"))) {
					JSONObject jo2 = new JSONObject();
					jo2.put("opened", true);
					jo2.put("selected", true);
					jo.put("state", jo2);
					break;
				}   
			}
			if (menu.getType() == 0) {
				jo.put("parent", "#");
				JSONObject jo2 = new JSONObject();
				jo2.put("opened", true);
				jo2.put("selected", true);
				jo.put("state", jo2);
			} else {
				jo.put("parent", menu.getParent_id());
			}
			jo.put("text", menu.getName());
			jsonMenu.put(jo);
		}
		model.addAttribute("menujson", jsonMenu);
		return "/system/role/set";
	}
	
	
	
	@RequestMapping(value = "/view/setApp", method = RequestMethod.GET)
	protected String setApp(Model model, String roleId) {
		model.addAttribute("role", this.roleService.getRole(roleId));
		model.addAttribute("menuAppIds", this.menuAppService.getRoleMenuAppIds(roleId));
		return "/system/role/appSet";
	}

	/**
	* 功能描述： 角色权限菜单功能保存
	* 输入参数:  @param roleId 角色ID
	* 输入参数:  @param menuIds  菜单IDs,用逗号分隔
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月29日下午1:56:01
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "set", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> menuSet(String roleId, String menuIds) {
		if (null == roleId || "".equals(roleId)) {
			return jsonView(false, "请选择角色！");
		}
		if (null == menuIds) {
			return jsonView(false, "请选择菜单！");
		}

		this.roleService.saveRoleMenu(roleId, menuIds);

		return jsonView(true, SAVE_SUCCESS);
	}
	
	@RequestMapping(value = "setApp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> menuSetApp(String roleId, String menuAppIds) {
		if (null == roleId || "".equals(roleId)) {
			return jsonView(false, "请选择角色！");
		}
		if (null == menuAppIds) {
			return jsonView(false, "请选择APP菜单！");
		}

		this.roleService.saveRoleMenuApp(roleId, menuAppIds);

		return jsonView(true, "APP菜单设置成功！");
	}

	/**
	* 功能描述： 删除角色信息
	* 输入参数:  @param roleIds 角色IDs,逗号分隔
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月29日下午1:56:50
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeRole(String roleIds) {
		if(this.accountService.getAccountRoleList(roleIds) > 0){
			return jsonView(false, "删除失败，该角色已经有用户，不能删除！");
		}else{
			this.roleService.removeRole(roleIds);
		}
		return jsonView(true, REMOVE_SUCCESS);
	}
}
