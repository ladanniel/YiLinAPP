package com.memory.platform.module.global.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memory.platform.entity.order.RobOrderRecordInfo;
import com.memory.platform.module.order.service.IRobOrderRecordInfoService;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月21日 下午4:07:30 
* 修 改 人： 
* 日   期： 
* 描   述： 抢单详情布局控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/global/order")
public class OrderController {

	@Autowired
	private IRobOrderRecordInfoService orderRecordInfoService;
	
	@RequestMapping("/orderviews")
	public String getViews(Model model,HttpServletRequest request,String id) {
		List<RobOrderRecordInfo> infos = orderRecordInfoService.getList(id);
		model.addAttribute("info", infos);
		return "global/orderviews";
	}
}
