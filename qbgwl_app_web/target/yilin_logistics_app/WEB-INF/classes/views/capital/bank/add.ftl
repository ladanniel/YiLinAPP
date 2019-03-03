<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title">添加银行卡</h6></br>
                    <form class="form-horizontal validate big" id="form-card"> 
						          <div class="form-group">
						            <label class="control-label col-md-3">真实姓名：</label>
						            <div class="col-md-8">
					            		<p class="form-control-static">
						            	<#if user.name != null && user.name != "">
						            		*${(user.name?substring(1,user.name?length))!}
					               		<#else>
						               		你还没有通过实名认证
						            	</#if>
						            	</p>
						            </div>
						          </div>
						          <div class="form-group">
		                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>银行卡号：</label>
		                                <div class="col-sm-8 card-number-content">
		                                   <input type="text" id="card-number" class="form-control" required="" aria-required="true" name="bankCard" maxlength="32"/>
		                                </div>
		                           </div>
						          <div class="form-group">
		                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>银行预留号码：</label>
		                                <div class="col-sm-8">
		                                   <input id="mobile" name="mobile" type="number" class="form-control" minlength="11" maxlength="11" required="" aria-required="true"  disabled>
		                                </div>
		                           </div>
		                           <div class="form-group">
			                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>手机验证码：</label>
			                                <div class="col-sm-4">
			                                    <input id="code" name="code"  type="number"  minlength="6" maxlength="6"  class="form-control"  disabled>
			                                </div>
			                                <div class="col-sm-1">
			                                    <button type="button" id="send" class="btn btn-primary" data-url="${WEB_PATH}"  disabled>获取短信验证码</button>
			                                </div>
			                      </div>
						          <div class="form-group">
						            <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>开户行名称：</label>
						            <div class="col-md-8">
						               <input type="text" required="" aria-required="true" class="form-control" id="openBank" name="openBank" maxlength="128"  disabled/>
						            </div>
						          </div>
						           <div class="form-group">
						            <div class="col-md-8 col-md-offset-3">
						            	<p class="help-block">
						            	为保证提现及时收款，请填写中国**银行**省**市**支行或分行**分理处(如：中国农业银行浙江省杭州市西湖支行)
						            	</p>
						            </div>
						          </div>
					          <div class="form-group">
					          	<label class="control-label col-md-4"></label>
					            <div class="col-md-7">
					              <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
					              <button type="submit" class="btn btn-primary" id="saveCard">
					               	<span class="glyphicon glyphicon-ok"></span> 
					               	确认添加
					              </button>
					            </div>
					          </div>
					          
					        </form>
					        <div class="alert alert-info big">
					        <div class="col-md-1">
					        </div>
		     	 		 	<h4>温馨提示</h4>
		     	 		 	<ol class="padding-left-20">
							  <li>请您输入正确的开户行以及银行卡号。</li>
							  <li>系统会根据输入的卡号信息智能识别银行以及卡种。</li>
							  <li>如果您填写的银行信息不正确可能会导致提现失败。</li>
							  <li>如果您的银行卡信息系统不能识别，请及时联系客服。</li>
							</ol>
	     	 		 </div>
                </div>
            </div>
        </div> 
    </div>
    
 <script src="${WEB_PATH}/resources/js/capital/bankinput.js"></script>   
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
 		$("#card-number").bankInput();//银行卡格式化
 		var e = "<i class='fa fa-times-circle'></i> ";
        	 $("#form-card").validate({
        	 	rules: {
		        	bankCard: {
		                required: !0,
		            },
		            mobile: {
		                required: !0,
		                number: !0,
		                isMobile:!0
		            },
		            openBank: {
		                required: !0,
		            },
		            code: {
		            	required: !0,
		            	number: !0,
		            }
		        },
		        messages: {
		            bankCard: {
		               required: e + "请输入银行卡号。",
		               remote: e + "银行卡输入错误无法被检测，或该卡已被绑定。",
		            },
		            mobile: {
		            	required: e + "请输入银行预留手机号。",
		            	number: e + "请输入数字。",
		            },
		            openBank: {
		               required: e + "请输入开户行名称。",
		            },
		            code: {
		                required: e + "请输入手机验证码。",
		            }
		        }
		});
        $('#saveCard').click(function(){
			 	if($("#form-card").valid()){ 
			   	  $('#saveCard').attr("disabled",true); 
			   	   $.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/capital/bankcard/add.do',  
					  data: $('#form-card').serialize(),
					  dataType: "json",
					  success:function(result){  
							if(result.success == true){
								$('#userModal').remove();
								swal("", result.msg, "success");
								setTimeout(function () { 
							        window.location.reload();
							    }, 1000);
							}
					   },
					   error:function(data){
					   		$('#saveCard').attr("disabled",false);  
	                        var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
                       }
					});
        	 }
        });
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').remove();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').remove();
       }); 
       
       
	$('#card-number').blur(function(){
		var number = $("#card-number").val();
		var html = '<span id="alert">正在检测....</span>' 
		$("#card-number").after(html);
		$.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/capital/bankcard/verify.do',  
					  data: {"number":number},
					  dataType: "json",
						 success:function(data){  
						 	$("#mobile").attr("disabled",false);
						 	$("#openBank").attr("disabled",false);
						 	$("#code").attr("disabled",false);
						 	$("#send").attr("disabled",false);
							$("#alert").remove();
							$(".card-number-content").empty();
							var charname = new Array();
							var number = data.bankCard;
							$("#bankCard").val(number);
							var newNumber = '';
							for(var i=0;i<number.length;i++){
								if(i > 3 && i< (number.length-3) ){
									charname.push('*');
								}else{
									charname.push(number[i]);
								}
							}
							for(var i=0;i<charname.length;i++){
								newNumber = newNumber + charname[i];
							}
							var result = '<div class="form-control-static">' + newNumber + '('+ data.bankName + ')</div>';
							result += '<input type="hidden" class="form-control" id="bankCard" name="bankCard" value="'+number+'"/>';
							$(".card-number-content").append(result);
						},
						error:function(data){
							$("#alert").remove();
							var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						}
		});
	});
	
  });   
  </script>   
