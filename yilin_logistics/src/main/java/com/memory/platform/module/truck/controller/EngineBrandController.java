package com.memory.platform.module.truck.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.truck.EngineBrand;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.truck.service.IEngineBrandService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：发动机品牌控制器
* 版    本    号：  V1.0
*/
@Controller
@RequestMapping("/truck/engineBrand")
public class EngineBrandController extends BaseController {
	
	@Autowired
	private IEngineBrandService engineBrandService;
	/**
	* 功 能 描 述：分页查询发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(EngineBrand engineBrand, HttpServletRequest request) {
		return this.engineBrandService.getPage(engineBrand, getStart(request), getLimit(request));
	}
	/**
	* 功 能 描 述：保存发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(EngineBrand engineBrand) {
		String msg = "";
		boolean flag = true;
		if (engineBrandService.getEngineBrandByName(engineBrand.getName(),null)) {
			this.engineBrandService.saveEngineBrand(engineBrand); 
			msg = SAVE_SUCCESS;
		}else {
			msg = "["+engineBrand.getName()+"]品牌，在数据库已存在！";
			flag = false;
		}
		return jsonView(flag, msg);
	}
	/**
	* 功 能 描 述：进入修改发动机品牌页面
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：url
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id) {
		model.addAttribute("engineBrand", this.engineBrandService.getEngineBrandById(id));
		return "/truck/engineBrand/edit";
	}
	/**
	* 功 能 描 述：修改发动机品牌
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：jsonview
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(EngineBrand engineBrand) {
		String msg = "";
		boolean flag = true;
		if (engineBrandService.getEngineBrandByName(engineBrand.getName(), engineBrand.getId())) {  
			this.engineBrandService.updateEngineBrand(engineBrand);
			msg = UPDATE_SUCCESS;
		}else {
			flag = false;
			msg = "["+engineBrand.getName()+"]品牌，在数据库已存在！";
		}
		return jsonView(flag, msg);
	}
	/**
	* 功 能 描 述：删除发动机品牌
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
				this.engineBrandService.removeEngineBrand(resId);
			}
		} catch (Exception e) {
			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
		}
		
		return jsonView(true, REMOVE_SUCCESS);
	}
}
