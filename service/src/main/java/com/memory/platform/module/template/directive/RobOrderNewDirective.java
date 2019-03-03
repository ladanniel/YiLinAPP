/*
 * 
 * 
 * 
 */
package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.member.Account;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.order.service.IRobOrderRecordService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年8月6日 下午2:26:37 
* 修 改 人： 
* 日   期： 
* 描   述： 订单统计
*        
* 版 本 号：  V1.0
 */
@Component("robOrderNewDirective")
public class RobOrderNewDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "robOrderCount";

	
	@Autowired
	private IRobOrderRecordService robOrderRecordService;
	
	 
	
//	@Autowired
//	private IIdcardService idcardService;
//	
//	@Autowired
//	private IDrivingLicenseService drivingLicenseService;

	 
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Account account = UserUtil.getUser();
		String month = DateUtil.getMonth();
		Map<String,Object> map = robOrderRecordService.getRobOrderCount(account);
		map.put("month", month);
		map.put(VARIABLE_NAME, map);
		setLocalVariable(VARIABLE_NAME, map, env, body);
	}

}