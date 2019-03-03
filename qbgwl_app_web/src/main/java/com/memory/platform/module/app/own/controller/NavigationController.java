package com.memory.platform.module.app.own.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.global.StringUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.own.service.INavigationService;
import com.memory.platform.module.own.service.impl.NavigationService;

@Controller
@RequestMapping("app/navigation")
public class NavigationController extends BaseController {
	@Autowired
	INavigationService navigationService;
	@RequestMapping(value = "getAction", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getAction(String goodsBasicID,String robOrderRecordID,String robOrderConfirmID ) {
		Map<String, Object> ret = null;
		if(StringUtil.isNotEmpty(goodsBasicID)) {
			ret=  navigationService.getGoodsBasicNavigationAction(goodsBasicID);
		}else if(StringUtil.isNotEmpty(robOrderRecordID)) {
			ret =  navigationService.getRobOrderRecordNavigationAction(robOrderRecordID);
			
		}else if(StringUtil.isNotEmpty(robOrderConfirmID)) {
			ret =  navigationService.getRobOrderRecordConfirmNavigationAction(robOrderConfirmID);
			
		}
		return jsonView(true, "成功", ret);
	 
	}
}
