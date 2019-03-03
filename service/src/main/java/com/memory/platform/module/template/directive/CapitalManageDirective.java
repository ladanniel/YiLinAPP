package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.member.Account;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.capital.service.IMoneyRecordService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
* 创 建 人： longqibo
* 日    期： 2016年8月20日 上午11:57:24 
* 修 改 人： 
* 日   期： 
* 描   述： 资金统计指令模板
* 版 本 号：  V1.0
 */
@Component("capitalManageDirective")
public class CapitalManageDirective extends BaseDirective {
	
	@Autowired
	private ICapitalAccountService capitalAccountService;
	@Autowired
	private IMoneyRecordService moneyRecordService;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Map<String, Object> map = new HashMap<String,Object>();
		Account account = UserUtil.getUser();
		Map<String, Object> capitalInfo = capitalAccountService.getSystemCapitalInfo();
		List<Map<String, Object>> list = moneyRecordService.getMoneyRecordInfo();
		Map<String, Object> moneyRecordMap = CapitalUtil.getMoneyReordMap(list);
		
		map.put("moneyRecord", moneyRecordMap);   //交易记录统计
		map.put("type", account.getCompany().getCompanyType().getName().equals("管理") || account.getCompany().getCompanyType().getName().equals("系统") ? "open" : "close");
		map.put("capitalInfo", capitalInfo);
		setLocalVariable("capitalManage", map, env, body);
	}

}
