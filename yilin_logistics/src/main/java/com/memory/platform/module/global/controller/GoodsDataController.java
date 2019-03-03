package com.memory.platform.module.global.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.module.goods.service.IGoodsAutLogService;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.goods.service.IGoodsDetailService;

@Controller
@RequestMapping("/global/goodsdata")
public class GoodsDataController {

	@Autowired
	private IGoodsBasicService goodsBasicService;
	@Autowired
	private IGoodsDetailService goodsDetailService;
	@Autowired
	private IGoodsAutLogService logService;

	@RequestMapping("/goodsviews")
	public String getUserId(Model model, HttpServletRequest request, String id) {
		GoodsBasic goods = goodsBasicService.getGoodsBasicById(id);
		model.addAttribute("goods", goods);
		model.addAttribute("list", goodsDetailService.getListForMap(goods.getId()));
		model.addAttribute("logList", logService.getListForMap(id));
		return "global/goodsviews";
	}

	@RequestMapping("/getgoodsinfo")
	public String getGoodsInfo(Model model, HttpServletRequest request, String id) {
		GoodsBasic goods = goodsBasicService.getGoodsBasicById(id);
		List<Map<String, Object>> listGoodsDetail = goodsDetailService.getListMapGoodsDetail(goods.getId());
		model.addAttribute("goods", goods);
		model.addAttribute("list", listGoodsDetail);
		return "global/goodsinfo";
	}
}
