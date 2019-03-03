/*
 * 
 * 
 * 
 */
package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.FreemarkerUtils;
import com.memory.platform.module.system.service.ICompanyTypeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 菜单信息
 */
@Component("companyTypeDirective")
public class CompanyTypeDirective extends BaseDirective {


	/** 变量名称 */
	private static final String VARIABLE_NAME = "companyTypeViews";

	/** 参数名称 */
	private static final String PARAMS_NAME = "isreg";
	
	@Autowired
	private ICompanyTypeService companyTypeService;
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException { 
		String isreg = FreemarkerUtils.getParameter(PARAMS_NAME, String.class, params);
		List<CompanyType> list = null;
		if(StringUtils.isEmpty(isreg)){
			list =  companyTypeService.getCompanyTypeList();
		}else{
			list =  companyTypeService.getCompanyTypeIsRegisterList();
		}
		setLocalVariable(VARIABLE_NAME, list, env, body);
	}

}