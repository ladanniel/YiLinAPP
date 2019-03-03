<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title">充值详情</h6></br>
                    
                    <form class="form-horizontal m-t" id="form-recharge">
	                    <div class="modal-body"> 
	                    	<div class="form-group">
	                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>充值金额：</label>
	                                <div class="col-sm-8">
	                                    <input id="money" name="money" type="text"  class="form-control"  required="" aria-required="true" maxlength="11">
	                                </div>
	                        </div>
	                        <@bankCard>
	                        <div id="oldCard">
								<div class="form-group">
									<label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>选择付款方式：</label>
									<div class="col-sm-8">
										<select class="form-control" name="source" id="source">
										<#if bankCards?size == 0>
											<option value="no">未绑定银行卡，不能充值。</option>
										<#else>
											<#list bankCards as vo >
											<option value="${vo.bankCard}"><@bankCard split=vo.bankCard cardType=vo.bankType>${cardVist}${(vo.bankType=="creditCard")?string('(信用卡)','(储蓄卡)')}</@bankCard></option>
											</#list>
										</#if>
										</select>
									</div>
								</div>
	                            <div class="form-group">
	                                 <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>手机验证码：</label>
	                                 <div class="col-sm-4">
	                                     <input id="smsCode" name="smsCode"  type="text"  maxlength="6"  class="form-control">
	                                 </div>
	                                 <div class="col-sm-1">
	                                     <button type="button" class="btn btn-primary" data-url="${WEB_PATH}" onclick="sendCode(this)">获取短信验证码</button>
	                                 </div>
	                             </div>
	                         </div>
	                        </@bankCard>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
	                        <button type="button" class="btn btn-primary" id="saveBut">确定</button>
	                    </div>
                 	</form>
                </div>
            </div>
        </div> 
    </div>
    <script src="${WEB_PATH}/resources/js/capital/bankinput.js"></script>
 <script>
 $('#saveBut').attr("disabled",true);
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
        	 $("#form-recharge").validate({
        	 	rules: {
		        	money: {
		                required: !0,
		                number: !0,
		            },
		            accNo: {
		                required: !0,
		                rangelength:[16,23],
		            }/*,
		            phoneNo: {
		                required: !0,
		                number: !0,
		            }*/
		        },
		        messages: {
		            money: {
		                required: e + "请输入充值金额。",
		                number: e + "请输入正确的充值金额。",
		            },
		            bankCard: {
		               required: e + "请输入银行卡号。",
		               rangelength: e + "请输入正确的银行卡",
		            }/*,
		            phoneNo: {
		            	required: e + "请输入银行预留手机号。",
		            	number: e + "请输入数字。",
		            }*/
		        }
		});
        $('#saveBut').click(function(){
        		$("#smsCode").rules("add", { required: true,number:true});
        		if(dt==null){
        			swal("", "获取验证码失败!", "error");
        			return;
        		}
        		var smsCode = $("#smsCode").val();
        		if($("#form-recharge").valid()){
			   	  $('#saveBut').attr("disabled",true);
			   	  dt.smsCode = smsCode;
			   	  $.yilinAjax({
				  	 type:'POST',
				  	 dataType: "json",
					 contentType:"application/json",
				  	 loadmsg:'正在努力提交......',
				  	 url:'${WEB_PATH }/capital/rechargerecord/rechargeConsume.do',  
				  	 data:JSON.stringify(dt),
					 errorcallback:function(data){
						 	$('#saveBut').attr("disabled",false); 
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
					 },
					 successcallback:function(result){
							if(result.success){
								swal("", result.msg, "success");
								$("#exampleTableEvents").bootstrapTable('refresh');
								$('#userModal').remove();
							}else{
								$('#saveBut').attr("disabled",false); 
								swal("", result.msg, "error");
							}
						}
				 });
			   	 /*  $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/capital/rechargerecord/rechargeConsume.do',  
					  data: JSON.stringify(dt),
					  dataType: "json",
					  contentType:"application/json",
					  success:function(result){  
							if(result.success){
								setTimeout(function () { 
								swal("", result.msg, "success");
								$("#exampleTableEvents").bootstrapTable('refresh');
								$('#userModal').remove();
							   }, 2000);
							}else{
								$('#saveBut').attr("disabled",false); 
								swal("", result.msg, "error");
							}
						},
						error:function(data){
						 	$('#saveBut').attr("disabled",false); 
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						 }
					});*/
        	 }
        });
        
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').remove();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').remove();
       }); 
       
  });   
  var dt;
  function sendCode(obj) {
		countdown=60;
		var obj = $(obj);
		$("#money").rules("add", { required: true,number:true});
		if($("#form-recharge").valid()){
	    		var money = $("#money").val();
	        	var accNo = $("#source option:selected").val();
	        	settime(obj);
	        	$.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/capital/rechargerecord/sendConsumeSMSCode.do',
					  data: {"money":money,"accNo":accNo},
					  dataType: "json",
					  success:function(data){
					 	  if(data.success){
					 	  	  dt = data.data;
						 	  $('#saveBut').attr("disabled",false);
					 	  }else{
		  					  swal("", data.msg, "error");
					 	  }
					  },
					  error:function(data){
						  $("#alert").remove();
				          var data = $.parseJSON(data.responseText);
				          swal("", data.msg, "error");
					  }
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
		    settime(obj) 
		 },1000);
	}
  </script>   
