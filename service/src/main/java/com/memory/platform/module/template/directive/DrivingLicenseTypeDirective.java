 
package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.base.DrivingLicenseType;
import com.memory.platform.module.aut.service.IDrivingLicenseTypeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月9日 下午4:44:39 
* 修 改 人： 
* 日   期： 
* 描   述： 准驾车型自定义标签
* 版 本 号：  V1.0
 */
@Component("drivingLicenseTypeDirective")
public class DrivingLicenseTypeDirective extends BaseDirective {
	/** 变量名称 */
	private static final String VARIABLE_NAME = "drivingtypeviews";
	/** 参数名称 */
	@Autowired
	private IDrivingLicenseTypeService drivingLicenseTypeService;
	@SuppressWarnings({  "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<DrivingLicenseType> list =  drivingLicenseTypeService.getDrivingLicenseTypeList();
		setLocalVariable(VARIABLE_NAME, list, env, body);
	}

}