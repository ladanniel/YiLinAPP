package com.memory.platform.module.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.sys.Feedback;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IFeedbackService;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月25日 上午10:05:30 
* 修 改 人： 
* 日   期： 
* 描   述： 	意见反馈控制器
* 版 本 号：  V1.0
 */

@Controller
@RequestMapping("/system/feedback")
public class FeedbackController extends BaseController {

	@Autowired
	private IFeedbackService feedbackService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/system/feedback/index";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Feedback feedback,HttpServletRequest request) {
		return this.feedbackService.getPage(feedback, getStart(request), getLimit(request));
	}
}
