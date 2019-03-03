package com.memory.platform.module.global.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UrlPathHelper;

import com.memory.platform.core.AppUtil;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.ApiStatusCode;
import com.memory.platform.global.StringUtil;
import com.memory.platform.security.annotation.LoginValidate;

public class BaseController {
	protected String RESPONSE_CODE = "rc";
	protected String RESPONSE_MESSAGE = "rm";

	protected	String RESPONSE_BODY = "body";
	protected String RESPONSE_HEADER = "header";
	protected String RESPONSE_RESPONSE = "response";
	protected String RESPONSE_RESULT = "result";

	protected String ACCOUNT = "account";

	protected String APP_VERSION_V1 = "app-version=1";// app版本

	protected String SUCCESS = "success";
	protected String MESSAGE = "msg";
	protected String TOKEN = "token";

	protected String DATA = "data";

	protected String SAVE_SUCCESS_MESSAGE = "保存成功！";
	protected String SAVE_FAIL_MESSAGE = "保存失败:";

	protected String UPDATE_SUCCESS_MESSAGE = "修改成功！";
	protected String UPDATE_FAIL_MESSAGE = "修改失败！";

	protected String REMOVE_SUCCESS_MESSAGE = "删除成功！";
	protected String REMOVE_FAIL_MESSAGE = "删除失败！";

	protected String ACTION_SUCCESS_MESSAGE = "操作成功！";
	protected String ACTION_FAIL_MESSAGE = "操作失败！";

	protected String MAILING_ADDRESS = "贵阳市云岩区枫林小区";
	protected String MAILING_USERNAME = "易林管理员";
	protected 	String MAILING_PHONE = "18285053436";
	protected String SAVE_SUCCESS = "保存成功！";
	protected String SAVE_FAIL = "保存失败:";

	protected String UPDATE_SUCCESS = "修改成功！";
	protected String UPDATE_FAIL = "修改失败！";

	protected String REMOVE_SUCCESS = "删除成功！";
	protected String REMOVE_FAIL = "删除失败！";

	protected String ACTION_SUCCESS = "操作成功！";
	protected String ACTION_FAIL = "操作失败！";
	
	protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
 
