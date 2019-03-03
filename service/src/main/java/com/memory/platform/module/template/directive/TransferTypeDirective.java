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

import com.memory.platform.entity.capital.TransferType;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.service.ITransferTypeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 菜单信息
 */
@Component("transferTypeDirective")
public class TransferTypeDirective extends BaseDirective {


	/** 变量名称 */
	private static final String VARIABLE_NAME = "transferTypeViews";

	
	@Autowired
	private ITransferTypeService transferTypeService;
	 
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException { 
		String companyTypeId = UserUtil.getUser().getCompany().getCompanyType().getId();
		List<TransferType> list =  transferTypeService.getAll(companyTypeId);
		setLocalVariable(VARIABLE_NAME, list, env, body);
	}

}