package com.memory.platform.module.global.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memory.platform.module.goods.service.IGoodsAutLogService;
import com.memory.platform.module.order.service.IOrderAutLogService;

@Controller
@RequestMapping("/global/log")
public class LogController {

	@Autowired
	private IGoodsAutLogService logService;
	@Autowired
	private IOrderAutLogService orderLogService;
	
	@RequestMapping("/operatelog")
	public String getUserId(Model model,HttpServletRequest request,String goodsId) {
		model.addAttribute("list", logService.getListForMap(goodsId));
		model.addAttribute("type", "goods");
		return "global/operatelog";
	}
	
	@RequestMapping("/orderlog")
	public String orderLog(Model model,HttpServletRequest request,String orderId) {
		model.addAttribute("list", orderLogService.getListForMap(orderId));
		model.addAttribute("type", "order");
		return "global/operatelog";
	}
}
