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

import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Resource;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ICompanyTypeService;
import com.memory.platform.module.system.service.IResourceService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月27日 下午4:50:51 
* 修 改 人： 
* 日   期： 
* 描   述： 资源控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/resource")
public class ResourceController extends BaseController{
	@Autowired
	private IResourceService resourceService; //资源业务层接口
	@Autowired
	private ICompanyTypeService companyTypeService; //商户类型业务层接口
	
	
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
	public Map<String, Object> getPage(Resource resource,HttpServletRequest request) {
		resource.setSearch(request.getParameter("search"));  //关键字
		return this.resourceService.getPage(resource,getStart(request), getLimit(request));
	}
	
	/**
	* 功能描述： 资源地址增加页面
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
		return "/system/resource/add";
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
	public Map<String, Object> saveUser(Resource resource) { 
		if(resourceService.getResourceByName(resource.getName(),resource.getCompanyType().getId())){
			this.resourceService.saveResource(resource);
			return jsonView(true, SAVE_SUCCESS);
		}else{
			CompanyType companyType  = companyTypeService.getCompanyTypeById(resource.getCompanyType().getId());
			return jsonView(false, "【"+companyType.getName()+"】类型，资源名称：【"+resource.getName()+"】，在系统中已经存在！");
		}
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
	protected String edit(Model model, String resourceId) {
		model.addAttribute("resource", this.resourceService.getResource(resourceId));
		return "/system/resource/edit";
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
	public Map<String, Object> editResource(Resource resource) {
		this.resourceService.updateResource(resource);
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
	@RequestMapping("/getResourceList")
	@ResponseBody
	public List<Resource> getResourceList() {
		return this.resourceService.getResourceList();
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
	public Map<String, Object> removeResource(String resourceId) {
		
		try {
			String[] resArr = resourceId.split(",");
			for (String resId : resArr) {
				this.resourceService.removeResource(resId);
			}
		} catch (Exception e) {
			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
		}
		
		return jsonView(true, REMOVE_SUCCESS);
	}
}
