package com.memory.platform.module.system.controller;

import java.util.Collections;
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

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ICompanySectionService;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年4月27日 上午10:36:56 
* 修 改 人： 
* 日   期： 
* 描   述：  组织机构控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/companysection")
public class CompanySectionController extends BaseController{
	
	@Autowired
	private ICompanySectionService companySectionService;
	
	
	@RequestMapping(value = "/view/index")
	protected String index(Model model, HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("USER");
		Company company = account.getCompany();
		if(company == null) {
			model.addAttribute("json", "");
		}else {
			List<CompanySection> list = companySectionService.getListByCompany(company.getId());
			JSONArray json = new JSONArray();
			for (CompanySection menu : list) {
				JSONObject jo = new JSONObject();
				jo.put("id", menu.getId()); 
				if(menu.getParent_id().equals("0")){
					jo.put("parent", "#");
				}else{
					jo.put("parent", menu.getParent_id());
				}
				jo.put("text", menu.getName());
				json.put(jo);
			}
			model.addAttribute("json", json);
		}
		
		return "/system/companysection/index";
	}

	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(HttpServletRequest request,Model model,String id) {
		Account account = (Account) request.getSession().getAttribute("USER");
		Company company = account.getCompany();
		if(company == null) {
			model.addAttribute("list", Collections.emptyList());
		}else {
			List<CompanySection> list = companySectionService.getParentListByCompany(company.getId());
			model.addAttribute("list", list);
			model.addAttribute("id", id);
		}
		
		return "/system/companysection/add";
	}
	
	@RequestMapping(value = "add")
	@ResponseBody
	public Map<String, Object> saveCompanySection(HttpServletRequest request,CompanySection companySection) {
		Account account = (Account) request.getSession().getAttribute("USER");
		Company company = account.getCompany();
		companySection.setCompany_id(company.getId());
		return companySectionService.saveCompanySection(companySection);
	}
	
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model,String id) {
		CompanySection companySection = companySectionService.getCompanySection(id);
		model.addAttribute(companySection);
		return "/system/companysection/edit";
	}
	
	@RequestMapping(value = "edit")
	@ResponseBody
	public Map<String, Object> updateCompanySection(CompanySection companySection) {
		return companySectionService.updateCompanySection(companySection);
	}
	
	@RequestMapping(value = "remove")
	@ResponseBody
	public Map<String, Object> remove(String id,HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("USER");
		Company company = account.getCompany();
		return companySectionService.deleteCompanySection(id,company.getId());
	}
}
