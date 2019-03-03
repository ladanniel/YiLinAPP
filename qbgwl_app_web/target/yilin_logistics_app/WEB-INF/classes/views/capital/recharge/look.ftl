<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">查看充值记录详情</h6></br>
                    <div class="modal-body"> 
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">充值流水号：</label>
                                <div class="col-sm-8">${vo.tradeNo}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">充值用户账号：</label>
                                <div class="col-sm-8">${vo.account.account}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">充值用户手机：</label>
                                <div class="col-sm-8">${vo.account.phone}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">充值用户姓名：</label>
                                <div class="col-sm-8">${vo.account.name}</p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: right;">充值类型：</label>
                                <div class="col-sm-8">${vo.sourceType}</p></div>
                            </div>
                            <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">充值来源名称：</label>
						         <div class="col-sm-8">${vo.tradeName}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">充值来源账号：</label>
						         <div class="col-sm-8">${vo.tradeAccount}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">充值金额：</label>
						         <div class="col-sm-8">${vo.money}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">充值日期：</label>
						         <div class="col-sm-8">${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">状态：</label>
						         <div class="col-sm-8"><#if vo.status == 'failed'>失败<#elseif vo.status == 'success'>成功<#else>等待处理</#if></p></div>
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
