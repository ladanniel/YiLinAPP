package com.memory.platform.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.alibaba.druid.sql.visitor.functions.Nil;
import com.google.gson.Gson;
import com.memory.platform.Interface.BaseInterFace;
import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.zookeeper.ZooKeeperManager;

public class OauthAuthorizeInterceptor implements HandlerInterceptor,BaseInterFace {
	@Autowired
	ZooKeeperManager  zookeeperManager;
	
	@Autowired
	private IAccountService accountService;
	Logger log = Logger.getLogger(OauthAuthorizeInterceptor.class);
	private Lock lock = new ReentrantLock();
	@Autowired
	private  IPushService pushService;
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	//	unlLockHandler();
		AppUtil.clearThreadMap();
		
	}


	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		AppUtil.setThreadLocalObject("SESSION", request.getSession());
		AppUtil.setThreadLocalObject("REQUEST", request);
		AppUtil.setThreadLocalObject("RESPONSE", response);
		String token = request.getHeader("token");
		HandlerMethod handlerMethod =  null;
		DefaultServletHttpRequestHandler defaultHandler = null;
		if(HandlerMethod.class == handler.getClass()){
		  handlerMethod = (HandlerMethod) handler;
		  
		  log.info("---------------------------------------------"+ request.getRequestURI());
		}else{
		  defaultHandler = (DefaultServletHttpRequestHandler)handler ;
		  return true;
		}
		 
		
		Account account = null;
		if(StringUtil.isNotEmpty(token)){
			account = accountService.getAccountByToken(token);
			
			request.setAttribute(ACCOUNT, account);
			AppUtil.setThreadLocalObject("USER", account);
		}
		if(handlerMethod!=null){
			UnInterceptor unInterceptor = handlerMethod.getMethod().getAnnotation(UnInterceptor.class); //不拦截注解
			if(unInterceptor!=null){
				return true;
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		if(StringUtils.isBlank(token)){
			 response.setContentType("text/json");//设置格式为text/json 
			 response.setCharacterEncoding("UTF-8");//设置字符集为'UTF-8'
			 
			 map.put(SUCCESS, false);
			 map.put(MESSAGE, "参数有误");
			 map.put(DATA,null);
			 PrintWriter write = response.getWriter();
	         write.write(gson.toJson(map));
	         write.flush();
	         write.close();
	         return false;
		}
		
	
		
		if(account==null){
			 response.setContentType("text/json");//设置格式为text/json 
			 response.setCharacterEncoding("UTF-8");//设置字符集为'UTF-8'
			 
			 map.put(SUCCESS, false);
			 //map.put(MESSAGE, "无效的token" +token);
			 map.put(MESSAGE, "该账号已在其他地方登录，当前登录已失效，请重新登录！");
			 map.put(DATA,null);
			 PrintWriter write = response.getWriter();
	         write.write(gson.toJson(map));
	         write.flush();
	         write.close();
	         return false;
		}
		
		//LocckHandler(handlerMethod);
	 
		/*	HandlerMethod handlerMethod = (HandlerMethod) handler;
		for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {  
	        String parameterName = methodParameter.getParameterName();
	        request.getParameter(parameterName);
	    }*/
		
		return true;
	}

	private void LocckHandler(HandlerMethod handlerMethod) throws Exception {
		if(handlerMethod!=null) {
			
			LockInterceptor lockInterceptor =  handlerMethod.getMethod().getAnnotation(LockInterceptor.class);
			
			if(lockInterceptor!= null) {
				Lock lockObj = null;
				 switch ( lockInterceptor.value()) {
				case global:
					zookeeperManager.getProcessLock().lock();
					AppUtil.setThreadLocalObject("global_lock",true);
					break;

				default:
					break;
				}
			
				 if(lockObj!=null) {
					 lockObj.lock();
					 AppUtil.setThreadLocalObject("lockObj",	lockObj);
					
				 }
			}
			
		}
	}
	

	private void unlLockHandler() throws InterruptedException, KeeperException {
		Lock lockObj = AppUtil.getThreadLocalObjectForKey("LOCK_OBJ");
		if(lockObj!=null ) {
			lockObj.unlock();
		}
		Boolean b = (Boolean)AppUtil.getThreadLocalObjectForKey("global_lock");
		if(b!=null && b) {
			zookeeperManager.getProcessLock().unlock();
		}
		
	}

}
