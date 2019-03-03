/*
 * 
 * 
 * 
 */
package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.service.IRoleService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 角色所有信息
 */
@Component("roleInfoDirective")
public class RoleInfoDirective extends BaseDirective {
	/** 变量名称 */
	private static final String VARIABLE_NAME = "roleviews";
	/** 参数名称 */
	@Autowired
	private IRoleService roleService;
	@SuppressWarnings({  "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Account account = UserUtil.getUser();
		List<Role> list =  roleService.getList(account.getCompany().getCompanyType().getId());
		setLocalVariable(VARIABLE_NAME, list, env, body);
	}

}