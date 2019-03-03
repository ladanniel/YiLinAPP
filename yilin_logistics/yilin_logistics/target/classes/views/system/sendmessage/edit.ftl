<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog"  style="width: 70%;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">编辑短信接口信息</h6></br>
                    
                    <form class="form-horizontal m-t" id="form">
	                    <div class="modal-body"> 
	                    	<div class="form-group">
	                                <label class="col-sm-4 control-label">接口名称：</label>
	                                <div class="col-sm-7">
	                                	<input type="text" value="${vo.name}" name="name" hidden="true"/>
	                                	<input type="text" value="${vo.id}" name="id" hidden="true"/>
	                                    <p class="form-control-static">${vo.name}</p>
	                                </div>
	                        </div>
							<div class="form-group">
	                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>发送短信URL：</label>
	                                <div class="col-sm-7">
	                                    <input id="sendUrl" name="sendUrl" type="text" value="${vo.sendUrl}" class="form-control">
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>发送短信成功表达式：</label>
	                                <div class="col-sm-7">
	                                    <input id="sendSuccess" name="sendSuccess" type="text"  value="${vo.sendSuccess}" class="form-control" placeholder="">
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">查询余额URL：</label>
	                                <div class="col-sm-7">
	                                    <input id="queryBalanceUrl" name="queryBalanceUrl" type="text" value="${vo.queryBalanceUrl}" class="form-control"    >
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">查询余额成功表达式：</label>
	                                <div class="col-sm-7">
	                                    <input id="queryBalSuccess" name="queryBalSuccess" type="text" value="${vo.queryBalSuccess}" class="form-control" placeholder="">
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">接收余额key：</label>
	                                <div class="col-sm-7">
	                                    <input id="returnBluKey" name="returnBluKey" type="text" value="${vo.returnBluKey}" class="form-control">
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">短信提供商：</label>
	                                <div class="col-sm-7">
	                                    <p class="form-control-static">${vo.ownedBusiness}</p>
	                                    <input type="text" value="${vo.ownedBusiness}" name="ownedBusiness" hidden="true"/>
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">返回结果数据类型：</label>
	                                <div class="col-sm-7">
	                                    <p class="form-control-static">${vo.type}</p>
	                                    <input type="text" value="${vo.type}" name="type" hidden="true"/>
	                                    <input type="text" value="${vo.create_time}" name="create_time" hidden="true"/>
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">是否启用：</label>
	                                <div class="col-sm-7">
	                                    <select class="form-control" name="status" id="status" placeholder="选择是否启用">
						                        <option value="enabled" <#if vo.status = "enabled">selected</#if>>启用</option>
									            <option value="notenabled"  <#if vo.status = "notenabled">selected</#if>>禁用</option> 
										</select>
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
        	 $("#form").validate({
        	 	rules: {
		            sendUrl: {
		                required: !0,
		            },
		            sendSuccess: {
		                required: !0,
		            }
		        },
		        messages: {
		            sendUrl: {
		               required: e + "请输入短信发送URL。",
		            },
		            sendSuccess: {
		            	required: e + "请输入成功表达式。",
		            }
		        }
		});
        $('#saveBut').click(function(){
        		if($("#form").valid()){
			   	  $('#saveBut').attr("disabled",true); 
			   	   $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/sendmessage/update.do',  
					  data: $('#form').serialize(),
					  dataType: "json",
					  success:function(result){  
					  		$('#userModal').remove();
							swal("", result.msg, "success");
							$("#exampleTableEvents").bootstrapTable('refresh');
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
      
  });   
  
  </script>   
