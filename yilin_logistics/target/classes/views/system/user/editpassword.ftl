<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>密码修改</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/>
    <link href="${WEB_PATH}/resources/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/jsTree/style.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="col-sm-12">
		
		<div class="wrapper wrapper-content animated fadeInUp">
			<div class="ibox-title">
	            <h2>登陆密码修改</h2>
	            <div class="ibox-tools"> 
	            </div>
	        </div>
			<div class="ibox-content">
				<div class="tab-content">
					<div class="tab-pane active" style="border: none;" >
						<form  class="form-horizontal m-t" id="regForm">
							<input type="hidden" name="codeType" value="editPassword" />
	            			<div class="form-group">
	                            <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>验证码：</label>
	                            <div class="col-sm-4">
	                                <input  name="code" minlength="6" maxlength="6" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"  class="form-control"  required="" aria-required="true">
	                            </div>
	                        </div>
	                		<div class="form-group">
	                            <label class="col-sm-3 control-label"></label>
	                            <div class="col-sm-8">
	                                 <button class="btn btn-primary" type="button"  onclick="sendCode(this)">获取短信验证码</button>
	                            </div>
	                        </div>
                        </form>
					</div>
					<div class="tab-pane" style="border: none;" >
						
						<form class="form-horizontal m-t" id="editPasswordId">
						    <div class="form-group">
						        <label class="col-sm-3 control-label"></label>
						        <div class="col-sm-4">
						            <span class="label label-danger" >密码必须是英文和数字的组合</span>
						        </div>
						    </div>
						    <div class="form-group">
						        <label class="col-sm-3 control-label">密码：</label>
						        <div class="col-sm-4">
						            <input id="password" name="password" class="form-control" type="password">
						        </div>
						    </div>
						    <div class="form-group">
						        <label class="col-sm-3 control-label">确认密码：</label>
						        <div class="col-sm-4">
						            <input id="confirm_password" name="confirm_password" class="form-control" type="password">
						            <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 请再次输入您的密码</span>
						        </div>
						    </div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
	                <button type="submit" class="btn btn-primary" id="saveBut">下一步</button>
	            </div>
			</div>
			 
		</div>
	</div>

</body>
<@import.tableManagerImportScript/>
<script>
 $(function(){  
 
 	$('#saveBut').click(function(){
 		var btn = $(this);
    	var text = btn.text();
    	if(text == "下一步") {
    		if($("#regForm").valid()) {
    			 $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/user/validPhone.do',  
					  data: $('#regForm').serialize(),
					  dataType: "json",
						 success:function(result){  
							if(result.success == true){
								$("#rootwizard ul li:eq(1)").addClass("active");
								$(".tab-content .tab-pane:eq(1)").addClass("active").siblings().removeClass("active");
								btn.text("保存"); 
							}else{
								swal({
							        title: "操作失败",
							        text: result.msg
						    	})
							}
						}
					});
			}	
    	}else {
    		if($("#editPasswordId").valid()) {
    			
    			btn.attr("disabled",true);  
    			$.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/user/editpassword.do',  
					  data: $('#editPasswordId').serialize(),
					  dataType: "json",
						 success:function(result){  
							if(result.success == true){
								swal("", result.msg, "success");
								setTimeout(function(){
									location.href = "${WEB_PATH }/system/user/logout.do";
								},3000);
							}else{
								btn.attr("disabled",false);    
								swal({
							        title: "操作失败",
							        text: result.msg
						    	})
							}
						}
					});
    		}
    		
    	}
	 });
	 
	 
 	var e = "<i class='fa fa-times-circle'></i> ";
 	$("#regForm").validate({
 		rules:{
 			code:{
 				required: !0,
 				minlength:6,
 				maxlength:6
 			}
 		},
 		messages:{
 			code: {
	                required: e + "请输入验证码",
	                minlength: e + "请正确输入验证码",
	                maxlength: e + "请正确输入验证码",
	           }
 		}
 	}); 
 	
 	$("#editPasswordId").validate({
 		rules:{
 			 password: {
                required: !0,
                minlength: 6,
                maxlength:12,
                isPasword:true
               
            },
            confirm_password: {
                required: !0,
                minlength: 6,
                maxlength:12,
                equalTo: "#password"
            },
 		},
 		messages:{
 			 password: {
                required: e + "请输入您的密码",
                minlength: e + "密码必须6个字符以上",
                maxlength: e + "密码必须小于12个字符",
            },
            confirm_password: {
                required: e + "请再次输入密码",
                minlength: e + "密码必须6个字符以上",
                equalTo: e + "两次输入的密码不一致"
            }
 		}
 	});
 
});  


function sendCode(obj) {
		countdown=60;
		var obj = $(obj);
	    $.ajax({
			  type: 'POST',
			  url: '${WEB_PATH}/system/user/sendCode.do',  
			  data: {"phone":'${USER.phone}',"codeType":"editPassword"},
			  dataType: "json",
			  success:function(result){  
				  if(result.success){
					  settime(obj);
				  }else {
				  	countdown = 60-parseInt(result.msg);
				  	settime(obj);
				  }
			  },
		});
	}

	countdown=60; 
	function settime(obj){
		 if (countdown == 0) { 
		        obj.attr("disabled",false);    
		        obj.html("免费获取验证码");
		        countdown = 60; 
		        return;
		    } else { 
		        obj.attr("disabled", true); 
		        obj.text("重新发送(" + countdown + ")");
		        countdown--; 
		    } 
		setTimeout(function() { 
		    settime(obj) }
		    ,1000);
	} 
  </script>   
</html>