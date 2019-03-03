package com.memory.platform.module.truck.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.truck.TruckPlate;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.truck.service.ITruckPlateService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车牌类型控制器
* 版    本    号：  V1.0
*/
@Controller
@RequestMapping("/truck/truckPlate")
public class TruckPlateController extends BaseController {
	
	@Autowired
	private ITruckPlateService truckPlateService;
	/**
	* 功 能 描 述：分页查询车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(TruckPlate truckPlate, HttpServletRequest request) throws UnsupportedEncodingException {
		return this.truckPlateService.getPage(truckPlate, getStart(request), getLimit(request));
	}
	/**
	* 功 能 描 述：保存车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(TruckPlate truckPlate) {
		String msg = "";
		boolean flag = true;
		if (truckPlateService.getTruckPlateByName(truckPlate.getName(),null)) {
			this.truckPlateService.saveTruckPlate(truckPlate); 
			msg = SAVE_SUCCESS;
		}else {
			msg = "["+truckPlate.getName()+"]类型，在数据库已存在！";
			flag = false;
		}
		return jsonView(flag, msg);
	}
	/**
	* 功 能 描 述：进入修改车牌类型页面
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：url
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id) {
		model.addAttribute("truckPlate", this.truckPlateService.getTruckPlateById(id));
		return "/truck/truckPlate/edit";
	}
	/**
	* 功 能 描 述：修改车牌类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(TruckPlate truckPlate) {
		String msg = "";
		boolean flag = true;
		if (truckPlateService.getTruckPlateByName(truckPlate.getName(), truckPlate.getId())) {
			this.truckPlateService.updateTruckPlate(truckPlate);
			msg = UPDATE_SUCCESS;
		}else {
			flag = false;
			msg = "["+truckPlate.getName()+"]类型，在数据库已存在！";
		}
		return jsonView(flag, msg);
	}
	/**
	* 功 能 描 述：删除车牌类型
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
				this.truckPlateService.removeTruckPlate(resId);
			}
		} catch (Exception e) {
			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
		}
		
		return jsonView(true, REMOVE_SUCCESS);
	}
}
