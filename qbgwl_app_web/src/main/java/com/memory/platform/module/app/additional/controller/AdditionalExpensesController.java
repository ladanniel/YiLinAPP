package com.memory.platform.module.app.additional.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.additional.service.IAdditionalExpensesService;
import com.memory.platform.module.global.controller.BaseController;

/**
 * aiqiwu 2017-09-12 附加费用类型控制器
 */
@Controller
@RequestMapping("app/additional")
public class AdditionalExpensesController extends BaseController {
	@Autowired
	private IAdditionalExpensesService additionalExpensesService;

	@RequestMapping(value = "getAdditionalExpensesAll", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getAdditionalExpensesAll(HttpServletRequest request) {
		List<Map<String, Object>> ret = additionalExpensesService.getAdditionalExpensesAll();
		return jsonView(true, "成功获取附加费类型列表", ret);
	}
}
