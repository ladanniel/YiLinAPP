<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">修改资源</h1>
                    <form class="form-horizontal m-t" id="editResourceInfo">
                    <div class="modal-body"> 
                    		<div class="form-group">
                    			<label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>所属类型：</label>
                                <div class="col-sm-8">
                                 <select class="form-control" name="companyType.id">
	                                 <@companyTypeInfo>
				                    	 <#list companyTypeViews as companyType >
			                              	 <option value="${companyType.id}" <#if resource.companyType.id == companyType.id>selected = "selected"</#if>>${companyType.name}</option> 
			                             </#list>
									 </@companyTypeInfo>
                                 </select> 
                                </div> 
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>资源名称：</label>
                                <div class="col-sm-8">
                                	<input id="id" name="id"   type="hidden"  value="${resource.id}">
                                    <input id="name" name="name" minlength="2" type="text"  class="form-control"  required="" aria-required="true" value="${resource.name}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">链接：</label>
                                <div class="col-sm-8">
                                    <input type="text" id="url" name="url" class="form-control"  value="${resource.url}" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <input id="remark" name="remark" class="form-control"  value="${resource.remark}" >
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
     //添加用户信息#commentForm
        $('#saveBut').click(function(){
        	 $("#editResourceInfo").validate({     
			 submitHandler: function(form) 
			   {  
			   	  $('#saveBut').attr("disabled",true);    
				  $.yilinAjax({
				   	  	type:'POST',
				   	  	url:'${WEB_PATH }/system/resource/edit.do',
				   	  	data:$('#editResourceInfo').serialize(),
	            		errorcallback:null,
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
				swal("", result.msg, "success");
		    	$("#resourceTableEvents").bootstrapTable('refresh');
			}else{
				$('#saveBut').attr("disabled",false);    
				swal({
			        title: "操作失败",
			        text: result.msg
		    	});
			}
	   }
  });   
  </script>   
