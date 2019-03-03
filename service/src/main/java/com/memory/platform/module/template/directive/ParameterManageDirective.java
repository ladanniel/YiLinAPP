package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.module.system.service.IParameterManageService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("parameterManageDirective")
public class ParameterManageDirective extends BaseDirective {

	@Autowired
	private IParameterManageService parameterManageService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		ParameterManage parameterManage = parameterManageService.getTypeInfo(ParameterManage.ParaType.withdrawal);
		setLocalVariable("parameterManage", parameterManage, env, body);
	}

}
