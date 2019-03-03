/*
 * 
 * 
 * 
 */
package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanySectionService;
import com.memory.platform.module.system.service.IRoleService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年4月29日 上午11:13:49 
* 修 改 人： 
* 日   期： 
* 描   述： 获取账户相关信息如角色  组织机构  认证。。。
* 版 本 号：  V1.0
 */
@Component("userInfoDirective")
public class UserInfoDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "userinfo";

	/** 参数名称 */
	private static final String PARAMS_NAME = "accontId";
	
	@Autowired
	private ICompanySectionService companySectionService;
	
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IAccountService accountService;
	
//	@Autowired
//	private IIdcardService idcardService;
//	
//	@Autowired
//	private IDrivingLicenseService drivingLicenseService;

	 
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Account account = UserUtil.getAccount();
		if(account == null) return;
		Account newAccount = accountService.getAccount(account.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		CompanySection companySection = companySectionService.getCompanySectionByCompanyId(account.getCompany().getId());
		map.put("companySection", companySection);
		Role role = roleService.getRole(account.getRole().getId());
		map.put("role", role);
		map.put("account", newAccount);
		setLocalVariable(VARIABLE_NAME, map, env, body);
	}

}