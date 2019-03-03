<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">添加系统参数</h1>
                    <form class="form-horizontal m-t" id="addInfo">
                    <div class="modal-body"> 
                            <div class="form-group">
		                                    <label class="col-sm-5 control-label">参数名称：</label>
		                                    <div class="col-sm-6">
		                                    <select data-placeholder="选择参数类型" id="companyType" name="key" class="chosen-select form-control" >
			                                    <option value="consignor" hassubinfo="true">货主</option>
			                                    <option value="driver" hassubinfo="true">司机</option>
			                                    <option value="withdrawal" hassubinfo="true">提现有效期</option>
			                                </select>
		                                </div> 
		                    </div>
		                    <div class="form-group">
                                <label class="col-sm-5 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>参数值：</label>
                                <div class="col-sm-6">
                                    <input id="name" name="value" type="number"  class="form-control"  required="" aria-required="true" maxlenth="9">
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-5 control-label"><span>&nbsp;&nbsp;</span>参数描述：</label>
                                <div class="col-sm-6">
                                	<textarea class="form-control" rows="2" name="description"></textarea>
                                </div>
                            </div> 
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                        <button type="submit" class="btn btn-primary" id="saveBut">保存</button>
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
}),
 $(function(){   
        $('#saveBut').click(function(){
        	var e = "<i class='fa fa-times-circle'></i> ";
        	 $("#addInfo").validate({
        	 	rules: {
		        	value: {
		                required: !0,
		            }
		        },
		        messages: {
		            name: {
		                required: e + "请输入参数值。",
		            }
		        },
			 	submitHandler: function(form){  
			   	  $('#saveBut').attr("disabled",true); 
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/parameter/save.do',  
					  data: $('#addInfo').serialize(),
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
