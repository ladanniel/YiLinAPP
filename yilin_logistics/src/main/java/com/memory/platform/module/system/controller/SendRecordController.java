package com.memory.platform.module.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.sys.SendRecord;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ISendRecordService;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月31日 下午2:45:21 
* 修 改 人： 
* 日   期： 
* 描   述： 短信记录 － 控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/sendrecord")
public class SendRecordController extends BaseController {

	@Autowired
	private ISendRecordService sendRecordService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/system/sendrecord/index";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(SendRecord sendRecord,HttpServletRequest request) {
		return this.sendRecordService.getPage(sendRecord, getStart(request), getLimit(request));
	}
	
	@RequestMapping(value = "/view/look", method = RequestMethod.GET)
	protected String look(Model model,String id, HttpServletRequest request) {
		model.addAttribute("vo", sendRecordService.getSendRecord(id));
		return "/system/sendrecord/look";
	}
}
