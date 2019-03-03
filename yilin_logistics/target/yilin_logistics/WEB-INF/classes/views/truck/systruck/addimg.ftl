<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width: 85%;height:30px">
            <div id="rootwizard" class="modal-content animated bounceInRight">
                <div class="modal-header" style="text-align: center;">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h5 class="modal-title" style="text-align: center;">${truck.track_no}</h5>
                    <span class="label label-danger">尚未添加车辆图片,请在以下表单中按示例图片添加</span>
                    <div  class="modal-body" style="max-height:450px;overflow-y: auto;"> 
                         <div  class="tabbable tabs-left">
							<ul class="nav nav-tabs" id="myTab">
							       <input id="truckid" name="truck.id" type="hidden"  class="form-control" value="${truck.id}" >
									<li><a href="#tab2" data-toggle="tab" id="idcard_info">车辆前方</a></li>
									<li><a href="#tab3" data-toggle="tab" id="driving_license_info">车辆后方</a></li>
									<li><a href="#tab4" data-toggle="tab" id="business_license_info">侧面及中控</a></li> 
							</ul>
							<div class="tab-content">
							    <div class="tab-pane" id="tab2">
							      	<div class="ibox-content">
							      		<form id="truck_info_form1">
					                    	<div class="row">
					                            <div class="col-sm-12">
					                                <div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="track_ahead_img_view_panel">
							                                    <div class="panel-heading">
							                                      	车辆正前方图片-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div >
							                                    		 <div >
								                                         	<img id="track_ahead_img_view" src="${WEB_PATH}/resources/img/front.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_front_img_view_btn" >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">正前方图片上传</span>
    																					<input type="file" id="track_track_ahead_img" datatype="TRUCK" name="file" onchange="javascript:setImagePreview1(this,'track_ahead_img_view');">
    																					<input type="hidden" id="track_ahead_img" name = "track_ahead_img">
																					</a>
																				</div>
												                            </div>
								                                         </div>
									                                </div>
							                                    </div>
							                                </div>
						                                </div>
					                            	</div>
					                            	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="track_left_front_img_view_panel">
							                                    <div class="panel-heading">
							                                      	车辆左前方图片-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
								                                         <div >
								                                         	<img id="track_left_front_img_view" src="${WEB_PATH}/resources/img/leftfront.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_back_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">左前方图片上传</span>
    																					<input type="file" id="track_track_left_front_img" datatype="TRUCK"  name="file"  onchange="javascript:setImagePreview1(this,'track_left_front_img_view');">
    																					<input type="hidden" id="track_left_front_img"  name="track_left_front_img" >
																					</a>
																				</div>
												                            </div>
								                                         </div>
									                                </div>
							                                    </div>
							                                </div>
						                                </div>
					                            	</div>
					                            	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="track_right_front_img_view_panel">
							                                    <div class="panel-heading">
							                                      	车辆右前方图片-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
							                                    		 <div >
								                                         	<img id="track_right_front_img_view" src="${WEB_PATH}/resources/img/rightfront.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_persoin_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">右前方图片上传</span>
    																					<input type="file" id="track_track_right_front_img" datatype="TRUCK" name="file" onchange="javascript:setImagePreview1(this,'track_right_front_img_view');">
    																					<input type="hidden" id="track_right_front_img"  name="track_right_front_img" >
																					</a>
																				</div>
												                            </div>
								                                         </div>
									                                </div>
							                                    </div>
							                                </div>
						                                </div>
					                            	</div>
					                            </div>
					                        </div>
				                        </form>
				                    </div>
							    </div>
								<div class="tab-pane" id="tab3">
									<div class="ibox-content">
									    <form id="truck_info_form2">
					                    	<div class="row">
					                            <div class="col-sm-12">
					                            	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="track_abehind_img_view_panel">
							                                    <div class="panel-heading">
							                                      	车辆正后方图片-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div >
							                                    		 <div >
								                                         	<img id="track_abehind_img_view" src="${WEB_PATH}/resources/img/behind.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_front_img_view_btn" >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">正后方图片上传</span>
    																					<input type="file" id="track_track_abehind_img" datatype="TRUCK" name="file" onchange="javascript:setImagePreview1(this,'track_abehind_img_view');">
    																					<input type="hidden" id="track_abehind_img" name = "track_abehind_img">
																					</a>
																				</div>
												                            </div>
								                                         </div>
									                                </div>
							                                    </div>
							                                </div>
						                                </div>
					                            	</div>
					                            	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="track_behind_img_view_panel">
							                                    <div class="panel-heading">
							                                      	车辆后侧面图片-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
							                                    		 <div >
								                                         	<img id="track_behind_img_view" src="${WEB_PATH}/resources/img/leftbehind.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_persoin_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">后侧面图片上传</span>
    																					<input type="file" id="track_track_behind_img" datatype="TRUCK" name="file" onchange="javascript:setImagePreview1(this,'track_behind_img_view');">
    																					<input type="hidden" id="track_behind_img"  name="track_behind_img" >
																					</a>
																				</div>
												                            </div>
								                                         </div>
									                                </div>
							                                    </div>
							                                </div>
						                                </div>
					                            	</div>
					                            	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="track_side_img_view_panel">
							                                    <div class="panel-heading">
							                                      	车辆侧面图片-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
							                                    		 <div >
								                                         	<img id="track_side_img_view" src="${WEB_PATH}/resources/img/side.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_persoin_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">侧面图片上传</span>
    																					<input type="file" id="track_track_side_img" datatype="TRUCK" name="file" onchange="javascript:setImagePreview1(this,'track_side_img_view');">
    																					<input type="hidden" id="track_side_img"  name="track_side_img" >
																					</a>
																				</div>
												                            </div>
								                                         </div>
									                                </div>
							                                    </div>
							                                </div>
						                                </div>
					                            	</div>
					                            </div>
					                        </div>
				                        </form>
				                    </div>
							    </div>
								<div class="tab-pane" id="tab4">
									<div class="ibox-content">
										<form id="truck_info_form3">
					                    	<div class="row">
					                            <div class="col-sm-12">
					                            	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="track_console_img_view_panel">
							                                    <div class="panel-heading">
							                                      	车辆中控图片-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
							                                    		 <div >
								                                         	<img id="track_console_img_view" src="${WEB_PATH}/resources/img/console.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_persoin_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">中控图片上传</span>
    																					<input type="file" id="track_track_console_img" datatype="TRUCK" name="file" onchange="javascript:setImagePreview1(this,'track_console_img_view');">
    																					<input type="hidden" id="track_console_img"  name="track_console_img" >
																					</a>
																				</div>
												                            </div>
								                                         </div>
									                                </div>
							                                    </div>
							                                </div>
						                                </div>
					                            	</div>
					                            	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="track_dashboard_img_view_panel">
							                                    <div class="panel-heading">
							                                      	车辆仪表盘图片-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
							                                    		 <div >
								                                         	<img id="track_dashboard_img_view" src="${WEB_PATH}/resources/img/instrumentpanel.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_persoin_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">仪表盘图片上传</span>
    																					<input type="file" id="track_track_dashboard_img" datatype="TRUCK" name="file" onchange="javascript:setImagePreview1(this,'track_dashboard_img_view');">
    																					<input type="hidden" id="track_dashboard_img"  name="track_dashboard_img" >
																					</a>
																				</div>
												                            </div>
								                                         </div>
									                                </div>
							                                    </div>
							                                </div>
						                                </div>
					                            	</div>
					                            </div>
					                        </div>
				                        </form>
				                    </div>
							    </div>
							</div>
						</div>
                    </div>
                    <ul class="pager wizard" style="text-align: right;padding-right: 20px;">
						<li class="previous"><button class="btn btn-primary " type="button"><i class="fa fa-arrow-circle-left"></i>&nbsp;&nbsp;<span class="bold">上一步</span></button></li>
					  	<li class="next"><button class="btn btn-primary " type="button"><i class="fa fa-arrow-circle-right"></i>&nbsp;&nbsp;<span class="bold">下一步</span></button></li>
					  	<li class="finish"><button class="btn btn-primary btn-danger" id="savecompanyaut" type="button"><i class="fa fa-check"></i>&nbsp;<span class="bold">完 &nbsp;&nbsp;成</span></button></li>
					</ul>
                </div>
            </div>
        </div> 
    </div>
 <script>
    var e = "<i class='fa fa-times-circle'></i> ";
	var fileTypes = "jpg/png/jpeg";
	function setImagePreview1(obj,showImg){//参数为上传控件对象和显示图片控件id
	     var fileType  = $("#"+obj.id).val();
		 fileType = fileType.substring(fileType.lastIndexOf(".")+1,fileType.length);
		 fileType = fileType.toLowerCase();
		 if(fileTypes.indexOf(fileType) > -1){//判断格式是否正确
			 var datatype = $("#"+obj.id).attr("datatype");
			 var imgurl  = obj.id.substring(obj.id.indexOf("_")+1,obj.id.length);//获取图片服务器路径隐藏域id
			 layer.msg('文件上传中......', {icon: 16,shade: 0.3,time:8000});
			 $.ajaxFileUpload({
			 		type: "POST",
	                url:'${WEB_PATH}/truck/truckimg/uploadimg.do?datatype='+datatype,
	                secureuri:false,
	                fileElementId:obj.id,
	                dataType: 'json',
	                success: function (data, status)
	                {
	                	layer.closeAll();
	                    if(data.success == true){
	                        $("#"+imgurl).val(data.imgpath);//设置图片路径隐藏域的值为服务器路径
	                        $("#"+showImg).attr("src", '${WEB_PATH}'+data.imgpath);  //设置显示图片控件为新图片
	                        setFileError(showImg+"_panel",true,null);
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
	      	  layer.tips("文件格式不正确，请重新选择！", '#'+showImg+"_panel",{
				  tips: [1, '#ed5565']
			  });
	      }
	}
	
	function setFileError(id,isfalse,msg){
		var btn_id = id.substring(0,id.lastIndexOf("_"))
		var btn = $("#"+btn_id+"_btn");
		if(!isfalse){
			$("#"+id).removeClass('panel-primary');
			$("#"+id).addClass('panel-danger');
			btn.addClass('error');
			if(msg != null ){
				layer.tips(msg, '#'+id,{
				  tips: [3, '#ed5565']
				});
			}
		}else{
			$("#"+id+"").removeClass('panel-danger');
			$("#"+id).addClass('panel-primary');
			btn.removeClass('error');
		}
		
	}
   $(function(){ 
    $('#rootwizard').bootstrapWizard({
        'tabClass': 'nav nav-tabs',
    	'onNext': function(tab, navigation, index) {
    		$('body,html').animate({scrollTop:0},1000);
    	    var type = tab.get(0).childNodes[0].id;
  			return true;
  		},
  		'onFinish': function(tab, navigation, index) {
  			
  			var type = tab.get(0).childNodes[0].id;
  			//if(validatCompanyAutInfo(type)){
  				layer.msg('正在努力提交中', {icon: 16,shade: 0.3,time:8000});
  				saveCompanyAutInfo();
  			//} 
  		},
  		'onPrevious': function(tab, navigation, index) {
  			$('body,html').animate({scrollTop:0},1000);
  		},
  		'onTabClick': function(tab, navigation, index) {
  			$('body,html').animate({scrollTop:0},1000);
  			var type = tab.get(0).childNodes[0].id;
  			return true;
  		}
    });
	var $idcard_info_form =  $('#idcard_info_form').validate({
		  rules: {
		    name: {
		      required: true,
		      isChinese:true
		    },
		    idcard_no: {
		      required: true,
		      isIdCardNo:true,
		      remote:{
              	  type:"POST",
                  url:"${WEB_PATH }/aut/authenticat/checkIdcardNo.do",
              }
		    } 
		  },
		  messages: {
            idcard_no: {
                remote:e+"身份证号码已存在！"
            }
          },
		  errorPlacement: function(error, element) { 
            	element.before(error)
          }
	});
    var $driving_license_info_form =  $('#driving_license_info_form').validate({
		  rules: {
		    name: {
		      required: true,
		      isChinese:true
		    },
		    driving_license_no: {
		      required: true,
		      isDigits: true,
		      remote:{
              	  type:"POST",
                  url:"${WEB_PATH }/aut/authenticat/checkDrivingLicenseNo.do" 
              }
		    },
		    sex: {
		      required: true
		    },
		    valid_start_date: {
		      required: true
		    },
		    valid_year: {
		      required: true
		    } 
		  },
		  messages: {
            driving_license_no: {
                remote:e+"驾驶证编号已存在！"
            }
          },
		  errorPlacement: function(error, element) {
              element.before(error)
          }
	});
	var $business_license_info_form =  $('#business_license_info_form').validate({
		  rules: {
		    name: {
		      required: true,
		      isChinese:true
		    },
		    company_no: {
		      required: true,
		      isZiMuNo:true,
		      remote:{
              	  type:"POST",
                  url:"${WEB_PATH }/aut/authenticat/checkBusinessLicenseNo.do",
              }
		    },
		    company_create_date: {
		      required: true
		    },
		    company_validity_date: {
		      required: true
		    } 
		    
		  },
		  messages: {
            company_no: {
                remote:e+"营业执照编号已存在！"
            }
          },
          errorPlacement: function(error, element) { 
            	element.before(error)
          }
	});
	$('#areaFullName').citypicker({areaId:'area_id'}); 
	$("#valid_start_date").datepicker({
        keyboardNavigation: !1,
        forceParse: !1,
        autoclose: !1
    });
    $("#company_create_date").datepicker({
        keyboardNavigation: !1,
        forceParse: !1,
        autoclose: !1
    });
    $("#company_validity_date").datepicker({
        keyboardNavigation: !1,
        forceParse: !1,
        autoclose: !1
    });
    //关闭层
    $('#close-but').click(function(){
   		$('#userModal').remove();
    }); 
    $('#close-x').click(function(){
   		$('#userModal').remove();
    }); 
    $('#saveBut').click(function(){  
	   	 //var isfalse = validatCompanyAccountInfo();
	   	// if(isfalse){
	   	 	saveAccountCompany();
	   	 //}
	}); 
	$('#noSaveBut').click(function(){  
	   	  var id = $("#company_id").val();  
		  layer.prompt({title: '请填写不通过原因', formType: 2}, function(text){
		  	  $('#noSaveBut').attr("disabled",true);  
		      saveAutCompany(id,false,text,$('#noSaveBut'))
		  }); 
	   	  
	}); 
	
});

/*
function validatCompanyAutInfo(type){
	if(type == "idcard_info"){
		var $valid = $("#"+type+"_form").valid();
		if(!$valid) {
			return false;
		}else{
    		var idcard_idcard_front_img = $('#idcard_front_img').val();
        	var idcard_idcard_back_img = $('#idcard_back_img').val();
        	var idcard_idcard_persoin_img = $('#idcard_persoin_img').val();
        	if(idcard_idcard_front_img == "" ){
        		setFileError('idcard_front_img_view_panel',false,"请上传身份证正面文件！");
        		return false;
        	}
        	if(idcard_idcard_back_img == "" ){
        		setFileError('idcard_back_img_view_panel',false,"请上传身份证反面文件！");
        		return false;
        	}
    	}
    	
	}
	if(type == "driving_license_info"){
		var $valid = $("#"+type+"_form").valid();
		if(!$valid) {
			return false;
		}else{
    		var driving_driving_license_img = $('#driving_license_img').val();
        	if(driving_driving_license_img == "" ){
        		setFileError('driving_license_img_view_panel',false,"请上传驾驶证文件！");
        		return false;
        	}
    	}
	}
	if(type == "business_license_info"){
		var $valid = $("#"+type+"_form").valid();
		if(!$valid) {
			return false;
		}else{
    		var business_company_img = $('#company_img').val();
        	if(business_company_img == "" ){
        		setFileError('company_img_view_panel',false,"请上传营业执照文件！");
        		return false;
        	}
    	}
	}
	return true;
}
*/
function saveCompanyAutInfo(){
	//var truck_id =  $("#truckid").val();
	//var track_ahead_img=$("#track_ahead_img").val();
	//var track_abehind_img=$("#track_abehind_img").val();
	//var track_console_img=$("#track_console_img").val();
	//var track_left_front_img=$("#track_left_front_img").val();
	//var track_right_front_img=$("#track_right_front_img").val();
	//var track_side_img=$("#track_side_img").val();
	//var track_behind_img=$("#track_behind_img").val();
	//var track_dashboard_img=$("#track_dashboard_img").val();
	
	//var truck_info1 = $("#truck_info_form1").serializeJson();
	//var truck_info2 = $("#truck_info_form2").serializeJson();
	//var truck_info3 = $("#truck_info_form3").serializeJson();
	//truck=new Object();
	//truck.truck_id= company_id;
	//companyAutInfo.idcard_info=JSON.stringify(idcard_info);
	//companyAutInfo.driving_license_info=JSON.stringify(driving_license_info);
	//companyAutInfo.business_license_info=JSON.stringify(business_license_info);
	//companyAutInfo.type = "save";
	$('#savecompanyaut').attr("disabled",true);    
	$.yilinAjax({
   	  	type:'POST',
   	  	url:'${WEB_PATH }/truck/truckimg/save.do',
   	  	//data:null,
   	  	data:{"truck.id":$("#truckid").val(),
   	  	"track_ahead_img":$("#track_ahead_img").val(),
   	  	"track_abehind_img":$("#track_abehind_img").val(),
   	  	"track_console_img":$("#track_console_img").val(),
   	  	"track_left_front_img":$("#track_left_front_img").val(),
   	  	"track_right_front_img":$("#track_right_front_img").val(),
   	  	"track_side_img":$("#track_side_img").val(),
   	  	"track_behind_img":$("#track_behind_img").val(),
   	  	"track_dashboard_img":$("#track_dashboard_img").val()},
   	  	btn:null,
		errorcallback:null,
		successcallback:success
   	});
	
}
function success(result){  
	layer.closeAll(); 
	if(result.success == true){
		$('#userModal').remove();
    	swal("操作成功",  result.msg, "success"); 
    	$("#exampleTableEvents").bootstrapTable('refresh');
	}else{
		swal("操作失败", result.msg, "error");
		$('#saveBut').attr("disabled",false);  
	}
} 
  </script>   
