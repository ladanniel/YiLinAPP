package com.memory.platform.module.system.controller;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jpush.api.report.UsersResult.User;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.global.UpdateColume;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.RoleType;
import com.memory.platform.entity.member.Account.Status;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.Auth;
import com.memory.platform.global.SMSUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.aut.service.IDrivingLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseTypeService;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.my.dao.MyDao;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.system.service.impl.CompanySectionService;
import com.memory.platform.security.annotation.LoginValidate;

@Controller
@RequestMapping("/system/user")
public class AccountController extends BaseController {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CompanySectionService companySectionService;

	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IIdcardService idcardService; // 身份证业务接口
	@Autowired
	private IDrivingLicenseService drivingLicenseService; // 驾驶证业务接口
	@Autowired
	private IDrivingLicenseTypeService drivingLicenseTypeService; // 准假车型业务接口
	@Autowired
	private ISendMessageService sendMessageService;
	Logger log = Logger.getLogger(AccountController.class);

	@Autowired
	private MyDao myDao;

	/**
	 * 显示登陆页面
	 * 
	 * @param userId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/view/login", method = RequestMethod.GET)
	@LoginValidate(false)
	protected String vieWlogin(String userId, Model model,
			HttpServletRequest request) {

		return "/system/user/login";
	}

	/**
	 * 用户登陆
	 * 
	 * @param request
	 * @param account
	 * @param pass
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@LoginValidate(false)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request,
			String account, String pass, String code) {

		HttpSession session = request.getSession();
		String codeSession = session.getAttribute("code").toString();
		if (!code.equals(codeSession)) {
			return jsonView(false, "输入的验证码不正确，请重新输入！");
		}
		Account accountUser = this.accountService.accountLogin(account, pass);
		if (null == accountUser) {
			return jsonView(false, "您输入的用户名不存在！");
		}
		if (!accountUser.getPassword().equals(AppUtil.md5(pass))) {
			return jsonView(false, "您输入的密码不正确！");
		}
		if (accountUser.getStatus().equals(Account.Status.stop)) {
			return jsonView(false, "该用户已停用，无法登陆，请联系管理员！");
		}
		if (accountUser.getStatus().equals(Account.Status.delete)) {
			return jsonView(false, "该用户已删除，无法登陆，请联系管理员！");
		}

		if (accountUser.getCompany().getStatus().equals(Company.Status.colse)) {
			return jsonView(false, "该商户已关闭，无法登陆，请联系管理员！");
		}

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				account, pass);
		Authentication authentication = authenticationManager
				.authenticate(authRequest); // 调用loadUserByUsername
		SecurityContextHolder.getContext().setAuthentication(authentication);
		session.setAttribute("SPRING_SECURITY_CONTEXT",
				SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		request.getSession().setAttribute("USER", accountUser);

		UserType userType = accountUser.getUserType();
		// 判断商户是否补录
		if (accountUser.getCompany().getCompanyType().getIs_aut()
				&& accountUser.getCompany().getAudit().equals(Auth.auth)
				&& UserType.company.equals(userType)) {
			supplementCheckCompany(accountUser);
		}

		// 判断用户是否补录
		if (accountUser.getRole().getIs_aut()
				&& accountUser.getAuthentication().equals(Auth.auth)) {
			supplementCheckUser(accountUser);
		}

		accountUser.setLogin_ip(AppUtil.getIp(request));// 登录IP
		accountUser.setLogin_count(accountUser.getLogin_count() + 1); // 登录次数加1
		accountUser.setLast_login_time(new Date());
		this.accountService.updateAccount(accountUser);

		return jsonView(true, "登陆成功！");
	}

	// 验证商户是否需要补录
	public void supplementCheckCompany(Account accountUser) {
		Company company = accountUser.getCompany();
		CompanyType companyType = accountUser.getCompany().getCompanyType();
		String msg = "";
		if (companyType.getBusiness_license()
				&& StringUtils.isBlank(company.getBusiness_license_id())) {
			msg += "营业执照证件,";
		}
		if (companyType.getDriving_license()
				&& StringUtils.isBlank(company.getDriving_license_id())) {
			msg += "驾驶证证件,";
		}
		if (companyType.getIdcard()
				&& StringUtils.isBlank(company.getIdcard_id())) {
			msg += "身份证证件,";
		}
		if (!StringUtils.isBlank(msg)) {
			company.setAudit(Auth.supplement);
			msg = msg.substring(0, msg.length() - 1);
			company.setFailed_msg("认证系统统升级，您需要补录" + msg);
			companyService.updateCompany(company);
		} else {
			company.setAudit(Auth.auth);
			company.setFailed_msg("");
			companyService.updateCompany(company);
		}

	}

	// 验证用户是否需要补录
	public void supplementCheckUser(Account accountUser) {
		Role role = accountUser.getRole();
		String msg = "";
		if (role.getDriving_license()
				&& StringUtils.isBlank(accountUser.getDriving_license_id())) {
			msg += "驾驶证证件,";
		}
		if (role.getIdcard() && StringUtils.isBlank(accountUser.getIdcard_id())) {
			msg += "身份证证件,";
		}

		if (!StringUtils.isBlank(msg)) {
			accountUser.setAuthentication(Auth.supplement);
			msg = msg.substring(0, msg.length() - 1);
			accountUser.setFailed_msg("认证系统统升级，您需要补录" + msg);
			accountService.updateUser(accountUser);
		} else {
			accountUser.setAuthentication(Auth.auth);
			accountUser.setFailed_msg("");
			accountService.updateUser(accountUser);
		}

	}

	@RequestMapping("logout")
	@LoginValidate(false)
	public String logout(HttpServletRequest request) {
		// User user = UserUtil.getUser(request);
		// userService.updateUserStart(user.getId(), false);
		request.getSession().invalidate();
		return "/system/user/login";
	}

	@RequestMapping("view/accountinfo")
	public String accountInfo(HttpServletRequest request, Model model) {
		Account account = UserUtil.getUser(request);
		Role role = account.getRole();
		if (Auth.notapply.equals(account.getAuthentication())) {
			model.addAttribute("account", account);
			model.addAttribute("role", role);
		} else if (Auth.waitProcess.equals(account.getAuthentication())
				|| Auth.waitProcessSupplement.equals(account
						.getAuthentication())
				|| Auth.notAuth.equals(account.getAuthentication())
				|| Auth.auth.equals(account.getAuthentication())) {
			model.addAttribute("account", account);
			model.addAttribute("role", role);
			if (!StringUtils.isEmpty(account.getIdcard_id())) {
				model.addAttribute("idcard",
						idcardService.get(account.getIdcard_id()));
			}
			if (!StringUtils.isEmpty(account.getDriving_license_id())) {
				DrivingLicense drivingLicense = drivingLicenseService
						.get(account.getDriving_license_id());
				drivingLicense.setDrivingLicenseType(drivingLicenseTypeService
						.get(drivingLicense.getDriving_license_type_id()));
				model.addAttribute("drivingLicense", drivingLicense);
			}
		}
		return "/system/user/accountinfo";
	}

	/**
	 * 
	 * 功能描述： 修改用户的名称页面 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yico-cj 日
	 * 期: 2016年5月2日下午1:23:28 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping("view/editname")
	public String editname(HttpServletRequest request) {
		return "/system/user/editname";
	}

	/**
	 * 
	 * 功能描述： 修改用户名称 输入参数: @param request 输入参数: @param name 输入参数: @return 异 常： 创
	 * 建 人: yico-cj 日 期: 2016年5月2日下午1:23:46 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "editname")
	@ResponseBody
	public Map<String, Object> editName(HttpServletRequest request, String name) {
		Account account = (Account) request.getSession().getAttribute("USER");
		return accountService.editName(account.getId(), name, request);
	}

	/**
	 * 
	 * 功能描述： 修改绑定手机号页面 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yico-cj 日
	 * 期: 2016年5月2日下午1:24:02 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping("view/editphone")
	public String editphone(HttpServletRequest request) {
		return "/system/user/editphone";
	}

	/**
	 * 
	 * 功能描述： 修改绑定手机号 输入参数: @param request 输入参数: @param phone 输入参数: @param code
	 * 输入参数: @return 异 常： 创 建 人: yico-cj 日 期: 2016年5月2日下午1:24:17 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "editphone")
	@ResponseBody
	public Map<String, Object> editPhone(HttpServletRequest request,
			String phone, String code, String codeType) {
		Account account = (Account) request.getSession().getAttribute("USER");
		return accountService.editPhone(account.getId(), phone, code, codeType,
				request);
	}

	/**
	 * 
	 * 功能描述： 修改用户登陆密码页面 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yico-cj 日
	 * 期: 2016年5月2日下午1:24:02 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping("view/editpassword")
	public String editpassword(HttpServletRequest request) {
		return "/system/user/editpassword";
	}

	/**
	 * 
	 * 功能描述： 修改用户登陆密码 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yico-cj 日
	 * 期: 2016年5月2日下午1:24:02 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping("editpassword")
	@ResponseBody
	public Map<String, Object> editPassword(HttpServletRequest request,
			String password) {
		Account account = (Account) request.getSession().getAttribute("USER");

		return accountService.editPassword(account.getId(), password);
	}

	/**
	 * 功能描述： 修改支付密码页面 输入参数: @param request 输入参数: @return 异 常： 创 建 人: longqibo 日
	 * 期: 2016年8月8日下午5:56:36 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping("view/editPayPassword")
	public String editPayPassword(HttpServletRequest request) {
		return "/system/user/editpaypassword";
	}

	/**
	 * 功能描述： 修改支付密码 输入参数: @param request 输入参数: @param password 输入参数: @return 异
	 * 常： 创 建 人: longqibo 日 期: 2016年8月8日下午5:46:54 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("editPayPassword")
	@ResponseBody
	public Map<String, Object> editPayPassword(HttpServletRequest request,
			String password) {
		Account account = UserUtil.getUser(request);
		Account user = accountService.getAccount(account.getId());
		user.setPaypassword(AppUtil.md5(password));
		accountService.updateAccount(user);
		request.getSession().setAttribute("USER", user);
		return jsonView(true, "成功修改支付密码");
	}

	/**
	 * 
	 * 功能描述： 根据验证码验证手机是否一直 输入参数: @param request 输入参数: @param password 输入参数: @param
	 * repassword 输入参数: @return 异 常： 创 建 人: yico-cj 日 期: 2016年5月15日上午11:35:19 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("validPhone")
	@ResponseBody
	public Map<String, Object> validPhone(HttpServletRequest request,
			String code, String codeType) {
		Account user = UserUtil.getUser(request);
		return accountService.validPhone(user.getPhone(), code, codeType);
	}

	/**
	 * 功能描述： 商户员工管理列表 输入参数: @param userId 输入参数: @param model 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月29日下午4:14:13 修 改
	 * 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/personnel", method = RequestMethod.GET)
	protected String personnel(String userId, Model model,
			HttpServletRequest request) {
		List<CompanySection> list = companySectionService
				.getListByCompany(UserUtil.getUser().getCompany().getId());
		JSONArray json = new JSONArray();
		for (CompanySection menu : list) {
			JSONObject jo = new JSONObject();
			jo.put("id", menu.getId());
			if (StringUtil.isEmpty(menu.getParent_id())
					|| menu.getParent_id().equals("0")) {
				jo.put("parent", "#");
			} else {
				jo.put("parent", menu.getParent_id());
			}
			jo.put("text", menu.getName());
			json.put(jo);
		}
		model.addAttribute("json", json);
		return "/system/user/accountpersonnel";
	}

	/**
	 * 功能描述： 添加用户（添加员工）页面 输入参数: @param userId 输入参数: @param model 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月29日下午5:37:37 修 改
	 * 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/addpersonnel", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		return "/system/user/addpersonnel";
	}

	/**
	 * 功能描述： 编辑用户（编辑员工）页面 输入参数: @param userId 输入参数: @param model 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月29日下午5:38:45 修 改
	 * 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/editpersonnel", method = RequestMethod.GET)
	protected String edit(String userId, Model model, HttpServletRequest request) {
		Account account = accountService.getAccount(userId);
		account.setPassword(null);
		account.setPaypassword(null);
		model.addAttribute("user", account);
		return "/system/user/editpersonnel";
	}

	/**
	 * 功能描述： 分页显示账号列表 输入参数: @param cashApplication 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年4月29日下午4:17:59 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Account account,
			String scompanySectionId, HttpServletRequest request) {
		account.setCompany(UserUtil.getUser().getCompany());
		CompanySection companySection = new CompanySection();
		companySection.setId(scompanySectionId);
		account.setCompanySection(companySection);
		Map<String, Object> map = this.accountService.getPage(account,
				getStart(request), getLimit(request));
		return map;
	}

	/**
	 * 功能描述： 添加员工信息 输入参数: @param account 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月3日下午2:00:18 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Account account) {
		if (StringUtil.isEmpty(account.getAccount())) {
			throw new DataBaseException("账号不能为空！");
		}
		if (!StringUtil.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)) {
			throw new DataBaseException("账号由小写英文字母开头数字或小写英文字母组成并且长度为6到16位！");
		}
		/*
		 * if(StringUtil.isEmpty(account.getPassword())){ throw new
		 * DataBaseException("密码不能为空！"); }
		 */
		/*
		 * if(account.getPassword().length() < 6 ||
		 * account.getPassword().length() > 12){ throw new
		 * DataBaseException("密码长度为6到16位！"); }
		 */
		if (StringUtil.isEmpty(account.getName())) {
			throw new DataBaseException("姓名不能为空！");
		}
		if (StringUtil.isEmpty(account.getPhone())) {
			throw new DataBaseException("手机号码不能为空！");
		}
		if (!StringUtil.regExp(account.getPhone(), StringUtil.MOBILE_REG)) {
			throw new DataBaseException("手机号码错误！");
		}
		if (StringUtil.isEmpty(account.getStatus().name())) {
			throw new DataBaseException("状态不能为空！");
		}

