<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title">充值详情</h6></br>
                    
                    <form class="form-horizontal m-t">
	                    <div class="modal-body"> 
	                    	<div class="form-group">
	                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>充值金额：</label>
	                                <div class="col-sm-8">
	                                    <input id="money" name="money" type="number"  class="form-control"  required="" aria-required="true" maxlength="11">
	                                </div>
	                        </div>
							<@tradeSequence recharge = "1">
									<div id="oldCard">
										<div class="form-group">
											<label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>选择付款方式：</label>
											<div class="col-sm-8">
												<select class="form-control" name="source" id="source">
												<#if tradeSequences?size == 0>
													<option value="no">未绑定银行卡，请选择新卡支付。</option>
												<#else>
													<#list tradeSequences as vo >
														<#assign arr = vo.sourceAccount?split("-")>
														<#if vo.sourcType = "储蓄卡" || vo.sourcType = "信用卡">
															<option value="${vo.sourceAccount+"-"+vo.sourcType}">${arr[0] + "尾号" + arr[1]?substring(arr[1]?length - 4,arr[1]?length)}</option>
														<#else>
															<option value="${avo.sourceAccount +"-"+vo.sourcType}">${arr[0] + arr[1]?substring(0,2) +"******" + arr[1]?substring(arr[1]?length - 3,arr[1]?length)}</option>
														</#if>
													</#list>
												</#if>
												</select>
											</div>
							       		</div>
							       		<div class="form-group">
				                                <div class="col-sm-6 col-sm-offset-4">
				                                    <span class="btn btn-primary btn-block m-t" id="butNewCard"><i class="fa fa-plus"></i>&nbsp;&nbsp;使用新卡充值</span>
				                                </div>
				                        </div>
				                    </div>
			                        <div id="newCard" hidden="true">
			                        	<div class="form-group">
			                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>银行卡号：</label>
			                                <input type="hidden" class="form-control" id="bankCard" name="bankCard" maxlength="32"/>
			                                <input type="hidden" class="form-control" id="bankName" name="bankName" maxlength="32"/>
			                                <input type="hidden" class="form-control" id="cardType" name="cardType" maxlength="32"/>
			                                <div class="col-sm-8 card-number-content">
			                                   <input type="text" id="card-number" class="form-control" required="" aria-required="true" name="bankCard" maxlength="32"/>
			                                </div>
			                           </div>
			                            <div class="form-group row">
						                                <label class="col-sm-3 control-label" style="text-align: right;"><span style="color: red;">*&nbsp;&nbsp;</span>支付密码：</label>
						                                <div class="col-sm-8">
						                                	<input id="payPassword" name="payPassword" type="password"  class="form-control"  required="" aria-required="true" style="width:260px" placeholder="支付密码" maxlength="18" disabled>
						                                </div>
						    			</div>
			                        </div>
							</@tradeSequence>
	                    </div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
	                        <button type="button"  class="btn btn-primary" id="saveBut">确定</button>
	                    </div>
                 	</form>
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
 		$("#card-number").bankInput();
 		var e = "<i class='fa fa-times-circle'></i> ";
        	 $("#form-recharge").validate({
        	 	rules: {
		        	money: {
		                required: !0,
		                number: !0,
		            },
		            bankCard: {
		                required: !0,
		                rangelength:[16,23],
		            },
		            payPassword: {
		                required: !0,
		                rangelength: [6, 6],
		                isDigits: !0
		            }
		        },
		        messages: {
		            money: {
		                required: e + "请输入充值金额。",
		                number: e + "请输入正确的充值金额。",
		            },
		            bankCard: {
		               required: e + "请输入银行卡号。",
		               rangelength: e + "请输入正确的银行卡",
		            },
		            payPassword: {
		                required: e + "请输入您的密码",
		                rangelength: e + "支付密码必须为6位数字。",
		            }
		        }
		});
 		$("#butNewCard").click(function(){
 			$("#oldCard").hide();
 			$("#newCard").show();
 		});
 		
 		
        $('#saveBut').click(function(){
        		if($("#form-recharge").valid()){
			   	  $('#saveBut').attr("disabled",true); 
			   	   $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/capital/rechargerecord/add.do',  
					  data: $('#form-recharge').serialize(),
					  dataType: "json",
					  success:function(result){  
					  		console.log(result);
							if(result.success == true){
								window.open("www.baidu.com","_blank");
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
       
       $('#card-number').blur(function(){
		var html = '<span id="alert">正在检测....</span>' 
		$("#card-number").after(html);
		var number = $("#card-number").val();
		$.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/capital/bankcard/verify.do',  
					  data: {"number":number},
					  dataType: "json",
						 success:function(data){  
						 	$("#payPassword").attr("disabled",false);
							$("#alert").remove();
							$(".card-number-content").empty();
							var charname = new Array();
							var number = data.bankCard;
							var bankName = data.cnName;
							var cardType = data.cardType;
							$("#bankCard").val(number);
							$("#bankName").val(bankName);
							$("#cardType").val(cardType);
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
							var result = '<div class="form-control-static">' + newNumber + '('+ data.cnName + ')</div>';
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
