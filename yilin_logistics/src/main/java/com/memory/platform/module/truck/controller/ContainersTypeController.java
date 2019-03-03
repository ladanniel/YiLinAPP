package com.memory.platform.module.truck.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.truck.ContainersType;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.truck.service.IContainersTypeService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：货箱形式控制器
* 版    本    号：  V1.0
*/
@Controller
@RequestMapping("/truck/containersType")
public class ContainersTypeController extends BaseController {
	
	@Autowired
	private IContainersTypeService containersTypeService;
	/**
	* 功 能 描 述：分页查询货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(ContainersType containersType, HttpServletRequest request) {
		return this.containersTypeService.getPage(containersType, getStart(request), getLimit(request));
	}
	/**
	* 功 能 描 述：保存货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ContainersType containersType) {
		String msg = "";
		boolean flag = true;
		if (containersTypeService.getContainersTypeByName(containersType.getName(),null)) {
			this.containersTypeService.saveContainersType(containersType); 
			msg = SAVE_SUCCESS;
		}else {
			msg = "["+containersType.getName()+"]货箱形式，在数据库已存在！";
			flag = false;
		}
		return jsonView(flag, msg);
	}
	/**
	* 功 能 描 述：进入修改货箱形式页面
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：url
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id) {
		model.addAttribute("containersType", this.containersTypeService.getContainersTypeById(id));
		return "/truck/containersType/edit";
	}
	/**
	* 功 能 描 述：修改货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(ContainersType containersType) {
		String msg = "";
		boolean flag = true;
		if (containersTypeService.getContainersTypeByName(containersType.getName(), containersType.getId())) {
			this.containersTypeService.updateContainersType(containersType);
			msg = UPDATE_SUCCESS;
		}else {
			flag = false;
			msg = "["+containersType.getName()+"]货箱形式，在数据库已存在！";
		}
		return jsonView(flag, msg);
	}
	/**
	* 功 能 描 述：删除货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public Map<String, Object> remove(String ids) {
		try {
			String[] resArr = ids.split(",");
			for (String resId : resArr) {
				this.containersTypeService.removeContainersType(resId);
			}
		} catch (Exception e) {
			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
		}
		
		return jsonView(true, REMOVE_SUCCESS);
	}
}
