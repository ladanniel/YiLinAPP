<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width: 70%;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">查看短信详情</h6></br>
                    <div class="modal-body"> 
                            <div class="form-group" >
                                <label class="col-sm-3 control-label" style="text-align: right;">接口名称：</label>
                                <div class="col-sm-8">${vo.sendMessage.name}</p></div>
                            </div>
                            <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">短信提供商：</label>
						         <div class="col-sm-8">${vo.sendMessage.ownedBusiness}</p></div>
						    </div>
						     <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">接收手机号：</label>
						         <div class="col-sm-8">${vo.phone}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">接收内容：</label>
						         <div class="col-sm-8">${vo.content}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">短信切入点：</label>
						         <div class="col-sm-8">${vo.sendPoint}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">创建日期：</label>
						         <div class="col-sm-8">${vo.create_time?string("yyyy-MM-dd HH:mm:ss")}</p></div>
						    </div>
						    <div class="form-group">
						         <label class="col-sm-3 control-label" style="text-align: right;">启用状态：</label>
						         <div class="col-sm-8"><#if vo.status == "success" >成功<#else>失败</#if></div>
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