		/*
		 * // lix 2017-08-29 注释修改 判断用户账号是否重复 if (null !=
		 * accountService.checkAccount(account.getAccount())) { throw new
		 * DataBaseException("用户名存在！"); }
		 */
		Account loginuserAccount = UserUtil.getAccount();
		Account accountuser = null;
		if (loginuserAccount.getRole_type() == RoleType.consignor ) {// 货主
			accountuser = accountService.checkAccount(account.getAccount() + "_hz");
			
		}else  if(loginuserAccount.getRole_type() == RoleType.truck) { // 车队
			accountuser = accountService.checkAccount(account.getAccount() + "_cd");
		}
		else {
			accountuser = accountService.checkAccount(account.getAccount());
		}
		if (accountuser != null) {
			throw new DataBaseException("用户名存在！");
		}
		/*
		 * aqw 2017/03/28注释 if(null !=
		 * accountService.checkAccountByPhone(account.getPhone())){ throw new
		 * DataBaseException("手机号码已存在！"); }
		 */
		Map<String, Object> map = SMSUtil.checkPhoneCode(account.getPhone(),
				account.getCode(), "1");
		Boolean flag = (Boolean) map.get("success");
		if (!flag) {
			throw new DataBaseException("验证码不正确！");
		}

		String password = AppUtil.random(8).toString();
		account.setPassword(password);
		Account user = UserUtil.getUser();
		account.setCompany(user.getCompany());
		account.setAdd_user_id(user.getId());
		if (account.getCompany().getCompanyType().getId().equals("2")) {
			account.setRole_type(RoleType.consignor);
			account.setAccount(account.getAccount() + "_hz");
		} else  if(account.getCompany().getCompanyType().getId().equals("4")){
			account.setRole_type(RoleType.truck);
			account.setAccount(account.getAccount() + "_cd");
		}else {
			account.setRole_type(UserUtil.getUser().getRole_type());
			account.setAccount(account.getAccount());
		}
		this.accountService.saveUser(account);

