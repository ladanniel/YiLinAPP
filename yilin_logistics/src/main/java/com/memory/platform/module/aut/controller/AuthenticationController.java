package com.memory.platform.module.aut.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.aut.AutInfo;
import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.AES;
import com.memory.platform.global.Auth;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.aut.service.IAuthenticationService;
import com.memory.platform.module.aut.service.IBusinessLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseService;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ICompanyTypeService;
import com.memory.platform.module.system.service.IRoleService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月30日 下午12:21:29 
* 修 改 人： 
* 日   期： 
* 描   述： 用户认证信息控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/aut/authenticat")
public class AuthenticationController extends BaseController {
	
	@Autowired
	private ICompanyService companyService;//商户类型业务接口
	@Autowired
	private IRoleService roleService;//角色业务接口
	@Autowired
	private IAccountService accountService;//用户业务接口
	@Autowired
	private ICompanyTypeService companyTypeService;//商户类型业务接口
	@Autowired
	IAuthenticationService authenticationService ;//认证信息保存接口
	@Autowired
	IIdcardService idcardService ;//身份证业务接口
	@Autowired
	IDrivingLicenseService drivingLicenseService ;//身份证业务接口
	@Autowired
	IBusinessLicenseService businessLicenseService ;//身份证业务接口
	
	/**
	* 功能描述： 认证信息主页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:08:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/index")
	protected String index(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		Account newAccount = accountService.getAccount(account.getId());
		String url = "";
		 
		if(account.getUserType().equals(Account.UserType.company)){
			CompanyType companyType = companyTypeService.getCompanyTypeById(account.getCompany().getCompanyType().getId());
			Company company = companyService.getLoadById(account.getCompany().getId());
			model.addAttribute("companyType",companyType);
			model.addAttribute("company",company);
			if(company.getAudit().equals(Auth.notapply)){
				url = "/aut/authenticat/saveautcompany";
			}else if(company.getAudit().equals(Auth.waitProcess) || company.getAudit().equals(Auth.waitProcessSupplement) || company.getAudit().equals(Auth.auth)){
				url = "redirect:/system/company/companyinfo.do";
				
			}else if(company.getAudit().equals(Auth.notAuth)){
				url = "redirect:/aut/authenticat/editautcompany.do";
			}else if(company.getAudit().equals(Auth.supplement) && StringUtils.isBlank(company.getAut_supplement_type())){
				url = "redirect:/aut/authenticat/editsuppautcompany.do";
			}else if(company.getAudit().equals(Auth.supplement) && !StringUtils.isBlank(company.getAut_supplement_type())){
				url = "redirect:/aut/authenticat/editsuppautcompany.do";
			} 
			
		}else{
			Role role = roleService.getRole(account.getRole().getId());
			model.addAttribute("role",role);
			model.addAttribute("account",newAccount);
			if(newAccount.getAuthentication().equals(Auth.notapply)){
				url = "/aut/authenticat/saveautuser";
			}else if(newAccount.getAuthentication().equals(Auth.waitProcess) || newAccount.getAuthentication().equals(Auth.waitProcessSupplement) || newAccount.getAuthentication().equals(Auth.auth)){
				url = "redirect:/system/user/view/accountinfo.do";
			}else if(newAccount.getAuthentication().equals(Auth.notAuth)){
				url = "redirect:/aut/authenticat/editautuser.do";
			}else if(newAccount.getAuthentication().equals(Auth.supplement) && StringUtils.isBlank(newAccount.getAut_supplement_type())){
				url = "redirect:/aut/authenticat/supplementautuser.do";
			}else if(newAccount.getAuthentication().equals(Auth.supplement) && !StringUtils.isBlank(newAccount.getAut_supplement_type())){
				url = "redirect:/aut/authenticat/editsuppautuser.do";
			} 
		}
		
		return url;
	}
	
	/**
	* 功能描述：  商家认证信息审核未通过进入认证信息修改页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月12日下午12:08:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/editautcompany")
	protected String editAutCompany(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		CompanyType companyType = companyTypeService.getCompanyTypeById(account.getCompany().getCompanyType().getId());
		Company company = companyService.getCompanyById(account.getCompany().getId());
		model.addAttribute("companyType",companyType);
		model.addAttribute("company",company);
		if(!StringUtils.isEmpty(company.getIdcard_id())){
			model.addAttribute("idcard",idcardService.get(company.getIdcard_id()));
		}
		if(!StringUtils.isEmpty(company.getDriving_license_id())){
			model.addAttribute("drivingLicense",drivingLicenseService.get(company.getDriving_license_id()));
		}
		if(!StringUtils.isEmpty(company.getBusiness_license_id())){
			model.addAttribute("businessLicense",businessLicenseService.get(company.getBusiness_license_id()));
		}
		return "/aut/authenticat/editautcompany";
	}
	
	
	/**
	* 功能描述：  商家认证补录信息审核未通过进入认证信息修改页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月12日下午12:08:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/editsuppautcompany")
	protected String editSuppAutCompany(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		CompanyType companyType = companyTypeService.getCompanyTypeById(account.getCompany().getCompanyType().getId());
		Company company = companyService.getCompanyById(account.getCompany().getId());
		model.addAttribute("companyType",companyType);
		model.addAttribute("company",company);
		if(!StringUtils.isEmpty(company.getIdcard_id()) && company.getAut_supplement_type().indexOf("idcard") != -1){
			model.addAttribute("idcard",idcardService.get(company.getIdcard_id()));
		}
		if(!StringUtils.isEmpty(company.getDriving_license_id()) && company.getAut_supplement_type().indexOf("driving") != -1){
			model.addAttribute("drivingLicense",drivingLicenseService.get(company.getDriving_license_id()));
		}
		if(!StringUtils.isEmpty(company.getBusiness_license_id()) && company.getAut_supplement_type().indexOf("business") != -1){
			model.addAttribute("businessLicense",businessLicenseService.get(company.getBusiness_license_id()));
		}
		return "/aut/authenticat/editsuppautcompany";
	}
	
	/**
	* 功能描述： 商家认证信息补录页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月12日下午12:18:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/supplementautcompany")
	protected String supplementAutCompany(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		CompanyType companyType = companyTypeService.getCompanyTypeById(account.getCompany().getCompanyType().getId());
		Company company = companyService.getCompanyById(account.getCompany().getId());
		model.addAttribute("companyType",companyType);
		model.addAttribute("company",company);
		return "/aut/authenticat/supplementautcompany";
	}
	
	/**
	* 功能描述： 文件上传
	* 输入参数:  @param request
	* 输入参数:  @param file
	* 输入参数:  @return
	* 输入参数:  @throws IllegalStateException
	* 输入参数:  @throws IOException
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:08:51
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> uploadimg( HttpServletRequest request,@RequestParam(value = "file",required=false) MultipartFile file) throws IllegalStateException, IOException{
		String dataType = request.getParameter("datatype");
		Map<String, Object> map = new HashMap<String, Object>();
		if(file.isEmpty()){
            System.out.println("【文件为空！】");
    		map.put("success", false);
    		map.put("msg", "文件为空！");
        }
		String img_path = ImageFileUtil.uploadTemporary(file,AppUtil.getUpLoadPath(request),dataType) ; 
		map.put("success", true);
		map.put("msg", "上传成功！");
		map.put("imgpath", img_path);
        return  map;
	}
	
	
	
	/**
	* 功能描述： 商家认证信息保存
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @param companyAutInfo
	* 输入参数:  @return
	* 输入参数:  @throws IOException
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:09:05
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping(value = "/companyAutSave", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> companyAutSave(Model model, HttpServletRequest request,AutInfo companyAutInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = UserUtil.getUser(request);
		Account account1 = accountService.getAccount(account.getId());
		if(Auth.waitProcess.equals(account1.getCompany().getAudit())){ //待审核
			map.put("success", false);
			map.put("msg", "已提交认证资料，正在审核中，无需重复提交！");
		}else if(Auth.auth.equals(account1.getCompany().getAudit())){
			map.put("success", false);
			map.put("msg", "您所提交的资料已通过审核，无需重复提交！");
		}else{
			CompanyType companyType = companyTypeService.getCompanyTypeById(account.getCompany().getCompanyType().getId());
			String path = AppUtil.getUpLoadPath(request);
			Idcard idcard = null;
			DrivingLicense drivingLicense = null;
			BusinessLicense businessLicense = null;
			Company companyValue = null;
			if(!companyAutInfo.getType().equals("supplement") && !companyAutInfo.getType().equals("supplementedit") && account.getCompany().getSource() != 0){
				companyValue = JsonPluginsUtil.jsonToBean(companyAutInfo.getCompany_info(), Company.class);
			}
			String aut_supplement_type = "";
			if(companyType.getIs_aut()){
				if(companyType.getIdcard()){
					idcard = JsonPluginsUtil.jsonToBean(companyAutInfo.getIdcard_info(), Idcard.class);
					if(!StringUtils.isBlank(idcard.getIdcard_no())){
						aut_supplement_type += StringUtils.isEmpty(aut_supplement_type) ? "idcard":aut_supplement_type+",idcard";
					}
				}
				if(companyType.getDriving_license()){
					drivingLicense = JsonPluginsUtil.jsonToBean(companyAutInfo.getDriving_license_info(), DrivingLicense.class);
					if(!StringUtils.isBlank(drivingLicense.getDriving_license_no())){
						aut_supplement_type += StringUtils.isEmpty(aut_supplement_type) ? "driving":aut_supplement_type+",driving";
					}
				}
				if(companyType.getBusiness_license()){ 
					businessLicense = JsonPluginsUtil.jsonToBean(companyAutInfo.getBusiness_license_info(), BusinessLicense.class);
					if(!StringUtils.isBlank(businessLicense.getCompany_no())){
						aut_supplement_type += StringUtils.isEmpty(aut_supplement_type) ? "business":aut_supplement_type+",business";
					}
				}
			}
			map = authenticationService.saveAuthenticationCompantInfo(companyValue, idcard, drivingLicense, businessLicense,path,account,companyAutInfo.getType(),aut_supplement_type);
		}
		return  map;
	}
	
	/**
	* 功能描述： 用户认证信息保存
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @param userAutInfo
	* 输入参数:  @return
	* 输入参数:  @throws Exception
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月17日下午2:12:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "/userAutSave", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> userAutSave(Model model, HttpServletRequest request,AutInfo userAutInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = UserUtil.getUser(request);
		if(Auth.waitProcess.equals(account.getAuthentication())){ //待审核
			map.put("success", false);
			map.put("msg", "已提交认证资料，正在审核中，无需重复提交！");
		}else if(Auth.auth.equals(account.getAuthentication())){
			map.put("success", false);
			map.put("msg", "您所提交的资料已通过审核，无需重复提交！");
		}else{
			Role role = roleService.getRole(account.getRole().getId());
			String path = AppUtil.getUpLoadPath(request);
			Idcard idcard = null;
			DrivingLicense drivingLicense = null;
			String aut_supplement_type = "";
			if(role.getIs_aut()){
				if(role.getIdcard()){
					idcard = JsonPluginsUtil.jsonToBean(userAutInfo.getIdcard_info(), Idcard.class);
					if(!StringUtils.isBlank(idcard.getIdcard_no())){
						aut_supplement_type += StringUtils.isEmpty(aut_supplement_type) ? "idcard":aut_supplement_type+",idcard";
					}
				}
				if(role.getDriving_license()){
					drivingLicense = JsonPluginsUtil.jsonToBean(userAutInfo.getDriving_license_info(), DrivingLicense.class);
					if(!StringUtils.isBlank(drivingLicense.getDriving_license_no())){
						aut_supplement_type += StringUtils.isEmpty(aut_supplement_type) ? "driving":aut_supplement_type+",driving";
					}
				}
			}
			map = authenticationService.saveAuthenticationUserInfo(idcard, drivingLicense, path,account,userAutInfo.getType(),aut_supplement_type);
		}
		return  map;
	}
	
	
	/**
	* 功能描述：  商家认证信息审核未通过进入认证信息修改页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月12日下午12:08:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/editautuser")
	protected String editAutUser(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		Role role = roleService.getRole(account.getRole().getId());
		model.addAttribute("account",account);
		model.addAttribute("role",role);
		if(!StringUtils.isEmpty(account.getIdcard_id())){
			model.addAttribute("idcard",idcardService.get(account.getIdcard_id()));
		}
		if(!StringUtils.isEmpty(account.getDriving_license_id())){
			model.addAttribute("drivingLicense",drivingLicenseService.get(account.getDriving_license_id()));
		}
		return "/aut/authenticat/editautuser";
	}
	
	
	/**
	* 功能描述： 用户认证信息补录页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月12日下午12:18:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/supplementautuser")
	protected String supplementAutUserser(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		Role role = roleService.getRole(account.getRole().getId());
		model.addAttribute("account",account);
		model.addAttribute("role",role);
		return "/aut/authenticat/supplementautuser";
	}
	
	
	
	/**
	* 功能描述：  用户认证补录信息审核未通过进入认证信息修改页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月12日下午12:08:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/editsuppautuser")
	protected String editsuppAutUser(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		Role role = roleService.getRole(account.getRole().getId());
		model.addAttribute("account",account);
		model.addAttribute("role",role);
		if(!StringUtils.isEmpty(account.getIdcard_id()) && account.getAut_supplement_type().indexOf("idcard") != -1){
			model.addAttribute("idcard",idcardService.get(account.getIdcard_id()));
		}
		if(!StringUtils.isEmpty(account.getDriving_license_id()) && account.getAut_supplement_type().indexOf("driving") != -1){
			model.addAttribute("drivingLicense",drivingLicenseService.get(account.getDriving_license_id()));
		}
		return "/aut/authenticat/editsuppautuser";
	}
	
	/**
	* 功能描述：  验证商户名称是否重复
	* 输入参数:  @param model
	* 输入参数:  @param name
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:19:03
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	@RequestMapping(value = "/checkCompanyName",method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkName(Model model,String name,String id,HttpServletRequest request) {
		Company company = companyService.getCompanyByName(name,id);
		return company==null?true:false;
	}
	
	
	/**
	* 功能描述：  验证商户名称是否重复
	* 输入参数:  @param model
	* 输入参数:  @param name
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:19:03
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 * @throws Exception 
	 */
	@RequestMapping(value = "/checkIdcardNo",method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkIdcardNo(Model model,String idcard_no,String id,HttpServletRequest request) throws Exception {
		Idcard idcard = idcardService.getNo(AES.Encrypt(idcard_no),id);
		return idcard==null?true:false;
	}
	
	/**
	* 功能描述： 检测驾驶证编号是否存在
	* 输入参数:  @param model
	* 输入参数:  @param driving_license_no
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:58:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	@RequestMapping(value = "/checkDrivingLicenseNo",method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkDrivingLicenseNo(Model model,String driving_license_no,String id,HttpServletRequest request) {
		DrivingLicense drivingLicense = drivingLicenseService.getDrivingLicenseByNo(driving_license_no,id);
		return drivingLicense==null?true:false;
	}
	
	/**
	* 功能描述：  检测营业执照编号是否存在
	* 输入参数:  @param model
	* 输入参数:  @param company_no
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月9日下午3:25:18
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	@RequestMapping(value = "/checkBusinessLicenseNo",method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkBusinessLicenseNo(Model model,String company_no,String id,HttpServletRequest request) {
		BusinessLicense businessLicense = businessLicenseService.getBusinessLicenseByNo(company_no,id);
		return businessLicense==null?true:false;
	}
	
}
