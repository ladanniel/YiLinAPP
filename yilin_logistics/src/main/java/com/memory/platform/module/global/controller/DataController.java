package com.memory.platform.module.global.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.service.IGoodsTypeService;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.IPersonnelAreaService;
import com.memory.platform.module.truck.service.ITruckService;


@Controller
@RequestMapping("/global/data")
public class DataController {
	@Autowired
	private IPersonnelAreaService personnelAreaService;
	
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IGoodsTypeService goodsTypeService;
	@Autowired
	ITruckService truckService;
	
	@RequestMapping("/getUser")
	@ResponseBody
	public Account getUser(HttpServletRequest request) {
		 Account account = UserUtil.getUser(request);
		 Account  account1 = accountService.getAccount(account.getId());
		 String WEB_PATH = AppUtil.getWebPath(request);// 项目路径 
		 account1.setSearch(WEB_PATH);
		 return account1;
	}
	
	@RequestMapping("/getCompantId")
	public String getUserId(Model model,HttpServletRequest request,String id) {
		 Company company = companyService.getCompanyById(id);
		 model.addAttribute("company", company);
		 Account user = accountService.getAccount(company.getAccount_id());
		 model.addAttribute("user", user);
		 if(!StringUtils.isEmpty(company.getAdd_user_id())){
			 Account createUser = accountService.getAccount(company.getAdd_user_id());
			 model.addAttribute("createUser", createUser);
		 }
		 if(!StringUtils.isEmpty(company.getUpdate_user_id())){
			 Account updateUser = accountService.getAccount(company.getUpdate_user_id());
			 model.addAttribute("updateUser", updateUser);
		 }
		 if(!StringUtils.isEmpty(company.getAut_user_id())){
			 Account autUser = accountService.getAccount(company.getAut_user_id());
			 model.addAttribute("autUser", autUser);
		 }
		 return "global/companyAccountView";
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
	
	@RequestMapping("/getCompanyStatusList")
	@ResponseBody
	public List<Map<String, Object>> getCompanyStatusList(HttpServletRequest request) {
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("value", "open");
		map1.put("text", "启用");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", "colse");
		map.put("text", "关闭");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map1);
		list.add(map);
		return list;
	}
	
	@RequestMapping("/getUserStatusList")
	@ResponseBody
	public List<Map<String, Object>> getUserStatusList(HttpServletRequest request) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("value", "start");
		map1.put("text", "启用");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", "stop");
		map.put("text", "停用");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("value", "cancel");
		map2.put("text", "注销");
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("value", "delete");
		map3.put("text", "删除");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map1);
		list.add(map);
		list.add(map2);
		list.add(map3);
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
	
	
	@RequestMapping("/getGoodsTypeById")
	@ResponseBody
	public List<Map<String, Object>>  getGoodsTypeById(String id, HttpServletRequest request) {
		if(id==null) return null;
		return goodsTypeService.getListMapByPrentId(id);
	}
	
	@RequestMapping("/getGoodsType")
	@ResponseBody
	public List<Map<String, Object>> getGoodsType(HttpServletRequest request) {
		List<Map<String, Object>> ret =  goodsTypeService.getGoodsType();
		return ret;
	}
	
	/**
	* 功能描述： 获取商户所有车辆信息
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月1日下午2:42:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	@RequestMapping("/getCompanyTrucks")
	@ResponseBody
	public List<Map<String, Object>>  getCompanyTrucks(HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		return truckService.getCompanyTrucks(account.getCompany().getId());
	}
	
	
	@RequestMapping("/chekcTruckNum")
	@ResponseBody
	public Map<String, Object>  getChekcTruckNum(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = UserUtil.getUser(request);
		map.put("num", truckService.getChekcTruckNum(account.getCompany().getId()));
		return map;
	}
	
	
}
