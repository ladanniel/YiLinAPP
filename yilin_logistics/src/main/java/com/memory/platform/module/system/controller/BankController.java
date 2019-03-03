package com.memory.platform.module.system.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.sys.Bank;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.controller.BaseController;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月25日 上午10:05:07 
* 修 改 人： 
* 日   期： 
* 描   述： 银行控制器
* 版 本 号：  V1.0
 */
import com.memory.platform.module.system.service.IBankService;
@Controller
@RequestMapping("/system/bank")
public class BankController extends BaseController {

	@Autowired
	private IBankService bankService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/system/bank/index";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Bank bank,HttpServletRequest request) {
		return this.bankService.getPage(bank, getStart(request), getLimit(request));
	}
	
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		return "/system/bank/add";
	}
	
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model,String id, HttpServletRequest request) {
		model.addAttribute("vo", bankService.getBankById(id));
		return "/system/bank/edit";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> save(Bank bank,HttpServletRequest request) {
		String path = AppUtil.getUpLoadPath(request);
		if(StringUtil.isEmpty(bank.getCnName())){
			throw new BusinessException("银行中文全称不能为空。");
		}
		if(StringUtil.isEmpty(bank.getShortName())){
			throw new BusinessException("银行英文缩写不能为空。");
		}
		if(StringUtil.isEmpty(bank.getImage())){
			throw new BusinessException("请上传银行Logo图片。");
		}
		if(StringUtil.isEmpty(bank.getMarkImage())){
			throw new BusinessException("请上传银行标志图。");
		}
		bank.setCreate_time(new Date());
		try {
			bankService.saveInfo(bank,path);
		} catch (IOException e) {
			return jsonView(false, "上传图片出错，联系管理员。");
		}
		return jsonView(true, SAVE_SUCCESS);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(Bank bank,HttpServletRequest request) {
		String path = AppUtil.getUpLoadPath(request);
		if(StringUtil.isEmpty(bank.getCnName())){
			throw new BusinessException("银行中文全称不能为空。");
		}
		if(StringUtil.isEmpty(bank.getShortName())){
			throw new BusinessException("银行英文缩写不能为空。");
		}
		if(StringUtil.isEmpty(bank.getImage())){
			throw new BusinessException("请上传银行Logo图片。");
		}
		try {
			bankService.updateInfo(bank,path);
		} catch (IOException e) {
			return jsonView(false, "上传图片出错，联系管理员。");
		}
		return jsonView(true, "修改成功。");
	}
	
	@RequestMapping("/remove")
	@ResponseBody
	public Map<String, Object> remove(String id){
		bankService.removeInfo(id);
		return jsonView(true, "删除成功。");
	}
	
	@RequestMapping("/checkShortName")
	@ResponseBody
	public boolean checkShortName(String shortName,String oldName){
		Bank bank = bankService.getBankByShortName(shortName);
		if(null == bank){
			return true;
		}
		if(StringUtil.isNotEmpty(oldName)){
			if(oldName.equals(bank.getShortName())){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping("/checkCnName")
	@ResponseBody
	public boolean checkCnName(String cnName,String oldName){
		Bank bank = bankService.getBankByCnName(cnName);
		if(null == bank){
			return true;
		}
		if(StringUtil.isNotEmpty(oldName)){
			if(oldName.equals(bank.getCnName())){
				return true;
			}
		}
		return false;
	}
}
