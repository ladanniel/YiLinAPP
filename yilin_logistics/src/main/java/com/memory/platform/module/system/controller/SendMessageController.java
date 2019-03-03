package com.memory.platform.module.system.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.sys.SendMessage;
import com.memory.platform.entity.sys.SendMessage.Type;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.HTTPUtil;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ISendMessageService;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月31日 下午2:44:37 
* 修 改 人： 
* 日   期： 
* 描   述： 发送短信接口 － 控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/sendmessage")
public class SendMessageController extends BaseController {

	@Autowired
	private ISendMessageService sendMessageService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/system/sendmessage/index";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(SendMessage sendMessage,HttpServletRequest request) {
		return this.sendMessageService.getPage(sendMessage, getStart(request), getLimit(request));
	}
	
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		return "/system/sendmessage/add";
	}
	
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model,String id, HttpServletRequest request) {
		model.addAttribute("vo", sendMessageService.getSendMessage(id));
		return "/system/sendmessage/edit";
	}
	
	@RequestMapping(value = "/send")
	@ResponseBody
	protected Map<String, Object> send(HttpServletRequest request) {
		sendMessageService.saveRecordAndSendMessage("15285112550", "您的验证码是：666666。请不要把验证码泄露给其他人。", "com.memory.platform.module.system.controller.send");
		return jsonView(true,"发送成功");
	}
	
	@RequestMapping(value = "/view/look", method = RequestMethod.GET)
	protected String look(Model model,String id, HttpServletRequest request) {
		SendMessage sendMessage = sendMessageService.getSendMessage(id);
		String str = "";
		if(StringUtil.isNotEmpty(sendMessage.getQueryBalanceUrl())){
			try {
				str = HTTPUtil.sendGet(sendMessage.getQueryBalanceUrl());
			} catch (Exception e) {
				throw new BusinessException("url异常"+e.getMessage());
			}
		}
		String bal = null;
		String array[] = sendMessage.getQueryBalSuccess().split(":");
		if(sendMessage.getType().equals(Type.json)){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JsonPluginsUtil.jsonToMap(str);
			if(!map.get(array[0]).equals(array[1])){
				throw new BusinessException("查询余额url异常，请联系管理员。");
			}
			bal = map.get(sendMessage.getReturnBluKey()).toString();
		}else if(sendMessage.getType().equals(Type.text)){
			Document doc = null;
			try {
				doc = DocumentHelper.parseText(str);
			} catch (DocumentException e) {
				throw new BusinessException("解析数据异常"+e.getMessage());
			} 
			Element root = doc.getRootElement();
			if(!root.elementText(array[0]).equals(array[1])){
				throw new BusinessException("查询余额url异常，请联系管理员。");
			}
			bal = root.elementText(sendMessage.getReturnBluKey());
		}else if(sendMessage.getType().equals(Type.array)){
			String[] arr = str.split(",");
			bal = arr[2];
		}
		model.addAttribute("vo", sendMessage);
		model.addAttribute("bal", bal);
		return "/system/sendmessage/look";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> save(SendMessage sendMessage,HttpServletRequest request) {
		sendMessage.setCreate_time(new Date());
		sendMessageService.saveMessage(sendMessage);
		return jsonView(true, SAVE_SUCCESS);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(SendMessage sendMessage,HttpServletRequest request) {
		sendMessageService.updateMessage(sendMessage);
		return jsonView(true, "修改成功。");
	}
}
