package com.memory.platform.module.websocket.controller;

import java.net.InetSocketAddress;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.memory.platform.entity.member.Account;


public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

	    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object
	                > attributes) throws Exception {
	    	System.out.println("握手协议前");
	        if (request instanceof ServletServerHttpRequest) {
	            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
	            HttpSession session = servletRequest.getServletRequest().getSession(false);
	            if (session != null) {
	            	Account account  = (Account)session.getAttribute("USER");
	            	attributes.put("USER",account);
	                //使用userName区分WebSocketHandler，以便定向发送消息
	            }
	        }
	        return true;
	    }
	 
	    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
	    	System.out.println("握手协议后");
	    }
	    
	    
	    public String getIpAddr(ServerHttpRequest request) {
	        InetSocketAddress remoteAddress = request.getRemoteAddress();
	        String ip = remoteAddress.getHostName();
	        return ip; 
	    }
}
