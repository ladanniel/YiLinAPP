package com.memory.platform.module.app.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.module.aut.service.IBusinessLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseService;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ICompanyTypeService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 控制器
* 创 建 人： 武国庆
* 日    期： 2016年5月14日 下午9:44:01 
* 修 改 人： 
* 日   期： 
* 描   述： 商户管理控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/app/company")
@Api(value = "/app/company", description = "商户相关操作",consumes="application/json")
public class CompanyController extends BaseController{
	
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IIdcardService idcardService;
	@Autowired
	private IBusinessLicenseService businessLicenseService;
	@Autowired
	private IDrivingLicenseService drivingLicenseService;
	@Autowired
	private ICompanyTypeService companyTypeService;
	
	
	
	
	
	/**
	 * 商户信息显示
	* 功能描述： 
	* 输入参数:  @param companyID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: 武国庆
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "companyInfo")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),})
	@ApiOperation(value = "获取商户信息", httpMethod = "GET", response = Company.class, notes = "根据商户ID获取商户信息",position = 0)
	@ResponseBody
	public Map<String, Object> companyInfo(@ApiParam(required = true, name = "companyID", value = "商户ID") @RequestParam String companyID) {
	 
		if(StringUtils.isEmpty(companyID)){
			return jsonView(false, "商户id不能为空!",null);
		}
		Company company = companyService.getCompanyById(companyID);
		if(company==null){
			return jsonView(false, "没有找到商户信息!",null);
		}
		
		Idcard idCard = StringUtils.isBlank(company.getIdcard_id())?null:idcardService.get(company.getIdcard_id());
		BusinessLicense businessLicense = StringUtils.isBlank(company.getBusiness_license_id())?null:businessLicenseService.get(company.getBusiness_license_id());
		DrivingLicense drivingLicense = StringUtils.isBlank(company.getDriving_license_id())?null:drivingLicenseService.get(company.getDriving_license_id());
		
		company.setIdCard(idCard);
		company.setBusinessLicense(businessLicense);
		company.setDrivingLicense(drivingLicense);
		
		return jsonView(true, "操作成功!",company);
	}
	
	
	/**
	 * 商户类型
	* 功能描述： 
	* 输入参数:  @param companyID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: 武国庆
	* 日    期: 2016年6月15日下午9:15:293
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getcompanyType")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getcompanyType(){
		List<CompanyType> list = companyTypeService.getCompanyTypeIsRegisterList();
		return jsonView(true, "操作成功!",list);
	}
}
