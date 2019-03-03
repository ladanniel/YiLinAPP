/*
 * 
 * 
 * 
 */
package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.order.service.IRobOrderConfirmStatisticsService;
import com.memory.platform.module.order.vo.Item;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年8月6日 下午2:26:37 
* 修 改 人： 
* 日   期： 
* 描   述：首页订单统计
* 版 本 号：  V1.0
 */
@Component("robOrderConfirmDirective")
public class RobOrderConfirmDirective extends BaseDirective {
	@Autowired
	private IRobOrderConfirmStatisticsService robOrderConfirmStatisticsService;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		RobOrderConfirm robOrderConfirm = new RobOrderConfirm();;
		List<String> legendData = getRobOrderConfirmStatis();
		List<Item> seriesData = new ArrayList<>();
		
		Account account = UserUtil.getUser();
		if((account.getCompany().getCompanyType().getName().equals("货主"))&&account.getUserType().equals(UserType.company)){//货主管理员
			robOrderConfirm.setRobbedCompanyId(account.getCompany().getId());
		}else if((account.getCompany().getCompanyType().getName().equals("车队"))&&account.getUserType().equals(UserType.company)){//车队管理员
			robOrderConfirm.setCompanyName(account.getCompany().getName());
		}else if((account.getCompany().getCompanyType().getName().equals("货主"))&&account.getUserType().equals(UserType.user)){//货主员工
			robOrderConfirm.setRobbedAccountId(account.getId());
		}else if((account.getCompany().getCompanyType().getName().equals("车队"))&&account.getUserType().equals(UserType.user)){//车队员工
			robOrderConfirm.setTurckUserId(account.getId());
		}
		
		List<Map<String, Object>> list = robOrderConfirmStatisticsService.getRobOrderConfirmStatusCount(robOrderConfirm);
		for(Map<String, Object> map:list){
			for (String key : map.keySet()) {
				Item item = new Item();
				item.setValue(map.get(key));
				item.setName(key);
				seriesData.add(item);
		    }
		}
		
		
		
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		Date strDate = calendar.getTime();
		Date endDate = DateUtil.getbeforeDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Map dataMap = robOrderConfirmStatisticsService.getRobOrderConfirm(strDate, endDate, robOrderConfirm,sdf);
		
		List<Object[]> dataList = (List<Object[]>) dataMap.get("list");
		List<String> xAxis = (List<String>) dataMap.get("dateList");
		Gson gson = new Gson();
		Type type = new TypeToken<List<Item>>(){}.getType();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("legendData", gson.toJson(legendData));
		map.put("seriesData", gson.toJson(seriesData, type));
		
		map.put("xAxis", gson.toJson(xAxis));
		map.put("yseriesData", gson.toJson(dataList.get(0)));
		setLocalVariable("map", map, env, body);
	}

}