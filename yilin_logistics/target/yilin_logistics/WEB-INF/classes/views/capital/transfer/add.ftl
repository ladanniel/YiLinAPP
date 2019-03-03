<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                 	<div class="row">
                 		<div class="col-sm-12">
			                <div class="ibox float-e-margins">
			                    <h2>转账</h2>
			                    <div class="ibox-content">
			                    	<form id="form-transfer" class="wizard-big">
			                                <div class="row" style="margin-top:30px;">
						                            <div class="form-group row">
						                                <label class="col-sm-4 control-label form-control-static" style="text-align: right;"><span style="color: red;">*&nbsp;&nbsp;</span>选择付款方式：</label>
						                                <div class="col-sm-8">
						                                	<select class="form-control" name="source" id="source" style="width:260px">
																<@tradeSequence trade = "1">
																<#list tradeSequences as vo >
																<#assign arr = vo.sourceAccount?split("-")>
																	<#if vo.sourcType = "储蓄卡" || vo.sourcType = "信用卡">
																		<option value="${vo.sourceAccount+"-"+vo.sourcType}">${arr[0] + "尾号" + arr[1]?substring(arr[1]?length - 4,arr[1]?length)}</option>
																	<#else>
																		<option value="${vo.sourceAccount +"-"+vo.sourcType}"><#if arr[0] == "易林帐户">可用余额:¥<#if capitalAccount.avaiable == 0>0<#elseif capitalAccount.avaiable gt 0 && capitalAccount.avaiable lt 1>${capitalAccount.avaiable?string('0.00')}<#else>${capitalAccount.avaiable?string(',###.00')}</#if><#else>${arr[0] + arr[1]?substring(0,2) +"******" + arr[1]?substring(arr[1]?length - 3,arr[1]?length)}</#if></option>
																	</#if>
																</#list>
																</@tradeSequence>
														    </select>
						                                </div>
						                            </div>
						                            <div class="form-group row">
						                                <label class="col-sm-4 control-label form-control-static" style="text-align: right;">选择类型：</label>
						                                <div class="col-sm-8">
						                                	<select class="form-control" name="transferType.id" id="transferType" style="width:260px">
																<@transferType >
																	<#if transferTypeViews?size == 0>
																		<option value="">未设置转账类型</option>
																	</#if>
											                    	 <#list transferTypeViews as vo >
										                              	 <option value="${vo.id}">${vo.name}</option> 
										                             </#list>
															    </@transferType>
														    </select>
						                                </div>
						                            </div>
			                                    	<div class="form-group row">
						                                <label class="col-sm-4 control-label form-control-static" style="text-align: right;"><span style="color: red;">*&nbsp;&nbsp;</span>对方帐号：</label>
						                                <div class="col-sm-8" id="account-content">
						                                	<input id="accountId" name="accountId"  class="form-control"  required="" aria-required="true" style="width:260px" placeholder="对方账号／手机号" maxlength="64">
						                                </div>
						                            </div>
						                            <div class="form-group row">
						                                <label class="col-sm-4 control-label form-control-static" style="text-align: right;"><span style="color: red;">*&nbsp;&nbsp;</span>转账金额：</label>
						                                <div class="col-sm-8">
						                                	<input id="money" name="money" type="number"  class="form-control"  required="" aria-required="true" style="width:260px" placeholder="转账金额" maxlength="10">
						                                </div>
						                            </div>
						                            <div class="form-group row">
						                                <label class="col-sm-4 control-label form-control-static" style="text-align: right;"><span style="color: red;">*&nbsp;&nbsp;</span>支付密码：</label>
						                                <div class="col-sm-8">
						                                	<input id="payPassword" name="payPassword" type="password"  class="form-control"  required="" aria-required="true" style="width:260px" placeholder="支付密码" maxlength="18">
						                                </div>
						                            </div>
						                            <div class="form-group row">
						                                <label class="col-sm-4 control-label form-control-static" style="text-align: right;">备注：</label>
						                                <div class="col-sm-8">
						                                	<input id="remark" name＝"remark" type="text"   class="form-control" style="width:260px" placeholder="备注100字以内。" maxlength="100">
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
        	 $("#form-transfer").validate({
        	 	rules: {
		        	accountId: {
		                required: !0,
		                remote:{
		              	  type:"POST",
		                  url:"${WEB_PATH }/capital/transfer/checkToAccount.do",
		              }
		            },
		            payPassword: {
		                required: !0,
		              },
		            money: {
		                required: !0,
		                number: !0,
		              }
		        },
		        messages: {
		            accountId: {
		                required: e + "请输入收款人帐户或手机号。",
		                remote: e + "收款账户不存在或未实名认证或为自己账号，不能转账。"
		            },
		            payPassword: {
		                required: e + "请支付密码。",
		            },
		            money: {
		                required: e + "请输入充值金额。",
		                number: e + "请输入正确的充值金额。",
		            }
		        }
		});
		
        $('#saveBut').click(function(){
			 	if($("#form-transfer").valid()){   
			   	  $('#saveBut').attr("disabled",true); 
			   	   $.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/capital/transfer/add.do',  
					  data: {"remark":$("#remark").val(),"accountId":$("#accountId").val(),"payPassword":$("#payPassword").val(),"money":$("#money").val(),"transferType.id":$("#transferType option:selected").val(),"source":$("#source option:selected").val()},
					  dataType: "json",
					  success:function(result){  
							if(result.success == true){
								var html = $("#source option:selected").html();
								if(html.substring(0,4) == "可用余额"){
									var avaiable = $("#avaiable").html();
									var money = $("#money").val();
									var total = $("#total").html();
									$("#avaiable").html(Number(avaiable) - Number(money));
									$("#total").html(Number(total) - Number(money));
								}
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
       
       $("#accountId").blur(function(){
       		$.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/capital/transfer/getAccount.do',  
					  data: {"account":$("#accountId").val()},
					  dataType: "json",
					  success:function(result){ 
					  		console.log(result);
					  		$("#account-content").empty();
					  		var html = '<div class="form-control-static">' + result.msg.name +'(' + result.msg.account + ')</div>';
							html += '<input type="hidden" class="form-control" id="accountId" name="accountId" value="'+result.msg.account+'"/>';
							$("#account-content").append(html);
					  },
					 error:function(data){
						 	$('#saveBut').attr("disabled",false); 
					  }
			});
       });
  });   
  </script>   
