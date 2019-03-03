package com.memory.platform.module.account.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.SMSUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.account.service.IRegisterService;
import com.memory.platform.module.capital.service.IBankCardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyTypeService;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.security.annotation.LoginValidate;

/**
 * 
 * 创 建 人： 武国庆 日 期： 2016年4月30日 上午11:25:30 修 改 人： 日 期： 描 述： 商户注册 版 本 号： V1.0
 */
@Controller
@RequestMapping("/account/register")
@LoginValidate(false)
public class RegisterController extends BaseController {

	@Autowired
	private ICompanyTypeService companyTypeService;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private ISendMessageService sendMessageService;

	@Autowired
	private IBankCardService bankCardService;

	/**
	 * 
	 * 功能描述： 商户注册页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: 武国庆 日 期: 2016年4月30日上午11:38:55 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		// 商户类型
		List<CompanyType> list = companyTypeService
				.getCompanyTypeIsRegisterList();
		model.addAttribute("typeList", list);

		return "/account/register/index";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	protected String register(Model model, HttpServletRequest request) {
		// 商户类型
		List<CompanyType> list = companyTypeService
				.getCompanyTypeIsRegisterList();
		model.addAttribute("typeList", list);

		return "/account/register/register";
	}

	/**
	 * 
	 * 功能描述： 验证账户名是否重复 输入参数: @param model 输入参数: @param account 账户名 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: 武国庆 日 期: 2016年4月30日下午3:32:49 修 改 人: 日
	 * 期: 返 回：boolean
	 */
	@RequestMapping(value = "/checkName", method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkName(Model model, String account,String companyType,
			HttpServletRequest request) {
		//lix 2017-08-29 注释修改 判断用户账号是否重复
		//Account user = accountService.checkAccount(account);
		Account user =null;
		
//		if (companyType.equals("2")) { // 货主
//			user = accountService.checkAccount(account + "_hz");
//		} else if (companyType.equals("4")) { // 车队
//			user = accountService.checkAccount(account + "_cd");
//		}else {
//			user = accountService.checkAccount(account);
//		}
		user = accountService.checkAccount(account + "_cd");
		return user == null ? true : false;
	}

	/**
	 * 验证手机号是否重复 功能描述： 输入参数: @param model 输入参数: @param phone 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: 武国庆 日 期: 2016年4月30日下午3:43:56 修 改 人: 日
	 * 期: 返 回：boolean
	 */
	@RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkPhone(Model model, String phone,
			HttpServletRequest request) {
		// 艾其武 20170512注释，解除手机号不能重复
		/*
		 * Account user = accountService.checkAccountByPhone(phone); return
		 * user==null?true:false;
		 */
		return true;
	}

	/**
	 * 找回密码验证手机 功能描述： 输入参数: @param model 输入参数: @param phone 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: 武国庆 日 期: 2016年4月30日下午3:43:56 修 改 人: 日 期: 返
	 * 回：boolean
	 */
	@RequestMapping(value = "/restPhone", method = RequestMethod.POST)
	@ResponseBody
	protected boolean restPhone(Model model, String phone,
			HttpServletRequest request) {
		Account user = accountService.checkAccountByPhone(phone);
		return user == null ? false : true;
	}

	/**
	 * 
	 * 功能描述： 发送验证码 输入参数: @param model 输入参数: @param phone 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: 武国庆 日 期: 2016年4月30日下午8:52:03 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> sendCode(Model model, String phone,
			String codeType, HttpServletRequest request) {
		if (StringUtils.isEmpty(phone)) {
			return jsonView(false, "手机号不能为空");
		}
		// 艾其武 20170512注释，解除手机号不能重复
		/*
		 * Account user = accountService.checkAccountByPhone(phone); if(user !=
		 * null){ return jsonView(false, "手机号已被注册"); }
		 */

		String code = AppUtil.random(6).toString();
		codeType = StringUtils.isBlank(codeType) ? "1" : codeType;
		Map<String, Object> map = SMSUtil.sendPhoneCode(code, phone, codeType);
		Boolean flag = (Boolean) map.get("success");
		if (flag) {
			String msg = "您的注册验证码是:" + code + ",请不要把验证码泄露给其他人.";
			sendMessageService
					.saveRecordAndSendMessage(phone, msg,
							"com.memory.platform.module.account.controller.RegisterController.sendCode");
		}
		return map;
	}

