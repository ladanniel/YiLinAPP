<%@ page language="java" import="java.util.*,java.lang.Exception,com.google.gson.Gson,com.dabei.platform.global.Config" pageEncoding="UTF-8"%>
<%
Exception ex=(Exception)request.getAttribute("exception");
String message = ex.getMessage();

Map<String, Object> map = new HashMap<String, Object>();
map.put("success", false);
map.put("debug", Config.debug);

if(message != null && message.indexOf("<{}>") >= 0){
	//如果有<{}>说明是自定义消息，将debug设为true，前台将直接显示消息
	map.put("debug", true);
	message = message.replace("<{}>", "");
}

map.put("msg", message);

Gson gson = new Gson();
out.print(gson.toJson(map));
%>

