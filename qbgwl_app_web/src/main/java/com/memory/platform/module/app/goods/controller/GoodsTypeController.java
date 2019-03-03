package com.memory.platform.module.app.goods.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsTypeService;
import com.memory.platform.module.system.service.IAccountService;

@Controller
@RequestMapping("goods/goodstype")
public class GoodsTypeController extends BaseController{
	
	@Autowired
	IGoodsTypeService goodsTypeService;
	
	@RequestMapping("/getGoodsType")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getGoodsType(HttpServletRequest request) {
 
		List<GoodsType> listGoodsType = goodsTypeService.getAllList();
		return jsonView(true, "", listGoodsType);
	}
}
