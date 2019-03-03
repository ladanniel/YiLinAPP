package com.memory.platform.module.goods.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.goods.service.IGoodsTypeService;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月14日 下午6:23:55 
* 修 改 人： 
* 日   期： 
* 描   述： 货物操作日志记录控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/goods/goodslog")
public class GoodsAutLogController extends BaseController {

	@Autowired
	private IGoodsBasicService goodsBasicService;
	@Autowired
	private IGoodsTypeService goodsTypeService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		model.addAttribute("goodsTypeList", goodsTypeService.getListByLeav());
		Account account = UserUtil.getUser();
		if(account.getCompany().getCompanyType().getName().equals("货主")){
			model.addAttribute("query", "0");
		}else{
			model.addAttribute("query", "1");
		}
		return "/goods/goodslog/index";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(GoodsBasic goodsBasic,HttpServletRequest request){
		return goodsBasicService.getLogPage(goodsBasic, getStart(request), getLimit(request));
	}
}