	/**
	 * 
	 * 功能描述： 重置密码发送验证码 输入参数: @param model 输入参数: @param phone 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: 武国庆 日 期: 2016年4月30日下午8:52:03 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/resetpw/resetpwCode", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> resetpwCode(Model model, String phone,
			HttpServletRequest request) {

		if (phone.isEmpty()) {
			return jsonView(false, "手机号不能为空");
		}

		Account user = accountService.checkAccountByPhone(phone);
		if (user == null) {
			return jsonView(false, "该手机没在平台注册");
		}

		// 验证码存入缓存
		StringBuffer code = AppUtil.random(6);
		Map<String, Object> map = SMSUtil.sendPhoneCode(code.toString(), phone,
				"2");
		Boolean flag = (Boolean) map.get("success");
		if (flag) {
			String msg = "您的重置密码验证码是:" + code + ",请不要把验证码泄露给其他人.";
			sendMessageService
					.saveRecordAndSendMessage(phone, msg,
							"com.memory.platform.module.account.controller.RegisterController.sendCode");
		}
		return map;

	}

	/**
	 * 
	 * 功能描述： 验证验证码 输入参数: @param model 输入参数: @param phone 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: 武国庆 日 期: 2016年4月30日下午8:52:03 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "/resetCodeCheck", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> resetpw(Model model, String phone,
			String code, HttpServletRequest request, String iamgeCode) {
		HttpSession session = request.getSession();
		String codeSession = session.getAttribute("code").toString();
		if (!iamgeCode.equals(codeSession)) {
			return jsonView(false, "验证码不正确！");
		}

		Map<String, Object> map = SMSUtil.checkPhoneCode(phone, code, "2");
		Boolean flag = (Boolean) map.get("success");
		if (!flag) {
			return jsonView(false, "短信验证码不正确");
		}

		session.setAttribute("restPwd", phone);

		return jsonView(true, "成功");
	}

	/**
	 * 
	 * 功能描述： 修改密码页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: 武国庆 日 期: 2016年5月2日下午5:21:38 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/restPasword", method = RequestMethod.GET)
	protected String restPasword(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("restPwd");
		if (StringUtils.isBlank(phone)) {
			return "/account/register/resetpwdview";
		}

		return "/account/register/restPasword";
	}

	/**
	 * 
	 * 功能描述： 修改密码页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: 武国庆 日 期: 2016年5月2日下午5:21:38 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/restPas", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> restPas(Model model, String password,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("restPwd");
		// 重置密码手机号
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
			return jsonView(false, "/account/register/resetpwdview");
		}

		Account account = accountService.checkAccountByPhone(phone);
		if (account == null) {
			return jsonView(false, "/account/register/resetpwdview");
		}

		account.setPassword(AppUtil.md5(password));
		accountService.updateAccount(account);

		session.removeAttribute("restPwd");

		return jsonView(true, "密码修改成功，3秒后自动跳转登陆页面");
	}

	/**
	 * 
	 * 功能描述： 注册保存 输入参数: @param model 输入参数: @param request 输入参数: @param account
	 * 输入参数: @return 异 常： 创 建 人: 武国庆 日 期: 2016年5月2日下午5:19:45 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> add(Model model, HttpServletRequest request,
			Account account) {
		String aaString = account.getCompanyType();
		if (StringUtils.isEmpty(account.getCompanyType())) {
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
		if (StringUtils.isEmpty(account.getCode())) {
			return jsonView(false, "验证码不能为空");
		}

		if (!StringUtil.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)) {
			return jsonView(false, "账号由数字英文字母及下划线组成并且长度为6到16位！");
		}

		if (account.getPassword().length() < 6
				|| account.getPassword().length() > 16) {
			return jsonView(false, "密码长度为6到16位！");
		}
		// lix 2017-08-29 注释修改 判断用户账号是否重复
		// Account user = accountService.checkAccount(account.getAccount());

		Account user = null;
		if (account.getCompanyType().equals("2")) { // 货主
			user = accountService.checkAccount(account.getAccount() + "_hz");
		} else if (account.getCompanyType().equals("4")) { // 车队
			user = accountService.checkAccount(account.getAccount() + "_cd");
		}else {
			user = accountService.checkAccount(account.getAccount());
		}
		if (user != null) {
			return jsonView(false, "用户名重复");
		}

		// 艾其武 20170512注释，解除手机号不能重复
		/*
		 * Account user_1 =
		 * accountService.checkAccountByPhone(account.getPhone());
		 * if(user_1!=null){ return jsonView(false, "手机号重复"); }
		 */

		Map<String, Object> map = SMSUtil.checkPhoneCode(account.getPhone(),
				account.getCode(), "1");
		Boolean flag = (Boolean) map.get("success");
		if (!flag) {
			return jsonView(false, "验证码不正确");
		}
		/*
		 * //验证码判断 测试不用 CacheManager cacheManager =
		 * CacheManager.create("/config/ehcache.xml"); Cache cacheCode =
		 * cacheManager.getCache("phoneCode"); Element result =
		 * cacheCode.get(account.getPhone());
		 * if(result==null||!account.getCode()
		 * .equals(result.getObjectValue().toString())){ return jsonView(false,
		 * "验证码不正确"); }
		 */

		String pass = account.getPassword();

		this.registerService.saveCompanyReg(account);

		HttpSession session = request.getSession();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				account.getAccount(), pass);
		Authentication authentication = authenticationManager
				.authenticate(authRequest); // 调用loadUserByUsername
		SecurityContextHolder.getContext().setAuthentication(authentication);
		session.setAttribute("SPRING_SECURITY_CONTEXT",
				SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆

		account.setLogin_ip(AppUtil.getIp(request));// 登录IP
		account.setLogin_count(account.getLogin_count() + 1); // 登录次数加1
		account.setLast_login_time(new Date());
		this.accountService.updateAccount(account);
		request.getSession().setAttribute("USER", account);

		return jsonView(true, "注册成功");

	}

	/**
	 * 
	 * 功能描述： 注册成功页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: 武国庆 日 期: 2016年5月2日下午5:21:38 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	protected String success(Model model, HttpServletRequest request) {
		return "/account/register/success";
	}

	/**
	 * 
	 * 功能描述： 找回密码页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: 武国庆 日 期: 2016年5月2日下午5:21:38 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/resetpwdview", method = RequestMethod.GET)
	protected String resetpwd(Model model, HttpServletRequest request) {
		return "/account/register/resetpwdview";
	}

	/**
	 * 
	 * 功能描述： 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * 武国庆 日 期: 2016年5月15日上午10:28:04 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/readtemplate", method = RequestMethod.GET)
	protected String readtemplate(Model model, HttpServletRequest request) {
		return "/account/register/readtemplate";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	protected String test(Model model, HttpServletRequest request) {
		BankCard bankCard = new BankCard();
		bankCard.setBankCard("6212262402008845428");
		System.out.print(bankCard.getBankCard());
		bankCardService.saveBankCard(bankCard);

		return "";

	}
}
