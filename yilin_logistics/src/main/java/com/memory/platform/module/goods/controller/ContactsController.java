package com.memory.platform.module.goods.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.Contacts;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
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

	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/goods/contacts/index";
	}

	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Contacts contacts, HttpServletRequest request) {
		contacts.setCompanyId(UserUtil.getUser(request).getCompany().getId());
		return contactsService.getPage(contacts, getStart(request), getLimit(request));
	}

	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		return "/goods/contacts/add";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Contacts contacts, HttpServletRequest request) {
		contacts.setCreate_time(new Date());
		contacts.setCompanyId(UserUtil.getUser(request).getCompany().getId());
		contactsService.saveContacts(contacts);
		return jsonView(true, SAVE_SUCCESS);
	}

	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id, HttpServletRequest request) {
		model.addAttribute("vo", contactsService.getContactsById(id));
		return "/goods/contacts/edit";
	}

	@RequestMapping(value = "/view/gis", method = RequestMethod.GET)
	protected String gis(Model model, String point, HttpServletRequest request) {
		if (StringUtil.isEmpty(point)) {
			point = "106.720628,26.574155";
		}
		model.addAttribute("point", point);
		return "/goods/contacts/gis";
	}

	@RequestMapping(value = "/view/gps", method = RequestMethod.GET)
	protected String gps(Model model,String robOrderId,HttpServletRequest request) {
		model.addAttribute("robOrderId",robOrderId);
		return "/goods/contacts/gps";
	}
	
	@RequestMapping(value ="/gps", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> gps(String robOrderId,HttpServletRequest request){
		List<Map<String,Object>> carInfo = truckService.getTruckByRobOrderId(robOrderId);
		return super.jsonView(true, "",carInfo);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
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
		return jsonView(true, UPDATE_SUCCESS);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public Map<String, Object> remove(String id) {
		contactsService.removeContacts(id);
		return jsonView(true, REMOVE_SUCCESS);
	}
}
