<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">查看转账记录详情</h6></br>
                    <div class="modal-body"> 
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">转账流水号：</label>
                                <div class="col-sm-8">${vo.tradeNo}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">对方账号：</label>
                                <div class="col-sm-8">${vo.toAccount.account}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">对方手机：</label>
                                <div class="col-sm-8">${vo.toAccount.phone}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">对方姓名：</label>
                                <div class="col-sm-8">${vo.toAccount.name}</p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: right;">转账类型：</label>
                                <div class="col-sm-8">${vo.transferType.name}</p></div>
                            </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">转账金额：</label>
						         <div class="col-sm-8"><#if vo.money gt 0>转入${vo.money}<#else>转出${(0 - vo.money)}</#if>元</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">转账日期：</label>
						         <div class="col-sm-8">${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">状态：</label>
						         <div class="col-sm-8"><#if vo.status == 'failed'>失败<#elseif vo.status == 'success'>成功<#else>等待处理</#if></p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">备注：</label>
						         <div class="col-sm-8"><#if vo.remark == null || "" == vo.remark>无<#else>${vo.remark}</#if></p></div>
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
