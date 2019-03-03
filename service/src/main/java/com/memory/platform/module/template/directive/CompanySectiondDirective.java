package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.service.ICompanySectionService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("companySectiondDirective")
public class CompanySectiondDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "companySections";

	@Autowired
	private ICompanySectionService companySectionService;
	 
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException { 
		List<CompanySection> list = companySectionService.getListByCompany(UserUtil.getUser().getCompany().getId());
		for (CompanySection companySection : list) {
			String str = "－";
			if(companySection.getLeav() != 1){
				for (int i = 2; i < companySection.getLeav(); i++) {
					str += str;
				}
				companySection.setName(str + companySection.getName());
			}
		}
		setLocalVariable(VARIABLE_NAME, list, env, body);
	}

}
