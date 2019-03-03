package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.Auth;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.aut.service.IAuthenticationService;
import com.memory.platform.module.aut.service.IBusinessLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseService;
import com.memory.platform.module.aut.service.IDrivingLicenseTypeService;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.system.service.ICompanySectionService;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ICompanyTypeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月10日 下午1:02:01 
* 修 改 人： 
* 日   期： 
* 描   述： 认证信息读取
* 版 本 号：  V1.0
 */
@Component("autInfoDirective")
public class AutInfoDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "autinfo";

	/** 参数名称 */
	private static final String PARAMS_NAME = "accontId";
	
	@Autowired
	private ICompanySectionService companySectionService;
	
	
    @Autowired
	private ICompanyService companyService;//商户类型业务接口
	@Autowired
	private ICompanyTypeService companyTypeService;//商户类型业务接口
	@Autowired
	IAuthenticationService authenticationService ;//认证信息保存接口
	@Autowired
	IIdcardService idcardService ;//身份证业务接口
	@Autowired
	IDrivingLicenseService drivingLicenseService ;//驾驶证业务接口
	@Autowired
	IDrivingLicenseTypeService drivingLicenseTypeService ;//准驾车型业务接口
	@Autowired
	IBusinessLicenseService businessLicenseService ;//营业执照业务接口

	 
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Account account = UserUtil.getUser();
		Map<String,Object> map = new HashMap<String,Object>();
		CompanySection companySection = companySectionService.getCompanySectionByCompanyId(account.getCompany().getId());
		map.put("companySection", companySection);
		CompanyType companyType = companyTypeService.getCompanyTypeById(account.getCompany().getCompanyType().getId());
		Company company = companyService.getCompanyById(account.getCompany().getId());
		if(Auth.notapply.equals(company.getAudit()) ){
			map.put("companyType",companyType); "idcard,div".indexOf("idcard");
			map.put("company",company);
		}else if(Auth.waitProcess.equals(company.getAudit())|| 
				Auth.supplement.equals(company.getAudit()) || 
				Auth.waitProcessSupplement.equals(company.getAudit()) || 
				Auth.auth.equals(company.getAudit())||
				Auth.notAuth.equals(company.getAudit())){
			map.put("companyType",companyType);
			map.put("company",company);
			if(!StringUtils.isEmpty(company.getIdcard_id())){
				map.put("idcard",idcardService.get(company.getIdcard_id()));
			}
			if(!StringUtils.isEmpty(company.getDriving_license_id())){
				DrivingLicense drivingLicense = drivingLicenseService.get(company.getDriving_license_id());
				drivingLicense.setDrivingLicenseType(drivingLicenseTypeService.get(drivingLicense.getDriving_license_type_id()));
				map.put("drivingLicense",drivingLicense);
			}
			if(!StringUtils.isEmpty(company.getBusiness_license_id())){
				map.put("businessLicense",businessLicenseService.get(company.getBusiness_license_id()));
			}
		} 
		setLocalVariable(VARIABLE_NAME, map, env, body);
	}

}