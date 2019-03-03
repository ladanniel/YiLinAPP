<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title">提现详情</h6></br>
                    <form class="form-horizontal m-t" id="form-cash">
                    <div class="modal-body"> 
                    		<div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>可提现余额：</label>
                                <div class="col-sm-8">
                                	<p class="form-control-static" style="color:red">
                                		¥&nbsp;<#if capitalAccount.avaiable == 0>0<#else>${capitalAccount.avaiable?string(',###.00')}</#if>
                                	</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>选择银行卡：</label>
                                <div class="col-sm-8">
                                    <select class="form-control" name="bankcard" id="bankcard">
                                    <@tradeSequence cash = "1">
                                    		<#if tradeSequences?size == 0>
                                    			<option value="no">还未绑定银行卡</option>
                                    		</#if>
											<#list tradeSequences as vo >
													<#assign arr = vo.sourceAccount?split("-")>
													<option value="${vo.sourceId}">${arr[0] + "尾号" + arr[1]?substring(arr[1]?length - 4,arr[1]?length)}</option>
											</#list>
									</@tradeSequence>
                                 </select> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>提现金额：</label>
                                <div class="col-sm-8">
                                    <input id="money" name="money" type="number"  class="form-control"  required="" aria-required="true" maxlength="11">
                                </div>
                            </div>
                            <div class="form-group row">
						                                <label class="col-sm-3 control-label" style="text-align: right;"><span style="color: red;">*&nbsp;&nbsp;</span>支付密码：</label>
						                                <div class="col-sm-8">
						                                	<input id="payPassword" name="payPassword" type="password"  class="form-control"  required="" aria-required="true" style="width:260px" placeholder="支付密码" maxlength="18">
						                                </div>
						    </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                        <button type="submit" class="btn btn-primary" id="saveBut">确定</button>
                    </div>
                 	</form> 
                </div>
            </div>
        </div> 
    </div>
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
        	 $("#form-cash").validate({
        	 	rules: {
		        	money: {
		                required: !0,
		                number: !0,
		            },
		            bankcard: {
		            	required: !0,
		            },
		            payPassword: {
		                required: !0,
		                rangelength: [6, 6],
		                isDigits: !0
		            }
		        },
		        messages: {
		            money: {
		                required: e + "请输入提现金额。",
		                number: e + "请输入正确的充值金额。",
		            },
		            bankcard: {
		            	 required: e + "请绑定银行卡后在申请提现。",
		            }, 
		            payPassword: {
		                required: e + "请输入您的密码",
		                rangelength: e + "支付密码必须为6位数字。",
		            }
		        }
		});
        $('#saveBut').click(function(){
			 	if($("#form-cash").valid()){ 
			   	  $('#saveBut').attr("disabled",true); 
			   	   $.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/capital/cashapplication/add.do',  
					  data: $('#form-cash').serialize(),
					  dataType: "json",
					  success:function(result){  
							if(result.success == true){
								var avaiable = $("#avaiable").html();
								var money = $("#money").val();
								var frozen = $("#frozen").html();
								$("#avaiable").html(Number(avaiable) - Number(money));
								$("#frozen").html(Number(frozen) + Number(money));
								$('#userModal').remove();
								swal("", result.msg, "success");
							    $("#exampleTableEvents").bootstrapTable('refresh');
							}
						},
						error:function(data){
						 	$('#saveBut').attr("disabled",false); 
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
  });   
  </script>   
