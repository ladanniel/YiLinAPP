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
import com.memory.platform.global.FreemarkerUtils;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IRobOrderConfirmStatisticsService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年8月6日 下午2:26:37 
* 修 改 人： 
* 日   期： 
* 描   述： 统计平台的货物数量，在商户类型为：系统、管理的时候，查询所有的货物信息
*        不是系统、管理商户的类型的，只统计该商户的所有货物信息
* 版 本 号：  V1.0
 */
@Component("confirmStatisticsDirective")
public class ConfirmStatisticsDirectiveDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "confirmCount";
	
	@Autowired
	private IRobOrderConfirmStatisticsService robOrderConfirmStatisticsService;
	
	 
	

	 
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Account account = UserUtil.getUser();
		Map<String,Object> map = robOrderConfirmStatisticsService.getRobOrderConfirmCount(account);
		map.put(VARIABLE_NAME, map);
		setLocalVariable(VARIABLE_NAME, map, env, body);
	}

}