
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编辑员工信息</title>
    <meta name="keywords" content="易林物流平台">
    <meta name="description" content="易林物流平台">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/bootstrap-table/bootstrap-editable.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
	<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
	
</head>

<body>
	<div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-8" style="margin-left:10%;margin-right:10%;">
					<form class="form-horizontal m-t" id="editUserInfo">
		                        	<input id="id" name="id" type="hidden" value="${user.id}" >
		                            <div class="form-group">
		                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>帐号：</label>
		                                <div class="col-sm-8">
		                                    <input id="account"name="account" maxlength="16" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" value="${user.account}" class="form-control" required="" aria-required="true">
		                                </div>
		                            </div>
		                            <div class="form-group">
		                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>姓名：</label>
		                                <div class="col-sm-8">
		                                    <input id="name" name="name" class="form-control"  value="${user.name}" required="" aria-required="true">
		                                </div>
		                            </div>
		                            <div class="form-group">
		                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>电话：</label>
		                                <div class="col-sm-8">
		                                   <input id="phone" name="phone" type="text" class="form-control" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11" class="form-control" required="" aria-required="true" value="${user.phone}">
		                                </div>
		                            </div>
		                            <div class="form-group" id="show-coed" hidden="true">
			                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>手机验证码：</label>
			                                <div class="col-sm-4">
			                                    <input id="code" name="code"  type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  maxlength="6" value="666666"  class="form-control" >
			                                </div>
			                                <div class="col-sm-1">
			                                    <button type="button" id="send" class="btn btn-primary" data-url="${WEB_PATH}" >获取短信验证码</button>
			                                </div>
			                		</div>
		                             <div class="form-group">
		                            	<label class="col-sm-3 control-label">组织机构：</label>
		                            	<div class="col-sm-8">
		                            		<select class="form-control" name="companySection.id" id="source">
												<@companySection>
													<#list companySections as vo >
														<option value="${vo.id}" <#if vo.id == user.companySection.id> selected</#if>>${vo.name}</option>
													</#list>
												</@companySection>
											</select>
			                            </div>
		                            </div>
		                            <div class="form-group">
		                            	<label class="col-sm-3 control-label">角色：</label>
		                            	<div class="col-sm-8">
				                            <div class="input-group">
				                            	<select class="form-control" name="role.id">
			                                     <@roleInfo>
							                    	 <#list roleviews as role >
						                              	 <option value="${role.id}" hassubinfo="true" <#if role.checked == 'true'> selected = "selected"</#if>>${role.name}</option>
						                             </#list>
												</@roleInfo>
											 	</select>
				                            </div>
			                            </div>
		                            </div>
		                            <div class="form-group">
		                            	<label class="col-sm-3 control-label">状态：</label>
		                            	<div class="col-sm-8">
			                                <div class="radio radio-info radio-inline">
			                                   <input type="radio" id="inlineRadio1" value="start" name="status" <#if user.status ="start"> checked="true" </#if>>
			                                   <label for="inlineRadio1"> 正常 </label>
			                                </div>
			                                <div class="radio radio-inline">
			                                    <input type="radio" id="inlineRadio2" value="stop" name="status" <#if user.status = "stop"> checked="true" </#if>>
			                                    <label for="inlineRadio2"> 停用 </label>
			                                </div>
			                                 <div class="radio radio-inline">
			                                    <input type="radio" id="inlineRadio3" value="cancel" name="status" <#if user.status = "cancel"> checked="true" </#if>>
			                                    <label for="inlineRadio3">注销 </label>
			                                </div>
		                                </div>
		                            </div>
		                    </form> 
			     	 </div>
			    </div>
		</div>
	</div>
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/ajax.extend.js"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/plugins/wizard/jquery.bootstrap.wizard.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-table.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/bootstrap-table/bootstrap-editable.js"></script> 
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.method.js"></script>
    <script src="${WEB_PATH}/resources/js/city-picker.data.js"></script>
	<script src="${WEB_PATH}/resources/js/city-picker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
	<script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/chosen/chosen.jquery.js"></script>
	 <script>
     $.validator.setDefaults({
	    highlight: function(e) {
	        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
	    },
	    success: function(e) {
	        e.closest(".form-group").removeClass("has-error").addClass("has-success")
	    },
	    errorElement: "span",
	    errorPlacement: function(e, r) {
	        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
	    },
	    errorClass: "help-block m-b-none",
	    validClass: "help-block m-b-none"
	});
 			var e = "<i class='fa fa-times-circle'></i> ";
        	 $("#editUserInfo").validate({
        	 	rules: {
		        	account: {
		                required: !0,
		                rangelength: [6, 16],
		                remote:{
		                	  type:"POST",
		                      url:"${WEB_PATH }/system/user/checkName.do?old=${user.account}",
		                }
		            },
		            name: {
		            	required: !0,
		            },
		            code:{
		            	required: !0,
		            	rangelength: [6,6],
		            },
		            phone: {
		                required: !0,
		                isMobile: !0,
		                rangelength: [11,11],
		                remote:{
		              	  type:"POST",
		                  url:"${WEB_PATH }/system/user/checkPhone.do?old=${user.phone}",
		              }
		            }
		        },
		        messages: {
		            account: {
		                required: e + "请输入用户名。",
		                rangelength: e + "用户名由字母开头及数字或字母组成并且长度为6到16位。",
		                remote: e + "用户名重复。"
		            },
		            name: {
		            	required: e + "请输入姓名。",
		            },
		            code:{
		            	required:e + "请输入验证码。",
		            	rangelength:e + "验证码输入错误。",
		            },
		            phone: {
		                required: e + "请输入手机号。",
		                remote: e + "手机号已被注册过。",
		                rangelength:e + "请输入正确的手机号码。",
		                isMobile:e+"请输入正确的手机号码。"
		            }
		        }
		});    
		
		$("#phone").blur(function(){
        	var phone = "${user.phone}";
        	var $this = $("#phone").val();
        	if(phone != $this){
        		$("#code").val("");
        		$("#show-coed").show();
        	}else{
        		$("#code").val("666666");
        		$("#show-coed").hide();
        	}
        });
		
		var countdown=60; 
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
	//获取验证码   
	$("#send").on('click',function(){
		var obj = $(this);
		var webPath = obj.attr('data-url');
		var phone = $("#phone").val();
		var validator = $( "#editUserInfo" ).validate();
		if(!validator.element( "#phone" )){
			return false;
		}
		
	    $.ajax({
			  type: 'POST',
			  url: webPath+'/account/register/sendCode.do',  
			  data: {"phone":phone},
			  dataType: "json",
			  success:function(result){  
				  if(result.success){
					  settime(obj);
				  }else{
					  layer.tips(result.msg, '#send'); 
				  }
			  },
		});
	});
		
		function editInfo(){
     		if($("#editUserInfo").valid()){
			   	  $('#saveBut').attr("disabled",true); 
			   	  layer.msg('提交中......', {icon: 16,shade: 0.3,time:8000});
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/user/update.do',  
					  data: $('#editUserInfo').serialize(),
					  dataType: "json",
						 success:function(result){  
							layer.alert('<font color="#1ab394">'+result.msg+'</font>', {
							  	skin: 'layui-layer-molv',
							  	icon: 1,
							  	closeBtn: 0
							},function(){
								parent.$("#exampleTableEvents").bootstrapTable('refresh');
							   	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							   	parent.$('#addUser').attr("disabled",false);
								parent.layer.close(index); 
								
							});
						 },
						 error:function(data){
						 	var data = $.parseJSON(data.responseText);
					        layer.alert('<font color="red">'+ data.msg+'</font>', {
							  skin: 'layui-layer-molv' ,
							  closeBtn: 0,
							  icon: 5
							},function(index){
							   	layer.close(index);
							});
						 }
					});
        	 }
     	}
  </script>   
</body>
</html>