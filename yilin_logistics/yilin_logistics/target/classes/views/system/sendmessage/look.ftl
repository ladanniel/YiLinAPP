<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width: 70%;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">查看短信接口详情</h6></br>
                    <div class="modal-body"> 
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">接口名称：</label>
                                <div class="col-sm-8">${vo.name}</p></div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">发送短信URL：</label>
                                <div class="col-sm-8">${vo.sendUrl}</p></div>
                            </div>
                            <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">发送短信成功表达式：</label>
						         <div class="col-sm-8">${vo.sendSuccess}</p></div>
						    </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">查询余额URL：</label>
                                <div class="col-sm-8">${vo.queryBalanceUrl}</p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: right;">查询余额成功表达式：</label>
                                <div class="col-sm-8">${vo.queryBalSuccess}</p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: right;">查询余额接收参数key：</label>
                                <div class="col-sm-8">${vo.returnBluKey}</p></div>
                            </div>
                            <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">短信提供商：</label>
						         <div class="col-sm-8">${vo.ownedBusiness}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">剩余短信数：</label>
						         <div class="col-sm-8">${bal}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">创建日期：</label>
						         <div class="col-sm-8">${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">返回结果数据类型：</label>
						         <div class="col-sm-8">${vo.type}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">启用状态：</label>
						         <div class="col-sm-8"><#if vo.status == "enabled" >启用<#else>禁用</#if></div>
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
