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

import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ICompanyTypeService;
import com.memory.platform.module.system.service.IRoleService;

/**
 * 
 * 创 建 人： yangjiaqiao 日 期： 2016年4月23日 下午4:19:11 修 改 人： 日 期： 描 述： 商户类型控制器类 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/system/companytype")
public class CompanyTypeController extends BaseController {

	@Autowired
	private ICompanyTypeService companyTypeService;
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IRoleService roleService;

	/**
	 * 功能描述： 商户类型分页查询 输入参数: @param CompanyType 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年4月23日下午4:20:09 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(CompanyType CompanyType, HttpServletRequest request) {
		return this.companyTypeService.getPage(CompanyType, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 商户类型修改页面 输入参数: @param model 输入参数: @param id 输入参数: @return 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年4月23日下午4:19:55 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id) {
		model.addAttribute("companytype", this.companyTypeService.getCompanyTypeById(id));
		return "/system/companytype/edit";
	}

	/**
	 * 功能描述： 商户类型保存 输入参数: @param CompanyType 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年4月23日下午4:19:28 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveObject(CompanyType CompanyType) {
		String msg = "";
		boolean isfalse = true;
		if (companyTypeService.getCompanyTypeByName(CompanyType.getName(), null)) {
			this.companyTypeService.saveCompanyType(CompanyType);
			msg = SAVE_SUCCESS;
		} else {
			msg = "[" + CompanyType.getName() + "]类型，在数据库中已经存在！";
			isfalse = false;
		}
		return jsonView(isfalse, msg);
	}

	/**
	 * 功能描述： 修改商户类型数据 输入参数: @param CompanyType 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年4月23日下午4:20:29 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editCompanyType(CompanyType companyType) {
		String msg = "";
		boolean isfalse = true;
		if (companyTypeService.getCompanyTypeByName(companyType.getName(), companyType.getId())) {
			this.companyTypeService.updateCompanyType(companyType);
			msg = UPDATE_SUCCESS;
		} else {
			isfalse = false;
			msg = "[" + companyType.getName() + "]类型，在数据库中已经存在！";
		}
		return jsonView(isfalse, msg);
	}

	/**
	 * 
	 * 功能描述： 删除商户类型数据 输入参数: @param ids 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日
	 * 期: 2016年4月23日下午4:20:46 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeCompanyType(String ids) {
		String idsList[];
		idsList = ids.split("=");
		for (String id : idsList) {
			if (!"".equals(id)) {
				// getCompanyListByTypeId
				List<Company> lstCompany = companyService.getCompanyListByTypeId(id);
				List<Role> lstRole = roleService.getRoleListByCompanyTypeId(id);
				if (lstCompany.size() > 0 || lstRole.size() > 0)
					return jsonView(false, "商户类型正在使用中，不能删除该商户类型");
				this.companyTypeService.removeCompanyType(id);
			}
		}
		return jsonView(true, REMOVE_SUCCESS);
	}
}
