<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Testing websockets</title>
<script type="text/javascript" src="<%=basePath%>resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/socket/sockjs-0.3.min.js"></script>
</head>
<body>
	<div id="userInfo">


	</div>
	<div id="messages"></div>
	<script type="text/javascript">
		var websocket;
		var userName = "${yicoUsername}";
		if(userName== null || userName == "") {
			alert("请先登录")
		}else {
			
			if ('WebSocket' in window) {
				websocket = new WebSocket("ws://127.0.0.1:8080/memory/app.ws");
				//websocket = new MozWebSocket("ws://localhost:8080/xmppserver/myHandler");
				//websocket = new SockJS("http://localhost:8080/xmppserver/sockjs/myHandler");
			} else if ('MozWebSocket' in window) {
				websocket = new MozWebSocket(
						"ws://localhost:8080/websocket/myHandler");
			} else {
				websocket = new SockJS(
						"http://localhost:8080/websocket/sockjs/myHandler");
			}
			websocket.onopen = function(evnt) {
				//alert(evnt.type);
			};
			websocket.onmessage = function(evnt) {
				var data = jQuery.parseJSON(evnt.data);
				var userList = data.userList;
				var curUser = data.self;
				var msg = data.msg;
				var sendUserName = data.sendUserName;
				var sendTime = data.sendTime;
				$("#userList option").remove();
				for(var i=0;i<userList.length;i++){  
					var tempUser = userList[i];
				    $("#userList").append('<option value="'+tempUser.userName+'">'+tempUser.userName+'</option>');     
				}
				if(msg == undefined || msg == null) {
					$("#userInfo").text("当前登录用户："+curUser.name);
				}else {
					var title;
					if(sendUserName==curUser.userName) {
						title = "<span style='color:green;'>"+sendUserName+"   "+sendTime+"</span><br>";
					}else {
						title = "<span style='color:blue;'>"+sendUserName+"   "+sendTime+"</span><br>";
					}
					
					$("#msgcount").append(title+data.msg+"<br>");
				}
				
				
			};
			websocket.onerror = function(evnt) {
				alert(evnt);
			};
		}
		
		
		function startSend() {
			var map = {"msgInfo":$("#textMsg").val(),"sendUser":$("#userList").val()};
			var jsonStr = JSON.stringify(map);
			websocket.send(jsonStr);
			
		}
		
		$(document).keypress(function(e) {  
		    // 回车键事件  
		       if(e.which == 13) {  
		    	   startSend();
		    	   $("#textMsg").val("");
		       }  
		   }); 
	</script>
	<input type="button" value="发送" onclick="startSend()" />
	<textarea id="textMsg" rows="10" cols="50"></textarea><br>
	<select id="userList" style="width:300px;"></select>
	<div id="msgcount" style="width: 500px;height: 400px;background: #fee;margin-left: 300px;">
		
	</div>
	
</body>
</html>
