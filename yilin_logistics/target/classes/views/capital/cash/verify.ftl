<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">提现记录详情</h6></br>
                    <form class="form-horizontal m-t" id="form-cash">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label">提现编号：</label>
                                <div class="col-sm-8">
	                                    <p class="form-control-static">${cash.tradeNo}</p>
                                </div>
                            </div>
                             <div class="form-group" >
                                <label class="col-sm-3 control-label">手机账号：</label>
                                <div class="col-sm-8"><p class="form-control-static">${cash.account.phone}</p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">姓名：</label>
                                <div class="col-sm-8">
	                                    <p class="form-control-static">${cash.account.name}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">提现银行：</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static">${cash.bankname}</p>
                                </div>
                            </div>
                            <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">银行卡号：</label>
						         <div class="col-sm-8"><p class="form-control-static">${cash.bankcard}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">开户行名称：</label>
						         <div class="col-sm-8"><p class="form-control-static">${cash.openbank}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">提现金额：</label>
						         <div class="col-sm-8"><p class="form-control-static">${cash.money}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">实际到账金额：</label>
						         <div class="col-sm-8"><p class="form-control-static">${cash.actualMoney}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">提现申请时间：</label>
						         <div class="col-sm-8"><p class="form-control-static">${cash.create_time?string("yyyy-MM-dd HH:mm:ss")}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">状态：</label>
						         <div class="col-sm-8"><p class="form-control-static"><#if cash.status == 'failed'>未通过<#elseif cash.status == 'success'>通过<#elseif cash.status == 'lock'>锁定<#else>等待审核</#if></p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">理由：</label>
						         <div class="col-sm-8">
						         	<textarea class="form-control" rows="3" name="remark" id="remark"></textarea>
						         </div>
						    </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                        <button type="button" class="btn btn-primary" id="success-but">通过</button>
                        <button type="button" class="btn btn-primary" id="failed-but">不通过</button>
                    </div>
                 	</form> 
                </div>
            </div>
        </div> 
    </div>
 <script>
 $(function(){ 
        $('#success-but').click(function(){
			   	   $('#success-but').attr("disabled",true); 
			   	   $.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/capital/cashapplication/verify.do',  
					  data: {"id":"${cash.id}","remark":$("#remark").val(),"status":"success"},
					  dataType: "json",
					  success:function(result){  
							$('#userModal').remove();
							swal("", result.msg, "success");
						    $("#exampleTableEvents").bootstrapTable('refresh');
						},
						error:function(data){
						 	$('#success-but').attr("disabled",false); 
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						 }
					});
        });
        $('#failed-but').click(function(){
			   	   $('#failed-but').attr("disabled",true);
			   	   $.ajax({
					  type: 'POST',
					  url:'${WEB_PATH }/capital/cashapplication/verify.do',  
					  data: {"id":"${cash.id}","remark":$("#remark").val(),"status":"failed"},
					  dataType: "json",
					  success:function(result){  
							$('#userModal').remove();
							swal("", result.msg, "success");
						    $("#exampleTableEvents").bootstrapTable('refresh');
						},
						error:function(data){
						 	$('#success-but').attr("disabled",false); 
						 	var data = $.parseJSON(data.responseText);
					        swal("", data.msg, "error");
						 }
					});
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
