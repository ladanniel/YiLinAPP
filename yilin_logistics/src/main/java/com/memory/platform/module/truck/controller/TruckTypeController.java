package com.memory.platform.module.truck.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.truck.TruckType;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.truck.service.ITruckTypeService;

@Controller
@RequestMapping("/truck/truckType")
public class TruckTypeController extends BaseController {
	
	@Autowired
	private ITruckTypeService truckTypeService;
	/**
	* 功 能 描 述：分页查询所有车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(TruckType truckType, HttpServletRequest request) {
		return this.truckTypeService.getPage(truckType, getStart(request), getLimit(request));
	}
	/**
	* 功 能 描 述：保存车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(TruckType truckType) {
		String msg = "";
		boolean flag = true;
		if (truckTypeService.getTruckTypeByName(truckType.getName(),null)) {
			this.truckTypeService.saveTruckType(truckType);
			msg = SAVE_SUCCESS;
		}else {
			msg = "["+truckType.getName()+"]类型，在数据库已存在！";
			flag = false;
		}
		return jsonView(flag, msg);
	}
	/**
	* 功 能 描 述：进入修改车辆类型页面
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：rul
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id) {
		model.addAttribute("truckType", this.truckTypeService.getTruckTypeById(id));
		return "/truck/truckType/edit";
	}
	/**
	* 功 能 描 述：修改车辆类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(TruckType truckType) {
		String msg = "";
		boolean flag = true;
		if (truckTypeService.getTruckTypeByName(truckType.getName(), truckType.getId())) {
			this.truckTypeService.updateTruckType(truckType);
			msg = UPDATE_SUCCESS;
		}else {
			flag = false;
			msg = "["+truckType.getName()+"]类型，在数据库已存在！";
		}
		return jsonView(flag, msg);
	}
	/**
	* 功 能 描 述：删除车辆类型
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
				this.truckTypeService.removeTruckType(resId);
			}
		} catch (Exception e) {
			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
		}
		
		return jsonView(true, REMOVE_SUCCESS);
	}
}
