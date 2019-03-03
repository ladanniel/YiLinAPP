package com.memory.platform.module.app.aut.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.aut.AutInfo;
import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.base.DrivingLicenseType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.Auth;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.aut.service.IAuthenticationService;
import com.memory.platform.module.aut.service.IBusinessLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseTypeService;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ICompanyTypeService;
import com.memory.platform.module.system.service.IRoleService;

/**
 * 创 建 人： longqibo 日 期： 2016年8月25日 上午10:25:36 修 改 人： 日 期： 描 述： app认证接口控制器 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("app/auth/")
public class AuthenticationController extends BaseController {

	@Autowired
	private IAuthenticationService authenticationService;
	@Autowired
	private ICompanyTypeService companyTypeService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IDrivingLicenseTypeService drivingLicenseTypeService;
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IDrivingLicenseService drivingLicenseService;
	@Autowired
	private IBusinessLicenseService businessLicenseService;
	@Autowired
	private IIdcardService idcardService;
	@Autowired
	private IAccountService accountService;

	/**
	 * 功能描述： 获取商户认证类型信息 输入参数: @param request 输入参数: @param headers 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年8月25日上午11:51:00 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "/getAuthInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAuthInfo(HttpServletRequest request,
			@RequestHeader HttpHeaders headers) {
		Account account = UserUtil.getAccount();
		CompanyType companyType = companyTypeService.getCompanyTypeById(account
				.getCompany().getCompanyType().getId());
		Map<String, Object> map = new HashMap<String, Object>();
		if (companyType.getIs_aut()) {

			if (account.getUserType().equals(Account.UserType.company)) { // 商户认证
				map.put("company", "company");
			} else {
				map.put("company", null);
			}
			if (companyType.getBusiness_license()) {
				map.put("business", "business");
			} else {
				map.put("business", null);
			}
			if (companyType.getIdcard()) {
				map.put("idcard", "idcard");
			} else {
				map.put("idcard", null);
			}
			if (companyType.getDriving_license()) {
				map.put("driving", "driving");
			} else {
				map.put("driving", null);
			}
			return jsonView(true, "成功获取商户认证类型信息。", map);
		}

		return jsonView(false, "该商户不需要认证", null);
	}

	/**
	 * 功能描述： 获取准驾车型列表 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年8月26日上午11:17:16 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/getQuasiDrivingType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getQuasiDrivingType() {
		List<DrivingLicenseType> list = drivingLicenseTypeService
				.getDrivingLicenseTypeList();
		return jsonView(true, "成功获取准驾车型。", list);
	}

	/**
	 * 功能描述： 文件上传 输入参数: @param imgArry 输入参数: @param dataType 输入参数: @param
	 * headers 输入参数: @param request 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年9月20日下午11:34:53 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "uploadimg", method = RequestMethod.POST)
	@UnInterceptor
	@ResponseBody
	public Map<String, Object> updateimg(
			@RequestParam(value = "img", required = false) MultipartFile[] imgArry,
			String dataType, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		String path = "";
		for (MultipartFile img : imgArry) {
			try {
				String img_path = ImageFileUtil.uploadTemporary(img,
						AppUtil.getUpLoadPath(request), dataType);
				path += img_path + ",";
			} catch (IOException e) {
				return jsonView(false, "上传图片异常请重试!", null);
			}

		}
		path = path.substring(0, path.length() - 1);
		return jsonView(true, "上传图片成功", path);
	}

	/**
	 * 功能描述： 商家认证信息保存 输入参数: @param model 输入参数: @param request 输入参数: @param
	 * companyAutInfo 输入参数: @return 输入参数: @throws IOException 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年5月5日下午7:09:05 修 改 人: 日 期: 返 回：Map<String,Object>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyAutSave", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> companyAutSave(AutInfo companyAutInfo)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = UserUtil.getAccount();
		if(account.getIsAdmin() == false) { //不是管理账号
		 return 	this.userAutSave( companyAutInfo); 
		} 
		if (Auth.waitProcess.equals(account.getCompany().getAudit())) { // 待审核
			return jsonView(false, "已提交认证资料，正在审核中，无需重复提交！");
		} else if (Auth.auth.equals(account.getCompany().getAudit())) {
			return jsonView(false, "您所提交的资料已通过审核，无需重复提交！");
		} else {
			CompanyType companyType = companyTypeService
					.getCompanyTypeById(account.getCompany().getCompanyType()
							.getId());
			String path = AppUtil.getUpLoadPath(request);
			Idcard idcard = null;
			DrivingLicense drivingLicense = null;
			BusinessLicense businessLicense = null;
			Company companyValue = account.getCompany();
			Company company = account.getCompany();
			if (!companyAutInfo.getType().equals("supplement")
					&& !companyAutInfo.getType().equals("supplementedit")
					&& account.getCompany().getSource() != 0) {
				companyValue = JsonPluginsUtil.jsonToBean(
						companyAutInfo.getCompany_info(), Company.class);
				companyValue.setId(company.getId());
			}
			if (StringUtil.isEmpty(companyValue.getName())) {
				return jsonView(false, "商户名称不能为空。");
			}
			if (StringUtil.isEmpty(companyValue.getContactName())) {
				return jsonView(false, "联系人姓名不能为空。");
			}
			if (StringUtil.isEmpty(companyValue.getContactTel())) {
				return jsonView(false, "联系人电话不能为空。");
			}
			if (StringUtil.isEmpty(companyValue.getAreaFullName())) {
				return jsonView(false, "地区不能为空。");
			}
			if (StringUtil.isEmpty(companyValue.getAddress())) {
				return jsonView(false, "地址不能为空。");
			}
			String aut_supplement_type = "";
			if (companyService.getCompanyByName(companyValue.getName(), account
					.getCompany().getId()) != null) {
				return jsonView(false, "该商户名称已存在。");
			}
			if (companyType.getIs_aut()) {
				if (companyType.getIdcard()) {

					idcard = JsonPluginsUtil.jsonToBean(
							companyAutInfo.getIdcard_info(), Idcard.class);
					if (StringUtil.isEmpty(idcard.getName())) {
						return jsonView(false, "真实姓名不能为空。");
					}
					if (StringUtil.isEmpty(idcard.getIdcard_no())) {
						return jsonView(false, "身份证号码不能为空。");
					}
					if (StringUtil.isEmpty(idcard.getIdcard_back_img())) {
						return jsonView(false, "请上传身份证正面照。");
					}
					if (StringUtil.isEmpty(idcard.getIdcard_front_img())) {
						return jsonView(false, "请上传身份证反面照。");
					}
					if (StringUtil.isEmpty(idcard.getIdcard_persoin_img())) {
						return jsonView(false, "请上传手持身份证证件照。");
					}
					if (null != idcardService.getNo(idcard.getIdcard_no(),
							idcard.getId())) {
						return jsonView(false, "身份证编号已被使用。");
					}
					aut_supplement_type += StringUtils
							.isEmpty(aut_supplement_type) ? "idcard"
							: aut_supplement_type + ",idcard";
				}
				if (companyType.getDriving_license()) {
					drivingLicense = JsonPluginsUtil.jsonToBean(
							companyAutInfo.getDriving_license_info(),
							DrivingLicense.class);
					if (StringUtil.isEmpty(drivingLicense.getName())) {
						return jsonView(false, "驾驶员姓名不能为空。");
					}
					if (null == drivingLicense.getValid_start_date()) {
						return jsonView(false, "驾驶证有效期不能为空。");
					}
					if (StringUtil.isEmpty(drivingLicense
							.getDriving_license_type_id())) {
						return jsonView(false, "请选择准驾车型。");
					}
					if (StringUtil.isEmpty(drivingLicense
							.getDriving_license_no())) {
						return jsonView(false, "驾驶证编号不能为空。");
					}
					if (StringUtil.isEmpty(drivingLicense.getAddress())) {
						return jsonView(false, "地址不能为空。");
					}
					if (StringUtil.isEmpty(drivingLicense
							.getDriving_license_img())) {
						return jsonView(false, "驾驶证件照不能为空。");
					}
					if (null != drivingLicenseService.getDrivingLicenseByNo(
							drivingLicense.getDriving_license_no(),
							drivingLicense.getId())) {
						return jsonView(false, "驾驶证件编号已被使用。");
					}
					aut_supplement_type += StringUtils
							.isEmpty(aut_supplement_type) ? "driving"
							: aut_supplement_type + ",driving";
				}
				if (companyType.getBusiness_license()) {
					businessLicense = JsonPluginsUtil.jsonToBean(
							companyAutInfo.getBusiness_license_info(),
							BusinessLicense.class);
					if (StringUtil.isEmpty(businessLicense.getCompany_no())) {
						return jsonView(false, "营业执照编号不能为空。");
					}
					if (StringUtil.isEmpty(businessLicense.getName())) {
						return jsonView(false, "企业名称不能为空。");
					}
					if (null == businessLicense.getCompany_create_date()
							|| null == businessLicense
									.getCompany_validity_date()) {
						return jsonView(false, "营业执照有效时间不能为空。");
					}
					if (StringUtil
							.isEmpty(businessLicense.getCompany_address())) {
						return jsonView(false, "公司地址不能为空。");
					}
					if (StringUtil.isEmpty(businessLicense.getCompany_no())) {
						return jsonView(false, "营业执照编号不能为空。");
					}
					if (StringUtil.isEmpty(businessLicense.getCompany_img())) {
						return jsonView(false, "请上传营业执照图片。");
					}
					if (null != businessLicenseService.getBusinessLicenseByNo(
							businessLicense.getCompany_no(),
							businessLicense.getId())) {
						return jsonView(false, "营业执照编号已被使用。");
					}
					aut_supplement_type += StringUtils
							.isEmpty(aut_supplement_type) ? "business"
							: aut_supplement_type + ",business";
				}
			}
			map = authenticationService.saveAuthenticationCompantInfo(
					companyValue, idcard, drivingLicense, businessLicense,
					path, account, companyAutInfo.getType(),
					aut_supplement_type);
		}
		// 刷新当前会话用户
		account = accountService.getAccount(account.getId());
		AppUtil.setLoginUser(account);
		return map;
	}

	public static void main(String[] args) {

		String json = "{\"company_address\": \"vhc-j\",\"company_create_date\": \"2017-10-10\",}";
		json = "{\"company_create_date\": \"2017-10-11\"}";
		json = "{\"company_create_date\":\"2017-10-11\",\"company_address\":\"大多数\",\"company_img\":\"http://qibogu.oss-cn-shenzhen.aliyuncs.com/emergencyImg/58b77e55-4221-42ef-aa58-7739da1f2dca.?Expires=1823055725&OSSAccessKeyId=LTAIdkoSD77S2lJM&Signature=zjQYC8hqUjUUIRWfw8DJcMDUP5Y%3D\",\"company_validity_date\":\"2018-10-11\",\"company_no\":\"1235446666\",\"name\":\"说说\"}";
		json = "{, \"company_create_date\":\"2017-10-11\",\"company_address\": \"大多数\"}";
		BusinessLicense lisLicense = JsonPluginsUtil.jsonToBean(json,
				BusinessLicense.class);

	}

	/**
	 * 功能描述： 用户认证信息保存 输入参数: @param model 输入参数: @param request 输入参数: @param
	 * userAutInfo 输入参数: @return 输入参数: @throws Exception 异 常： 创 建 人: yangjiaqiao
	 * 日 期: 2016年5月17日下午2:12:49 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/userAutSave", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> userAutSave( 
			AutInfo userAutInfo )
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = UserUtil.getAccount();
		if (Auth.waitProcess.equals(account.getAuthentication())) { // 待审核
			map.put("success", false);
			map.put("msg", "已提交认证资料，正在审核中，无需重复提交！");
		} else if (Auth.auth.equals(account.getAuthentication())) {
			map.put("success", false);
			map.put("msg", "您所提交的资料已通过审核，无需重复提交！");
		} else {
			Role role = roleService.getRole(account.getRole().getId());
			String path = AppUtil.getUpLoadPath(request);
			Idcard idcard = null;
			DrivingLicense drivingLicense = null;
			String aut_supplement_type = "";
			if (role.getIs_aut()) {
				if (role.getIdcard()) {
					idcard = JsonPluginsUtil.jsonToBean(
							userAutInfo.getIdcard_info(), Idcard.class);
					if (StringUtil.isEmpty(idcard.getName())) {
						return jsonView(false, "真实姓名不能为空。");
					}
					if (StringUtil.isEmpty(idcard.getIdcard_no())) {
						return jsonView(false, "身份证号码不能为空。");
					}
					if (StringUtil.isEmpty(idcard.getIdcard_back_img())) {
						return jsonView(false, "请上传身份证正面照。");
					}
					if (StringUtil.isEmpty(idcard.getIdcard_front_img())) {
						return jsonView(false, "请上传身份证反面照。");
					}
					if (StringUtil.isEmpty(idcard.getIdcard_persoin_img())) {
						return jsonView(false, "请上传手持身份证证件照。");
					}
					if (null != idcardService.getNo(idcard.getIdcard_no(),
							idcard.getId())) {
						return jsonView(false, "身份证编号已被使用。");
					}
					aut_supplement_type += StringUtils
							.isEmpty(aut_supplement_type) ? "idcard"
							: aut_supplement_type + ",idcard";
				}
				if (role.getDriving_license()) {
					drivingLicense = JsonPluginsUtil.jsonToBean(
							userAutInfo.getDriving_license_info(),
							DrivingLicense.class);
					if (StringUtil.isEmpty(drivingLicense.getName())) {
						return jsonView(false, "驾驶员姓名不能为空。");
					}
					if (null == drivingLicense.getValid_start_date()) {
						return jsonView(false, "驾驶证有效期不能为空。");
					}
					if (StringUtil.isEmpty(drivingLicense
							.getDriving_license_type_id())) {
						return jsonView(false, "请选择准驾车型。");
					}
					if (StringUtil.isEmpty(drivingLicense
							.getDriving_license_no())) {
						return jsonView(false, "驾驶证编号不能为空。");
					}
					if (StringUtil.isEmpty(drivingLicense.getAddress())) {
						return jsonView(false, "地址不能为空。");
					}
					if (StringUtil.isEmpty(drivingLicense
							.getDriving_license_img())) {
						return jsonView(false, "驾驶证件照不能为空。");
					}
					if (null != drivingLicenseService.getDrivingLicenseByNo(
							drivingLicense.getDriving_license_no(),
							drivingLicense.getId())) {
						return jsonView(false, "驾驶证件编号已被使用。");
					}
					aut_supplement_type += StringUtils
							.isEmpty(aut_supplement_type) ? "driving"
							: aut_supplement_type + ",driving";
				}
			}
			map = authenticationService.saveAuthenticationUserInfo(idcard,
					drivingLicense, path, account, userAutInfo.getType(),
					aut_supplement_type);
			//认证成功重新保存状态
			account = accountService.getAccount(account.getId());
			AppUtil.setLoginUser(account);
		}
		return map;
	}

	/**
	 * 功能描述： 获取认证信息 输入参数: @param request 输入参数: @param headers 输入参数: @return 异 常：
	 * 创 建 人: longqibo 日 期: 2016年9月22日下午2:46:22 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "/getAutInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAutInfo(HttpServletRequest request,
			@RequestHeader HttpHeaders headers) {
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = UserUtil.getAccount();
		if (null == account) {
			return jsonView(false, "请登陆后再操作。");
		}
		Map<String, Object> company = companyService
				.getCompanyByAccountId(account.getId());
		if (account.getUserType().equals(Account.UserType.company)) {
			map.put("company", company);
			if (null != company.get("idcard_id")) {
				Idcard card = idcardService.get(company.get("idcard_id")
						.toString());
				try {
					card.setIdcard_no1(card.getIdcard_no());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put("idcard", card);
			} else {
				map.put("idcard", null);
			}
			if (null != company.get("driving_license_id")) {
				DrivingLicense license = drivingLicenseService.get(company.get(
						"driving_license_id").toString());
				String id = license.getDriving_license_type_id();
				try {
					license.setDriving_license_no1(license
							.getDriving_license_no());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (StringUtil.isNotEmpty(id)) {
					license.setDrivingLicenseType(drivingLicenseTypeService
							.get(id));
				}
				map.put("drivingLicense", license);
			} else {
				map.put("drivingLicense", null);
			}
			if (null != company.get("business_license_id")) {
				map.put("businessLicense",
						businessLicenseService.get(company.get(
								"business_license_id").toString()));
			} else {
				map.put("businessLicense", null);
			}
			map.put("autInfo", account.getCompany().getAudit());
		} else {
			map.put("company", null);
			if (null != account.getIdcard_id()) {
				 Idcard idcard = idcardService.get(account.getIdcard_id());
				 try {
					idcard.setIdcard_no1(idcard.getIdcard_no());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put("idcard", idcard);
			} else {
				map.put("idcard", null);
			}
			if (null != account.getDriving_license_id()) {
				DrivingLicense license = drivingLicenseService.get(account.getDriving_license_id());
				String id = license.getDriving_license_type_id();
				try {
					license.setDriving_license_no1(license
							.getDriving_license_no());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (StringUtil.isNotEmpty(id)) {
					license.setDrivingLicenseType(drivingLicenseTypeService
							.get(id));
				}
				map.put("drivingLicense", license);
			} else {
				map.put("drivingLicense", null);
			}
			map.put("businessLicense", null);
			map.put("autInfo", account.getAuthentication());
		}

		return jsonView(true, "成功获取认证信息。", map);
	}

}
