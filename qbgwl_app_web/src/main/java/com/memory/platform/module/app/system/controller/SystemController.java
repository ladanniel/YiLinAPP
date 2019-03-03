package com.memory.platform.module.app.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Feedback;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.service.IAccountService;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月26日 上午10:49:01 
* 修 改 人： 
* 日   期： 
* 描   述： 系统模块级app
* 版 本 号：  V1.0
 */
import com.memory.platform.module.system.service.IFeedbackService;

import javassist.compiler.ast.NewExpr;
@Controller
@RequestMapping("/app/system")
public class SystemController extends BaseController {

	@Autowired
	private IFeedbackService feedbackService;
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IPushService pushService;
	@RequestMapping(value = "saveFeedback")
	@ResponseBody
	public Map<String, Object> saveFeedback(Feedback feedback,String type,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(feedback.getTitle())){
			return jsonView(false, "标题不能为空。");
		}
		if(StringUtil.isEmpty(feedback.getInfo())){
			return jsonView(false, "反馈信息不能为空。");
		}
		if(feedback.getInfo().length() > 512){
			return jsonView(false, "反馈信息字符过长，请删减后提交。");
		}
		if(StringUtil.isEmpty(type)){
			feedback.setType(Feedback.Type.android);
		}
		feedback.setAccount(account);
		feedback.setCreate_time(new Date());
		feedbackService.saveFeedback(feedback);
		return jsonView(true, "保存成功。");
	}
	@RequestMapping(value = "pushTest")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> pushTest(@RequestParam(required=false) String ids,@RequestParam(required=false)String title ,
			@RequestParam(required=false) String content ) {
		ArrayList<String> arrayList =  null;
		if(StringUtil.isNotEmpty( ids)) {
			String[] arrIDS =  ids.split(",");
			arrayList =  new ArrayList<String>(Arrays.asList(arrIDS));
			
		}
		Map<String,String> extendMap = new HashMap<String, String>();
		extendMap.put("goods_basic_id", "4028801662fa87ed0162fc39475a0000");
		pushService.saveRecordAndSendMessageWithAccountIDArray(arrayList, title, content, extendMap, null);
		 Map<String, Object> ret = new HashMap<>();
		
		return jsonView(true, "发送成功"); 
	}
}
