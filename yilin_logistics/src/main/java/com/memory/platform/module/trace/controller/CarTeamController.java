package com.memory.platform.module.trace.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.truck.TruckType;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.trace.dto.GaodeApiConfig;
import com.memory.platform.module.trace.service.IGaoDeWebService;
import com.memory.platform.module.trace.service.impl.GaoDeWebService;
import com.memory.platform.module.truck.service.ITruckService;
import com.memory.platform.module.truck.service.ITruckTypeService;

 
@Controller
@RequestMapping("/trace")
public class CarTeamController extends BaseController {
	@Autowired
	IGaoDeWebService gaoDeWebService;
	@Autowired
    ITruckService truckService;
	@ModelAttribute
	public void before(Model model){
		GaodeApiConfig cfg = gaoDeWebService.getConfig();
		 
		model.addAttribute("gaoDeCfg", cfg);
	}
	@RequestMapping("/index")
	public String index(Model model){
		
		return "/trace/index";
	}
	@RequestMapping("/myTraceCar")
	@ResponseBody
	public Map<String,Object> getMyTraceCar(){
		Account a= UserUtil.getAccount();
		List<Map<String,Object>> traceCar = truckService.getTraceCarInfomation(a.getCompany().getId());
		return super.jsonView(true, "",traceCar);
	}
}
