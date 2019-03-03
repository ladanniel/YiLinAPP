
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>商户信息认证</title>
    <meta name="keywords" content="易林物流平台-商户信息认证">
    <meta name="description" content="易林物流平台-商户信息认证">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${WEB_PATH}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${WEB_PATH}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
	<link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="${WEB_PATH}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox">
                    <div class="ibox-title"> 
                     	商户认证信息填写
                    </div>
	                    <div class="ibox-content">
                         <div id="rootwizard" class="tabbable tabs-left">
							<ul class="nav nav-tabs" id="myTab">
							    <#if company.source == 1> 
							  		<li><a href="#tab1" data-toggle="tab" id="company_info">商户基本信息</a></li>
							  	</#if>
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
								<#if company.source == 1> 
							    <div class="tab-pane" id="tab1">
								   <div class="ibox-content">
								   		<form id="company_info_form">
					                    	<div class="row">
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                    <label>商户名：</label>
					                                    <input id="name" name="name" type="text" class="form-control" placeholder="请输入商户名称">
					                                </div> 
					                            </div>
					                             <div class="col-sm-4"> 
					                                <div class="form-group">
					                                    <label>联系人姓名：</label>
					                                    <input id="contactName" name="contactName" type="text" class="form-control"  placeholder="请输入联系人姓名">
					                                </div>
					                            </div>
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                    <label>联系人电话：</label>
					                                    <input id="contactTel" name="contactTel" type="text" class="form-control" value="${company.contactTel}" placeholder="请输入联系人电话" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')">
					                                </div>
					                            </div>
					                            <div class="col-sm-4">
					                                <div class="form-group">
					                                	 <label>地区：</label>
													     <input id="areaFullName" name="areaFullName" class="form-control" readonly type="text" value="" data-toggle="city-picker">
													     <input id="area_id" name="area_id" type="hidden" >
					                                </div>
					                            </div>
					                            <div class="col-sm-8">
					                                <div class="form-group">
					                                    <label>地址：</label>
					                                    <input id="address" name="address" type="text" class="form-control" placeholder="请输入地址">
					                                </div>
					                            </div>
					                        </div>
				                        </form>
				                    </div>
							    </div>
							    </#if>
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
					                            	<div class="col-sm-7">
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
					                             	<div class="col-sm-7">
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
												                                		<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">营业执照照片上传</span>
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
								<ul class="pager wizard" style="text-align: right;">
									<li class="previous"><button class="btn btn-primary " type="button"><i class="fa fa-arrow-circle-left"></i>&nbsp;&nbsp;<span class="bold">上一步</span></button></li>
								  	<li class="next"><button class="btn btn-primary " type="button"><i class="fa fa-arrow-circle-right"></i>&nbsp;&nbsp;<span class="bold">下一步</span></button></li>
								  	<li class="finish"><button class="btn btn-primary btn-danger" id="savecompanyaut" type="button"><i class="fa fa-check"></i>&nbsp;<span class="bold">完 &nbsp;&nbsp;成</span></button></li>
								</ul>
							</div>
						</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${WEB_PATH}/resources/js/jquery.min.js?v=2.1.4"></script>
    <script src="${WEB_PATH}/resources/js/ajax.extend.js"></script>
    <script src="${WEB_PATH}/resources/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/wizard/jquery.bootstrap.wizard.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${WEB_PATH}/resources/js/plugins/validate/jquery.validate.method.js"></script>
    <script src="${WEB_PATH}/resources/js/city-picker.data.js"></script>
	<script src="${WEB_PATH}/resources/js/city-picker.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
	<script src="${WEB_PATH}/resources/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${WEB_PATH}/resources/js/ajaxfileupload.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
    <script>
    	var e = "<i class='fa fa-times-circle'></i> ";
    	var fileTypes = "jpg/png/jpeg";
    	function setImagePreview1(obj,showImg){
    	     var fileType  = $("#"+obj.id).val()
    		 fileType = fileType.substring(fileType.lastIndexOf(".")+1,fileType.length);
    		 fileType = fileType.toLowerCase();
    		 if(fileTypes.indexOf(fileType) > -1){
	    		 var datatype = $("#"+obj.id).attr("datatype");
	    		 var imgurl  = obj.id.substring(obj.id.indexOf("_")+1,obj.id.length);
	    		 var index = layer.msg('文件上传中......', {icon: 16,shade: 0.3,time:0});
				 $.ajaxFileUpload({
		                url:'${WEB_PATH}/aut/authenticat/uploadimg.do?datatype='+datatype,
		                secureuri:false,
		                fileElementId:obj.id,
		                dataType: 'json',
		                success: function (data, status)
		                {
		                	layer.close(index);
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
        $(document).ready(function() {
        
        	var $company_info_form =  $('#company_info_form').validate({
				  rules: {
				    name: {
				      required: true, 
				      minlength: 3,
				      maxlength:20,
				      remote:{
		              	  type:"POST",
		                  url:"checkCompanyName.do" 
		              }
				    },
				    contactName: {
				      required: true,
				      isChinese:true
				    },
				    contactTel: {
				      required: true,
				      isMobile: true,
				      maxlength:11
				    },
				    areaFullName:{
				      required: true
				    },
				    address: {
				      required: true
				    }
				  },
				  messages: {
		            name: {
		                remote:e+"商户名已存在，请重新输入！"
		            }
		          },
				  errorPlacement: function(error, element) {
				  		if(element.get(0).id == "areaFullName"){
		            		$('#areaFullName').citypicker("setError");
		            	}
		            	element.before(error)
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
		                  url:"checkIdcardNo.do",
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
		                  url:"checkDrivingLicenseNo.do" 
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
		                  url:"checkBusinessLicenseNo.do",
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
		});
		
		function saveCompanyAutInfo(){
			var company_info = $("#company_info_form").serializeJson();
			var idcard_info = $("#idcard_info_form").serializeJson();
			var driving_license_info = $("#driving_license_info_form").serializeJson();
			var business_license_info = $("#business_license_info_form").serializeJson();
			companyAutInfo=new Object();
			companyAutInfo.company_info= JSON.stringify(company_info);
			companyAutInfo.idcard_info=JSON.stringify(idcard_info);
			companyAutInfo.driving_license_info=JSON.stringify(driving_license_info);
			companyAutInfo.business_license_info=JSON.stringify(business_license_info);
			companyAutInfo.type = "save";
			$('#savecompanyaut').attr("disabled",true);    
			$.yilinAjax({
		   	  	type:'POST',
		   	  	loadmsg:'正在努力提交中......',
		   	  	url:'${WEB_PATH }/aut/authenticat/companyAutSave.do',
		   	  	data:companyAutInfo,
		   	  	btn:null,
        		errorcallback:null,
        		successcallback:success
		   	});
			
		}
		
		function success(result){  
			if(result.success == true){
		    	layer.alert('恭喜您，你的认证信息提交成功，管理员会进行审核，请等待！', {
				  skin: 'layui-layer-molv',
				  closeBtn: 0
				},function(){
				   window.location.href="${WEB_PATH }/system/company/companyinfo.do"
				   $("#hideli_id",window.parent.document).hide();
				});
			}else{
				$('#savecompanyaut').attr("disabled",false);    
		    	layer.alert(result.msg, {
				  skin: 'layui-layer-molv' ,
				  closeBtn: 0
				});
			}
		}
		
		function validatCompanyAutInfo(type){
			if(type == "company_info"){
				var $valid = $("#company_info_form").valid();
	  			if(!$valid) {
	  				return false;
	  			}else{
	  				return true;
	  			}
	    	}
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
		        	if(idcard_idcard_persoin_img == "" ){
		        		setFileError('idcard_persoin_img_view_panel',false,"请上传手持身份证文件！");
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
    </script>
</body>

</html>