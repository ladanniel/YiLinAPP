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
import com.memory.platform.entity.sys.Menu;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IMenuService;
import com.memory.platform.module.system.service.IResourceService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月27日 下午4:46:05 
* 修 改 人： 
* 日   期： 
* 描   述： 菜单管理控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController{
	
	@Autowired
	private IMenuService menuService; //菜单业务层接口
	
	@Autowired
	private  IResourceService resourceService; // 资源业务层接口
	
	
	/**
	* 功能描述： 通过菜单ID,查询子菜单信息
	* 输入参数:  @param id 菜单ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:46:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Menu>
	 */
	@RequestMapping("/getMenuList")
	@ResponseBody
	public List<Menu> getMenuList(String id) {
		if(null == id || "".equals(id)){
			id = "0";
		}
		return this.menuService.getMenuListByPid(id);
	}
	
	
	/**
	* 功能描述：  菜单管理煮页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:47:17
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		List<Menu> list = menuService.getMenuList();
		JSONArray json = new JSONArray();
		for (Menu menu : list) {
			JSONObject jo = new JSONObject();
			jo.put("id", menu.getId()); 
			if(menu.getParent_id().equals("-1")){
				jo.put("parent", "#");
			}else{
				jo.put("parent", menu.getParent_id());
			}
			String menuType = AppUtil.getMenuTypeName(menu.getType());
			String icon = menu.getIconcls() != null && !menu.getIconcls().equals("")?menu.getIconcls():"&#xe62d;";
			if(menu.getType() > 0){
				jo.put("text", "<i class='iconfont' style='font-size:14px;font-style:normal;' >"+icon+"</i>&nbsp;&nbsp;"+menu.getName()+"--------<font color='#1ab394'>排序：</font><span class='label label-primary'>"+menu.getSoft()+"</span>"+"--------<font color='#1ab394'>类型：</font><span class='label label-primary'>"+menuType+"</span>"+"--------<font color='#ed5565'>菜单地址：</font><span class='label label-danger'>"+menu.getResource().getUrl()+"</span>");
			}else{
				jo.put("text", menu.getName()+"--------<font color='#1ab394'>排序：</font><span class='label label-primary'>"+menu.getSoft()+"</span>"+"--------<font color='#ed5565'>类型：</font><span class='label label-danger'>"+menuType+"</span>"+"--------<font color='#ed5565'>菜单地址：</font><span class='label label-danger'>"+menu.getResource().getUrl()+"</span>");
			}
			json.put(jo);
		}
		model.addAttribute("json", json);
		return "/system/menu/index";
	}
	
	
	
	/**
	* 功能描述： 通过菜单ID,查询子菜单信息
	* 输入参数:  @param id 菜单ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:46:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Menu>
	 */
	@RequestMapping("/getMenuComboTreeList")
	@ResponseBody
	public List<Menu> getMenuComboTreeList(String id) {
		if(null == id || "".equals(id)){
			id = "-1";
		}
		return this.menuService.getMenuListByPid(id);
	}
	
	/**
	* 功能描述： 菜单信息增加
	* 输入参数:  @param menu 菜单对象
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:48:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMenu(Menu menu) {
		this.menuService.saveMenu(menu);
		return jsonView(true, SAVE_SUCCESS);
	}
	
	
	/**
	* 功能描述： 菜单信息编辑页面
	* 输入参数:  @param model
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:49:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String menuId) {
		Menu menu = this.menuService.getMenu(menuId);
		model.addAttribute("menu", menu);
		model.addAttribute("typeName", AppUtil.getMenuTypeName(menu.getType()));
		model.addAttribute("pmenu", this.menuService.getMenu(menu.getParent_id()));
		model.addAttribute("resourceList", this.resourceService.getResourceList());
		return "/system/menu/edit";
	}
	
	/**
	* 功能描述： 菜单信息增加页面
	* 输入参数:  @param model
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:49:50
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, String menuId) {
		Menu menu = menuService.getMenu(menuId);
		int type = menu.getType();//菜单类型： 0：系统菜单 1：顶级菜单，2：模块，3：菜单 ，4：操作
		model.addAttribute("menu", menu);
		model.addAttribute("typeMap", AppUtil.getMenuTypeMap(type));
		model.addAttribute("resourceList", this.resourceService.getResourceList());
		return "/system/menu/add";
	}
	
	/**
	* 功能描述： 菜单信息修改
	* 输入参数:  @param menu
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:50:06
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> Menu(Menu menu) {
		this.menuService.updateMenu(menu);
		return jsonView(true, SAVE_SUCCESS);
	}

	/**
	* 功能描述： 菜单信息删除
	* 输入参数:  @param menuId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:50:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeMenu(String menuId) {
		try {
			this.menuService.removeMenu(menuId);
		} catch (Exception e) {
			return jsonView(false, e.getMessage());
		}
		return jsonView(true, REMOVE_SUCCESS);
	}
}