		Account admin = UserUtil.getUser();

		String inPoint = "com.memory.platform.module.system.service.impl.AccountService.saveUser";
		String sendMsg = "尊敬的用户您好，您的管理员" + admin.getName()
				+ "已为您成功注册易林物流平台账号。您的账号为：" + account.getPhone() + "，密码为："
				+ password
				+ "，请及时登录www.ylwuliu.cn修改您的密码。如忘记密码可请管理员重置或登录平台使用密码找回功能找回您的密码。";
		Map<String, Object> map_v = sendMessageService
				.saveRecordAndSendMessage(account.getPhone(), sendMsg, inPoint);
		if (!Boolean.valueOf(map_v.get("success").toString())) {
			throw new BusinessException("短信提示异常，请联系管理员,添加员工失败。");
		}

		return jsonView(true, SAVE_SUCCESS);
	}

	/**
	 * 功能描述： 更新员工信息 输入参数: @param account 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月3日下午2:01:28 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Account account) {
		if (StringUtil.isEmpty(account.getAccount())) {
			throw new DataBaseException("账号不能为空！");
		}
		if (!StringUtil.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)) {
			throw new DataBaseException("账号由小写英文字母开头数字或小写英文字母组成并且长度为6到16位！");
		}
		if (!StringUtil.isEmpty(account.getPassword())) {
			if (account.getPassword().length() < 6
					|| account.getPassword().length() > 12) {
				throw new DataBaseException("密码长度为6到16位！");
			}
		}
		if (StringUtil.isEmpty(account.getName())) {
			throw new DataBaseException("姓名不能为空！");
		}
		if (StringUtil.isEmpty(account.getPhone())) {
			throw new DataBaseException("手机号码不能为空！");
		}
		if (!StringUtil.regExp(account.getPhone(), StringUtil.MOBILE_REG)) {
			throw new DataBaseException("手机号码错误！");
		}
		if (StringUtil.isEmpty(account.getStatus().name())) {
			throw new DataBaseException("状态不能为空！");
		}

		Account user = UserUtil.getUser();
		Account prent = this.accountService.getAccount(account.getId());
		if (!account.getPhone().equals(prent.getPhone())) {
			Map<String, Object> map = SMSUtil.checkPhoneCode(
					account.getPhone(), account.getCode(), "1");
			Boolean flag = (Boolean) map.get("success");
			if (!flag) {
				throw new DataBaseException("验证码不正确！");
			}
		}
		prent.setUpdate_time(new Date());
		prent.setUpdate_user_id(user.getId());
		prent.setAccount(account.getAccount());
		prent.setName(account.getName());
		prent.setPhone(account.getPhone());
		prent.setStatus(account.getStatus());
		prent.setRole(account.getRole());
		prent.setCompanySection(account.getCompanySection());
		this.accountService.updateUser(prent);
		return jsonView(true, UPDATE_SUCCESS);
	}

	/**
	 * 功能描述： 删除员工 输入参数: @param id 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月3日下午9:02:21 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> del(String userIds) {
		String[] userArray = userIds.split(",");
		for (String string : userArray) {
			Account account = accountService.getAccount(string);
			if (account.getId().equals(UserUtil.getUser().getId())) {
				throw new BusinessException("不能删除自己账号。");
			}
			account.setStatus(Status.delete);
			this.accountService.updateAccount(account);
		}
		return jsonView(true, REMOVE_SUCCESS);
	}

	/**
	 * 功能描述： 帐号验证 输入参数: @param model 输入参数: @param account 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月3日下午2:29:50 修 改 人: 日 期: 返
	 * 回：boolean
	 */
	@RequestMapping(value = "/checkName", method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkName(Model model, String account, String old,
			 HttpServletRequest request) {
		if (StringUtil.isNotEmpty(old)) {
			if (account.equals(old)) {
				return true;
			}
		}
		// lix 2017-08-29 注释修改 判断用户账号是否重复
		// Account user = accountService.checkAccount(account);
		Account loginuserAccount = UserUtil.getUser();
		Account user = null;
		if (loginuserAccount.getCompany().getCompanyType().getId().equals("2")) {// 货主
			user = accountService.checkAccount(account + "_hz");
			
		}else  if(loginuserAccount.getCompany().getCompanyType().getId().equals("4")) { // 车队
			user = accountService.checkAccount(account + "_cd");
		}
		else {
			user = accountService.checkAccount(account);
		}
		return user == null ? true : false;
	}

	/**
	 * 功能描述： 手机号验证 输入参数: @param model 输入参数: @param phone 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月3日下午3:35:08 修 改 人: 日 期: 返
	 * 回：boolean
	 */
	@RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkPhone(Model model, String phone, String old,
			HttpServletRequest request) {
		/*
		 * aqw 2017/03/28注释 if(StringUtil.isNotEmpty(old)){
		 * if(phone.equals(old)){ return true; } } Account user =
		 * accountService.checkAccountByPhone(phone); return
		 * user==null?true:false;
		 */
		return true;
	}

	/**
	 * 功能描述： 联系电话验证 输入参数: @param model 输入参数: @param phone 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月3日下午3:35:08 修 改 人: 日 期: 返
	 * 回：boolean
	 */
	@RequestMapping(value = "/checkContactTel", method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkContactTel(Model model, String contactTel,
			String old, HttpServletRequest request) {
		Account user = accountService.checkAccountByPhone(contactTel);
		return user == null ? true : false;
	}

	/**
	 * 功能描述： 查询平台所有用户信息 输入参数: @param account 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年6月2日上午9:47:38 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getUserAllPage")
	@ResponseBody
	public Map<String, Object> getUserAllPage(Account account,
			HttpServletRequest request) {
		Map<String, Object> map = this.accountService.getUserAllPage(account,
				getStart(request), getLimit(request));
		return map;
	}

	/**
	 * 功能描述： 用户状态修改 输入参数: @param model 输入参数: @param request 输入参数: @param
	 * updateColume 输入参数: @return 输入参数: @throws Exception 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年6月2日下午2:04:10 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/updateAccountFiled", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> updateAccountFiled(Model model,
			HttpServletRequest request, UpdateColume updateColume)
			throws Exception {
		accountService.updateAccountFiled(updateColume);
		return jsonView(true, "信息修改成功！");
	}

	/**
	 * 功能描述： 用户密码重置 输入参数: @param model 输入参数: @param request 输入参数: @param id
	 * 输入参数: @return 输入参数: @throws Exception 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月2日下午5:06:14 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/updateResetPassWord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResetPassWord(Model model,
			HttpServletRequest request, String id) throws Exception {
		return accountService.updateResetPassWord(id);
	}

	/**
	 * 功能描述：商户重置员工密码 输入参数: @param model 输入参数: @param request 输入参数: @param id
	 * 输入参数: @return 输入参数: @throws Exception 异 常： 创 建 人: longqibo 日 期:
	 * 2016年7月15日下午12:34:15 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/resetPassWord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPassWord(Model model,
			HttpServletRequest request, String id) throws Exception {
		return accountService.updateResetPassWord(id);
	}

	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> sendCode(Model model, String phone,
			String codeType, HttpServletRequest request) {
		if (StringUtils.isEmpty(phone)) {
			return jsonView(false, "手机号不能为空");
		}
		String code = AppUtil.random(6).toString();
		codeType = StringUtils.isBlank(codeType) ? "1" : codeType;
		Map<String, Object> map = SMSUtil.sendPhoneCode(code, phone, codeType);
		Boolean flag = (Boolean) map.get("success");
		if (flag) {
			String msg = "";
			if ("editPhone".equals(codeType)) {
				msg = "您正在进行更换手机操作，当前手机验证码为:" + code + ",请不要把验证码泄露给其他人.";
			} else {
				msg = "您正在进行更换手机操作，您的新手机的验证码为:" + code + ",请不要把验证码泄露给其他人.";
			}

			sendMessageService
					.saveRecordAndSendMessage(phone, msg,
							"com.memory.platform.module.account.controller.RegisterController.sendCode");
		}
		return map;
	}

	/**
	 * 
	 * 功能描述： 发送验证码 输入参数: @param model 输入参数: @param phone 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: 武国庆 日 期: 2016年4月30日下午8:52:03 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "/addSendCode", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> addSendCode(Model model, String phone,
			String codeType, HttpServletRequest request) {
		if (StringUtils.isEmpty(phone)) {
			return jsonView(false, "手机号不能为空");
		}
		// aiqiwu 20170512注释,解除重复注册手机号
		/*
		 * Account user = accountService.checkAccountByPhone(phone); if (user !=
		 * null) { return jsonView(false, "手机号已被注册"); }
		 */

		String code = AppUtil.random(6).toString();
		codeType = StringUtils.isBlank(codeType) ? "1" : codeType;
		Map<String, Object> map = SMSUtil.sendPhoneCode(code, phone, codeType);
		Boolean flag = (Boolean) map.get("success");
		Account admin = UserUtil.getUser();
		if (flag) {
			String msg = "管理员:" + admin.getName() + "正在为你注册易林物流平台账号，请将该验证码"
					+ code + "告知管理员您的注册验证码";
			sendMessageService
					.saveRecordAndSendMessage(phone, msg,
							"com.memory.platform.module.account.controller.RegisterController.sendCode");
		}
		return map;
	}
}
