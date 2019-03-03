package com.memory.platform.module.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.ApiStatusCode;
import com.memory.platform.global.SMSUtil;
import com.memory.platform.module.account.service.IRegisterService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.impl.CompanySectionService;
import com.wordnik.swagger.annotations.ApiOperation;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Controller
@RequestMapping("/app/user")
public class AppAccountController extends BaseController {
	@Autowired
	private IAccountService accountService;
	/*@Autowired
	private CompanySectionService companySectionService;*/
	
	@Autowired
	private IRegisterService registerService;
	
	
	

	
	/**
	 * 用户登陆
	 * 
	 * @param request
	 * @param account
	 * @param pass
	 * @return
	 */
	@RequestMapping(value = "login",method = RequestMethod.POST)
	@ApiOperation(value = "用户登录", httpMethod = "POST", response = Account.class, notes = "用户登录")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, String account, String pass) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		
		Account accountUser = this.accountService.accountLogin(account, pass);
		if (null == accountUser) {
			return jsonView(ApiStatusCode.LOGIN_CODE_10000,body);
		}
		
		if(!accountUser.getPassword().equals(AppUtil.md5(pass))){
			return jsonView(ApiStatusCode.LOGIN_CODE_10000,body);
		}
		
		if (accountUser.getStatus().equals(Account.Status.stop)) {
			return jsonView(ApiStatusCode.LOGIN_CODE_10001,body);
		}
		
		if (accountUser.getCompany().getStatus().equals(Account.Status.stop)) {
			return jsonView(ApiStatusCode.LOGIN_CODE_10002,body);
		}
		
		accountUser.setLogin_ip(AppUtil.getIp(request));//登录IP
		accountUser.setLogin_count(accountUser.getLogin_count()+1); //登录次数加1 
		accountUser.setLast_login_time(new Date());
		this.accountService.updateAccount(accountUser);
		this.request.getSession().setAttribute("token", accountUser.getToken());
		//request.setAttribute("token", accountUser.getToken());
		header.put("rc", "0");
		header.put("rm", "success");
		body.put("account", accountUser);
		return jsonView(header,body);
	}
	
	
	
	/**
	 * 用户登陆
	 * 
	 * @param request
	 * @param account
	 * @param pass
	 * @return
	 */
	@RequestMapping(value = "register",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(HttpServletRequest request,Account account) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		
		
		if(StringUtils.isEmpty(account.getCompanyType())){
			header.put("rc", "-1");
			header.put("rm", "商户类型不能为空");
			return jsonView(header,body);
			
		}
		if(StringUtils.isEmpty(account.getAccount())){
			header.put("rc", "-1");
			header.put("rm", "账号不能为空");
			return jsonView(header,body);
			
		}
		if(StringUtils.isEmpty(account.getPhone())){
			header.put("rc", "-1");
			header.put("rm", "手机号不能为空");
			return jsonView(header,body);
		}
		if(StringUtils.isEmpty(account.getPassword())){
			header.put("rc", "-1");
			header.put("rm", "密码不能为空");
			
			return jsonView(header,body);
			
		}
		if(StringUtils.isEmpty(account.getCode())){
			
			header.put("rc", "-1");
			header.put("rm", "验证码不能为空");
			
			return jsonView(header,body);
		}
		
		Account user = accountService.checkAccount(account.getAccount());
		if(user!=null){
			header.put("rc", "-1");
			header.put("rm", "用户名重复");
			return jsonView(header,body);
		}
		Account user_1 = accountService.checkAccountByPhone(account.getPhone());
		if(user_1!=null){
			header.put("rc", "-1");
			header.put("rm", "手机号重复");
			return jsonView(header,body);
		}
		
		
		
		this.registerService.saveCompanyReg(account);
		
	
		header.put("rc", "0");
		header.put("rm", "注册成功");
		return jsonView(header,body);
	}
	
	/**
	 * 
	* 功能描述： 获取个人信息 
	* 输入参数:  @param json
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月15日下午12:29:25
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "accountyDisplay",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accountinfo(String json,String phone,String password) {
//		Gson gson = new Gson();
//		Map<String, Object> map =  gson.fromJson(json,
//				new TypeToken<Map<String, Object>>() {
//				}.getType());
//		String phone = (String) map.get("phone");
//		String password = (String) map.get("password");
		
		if(phone == null || "".equals(phone) ||password == null || "".equals(password)) {
			return jsonView(false, "参数不正确",null);
		}
		Account account = accountService.checkAccountByPhone(phone);
		if(account == null) {
			return jsonView(false, "改手机没有注册用户",null);
		}
		if(!account.getPassword().equals(AppUtil.md5(password))) {
			return jsonView(false, "密码不正确",null);
		}
		return jsonView(true, "成功",new Object[]{account});
	}
	
	
	
	
	/**
	 * 
	* 功能描述： 验证手机号是否存在
	* 输入参数:  @param json
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月15日下午1:56:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "checkPhone",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPhone(String json,String phone) {
//		Gson gson = new Gson();
//		Map<String, Object> map =  gson.fromJson(json,
//				new TypeToken<Map<String, Object>>() {
//				}.getType());
//		String phone = (String) map.get("phone");
//		map.clear();
		if(phone == null || "".equals(phone) ) {
			return jsonView(false, "参数不正确",null);
		}
		Account user = accountService.checkAccountByPhone(phone);
		if(user == null) {
			return jsonView(true, "手机号不存在",null);
		}
		return jsonView(true, "手机号已存在",null);
	}
	
	/**
	 * 
	* 功能描述： 验证手机号是否存在
	* 输入参数:  @param json
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月15日下午1:56:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "checkCode",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkCode(String phone,String code,String codeType) {
		if(phone == null || "".equals(phone) ) {
			return jsonView(false, "参数不正确",null);
		}
		//验证验证码
		Map<String, Object> map = SMSUtil.checkPhoneCode(phone,code,codeType);
		if(!(Boolean)map.get(SUCCESS)){
			String msg = (String)map.get(MESSAGE);
			return jsonView(false,msg,null);
		}
		return jsonView(true,"验证成功！",null);
	}
	
	/**
	 * 
	* 功能描述：修改绑定手机号 
	* 输入参数:  @param json
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月15日下午1:58:42
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "changePhone",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editphone(String json,String phone,String accountID,String code,String codeType) {
//		Gson gson = new Gson();
//		Map<String, Object> map =  gson.fromJson(json,
//				new TypeToken<Map<String, Object>>() {
//				}.getType());
//		String phone = (String) map.get("phone");
//		String accountID = (String) map.get("accountID");
//		map.clear();
		if(phone == null || "".equals(phone) || accountID == null || "".equals(accountID) ) {
			return jsonView(false, "参数不正确",null);
		}
		//验证验证码
				Map<String, Object> map = SMSUtil.checkPhoneCode(phone,code,codeType);
				if(!(Boolean)map.get(SUCCESS)){
					String msg = (String)map.get(MESSAGE);
					return jsonView(false,msg,null);
				}
		return accountService.editPhone(accountID,phone);
	}
	
	
}
