package com.memory.platform.module.app.additional.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.module.additional.service.IAdditionalExpensesRecordService;
import com.memory.platform.module.global.controller.BaseController;

/**
 * aiqiwu 2017-09-12 附加费用记录控制器
 */
@Controller
@RequestMapping("app/additional/record")
public class AdditionalExpensesRecordController extends BaseController {
	@Autowired
	private IAdditionalExpensesRecordService additionalExpensesRecordService;

	@RequestMapping(value = "getRecordByRobOrderConfirmId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRecordByRobOrderConfirmId(String robOrderConfirmId, HttpServletRequest request) {
		Map<String, Object> ret = additionalExpensesRecordService.getRecordByRobOrderConfirmId(robOrderConfirmId);
		return jsonView(true, "成功获取附加费用详细列表", ret);
	}
}
