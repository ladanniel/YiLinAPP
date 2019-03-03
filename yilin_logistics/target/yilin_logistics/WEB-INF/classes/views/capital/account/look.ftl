<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">查看充值记录详情</h6></br>
                    <div class="modal-body"> 
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">账号：</label>
                                <div class="col-sm-8">${vo.account.account}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">手机：</label>
                                <div class="col-sm-8">${vo.account.phone}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">姓名：</label>
                                <div class="col-sm-8">${vo.account.name}</p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: right;">总金额：</label>
                                <div class="col-sm-8">${vo.total}元</p></div>
                            </div>
                            <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">可用余额：</label>
						         <div class="col-sm-8">${vo.avaiable}元</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">冻结资金：</label>
						         <div class="col-sm-8">${vo.frozen}元</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">总充值：</label>
						         <div class="col-sm-8">${vo.totalrecharge}元</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">总提现：</label>
						         <div class="col-sm-8">${vo.totalcash}元</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">创建日期：</label>
						         <div class="col-sm-8">${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">状态：</label>
						         <div class="col-sm-8"><#if vo.account.capitalStatus == 'close'>冻结<#elseif vo.account.capitalStatus == 'open'>正常</#if></p></div>
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
