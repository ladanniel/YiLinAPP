<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">请假条填写</h1>
                    <form class="form-horizontal m-t" id="addUserInfo">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>请假天数：</label>
                                <div class="col-sm-8">
                                    <input id="account"name="account" minlength="2" type="text"  class="form-control"  required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>请假时间：</label>
                                <div class="col-sm-8" id="data_5">
                                   	<div class="input-daterange input-group" id="datepicker">
		                                <input type="text" class="input-sm form-control " id="start" name="start" value="2014-11-11">
		                                <span class="input-group-addon">到</span>
		                                <input type="text" class="input-sm form-control"  id="end" name="end" value="2014-11-17">
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
});
 $(function(){   
 		 $("#data_5 .input-daterange").datepicker({
	        keyboardNavigation: !1,
	        forceParse: !1,
	        autoclose: !0
	     }); 
 		$("#roleSelect").chosen(); 
     //添加用户信息#commentForm
        $('#saveBut').click(function(){
        	 $("#addUserInfo").validate({     
			 submitHandler: function(form) 
			   {  
			   	  $('#saveBut').attr("disabled",true);    
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/user/add.do',  
					  data: $('#addUserInfo').serialize(),
					  dataType: "json",
						 success:function(result){  
							if(result.success == true){
								$('#userModal').remove();
								swal("", result.msg, "success");
						    	$("#exampleTableEvents").bootstrapTable('refresh');
							}else{
								$('#saveBut').attr("disabled",false);    
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