	Logger loger = Logger.getLogger(BaseController.class);
    
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;   
        this.session = request.getSession();  
    }  


	@RequestMapping(value = "/view/{viewName}", method = RequestMethod.GET)
	@LoginValidate(false)
	protected String view(HttpServletRequest request,
			@PathVariable String viewName) {

		// String uri = request.getRequestURI();

		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String uri = urlPathHelper.getLookupPathForRequest(request);

		if (null == uri) {
			throw new RuntimeException(AppUtil.getExMsg("未定义的视图！"));
		}

		String str[] = uri.replace(".do", "").split("/");

		StringBuffer sb = new StringBuffer();
		sb.append(str[1]).append("/").append(str[2]).append("/").append(str[4]);

		return sb.toString();
	}

	@RequestMapping(value = "ylwl/index", method = RequestMethod.GET)
	@LoginValidate(false)
	protected String index(HttpServletRequest request,
			@PathVariable String viewName) {
		return "/ylwl/index.html";
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> handleException(Exception ex,
			HttpServletRequest request) {
		String msg = null;
		String code = "";
		if (ex != null && ex instanceof BusinessException) {
			BusinessException businessException = (BusinessException) ex;
			msg = businessException.getMessage();
			code = businessException.getCode();
		} else {
			msg = ex.getMessage();
			String[] args = msg.split("\r\n");
			if (args.length > 1) {
				msg = args[0];
				int index = msg.indexOf("Exception:");
				if (index != -1) {
					msg = msg.substring(index + 10);
				}

			}
		}
		if (loger.isInfoEnabled()) {
			System.out.println("------------系统异常---------------");
			ex.printStackTrace();

		}
		if(StringUtil.isNotEmpty(code)){
			return  jsonViewCode(false, msg,code);
		}
		
		return jsonView(false, msg);
	}

	protected Map<String, Object> jsonView(boolean success, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", message);
		return map;
	}
	protected  Map<String, Object> jsonView(Boolean success,String code ,String message, Object data ,Map<String, Object>  own   ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SUCCESS, success);
		map.put(MESSAGE, message);
		//String token = request.getHeader("token");
		if(StringUtil.isNotEmpty(code)) {
			map.put("code", code);
			
		}
	    if(own!=null) {
	    	int i= 0;
	    	if(data ==null || !(data.getClass()== map.getClass())) {
	    		for (String key : own.keySet()) {
					if(map.containsKey(key) ) {
						map.put(key + i++, own.get(key));
					}else {
						map.put(key , own.get(key));
					}
				}
	    		
	    	}else {
	    		@SuppressWarnings("unchecked")
				Map<String, Object> tMap = (Map<String, Object>) data;
	    		for (String key : own.keySet()) {
					if(tMap.containsKey(key) ) {
						tMap.put(key + i++, own.get(key));
					}else {
						tMap.put(key , own.get(key));
					}
				}
	    	}
	    	
	    }
	
		
		
		map.put(DATA, data);
		return map;
	}
 
	protected Map<String, Object> jsonView(boolean success, String message,Object data,Map<String, Object> own) {
	    return  jsonView(success, null, message, data, own);
		
	 
	}
	protected Map<String, Object> jsonView(ApiStatusCode statusCode,Map<String, Object> body) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> supresult = new HashMap<String, Object>();
		
		
		header.put(RESPONSE_CODE, statusCode.value());
		header.put(RESPONSE_MESSAGE, statusCode.getReasonPhrase());
		
		response.put(RESPONSE_BODY, body);
		response.put(RESPONSE_HEADER, header);
		result.put(RESPONSE_RESPONSE, response);
		supresult.put(RESPONSE_RESULT, result);
		return supresult;
	}

	protected Map<String, Object> jsonView(Map<String, Object> header,Map<String, Object> body) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> supresult = new HashMap<String, Object>();
		
		
		response.put(RESPONSE_BODY, body);
		response.put(RESPONSE_HEADER, header);
		result.put(RESPONSE_RESPONSE, response);
		supresult.put(RESPONSE_RESULT, result);
		return supresult;
	}
	protected Map<String, Object> jsonViewCode(boolean success, String msg,
			String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", msg);
		map.put("code", code);
		return map;
	}
	protected Map<String, Object> jsonView(boolean success, String msg,
			Object data) {
		return jsonView(success,msg,data,true);
	}
	
	protected Map<String, Object> jsonView(boolean success, String msg,
			Object data,boolean isEncrypt) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", msg);
		if(data!=null)
		map.put("data", data);
		if(isEncrypt==false)
		map.put("isEncrypt", isEncrypt);
		return map;
	}

	protected int getStart(HttpServletRequest request) {
		// int start = request.getParameter("page") != null ?
		// Integer.parseInt((String)request.getParameter("page")) - 1 : 0;
		// bootstarp-table分页参数
		int currentPage = request.getParameter("offset") == null ? 1 : Integer
				.parseInt(request.getParameter("offset")); // 获取当前页数
		int showCount = request.getParameter("limit") == null ? 10 : Integer
				.parseInt(request.getParameter("limit"));
		if (currentPage != 0) {// 获取页数
			currentPage = currentPage / showCount;
		}
		return currentPage;
	}

	protected int getLimit(HttpServletRequest request) {
		// int limit = request.getParameter("rows") != null ?
		// Integer.parseInt((String)request.getParameter("rows")) : 20;
		int limit = request.getParameter("limit") == null ? 10 : Integer
				.parseInt(request.getParameter("limit")); // 获取每页行数
		return limit;
	}
	protected Map<String, Object> jsonView(boolean success, String message,Object data,String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SUCCESS, success);
		map.put(MESSAGE, message);
		map.put(TOKEN, token);
		map.put(DATA, data);
//		if(data!=null){
//			String className = data.getClass().getName();
//			if(className.equals("java.util.LinkedList")||className.equals("java.util.ArrayList")){
//				map.put(DATA, desJsonEncode(data,token));
//				return map;
//			}
//			
//			ArrayList<Object> list = new ArrayList<Object>();
//			list.add(data);
//			map.put(DATA, desJsonEncode(list,token));
//			return map;
//		}
		//map.put(DATA, null);
		return map;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
