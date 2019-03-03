package com.memory.platform.module.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.aut.AutInfo;
import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.global.UpdateColume;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.AccountCompanyInfo;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.Auth;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.aut.service.impl.BusinessLicenseService;
import com.memory.platform.module.aut.service.impl.DrivingLicenseService;
import com.memory.platform.module.aut.service.impl.DrivingLicenseTypeService;
import com.memory.platform.module.aut.service.impl.IdcardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ICompanyTypeService;

/**
 * 控制器 创 建 人： 武国庆 日 期： 2016年5月14日 下午9:44:01 修 改 人： 日 期： 描 述： 商户管理控制器 版 本 号： V1.0
 */
@Controller
@RequestMapping("/system/company")
public class CompanyController extends BaseController {

	@Autowired
	private IAccountService accountService; // 账号业务层接口

	@Autowired
	private ICompanyService companyService; // 商户业务层接口

	@Autowired
	private ICompanyTypeService companyTypeService; // 商户类型业务层接口

	@Autowired
	private IdcardService idcardService; // 身份证业务层接口

	@Autowired
	private DrivingLicenseService drivingLicenseService;// 驾驶证业务层接口

	@Autowired
	private DrivingLicenseTypeService drivingLicenseTypeService;// 准假车型业务接口

	@Autowired
	private BusinessLicenseService businessLicenseService;// 营业执照业务接口

	@RequestMapping(value = "/companyinfo")
	protected String index(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("USER");
		Account accountNew = accountService.getAccount(account.getId());
		session.setAttribute("USER", accountNew);
		model.addAttribute("USER", accountNew);
		return "/system/company/companyinfo";
	}

	@RequestMapping(value = "/view/index1")
	protected String index1(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("USER");
		Account accountNew = accountService.getAccount(account.getId());
		session.setAttribute("USER", accountNew);
		return "/system/company/index1";
	}

