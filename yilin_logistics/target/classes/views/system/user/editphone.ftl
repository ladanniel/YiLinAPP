<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">更换手机号</h1>
                    <div class="modal-body"> 
                    		<div class="panel blank-panel">
                                    <div class="panel-heading">
                                        <div class="panel-options" id="rootwizard">
                                            <ul class="nav nav-tabs">
                                                <li class="active" style="background:none;" onmouseout="this.style.cssText='background:none;'"><a href="#tab-1"  style="background:none;" onmouseout="this.style.cssText='background:none;'" >验证手机</a>
	                                            </li>
	                                            <li  style="background:none;" onmouseout="this.style.cssText='background:none;'"><a href="#tab-2"  style="background:none;" onmouseout="this.style.cssText='background:none;'">输入新手机</a>
	                                            </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="panel-body">

                                        <div class="tab-content">
                                            <div class="tab-pane active" id="tab-1" style="border: none;">
													<form  class="form-horizontal m-t" id="regForm">
													<input type="hidden" name="codeType" value="editPhone" />
		                                			<div class="form-group">
						                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>验证码：</label>
						                                <div class="col-sm-8">
						                                    <input  name="code" minlength="6" maxLength="6" onkeyup="this.value=this.value.replace(/\D/g,'')"    type="text" class="form-control"  required="" aria-required="true">
						                                </div>
						                            </div>
						                    		<div class="form-group">
						                                <label class="col-sm-3 control-label"></label>
						                                <div class="col-sm-8">
						                                     <button class="btn btn-primary" type="button"  onclick="sendCode(this,'${USER.phone}','editPhone','0')">获取短信验证码</button>
						                                </div>
						                            </div>
						                            </form>
                                            </div>
                                            <div class="tab-pane" id="tab-2" style="border: none;">
													<form  class="form-horizontal m-t" id="newphone">
													<input type="hidden" name="codeType" value="editPhone2" />
		                                			  <div class="form-group">
						                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>新手机号：</label>
						                                <div class="col-sm-8">
						                                    <input  id="phone" name="phone" minlength="11" maxlength="11" type="text"  onkeyup="this.value=this.value.replace(/\D/g,'')"    class="form-control"  required="" aria-required="true">
						                                </div>
						                            </div>
						                    		<div class="form-group">
						                                <label class="col-sm-3 control-label"></label>
						                                <div class="col-sm-8">
						                                     <button class="btn btn-primary" type="button"  onclick="sendCode(this,$('#phone').val(),'editPhone2','1')">获取短信验证码</button>
						                                </div>
						                            </div>
						                            <div class="form-group">
						                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>验证码：</label>
						                                <div class="col-sm-8">
						                                    <input  name="code" minlength="6" maxlength="6"  type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"   class="form-control"  required="" aria-required="true">
						                                </div>
						                            </div>
						                          </form>
														
                                            </div>
                                        </div>

                                    </div>

                            </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                        <button type="submit" class="btn btn-primary" id="saveBut">下一步</button>
                    </div>
                </div>
            </div>
        </div> 
    </div>
 <script>
function sendCode(obj,phone,codeType,flag) {
	if(phone == "") {
		layer.tips('手机号不能为空!', '#phone');
		return false;
	}
	countdown=60;
	var obj = $(obj);
	if("1" == flag) {
		$("#phone").valid();
		setTimeout(function(){
			var node = $("#phone-error");
			if(!node.is(':hidden')){　　//如果node是隐藏的则显示node元素，否则隐藏
			　　$.ajax({
					  type: 'POST',
					  url: '${WEB_PATH}/system/user/sendCode.do',  
					  data: {"phone":phone,"codeType":codeType},
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
			}else{
			　　return false;
			}
		},500);
	}else {
		$.ajax({
			  type: 'POST',
			  url: '${WEB_PATH}/system/user/sendCode.do',  
			  data: {"phone":phone,"codeType":codeType},
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

 $(function(){  
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
 	$("#newphone").validate({
 		rules:{
 			code:{
 				required: !0,
 				minlength:6,
 				maxlength:6
 			},
 			phone:{
 				required: !0,
                isMobile: !0,
                minlength:11,
 				maxlength:11,
                remote:{
              	  type:"POST",
                  url:"${WEB_PATH }/system/user/checkPhone.do",
              }
 			}
 		},
 		messages:{
 			code: {
	                required: e + "请输入验证码",
	                minlength: e + "请正确输入验证码",
	                maxlength: e + "请正确输入验证码",
	           },
	        phone: {
	        	required: e + "请输入您手机号",
	        	isMobile: e + "请正确输入您手机号",
	        	minlength: e + "请正确输入手机号",
	            maxlength: e + "请正确输入手机号",
                remote:"手机号已被占用"
	        }
 		}
 	}); 
    
    $("#rootwizard ul li").on("click",function(){
   		var li = $(this);
     /*	var index = li.index();
    	if(index = 0) {
    		return false;
    	}
    	return $("#regForm").valid(); */

    	return false;
    });
    
     //添加用户信息#commentForm
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
    	//	layer.tips('为什么不验证!'+$("#newphone").valid(), '#phone');
    		if($("#newphone").valid()) {
    			
    			btn.attr("disabled",true);  
    			$.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/user/editphone.do',  
					  data: $('#newphone').serialize(),
					  dataType: "json",
						 success:function(result){  
							if(result.success == true){
								swal("", result.msg, "success");
								setTimeout(function(){
									location.reload();
								},1000);
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
        
       
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').hide();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').hide();
       }); 
  });   
  </script>   
