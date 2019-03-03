/*
 * 
 * 
 * 
 */
package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.CapitalStatus;
import com.memory.platform.entity.sys.Menu;
import com.memory.platform.entity.sys.Resource;
import com.memory.platform.global.FreemarkerUtils;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.IMenuService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 菜单信息
 */
@Component("menuInfoDirective")
public class MenuInfoDirective extends BaseDirective {


	/** 变量名称 */
	private static final String VARIABLE_NAME = "menuviews";

	/** 参数名称 */
	private static final String PARAMS_NAME = "pid";
	
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IAccountService accountService;
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String menuId = FreemarkerUtils.getParameter(PARAMS_NAME, String.class, params);
		Account account = accountService.getAccount(UserUtil.getUser().getId());
        List<Menu> list = menuService.getUserMenuList(account.getRole().getId(),menuId);
        if((!account.getCapitalStatus().equals(CapitalStatus.open) && menuId.equals("40289781527cb97c01527cbd0d980000")) || (!account.getCapitalStatus().equals(CapitalStatus.open) && menuId.equals("40288006556c811c01556c8f6c3c0000"))){
        	for (Menu menu : list) {
        		Resource resource =  menu.getResource();
        		resource.setUrl("/capital/account/view/opencapital.do");
        		menu.setResource(resource);
			}
		}
		setLocalVariable(VARIABLE_NAME, list, env, body);
	}

}