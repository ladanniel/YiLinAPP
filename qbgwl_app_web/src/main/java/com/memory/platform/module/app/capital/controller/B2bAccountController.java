package com.memory.platform.module.app.capital.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.XmlUtils;
import com.memory.platform.entity.capital.B2bAccount;
import com.memory.platform.entity.capital.ResData;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.capital.service.IB2bAccountService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;



@Controller
@RequestMapping("app/capital/b2b")
public class B2bAccountController extends BaseController {

	@Autowired
	IB2bAccountService b2bAccountService;
	@Autowired
	IAccountService accountService;
	@Autowired
	IIdcardService idcardService;
	
	/**
	 * 获取商户公钥
	 * */
	@RequestMapping(value = "getKey", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> getKey(){
	
		ResData data=b2bAccountService.getKey();

		return jsonView(data.getCode()=="000000"?true:false, data.getMessage(), data.getData());
	}
	
	/**
	 * 子账户开户
	 * */
	@RequestMapping(value = "saveB2bAccount", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> saveB2bAccount(String accountId){
		if(accountId==null) {
			 throw new  BusinessException("accountId不能为空");
		}
		Account account=accountService.getAccount(accountId);
		
		B2bAccount b2bAccount=new B2bAccount();

		b2bAccount.setTradeMemBerName(account.getName());
		b2bAccount.setCurrency("CNY");
		b2bAccount.setSubAcc(account.getAccount());
		b2bAccount.setBoothNo("b2b"+account.getAccount());
		b2bAccount.setTradeMemBerGrade(1);
		b2bAccount.setGradeCode("");
		b2bAccount.setTradeMemberProperty(1);
		b2bAccount.setContact(account.getName());
		b2bAccount.setContactPhone(account.getPhone());
		b2bAccount.setPhone(account.getPhone());
		b2bAccount.setContactAddr(account.getCompanyName());
		b2bAccount.setBusinessName(account.getName());
		b2bAccount.setPapersType(10);
		try {
			b2bAccount.setPapersCode(idcardService.get(account.getIdcard_id()).getIdcard_no());
		} catch (Exception e) {
			e.printStackTrace();
		}
		b2bAccount.setIsMessager(1);
		b2bAccount.setMessagePhone(account.getPhone());
		b2bAccount.setEmail("y1911214317@qq.com");
		
		ResData data=b2bAccountService.saveB2bAccount(b2bAccount);

		return jsonView(data.getCode()=="000000"?true:false, data.getMessage(), data.getData());
	}
	
	/**
	 * 根据摊位号（"b2b"+account.getAccount()）查询客户信息
	 * 
	 * */
	@RequestMapping(value = "getB2bAccountByBoothNo", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> getB2bAccountByBoothNo(String boothNo){
		if(boothNo==null) {
			 throw new  BusinessException("摊位号不能为空");
		}
		
		ResData data=b2bAccountService.getB2bAccountByBoothNo(boothNo);
		//将客户B2B XML数据data.getData()转为实体
		B2bAccount b2bAccount=XmlUtils.converyToJavaBean( data.getData(),B2bAccount.class );
		
		return jsonView(data.getCode()=="000000"?true:false, data.getMessage(), b2bAccount);
	}
}
