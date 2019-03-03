package com.memory.platform.module.system.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IParameterManageService;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月23日 下午3:14:48 
* 修 改 人： 
* 日   期： 
* 描   述： 参数设置控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/parameter")
public class ParameterManageController extends BaseController {

	@Autowired
	private IParameterManageService parameterManageService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/system/parameter/index";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(ParameterManage parameter,HttpServletRequest request) {
		return this.parameterManageService.getPage(parameter, getStart(request), getLimit(request));
	}
	
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		return "/system/parameter/add";
	}
	
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model,String id, HttpServletRequest request) {
		model.addAttribute("vo", parameterManageService.getInfo(id));
		return "/system/parameter/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(ParameterManage parameter,HttpServletRequest request) {
		parameter.setCreate_time(new Date());
		try {
			parameterManageService.saveInfo(parameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonView(true, "添加成功");
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(ParameterManage parameter,HttpServletRequest request) {
		parameterManageService.updateInfo(parameter);
		return jsonView(true, "编辑成功");
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request,String id) {
		ParameterManage parameter = parameterManageService.getInfo(id);
		parameterManageService.removeInfo(parameter);
		return jsonView(true, "删除成功");
	}
}
