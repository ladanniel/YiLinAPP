<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">新增上下班时间</h1>
                    <form class="form-horizontal m-t" id="addWorkDateInfo">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>上班时间：</label>
                                <div class="col-sm-8">
                                    <div class="input-group work_date" data-autoclose="true">
			                            <input type="text" class="form-control" name="go_to_work_date" required="" aria-required="true" value="08:30">
			                            <span class="input-group-addon">
			                                    <span class="fa fa-clock-o"></span>
			                            </span>
			                        </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>下班时间：</label>
                                <div class="col-sm-8">
                                    <div class="input-group work_date"  data-autoclose="true">
			                            <input type="text" class="form-control" name="go_off_work_date" required="" aria-required="true" value="17:30">
			                            <span class="input-group-addon">
			                                    <span class="fa fa-clock-o"></span>
			                            </span>
			                        </div>
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
 		$(".work_date").clockpicker();
     //添加角色信息#commentForm
        $('#saveBut').click(function(){
        	 $("#addWorkDateInfo").validate({     
			 submitHandler: function(form) 
			   {  
			   	  $('#saveBut').attr("disabled",true);    
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH}/system/positions/saveWorkDate.do',  
					  data: $('#addWorkDateInfo').serialize(),
					  dataType: "json",
						 success:function(result){  
						 	$('#userModal').remove();
							if(result.success == true){
								swal({
							        title: "操作成功",
							        text: result.msg
						    	})
						    	$("#workDateTableEvents").bootstrapTable('refresh');
							}else{
								swal({
							        title: "操作失败",
							        text: result.msg
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
