<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">编辑转账类型信息</h1>
                    <form class="form-horizontal m-t" id="editInfo">
                    <input name="id" type="hidden"  class="form-control" value="${transferType.id}"  required="" aria-required="true">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-5 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>类型名称：</label>
                                <div class="col-sm-6">
                                    <input id="name" name="name" minlength="2" type="text"  class="form-control"  required="" value="${transferType.name}" aria-required="true" maxlenth="16">
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
		                rangelength: [1, 16],
		                remote:{
		                	  type:"POST",
		                      url:"${WEB_PATH }/system/transfertype/checkName.do",
		                }
		            }
		        },
		        messages: {
		            name: {
		                required: e + "请输入名称。",
		                rangelength: e + "不能超过16个字符。",
		                remote: e + "已存在该类型。"
		            }
		        },
			 	submitHandler: function(form){  
			   	  $('#saveBut').attr("disabled",true); 
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/transfertype/edit.do',  
					  data: $('#editInfo').serialize(),
					  dataType: "json",
						 success:function(result){  
							if(result.success == true){
								$('#userModal').remove();
								swal("", result.msg, "success");
						    	$("#exampleTableEvents").bootstrapTable('refresh');
							}
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
       function success(result){  
			if(result.success == true){
				$('#userModal').remove();
		    	swal("操作成功",result.msg, "success");
		    	$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				swal("操作失败", result.msg, "error");
				$('#saveBut').attr("disabled",false);  
			}
	   } 
  });   
  </script>   
