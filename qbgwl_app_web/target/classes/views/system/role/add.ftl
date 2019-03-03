<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">新增角色</h1>
                    <form class="form-horizontal m-t" id="addRoleInfo">
                    	<div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>角色名称：</label>
                                <div class="col-sm-7">
                                    <input id="text" name="name" minlength="2" type="text"  class="form-control"  required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>商户类型：</label>
                                <div class="col-sm-7">
                                	 <select class="form-control" name="companyType.id">
	                                     <@companyTypeInfo >
					                    	 <#list companyTypeViews as companyType >
				                              	 <option value="${companyType.id}">${companyType.name}</option> 
				                             </#list>
										 </@companyTypeInfo>
									 </select> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>状态：</label>
                                <div class="col-sm-7">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio"  value="true" name="status" id="status1" checked="">
	                                   <label for="status1">启用</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio"  id="status2" value="false" name="status">
	                                    <label for="status2"> 禁用 </label>
	                                </div>
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>是否管理员：</label>
                                <div class="col-sm-7">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio"  value="true" id="is_admin1" name="is_admin" checked="">
	                                   <label for="is_admin1">是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio"  value="false" id="is_admin2" name="is_admin">
	                                    <label for="is_admin2">否</label>
	                                </div>
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-4 control-label">是否启用认证：</label>
                            	<div class="col-sm-7">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio"  value="true" id="is_aut1" name="is_aut" checked="">
	                                   <label for="is_aut1"> 是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="is_aut2"  value="false" name="is_aut">
	                                    <label for="is_aut2"> 否 </label>
	                                </div>
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-4 control-label">是否启用身份证认证：</label>
                            	<div class="col-sm-7">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio" id="idcard1" value="true" name="idcard" checked="">
	                                   <label for="idcard1"> 是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="idcard2"  value="false" name="idcard">
	                                    <label for="idcard2"> 否 </label>
	                                </div>
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-4 control-label">是否启用驾驶证认证：</label>
                            	<div class="col-sm-7">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio" id="driving_license"  value="true" name="driving_license" checked="">
	                                   <label for="driving_license1"> 是</label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="driving_license2" value="false" name="driving_license">
	                                    <label for="driving_license2"> 否 </label>
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
});
 function success(result){
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
 
 $(function(){   
     //添加角色信息#commentForm
        $('#saveBut').click(function(){
        	 $("#addRoleInfo").validate({     
			 submitHandler: function(form) 
			   {  
			   	  $('#saveBut').attr("disabled",true);    
				  $.yilinAjax({
				   	  	type:'POST',
				   	  	url:'${WEB_PATH }/system/role/add.do',
				   	  	data:$('#addRoleInfo').serialize(),
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
  });   
  </script>   
