package com.memory.platform.global;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;

import com.alibaba.dubbo.rpc.RpcContext;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.AccountDetails;




public class UserUtil {
	public static Account getUser() {
		AccountDetails userDetails = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getAccount(); 
	}
	
	public static Account getUser(HttpServletRequest request) {
		return (Account) request.getSession().getAttribute("USER");
	}
	public static Account getAccount(){
		//Account acc =  AppUtil.getThreadLocalObjectForKey("USER");
		 
		//String tockenID =  RpcContext.getContext().getAttachment("tokenID");
	 
		
		return (Account)AppUtil.getLoginUser();
	}
}