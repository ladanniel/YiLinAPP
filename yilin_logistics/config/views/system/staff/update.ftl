<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width: 85%;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">用户详情信息修改</h1>
                    <div class="modal-body" style="max-height:450px;overflow-y: auto;"> 
                        <form class="m-t" id="staffFormId">
                        	<input type="hidden" name="id" value="${staff.id}" />
	                        <div style="" class="company ui-sortable">
	                    		 <div class="row">
	                    		 	<div class="col-sm-4"> 
		                                <div class="form-group">
		                                    <label>员工名称：</label>
		                                   	<input id="text" value="${staff.account.name}"  minlength="2" type="text" readonly class="form-control" placeholder="请输入姓名">
		                                </div>
		                            </div>
		                            <div class="col-sm-4" >
		                                <div class="form-group"  id="data_3">
		                                    <label>出生日期：</label>
		                                    <div class="input-group date">
                                				<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                                    	<input name="birthday" value="${staff.birthday?string("yyyy-MM-dd")}" class="form-control" readonly type="text" value="" >
		                                    </div>
		                                </div>
		                            </div> 
		                             <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>学历：</label>
		                                     <select data-placeholder="选择学历"  name="education.id" class="chosen-select form-control" >
		                                     	<#list edus as o >
			                                   		<option value="${o.id}" <#if staff.education.id == o.id >selected</#if> hassubinfo="true">${o.name}</option>
			                                   	</#list>
			                                  </select>
		                                </div>
		                            </div>  
		                         </div>
	                    		 <div class="row">
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>性别：</label>
		                                   <select data-placeholder="选择性别"  name="sex" class="chosen-select form-control" tabindex="2" >
		                                   	<option value="female" hassubinfo="true"<#if staff.sex == 'female'>selected</#if> >女</option>
		                                   	<option value="male" hassubinfo="true"<#if staff.sex == 'male'>selected</#if>>男</option>
		                                   </select>
		                                </div>
		                            </div> 
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>民族：</label>
		                                     <select data-placeholder="选择民族"  name="nation" class="chosen-select form-control" >
		                                     	<#list national as o >
			                                   		<option value="${o}" hassubinfo="true" <#if staff.nation == o>selected</#if>>${o}</option>
			                                   	</#list>
			                                  </select>
		                                </div>
		                            </div> 
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>籍贯：</label>
											<input id="area_id_id" name="area_id" type="hidden" >
		                                    <input id="origin_id" name="origin" class="form-control" readonly type="text" value="${staff.origin}" >
		                                </div>
		                            </div> 
		                         </div>
		                         <div class="row">
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>专业：</label>
		                                    <input  name="major"  value="${staff.major}"  type="text"  class="form-control" placeholder="请输入专业">
		                                </div>
		                            </div> 
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>毕业学校：</label>
		                                    <input   name="graduate_school"  value="${staff.graduate_school}"  type="text"  class="form-control" placeholder="请输入毕业学校">
		                                </div>
		                            </div> 
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>邮箱：</label>
		                                    <input  name="email"  type="email" value="${staff.email}"  class="form-control" placeholder="请输入地址">
		                                </div>
		                            </div>  
		                        </div>
		                    </div>
	                    </form> 
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary btn-outline" id="saveBut"><i class="fa fa-check"></i>&nbsp;&nbsp;保存</button>
                        <button type="button" class="btn btn-white btn-outline" data-dismiss="modal" id="close-but">取消</button>
                    </div>
                </div>
            </div>
        </div> 
    </div>
 <script>
 $(function(){ 
 	var e = "<i class='fa fa-times-circle'></i> ";
 	$("#staffFormId").validate({
 		rules:{
 			name:{
 				required: true, 
 				isChinese:true,
			    minlength: 2,
			    maxlength:5
 			},
 			major:{
 				required: true, 
 				isChinese:true,
			    minlength: 3,
			    maxlength:30
 			},
 			birthday:{
 				required: true,
 			},
 			email:{
 				required: true, 
 			},
 			origin:{
 				required: true, 
 			},
 			graduate_school:{
 				required: true, 
 				isChinese:true,
			    minlength: 3,
			    maxlength:30
 			}
 		},
 		messages:{
 			name:{
 				required:e+"请输入您的姓名"
 			},
 			major:{
 				required:e+"请输入您的专业"
 			},
 			birthday:{
 				required:e+"请选择您的出生日期"
 			},
 			origin:{
 				required:e+"请选择您的籍贯"
 			},
 			email:{
 				required:e+"请输入您的电子邮箱"
 			},
 			graduate_school:{
 				required:e+"请输入您的毕业院校"
 			}
 		}
 	});
 
 	$('#origin_id').citypicker({areaId:'area_id_id'}); 
    //关闭层
    $('#close-but').click(function(){
   		$('#userModal').remove();
    }); 
    $('#close-x').click(function(){
   		$('#userModal').remove();
    }); 
    $('#saveBut').click(function(){  
    	if($("#staffFormId").valid()) {
    		layer.msg('加载中', {icon: 16,shade: 0.3,time:8000});
    		$.yilinAjax({
			  	 type:'POST',
			  	 url:'${WEB_PATH }/system/staff/update.do',
			  	 data:$("#staffFormId").serializeJson(),
				 errorcallback:null,
				 btn:null,
				 successcallback:success
			 });
    	}
	}); 
	
	function success(result){
		layer.closeAll(); 
		if(result.success == true){
		  	$("#exampleTableEvents").bootstrapTable('refresh');
		  	$('#userModal').remove();
		}else{
			swal("操作失败", result.msg, "error");
			$('#saveBut').attr("disabled",false);  
		}
	} 
	
	 $("#data_3 .input-group.date").datepicker({
        startView: 2,
        todayBtn: "linked",
        keyboardNavigation: !1,
        forceParse: !1,
        autoclose: !0
    })
});

  </script>   
