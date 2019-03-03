<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only">关闭</span>
                    </button>
                    <h1 class="modal-title">修改用户</h1>
                    <form class="form-horizontal m-t" id="editUserInfo">
                    <div class="modal-body"> 
                        	<input id="id" name="id" type="hidden" value="${user.id}" >
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>帐号：</label>
                                <div class="col-sm-8">
                                    <input id="account"name="account" minlength="2" type="text" value="${user.account}" class="form-control" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">密码：</label>
                                <div class="col-sm-8">
                                    <input type="password" id="pass" name="pass" class="form-control"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>姓名：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" class="form-control"  value="${user.name}" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">年龄：</label>
                                <div class="col-sm-8">
                                   <input id="age" name="age" type="number" class="form-control"  value="${user.age}" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">电话：</label>
                                <div class="col-sm-8">
                                   <input id="phone" name="phone" type="number" minlength="11" class="form-control" value="${user.phone}">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">角色：</label>
                            	<div class="col-sm-8">
		                            <div class="input-group">
		                                <select data-placeholder="选择角色" id="roleSelect" name="roleIds"  class="chosen-select" multiple style="width:328px;" tabindex="4">
		                                    <@roleInfo uid = user.id>
						                    	<#list roleviews as role >
					                            	 <option value="${role.id}" hassubinfo="true" <#if role.checked == 'true'> selected = "selected"</#if>>${role.text}</option>
					                            </#list>
											</@roleInfo>
		                                </select>
		                            </div>
	                            </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">状态：</label>
                            	<div class="col-sm-8">
	                                <div class="radio radio-info radio-inline">
	                                   <input type="radio" id="inlineRadio1" value="0" name="status" <#if user.status = 0> checked="true" </#if>>
	                                   <label for="inlineRadio1"> 正常 </label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="inlineRadio2" value="1" name="status" <#if user.status = 1> checked="true" </#if>>
	                                    <label for="inlineRadio2"> 停用 </label>
	                                </div>
	                                 <div class="radio radio-inline">
	                                    <input type="radio" id="inlineRadio3" value="2" name="status" <#if user.status = 2> checked="true" </#if>>
	                                    <label for="inlineRadio3">注销 </label>
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
		$("#roleSelect").chosen(); 
     //添加用户信息#commentForm
        $('#saveBut').click(function(){
        	 $("#editUserInfo").validate({     
			 submitHandler: function(form) 
			   {   
			   	  $('#saveBut').attr("disabled",true);       
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/user/edit.do',  
					  data: $('#editUserInfo').serialize(),
					  dataType: "json",
						 success:function(result){  
						 	$('#userModal').remove();
							if(result.success == true){
						    	swal("", result.msg, "success");
						    	$("#exampleTableEvents").bootstrapTable('refresh');
							}else{
						    	swal("", result.msg, "error");
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
