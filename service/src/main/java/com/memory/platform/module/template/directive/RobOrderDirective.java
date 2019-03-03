/*
 * 
 * 
 * 
 */
package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.member.Account;
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
* 描   述： 统一用户抢单总数、各状态的抢单数量
* 版 本 号：  V1.0
 */
@Component("robOrderDirective")
public class RobOrderDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "robOrderCount";

	/** 参数名称 */
	private static final String PARAMS_NAME = "accontId";
	
	@Autowired
	private IRobOrderRecordService robOrderRecordService;
	
	 
	
//	@Autowired
//	private IIdcardService idcardService;
//	
//	@Autowired
//	private IDrivingLicenseService drivingLicenseService;

	 
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		@SuppressWarnings("unchecked")
		Account account = UserUtil.getUser();
		List<Map<String, Object>> list = robOrderRecordService.getAccountRobOrderCount(account.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("robcount", list.get(0));
		setLocalVariable(VARIABLE_NAME, map, env, body);
	}

}