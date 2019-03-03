<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>资金管理</title> 
    <!--<link rel="shortcut icon" href="favicon.ico">-->
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

<body class="gray-bg">
    <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>资金管理 - 设置支付密码</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
            <div class="ibox-content">
            	<div class="row">
            		<form class="form-horizontal m-t" id="form" method="post"> 
            			<div class="form-group">
						        <label class="col-sm-3 col-md-offset-2 control-label"></label>
						        <div class="col-sm-4">
						            <span class="label label-danger" >支付密码为6位数字</span>
						        </div>
						</div>
	            		<div class="form-group">
							   <label class="control-label col-md-2 col-md-offset-3"><font color="red">*</font>支付密码</label>
							   <div class="col-md-2">
							           <input type="password" maxlength="6" id="payPassword" name="payPassword" class="form-control" required="" aria-required="true"/>
							   </div>
						</div>
						<div class="form-group">
							   <label class="control-label col-md-2 col-md-offset-3"><font color="red">*</font>重复支付密码</label>
							   <div class="col-md-2">
							   		<input type="password" maxlength="6" id="confirm_password" name="confirm_password" class="form-control" required="" aria-required="true"/>
							   </div>
						</div>
						<button type="submit" class="btn btn-primary col-md-offset-5" id="save">
					               	<span class="glyphicon glyphicon-ok"></span>确认添加
					    </button>  
					</form>
					<div class="col-md-12">&nbsp;</div>
					<div class="form-group">
						<div class="col-md-6 col-md-offset-3">
						   	<div class="alert alert-info big">
					        <div class="col-md-1">
					        </div>
		     	 		 	<h4>温馨提示</h4>
		     	 		 	<ol class="padding-left-20">
								  <li>尊敬的易林物流用户，为了保证广大用户的账户资金安全，本站特别增加了支付密码功能。</li>
								  <li>您在使用资金管理之前，可以通过设置个人支付密码来更加安全的在平台上交易。</li>
								  <li>下面简单介绍下支付密码使用场景及注意事项。</li>
								  <li>在本平台上进行转账会使用到您的个人支付密码。</li>
								  <li>如果忘记支付密码可通过平台客服电话或通过绑定手机来找回支付密码。</li>
								  <li>请您务必妥善保管好自己的密码，不要泄漏给其它人。</li>
							</ol>
	     	 		 	</div>
					</div>
					</div>
            	</div>
            </div>
    </div>
    <@import.tableManagerImportScript/> 
    <script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
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
 $(function(){ 
 		var e = "<i class='fa fa-times-circle'></i> ";
        	 $("#form").validate({
        	 	rules: {
		        	payPassword: {
		                required: !0,
		                rangelength: [6, 6],
		                isDigits: !0
		            },
		            confirm_password: {
		                required: !0,
		                rangelength: [6, 6],
		                equalTo: "#payPassword"
		            }
		        },
		        messages:{
		 			 payPassword: {
		                required: e + "请输入您的密码",
		                rangelength: e + "支付密码必须为6位数字。",
		            },
		            confirm_password: {
		                required: e + "请再次输入密码",
		                equalTo: e + "两次输入的密码不一致"
		            }
		 		}
 		});
 		
        $('#save').click(function(){
			 	if($("#form").valid()){   
			   	  $('#save').attr("disabled",true); 
			   	   $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/capital/account/setPayPassword.do',  
					  data: $('#form').serialize(),
					  dataType: "json",
					  success:function(result){  
							if(result.success == true){
								swal("", result.msg, "success");
								parent.location.reload();
							}
						},
						error:function(data){
						 	$('#save').attr("disabled",false); 
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						 }
					});
        	 }
        });
    });
</script>
</body>

</html>