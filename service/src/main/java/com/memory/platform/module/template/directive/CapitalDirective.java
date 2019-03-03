package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.capital.service.IMoneyRecordService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
* 创 建 人： longqibo
* 日    期： 2016年8月7日 下午3:07:32 
* 修 改 人： 
* 日   期： 
* 描   述： 资金指令模板
* 版 本 号：  V1.0
 */
@Component("capitalDirective")
public class CapitalDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "capitalAccountDirective";

	@Autowired
	private ICapitalAccountService capitalAccountService;
	@Autowired
	private IMoneyRecordService moneyRecordService;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Account account = UserUtil.getUser();
		CapitalAccount capitalAccount = capitalAccountService.getCapitalAccount(account.getId());
		List<Map<String, Object>> list = moneyRecordService.getMoneyRecordInfo(account.getId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date endTime = DateUtil.stringToDate(sdf.format(new Date())+"-01", null);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		calendar.add(Calendar.MONTH, -6);
		Date startTime = calendar.getTime();
		
		List<Map<String, Object>> moneyRecordDateList = moneyRecordService.getMoneyRecordInfo(account.getId(), sdf1.format(startTime), sdf1.format(endTime));
		
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> moneyRecordMap = CapitalUtil.getMoneyReordMap(list);
		Map<String, Object> moneyRecordTypes = this.getFormatMoneyRecord(moneyRecordDateList);
		
		map.put("status", account.getCapitalStatus());
		map.put("capitalAccount", capitalAccount);   //资金概况
		map.put("moneyRecord", moneyRecordMap);   //交易记录统计
		map.put("moneyRecordTypes", moneyRecordTypes);
		
		setLocalVariable(VARIABLE_NAME, map, env, body);
	}
	
	public Map<String, Object> getFormatMoneyRecord(List<Map<String, Object>> list){
		Map<String, Object> dataMap = new HashMap<String,Object>();
		List<String> dataType = new ArrayList<String>();
		int month = Integer.parseInt(DateUtil.getMonth());
		int year = Integer.parseInt(DateUtil.getYear());
		for (int i = 1; i < 7; i++) {
			if(month - i == 0){
				dataType.add("'" + (year - 1) + "-12'");
			}else if(month - i == -1){
				dataType.add("'" + (year - 1) + "-11'");
			}else if(month - i == -2){
				dataType.add("'" + (year - 1) + "-10'");
			}else if(month - i == -3){
				dataType.add("'" + (year - 1) + "-09'");
			}else if(month - i == -4){
				dataType.add("'" + (year - 1) + "-08'");
			}else if(month - i == -5){
				dataType.add("'" + (year - 1) + "-07'");
			}else{
				dataType.add("'" + year + "-0" + (month - i) + "'");
			}
		}
		Map<String, Object> dataType1 = new HashMap<String,Object>();
		Map<String, Object> dataType2 = new HashMap<String,Object>();
		Map<String, Object> dataType3 = new HashMap<String,Object>();
		Map<String, Object> dataType4 = new HashMap<String,Object>();
		Map<String, Object> dataType5 = new HashMap<String,Object>();
		Map<String, Object> dataType6 = new HashMap<String,Object>();
		for (Map<String, Object> map : list) {
			switch (map.get("type").toString()) {
			case "0":
				dataType1.put(map.get("formatDate").toString(), map.get("money").toString());
				break;
			case "1":
				dataType2.put(map.get("formatDate").toString(), map.get("money").toString());
				break;
			case "2":
				dataType3.put(map.get("formatDate").toString(), map.get("money").toString());
				break;
			case "6":
				dataType4.put(map.get("formatDate").toString(), map.get("money").toString());
				break;
			case "8":
				dataType6.put(map.get("formatDate").toString(), map.get("money").toString());
				break;
			case "10":
				dataType5.put(map.get("formatDate").toString(), map.get("money").toString());
				break;
			default:
				break;
			}
		}
		Collections.reverse(dataType);
		List<String> dataList1 = this.toList(dataType1,dataType);
		List<String> dataList2 = this.toList(dataType2,dataType);
		List<String> dataList3 = this.toList(dataType3,dataType);
		List<String> dataList4 = this.toList(dataType4,dataType);
		List<String> dataList5 = this.toList(dataType5,dataType);
		List<String> dataList6 = this.toList(dataType6,dataType);
		
		dataMap.put("dataType", dataType);
		dataMap.put("dataType1", dataList1);
		dataMap.put("dataType2", dataList2);
		dataMap.put("dataType3", dataList3);
		dataMap.put("dataType4", dataList4);
		dataMap.put("dataType5", dataList5);
		dataMap.put("dataType6", dataList6);
		return dataMap;
	}
	
	public List<String> toList(Map<String, Object> map,List<String> dataType){
		String[] array = {"0","0","0","0","0","0"};
		List<String> list = Arrays.asList(array);
		for (String key : map.keySet()) {
			int i = 0;
			for (String string : dataType) {
				if(("'"+key+"'").equals(string)){
					list.set(i, map.get(key).toString());
				}
				i ++;
			}
	    }
		return list;
	}
}
