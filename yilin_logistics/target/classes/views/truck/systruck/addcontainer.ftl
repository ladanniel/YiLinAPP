<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header" style="text-align: center;">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h5 class="modal-title" style="text-align: center;">${truck.track_no}</h5>
                    <span class="label label-danger">尚未添加车辆货箱属性,请在下面表单填写</span>
                    <form class="form-horizontal m-t" id="addContainer">
                    <div class="modal-body"> 
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>车牌号:</label>
                                <div class="col-sm-8">
                                	<input name="truck.id" type="hidden"  class="form-control" value="${truck.id}" >
                                    <input id="tru" name="tru"  type="text"  class="form-control" readonly="readonly" value=${truck.track_no}>
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>货箱形式:</label>
                               	<div class="col-sm-8">
	                                <select data-placeholder="请选择货箱形式" class="chosen-select form-control"  id="containersTypeList" name="containersType.id">
									        		<option value="-1" hassubinfo="true" ></option>
									        		<#list containersTypeList as ct>
									        			<option value="${ct.id}">${ct.name}</option>
									        		</#list>
									</select>
								</div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>货箱长度(单位:米):</label>
                                <div class="col-sm-8">
		                       		<input   name="containers_long" id="containers_long"  type="text"  class="form-control" placeholder="请输入货箱长度">
                            	</div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>货箱宽度(单位:米):</label>
                                <div class="col-sm-8">
		                       		<input   name="containers_width" id="containers_width"  type="text"  class="form-control" placeholder="请输入货箱宽度">
                            	</div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>货箱高度(单位:米):</label>
                                <div class="col-sm-8">
		                       		<input   name="containers_height" id="containers_height"  type="text"  class="form-control" placeholder="请输入货箱高度">
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
 
 $(function(){   
 		
 		var $addContainer = $("#addContainer").validate({
 		rules:{
 			containers_long:{
 				required: true,
 				isNumber:true,
 				maxlength:4
 			},
 			containers_width:{
 				required: true,
 				isNumber:true,
 				maxlength:3
 			},
 			containers_height:{
 				required: true,
 				isNumber:true,
 				maxlength:3
 			}
 		} ,
 		messages:{
 			containers_long:{
 				required:e+"请输入货箱长度",
 				isNumber:e+"必须是数字",
 				maxlength:e+"最大四位数"
 			},
 			containers_width:{
 				required:e+"请输入货箱宽度",
 				isNumber:e+"必须是数字",
 				maxlength:e+"最大三位数"
 			},
 			containers_height:{
 				required:e+"请输入货箱高度",
 				isNumber:e+"必须是数字",
 				maxlength:e+"最大三位数"
 				
 			}
 		}
 	});
 		
 		$("#containersTypeList").chosen();
        $('#saveBut').click(function(){
			 	if($("#addContainer").valid()){
			   	  $('#saveBut').attr("disabled",true);    
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/truck/systruck/addcontainer.do',  
					  data: $('#addContainer').serialize(),
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
