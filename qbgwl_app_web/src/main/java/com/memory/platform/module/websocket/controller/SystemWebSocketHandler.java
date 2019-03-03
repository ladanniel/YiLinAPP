package com.memory.platform.module.websocket.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.memory.platform.entity.member.Account;

public class SystemWebSocketHandler implements WebSocketHandler {

	
	private static final Map<String,WebSocketSession> users;
	static {
		users = new HashMap<String,WebSocketSession>();
	}

	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		System.out.println("连接成功 ");

		Account account = (Account) session.getAttributes().get("USER");
		//userService.updateUserStart(user.getId(), true);
		if (account != null) {
			users.put(account.getId(),session);
			for (Entry<String, WebSocketSession> entry : users.entrySet()) {
				WebSocketSession tempSession =  entry.getValue();
				tempSession.sendMessage(new TextMessage(json(tempSession)));
			}
			
		}
	}


	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		System.out.println("接收信息");
		String json = (String) message.getPayload();
		Map<String, Object> map = toMap(json);
		sendMessage(map,session);
		// session.sendMessage(new TextMessage("这个就是人生的意思么？？？？"));
	}

	private void sendMessage(Map<String, Object> map,WebSocketSession cursession)  throws Exception {
//		String sendUserName = (String) map.get("sendUser");
		String sendMsg = (String) map.get("msgInfo");
//		String curUserName = (String) cursession.getAttributes().get(
//				WebContants.SESSION_USERNAME);
		for (Entry<String, WebSocketSession> entry : users.entrySet()) {
			WebSocketSession session = entry.getValue();
//			String userName = (String) session.getAttributes().get(
//					WebContants.SESSION_USERNAME);
			
			String json = json(session,cursession,sendMsg);
			session.sendMessage(new TextMessage(json));
			
//			if(userName.equals(sendUserName)) {
//				String json = json(session,sendMsg);
//				session.sendMessage(new TextMessage(json));
//			}
//			if( userName.equals(curUserName) ) {
//				String json = json(cursession,sendMsg);
//				cursession.sendMessage(new TextMessage(json));
//			}
		}
	}

	private Map<String, Object> toMap(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<Map<String,Object>>() {
		}.getType());
	}

	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);
		System.out.println("websocket connection closed......");
	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		String tempKey = null;
		for (Entry<String, WebSocketSession> entry : users.entrySet()) {
			WebSocketSession sessionTemp = entry.getValue();
			String user_id  = entry.getKey();
			if(sessionTemp == session) {
				tempKey =user_id;
				continue;
			}
		}
		users.remove(tempKey);
		//userService.updateUserStart(tempKey, true);
		System.out.println("websocket connection closed......");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	private String json(WebSocketSession sendSession) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Account> list = new ArrayList<Account>();
		Account self = null;
		Account sendUser = (Account) sendSession.getAttributes().get("USER");
		for (Entry<String, WebSocketSession> entry : users.entrySet()) {
			WebSocketSession session = entry.getValue();
			Account user  = (Account) session.getAttributes().get("USER");
		
			if (!user.getId().equals(sendUser.getId())) {
				list.add(user);
			} else {
				self = user;
			}
		}
		map.put("userList", list);
		map.put("self", self);
		return JSONObject.valueToString(map);

	}
	
	private String json(WebSocketSession sendSession,WebSocketSession receiveSession,String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		Account sendUser = (Account) sendSession.getAttributes().get("USER");
		Account receiveUser = (Account) receiveSession.getAttributes().get("USER");
		if(receiveUser.getId().equals(sendUser.getId())) {
			map.put("sendUser", sendUser);
		}else {
			map.put("sendUser", receiveUser);
		}
		List<Account> list = new ArrayList<Account>();
		Account self = null;
		for (Entry<String, WebSocketSession> entry : users.entrySet()) {
			WebSocketSession session = entry.getValue();
			Account user = (Account) session.getAttributes().get("USER");
			
			if (!user.getId().equals(sendUser.getId())) {
				list.add(user);
			} else {
				self = user;
			}
		}
		map.put("userList", list);
		map.put("self", self);
		map.put("msg", msg);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		map.put("sendTime", sdf.format(new Date()));
		return JSONObject.valueToString(map);

	}

}