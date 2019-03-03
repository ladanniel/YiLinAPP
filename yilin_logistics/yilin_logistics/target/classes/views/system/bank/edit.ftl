<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">编辑银行信息</h1>
                    <form class="form-horizontal m-t" id="addBankInfo">
                    <input type="hidden" value="${vo.id}" name="id">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-5 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>银行中文全称：</label>
                                <div class="col-sm-6">
                                    <input id="cnName" name="cnName" maxlength="36" type="text"  class="form-control"  required="" value="${vo.cnName}" aria-required="true">
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-5 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>银行英文缩写：</label>
                                <div class="col-sm-6">
                                    <input id="shortName" name="shortName" maxlength="8" type="text"  class="form-control" value="${vo.shortName}" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-5 control-label" id="upimage"><span style="color: red;">*&nbsp;&nbsp;</span>上传银行Logo：</label>
                                <div class="col-sm-6">
                                    <a href="javascript:void(0)" class="file" id="company_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">银行logo上传</span>
    																					<input type="file" id="bank_logo_image" datatype="BANK" value="${vo.image}" name="file" onchange="javascript:setImagePreview1(this);">
    																					<input type="hidden" id="bank_logo"  name="image" value="${vo.image}">
																					</a>
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-5 control-label" id="uploadMarkImage"><span style="color: red;">*&nbsp;&nbsp;</span>上传银行标志图片：</label>
                                <div class="col-sm-6">
                                    <a href="javascript:void(0)" class="file" id="uploadMarkImage_but"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">银行标志图片上传</span>
    																					<input type="file" id="bank_mark_image" datatype="BANK"  name="file" onchange="javascript:setImagePreview1(this);">
    																					<input type="hidden" id="bank_mark"  name="markImage" >
																					</a>
                                </div>
                            </div>
                            <div class="form-group" id="show-image">
                            	<label class="col-sm-3 control-label"></label>
                                <div class="col-sm-7">
                                    <img id="logo_img" src="${vo.image}">
                                    <img id="mark_img" src="${vo.markImage}">
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
       var e = "<i class='fa fa-times-circle'></i> ";
        	 $("#addBankInfo").validate({
        	 	rules: {
		        	cnName: {
		                required: !0,
		                remote:{
		                	  type:"POST",
		                      url:"${WEB_PATH }/system/bank/checkCnName.do?oldName=${vo.cnName}",
		                }
		            },
		            shortName: {
		                required: !0,
		                remote:{
		                	  type:"POST",
		                      url:"${WEB_PATH }/system/bank/checkShortName.do?oldName=${vo.shortName}",
		                }
		            }
		        },
		        messages: {
		            cnName: {
		                required: e + "请输入银行中文全称。",
		                remote: e + "已存在该银行。"
		            },
		            shortName: {
		               required: e + "请输入银行英文缩写。",
		               remote: e + "已存在该银行。"
		            }
		        }
		});
		
		$("#saveBut").click(function(){
			if(validImage() && $("#addBankInfo").valid()){
			   	  $('#saveBut').attr("disabled",true);
			   	  $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/system/bank/update.do',  
					  data: $('#addBankInfo').serialize(),
					  dataType: "json",
						 success:function(result){  
							if(result.success == true){
								$('#userModal').remove();
						    	swal("操作成功",  result.msg, "success");
						    	$("#exampleTableEvents").bootstrapTable('refresh');
							}else{
								swal("操作失败", result.msg, "error");
								$('#saveBut').attr("disabled",false);  
							}
						},
						error:function(data){
						 	var data = $.parseJSON(data.responseText);
						 	$('#saveBut').attr("disabled",false); 
					        layer.alert('<font color="red">'+ data.msg+'</font>', {
							  skin: 'layui-layer-molv' ,
							  closeBtn: 0,
							  icon: 5
							},function(index){
							   	layer.close(index);
							});
						 }
					});
			}else{
				return false;
			}
		});
		
		function validImage(){
			var img_url = $("#bank_logo").val();
			var mark_url = $("#bank_mark").val();
			var flag = true;
			if("" == img_url || null == img_url){
				$("#upimage").attr("style","color:#ed5565");
				$("#company_img_view_btn").after('<span id="erroimg" style="color:#a94442"><i class="fa fa-times-circle"></i> 请上传银行logo图片。</span>');
				flag = false;
			}
			if("" == mark_url || null == mark_url){
				$("#uploadMarkImage").attr("style","color:#ed5565");
				$("#uploadMarkImage_but").after('<span id="erroimg1" style="color:#a94442"><i class="fa fa-times-circle"></i> 请上传银行标志图片。</span>');
				flag = false;
			}
			return flag;
		}
		
       //关闭层
       $('#close-but').click(function(){
       		$('#userModal').remove();
       }); 
       $('#close-x').click(function(){
       		$('#userModal').remove();
       }); 
  });   
  
  		var fileTypes = "jpg/png/jpeg/gif";
  		function setImagePreview1($this){
			 var fileId = $this.id;
		     var fileType  = $("#"+fileId).val()
			 fileType = fileType.substring(fileType.lastIndexOf(".")+1,fileType.length);
			 fileType = fileType.toLowerCase();
			 if(fileTypes.indexOf(fileType) > -1){
				 $.ajaxFileUpload({
		                url:'${WEB_PATH}/aut/authenticat/uploadimg.do?datatype=BANK',
		                secureuri:false,
		                fileElementId:fileId,
		                dataType: 'json',
		                success: function (data, status)
		                {
		                    if(data.success == true){
		                        if(fileId == "bank_logo_image"){
		                        	$("#bank_logo").val(data.imgpath);
		                            $("#erroimg").remove();
		                        	$("#upimage").attr("style","");
		                        	$("#logo_img").attr("src", '${WEB_PATH}'+data.imgpath); 
		                        }else{
		                        	$("#bank_mark").val(data.imgpath);
		                        	$("#erroimg1").remove();
		                        	$("#uploadMarkImage").attr("style","");
		                        	$("#mark_img").attr("src", '${WEB_PATH}'+data.imgpath); 
		                        }
		                        $("#show-image").show();
		                    }else{
		                        alert("【提交失败！】");
		                    }
		                },
		                error: function (data, status, e)
		                {
		                    alert("【服务器异常，请连续管理员！】"+e);
		                }
		          });
		      }else{
		      	  alert("文件格式不正确，请重新选择！");
		      }
		}
  </script>   
