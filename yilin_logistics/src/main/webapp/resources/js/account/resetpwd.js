/*$.validator.setDefaults({
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
}),*/



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


$().ready(function() {
	

	
	$("#send").on('click',function(){
		var obj = $(this);
		var webPath = obj.attr('data-url');
		var phone = $("#phone").val();
		var validator = $( "#js_form" ).validate();
		if(!validator.element( "#phone" )){
			return false;
		}
		
		var code =$("#code").val();
		
	    $.ajax({
			  type: 'POST',
			  url: webPath+'/account/register/resetpw/resetpwCode.do',  
			  data: {"phone":phone,"code":code},
			  dataType: "json",
			  success:function(result){  
				  var html ='<i class="ui-tiptext-icon iconfont"></i>';
				  if(result.success){
					  $("#code_controls").removeClass("ui-form-item-error");
					  $("#code_controls").addClass("ui-tiptext-success");
					  $(".ui-form-explain").html(html+"验证码已发送到您的手机，15分钟内输入有效，请勿泄露");
					  settime(obj);
				  }else{
					  
					  countdown = 60-parseInt(result.msg);
					  settime(obj);
					 // $("#code_controls").removeClass("ui-tiptext-success");
					 // $("#code_controls").addClass("ui-form-item-error");
					 // $(".ui-form-explain").html('<i class="ui-tiptext-icon iconfont"></i>'+result.msg);
					  //layer.tips(result.msg, '#send'); 
				  }
			  },
		});
	});
	
    var e = "<i class='fa fa-times-circle'></i> ";
    $("#js_form").validate({
    	 submitHandler: function(form){
    		 $.ajax({
         		  type: 'POST',
         		  url: 'resetCodeCheck.do',  
         		  data:$('#js_form').serialize(),
         		  dataType: "json",
         		  async:false,
         		  success:function(result){
         			 if(result.success){
         				top.location.href="../../account/register/restPasword.do";
         			  }else{
         				 // l.stop();
         				 // layer.tips(result.msg, '#code'); 
         				  if("短信验证码不正确" == result.msg) {
         					 var html='<label id="code-error" class="error" for="code"><i class="fa fa-times-circle"></i> '+result.msg+'</label>'
             				 $("#code").after(html);
         				  }else {
         					 var html='<label id="code-error" class="error" for="code"><i class="fa fa-times-circle"></i> '+result.msg+'</label>'
             				 $("#code-img").after(html);
         				  }
         				 
         				
         			  }
         		  },
	         		error:function(){
					    alert("系统异常！");
					}
           	 });
			 return false;
		 },
        rules: {
        	account: {
                required: !0,
                userName:true,
                minlength: 6,
                remote:{
                	  type:"POST",
                      url:"checkName.do",
                }
            },
            password: {
                required: !0,
                maxlength:16,
                minlength: 6
            },
            confirm_password: {
                required: !0,
                minlength: 6,
                equalTo: "#password"
            },
            phone: {
                required: !0,
                isMobile: !0,
                remote:{
              	  type:"POST",
                  url:"restPhone.do",
              }
            },
            code: "required",
            iamgeCode:"required",
            agree: "required",
            companyType: "required",
        },
        messages: {
            account: {
                required: e + "请输入您的用户名",
                minlength: e + "用户名必须6个字符以上",
                remote:"用户名重复"
            },
            password: {
                required: e + "请输入您的密码",
                minlength: e + "密码必须6个字符以上",
                maxlength: e + "密码必须小于16个字符"
                
            },
            confirm_password: {
                required: e + "请再次输入密码",
                minlength: e + "密码必须6个字符以上",
                equalTo: e + "两次输入的密码不一致"
            },
            phone: {
                required: e + "请输入您手机号",
                remote:"该手机没在平台注册过"
            },
            code: {
                required: e + "请输入短信验证码",
            },
            iamgeCode: {
                required: e + "请输入验证码",
            },
            agree: {
                required: e + "必须同意协议后才能注册",
                element: "#agree-error"
            },
            companyType: {
                required: e + "请选择商户类型",
            }
            
        }
    });
});