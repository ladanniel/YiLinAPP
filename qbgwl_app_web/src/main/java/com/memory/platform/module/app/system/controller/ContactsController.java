package com.memory.platform.module.app.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.goods.Contacts;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.global.controller.BaseController;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 下午2:49:39 
* 修 改 人： 
* 日   期： 
* 描   述： 联系人控制器
* 版 本 号：  V1.0
 */
import com.memory.platform.module.goods.service.IContactsService;
import com.memory.platform.module.trace.dto.GaodeApiConfig;
import com.memory.platform.module.trace.service.IGaoDeWebService;
import com.memory.platform.module.truck.service.ITruckService;

@Controller
@RequestMapping("/goods/contacts")
public class ContactsController extends BaseController {

	@Autowired
	private IContactsService contactsService;
	@Autowired
	private ITruckService truckService;

	static String SAVE_SUCCESS = "保存成功";

	@RequestMapping("/getPage")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getPage(Contacts contacts, HttpServletRequest request, int start, int limit) {
		Account acount = UserUtil.getAccount();
		String acctountID = acount.getId();
		contacts.setCompanyId(acount.getCompany().getId());
		contacts.setAdd_user_id(acctountID);
		Map<String, Object> data = contactsService.getPage(contacts, start, limit);
		return jsonView(true, SAVE_SUCCESS, data);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> add(Contacts contacts, HttpServletRequest request) {
		Account account = UserUtil.getAccount();
		contacts.setCreate_time(new Date());
		contacts.setAdd_user_id(account.getAdd_user_id());
		contacts.setCompanyId(account.getCompany().getId());
		contactsService.saveContacts(contacts);
		return jsonView(true, SAVE_SUCCESS, account);
	}

	@RequestMapping(value = "/view/gps", method = RequestMethod.GET)
	protected String gps(Model model, String robOrderId, HttpServletRequest request) {
		model.addAttribute("robOrderId", robOrderId);
		return "/goods/contacts/gps";
	}

	@RequestMapping(value = "/gps", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> gps(String robOrderId, HttpServletRequest request) {
		List<Map<String, Object>> carInfo = truckService.getTruckByRobOrderId(robOrderId);
		return super.jsonView(true, "", carInfo);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> edit(Contacts contacts) {
		Contacts prent = contactsService.getContactsById(contacts.getId());
		prent.setAddress(contacts.getAddress());
		prent.setAreaFullName(contacts.getAreaFullName());
		prent.setEmail(contacts.getEmail());
		prent.setMobile(contacts.getMobile());
		prent.setName(contacts.getName());
		prent.setUpdate_time(new Date());
		prent.setPoint(contacts.getPoint());
		prent.setContactsType(contacts.getContactsType());
		prent.setAreaId(contacts.getAreaId());
		contactsService.updateContacts(prent);
		return jsonView(true, "修改联系人成功");
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> remove(String id) {
		contactsService.removeContacts(id);
		return jsonView(true, "删除联系人成功");
	}
}
