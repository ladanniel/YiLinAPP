package com.memory.platform.module.global.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.base.Area;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.IPersonnelAreaService;


@Controller
@RequestMapping("/global/data")
public class DataController extends BaseController {
	@Autowired
	private IPersonnelAreaService personnelAreaService;
	
	@Autowired
	private IAccountService accountService;
	
	@RequestMapping("/getUserStatusList")
	@ResponseBody
	public List<Map<String, Object>> getUserStatusList(String id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", "0");
		map.put("text", "正常");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("value", "1");
		map1.put("text", "停用");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("value", "2");
		map2.put("text", "注销");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map);
		list.add(map1);
		list.add(map2);
		
		return list;
	}
	
	@RequestMapping("/getUser")
	@ResponseBody
	public Account getUser(HttpServletRequest request) {
		 Account account = UserUtil.getUser(request);
		 account = accountService.getAccount(account.getId());
		 String WEB_PATH = AppUtil.getWebPath(request);// 项目路径 
		 account.setSearch(WEB_PATH);
		 return account;
	}
	
	@RequestMapping("/getAreaAll")
	@ResponseBody
	public Map<String, Object> getAreaAll() {
		List<Area> list = personnelAreaService.getAreaAll();
		 
		return jsonView(true, "获取数据成功！",list);
	}
	@RequestMapping("/getMenuTypeList")
	@ResponseBody 
	public List<Map<String, Object>> getMenuTypeList(String id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", "1");
		map.put("text", "模块");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("value", "2");
		map1.put("text", "菜单");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("value", "3");
		map2.put("text", "操作");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map);
		list.add(map1);
		list.add(map2);
		
		return list;
	}
	
	
	@RequestMapping("/getAreaList")
	@ResponseBody
	public List<Map<String, Object>>  getAreaList( HttpServletRequest request) {
		List<Map<String, Object>> list = personnelAreaService.getAreaList();
		List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			Map<String, Object> areaMap = new HashMap<String, Object>();
			areaMap.put("id", map.get("id").toString());
			areaMap.put("areaid", map.get("areaid").toString());
			if(map.get("parent_id") == null){
				areaMap.put("parent", "#");
			}else{
				areaMap.put("parent",map.get("parent_id"));
			}
			areaMap.put("text", map.get("areaname"));
			areaList.add(areaMap);
		}
		return areaList;
	}
	
	
}
