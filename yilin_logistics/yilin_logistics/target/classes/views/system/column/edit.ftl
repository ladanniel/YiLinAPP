<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">编辑列</h1>
                    <form class="form-horizontal m-t" id="addUserInfo">
                    <input name="id" type="hidden"  class="form-control" value="${table.id}"  required="" aria-required="true">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>表英文名：</label>
                                <div class="col-sm-8">
                                    <input id="text"name="s_table_name" minlength="2" type="text"  class="form-control" value="${table.s_table_name}" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>表中文名：</label>
                                <div class="col-sm-8">
                                    <input id="text"name="name" minlength="2" type="text"  class="form-control" value="${table.s_table_comment}" required="" aria-required="true">
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
     //添加角色信息#commentForm
        $('#saveBut').click(function(){
        	 $("#addUserInfo").validate({     
			 submitHandler: function(form) 
			   {  
			   	  $('#saveBut').attr("disabled",true);    
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/column/edit.do',  
					  data: $('#addUserInfo').serialize(),
					  dataType: "json",
						 success:function(result){  
						 	$('#userModal').remove();
							if(result.success == true){
								swal({
							        title: "操作成功",
							        text: "添加角色信息成功。"
						    	})
						    	location.reload();
							}else{
								swal({
							        title: "操作失败",
							        text: "系统异常。"
						    	})
							}
						}
					});
			   }  
			 });
   			 var e = "<i class='fa fa-times-circle'></i> ";
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
