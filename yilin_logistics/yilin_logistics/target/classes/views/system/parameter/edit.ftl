<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">编辑系统参数</h1>
                    <form class="form-horizontal m-t" id="editInfo">
                    <input name="id" type="hidden"  class="form-control" value="${vo.id}"  required="" aria-required="true">
                    <div class="modal-body"> 
                            
		                                <div class="form-group">
		                                    <label class="col-sm-5 control-label">参数名称：</label>
		                                    <input type="text" name="key" value="${vo.key}" hidden="true"/>
		                                    <input type="text" name="create_time" value="${vo.create_time}" hidden="true"/>
		                                    <div class="col-sm-6">
		                                    	 <p class="form-control-static"><#if vo.key == "consignor">货主<#elseif vo.key == "driver">司机<#elseif vo.key == "withdrawal">提现有效期</#if></p>
		                                    </div>
		                                </div> 
		                                <div class="form-group">
			                                <label class="col-sm-5 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>参数值：</label>
			                                <div class="col-sm-6">
			                                    <input id="name" name="value" type="number"  class="form-control" value="${vo.value}"  required="" aria-required="true" maxlenth="9">
			                                </div>
			                            </div> 
			                            <div class="form-group">
			                                <label class="col-sm-5 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>参数描述：</label>
			                                <div class="col-sm-6">
			                                	<textarea class="form-control" rows="2" name="description">${vo.description}</textarea>
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
        	 $("#editInfo").validate({
        	 	rules: {
		        	name: {
		                required: !0,
		            }
		        },
		        messages: {
		            name: {
		                required: e + "参数值。",
		            }
		        },
			 	submitHandler: function(form){  
			   	  $('#saveBut').attr("disabled",true); 
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/parameter/edit.do',  
					  data: $('#editInfo').serialize(),
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
