package com.memory.platform.module.app.account.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.Null;
import org.python.antlr.PythonParser.return_stmt_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.visitor.functions.If;
import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.CapitalStatus;
import com.memory.platform.entity.member.Account.PhonePlatform;
import com.memory.platform.entity.member.Account.RoleType;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderConfirm.Status;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.Auth;
import com.memory.platform.global.SMSUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.global.SMSUtil.CachNameType;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.interceptor.LockInterceptor;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.module.account.service.IRegisterService;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanySectionService;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.IRoleService;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.truck.service.ITruckService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import net.sf.json.util.JSONUtils;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/app/account")
@Api(value = "/account", description = "账户相关操作", consumes = "application/json")
public class AccountController extends BaseController {

	@Autowired
	private IAccountService accountService;
	@Autowired
	private IRegisterService registerService;

	@Autowired
	private ISendMessageService sendMessageService;

	@Autowired
	private ICompanyService companyService;
	@Autowired
	IPushService pushService;

	@Autowired
	ITruckService truckService;
	@Autowired
	ICompanySectionService companySectionService;
	@Autowired
	IRoleService roleService;
	@Autowired
	IRobOrderConfirmService robOrderConfirmService;
	@Autowired
	IRobOrderRecordService robOrderRecordService;
	@Autowired
	ICapitalAccountService capitalAccountService;
	/**
	 * 
	 * 功能描述： 修改手机密码 输入参数: @param json 输入参数: @return 异 常： 创 建 人: yico-cj 日 期:
	 * 2016年6月15日下午12:48:45 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changePassword(String accountID,
			String password, String code, String codeType) {
		// Gson gson = new Gson();
		// Map<String, Object> map = gson.fromJson(json,
		// new TypeToken<Map<String, Object>>() {
		// }.getType());
		// String accountID = (String) map.get("accountID");
		// String password = (String) map.get("password");
		// map.clear();
		// map =
		if (accountID == null || "".equals(accountID) || password == null
				|| "".equals(password) || code == null || "".equals(code)
				|| codeType == null || "".equals(codeType)) {
			return jsonView(false, "参数不正确", null);
		}
		Account account = accountService.getAccount(accountID);
		if (account == null) {
			return jsonView(false, "用户ID不存在", null);
		}
		String phone = account.getPhone();
		// 验证验证码
		Map<String, Object> map = SMSUtil.checkPhoneCode(phone, code, codeType);
		if (!(Boolean) map.get(SUCCESS)) {
			String msg = (String) map.get(MESSAGE);
			return jsonView(false, msg, null);
		}
		return accountService.editPassword(accountID, password);
	}

	/**
	 * 用户登陆
	 * 
	 * @param request
	 * @param account
	 *            账号
	 * @param pass
	 *            密码
	 * @return
	 */

	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录", httpMethod = "POST", response = Account.class, notes = "login", position = 0)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> login(
			HttpServletRequest request,

