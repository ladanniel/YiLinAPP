package com.memory.platform.module.websocket.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping("test")
	public String test(HttpServletRequest req,String userName) {
		if( userName != null && !"".equals(userName))
		req.getSession().setAttribute(WebContants.SESSION_USERNAME, userName);
		System.out.println("我还是进来咯");
		return "index.jsp";
	}
}
