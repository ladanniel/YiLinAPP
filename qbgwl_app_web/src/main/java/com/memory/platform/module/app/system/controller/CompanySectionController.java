package com.memory.platform.module.app.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ICompanySectionService;
import com.memory.platform.module.system.service.IRoleService;

@Controller
@RequestMapping("app/companysection")
public class CompanySectionController extends BaseController{
	@Autowired
	ICompanySectionService companySectionService;
	@Autowired
	IRoleService roleService;
	
	/**
	 * 获取组织机构列表 aiqiwu 2017-08-31
	 */
	@RequestMapping("/getCompanySectionPage")
	@ResponseBody
	public Map<String, Object> getCompanySectionPage(HttpServletRequest request) {
		List<CompanySection> list = companySectionService
				.getListByCompany(UserUtil.getAccount().getCompany().getId());
		return jsonView(true, "获取组织机构列表成功", list);
	}
	
	@RequestMapping(value = "saveCompanySection")
	@ResponseBody
	public Map<String, Object> saveCompanySection(CompanySection companySection,HttpServletRequest request) {
		Account account = UserUtil.getAccount();
		Company company = account.getCompany();
		companySection.setCompany_id(company.getId());
		companySection.setParent_id(account.getCompanySection().getId());
		companySection.setParent_name(company.getName());
		companySection.setLeav(2);
		return companySectionService.saveCompanySection(companySection);
	}
}
