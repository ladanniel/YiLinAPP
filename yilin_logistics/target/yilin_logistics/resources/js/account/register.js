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
}),



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
		var validator = $( "#regForm" ).validate();
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
	
	
    var e = "<i class='fa fa-times-circle'></i> ";
    $("#regForm").validate({
    	 submitHandler: function(form){
    		// $("#saveBtn").attr("disabled","disabled");
    	/*	var l = Ladda.create( document.querySelector( '.ladda-button' ) );
    		l.start();*/
    		 layer.msg('正在努力提交中', {icon: 16});
    		 $.ajax({
         		  type: 'POST',
         		  url: 'add.do',  
         		  data:$('#regForm').serialize(),
         		  dataType: "json",
         		  async:false,
         		  success:function(result){
         			 layer.closeAll(); 
         			 $("#code-error").remove();
         			 $("#saveBtn").attr("disabled",false);
         			 if(result.success){
         				top.location.href="../../system/admin/index.do";
         			  }else{
         				 var html='<label id="code-error" class="error" for="code"><i class="fa fa-times-circle"></i> '+result.msg+'</label>'
         				 $("#code").after(html);
         				 // l.stop();
         				 // layer.tips(result.msg, '#code'); 
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
                remote:{
                	  type:"POST",
                      url:"checkName.do",
                }
            },
            password: {
                required: !0,
                minlength: 6,
                maxlength:16,
                equalNoTo:"#account",
                isPasword:true
               
            },
            confirm_password: {
                required: !0,
                minlength: 6,
                equalTo: "#password"
            },
            phone: {
                required: !0,
                isMobile: !0,
                isInteger:true,
                remote:{
              	  type:"POST",
                  url:"checkPhone.do",
              }
            },
            code: "required",
            agree: "required",
            companyType: "required",
        },
        messages: {
            account: {
                required: e + "请输入您的用户名",
                minlength: e + "用户名必须6个字符以上",
                maxlength: e + "用户名必须小于16个字符",
                remote:"用户名重复"
            },
            password: {
                required: e + "请输入您的密码",
                minlength: e + "密码必须6个字符以上",
                maxlength: e + "密码必须小于16个字符",
                equalNoTo:e + "密码不能和用户名相同",
            },
            confirm_password: {
                required: e + "请再次输入密码",
                minlength: e + "密码必须6个字符以上",
                equalTo: e + "两次输入的密码不一致"
            },
            phone: {
                required: e + "请输入您手机号",
                remote:"手机号已被注册"
            },
            code: {
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