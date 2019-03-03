<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only">关闭</span>
                    </button>
                    <h1 class="modal-title">新增员工</h1>
                    <form class="form-horizontal m-t" id="editUserInfo">
                    <div class="modal-body"> 
                        	<input id="id" name="id" type="hidden" value="${user.id}" >
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>帐号：</label>
                                <div class="col-sm-8">
                                    <input id="account"name="account" maxlength="16" type="text" value="${user.account}" class="form-control" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">密码：</label>
                                <div class="col-sm-8">
                                    <input type="password" id="password" name="password" class="form-control" placeholder="不填写密码默认不修改密码" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>姓名：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" class="form-control"  value="${user.name}" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>电话：</label>
                                <div class="col-sm-8">
                                   <input id="phone" name="phone" type="number" minlength="11" class="form-control" required="" aria-required="true" value="${user.phone}">
                                </div>
                            </div>
                             <div class="form-group">
                            	<label class="col-sm-3 control-label">组织机构：</label>
                            	<div class="col-sm-8">
                            		<select class="form-control" name="companySection.id" id="source">
										<@companySection>
											<#list companySections as vo >
												<option value="${vo.id}" <#if vo.id == user.companySection.id> selected</#if>>${vo.name}</option>
											</#list>
										</@companySection>
									</select>
	                            </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">角色：</label>
                            	<div class="col-sm-8">
		                            <div class="input-group">
		                            	<select class="form-control" name="role.id">
	                                     <@roleInfo>
					                    	 <#list roleviews as role >
				                              	 <option value="${role.id}" hassubinfo="true" <#if role.checked == 'true'> selected = "selected"</#if>>${role.name}</option>
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
	                                   <input type="radio" id="inlineRadio1" value="start" name="status" <#if user.status ="start"> checked="true" </#if>>
	                                   <label for="inlineRadio1"> 正常 </label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="inlineRadio2" value="stop" name="status" <#if user.status = "stop"> checked="true" </#if>>
	                                    <label for="inlineRadio2"> 停用 </label>
	                                </div>
	                                 <div class="radio radio-inline">
	                                    <input type="radio" id="inlineRadio3" value="cancel" name="status" <#if user.status = "cancel"> checked="true" </#if>>
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
                    <div class="alert alert-info big">
					        <div class="col-md-1">
					        </div>
		     	 		 	<h4 style="color:red;">温馨提示：不填写密码默认不修改密码。</h4>
	     	 		 </div>
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
 		var e = "<i class='fa fa-times-circle'></i> ";
        	 $("#editUserInfo").validate({
        	 	rules: {
		        	account: {
		                required: !0,
		                rangelength: [6, 16],
		                remote:{
		                	  type:"POST",
		                      url:"${WEB_PATH }/system/user/checkName.do?old=${user.account}",
		                }
		            },
		            name: {
		            	required: !0,
		            },
		            password: {
		                rangelength: [6, 16],
		                isPasword:true,
		            },
		            phone: {
		                required: !0,
		                isMobile: !0,
		                remote:{
		              	  type:"POST",
		                  url:"${WEB_PATH }/system/user/checkPhone.do?old=${user.phone}",
		              }
		            }
		        },
		        messages: {
		            account: {
		                required: e + "请输入用户名。",
		                rangelength: e + "用户名由数字英文字母下划线组成并且长度为6到16位字符组成。",
		                remote: e + "用户名重复。"
		            },
		            name: {
		            	required: e + "请输入姓名。",
		            },
		            password: {
		                rangelength: e + "密码长度必须6到16个字符之间。",
		            },
		            phone: {
		                required: e + "请输入手机号。",
		                remote: e + "手机号已被注册过。",
		                isMobile:e+"手机号错误。"
		            }
		        }
		});    
		$('#saveBut').click(function(){
				if($("#editUserInfo").valid()){
			   	  $('#saveBut').attr("disabled",true); 
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/user/update.do',  
					  data: $('#editUserInfo').serialize(),
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
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').remove();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').remove();
       }); 
  });   
  </script>   
