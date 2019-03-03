package com.memory.platform.module.aut.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.Auth;
import com.memory.platform.module.aut.service.IBusinessLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseTypeService;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月10日 下午5:35:39 
* 修 改 人： 
* 日   期： 
* 描   述： 商户信息控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/aut/autcompany")
public class AutCompanyController extends BaseController{
	
	@Autowired
	private ICompanyService companyService; //商户基本信息
	@Autowired
	private IAccountService accountService; //账户基本信息
	@Autowired
	private IIdcardService idcardService; //身份证基本信息
	@Autowired
	private IDrivingLicenseService drivingLicenseService; //驾驶证基本信息
	@Autowired
	private IDrivingLicenseTypeService drivingLicenseTypeService; //准假车型基本信息
	@Autowired
	private IBusinessLicenseService businessLicenseService; //营业执照基本信息
	
	/**
	* 功能描述： 商户分页信息查询
	* 输入参数:  @param company
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月10日下午5:37:32
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Company company,HttpServletRequest request) {
		return this.companyService.getPageCompanyAut(company, getStart(request), getLimit(request));
	}
	
	
	/**
	* 功能描述： 进入商户认证审核页面
	* 输入参数:  @param accountId
	* 输入参数:  @param request
	* 输入参数:  @param model
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月14日下午12:34:26
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping("/autcompany")
	public String autCompany(String accountId,HttpServletRequest request,Model model) {
		Account account = accountService.getAccount(accountId);
		CompanyType companyType = account.getCompany().getCompanyType();
		Company company = account.getCompany();
		if(Auth.notapply.equals(company.getAudit()) ){
			model.addAttribute("companyType",companyType);
			model.addAttribute("company",company);
		}else if(Auth.waitProcess.equals(company.getAudit()) || Auth.waitProcessSupplement.equals(company.getAudit()) || Auth.notAuth.equals(company.getAudit())){
			model.addAttribute("companyType",companyType);
			model.addAttribute("company",company);
			if(!StringUtils.isEmpty(company.getIdcard_id())){
				model.addAttribute("idcard",idcardService.get(company.getIdcard_id()));
			}
			if(!StringUtils.isEmpty(company.getDriving_license_id())){
				DrivingLicense drivingLicense = drivingLicenseService.get(company.getDriving_license_id());
				drivingLicense.setDrivingLicenseType(drivingLicenseTypeService.get(drivingLicense.getDriving_license_type_id()));
				model.addAttribute("drivingLicense",drivingLicense);
			}
			if(!StringUtils.isEmpty(company.getBusiness_license_id())){
				model.addAttribute("businessLicense",businessLicenseService.get(company.getBusiness_license_id()));
			}
		} 
		return "/aut/autcompany/autcompany";
	}
	
	/**
	* 功能描述：  查看商户信息
	* 输入参数:  @param accountId
	* 输入参数:  @param request
	* 输入参数:  @param model
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月14日下午12:36:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping("/querycompany")
	public String queryCompany(String accountId,HttpServletRequest request,Model model) {
		Account account = accountService.getAccount(accountId);
		CompanyType companyType = account.getCompany().getCompanyType();
		Company company = account.getCompany();
		if(Auth.notapply.equals(company.getAudit()) ){
			model.addAttribute("companyType",companyType);
			model.addAttribute("company",company);
		}else if(Auth.waitProcess.equals(company.getAudit()) || Auth.waitProcessSupplement.equals(company.getAudit()) || Auth.notAuth.equals(company.getAudit()) || Auth.auth.equals(company.getAudit())){
			model.addAttribute("companyType",companyType);
			model.addAttribute("company",company);
			if(!StringUtils.isEmpty(company.getIdcard_id())){
				model.addAttribute("idcard",idcardService.get(company.getIdcard_id()));
			}
			if(!StringUtils.isEmpty(company.getDriving_license_id())){
				DrivingLicense drivingLicense = drivingLicenseService.get(company.getDriving_license_id());
				drivingLicense.setDrivingLicenseType(drivingLicenseTypeService.get(drivingLicense.getDriving_license_type_id()));
				model.addAttribute("drivingLicense",drivingLicense);
			}
			if(!StringUtils.isEmpty(company.getBusiness_license_id())){
				model.addAttribute("businessLicense",businessLicenseService.get(company.getBusiness_license_id()));
			}
		} 
		return "/aut/autcompany/querycompany";
	}
	
	/**
	* 功能描述： 审核商户信息
	* 输入参数:  @param companyId 商户信息ID
	* 输入参数:  @param isfalse 是否通过
	* 输入参数:  @param failed 未通过原因
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月11日上午9:40:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/saveAutCompany")
	@ResponseBody
	public Map<String, Object> saveAutCompany(String companyId,boolean isfalse,String failed,HttpServletRequest request) {
		return this.companyService.saveAutCompany(companyId, isfalse, failed);
	}
	 
}
