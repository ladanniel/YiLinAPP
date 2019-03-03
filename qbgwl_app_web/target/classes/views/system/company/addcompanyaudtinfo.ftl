<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width: 85%;">
            <div id="rootwizard" class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">商户认证信息添加</h1>
                    <div  class="modal-body" style="max-height:450px;overflow-y: auto;"> 
                         <div  class="tabbable tabs-left">
							<ul class="nav nav-tabs" id="myTab">
							    <input name="company_id" type="hidden"  id="company_id" class="form-control" value="${company.id}" >
							  	<#if companyType.is_aut == true>
							  		<#if companyType.idcard == true>
									<li><a href="#tab2" data-toggle="tab" id="idcard_info">身份证认证</a></li>
									</#if>
									<#if companyType.driving_license == true>
									<li><a href="#tab3" data-toggle="tab" id="driving_license_info">驾驶证认证</a></li>
									</#if>
									<#if companyType.business_license == true>
									<li><a href="#tab4" data-toggle="tab" id="business_license_info">营业执照认证</a></li> 
									</#if>
								</#if>
							</ul>
							<div class="tab-content">
							    <#if companyType.is_aut == true>
							    <#if companyType.idcard == true>
							    <div class="tab-pane" id="tab2">
							      	<div class="ibox-content">
							      		<form id="idcard_info_form">
					                    	<div class="row">
					                            <div class="col-sm-6">
					                                <div class="form-group">
					                                    <label>真实姓名：</label>
					                                    <input id="name" name="name" type="text" class="form-control" placeholder="请输入真实姓名">
					                                </div> 
					                            </div>
					                             <div class="col-sm-6"> 
					                                <div class="form-group">
					                                    <label>身份证号码：</label>
					                                    <input id="idcard_no" name="idcard_no" type="text" class="form-control" placeholder="请输入身份证号码">
					                                </div>
					                            </div>
					                            <div class="col-sm-12">
					                                <div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="idcard_front_img_view_panel">
							                                    <div class="panel-heading">
							                                      	身份证正面上传-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div >
							                                    		 <div >
								                                         	<img id="idcard_front_img_view" src="${WEB_PATH}/resources/img/shenfengzhengzm.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_front_img_view_btn" >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">身份证正面上传</span>
    																					<input type="file" id="idcard_idcard_front_img" datatype="IDCARD" name="file" onchange="javascript:setImagePreview1(this,'idcard_front_img_view');">
    																					<input type="hidden" id="idcard_front_img" name = "idcard_front_img">
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
						                                    <div class="panel panel-primary" id="idcard_back_img_view_panel">
							                                    <div class="panel-heading">
							                                      	身份证反面上传-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
								                                         <div >
								                                         	<img id="idcard_back_img_view" src="${WEB_PATH}/resources/img/shenfenzhengfm.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_back_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">身份证反面上传</span>
    																					<input type="file" id="idcard_idcard_back_img" datatype="IDCARD"  name="file"  onchange="javascript:setImagePreview1(this,'idcard_back_img_view');">
    																					<input type="hidden" id="idcard_back_img"  name="idcard_back_img" >
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
						                                    <div class="panel panel-primary" id="idcard_persoin_img_view_panel">
							                                    <div class="panel-heading">
							                                      	手持身份证照片上传-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
							                                    		 <div >
								                                         	<img id="idcard_persoin_img_view" src="${WEB_PATH}/resources/img/shenfenzhengsc.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="idcard_persoin_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">手持身份证照片上传</span>
    																					<input type="file" id="idcard_idcard_persoin_img" datatype="IDCARD" name="file" onchange="javascript:setImagePreview1(this,'idcard_persoin_img_view');">
    																					<input type="hidden" id="idcard_persoin_img"  name="idcard_persoin_img" >
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
							    </#if>
							    <#if companyType.driving_license == true>
								<div class="tab-pane" id="tab3">
									<div class="ibox-content">
									    <form id="driving_license_info_form">
					                    	<div class="row">
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                    <label>驾驶证姓名：</label>
					                                    <input id="driving_name" name="name" type="text" class="form-control" placeholder="驾驶证姓名">
					                                </div> 
					                            </div>
					                             <div class="col-sm-4"> 
					                                <div class="form-group">
					                                    <label>驾驶证编号：</label>
					                                    <input id="driving_license_no" name="driving_license_no" type="text" class="form-control"  placeholder="请输入驾驶证编号">
					                                </div>
					                            </div>
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                    <label>性别：</label>
					                                    <select class="form-control" name="sex">
									                        <option value="1" selected = "selected">男</option> 
									                        <option value="2">女</option> 
														</select> 
					                                </div>
					                            </div>
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                    <label>准驾车型：</label>
					                                    <select class="form-control" name="driving_license_type_id">
					                                    	<@drivingLicenseTypeInfo>
										                    	<#list drivingtypeviews as drivingtype >
									                            	<option value="${drivingtype.id}" >${drivingtype.no}</option> 
									                            </#list>
															</@drivingLicenseTypeInfo>
														</select> 
					                                </div>
					                            </div>
					                            <div class="col-sm-4" > 
					                                <div class="form-group">
					                                     <label>有效起始日期：</label>
							                             <input type="text" class="input-sm form-control" id="valid_start_date" name="valid_start_date" style="background-color: #FFF;" placeholder="请选择有效起始日期" readonly/>
					                                </div>
					                            </div>
					                            <div class="col-sm-4" > 
					                                <div class="form-group">
					                                     <label>有效年限：</label>
							                             <input type="number" class="input-sm form-control" name="valid_year" maxlength="1" value="6" placeholder="请填写有效年限"/>
					                                </div>
					                            </div>
					                            <div class="col-sm-8"> 
					                                <div class="form-group">
					                                    <label>地址：</label>
					                                    <input id="address" name="address" type="text" class="form-control"  placeholder="请输入地址">
					                                </div>
					                            </div>
					                            <div class="col-sm-12">
					                            	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="driving_license_img_view_panel">
							                                    <div class="panel-heading">
							                                      	驾驶证照片上传-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
							                                    		 <div >
								                                         	<img id="driving_license_img_view" src="${WEB_PATH}/resources/img/jiashizheng.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="driving_license_img_view_btn" >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">驾驶证照片上传</span>
    																					<input type="file" id="driving_driving_license_img" datatype="DRIVING" name="file" onchange="javascript:setImagePreview1(this,'driving_license_img_view');">
    																					<input type="hidden" id="driving_license_img"  name="driving_license_img" >
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
							    </#if>
							    <#if companyType.business_license == true>
								<div class="tab-pane" id="tab4">
									<div class="ibox-content">
										<form id="business_license_info_form">
					                    	<div class="row">
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                    <label>企业名称：</label>
					                                    <input id="name" name="name" type="text" class="form-control" placeholder="请输入企业名称">
					                                </div> 
					                            </div>
					                             <div class="col-sm-4"> 
					                                <div class="form-group">
					                                    <label>营业执照编号：</label>
					                                    <input id="company_no" name="company_no" type="text" class="form-control"  placeholder="请输入营业执照编号">
					                                </div>
					                            </div>
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                    <label>地址：</label>
					                                    <input id="company_address" name="company_address" type="text" class="form-control"  placeholder="请输入地址">
					                                </div>
					                            </div>
					                            <div class="col-sm-4" > 
					                                <div class="form-group">
					                                     <label>有效起始日期：</label>
							                             <input type="text" class="input-sm form-control" id="company_create_date" name="company_create_date"  placeholder="请选择有效起始日期" readonly/>
					                                </div>
					                            </div>
					                             <div class="col-sm-4" > 
					                                <div class="form-group">
					                                     <label>有效截止日期：</label>
							                             <input type="text" class="input-sm form-control" id="company_validity_date" name="company_validity_date"  placeholder="请选择有效截止日期" readonly/>
					                                </div>
					                            </div>
					                            <div class="col-sm-12">
					                             	<div class="col-sm-4">
					                                	<div class="form-group">
						                                    <div class="panel panel-primary" id="company_img_view_panel">
							                                    <div class="panel-heading">
							                                      	营业执照上传-----上传的文件中允许上传格式为jpg、png、jpeg的图片
							                                    </div>
							                                    <div class="panel-body">
							                                    	<div>
							                                    		 <div >
								                                         	<img id="company_img_view" src="${WEB_PATH}/resources/img/yingyezhizhao.jpg" style="width: 100%;border: 1px solid #e5e6e7;">
								                                         	<div class="form-group" style="width:100%;padding-top: 5px;">
												                                <div>
												                                	<a href="javascript:void(0)" class="file" id="company_img_view_btn"  >
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">驾驶证照片上传</span>
    																					<input type="file" id="business_company_img" datatype="BUSINESS"  name="file" onchange="javascript:setImagePreview1(this,'company_img_view');">
    																					<input type="hidden" id="company_img"  name="company_img" >
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
					                        </div>
				                        </form>
				                    </div>
							    </div>
							    </#if>
							    </#if> 
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
	function setImagePreview1(obj,showImg){
	     var fileType  = $("#"+obj.id).val()
		 fileType = fileType.substring(fileType.lastIndexOf(".")+1,fileType.length);
		 fileType = fileType.toLowerCase();
		 if(fileTypes.indexOf(fileType) > -1){
			 var datatype = $("#"+obj.id).attr("datatype");
			 var imgurl  = obj.id.substring(obj.id.indexOf("_")+1,obj.id.length)
			 $.ajaxFileUpload({
	                url:'${WEB_PATH}/aut/authenticat/uploadimg.do?datatype='+datatype,
	                secureuri:false,
	                fileElementId:obj.id,
	                dataType: 'json',
	                success: function (data, status)
	                {
	                    if(data.success == true){
	                        $("#"+imgurl).val(data.imgpath);
	                        $("#"+showImg).attr("src", '${WEB_PATH}'+data.imgpath); 
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
  			return validatCompanyAutInfo(type);
  		},
  		'onFinish': function(tab, navigation, index) {
  			
  			var type = tab.get(0).childNodes[0].id;
  			if(validatCompanyAutInfo(type)){
  				layer.msg('正在努力提交中', {icon: 16});
  				saveCompanyAutInfo();
  			} 
  		},
  		'onPrevious': function(tab, navigation, index) {
  			$('body,html').animate({scrollTop:0},1000);
  		},
  		'onTabClick': function(tab, navigation, index) {
  			$('body,html').animate({scrollTop:0},1000);
  			var type = tab.get(0).childNodes[0].id;
  			return validatCompanyAutInfo(type);
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
	   	 var isfalse = validatCompanyAccountInfo();
	   	 if(isfalse){
	   	 	saveAccountCompany();
	   	 }
	}); 
	$('#noSaveBut').click(function(){  
	   	  var id = $("#company_id").val();  
		  layer.prompt({title: '请填写不通过原因', formType: 2}, function(text){
		  	  $('#noSaveBut').attr("disabled",true);  
		      saveAutCompany(id,false,text,$('#noSaveBut'))
		  }); 
	   	  
	}); 
	
});


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
        		setFileError('company_img_view_panel',false,"请上传驾驶证文件！");
        		return false;
        	}
    	}
	}
	return true;
}

function saveCompanyAutInfo(){
	var company_id =  $("#company_id").val();
	var idcard_info = $("#idcard_info_form").serializeJson();
	var driving_license_info = $("#driving_license_info_form").serializeJson();
	var business_license_info = $("#business_license_info_form").serializeJson();
	companyAutInfo=new Object();
	companyAutInfo.company_id= company_id;
	companyAutInfo.idcard_info=JSON.stringify(idcard_info);
	companyAutInfo.driving_license_info=JSON.stringify(driving_license_info);
	companyAutInfo.business_license_info=JSON.stringify(business_license_info);
	companyAutInfo.type = "save";
	$('#savecompanyaut').attr("disabled",true);    
	$.yilinAjax({
   	  	type:'POST',
   	  	url:'${WEB_PATH }/system/company/companyAutSave.do',
   	  	data:companyAutInfo,
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
