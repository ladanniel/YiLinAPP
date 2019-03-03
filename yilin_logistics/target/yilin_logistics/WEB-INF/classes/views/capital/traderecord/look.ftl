<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">查看充值记录详情</h6></br>
                    <div class="modal-body"> 
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">用户名：</label>
                                <div class="col-sm-8">${vo.account.account}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">绑定手机：</label>
                                <div class="col-sm-8">${vo.account.phone}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">姓名：</label>
                                <div class="col-sm-8">${vo.account.name}</p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: right;">交易方式类型：</label>
                                <div class="col-sm-8">${vo.sourceType}</p></div>
                            </div>
                            <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">交易来源名称：</label>
						         <div class="col-sm-8">${vo.tradeName}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">交易来源账号：</label>
						         <div class="col-sm-8">${vo.tradeAccount}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">交易金额：</label>
						         <div class="col-sm-8">${vo.money}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">交易日期：</label>
						         <div class="col-sm-8">${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">类型：</label>
						         <div class="col-sm-8"><#if vo.type == 'recharge'>充值<#elseif vo.type == 'frozen'>冻结<#elseif vo.type == "cash">提现<#elseif vo.type = "transfer">转账<#elseif vo.type="fee">手续费<#elseif vo.type="consume">消费<#elseif vo.type="income">收款<#else>其它</#if></p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">交易信息：</label>
						         <div class="col-sm-8">${vo.remark}</p></div>
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
