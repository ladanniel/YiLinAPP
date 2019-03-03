<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">新增商户类型</h1>
                    <form class="form-horizontal m-t" id="addCompanyTypeInfo">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-5 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>类型名称：</label>
                                <div class="col-sm-6">
                                    <input id="name" name="name" minlength="2" type="text"  class="form-control"  required="" aria-required="true">
                                </div>
                                
                            </div> 
                            <div class="form-group">
                               <label class="col-sm-5 control-label">是否启用认证：</label>
                            	<div class="col-sm-6">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio" id="is_aut1" value="true" name="is_aut" checked="">
	                                   <label for="is_aut1"> 是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="is_aut2" value="false" name="is_aut">
	                                    <label for="is_aut2"> 否 </label>
	                                </div>
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-5 control-label">是否启用身份证认证：</label>
                            	<div class="col-sm-6">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio" id="idcard1" value="true" name="idcard" checked="">
	                                   <label for="idcard1"> 是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="idcard2" value="false" name="idcard">
	                                    <label for="idcard2"> 否 </label>
	                                </div>
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-5 control-label">是否启用驾驶证认证：</label>
                            	<div class="col-sm-6">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio" id="driving_license1" value="true" name="driving_license" checked="">
	                                   <label for="driving_license1"> 是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="driving_license2" value="false" name="driving_license">
	                                    <label for="driving_license2"> 否 </label>
	                                </div>
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-5 control-label">是否启用营业执照认证：</label>
                            	<div class="col-sm-6">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio" id="business_license1" value="true" name="business_license" checked="">
	                                   <label for="business_license1"> 是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="business_license2" value="false" name="business_license">
	                                    <label for="business_license2"> 否 </label>
	                                </div>
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-5 control-label">是否在注册页面显示：</label>
                            	<div class="col-sm-6">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio" id="is_register1" value="true" name="is_register" checked="">
	                                   <label for="is_register1"> 是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="is_register2" value="false" name="is_register">
	                                    <label for="is_register2"> 否 </label>
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
        $('#saveBut').click(function(){
        	 $("#addCompanyTypeInfo").validate({     
			 submitHandler: function(form) 
			   {  
			   	  $('#saveBut').attr("disabled",true);    
				  $.yilinAjax({
				   	  	type:'POST',
				   	  	url:'${WEB_PATH }/system/companytype/save.do',
				   	  	data:$('#addCompanyTypeInfo').serialize(),
	            		errorcallback:null,
	            		btn:$('#saveBut'),
	            		successcallback:success
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
       function success(result){  
			if(result.success == true){
				$('#userModal').remove();
		    	swal("操作成功",  result.msg, "success"); 
		    	$("#exampleTableEvents").bootstrapTable('refresh');
			}else{
				swal("操作失败", result.msg, "error");
				$('#saveBut').attr("disabled",false);  
			}
	   }
  });   
  </script>   
