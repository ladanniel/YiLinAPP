<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog"  style="width: 70%;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h6 class="modal-title" style="text-align: center;">添加短信接口信息</h6></br>
                    
                    <form class="form-horizontal m-t" id="form">
	                    <div class="modal-body"> 
	                    	<div class="form-group">
	                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>接口名称：</label>
	                                <div class="col-sm-7">
	                                    <input id="name" name="name" type="text"  class="form-control"    >
	                                </div>
	                        </div>
							<div class="form-group">
	                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>发送短信URL：</label>
	                                <div class="col-sm-7">
	                                    <input id="sendUrl" name="sendUrl" type="text"  class="form-control" placeholder="注意：手机和发送内容参数放在url最后面，格式如：&mobile=&content=">
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>发送短信成功表达式：</label>
	                                <div class="col-sm-7">
	                                    <input id="sendSuccess" name="sendSuccess" type="text"  class="form-control" placeholder="格式如：success:true">
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">查询余额URL：</label>
	                                <div class="col-sm-7">
	                                    <input id="queryBalanceUrl" name="queryBalanceUrl" type="text"  class="form-control"    >
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">查询余额成功表达式：</label>
	                                <div class="col-sm-7">
	                                    <input id="queryBalSuccess" name="queryBalSuccess" type="text"  class="form-control" placeholder="格式如：success:true">
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">接收余额key：</label>
	                                <div class="col-sm-7">
	                                    <input id="returnBluKey" name="returnBluKey" type="text"  class="form-control" placeholder="填写的是查询余额返回余额的key值，如{'success':true,’bl‘:'200'}应填写bl">
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>短信提供商：</label>
	                                <div class="col-sm-7">
	                                    <input id="ownedBusiness" name="ownedBusiness" type="text"  class="form-control"    >
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">返回结果数据类型：</label>
	                                <div class="col-sm-7">
	                                    <select class="form-control" name="type" id="type" placeholder="选择数据类型">
						                        <option value="json" selected>JSON</option>
									            <option value="xml">XML</option> 
									            <option value="text">TEXT</option> 
									            <option value="array">ARRAY</option> 
										</select>
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-sm-4 control-label">是否启用：</label>
	                                <div class="col-sm-7">
	                                    <select class="form-control" name="status" id="status" placeholder="选择是否启用">
						                        <option value="enabled" selected>启用</option>
									            <option value="notenabled">禁用</option> 
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
		        	name: {
		                required: !0,
		            },
		            sendUrl: {
		                required: !0,
		            },
		            ownedBusiness: {
		                required: !0,
		            },
		            sendSuccess: {
		            	 required: !0,
		            }
		        },
		        messages: {
		            name: {
		                required: e + "请输入短信接口名称。",
		            },
		            sendUrl: {
		               required: e + "请输入短信发送URL。",
		            },
		            ownedBusiness: {
		            	required: e + "请输入查询余额URL。",
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
					  url: '${WEB_PATH }/system/sendmessage/add.do',  
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