			@ApiParam(required = true, name = "account", value = "账号名 或手机号") @RequestParam String account,
			@ApiParam(required = true, name = "pass", value = "用户密码") @RequestParam String pass,
			@ApiParam(required = false) Account.PhonePlatform platform,
			@ApiParam(required = false) String device_id,
			@ApiParam(required = false) String push_id) {

		if (StringUtils.isEmpty(account)) {
			return jsonView(false, "账号不能为空!", null);
		}
		if (StringUtils.isEmpty(pass)) {
			return jsonView(false, "密码不能为空!", null);
		}

		Account accountUser = this.accountService.accountLogin(account, pass);
		if (null == accountUser) {
			return jsonView(false, "您输入的用户名不存在!", null);
		}
		if (!accountUser.getPassword().equals(AppUtil.md5(pass))) {
			return jsonView(false, "您输入的密码不正确！", null);
		}
		if (accountUser.getStatus().equals(Account.Status.stop)) {
			return jsonView(false, "该用户已停用，无法登陆，请联系管理员！", null);
		}

		if (accountUser.getCompany().getStatus().equals(Account.Status.stop)) {
			return jsonView(false, "该商户已注销，无法登陆，请联系管理员！", null);
		}

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
		accountUser.setToken(AppUtil.getToken());
		accountUser.setPhone_platform(platform == null ? PhonePlatform.none
				: platform);
		String oldDeviceId = accountUser.getDevice_id();
		String oldPushId = accountUser.getPush_id();
		accountUser.setDevice_id(device_id);
		accountUser.setPush_id(push_id); 
		if (StringUtil.isNotEmpty(push_id)  && ( push_id.equals(oldPushId) ==false ) 
				) {
			String roleName = accountUser.getCompany().getCompanyType()
					.getName();
			try {
				pushService.updateAliasAndTag(accountUser.getId(), push_id,
						accountUser.getId(), roleName);
			} catch (APIConnectionException e) {

				e.printStackTrace();
			} catch (APIRequestException e) {

				e.printStackTrace();
			}
		}

		AppUtil.setLoginUser(accountUser);
		this.accountService.updateAccount(accountUser);
		List<Map<String, Object>> list = accountService
				.getRoleAppTooleList(accountUser.getRole().getId());
		accountUser.setPassword("");
		accountUser.setPaypassword("");
		accountUser.setAppList(list);
		return jsonView(true, "登录成功", accountUser, accountUser.getToken());
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

	/**
	 * 用户注册 功能描述： 输入参数: @param request 输入参数: @param account 输入参数: @return 异 常： 创
	 * 建 人: 武国庆 日 期: 2016年6月15日下午7:51:28 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> register(String codeType, Account account) {

		if (StringUtils.isEmpty(account.getCompanyType())) {
			return jsonView(false, "商户类型不能为空!", null);
		}

		if (StringUtils.isEmpty(account.getAccount())) {
			return jsonView(false, "账号不能为空!", null);
		}

		if (!StringUtil.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)) {
			return jsonView(false, "账号由数字英文字母及下划线组成并且长度为6到16位！");
		}

		if (StringUtils.isEmpty(account.getPhone())) {
			return jsonView(false, "手机号不能为空!", null);
		}

		if (!StringUtil.regExp(account.getPhone(), StringUtil.MOBILE_REG)) {
			return jsonView(false, "手机号码错误！");
		}

		if (StringUtils.isEmpty(account.getPassword())) {
			return jsonView(false, "密码不能为空!", null);
		}

		if (account.getPassword().length() < 6
				|| account.getPassword().length() > 16) {
			return jsonView(false, "密码长度为6到16位！");
		}

		if (!StringUtil.regExp(account.getPassword(), StringUtil.PASSWORD_REG)) {
			return jsonView(false, "密码为字母数字混合6-16位！");
		}

		if (StringUtils.isEmpty(account.getCode())) {
			return jsonView(false, "验证码不能为空!", null);
		}
		if (StringUtils.isEmpty(codeType)) {
			return jsonView(false, "验证码类型不能为空!", null);
		}

		Account user = accountService.checkAccount(account.getAccount());
		if (user != null) {
			return jsonView(false, "用户名重复!", null);
		}
		Account user_1 = accountService.checkAccountByPhone(account.getPhone());
		if (user_1 != null) {
			return jsonView(false, "手机号重复!", null);
		}
		// 验证验证码
		Map<String, Object> map = SMSUtil.checkPhoneCode(account.getPhone(),
				account.getCode(), codeType);
		if (!(Boolean) map.get(SUCCESS)) {
			String msg = (String) map.get(MESSAGE);
			return jsonView(false, msg, null);
		}

		this.registerService.saveCompanyReg(account);
		return jsonView(true, "注册成功", null);
	}

	/**
	 * 判断用户名是否重复 功能描述： 输入参数: @param phone 输入参数: @param codeType 输入参数: @return 异
	 * 常： 创 建 人: 武国庆 日 期: 2016年6月15日下午8:34:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "checkAccount", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> checkAccount(String account) {

		if (StringUtils.isEmpty(account)) {
			return jsonView(false, "用名不能为空!", null);
		}

		Account user = accountService.checkAccount(account);
		if (user != null) {
			return jsonView(false, "用户名重复!", null);
		}

		return jsonView(true, "用户名可用!", null);
	}

	/**
	 * 判断手机号是否重复 功能描述： 输入参数: @param phone 输入参数: @param codeType 输入参数: @return 异
	 * 常： 创 建 人: 武国庆 日 期: 2016年6月15日下午8:34:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "checkPhone", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> checkPhone(String phone) {

		if (StringUtils.isEmpty(phone)) {
			return jsonView(false, "手机号不能为空!", null);
		}
		
		Account user = accountService.checkAccountByPhone(phone);
		if (user != null) {
			return jsonView(false, "手机号已被注册!", null);
		}

		return jsonView(true, "手机号可用!", null);
	}

	/**
	 * 
	 * ----根据手机号返回账号
	 * 
	 * 找回密码验证手机号是否存在 功能描述： 输入参数: @param phone 输入参数: @param codeType 输入参数: @return
	 * 异 常： 创 建 人: 武国庆 日 期: 2016年6月15日下午8:34:13 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	//----修改 2018-07-17---增加返回值  账号
	//- ----根据手机号返回账号
	@RequestMapping(value = "findPascheckPhone", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> findPascheckPhone(String phone,String companyType) {

		if (StringUtils.isEmpty(phone)) {
			return jsonView(false, "用户名不能为空!", null);
		}else if (StringUtils.isEmpty(companyType)) {
			return jsonView(false, "公司类型不能为空!", null);
		}
		Account account=new Account();
		account.setCompanyType(companyType);
		account.setAccount(phone);
		
		if (account.getCompanyType().equals("2")) {
			account.setAccount(account.getAccount() + "_hz");
		} else if (account.getCompanyType().equals("4")) {
			account.setAccount(account.getAccount() + "_cd");
		}
		Account account_new = accountService.checkAccount(account.getAccount());//手机号phone即账号
		
		if (account_new != null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("accountId",  account_new.getId());
			map.put("account", account_new.getAccount());
			return jsonView(true, "手机号可用!", map);
		}

		return jsonView(false, "手机号没有在平台注册过!", null);
	}

	/**
	 * 发送验证码 功能描述： 输入参数: @param phone 输入参数: @param codeType 输入参数: @return 异 常： 创
	 * 建 人: 武国庆 日 期: 2016年6月15日下午8:34:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "sendPhoneCode", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> sendPhoneCode(String phone, String codeType) {

		if (StringUtils.isEmpty(phone)) {
			return jsonView(false, "手机号不能为空!", null);
		}
		if (StringUtils.isEmpty(codeType)) {
			return jsonView(false, "验证码类型不能为空!", null);
		}

		if ("1".equals(codeType)) {
			Account user_1 = accountService.checkAccountByPhone(phone);
			if (user_1 != null) {
				return jsonView(false, "手机号重复!", null);
			}
		}
		if ("2".equals(codeType)) {
			Account user_1 = accountService.checkAccountByPhone(phone);
			if (user_1 == null) {
				return jsonView(false, "手机号没有平台注册过!", null);
			}
		}

		String code = AppUtil.random(6).toString();
		Map<String, Object> map = SMSUtil.sendPhoneCode(phone, code, codeType);
		Boolean flag = (Boolean) map.get("success");

		if (flag) {// 1注册手机验证码 3是修改手机密码 4是更换手机号码 5确认收货验证
			String msg = "您的注册验证码是:" + code + ",请不要把验证码泄露给其他人.";

			if ("2".equals(codeType)) {
				msg = "您的修改支付密码验证码是:" + code + ",请不要把验证码泄露给其他人.";
			} else if ("3".equals(codeType)) {
				msg = "您的修改账号密码验证码是:" + code + ",请不要把验证码泄露给其他人.";
			} else if ("4".equals(codeType)) {
				msg = "您的更换手机号码验证码是:" + code + ",请不要把验证码泄露给其他人.";
			} else if ("5".equals(codeType)) {
				msg = "您的确认收货验证验证码是:" + code + ",请不要把验证码泄露给其他人.";
			}

			sendMessageService
					.saveRecordAndSendMessage(phone, msg,
							"com.memory.platform.module.account.controller.RegisterController.sendCode");
		}
		return map;
	}

	@RequestMapping(value = "getPhoneCodeTime", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getPhoneCodeTime(String phone,
			SMSUtil.CachNameType cachType, String key) {
		if (StringUtil.isEmpty(key) && StringUtils.isEmpty(phone)) {
			throw new BusinessException("手机号不能为空");
		}
		if (StringUtil.isEmpty(key)) {
			key = phone;

		}
		Map<String, Object> map = SMSUtil.canSendCodeNew(phone, cachType, key);
		return jsonView(true, "", map);
	}

	@RequestMapping(value = "getPhoneWithAccount", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getPhoneWithAccount(String account) {
		Account persistentAccount = accountService.getAccountByAccount(account);
		Map<String, Object> map = new HashMap<String, Object>();
		if (persistentAccount == null) {
			throw new BusinessException("账号不存在");
		}
		map.put("phone", persistentAccount.getPhone());
		return jsonView(true, "ok", map);

	}

	/**
	 * 发送验证码 功能描述： 输入参数: @param phone 输入参数: @param codeType 输入参数: @return 异 常： 创
	 * 建 人: 武国庆 日 期: 2016年6月15日下午8:34:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "sendPhoneCodeNew", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> sendPhoneCodeNew(String phone, String key,
			SMSUtil.CachNameType cachType) {

		if (StringUtil.isEmpty(key) && StringUtils.isEmpty(phone)) {
			return jsonView(false, "手机号不能为空!", null);
		}

		if (cachType == null) {
			return jsonView(false, "验证码类型不能为空!", null);
		}
		if (StringUtil.isEmpty(key)) {

			key = phone;
		}
		Map<String, Object> map = SMSUtil.canSendCodeNew(phone, cachType, key);
		Boolean flag = (Boolean) map.get("success");
		String errorMsg = "获取验证码成功";
		if (flag) {// 1注册手机验证码 3是修改手机密码 4是更换手机号码 5确认收货验证
			String code = AppUtil.random(6).toString();
			String msg = "";
			if (cachType == CachNameType.phoneRegister) {
				// Map<String, Object> accountMap =
				// accountService.checkAccountCountByPhone(phone);
				// int count =
				// Integer.parseInt(accountMap.get("count").toString());
				// if (count > 0) {
				// return jsonView(false, "手机号已经被注册!", null);
				// }
				msg = "您的注册验证码是:" + code + ",请不要把验证码泄露给其他人.";
			} else if (cachType == CachNameType.phoneUpdatePayPassword) {
				msg = "您的修改支付密码验证码是:" + code + ",请不要把验证码泄露给其他人.";
			} else if (cachType == CachNameType.phoneUpdatePassword) {
				msg = "您的修改账号密码验证码是:" + code + ",请不要把验证码泄露给其他人.";
			} else if (cachType == CachNameType.phoneUpdatePhoneNo) {
				msg = "您的更换手机号码验证码是:" + code + ",请不要把验证码泄露给其他人.";
			} else if (cachType == CachNameType.phoneFindPassword) {
				msg = "您的找回密码验证码是:" + code + ",请不要把验证码泄露给其他人.";
			} else if (cachType == CachNameType.receiptTimeOut) {
				Account account = (Account) UserUtil.getAccount();
				if (account == null) {

					throw new BusinessException("你的登录回话已经过期,情重新打登录");
				}
				if (!(account.getCompany().getCompanyType().getName()
						.equals("车队") || account.getCompany().getCompanyType()
						.getName().equals("个人司机"))) {
					return jsonView(false, "你不是司机无权访问该接口！", null);
				}
				String robOrderConfirmId = key;
				if (StringUtils.isBlank(robOrderConfirmId)) {
					return jsonView(false, "运单ID不能为空！", null);
				}

				RobOrderConfirm robOrderConfirm = robOrderConfirmService
						.getRobOrderConfirmById(robOrderConfirmId);
				if (robOrderConfirm == null) {
					return jsonView(false, "运单不存在！", null);
				}

				if (!account.getId().equals(robOrderConfirm.getTurckUserId())) {
					return jsonView(false, "你没有该运单！", null);
				}

				if (!robOrderConfirm.getStatus().equals(Status.transit)) {
					return jsonView(false, "运单状态不在运输中,不能发送收货确认!", null);
				}

				RobOrderRecord robOrderRecord = robOrderRecordService
						.getRecordById(robOrderConfirm.getRobOrderId());
				GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
				String consigneeMobile = goodsBasic.getConsigneeMobile();// 收货人联系电话
				phone = consigneeMobile;

				Truck turck = truckService.getTruckById(robOrderConfirm
						.getTurckId());
				msg = "您好，您的收货验证码是:" + code + ",请在确认卸货完成后将该验证码告知驾驶员。车牌号:"
						+ turck.getTrack_no() + " 驾驶员:" + account.getName()
						+ " 联系电话：" + account.getPhone();

			}
			sendMessageService
					.saveRecordAndSendMessage(phone, msg,
							"com.memory.platform.module.account.controller.RegisterController.sendCode");
			map = SMSUtil.saveSendCodeNew(phone, code, cachType, key);
		} else {
			Long surplusSecond = (Long) map.get(SMSUtil.surplusSecond);
			errorMsg = String.format("获取验证码失败，请%d秒后重试", surplusSecond);

		}

		return jsonView(flag, errorMsg, map);
	}

	/**
	 * ----失效----
	 * 
	 * 找回密码 功能描述： 输入参数: @param phone 输入参数: @param password 输入参数: @param code
	 * 输入参数: @param codeType 输入参数: @return 异 常： 创 建 人: 武国庆 日 期:
	 * 2016年6月15日下午9:23:14 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "findPassword", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> findPassword(String phone, String password,
			String code, String codeType) {

		if (StringUtils.isEmpty(phone)) {
			return jsonView(false, "手机号不能为空!", null);
		}
		if (StringUtils.isEmpty(password)) {
			return jsonView(false, "密码不能为空!", null);
		}
		if (StringUtils.isEmpty(code)) {
			return jsonView(false, "验证码不能为空!", null);
		}
		if (StringUtils.isEmpty(codeType)) {
			return jsonView(false, "验证码类型不能为空!", null);
		}

		// 验证验证码
		Map<String, Object> map = SMSUtil.checkPhoneCode(phone, code, codeType);
		if (!(Boolean) map.get(SUCCESS)) {
			String msg = (String) map.get(MESSAGE);
			return jsonView(false, msg, null);
		}

		Account account = accountService.checkAccountByPhone(phone);
		if (account == null) {
			return jsonView(false, "用户不存在!", null);
		}

		account.setPassword(AppUtil.md5(password));
		accountService.updateAccount(account);

		return jsonView(true, "密码修改成功!", null);
	}

	/*
	 * @ApiOperation(value = "测试", notes = "测试1", response = Account.class,
	 * position = 0)
	 * 
	 * @RequestMapping(value = "test",method = RequestMethod.GET) public void
	 * test(@ApiParam(required = true, name = "phone", value = "手机号码")
	 * 
	 * @RequestParam String phone, @ApiParam(required = true, name = "account",
	 * value = "账号") @RequestParam String account){ String str =
	 * "3mHsw2TwuTkNFINKzrlAxb4dL7ftBvKpL1Ndx4opKToKHPbL848mr993vVWcJlC7fF4OyB53Qf8F\r\nDpT38A2KGNrMmPs6AW2fzLBAfDwc2ueR3mWWa8i/FLHqE9lS5jBlVxT87I2aKgp2aF5Z7AzIAiOp\r\nd1qTX8sWf+YB43Kd1PM74L5VwhcS/SJtfg2fDEJtlRYtRVVUBEXS6RNmkjfj2NWi3AQl8WbV86MP\r\nzQZ0vUnmYJZRhncnLx8I9XYDuiRDdvnaymkOfJ64SxJz1wsVIbvKpVhJstQW0Ej7q3qgXlW+5r8E\r\n5ejg0aDz4jSOAQHPdLHbRnz3RmZsnJBY+NhGqD59orgJwj80kap6q5g53HEk5uo0XAryE/9K7Iv3\r\nGIyMPS6fZFDkoXclNFQcfhtSJzD1/6xqTFvl1TyzhYdC0XktHeMQcYMopY7mBL8n+KwkDqQf/FTY\r\n4FR3b6yOv9V00ZJvIDr8P5o8ulk8F+6watY17D0Vf/i/XPZY12eQCyPiQhsHPF9bTovJanyUKymE\r\nkC9vL3+T5gLHys6S9pEBwC2gC1ryxiGXSYdQC95x/yEPSxCeSXPj3DdpdaUtTIV8hGuacbIMbTAl\r\n23S5Vz+9E7ZLFGSClkrYYRCUY42ROsDT3mvzMzkx1wCpEbfHWzdnmJYZ09Q+AQsz2YRo1tN4DsGw\r\nUGrd4StZ/ueT4/2NYFn7Yst5RKQEsiJDMvEotK8sqxPt7d4fooI7xgCIAfUGVFPIOfBxq39eVJik\r\nR8YbUrW+KOruz7CNX3kWPrd4hyJeukCuQYMdcA/sIdO9sv2WS+RhJTaDII7BZ/Cs7DGFaR9xVoTf\r\n3h2MdWvRNEWi1cwYycRj1lGRKmTY1rfMq/5HhxQa0LI0NENEA5i98PJ5/LxcGkbt0QaVf6Drz0OE\r\nA8+lpsg7rAhT9+ZDkOu+9emFcSRpay+mJABxhaXd2pG52m+xItRf0Ty5g82XeUQHO1w/Hwq0RboM\r\nLGrZK4t8Oa0XFwsspbbpWj14/cpjvryx1w+tm++GMO8OQk8LJwdCDcpWGZBpTxeByi51s7lMlbua\r\n3IBYV4HMRXWgpiBitLIkhh0CvbVWupUHHI5XoRhm8I20n2DftkXh5fKBfXlXzuLnDpF4nobRelJJ\r\nTrdWHWJVva1gfFOpiNdklmQ/odmYzVSqG5mWtFwZxdyR5FgKmt5RyISuhBcFYzRp6DhSQtUhHqDv\r\naH6MO9PhPqdC6M/oWgqJGYaog1YPGbHkvvbQjwutaQOG7d6kYPTkXmW0WCgDK4wjb+Olg6LuFlct\r\nff03l4a5ecoqhFHDrRcOTEXv7N8y1+ofM9UOkGZed+Vm6IEPAftt8USrkdmEGcjo9RG+wCWaL89x\r\nSVKVRTP2Lra9JbZ31mQC8GiyMDPJXl6cVQmb89Cd"
	 * ; try { //System.out.println(ThreeDESUtil.des3AppDecodeCBC(str)); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * }
	 */

	/**
	 * 验证账号和验证码是否正确
	 */
	@RequestMapping(value = "checkSmsCode", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> checkSmsCode(String account, String smsCode,
			SMSUtil.CachNameType cachType) {
		if (StringUtils.isEmpty(account)) {
			return jsonView(false, "手机号不能为空!", null);
		}

		if (!StringUtil.regExp(account, StringUtil.MOBILE_REG)) {
			return jsonView(false, "手机号码错误！");
		}
		if (StringUtils.isEmpty(smsCode)) {
			return jsonView(false, "验证码不能为空!", null);
		}
		if (cachType == null) {
			return jsonView(false, "验证码类型不能为空!", null);
		}
		// Account user = accountService.checkAccount(account);
		// if (user != null) {
		// return jsonView(false, "用户名重复!", null);
		// }
		// Account user_1 = accountService.checkAccountByPhone(account);
		// if (user_1 != null) {
		// return jsonView(false, "手机号重复!", null);
		// }
		// 验证验证码
		String code = SMSUtil.getSendCode(account, cachType);
		if (StringUtils.isEmpty(code))
			return jsonView(false, "验证码过期,请重新获取", null);
		if (!code.equals(smsCode)) {
			return jsonView(false, "验证码不对,请重新核对输入", null);
		}
		return jsonView(true, "验证成功", null);
	}

	/**
	 * 验证注册账号是否重复 和验证码
	 */
	@RequestMapping(value = "validateRegisterAccount", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> validateRegisterAccount(Account account) {
		if (StringUtil.isEmpty(account.getCompanyType())) {
			throw new BusinessException("商户类型不能为空");
		}
		if (canRegisterCompanyType.indexOf(account.getCompanyType()) == -1) {
			throw new BusinessException("错误的商户类型");
		}
		if (StringUtil.isEmpty(account.getAccount())) { // 手机注册
			account.setAccount(account.getPhone());
		}
		String oldAccount = account.getAccount();
		if (account.getCompanyType().equals("2")) {
			account.setAccount(account.getAccount() + "_hz");
		} else if (account.getCompanyType().equals("4")) {
			account.setAccount(account.getAccount() + "_cd");
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		Account account2 = accountService.checkAccount(account.getAccount());

		if (account2 != null) {
			throw new BusinessException("账号:" + oldAccount + "已存在!");
		}
		if (StringUtil.isNotEmpty(account.getCode())) { // 需要验证验证码
			String phone = StringUtil.isNotEmpty(account.getPhone()) ? account
					.getPhone() : oldAccount;

			Map<String, Object> codeMap = SMSUtil.getSendCodeMap(phone,
					CachNameType.phoneRegister, phone);
			String code = codeMap == null ? null : codeMap.get("code")
					.toString();
			if (StringUtil.isEmpty(code)) {

				throw new BusinessException("验证码已经过期或不存在请重新输入！");
			}
			if (code.equals(account.getCode()) == false) {
				Integer errCount = codeMap.get("errCount") == null ? new Integer(
						0) : (Integer) codeMap.get("errCount");
				errCount++;
				if (errCount == 4) {
					SMSUtil.deleteSendCode(phone, CachNameType.phoneRegister);
					throw new BusinessException("1", "验证码错误次数过多请重新发送验证码！");
				}
				codeMap.put("errCount", errCount);
				SMSUtil.saveSendCodeMap(codeMap, phone,
						CachNameType.phoneRegister);
				throw new BusinessException("验证码错误！");
			}

		}
		return jsonView(true, "", ret);
	}

	// 只能注册商户和车队
	List<String> canRegisterCompanyType = new ArrayList<String>(
			Arrays.asList(new String[] { "2", "4" }));

	/**
	 * 用户注册
	 */
	@RequestMapping(value = "appRegister", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> appRegister(SMSUtil.CachNameType cachType,
			Account account) {
		cachType = CachNameType.phoneRegister;
		if (StringUtils.isEmpty(account.getCompanyType())) {
			return jsonView(false, "商户类型不能为空!", null);
		}

		if (canRegisterCompanyType.indexOf(account.getCompanyType()) == -1) {
			throw new BusinessException("不能注册其他商户");
		}

		if (StringUtils.isEmpty(account.getAccount())) {
			return jsonView(false, "账号不能为空!", null);
		}

		if (account.getAccount() != null
				&& StringUtils.isEmpty(account.getAccount())) {
			if (!StringUtil
					.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)) {
				return jsonView(false, "账号由数字英文字母及下划线组成并且长度为6到16位！");
			}
		}
		if (account.getAccount() == null) {
			account.setAccount(account.getPhone());
		}
		if (StringUtils.isEmpty(account.getPhone())) {
			return jsonView(false, "手机号不能为空!", null);
		}

		if (!StringUtil.regExp(account.getPhone(), StringUtil.MOBILE_REG)) {
			return jsonView(false, "手机号码错误！");
		}

		if (StringUtils.isEmpty(account.getPassword())) {
			return jsonView(false, "密码不能为空!", null);
		}

		if (account.getPassword().length() < 6
				|| account.getPassword().length() > 16) {
			return jsonView(false, "密码长度为6到16位！");
		}

		if (!StringUtil.regExp(account.getPassword(), StringUtil.PASSWORD_REG)) {
			return jsonView(false, "密码为字母数字混合6-16位！");
		}

		if (StringUtils.isEmpty(account.getCode())) {
			return jsonView(false, "验证码不能为空!", null);
		}
		if (cachType == null) {
			return jsonView(false, "验证码类型不能为空!", null);
		}
		// 验证账号是否存在
		// if (account.getAccount() != null &&
		// !StringUtils.isEmpty(account.getAccount())) {
		// Account user = accountService.checkAccount(account.getAccount());
		// if (user != null) {
		// return jsonView(false, "用户名重复!", null);
		// }
		// }
		// 验证手机号是否存在
		// Account user_1 =
		// accountService.checkAccountByPhone(account.getPhone());
		// if (user_1 != null) {
		// return jsonView(false, "手机号重复!", null);
		// }
		String code = SMSUtil.getSendCode(account.getPhone(), cachType);
		if (StringUtils.isEmpty(code))
			return jsonView(false, "验证码过期,请重新获取", null);
		if (!code.equals(account.getCode())) {
			return jsonView(false, "验证码不对,请重新核对输入", null);
		}
		SMSUtil.deleteSendCode(account.getPhone(), cachType);
		this.registerService.saveCompanyReg(account);
		return jsonView(true, "注册成功", null);
	}

	/**
	 * 获取员工列表 aiqiwu 2017-08-31
	 */
	@RequestMapping("/getEmployeePage")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getEmployeePage(HttpServletRequest request) {
		Account account = UserUtil.getAccount();
		// 获取当前用户所在组织机构下的所有机构
		List<Map<String, Object>> companySectionMap = companySectionService
				.getCompanySectionByCompanyIdForMap(account.getCompany()
						.getId());
		// 根据组织机构获取该机构下所有用户
		for (Map<String, Object> smp : companySectionMap) {
			String sectionID = (String) smp.get("id");
			List<Map<String, Object>> accountMap = accountService
					.getEmployeeByCompanyIdAndSectionIdForMap(account
							.getCompany().getId(), sectionID);
			smp.put("truckAccounts", accountMap);

		}
		return jsonView(true, "获取数据成功", companySectionMap);
	}

	/**
	 * 获取组织机构列表 aiqiwu 2017-08-31
	 */
	@RequestMapping("/getRolePage")
	@ResponseBody
	public Map<String, Object> getRolePage(HttpServletRequest request) {
		List<Role> list = roleService.getList(UserUtil.getAccount()
				.getCompany().getCompanyType().getId());
		return jsonView(true, "获取角色列表成功", list);
	}

	/**
	 * 添加员工 aiqiwu 2017-08-31
	 */
	@RequestMapping(value = "registerEmployee", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> registerEmployee(Account account) {
		if (StringUtil.isEmpty(account.getAccount())) {
			throw new DataBaseException("账号不能为空！");
		}
		if (!StringUtil.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)) {
			throw new DataBaseException("账号由小写英文字母开头数字或小写英文字母组成并且长度为6到16位！");
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
		if (account.getStatus() == null) {
			account.setStatus(Account.Status.start);

		}
		if (StringUtil.isEmpty(account.getStatus().name())) {
			throw new DataBaseException("状态不能为空！");
		}

		/*
		 * aqw 2017/03/28注释 if(null !=
		 * accountService.checkAccountByPhone(account.getPhone())){ throw new
		 * DataBaseException("手机号码已存在！"); }
		 */

		// 获取缓存验证码
		String code = SMSUtil.getSendCode(account.getPhone(),
				SMSUtil.CachNameType.phoneRegister);
		if (StringUtils.isEmpty(code))
			return jsonView(false, "验证码过期,请重新获取", null);
		if (!code.equals(account.getCode())) {
			return jsonView(false, "验证码不对,请重新核对输入", null);
		}
		String password = AppUtil.random(8).toString();
		account.setPassword(password);
		Account user = UserUtil.getAccount();
		account.setCompany(user.getCompany());
		account.setAdd_user_id(user.getId());
		if (account.getCompanySection() == null) {

			account.setCompanySection(user.getCompanySection());

		}
		String oldAccount = account.getAccount();
		if (user.getRole_type() == RoleType.consignor) {
			account.setRole_type(RoleType.consignor);
			Role role = new Role();
			role.setId("402880ec563b9b1f01563ba1b56e0000");
			account.setRole(role);
			if (account.getAccount() == null
					|| StringUtils.isEmpty(account.getAccount()))
				account.setAccount(account.getPhone() + "_hz");
			else
				account.setAccount(account.getAccount() + "_hz");
		} else {
			Role role = new Role();
			role.setId("402880065596ea89015596fddf090003");
			account.setRole(role);
			account.setRole_type(RoleType.truck);
			if (account.getAccount() == null
					|| StringUtils.isEmpty(account.getAccount()))
				account.setAccount(account.getPhone() + "_cd");
			else
				account.setAccount(account.getAccount() + "_cd");
		}
		if (null != accountService.checkAccount(account.getAccount())) {
			throw new DataBaseException("用户名存在！");
		}
		this.accountService.saveUser(account);
		String inPoint = "com.memory.platform.module.system.service.impl.AccountService.saveUser";
		String sendMsg = "尊敬的用户您好，您的管理员" + user.getName()
				+ "已为您成功注册小镖人平台账号。您的账号为：" + oldAccount + "，密码为：" + password
				+ "，请及时登录APP修改您的密码。如忘记密码可请管理员重置或登录平台使用密码找回功能找回您的密码。";
		Map<String, Object> map_v = sendMessageService
				.saveRecordAndSendMessage(account.getPhone(), sendMsg, inPoint);
		if (!Boolean.valueOf(map_v.get("success").toString())) {
			throw new BusinessException("短信提示异常，请联系管理员,添加员工失败。");
		}
		// 删除缓存验证码
		SMSUtil.deleteSendCode(account.getPhone(),
				SMSUtil.CachNameType.phoneRegister);
		return jsonView(true, "注册成功");
	}

	/**
	 * aiqiwu 2017-09-05 修改支付密码
	 */
	@RequestMapping("editPayPassword")
	@ResponseBody
	public Map<String, Object> editPayPassword(Account account) {
		if (account.getPaypassword() == null
				|| StringUtils.isEmpty(account.getPaypassword())) {
			return jsonView(false, "密码不能为空!", null);
		}
		if (StringUtils.isEmpty(account.getCode())) {
			return jsonView(false, "验证码不能为空!", null);
		}
		
		Account tempAccount = StringUtil.isNotEmpty(account.getId()) ? accountService
				.getAccount(account.getId()) : accountService
				.getAccountByAccount(account.getAccount());
		if (tempAccount == null) {
			return jsonView(false, "用户不存在!", null);
		}
		Map<String, Object> codeMap = SMSUtil.getSendCodeMap(
				tempAccount.getPhone(), CachNameType.phoneUpdatePayPassword,
				tempAccount.getPhone());
		String code = codeMap == null ? null : codeMap.get(SMSUtil.code)
				.toString();
		if (StringUtils.isEmpty(code))
			return jsonView(false, "验证码过期,请重新获取", null);
		if (!code.equals(account.getCode())) {
			Integer errCount = (Integer) codeMap.get(SMSUtil.errCount);
			errCount = errCount == null ? 0 : errCount;
			if (errCount > 4) {
				SMSUtil.deleteSendCode(tempAccount.getPhone(),
						SMSUtil.CachNameType.phoneUpdatePayPassword);
				throw new BusinessException("验证码错误已超过最大次数，请重新获取");
			} else {

				errCount++;
				codeMap.put(SMSUtil.errCount, errCount);
				SMSUtil.saveSendCodeMap(codeMap, tempAccount.getPhone(),
						SMSUtil.CachNameType.phoneUpdatePayPassword);
			}
			return jsonView(false, "验证码不对,请重新核对输入", null);
		}
		//by lil  增加添加支付账号 
		tempAccount.setPaypassword(AppUtil.md5(account.getPaypassword()));
	   if( tempAccount.getCapitalStatus()== CapitalStatus.notenable && tempAccount.getIsAdmin()) { //未开通交易账户 并且是管理的
		   tempAccount.setCapitalStatus(CapitalStatus.open);
		   capitalAccountService.savePayPassword(tempAccount);
		//request.getSession().setAttribute("USER", account);
	   }
		
		accountService.updateAccount(tempAccount);
		AppUtil.setLoginUser(tempAccount);
		// 删除缓存验证码
		SMSUtil.deleteSendCode(tempAccount.getPhone(),
				SMSUtil.CachNameType.phoneUpdatePayPassword);
		return jsonView(true, "成功修改支付密码");
	}

	/**
	 * aiqiwu 2017-09-07 修改登录密码
	 */
	@RequestMapping("editLoginPassword")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> editLoginPassword(Account account) {
	 
		if (account.getPassword() == null
				|| StringUtils.isEmpty(account.getPassword())) {
			return jsonView(false, "密码不能为空!", null);
		}
		if (StringUtils.isEmpty(account.getCode())) {
			return jsonView(false, "验证码不能为空!", null);
		}
		Account tempAccount = StringUtil.isNotEmpty(account.getId()) ? accountService
				.getAccount(account.getId()) : accountService
				.getAccountByAccount(account.getAccount());
		if (tempAccount == null) {
			return jsonView(false, "用户不存在!", null);
		}
		Map<String, Object> codeMap = SMSUtil.getSendCodeMap(
				tempAccount.getPhone(), CachNameType.phoneUpdatePassword,
				tempAccount.getPhone());
		String code = codeMap == null ? null : codeMap.get(SMSUtil.code)
				.toString();
		if (StringUtils.isEmpty(code))
			return jsonView(false, "验证码过期,请重新获取", null);
		if (!code.equals(account.getCode())) {
			Integer errCount = (Integer) codeMap.get(SMSUtil.errCount);
			errCount = errCount == null ? 0 : errCount;
			if (errCount > 4) {
				SMSUtil.deleteSendCode(tempAccount.getPhone(),
						SMSUtil.CachNameType.phoneUpdatePassword);
				throw new BusinessException("验证码错误已超过最大次数，请重新获取");
			} else {

				errCount++;
				codeMap.put(SMSUtil.errCount, errCount);
				SMSUtil.saveSendCodeMap(codeMap, tempAccount.getPhone(),
						SMSUtil.CachNameType.phoneUpdatePassword);
			}
			return jsonView(false, "验证码不对,请重新核对输入", null);
		}
		tempAccount.setPassword(AppUtil.md5(account.getPassword()));
		accountService.updateAccount(tempAccount);
		AppUtil.setLoginUser(tempAccount);
		// 删除缓存验证码
		SMSUtil.deleteSendCode(tempAccount.getPhone(),
				SMSUtil.CachNameType.phoneUpdatePassword);
		return jsonView(true, "成功登陆密码");
	}
	@RequestMapping("userProtocal")
	@UnInterceptor
	public String userProtoca(String platform ,String type,Model model) {
		model.addAttribute("platform", platform);
		model.addAttribute("type", type);
		return "/system/user/userprotocal";
	}
	
	public static void main(String[] args){
		org.python.util.PythonInterpreter interpreter = new org.python.util.PythonInterpreter();
		interpreter.execfile("E:/code/CrawlerWeb/test.py");
		org.python.core.PyFunction pyFunction = interpreter.get("sayHello", org.python.core.PyFunction.class);
		
		org.python.core.PyObject pyObject = pyFunction.__call__();
		System.out.println(pyObject);
	}
}
