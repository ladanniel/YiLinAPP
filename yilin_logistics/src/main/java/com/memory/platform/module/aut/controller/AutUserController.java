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
import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.Auth;
import com.memory.platform.module.aut.service.IDrivingLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseTypeService;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月10日 下午5:35:39 
* 修 改 人： 
* 日   期： 
* 描   述： 商户信息控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/aut/autuser")
public class AutUserController extends BaseController{
	
	@Autowired
	private IAccountService accountService; //账户业务接口
	@Autowired
	private IIdcardService idcardService; //身份证业务接口
	@Autowired
	private IDrivingLicenseService drivingLicenseService; //驾驶证业务接口
	@Autowired
	private IDrivingLicenseTypeService drivingLicenseTypeService; //准假车型业务接口
	
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
	public Map<String, Object> getPage(Account account,HttpServletRequest request) {
		return this.accountService.getPageAccountAut(account, getStart(request), getLimit(request));
	}
	
	/**
	* 功能描述： 进入用户认证审核页面
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
	@RequestMapping("/audituser")
	public String auditUser(String accountId,HttpServletRequest request,Model model) {
		Account account = accountService.getAccount(accountId);
		Role role = account.getRole();
		if(Auth.notapply.equals(account.getAuthentication())){
			model.addAttribute("account",account);
			model.addAttribute("role",role);
		}else if(Auth.waitProcess.equals(account.getAuthentication()) || Auth.waitProcessSupplement.equals(account.getAuthentication()) || Auth.notAuth.equals(account.getAuthentication())){
			model.addAttribute("account",account);
			model.addAttribute("role",role);
			if(!StringUtils.isEmpty(account.getIdcard_id())){
				model.addAttribute("idcard",idcardService.get(account.getIdcard_id()));
			}
			if(!StringUtils.isEmpty(account.getDriving_license_id())){
				DrivingLicense drivingLicense = drivingLicenseService.get(account.getDriving_license_id());
				drivingLicense.setDrivingLicenseType(drivingLicenseTypeService.get(drivingLicense.getDriving_license_type_id()));
				model.addAttribute("drivingLicense",drivingLicense);
			}
		} 
		return "/aut/autuser/autuser";
	}
	
	
	/**
	* 功能描述： 进入用户认证审核页面
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
	@RequestMapping("/queryuser")
	public String queryUser(String accountId,HttpServletRequest request,Model model) {
		Account account = accountService.getAccount(accountId);
		Role role = account.getRole();
		if(Auth.notapply.equals(account.getAuthentication())){
			model.addAttribute("account",account);
			model.addAttribute("role",role);
		}else if(Auth.waitProcess.equals(account.getAuthentication()) || Auth.waitProcessSupplement.equals(account.getAuthentication()) || Auth.notAuth.equals(account.getAuthentication()) || Auth.auth.equals(account.getAuthentication())){
			model.addAttribute("account",account);
			model.addAttribute("role",role);
			if(!StringUtils.isEmpty(account.getIdcard_id())){
				model.addAttribute("idcard",idcardService.get(account.getIdcard_id()));
			}
			if(!StringUtils.isEmpty(account.getDriving_license_id())){
				DrivingLicense drivingLicense = drivingLicenseService.get(account.getDriving_license_id());
				drivingLicense.setDrivingLicenseType(drivingLicenseTypeService.get(drivingLicense.getDriving_license_type_id()));
				model.addAttribute("drivingLicense",drivingLicense);
			}
		}
		return "/aut/autuser/queryuser";
	}
	
	
	
	/**
	* 功能描述： 审核用户认证信息
	* 输入参数:  @param accountId
	* 输入参数:  @param isfalse
	* 输入参数:  @param failed
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月17日下午4:30:05
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/saveAutUser")
	@ResponseBody
	public Map<String, Object> saveAutUser(String accountId,boolean isfalse,String failed,HttpServletRequest request) {
		return this.accountService.saveAutUser(accountId, isfalse, failed);
	}
	
	 
}
