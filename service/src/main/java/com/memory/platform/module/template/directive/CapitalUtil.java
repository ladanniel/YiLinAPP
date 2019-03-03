package com.memory.platform.module.template.directive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CapitalUtil {

	public static Map<String, Object> getMoneyReordMap(List<Map<String, Object>> list){
		Map<String, Object> moneyRecordMap = new HashMap<String,Object>();
		List<String> names = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for (Map<String, Object> map : list) {
			String money = Double.parseDouble(map.get("money").toString()) > 0 ? Double.parseDouble(map.get("money").toString()) + "" : (-Double.parseDouble(map.get("money").toString())) + "";
			switch (map.get("type").toString()) {
			case "0":
				names.add("'充值'");
				values.add("{value:"+money+",name:'充值'}");
				break;
			case "1":
				names.add("'提现'");
				values.add("{value:"+money+",name:'提现'}");
				break;
			case "2":
				names.add("'转账'");
				values.add("{value:"+money+",name:'转账'}");
				break;
			case "3":
				names.add("'手续费'");
				values.add("{value:"+money+",name:'手续费'}");
				break;
			case "4":
				names.add("'消费'");
				values.add("{value:"+money+",name:'消费'}");
				break;
			case "5":
				names.add("'冻结'");
				values.add("{value:"+money+",name:'冻结'}");
				break;
			case "6":
				names.add("'收款'");
				values.add("{value:"+money+",name:'收款'}");
				break;
			case "7":
				names.add("'其它'");
				values.add("{value:"+money+",name:'其它'}");
				break;
			case "8":
				names.add("'仲裁赔付'");
				values.add("{value:"+money+",name:'仲裁赔付'}");
				break;
			case "9":
				names.add("'解冻'");
				values.add("{value:"+money+",name:'解冻'}");
				break;
			case "10":
				names.add("'运输款'");
				values.add("{value:"+money+",name:'运输款'}");
				break;
			default:
				break;
			}
		}
		moneyRecordMap.put("names", names);
		moneyRecordMap.put("vals", values);
		return moneyRecordMap;
	}
}
