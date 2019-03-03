package com.memory.platform.module.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.module.account.service.IRegisterService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.my.dao.MyDao;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.impl.CompanySectionService;

@Controller
@RequestMapping("/app/user")
public class AppAccountController extends BaseController {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private AuthenticationManager authenticationManager; 
	@Autowired
	private CompanySectionService companySectionService;
	
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
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, String account, String pass) {
		 
	    Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> supresult = new HashMap<String, Object>();
		
		
		Account accountUser = this.accountService.accountLogin(account, pass);
		if (null == accountUser) {
			header.put("rc", "-1");
			header.put("rm", "您输入的用户名不存在！");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
		}
		if (!accountUser.getPassword().equals(AppUtil.md5(pass))) {
			header.put("rc", "-1");
			header.put("rm", "您输入的密码不正确！");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
			
		}
		if (accountUser.getStatus().equals(Account.Status.stop)) {
			
			header.put("rc", "-1");
			header.put("rm", "该用户已停用，无法登陆，请联系管理员！");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
			
		}
	
		
		if (accountUser.getCompany().getStatus().equals(Account.Status.stop)) {
			header.put("rc", "-1");
			header.put("rm", "该商户已注销，无法登陆，请联系管理员！");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
		}
		
		accountUser.setLogin_ip(AppUtil.getIp(request));//登录IP
		accountUser.setLogin_count(accountUser.getLogin_count()+1); //登录次数加1 
		accountUser.setLast_login_time(new Date());
		this.accountService.updateAccount(accountUser);
		
		
	
		header.put("rc", "0");
		header.put("rm", "success");
		
		result.put("response", response);
		body.put("account", accountUser);
		
		response.put("body", body);
		response.put("header", header);
		
		supresult.put("result", result);
		return supresult;
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
	    Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> supresult = new HashMap<String, Object>();
		
		
		if(StringUtils.isEmpty(account.getCompanyType())){
			header.put("rc", "-1");
			header.put("rm", "商户类型不能为空");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
			
		}
		if(StringUtils.isEmpty(account.getAccount())){
			header.put("rc", "-1");
			header.put("rm", "账号不能为空");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
			
		}
		if(StringUtils.isEmpty(account.getPhone())){
			header.put("rc", "-1");
			header.put("rm", "手机号不能为空");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
		}
		if(StringUtils.isEmpty(account.getPassword())){
			header.put("rc", "-1");
			header.put("rm", "密码不能为空");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
		}
		if(StringUtils.isEmpty(account.getCode())){
			
			header.put("rc", "-1");
			header.put("rm", "验证码不能为空");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
		}
		
		Account user = accountService.checkAccount(account.getAccount());
		if(user!=null){
			header.put("rc", "-1");
			header.put("rm", "用户名重复");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
		}
		Account user_1 = accountService.checkAccountByPhone(account.getPhone());
		if(user_1!=null){
			header.put("rc", "-1");
			header.put("rm", "手机号重复");
			
			response.put("body", body);
			response.put("header", header);
			
			result.put("response", response);
			supresult.put("result", result);
			
			return supresult;
		}
		
		
		
		this.registerService.saveCompanyReg(account);
		
	
		header.put("rc", "0");
		header.put("rm", "注册成功");
		
		result.put("response", response);
		
		response.put("body", body);
		response.put("header", header);
		supresult.put("result", result);
		return supresult;
	}
	
}
