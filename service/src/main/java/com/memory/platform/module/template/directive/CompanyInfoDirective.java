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
import com.memory.platform.global.FreemarkerUtils;
import com.memory.platform.module.system.service.IAccountService;

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
@Component("companyInfoDirective")
public class CompanyInfoDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "userinfo";

	/** 参数名称 */
	private static final String PARAMS_NAME = "accontId";
	
	@Autowired
	private IAccountService accountService;
	
//	@Autowired
//	private IIdcardService idcardService;
//	
//	@Autowired
//	private IDrivingLicenseService drivingLicenseService;

	 
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String accontId = FreemarkerUtils.getParameter(PARAMS_NAME, String.class, params);
		Account newAccount = accountService.getAccount(accontId);
		Map<String,Object> map = new HashMap<String,Object>(); 
		
		Account comAccount = accountService.getAccount(newAccount.getCompany().getAccount_id());;
		
		map.put("account", newAccount);
		map.put("comAccount", comAccount);
		setLocalVariable(VARIABLE_NAME, map, env, body);
	}

}