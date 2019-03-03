<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header"  style="text-align: center;">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title" style="text-align: center;">填写真实姓名</h1>
                    <span class="label label-danger" >真实姓名只能填写一次，请谨慎操作</span>
                    <form class="form-horizontal m-t" id="addResourceInfo">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>用户姓名：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" minlength="2"  maxlength="10" type="text" value="${USER.name}"   class="form-control"  required="" aria-required="true">
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
 	$("#addResourceInfo").validate({
 		rules:{
 			name:{
 				required: !0,
 				minlength:2,
 				maxlength:10,
 				isChinese:true
 			}
 		},
 		messages:{
 			name: {
	                required: e + "请输入真实名称",
	                minlength: e + "输入名称长度至少2位",
	                maxlength: e + "输入名称长度最长10位",
	                isChinese: e + "请输入中文",
	                
	           }
 		}
 	});  
     //添加用户信息#commentForm
        $('#saveBut').click(function(){
        	
			var name = $("#name").val();
        	if(!$("#addResourceInfo").valid()) 
        		return false;
        	$('#saveBut').attr("disabled",true);    
        	swal({
		        title: "您确定要填写真实姓名吗？",
		        text: "填写后将不能在填写，请谨慎操作！",
		        type: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#DD6B55",
		        confirmButtonText: "是的，我要填写！",
		        cancelButtonText: "让我再考虑一下…",
		        closeOnConfirm: false,
		        closeOnCancel: false
		    },
		    function(isConfirm) {
		        if (isConfirm) {

		        	$.ajax({
		        		 type: 'POST',
						  url: '${WEB_PATH }/system/user/editname.do',  
						  data: $('#addResourceInfo').serialize(),
						  dataType: "json",
						  success:function(result){  
						  	if(result.success == true){
						  		swal("", result.msg, "success");
						  		setTimeout(function(){
									location.reload();
									$("#userNameId",window.parent.document).text("欢迎您："+$("#name").val());
								},1000);
						  	}else {
						  		$('#saveBut').attr("disabled",false);    
								swal({title: "操作失败", text: result.msg});
						  	}
						  }
		        	});
		        } else {
		            swal("已取消", "您取消了退出操作！", "error")
		        }
		    });
        
        	
        });
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').hide();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').hide();
       }); 
  });   
  </script>   
