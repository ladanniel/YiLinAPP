<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">上传app</h1>
                    <form class="form-horizontal m-t" id="add_apk_from_id" action="${WEB_PATH}/system/appupload/add.do" enctype="multipart/form-data" method="post">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>版本名称：</label>
                                <div class="col-sm-8">
                                    <input id="versionId" name="versionName" minlength="2" type="text"  class="form-control"  required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>版本号：</label>
                                <div class="col-sm-8">
                                    <input  name="versionCode" minlength="1" type="number"  class="form-control"  required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">更新内容：</label>
                                <div class="col-sm-8">
                                    <input id="contentId"  name="content" class="form-control"   >
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>APK类型：</label>
                                <div class="col-sm-8">
	                                <select data-placeholder="请选择APK类型..." class="chosen-select form-control" required="" id="type" name="type">
						        		 <option value="0">货主</option>
						        		 <option value="1">司机</option>
						    		</select>
					    		</div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择apk文件：</label>
                                <div class="col-sm-8">
                                    <input id="apk_fileid" type="file" name="file"  class="form-control"   >
                                </div>
                            </div> 
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">关闭</button>
                        <button type="button" class="btn btn-primary" id="saveBut">保存</button>
                    </div>
                 	</form> 
                </div>
            </div>
        </div> 
    </div>
 
 <script>

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
 
 	$("#saveBut").on("click",function(){
 		var b = $("#add_apk_from_id").valid();
 		if(b){
 			$("#add_apk_from_id").ajaxSubmit({  
                type:"post",  //提交方式  
                dataType:"json", //数据类型  
                url:$("#add_apk_from_id").attr("action"), //请求url  
                success:success  
            });
 		}
 	}); 
 	$("#add_apk_from_id").validate({
 		rules:{
 			version:{
 				required: !0,
 				remote:{
              	  type:"POST",
                  url:"${WEB_PATH }/system/appupload/checkAppVersion.do",
                }
 			},
 			content:{
 				required: !0,
 			}
 		},
 		messages:{
 			version: {
	                required: e + "请输入验证码",
	                remote:e + "该版本已经上传过，请重新输入版本号"
	           },
	           content: {
	                required: e + "请输入更新内容"
	           }
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
