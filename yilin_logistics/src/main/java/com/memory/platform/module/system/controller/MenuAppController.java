package com.memory.platform.module.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.sys.MenuApp;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IMenuAppService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月18日 上午11:02:36 
* 修 改 人： 
* 日   期： 
* 描   述： app资源控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/menuapp")
public class MenuAppController extends BaseController{
	@Autowired
	private IMenuAppService menuAppService; //APP资源业务层接口
	
	
	/**
	* 功能描述： 资源分页查询接口
	* 输入参数:  @param resource //资源对象
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:51:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(MenuApp menuApp,HttpServletRequest request) {
		return this.menuAppService.getPage(menuApp,getStart(request), getLimit(request));
	}
	
	/**
	* 功能描述： APP资源地址增加页面
	* 输入参数:  @param model
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:52:43
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model) {
		return "/system/menuapp/add";
	}
	
	/**
	* 功能描述： 资源信息保存
	* 输入参数:  @param resource 资源对象
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:52:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMenuApp(MenuApp menuApp) { 
			this.menuAppService.saveMenuApp(menuApp);
			return jsonView(true, SAVE_SUCCESS);
		 
	}
	
	/**
	* 功能描述： 资源信息修改页面
	* 输入参数:  @param model
	* 输入参数:  @param resourceId 资源ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:53:19
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id) {
		model.addAttribute("menuApp", menuAppService.getMenuApp(id));
		return "/system/menuapp/edit";
	}
	
	/**
	* 功能描述： 资源信息修改保存
	* 输入参数:  @param resource
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:53:39
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editMenuApp(MenuApp menuApp) {
		this.menuAppService.updateMenuApp(menuApp);
		return jsonView(true, SAVE_SUCCESS);
	}
	
	/**
	* 功能描述： 查询所有的资源信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:53:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Resource>
	 */
	@RequestMapping("/getMenuAppList")
	@ResponseBody
	public List<MenuApp> getMenuAppList() {
		return this.menuAppService.getMenuAppList();
	}

	/**
	* 功能描述： 资源信息删除
	* 输入参数:  @param resourceId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年4月27日下午4:54:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeMenuApp(String id) {
		try {
			this.menuAppService.removeMenuApp(id);
		} catch (Exception e) {
			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
		}
		
		return jsonView(true, REMOVE_SUCCESS);
	}
	
	
}
