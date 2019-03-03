<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">查看提现申请详情</h6></br>
                    <div class="modal-body"> 
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">提现编号：</label>
                                <div class="col-sm-8">${cash.tradeNo}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">提现用户账号：</label>
                                <div class="col-sm-8">${cash.account.account}</p></div>
                            </div>
                            
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">提现用户手机：</label>
                                <div class="col-sm-8">${cash.account.phone}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">提现用户姓名：</label>
                                <div class="col-sm-8">${cash.account.name}</p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: right;">提现银行：</label>
                                <div class="col-sm-8">${cash.bankname}</p></div>
                            </div>
                            <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">银行卡号：</label>
						         <div class="col-sm-8">${cash.bankcard}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">开户行名称：</label>
						         <div class="col-sm-8">${cash.openbank}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">提现金额：</label>
						         <div class="col-sm-8">${cash.money}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">实际到账金额：</label>
						         <div class="col-sm-8">${cash.actualMoney}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">提现申请时间：</label>
						         <div class="col-sm-8">${cash.create_time?string("yyyy-MM-dd HH:mm:ss")}</p></div>
						    </div>
						     <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">审核时间：</label>
						         <div class="col-sm-8"><#if cash.verifytime == null || cash.verifytime == "">无<#else>${cash.verifytime?string("yyyy-MM-dd HH:mm:ss")}</#if></p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">审核人：</label>
						         <div class="col-sm-8"><#if cash.verifytPeopson == null || cash.verifytPeopson == "">无<#else>*${cash.verifytPeopson?substring(1,cash.verifytPeopson?length - 1)}</#if></p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">状态：</label>
						         <div class="col-sm-8"><#if cash.status == 'failed'>未通过<#elseif cash.status == 'success'>通过<#elseif cash.status == 'lock'>锁定<#else>等待审核</#if></p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">理由：</label>
						         <div class="col-sm-8"><#if null == cash.remark || "" == cash.remark>无<#else>${cash.remark}</#if></p></div>
						    </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                    </div>
                </div>
            </div>
        </div> 
    </div>
 <script>
       $('#close-but').click(function(){
       		$('#userModal').remove();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').remove();
       }); 
  </script>   