	/**
	 * 功能描述： 商户信息查询 输入参数: @param company 输入参数: @param request 输入参数: @return 异 常：
	 * 创 建 人: yangjiaqiao 日 期: 2016年5月24日下午4:44:36 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Company company, HttpServletRequest request) {
		return this.companyService.getPageCompany(company, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 保存商户和账户信息 输入参数: @param model 输入参数: @param request 输入参数: @param
	 * accountCompanyInfo 输入参数: @return 输入参数: @throws Exception 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年5月26日上午10:57:37 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/accountCompanySave", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> userAutSave(Model model, HttpServletRequest request,
			AccountCompanyInfo accountCompanyInfo) throws Exception {
		Account account = JsonPluginsUtil.jsonToBean(accountCompanyInfo.getAccount_info(), Account.class);
		Company company = JsonPluginsUtil.jsonToBean(accountCompanyInfo.getCompany_info(), Company.class);
		if (StringUtils.isEmpty(company.getCompanyTypeId())) {
			return jsonView(false, "商户类型不能为空");
		}
		if (StringUtils.isEmpty(account.getAccount())) {
			return jsonView(false, "账号不能为空");
		}
		if (StringUtils.isEmpty(account.getPhone())) {
			return jsonView(false, "手机号不能为空");
		}
		if (StringUtils.isEmpty(account.getPassword())) {
			return jsonView(false, "密码不能为空");
		}
		if (!StringUtil.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)) {
			return jsonView(false, "账号由数字英文字母及下划线组成并且长度为6到16位！");
		}
		if (account.getPassword().length() < 6 || account.getPassword().length() > 16) {
			return jsonView(false, "密码长度为6到16位！");
		}
		Account user = accountService.checkAccount(account.getAccount());
		if (user != null) {
			return jsonView(false, "用户名重复");
		}
		// aiqiwu 2017-06-16 取消手机号重复验证
		// Account user_1 =
		// accountService.checkAccountByPhone(account.getPhone());
		// if(user_1!=null){
		// return jsonView(false, "手机号重复");
		// }
		CompanyType companyType = companyTypeService.getCompanyTypeById(company.getCompanyTypeId());
		if (!companyType.getIs_aut()) {
			account.setName(company.getName());
		}
		String company_id = this.companyService.saveAccountCompany(UserUtil.getUser(request), account, company);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("msg", "[" + company.getName() + "]商户添加成功！");
		map.put("companyId", company_id);
		map.put("isAut", companyType.getIs_aut());
		return map;
	}

	/**
	 * 功能描述： 保存商户和账户信息 输入参数: @param model 输入参数: @param request 输入参数: @param
	 * accountCompanyInfo 输入参数: @return 输入参数: @throws Exception 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年5月26日上午10:57:37 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/addcompanyaudtinfo", method = RequestMethod.GET)
	protected String companyAudtSave(Model model, HttpServletRequest request, String companyId) throws Exception {
		Company company = companyService.getCompanyById(companyId);
		CompanyType companyType = company.getCompanyType();
		model.addAttribute("companyType", companyType);
		model.addAttribute("company", company);
		String url = "";
		switch (company.getAudit()) {
		case notapply:
			url = "/system/company/addcompanyaudtinfo";
			break;
		default:
			url = "/system/company/querycompany";
			if (!StringUtils.isEmpty(company.getIdcard_id())) {
				model.addAttribute("idcard", idcardService.get(company.getIdcard_id()));
			}
			if (!StringUtils.isEmpty(company.getDriving_license_id())) {
				DrivingLicense drivingLicense = drivingLicenseService.get(company.getDriving_license_id());
				drivingLicense.setDrivingLicenseType(
						drivingLicenseTypeService.get(drivingLicense.getDriving_license_type_id()));
				model.addAttribute("drivingLicense", drivingLicense);
			}
			if (!StringUtils.isEmpty(company.getBusiness_license_id())) {
				model.addAttribute("businessLicense", businessLicenseService.get(company.getBusiness_license_id()));
			}
			break;
		}
		return url;
	}

	@RequestMapping(value = "/companyAutSave", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> companyAutSave(Model model, HttpServletRequest request, AutInfo companyAutInfo)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = UserUtil.getUser(request);
		Company company = companyService.getCompanyById(companyAutInfo.getCompany_id());
		if (Auth.waitProcess.equals(company.getAudit())) { // 待审核
			map.put("success", false);
			map.put("msg", "已提交认证资料，正在审核中，无需重复提交！");
		} else if (Auth.auth.equals(company.getAudit())) {
			map.put("success", false);
			map.put("msg", "您所提交的资料已通过审核，无需重复提交！");
		} else {
			CompanyType companyType = company.getCompanyType();
			String path = AppUtil.getUpLoadPath(request);
			Idcard idcard = null;
			DrivingLicense drivingLicense = null;
			BusinessLicense businessLicense = null;
			if (companyType.getIs_aut()) {
				if (companyType.getIdcard()) {
					idcard = JsonPluginsUtil.jsonToBean(companyAutInfo.getIdcard_info(), Idcard.class);
				}
				if (companyType.getDriving_license()) {
					drivingLicense = JsonPluginsUtil.jsonToBean(companyAutInfo.getDriving_license_info(),
							DrivingLicense.class);
				}
				if (companyType.getBusiness_license()) {
					businessLicense = JsonPluginsUtil.jsonToBean(companyAutInfo.getBusiness_license_info(),
							BusinessLicense.class);
				}
			}
			map = companyService.saveCompantAuthInfo(company, idcard, drivingLicense, businessLicense, path, account,
					companyAutInfo.getType());
		}
		return map;
	}

	/**
	 * 功能描述： 输入参数: @param model 输入参数: @param request 输入参数: @param updateColume
	 * 输入参数: @return 输入参数: @throws Exception 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月2日下午12:08:23 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/updateCompanyFiled", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> updateCompanyFiled(Model model, HttpServletRequest request, UpdateColume updateColume)
			throws Exception {
		companyService.updateCompanyFiled(updateColume);
		return jsonView(true, "信息修改成功！");
	}

}
