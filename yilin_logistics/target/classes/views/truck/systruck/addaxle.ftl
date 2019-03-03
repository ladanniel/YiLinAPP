<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header" style="text-align: center;">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h5 class="modal-title" style="text-align: center;">${truck.track_no}</h5>
                    <span class="label label-danger">尚未添加车辆轮轴属性,请在下面表单填写</span>
                    <form class="form-horizontal m-t" id="addAxle">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>车牌号:</label>
                                <div class="col-sm-8">
                                	<input name="truck.id" type="hidden"  class="form-control" value="${truck.id}" >
                                    <input id="tru" name="tru"  type="text"  class="form-control" readonly="readonly" value=${truck.track_no}>
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>拖挂轮轴:</label>
                               	<div class="col-sm-8">
	                                 <select data-placeholder="请选择拖挂轮轴类型" class="chosen-select form-control"  id="axleTypeList" name="axleType.id">
									        		<option value="-1" hassubinfo="true" ></option>
									        		<#list axleTypeList as at>
									        			<option value="${at.id}">${at.name}</option>
									        		</#list>
									</select>
								</div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>车辆轴数:</label>
                                <div class="col-sm-8">
                                	<select data-placeholder="请选择车辆轴数" class="chosen-select form-control"  id="bearingNumList" name="bearingNum.id">
								        		<option value="-1" hassubinfo="true" ></option>
								        		<#list bearingNumList as bn>
								        			<option value="${bn.id}">${bn.name}</option>
								        		</#list>
								    </select>
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
 		$("#axleTypeList").chosen();//轮轴类型下拉框搜索
 		$("#bearingNumList").chosen();//轴数类型下拉框搜索
 		
 		
 		
        $('#saveBut').click(function(){
        	 $("#addAxle").validate({     
			 submitHandler: function(form) 
			   {  
			   	  $('#saveBut').attr("disabled",true);    
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/truck/systruck/addaxle.do',  
					  data: $('#addAxle').serialize(),
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
