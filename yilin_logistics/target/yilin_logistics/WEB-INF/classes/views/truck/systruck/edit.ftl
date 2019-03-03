

	<style>
    	.company:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "车辆信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.company {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		}
		.account:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "账户信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.account {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-top: 10px;
		}
		 
    </style>
<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width: 85%;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">编辑车辆信息</h1>
                    <div class="modal-body" style="max-height:450px;overflow-y: auto;"> 
                        <form class="m-t" id="editTruckInfo">
                         <input name="id" type="hidden"  class="form-control" value="${truck.id}" >
                    <input name="create_time" type="hidden"  class="form-control" value="${truck.create_time}" >
	                        <div style="" class="company ui-sortable">
	                    		 <div class="row">
	                    		 	<div class="col-sm-4"> 
		                                <div class="form-group">
		                                    <label>车牌号：</label>
		                                    <input name="track_no" id="track_no"  type="text"  class="form-control" value="${truck.track_no}" placeholder="${truck.track_no}">
		                                </div>
		                            </div>
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>车辆类型：</label>
                                   			<select data-placeholder="请选择车辆类型..." class="chosen-select form-control" required="" id="trucktype_list" name="truckType.id">
								        		<#list trucktypeList as tt>
								        			<option value="${tt.id}" <#if truck.truckType.id == tt.id>selected = "selected"</#if>>${tt.name}</option>
								        		</#list>
								    		</select>
		                            	</div>
		                           </div>
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>车牌类型：</label>
		                                    <select data-placeholder="请选择车牌类型..." class="chosen-select form-control" required="" id="truckplate_list" name="truckPlate.id">
								        		<#list truckplateList as tp>
								        			<option value="${tp.id}" <#if truck.truckPlate.id == tp.id>selected = "selected"</#if>>${tp.name}</option>
								        		</#list>
								    		</select>
		                                </div>
		                            </div> 
		                         </div>
		                         <div class="row">
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>车辆长度(长度数字)：</label>
		                                    <input   name="track_long" id="track_long"  type="text"  class="form-control" value="${truck.track_long}" placeholder="请输入车辆长度">
		                                </div>
		                            </div> 
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>车辆载重(吨位数字)：</label>
		                                    <input   name="capacity" id="capacity"  type="text"  class="form-control" value="${truck.capacity}" placeholder="请输入车辆载重">
		                                </div>
		                            </div> 
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>车牌品牌：</label>
		                                    <select data-placeholder="请选择车辆品牌..." class="chosen-select form-control" required="" id="truckbrand_list" name="truckBrand.id">
								        		<#list truckbrandList as tb>
								        			<option value="${tb.id}" <#if truck.truckBrand.id == tb.id>selected = "selected"</#if>>${tb.name}</option>
								        		</#list>
								    	</select>
		                                </div>
		                            </div>  
		                        </div>
		                        <div class="row">
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>车辆识别代码：</label>
		                                    <input   name="track_read_no" id="track_read_no"  type="text"  class="form-control" value="${truck.track_read_no}" placeholder="请输入车辆识别码">
		                                </div>
		                            </div> 		                            
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>发动机品牌：</label>
		                                    <select data-placeholder="请选择发动机品牌..." class="chosen-select form-control" required="" id="enginebrand_list" name="engineBrand.id">
								        		<#list enginebrandList as eb>
								        			<option value="${eb.id}" <#if truck.engineBrand.id == eb.id>selected = "selected"</#if>>${eb.name}</option>
								        		</#list>
								    	</select>
		                                </div>
		                            </div>  
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>发动机编号：</label>
		                                    <input   name="engine_no" id="engine_no"  type="text"  class="form-control" value="${truck.engine_no}"  placeholder="请输入发动机编号">
		                                </div>
		                            </div> 
		                        </div>
		                        <div class="row">
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>发动机马力：</label>
		                                    <input   name="horsepower" id="horsepower"  type="text"  class="form-control" value="${truck.horsepower}"  placeholder="请输入发动机马力">
		                                </div>
		                            </div> 		
		                            <div class="col-sm-4">
		                                <div class="form-group"  id="data_1">
		                                    <label>上牌时间：</label>
		                                    <div class="input-group date">
                                				<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                				<input type="text" class="form-control" value="${truck.tag_time}" placeholder="选择上牌时间" name="tag_time" id="tag_time">
                           					 </div>
		                                </div>
		                            </div>                             
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>所属商户：</label>
		                                    <input name="company.id" type="hidden"  class="form-control" value="${truck.company.id}" >
		                                    <input   name="com"  type="text"  class="form-control"  readonly="readonly"  value="${truck.company.name}" >
		                                </div>
		                            </div>  
		                        </div>
		                        <div class="row">
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>车辆描述：</label>
		                                    <input   name="description" id="description"  type="text"  class="form-control" value="${truck.description}" placeholder="请输入车辆描述">
		                                </div>
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

    </div>


    
 <script>

 $(function(){   
 		var $editTruckInfo = $("#editTruckInfo").validate({
 		rules:{
 			track_no:{
 				required: true,
 				maxlength:7,
 				isPlateNo:true,
 				remote:{
                	  type:"POST",
                      url:"${WEB_PATH }/truck/systruck/checkNo.do?old=${truck.track_no}",
                }
 			},
 			track_long:{
 				required: true, 
 				maxlength:4,
 				isNumber:true
 			},
 			capacity:{
 				required: true, 
 				maxlength:4,
 				isNumber:true
 			},
 			track_read_no:{
 				required: true,
 				maxlength:17,
 				isZiMuNo:true,
 				isVin:true
 			},
 			engine_no:{
 				required: true,
 				isEngineNo:true,
 				maxlength:9
 			},
 			horsepower:{
 				required: true, 
 				isNumber:true,
 				maxlength:4
 			},
 			tag_time:{
 				required: true
 			}
 		} ,
 		messages:{
 			track_no:{
 				required:e+"请选择车牌信息",
 				remote:e+"该车牌已存在",
 				isPlateNo:e+"车牌格式错误"
 			},
 			track_long:{
 				required:e+"请输入车辆长度",
 				isNumber:e+"必须是数字",
 				maxlength:e+"长度最多四位数"
 			},
 			capacity:{
 				required:e+"请输入车辆载重量",
 				maxlength:e+"吨位最多四位数",
 				isNumber:e+"必须是数字"
 			},
 			track_read_no:{
 				required:e+"请输入车辆识别码",
 				maxlength:e+"车辆识别码最多十七位字符",
 				isZiMuNo:e+"车辆识别码不能包含中文"
 			},
 			engine_no:{
 				required:e+"请输入发动机编号",
 				maxlength:e+"发动机编号必须为9位字符"
 			},
 			horsepower:{
 				required:e+"请输入车辆马力",
 				maxlength:e+"车辆马力最多四位数",
 				isNumber:e+"必须是数字",
 				
 			},
 			tag_time:{
 				required:e+"请选择上牌时间"
 			}
 		}
 	});
 		//$('#track_no').citypicker(); 
 		$("#tag_time").datepicker({
	        keyboardNavigation: !1,
	        forceParse: !1,
	        autoclose: !0
	    }); 
   		$("#trucktype_list").chosen();//车辆类型下拉框搜索
   		$("#truckplate_list").chosen();//车牌类型下拉框搜索
   		$("#truckbrand_list").chosen();//车辆品牌下拉框搜索
  		$("#enginebrand_list").chosen();//发动机品牌下拉框搜索
   		
        $('#saveBut').click(function(){
        	 	if($("#editTruckInfo").valid()){	
			   	  $('#saveBut').attr("disabled",true);    
			      $.ajax({
					  type: 'POST',
					  url: '${WEB_PATH }/truck/systruck/edit.do',  
					  data: $('#editTruckInfo').serialize(),
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

