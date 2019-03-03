<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">新增员工</h1>
                    <form class="form-horizontal m-t" id="addUserInfo">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>帐号：</label>
                                <div class="col-sm-8">
                                    <input id="account"name="account" maxlength="16" type="text" required="" class="form-control"  aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>密码：</label>
                                <div class="col-sm-8">
                                	<input type="text" value="000" hidden="true"/>
                                    <input type="password" id="password" name="password" class="form-control"  required="" aria-required="true" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>姓名：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" class="form-control"  required="" aria-required="true" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>电话：</label>
                                <div class="col-sm-8">
                                   <input id="phone" name="phone" type="number" class="form-control" minlength="11" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">组织机构：</label>
                            	<div class="col-sm-8">
                            		<select class="form-control" name="companySection.id" id="source">
										<@companySection>
											<#list companySections as vo >
												<option value="${vo.id}" <#if vo.id == id> selected</#if>>${vo.name}</option>
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
				                              	 <option value="${role.id}">${role.name}</option> 
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
	                                   <input type="radio" id="inlineRadio1" value="start" name="status" checked="">
	                                   <label for="inlineRadio1"> 正常 </label>
	                                </div>
	                                <div class="radio radio-inline">
	                                    <input type="radio" id="inlineRadio2" value="stop" name="status">
	                                    <label for="inlineRadio2"> 停用 </label>
	                                </div>
	                                 <div class="radio radio-inline">
	                                    <input type="radio" id="inlineRadio3" value="cancel" name="status">
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
});
 $(function(){   
 		var e = "<i class='fa fa-times-circle'></i> ";
 		$("#addUserInfo").validate({
        	 	rules: {
		        	account: {
		                required: !0,
		                rangelength: [6, 16],
		                remote:{
		                	  type:"POST",
		                      url:"${WEB_PATH }/system/user/checkName.do",
		                }
		            },
		            name: {
		            	required: !0,
		            },
		            password: {
		                required: !0,
		                rangelength: [6, 16],
		                isPasword:true,
		            },
		            phone: {
		                required: !0,
		                isMobile: !0,
		                remote:{
		              	  type:"POST",
		                  url:"${WEB_PATH }/system/user/checkPhone.do",
		              }
		            }
		        },
		        messages: {
		            account: {
		                required: e + "请输入用户名。",
		                rangelength: e + "用户名由字母开头及数字或字母组成并且长度为6到16位。",
		                remote: e + "用户名重复。"
		            },
		            name: {
		            	required: e + "请输入姓名。",
		            },
		            password: {
		                required: e + "请输入密码。",
		                rangelength: e + "密码由字母和数字混合组成长度必须6到16个字符之间。",
		            },
		            phone: {
		                required: e + "请输入手机号。",
		                remote: e + "手机号已被注册过。",
		                isMobile:e+"手机号错误。"
		            }
		        }
		   });
     //添加用户信息#commentForm
        $('#saveBut').click(function(){
        	 if($("#addUserInfo").valid()){
        	 	$('#saveBut').attr("disabled",true); 
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/user/save.do',  
					  data: $('#addUserInfo').serialize(),
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
